package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class UncookedNetherBrickBlock extends AbstractBrickBlock {
    public UncookedNetherBrickBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        this.tryBreakBrick(world, state, pos, entity);
        super.onSteppedOn(world, pos, state, entity);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {

        this.tryBreakBrick(world, state, pos, entity);
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    private void tryBreakBrick(World world, BlockState state, BlockPos pos, Entity entity) {
        if (this.breaksBrick(world, entity)) {
            if (!world.isClient && state.isOf(BlocksRegistry.UNCOOKED_NETHER_BRICK.get())) {
                this.breakBrick(world, pos, state);
            }

        }
    }

    private void breakBrick(World world, BlockPos pos, BlockState state) {

        world.removeBlock(pos, false);
        world.emitGameEvent(null, GameEvent.BLOCK_DESTROY, pos);
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemsRegistry.NETHER_SLUDGE.get()));
        world.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F); // TODO find better sound

    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return blockState.isOf(this) ? (BlockState)blockState.with(FACING, blockState.get(FACING)) : super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (Direction)state.get(FACING) == Direction.NORTH || (Direction)state.get(FACING) == Direction.SOUTH ? SHAPE_1_SN : SHAPE_1_EW;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!(itemStack.isOf(this.asItem()))) {

            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            if (!player.isCreative()) {

                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
                world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F); // TODO find better sound
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {

        super.afterBreak(world, player, pos, state, blockEntity, stack);
        this.breakBrick(world, pos, state);
    }

    private boolean breaksBrick(World world, Entity entity) {

        return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
    }
}
