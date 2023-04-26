package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.HibachiBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HibachiBlock extends BlockWithEntity {

    public static final BooleanProperty ACTIVE;

    public HibachiBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(ACTIVE, false)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        this.updateEnabled(world, pos, state, 2);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        this.updateEnabled(world, pos, state, 4);
    }

    private void updateEnabled(World world, BlockPos pos, BlockState state, int flags) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(ACTIVE)) {
            world.setBlockState(pos, (BlockState)state.with(ACTIVE, bl), flags);
//            if (bl) {
//                world.setBlockState(pos.up(), BlocksRegistry.HIBACHI_FIRE.get().getDefaultState().with(HibachiFireBlock.AGE, 0).with(HibachiFireBlock.STOKED, false), flags);
//                world.scheduleBlockTick(pos, BlocksRegistry.HIBACHI.get(), 5);
//            }
//            world.scheduleBlockTick(pos.up(), BlocksRegistry.HIBACHI_FIRE.get(), 5);
        }
    }

//    @Override
//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        world.scheduleBlockTick(pos, this, 5);
//        if (state.get(ACTIVE)) {
//            BlockState blockState = world.getBlockState(pos.up());
//            if (!blockState.isIn(Tags.BLOCKS_HIBACHI_FIRE) && blockState.isOf(BlocksRegistry.HIBACHI_FIRE.get())) {
//                world.setBlockState(pos, BlocksRegistry.HIBACHI_FIRE.get().getDefaultState().with(HibachiFireBlock.AGE, 0).with(HibachiFireBlock.STOKED, false), Block.NOTIFY_ALL);
//            }
////            int i = state.get(AGE);
////            if (i >= 15) {
////            } else {
////                world.setBlockState(pos, state.with(AGE, i + 1).with(STOKED, true), Block.NO_REDRAW);
////            }
//        }
//    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.HIBACHI_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return state.get(ACTIVE) ? checkType(type, EntitiesRegistry.HIBACHI_ENTITY, HibachiBlockEntity::tick) : null;
    }

    static {
        ACTIVE = BooleanProperty.of("active");
    }
}
