package net.komisarzprog.minecart_tweaks.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceMinecartEntity.class)
public abstract class FurnaceMinecartEntityMixin {
    @Shadow public abstract boolean isLit();
    @Shadow private int fuel;

    // Furnace Minecarts get faster
    @Inject(method = "tick", at = @At("TAIL"))
    private void minecarttweaks$boostSpeed(CallbackInfo ci)
    {
        FurnaceMinecartEntity self = (FurnaceMinecartEntity)(Object)this;

        if(self.isAlive() && fuel > 0)
        {
            Vec3d velocity = self.getVelocity();
            self.setVelocity(velocity.multiply(1.05));
        }
    }

    // Boost minecart max speed
    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void minecarttweaks$boostMaxSpeed(CallbackInfoReturnable<Double> cir)
    {
        if(isLit())
            // Boost minecart max speed to 2x the amount for now
            cir.setReturnValue(0.8D);
        else
            cir.setReturnValue(0.4D);
    }
}
