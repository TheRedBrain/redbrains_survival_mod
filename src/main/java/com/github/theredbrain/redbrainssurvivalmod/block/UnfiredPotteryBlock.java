package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class UnfiredPotteryBlock extends Block {
    public static final IntProperty STAGE;
    public static VoxelShape SHAPE_COMPLETE = VoxelShapes.fullCube();
    protected final Block nextBlock;
    protected final boolean dropClayBall;

    public UnfiredPotteryBlock(Block nextBlock, boolean dropClayBall, Settings settings) {
        super(settings);
        this.nextBlock = nextBlock;
        this.dropClayBall = dropClayBall;
        this.setDefaultState((BlockState)(this.stateManager.getDefaultState().with(STAGE, 0)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos, Direction.UP);
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if (!world.isClient) {
            world.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.3f, 0.5f);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
            if (state.isOf(this) && newState.isOf(this.nextBlock) && this.dropClayBall) {
                ItemScatterer.spawn(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), Items.CLAY_BALL.getDefaultStack());
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_COMPLETE;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        int i = state.get(UnfiredPotteryBlock.STAGE);
        if (state.isOf(this) && i < 7) {
            return state.with(UnfiredPotteryBlock.STAGE, i + 1);
        } else {
            return nextBlock.getDefaultState();
        }
    }

    static {
        STAGE = IntProperty.of("stage", 0, 7);
    }
}
