package net.komisarzprog.minecart_tweaks.mixin;

import net.komisarzprog.minecart_tweaks.MinecartTweaksConfig;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecartEntity.class)
public class AbstractMinecartEntityMixin {
    @Unique double maxSpeed = MinecartTweaksConfig.getNormalMinecartSpeed();

    // * Boost minecart max speed
    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void minecarttweaks$boostMaxSpeed(CallbackInfoReturnable<Double> cir)
    {
        Vec3d velocity = ((FurnaceMinecartEntity)(Object)this).getVelocity();
        boolean isTurning = Math.abs(velocity.x) != 0 && Math.abs(velocity.z) != 0;

        if(!isTurning)
            cir.setReturnValue(maxSpeed);
        else
            cir.setReturnValue(0.4D);
    }
}
