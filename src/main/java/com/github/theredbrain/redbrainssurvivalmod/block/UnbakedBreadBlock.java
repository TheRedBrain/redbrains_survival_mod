package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class UnbakedBreadBlock extends Block {
    public static final EnumProperty<Direction.Axis> FACING;
    public static final VoxelShape X_SHAPE;
    public static final VoxelShape Z_SHAPE;

    public UnbakedBreadBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(FACING, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
//        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing() == Direction.NORTH || ctx.getHorizontalPlayerFacing() == Direction.SOUTH ? Direction.Axis.Z : Direction.Axis.X);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FACING) == Direction.Axis.Z  ? Z_SHAPE : X_SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        FACING = Properties.HORIZONTAL_AXIS;
        X_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 2.0D, 11.0D, 4.0D, 14.0D);
        Z_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 5.0D, 14.0D, 4.0D, 11.0D);
    }
}
