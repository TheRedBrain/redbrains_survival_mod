package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DirtPathSlabBlock extends CustomSlabBlock {
    protected static VoxelShape BOTTOM_SHAPE;
    protected static VoxelShape FULL_SHAPE;

    // dirt_path can be converted from dirt, grass block, coarse dirt, mycelium, podzol, or rooted dirt

    public DirtPathSlabBlock(Settings settings) {
        super(settings);
    }

//    public DirtPathBlock(Settings settings) {
//        super(settings);
//    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

//    public BlockState getPlacementState(ItemPlacementContext ctx) {
//        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? Block.pushEntitiesUpBeforeBlockChange(this.getDefaultState(), BlocksRegistry.DIRT_SLAB.get().getDefaultState(), ctx.getWorld(), ctx.getBlockPos()) : super.getPlacementState(ctx);
//    }
//
//    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
//        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
//            world.scheduleBlockTick(pos, this, 1);
//        }
//
//        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
//    }

//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        SlabType slabType = (SlabType)state.get(TYPE);
//        switch(slabType) {
//            case DOUBLE:
//                return VoxelShapes.fullCube();
//            default:
//                return BOTTOM_SHAPE;
//        }
//    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return FULL_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, BlocksRegistry.DIRT_SLAB.get().getDefaultState(), world, pos));
//    }
//
//    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//        BlockState blockState = world.getBlockState(pos.up());
//        return !(state.get(WATERLOGGED)) && (!blockState.getMaterial().isSolid() || blockState.getBlock() instanceof FenceGateBlock);
//    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
//
//    static {
//        SHAPE = FarmlandBlock.SHAPE;
//    }

    static {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        FULL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    }
}
