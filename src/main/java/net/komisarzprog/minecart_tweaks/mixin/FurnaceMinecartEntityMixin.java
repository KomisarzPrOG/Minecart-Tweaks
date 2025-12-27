package net.komisarzprog.minecart_tweaks.mixin;

import net.komisarzprog.minecart_tweaks.MinecartTweaksConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceMinecartEntity.class)
public abstract class FurnaceMinecartEntityMixin {
    @Shadow private int fuel;
    @Shadow public abstract boolean isLit();

    @Unique ChunkPos previousChunk;
    @Unique int maxFuel = MinecartTweaksConfig.furnaceBurnTime;
    @Unique int fuelPerItem = MinecartTweaksConfig.fuelPerItem;
    // TODO: allow for higher speeds; make minecarts not fall of tracks when going too fast on turns
    @Unique double maxSpeed = MinecartTweaksConfig.getFurnaceMinecartSpeed();

    // * Furnace Minecarts load chunks on their way
    @Inject(method = "tick", at = @At("HEAD"))
    private void minecarttweaks$loadChunks(CallbackInfo info)
    {
        Entity self = (Entity)(Object)this;
        World world = self.getEntityWorld();

        if(world instanceof ServerWorld server)
        {
            ChunkPos currentChunk = ChunkSectionPos.from(self).toChunkPos();

            if(previousChunk == null)
                previousChunk = currentChunk;

            if(fuel > 0)
            {
                server.getChunkManager().addTicket(ChunkTicketType.FORCED, currentChunk, 3);
//                System.out.println("[MinecartTweaks] ADD ticket @ " + currentChunk);
            }
            if(!currentChunk.equals(previousChunk) || fuel <= 0)
            {
                server.getChunkManager().removeTicket(ChunkTicketType.FORCED, previousChunk, 3);
//                System.out.println("[MinecartTweaks] REMOVE ticket @ " + previousChunk);
            }
            previousChunk = currentChunk;
        }
    }

    // * Boost minecart max speed
    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void minecarttweaks$boostMaxSpeed(CallbackInfoReturnable<Double> cir)
    {
        Vec3d velocity = ((FurnaceMinecartEntity)(Object)this).getVelocity();
        boolean isTurning = Math.abs(velocity.x) != 0 && Math.abs(velocity.z) != 0;

        if(isLit() && !isTurning)
            cir.setReturnValue(maxSpeed);
        else
            cir.setReturnValue(0.4D);
    }

    // * Increase max burn time
    @ModifyConstant(method = "addFuel", constant = @Constant(intValue = 32000))
    private int minecarttweaks$increaseMaxFuel(int original)
    {
        return maxFuel;
    }

    // * Modify how much fuel one item gives
    @ModifyConstant(method = "addFuel", constant = @Constant(intValue = 3600))
    private int minecarttweaks$increaseFuelPerItem(int original)
    {
        return fuelPerItem;
    }
}
