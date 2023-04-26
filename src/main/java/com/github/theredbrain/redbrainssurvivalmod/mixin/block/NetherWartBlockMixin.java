package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherWartBlock.class)
public class NetherWartBlockMixin {

    @Shadow
    @Final
    public static IntProperty AGE;

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        int i = (Integer)state.get(AGE);
        if (i < 3 && random.nextInt(10) == 0 && world.getDimension().ultrawarm()) {
            state = (BlockState)state.with(AGE, i + 1);
            world.setBlockState(pos, state, 2);
        }
        ci.cancel();
    }
}
