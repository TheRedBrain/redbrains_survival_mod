package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;

public class CreativeMechanicalSourceBlock extends Block {
    public CreativeMechanicalSourceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState superState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (!superState.isAir()) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.up()));
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.down()));
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.south()));
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.west()));
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.north()));
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.AXLE.get(), pos.east()));
        }

        return superState;
    }
}
