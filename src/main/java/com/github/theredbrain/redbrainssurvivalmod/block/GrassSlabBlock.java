package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.plants.CustomFernBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.plants.CustomTallPlantBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class GrassSlabBlock extends CustomSlabBlock {
    private static VoxelShape BOTTOM_SHAPE;
    private static VoxelShape FULL_SHAPE;
    public GrassSlabBlock(Settings settings) {
        super(settings);
    }

    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else if ((state.isOf(BlocksRegistry.LOOSE_DIRT_SLAB.get()) || state.isOf(BlocksRegistry.DIRT_SLAB.get()) || state.isOf(BlocksRegistry.GRASS_SLAB_SPARSE.get()) || state.isOf(BlocksRegistry.GRASS_SLAB.get())) && state.get(WATERLOGGED)) {
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
            world.setBlockState(pos, BlocksRegistry.DIRT_SLAB.get().getDefaultState().with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)));
        } else {
            if (world.getLightLevel(LightType.SKY, pos.up()) > 0 || (world.getLightLevel(LightType.BLOCK, pos.up()) > 0 && world.isSkyVisible(pos))) {
                BlockState fullBlockState = BlocksRegistry.GRASS_BLOCK.get().getDefaultState();
                BlockState slabBlockState = this.getDefaultState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

                    if ((world.getBlockState(blockPos).isOf(Blocks.DIRT) || world.getBlockState(blockPos).isOf(BlocksRegistry.LOOSE_DIRT.get())) && canSpread(fullBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)fullBlockState.with(SnowyBlock.SNOWY, (world.getBlockState(blockPos.up()).isOf(Blocks.SNOW) || (world.getBlockState(blockPos.up()).getBlock() instanceof CustomFernBlock && world.getBlockState(blockPos.up()).get(CustomFernBlock.SNOWY)) || (world.getBlockState(blockPos.up()).getBlock() instanceof CustomTallPlantBlock && world.getBlockState(blockPos.up()).get(CustomTallPlantBlock.SNOWY)))));
                    } else if ((world.getBlockState(blockPos).isOf(BlocksRegistry.DIRT_SLAB.get()) || world.getBlockState(blockPos).isOf(BlocksRegistry.LOOSE_DIRT_SLAB.get())) && !(world.getBlockState(blockPos).get(WATERLOGGED)) && canSpread(slabBlockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState)slabBlockState.with(TYPE, world.getBlockState(blockPos).get(TYPE)).with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW) && world.getBlockState(blockPos).get(TYPE) == SlabType.DOUBLE));
                    }
                }
            }

        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return FULL_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

    static {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        FULL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }
}
