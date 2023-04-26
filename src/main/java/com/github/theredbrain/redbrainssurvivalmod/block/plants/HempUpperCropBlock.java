package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import com.github.theredbrain.redbrainssurvivalmod.item.CustomShearsItem;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class HempUpperCropBlock extends Block {

    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    public HempUpperCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!(tool.getItem() instanceof CustomShearsItem)) {
            world.scheduleBlockTick(pos.down(), BlocksRegistry.HEMP_CROP.get(), 1);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return (blockState.isOf(BlocksRegistry.HEMP_CROP.get()) && blockState.get(HempCropBlock.AGE) == 9);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }
        entity.slowMovement(state, Constants.PLANTS_VELOCITY_MULTIPLYER);

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ItemsRegistry.HEMP_SEEDS.get());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type == NavigationType.AIR && !this.collidable) {
            return true;
        }
        return super.canPathfindThrough(state, world, pos, type);
    }
}
