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

public class UnfiredUrnBlock extends UnfiredPotteryBlock {

    public static VoxelShape SHAPE_COMPLETE;

    public UnfiredUrnBlock(Block nextBlock, boolean dropClayBall, Settings settings) {
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
        SHAPE_COMPLETE = VoxelShapes.union(
                Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0),
                Block.createCuboidShape(5.0, 1.0, 5.0, 11.0, 7.0, 11.0),
                Block.createCuboidShape(5.0, 8.0, 5.0, 11.0, 9.0, 11.0));
    }
}
