package com.github.theredbrain.redbrainssurvivalmod.block.subblocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class PillarCapBlock extends Block implements Waterloggable {
    public static DirectionProperty FACING;
    public static BooleanProperty WATERLOGGED;
    private final Block baseBlock;
    private final BlockState baseBlockState;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape EAST_SHAPE;

    public PillarCapBlock(BlockState baseBlockState, Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.SOUTH)).with(WATERLOGGED, false));
        this.baseBlock = baseBlockState.getBlock();
        this.baseBlockState = baseBlockState;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, WATERLOGGED});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACING) == Direction.DOWN) {
            return BOTTOM_SHAPE;
        } else if (state.get(FACING) == Direction.UP) {
            return TOP_SHAPE;
        } else if (state.get(FACING) == Direction.SOUTH) {
            return SOUTH_SHAPE;
        } else if (state.get(FACING) == Direction.WEST) {
            return WEST_SHAPE;
        } else if (state.get(FACING) == Direction.NORTH) {
            return NORTH_SHAPE;
        } else {
            return EAST_SHAPE;
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        BlockState blockState2 = (BlockState)((BlockState)this.getDefaultState().with(FACING, Direction.UP)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        Direction direction = ctx.getSide();
        return direction == Direction.UP ? blockState2 : direction == Direction.EAST ? blockState2.with(FACING, Direction.EAST) : direction == Direction.WEST ? blockState2.with(FACING, Direction.WEST) : direction == Direction.SOUTH ? blockState2.with(FACING, Direction.SOUTH) : direction == Direction.NORTH ? blockState2.with(FACING, Direction.NORTH) : blockState2.with(FACING, Direction.DOWN);
    }

    //-----------------------waterlogging stuff-----------------------

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    //-----------------------inheriting base block stats-----------------------

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.baseBlock.randomDisplayTick(state, world, pos, random);
    }

    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        this.baseBlockState.onBlockBreakStart(world, pos, player);
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        this.baseBlock.onBroken(world, pos, state);
    }

    public float getBlastResistance() {
        return this.baseBlock.getBlastResistance();
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(state.getBlock())) {
            world.updateNeighbor(this.baseBlockState, pos, Blocks.AIR, pos, false);
            this.baseBlock.onBlockAdded(this.baseBlockState, world, pos, oldState, false);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            this.baseBlockState.onStateReplaced(world, pos, newState, moved);
        }
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        this.baseBlock.onSteppedOn(world, pos, state, entity);
    }

    public boolean hasRandomTicks(BlockState state) {
        return this.baseBlock.hasRandomTicks(state);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.randomTick(state, world, pos, random);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.scheduledTick(state, world, pos, random);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return this.baseBlockState.onUse(world, player, hand, hit);
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        this.baseBlock.onDestroyedByExplosion(world, pos, explosion);
    }

    static {
        FACING = Properties.FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
                Block.createCuboidShape(1.0D, 2.0D, 1.0D, 15.0D, 4.0D, 15.0D),
                Block.createCuboidShape(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D));
        TOP_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.createCuboidShape(1.0D, 12.0D, 1.0D, 15.0D, 14.0D, 15.0D),
                Block.createCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D));
        SOUTH_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D),
                Block.createCuboidShape(1.0D, 1.0D, 12.0D, 15.0D, 15.0D, 14.0D),
                Block.createCuboidShape(2.0D, 2.0D, 14.0D, 14.0D, 14.0D, 16.0D));
        WEST_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(0.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D),
                Block.createCuboidShape(2.0D, 1.0D, 1.0D, 4.0D, 15.0D, 15.0D),
                Block.createCuboidShape(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
        NORTH_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 2.0D),
                Block.createCuboidShape(1.0D, 1.0D, 2.0D, 15.0D, 15.0D, 4.0D),
                Block.createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D));
        EAST_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D),
                Block.createCuboidShape(12.0D, 1.0D, 1.0D, 14.0D, 15.0D, 15.0D),
                Block.createCuboidShape(14.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
    }
}
