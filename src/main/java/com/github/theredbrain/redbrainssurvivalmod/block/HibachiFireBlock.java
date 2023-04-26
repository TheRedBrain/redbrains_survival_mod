package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HibachiFireBlock extends AbstractFireBlock {
    public static final IntProperty AGE;
    public static final BooleanProperty STOKED;
    public HibachiFireBlock(Settings settings) {
        super(settings, 2.0f);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0).with(STOKED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE, STOKED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return state;
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(BlocksRegistry.HIBACHI.get()) && world.getBlockState(pos.down()).get(HibachiBlock.ACTIVE);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.scheduleBlockTick(pos, this, 5);
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
        if (state.get(STOKED)) {
            int i = state.get(AGE);
            if (i >= 12) {
                world.setBlockState(pos, state.with(AGE, 0).with(STOKED, false), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, state.with(AGE, i + 1).with(STOKED, true), Block.NO_REDRAW);
            }
        }
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return true;
    }

    static {
        AGE = IntProperty.of("age", 0, 12);
        STOKED = BooleanProperty.of("stoked");
    }
}
