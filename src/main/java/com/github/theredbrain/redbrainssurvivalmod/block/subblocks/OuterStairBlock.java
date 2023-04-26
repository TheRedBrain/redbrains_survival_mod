package com.github.theredbrain.redbrainssurvivalmod.block.subblocks;

import com.github.theredbrain.redbrainssurvivalmod.block.enums.Orientation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class OuterStairBlock extends AbstractSubBlock {

    public static final EnumProperty<BlockHalf> HALF;
    public static final EnumProperty<Orientation> ORIENTATION;
    // part shapes
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    // final shapes
    protected static final VoxelShape HORIZONTAL_BOTTOM_SOUTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_WEST_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_NORTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_BOTTOM_EAST_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_SOUTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_WEST_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_NORTH_SHAPE;
    protected static final VoxelShape HORIZONTAL_TOP_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_NORTH_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_NORTH_WEST_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_EAST_SHAPE;
    protected static final VoxelShape VERTICAL_SOUTH_WEST_SHAPE;

    public OuterStairBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{ORIENTATION});
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return null;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return null;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        //TODO
        // can replace corners, edges and corner edges of same base block
        return false;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        ORIENTATION = EnumProperty.of("orientation", Orientation.class);
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);

        // TODO
        HORIZONTAL_BOTTOM_SOUTH_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_WEST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_NORTH_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_BOTTOM_EAST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        HORIZONTAL_TOP_SOUTH_SHAPE = VoxelShapes.union(TOP_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_WEST_SHAPE = VoxelShapes.union(TOP_NORTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_NORTH_SHAPE = VoxelShapes.union(TOP_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        HORIZONTAL_TOP_EAST_SHAPE = VoxelShapes.union(TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_NORTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE);
        VERTICAL_NORTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        VERTICAL_SOUTH_EAST_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        VERTICAL_SOUTH_WEST_SHAPE = VoxelShapes.union(BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
    }
}
