package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class TurntableBlock extends Block {
    public static final IntProperty DELAY;
    public static final BooleanProperty MECHANICAL_POWERED;
    public static final BooleanProperty REDSTONE_POWERED;
    public TurntableBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(DELAY, 0).with(MECHANICAL_POWERED, false).with(REDSTONE_POWERED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DELAY, MECHANICAL_POWERED, REDSTONE_POWERED);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState newState = this.getDefaultState().with(REDSTONE_POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
        BlockState neighborState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (neighborState.isOf(BlocksRegistry.AXLE.get()) && neighborState.get(AxleBlock.FACING) == Direction.DOWN) {
            boolean bl = neighborState.get(AxleBlock.POWER) >= 1;
            if (bl) {
                ctx.getWorld().scheduleBlockTick(ctx.getBlockPos(), BlocksRegistry.TURNTABLE.get(), getDelayForDelayState(newState.get(TurntableBlock.DELAY)));
            }
            newState = newState.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.POWER) >= 1);
        }

        return newState;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(REDSTONE_POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.scheduleBlockTick(pos, this, 4);
                } else {
                    world.setBlockState(pos, (BlockState)state.cycle(REDSTONE_POWERED), 2);
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty()) {
            if (world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                this.cycleDelay(state, world, pos);
                world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, 0.5f);
                world.emitGameEvent((Entity) player, GameEvent.BLOCK_CHANGE, pos);
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    public void cycleDelay(BlockState state, World world, BlockPos pos) {
        state = (BlockState)state.cycle(TurntableBlock.DELAY);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }

    private int getDelayForDelayState(int delayState) {
        return delayState == 0 ? 10 : delayState == 1 ? 20 : delayState == 2 ? 40 : 80;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl = state.get(REDSTONE_POWERED);
        if (bl && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, (BlockState)state.cycle(REDSTONE_POWERED), 2);
            bl = false;
        }
        if (state.isOf(this) && state.get(TurntableBlock.MECHANICAL_POWERED)) {
            RedBrainsSurvivalMod.LOGGER.info("can rotate");
            world.scheduleBlockTick(pos, BlocksRegistry.TURNTABLE.get(), getDelayForDelayState(state.get(TurntableBlock.DELAY)));
            BlockPos blockPos1 = pos.up();
            BlockState blockState1 = world.getBlockState(blockPos1);
            BlockPos blockPos1North = blockPos1.north();
            BlockState oldBlockState1North = world.getBlockState(blockPos1North);
            BlockState newBlockState1North;
            BlockPos blockPos1East = blockPos1.east();
            BlockState oldBlockState1East = world.getBlockState(blockPos1East);
            BlockState newBlockState1East;
            BlockPos blockPos1South = blockPos1.south();
            BlockState oldBlockState1South = world.getBlockState(blockPos1South);
            BlockState newBlockState1South;
            BlockPos blockPos1West = blockPos1.west();
            BlockState oldBlockState1West = world.getBlockState(blockPos1West);
            BlockState newBlockState1West;
            BlockPos blockPos2 = blockPos1.up();
            BlockState blockState2 = world.getBlockState(blockPos2);
            BlockPos blockPos2North = blockPos2.north();
            BlockState oldBlockState2North = world.getBlockState(blockPos2North);
            BlockState newBlockState2North;
            BlockPos blockPos2East = blockPos2.east();
            BlockState oldBlockState2East = world.getBlockState(blockPos2East);
            BlockState newBlockState2East;
            BlockPos blockPos2South = blockPos2.south();
            BlockState oldBlockState2South = world.getBlockState(blockPos2South);
            BlockState newBlockState2South;
            BlockPos blockPos2West = blockPos2.west();
            BlockState oldBlockState2West = world.getBlockState(blockPos2West);
            BlockState newBlockState2West;
            if (!blockState1.isIn(Tags.NOT_ROTATABLE_BY_TURNTABLE)) {
                RedBrainsSurvivalMod.LOGGER.info("rotate blockState1");
                world.setBlockState(blockPos1, world.getBlockState(blockPos1).rotate(bl ? BlockRotation.CLOCKWISE_90 : BlockRotation.COUNTERCLOCKWISE_90), Block.NOTIFY_ALL);
            }
            if (!blockState2.isIn(Tags.NOT_ROTATABLE_BY_TURNTABLE)) {
                world.setBlockState(blockPos2, world.getBlockState(blockPos2).rotate(bl ? BlockRotation.CLOCKWISE_90 : BlockRotation.COUNTERCLOCKWISE_90), Block.NOTIFY_ALL);
            }
            boolean oldBlockStateApproved;
            boolean newBlockStateApproved;
            BlockState newBlockState;
            // north 1
            oldBlockStateApproved = isBlockAttachedAndRotatable(oldBlockState1North, Direction.NORTH);
            newBlockState = bl ? oldBlockState1West : oldBlockState1East;
            newBlockStateApproved = isBlockAttachedAndRotatable(newBlockState, bl ? Direction.WEST : Direction.EAST);
            if (oldBlockStateApproved) {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can move block to north");
                    world.setBlockState(blockPos1North, newBlockState.rotate(bl ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90), Block.NOTIFY_ALL);
                    world.breakBlock(bl ? blockPos1West : blockPos1East, false);
                }
            } else {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can not move block to north");
                    ItemScatterer.spawn(world, blockPos1North.getX(), blockPos1North.getY(), blockPos1North.getZ(), newBlockState.getBlock().asItem().getDefaultStack());
                    world.breakBlock(bl ? blockPos1West : blockPos1East, false);
                }
            }
            // east 1
            oldBlockStateApproved = isBlockAttachedAndRotatable(oldBlockState1East, Direction.EAST);
            newBlockState = bl ? oldBlockState1North : oldBlockState1South;
            newBlockStateApproved = isBlockAttachedAndRotatable(newBlockState, bl ? Direction.NORTH : Direction.SOUTH);
            if (oldBlockStateApproved) {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can move block to east");
                    world.setBlockState(blockPos1East, newBlockState.rotate(bl ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90), Block.NOTIFY_ALL);
                    world.setBlockState(bl ? blockPos1North : blockPos1South, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            } else {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can not move block to east");
                    ItemScatterer.spawn(world, blockPos1East.getX(), blockPos1East.getY(), blockPos1East.getZ(), newBlockState.getBlock().asItem().getDefaultStack());
                    world.setBlockState(bl ? blockPos1North : blockPos1South, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
            // south 1
            oldBlockStateApproved = isBlockAttachedAndRotatable(oldBlockState1South, Direction.SOUTH);
            newBlockState = bl ? oldBlockState1East : oldBlockState1West;
            newBlockStateApproved = isBlockAttachedAndRotatable(newBlockState, bl ? Direction.EAST : Direction.WEST);
            if (oldBlockStateApproved) {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can move block to south");
                    world.setBlockState(blockPos1South, newBlockState.rotate(bl ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90), Block.NOTIFY_ALL);
                    world.setBlockState(bl ? blockPos1East : blockPos1West, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            } else {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can not move block to south");
                    ItemScatterer.spawn(world, blockPos1South.getX(), blockPos1South.getY(), blockPos1South.getZ(), newBlockState.getBlock().asItem().getDefaultStack());
                    world.setBlockState(bl ? blockPos1East : blockPos1West, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
            // west 1
            oldBlockStateApproved = isBlockAttachedAndRotatable(oldBlockState1West, Direction.WEST);
            newBlockState = bl ? oldBlockState1South : oldBlockState1North;
            newBlockStateApproved = isBlockAttachedAndRotatable(newBlockState, bl ? Direction.SOUTH : Direction.NORTH);
            if (oldBlockStateApproved) {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can move block to west");
                    world.setBlockState(blockPos1West, newBlockState.rotate(bl ? BlockRotation.COUNTERCLOCKWISE_90 : BlockRotation.CLOCKWISE_90), Block.NOTIFY_ALL);
                    world.setBlockState(bl ? blockPos1South : blockPos1North, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            } else {
                if (newBlockStateApproved) {
                    RedBrainsSurvivalMod.LOGGER.info("can not move block to west");
                    ItemScatterer.spawn(world, blockPos1West.getX(), blockPos1West.getY(), blockPos1West.getZ(), newBlockState.getBlock().asItem().getDefaultStack());
                    world.setBlockState(bl ? blockPos1South : blockPos1North, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

        if (direction == Direction.DOWN) {
            if (state.isOf(this) && neighborState.isOf(BlocksRegistry.AXLE.get()) && neighborState.get(AxleBlock.FACING) == Direction.DOWN) {

                boolean bl = neighborState.get(AxleBlock.POWER) >= 1;
                if (bl) {
                    world.scheduleBlockTick(pos, BlocksRegistry.TURNTABLE.get(), getDelayForDelayState(state.get(TurntableBlock.DELAY)));
                }
                return state.with(MECHANICAL_POWERED, bl);
            }
            return state.with(MECHANICAL_POWERED, false);
        }
        return state;
    }

    private boolean isBlockAttachedAndRotatable(BlockState blockState, Direction direction) {
        return ((blockState.getBlock() instanceof WallTorchBlock && blockState.get(WallTorchBlock.FACING) == direction)
                || (blockState.getBlock() instanceof LeverBlock && blockState.get(WallMountedBlock.FACE) == WallMountLocation.WALL && blockState.get(WallMountedBlock.FACING) == direction)
                || (blockState.getBlock() instanceof ButtonBlock && blockState.get(WallMountedBlock.FACE) == WallMountLocation.WALL && blockState.get(WallMountedBlock.FACING) == direction));
//        if ((blockState.getBlock() instanceof WallTorchBlock && blockState.get(WallTorchBlock.FACING) == direction)
//                || (blockState.getBlock() instanceof LeverBlock && blockState.get(WallMountedBlock.FACE) == WallMountLocation.WALL && blockState.get(WallMountedBlock.FACING) == direction)
//                || (blockState.getBlock() instanceof ButtonBlock && blockState.get(WallMountedBlock.FACE) == WallMountLocation.WALL && blockState.get(WallMountedBlock.FACING) == direction)/*
//                || (blockState.getPistonBehavior() == PistonBehavior.DESTROY)*/) {
//            return blockState;
//        } else {
//            return Blocks.AIR.getDefaultState();
//        }
    }

    static {
        DELAY = IntProperty.of("delay", 0, 3);
        MECHANICAL_POWERED = BooleanProperty.of("mechanical_powered");
        REDSTONE_POWERED = BooleanProperty.of("redstone_powered");
    }
}
