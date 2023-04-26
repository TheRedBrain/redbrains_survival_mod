package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.VaseBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class VaseBlock extends BlockWithEntity {

    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final VoxelShape SHAPE_COMPLETE;

    public VaseBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_COMPLETE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VaseBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        VaseBlockEntity vaseBlockEntity;
        BlockEntity blockEntity;

        if (!world.isClient && (blockEntity = world.getBlockEntity(pos)) instanceof VaseBlockEntity/* && !(vaseBlockEntity = (VaseBlockEntity)blockEntity).shouldDropNothing()*/) {
            ItemStack itemStack;
            if ((itemStack = ((VaseBlockEntity)blockEntity).getStack(0)).getItem() == ItemsRegistry.BLASTING_OIL.get()) {
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 1.0f, World.ExplosionSourceType.NONE);
            } else {
                ItemScatterer.spawn(world, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), ((VaseBlockEntity) blockEntity).getStack(0));
                world.playSound(null, pos, SoundEvents.BLOCK_DECORATED_POT_BREAK, SoundCategory.PLAYERS, 3.0f, 1.0f);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof VaseBlockEntity) {
            ItemStack itemStack = ((VaseBlockEntity) blockEntity).getStack(0);
            if (itemStack == ItemStack.EMPTY) {
                ((VaseBlockEntity) blockEntity).setStack(0, player.getStackInHand(hand).split(1));
                player.setStackInHand(hand, ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    static {
        SHAPE_COMPLETE = VoxelShapes.union(
                Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
                Block.createCuboidShape(3.0, 1.0, 3.0, 13.0, 7.0, 13.0),
                Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 15.0, 10.0),
                Block.createCuboidShape(5.0, 15.0, 5.0, 11.0, 16.0, 11.0));
    }
}
