package net.komisarzprog.minecart_tweaks.mixin;

import net.komisarzprog.minecart_tweaks.MinecartTweaks;
import net.komisarzprog.minecart_tweaks.MinecartTweaksConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin {

    @Shadow
    public abstract boolean collidesWith(Entity other);

    @Unique double maxSpeed = MinecartTweaksConfig.getNormalMinecartSpeed();

    // * Minecarts ram Entities on their way
    @Inject(method = "tick", at = @At("HEAD"))
    private void minecarttweaks$tick(CallbackInfo info)
    {
        Entity self = (Entity)(Object)this;
        World world = self.getEntityWorld();

        if(!world.isClient())
        {
            if(MinecartTweaksConfig.allowRamming)
            {
                for(Entity other : world.getOtherEntities(self, self.getBoundingBox().expand(0.1), this::collidesWith))
                {
                    float damage = MinecartTweaksConfig.ramDamage;

                    if(damage > 0 && other instanceof LivingEntity living && living.isAlive() && !living.hasVehicle() && self.getVelocity().length() > MinecartTweaksConfig.getMinRamSpeed())
                    {
                        Vec3d knockback = living.getVelocity().add(self.getVelocity().getX() * 0.9, self.getVelocity().length() * 0.2, self.getVelocity().getZ() * 0.9);
                        living.setVelocity(knockback);
                        living.velocityDirty = true;


                        DamageSource source = world.getDamageSources()
                                .create(
                                        MinecartTweaks.MINECART_DAMAGE,
                                        self
                                );

                        if(world instanceof ServerWorld server) living.damage(server, source, damage);
                    }
                }
            }
        }
    }

    // * Boost minecart max speed
    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void minecarttweaks$boostMaxSpeed(CallbackInfoReturnable<Double> cir)
    {
        Vec3d velocity = ((AbstractMinecartEntity)(Object)this).getVelocity();
        boolean isTurning = Math.abs(velocity.x) != 0 && Math.abs(velocity.z) != 0;

        if(!isTurning)
            cir.setReturnValue(maxSpeed);
        else
            cir.setReturnValue(0.4D);
    }
}
