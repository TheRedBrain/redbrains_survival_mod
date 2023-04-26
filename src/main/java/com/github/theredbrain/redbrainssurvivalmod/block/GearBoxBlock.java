package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class GearBoxBlock extends FacingBlock {
    public static final IntProperty MECHANICAL_POWERED;

    public GearBoxBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(FACING, Direction.DOWN).with(MECHANICAL_POWERED, 0)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{FACING, MECHANICAL_POWERED});
    }

    protected BlockState calculatePowerLevel(BlockState state, Direction facing, WorldAccess world, BlockPos pos) {
        if (state.isOf(this) && state.get(FACING) == facing) {

            BlockState newState = state.with(MECHANICAL_POWERED, 0);
            BlockState neighborState = world.getBlockState(pos.offset(state.get(FACING)));

            if (neighborState.isOf(BlocksRegistry.AXLE.get()) && neighborState.get(FACING) == facing && neighborState.get(AxleBlock.POWER) >= 1) {

                newState = state.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.GENERATED_POWER));
            }

            world.scheduleBlockTick(pos, this, 100);
            return newState;
        }
        return state;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculatePowerLevel(this.getDefaultState().with(FACING, ctx.getSide().getOpposite()), ctx.getSide().getOpposite(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

        return calculatePowerLevel(state, direction, world, pos);
    }

    // FIXME cycles only through 3 states
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && player.isSneaking() && player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
            BlockState newState = state.cycle(FACING);

            world.setBlockState(pos, calculatePowerLevel(newState, newState.get(FACING), world, pos));

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.isOf(this) && state.get(MECHANICAL_POWERED) == 4) {
            world.breakBlock(pos, true);
        }
    }

    static {
        MECHANICAL_POWERED = IntProperty.of("mechanical_power", 0, 2);
    }
}
