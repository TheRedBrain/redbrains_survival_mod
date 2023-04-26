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

public class UnfiredCrucibleBlock extends UnfiredPotteryBlock {

    public static VoxelShape SHAPE_COMPLETE;
    public static final VoxelShape SHAPE_POSITIVE;
    public static final VoxelShape SHAPE_NEGATIVE;

    public UnfiredCrucibleBlock(Block nextBlock, boolean dropClayBall, Settings settings) {
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
        SHAPE_POSITIVE = VoxelShapes.union(Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 14.0, 16.0));
        SHAPE_NEGATIVE = Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);
        SHAPE_COMPLETE = VoxelShapes.combineAndSimplify(SHAPE_POSITIVE, SHAPE_NEGATIVE, BooleanBiFunction.ONLY_FIRST);
    }
}
