package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StemBlock.class)
public class StemBlockMixin extends PlantBlock{

    @Shadow
    @Final
    public static IntProperty AGE;

    @Shadow
    @Final
    private GourdBlock gourdBlock;

    public StemBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void waitsForFertilization(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            float f = getAvailableMoisture(this, world, pos);
            if (random.nextInt((int)(100.0F / f) + 1) == 0) {
                int i = (Integer)state.get(AGE);
                if (i > 0 && i < 7) {
                    state = (BlockState)state.with(AGE, i + 1);
                    world.setBlockState(pos, state, 2);
                } else if (i >= 7){
                    Direction direction = Direction.Type.HORIZONTAL.random(random);
                    BlockPos blockPos = pos.offset(direction);
                    BlockState blockState = world.getBlockState(blockPos.down());
                    if (world.getBlockState(blockPos).isAir() && (blockState.isOf(Blocks.FARMLAND) || blockState.isIn(BlockTags.DIRT))) {
                        world.setBlockState(blockPos, this.gourdBlock.getDefaultState());
                        world.setBlockState(pos, (BlockState)this.gourdBlock.getAttachedStem().getDefaultState().with(HorizontalFacingBlock.FACING, direction));
                    }
                }
            }

        }
        ci.cancel();
    }

    @Inject(method = "isFertilizable", at = @At("RETURN"), cancellable = true)
    public void isFertilizableSometimes(WorldView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((Integer)state.get(AGE) == 0);
    }

    @Inject(method = "canGrow", at = @At("RETURN"), cancellable = true)
    public void canGrowSometimes(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "grow", at = @At("HEAD"), cancellable = true)
    public void growOneStage(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        int i = (Integer)state.get(AGE) + 1;
        BlockState blockState = (BlockState)state.with(AGE, i);
        world.setBlockState(pos, blockState, 2);

        ci.cancel();
    }

    private static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.isOf(Blocks.FARMLAND)) {
                    g = 1.0F;
                    if ((Integer)blockState.get(FarmlandBlock.MOISTURE) > 0) {
                        g = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }

        return f;
    }

//    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
//    private void stemBlockCanPlantOnTopOfRichSoil(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(cir.getReturnValue() || floor.isOf(BlocksRegistry.RICH_SOIL_FARMLAND));
//    }
}
