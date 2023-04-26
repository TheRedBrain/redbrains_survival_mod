package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {

    @Inject(method = "fillFromDripstone", at = @At("HEAD"), cancellable = true)
    protected void fillNotFromLavaDripstone(BlockState state, World world, BlockPos pos, Fluid fluid, CallbackInfo ci) {
        BlockState blockState;
        if (fluid == Fluids.WATER) {
            blockState = Blocks.WATER_CAULDRON.getDefaultState();
            world.setBlockState(pos, blockState);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
            world.syncWorldEvent(1047, pos, 0);
        }

        ci.cancel();
    }
}
