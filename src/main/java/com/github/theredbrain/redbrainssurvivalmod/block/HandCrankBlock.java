package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HandCrankBlock extends Block {
    public static final DirectionProperty FACING;
    public static final BooleanProperty POWERED;
    protected static final VoxelShape SHAPE_FULL;

    public HandCrankBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH).with(POWERED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_FULL;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(POWERED, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(POWERED)) {
            return ActionResult.CONSUME;
        } else if (player.getHungerManager().getFoodLevel() > 4) {
            Direction[] directions = {Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
            int k = 0;
            for (int i = 0; i<=3; i++) {
                if (world.getBlockState(pos.offset(directions[i])).isIn(Tags.POWERABLE_BY_HAND_CRANK)) {
                    k++;
                }
            }
            if (k > 1) {
                world.breakBlock(pos, true);
            } else {
                world.setBlockState(pos, state.with(POWERED, true), 3);
                world.scheduleBlockTick(pos, this, 100);
                world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
            }
            player.getHungerManager().addExhaustion(0.5F);
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.FAIL;
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(POWERED)) {
            world.setBlockState(pos, (BlockState)state.with(POWERED, false), 3);
            world.emitGameEvent((Entity)null, GameEvent.BLOCK_DEACTIVATE, pos);
        }
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
        POWERED = BooleanProperty.of("powered");
        SHAPE_FULL = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
    }
}
