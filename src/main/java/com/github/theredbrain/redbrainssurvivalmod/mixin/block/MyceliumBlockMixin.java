package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MyceliumBlock.class)
public class MyceliumBlockMixin {

    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else if ((state.isOf(BlocksRegistry.LOOSE_DIRT_SLAB.get()) || state.isOf(BlocksRegistry.DIRT_SLAB.get())) && state.get(Properties.WATERLOGGED)) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                BlockState fullBlockState = Blocks.MYCELIUM.getDefaultState();
                BlockState slabBlockState = BlocksRegistry.MYCELIUM_SLAB.get().getDefaultState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

                    if (world.getBlockState(blockPos).isOf(Blocks.DIRT) && canSpread(fullBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)fullBlockState.with(SnowyBlock.SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    } else if (world.getBlockState(blockPos).isOf(BlocksRegistry.LOOSE_DIRT.get()) && canSpread(fullBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)fullBlockState.with(SnowyBlock.SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    } else if (world.getBlockState(blockPos).isOf(BlocksRegistry.DIRT_SLAB.get()) && !(world.getBlockState(blockPos).get(Properties.WATERLOGGED)) && canSpread(slabBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)slabBlockState.with(Properties.SLAB_TYPE, world.getBlockState(blockPos).get(Properties.SLAB_TYPE)).with(Properties.SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW) && world.getBlockState(blockPos).get(Properties.SLAB_TYPE) == SlabType.DOUBLE));
                    } else if (world.getBlockState(blockPos).isOf(BlocksRegistry.LOOSE_DIRT_SLAB.get()) && !(world.getBlockState(blockPos).get(Properties.WATERLOGGED)) && canSpread(slabBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)slabBlockState.with(Properties.SLAB_TYPE, world.getBlockState(blockPos).get(Properties.SLAB_TYPE)).with(Properties.SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW) && world.getBlockState(blockPos).get(Properties.SLAB_TYPE) == SlabType.DOUBLE));
                    }
                }
            }

        }
    }
}
