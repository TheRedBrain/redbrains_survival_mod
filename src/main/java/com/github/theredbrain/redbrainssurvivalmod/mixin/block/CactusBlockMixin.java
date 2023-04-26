package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(CactusBlock.class)
public class CactusBlockMixin extends Block {

    @Shadow
    @Final
    public static IntProperty AGE;

    public CactusBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTickCheckForRoots(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos blockPos = pos.up();
        if (world.isAir(blockPos)) {
            int i;
            for(i = 1; (world.getBlockState(pos.down(i)).isOf(this) || world.getBlockState(pos.down(i)).isOf(BlocksRegistry.CACTUS_ROOT.get())); ++i) {
            }

            if (i < 3) {
                int j = (Integer)state.get(AGE);
                if (j == 15) {
                    world.setBlockState(blockPos, this.getDefaultState());
                    BlockState blockState = (BlockState)state.with(AGE, 0);
                    world.setBlockState(pos, blockState, 4);
                    world.updateNeighbor(blockState, blockPos, this, pos, false);
                } else {
                    world.setBlockState(pos, (BlockState)state.with(AGE, j + 1), 4);
                }

            }
        }
        ci.cancel();
    }

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void canPlaceAtRoots(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Iterator var4 = Type.HORIZONTAL.iterator();

        Direction direction;
        Material material;
        boolean bl = false;
        do {
            if (!var4.hasNext()) {
                BlockState blockState2 = world.getBlockState(pos.down());
                bl = ((blockState2.isOf(Blocks.CACTUS) || blockState2.isOf(BlocksRegistry.CACTUS_ROOT.get())) && !world.getBlockState(pos.up()).getMaterial().isLiquid());
                break;
            }

            direction = (Direction)var4.next();
            BlockState blockState = world.getBlockState(pos.offset(direction));
            material = blockState.getMaterial();
        } while(!material.isSolid() && !world.getFluidState(pos.offset(direction)).isIn(FluidTags.LAVA));

        cir.setReturnValue(bl);
        cir.cancel();
    }
}
