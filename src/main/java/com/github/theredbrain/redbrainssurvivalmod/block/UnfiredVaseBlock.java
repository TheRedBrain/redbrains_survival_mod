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

public class UnfiredVaseBlock extends UnfiredPotteryBlock {

    public static VoxelShape SHAPE_COMPLETE;

    public UnfiredVaseBlock(Block nextBlock, boolean dropClayBall, Settings settings) {
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
                Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
                Block.createCuboidShape(3.0, 1.0, 3.0, 13.0, 7.0, 13.0),
                Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 15.0, 10.0),
                Block.createCuboidShape(5.0, 15.0, 5.0, 11.0, 16.0, 11.0));
    }
}
