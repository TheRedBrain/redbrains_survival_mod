package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.AffectsVelocityOnCollision;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Iterator;

public class SugarCaneRootBlock extends Block implements AffectsVelocityOnCollision {
    public static final IntProperty AGE;
    protected static final VoxelShape SHAPE;

    public SugarCaneRootBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isAir(pos.up())) {
            int j = (Integer)state.get(AGE);
            if (j == 15) {
                world.setBlockState(pos.up(), Blocks.SUGAR_CANE.getDefaultState());
                world.setBlockState(pos, (BlockState)state.with(AGE, 0), 4);
            } else {
                world.setBlockState(pos, (BlockState)state.with(AGE, j + 1), 4);
            }
        }

    }

    @Override
    public float getVelocityMultiplierOnCollision() {
        return Constants.VELOCITY_MULTIPLYER_0_8;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (blockState.isIn(BlockTags.DIRT) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.RED_SAND)) {
            BlockPos blockPos = pos.down();
            Iterator var6 = Direction.Type.HORIZONTAL.iterator();

            while(var6.hasNext()) {
                Direction direction = (Direction)var6.next();
                BlockState blockState2 = world.getBlockState(blockPos.offset(direction));
                FluidState fluidState = world.getFluidState(blockPos.offset(direction));
                if (fluidState.isIn(FluidTags.WATER) || blockState2.isOf(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    static {
        AGE = Properties.AGE_15;
        SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    }
}
