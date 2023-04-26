package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MudSlabBlock extends CustomSlabBlock {
    protected static final VoxelShape COLLISION_SHAPE_FULL = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);
    protected static final VoxelShape COLLISION_SHAPE_SLAB = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    protected static final VoxelShape CAMERA_COLLISION_SHAPE_SLAB = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public MudSlabBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return COLLISION_SHAPE_FULL;
        } else {
            return COLLISION_SHAPE_SLAB;
        }
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return VoxelShapes.fullCube();
        } else {
            return CAMERA_COLLISION_SHAPE_SLAB;
        }
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return VoxelShapes.fullCube();
        } else {
            return CAMERA_COLLISION_SHAPE_SLAB;
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2f;
    }
}
