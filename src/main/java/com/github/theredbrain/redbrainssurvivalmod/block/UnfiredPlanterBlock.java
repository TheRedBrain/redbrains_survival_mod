package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class UnfiredPlanterBlock extends UnfiredPotteryBlock {

    public static VoxelShape SHAPE_COMPLETE;
    public static final VoxelShape SHAPE_POSITIVE;
    public static final VoxelShape SHAPE_NEGATIVE;

    public UnfiredPlanterBlock(Block nextBlock, boolean dropClayBall, Settings settings) {
        super(nextBlock, dropClayBall, settings);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_COMPLETE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    static {
        SHAPE_POSITIVE = VoxelShapes.union(Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 11.0, 14.0), Block.createCuboidShape(0.0, 11.0, 0.0, 16.0, 16.0, 16.0));
        SHAPE_NEGATIVE = VoxelShapes.union(Block.createCuboidShape(4.0, 2.0, 4.0, 12.0, 11.0, 12.0), Block.createCuboidShape(2.0, 11.0, 2.0, 14.0, 16.0, 14.0));
        SHAPE_COMPLETE = VoxelShapes.combineAndSimplify(SHAPE_POSITIVE, SHAPE_NEGATIVE, BooleanBiFunction.ONLY_FIRST);
    }
}
