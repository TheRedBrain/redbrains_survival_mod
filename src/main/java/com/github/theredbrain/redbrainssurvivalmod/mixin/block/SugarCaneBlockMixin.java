package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.block.AffectsVelocityOnCollision;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin extends Block implements AffectsVelocityOnCollision {

    @Shadow
    @Final
    public static IntProperty AGE;

    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTickCheckForRoots(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.isAir(pos.up())) {
            int i;
            for(i = 1; (world.getBlockState(pos.down(i)).isOf(this) || world.getBlockState(pos.down(i)).isOf(BlocksRegistry.SUGAR_CANE_ROOT.get())); ++i) {
            }

            if (i < 3) {
                int j = (Integer)state.get(AGE);
                if (j == 15) {
                    world.setBlockState(pos.up(), this.getDefaultState());
                    world.setBlockState(pos, (BlockState)state.with(AGE, 0), 4);
                } else {
                    world.setBlockState(pos, (BlockState)state.with(AGE, j + 1), 4);
                }
            }
        }
        ci.cancel();
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void canPlaceAtRoots(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isOf(this) || blockState.isOf(BlocksRegistry.SUGAR_CANE_ROOT.get())) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }

    @Override
    public float getVelocityMultiplierOnCollision() {
        return Constants.VELOCITY_MULTIPLYER_0_8;
    }
}
