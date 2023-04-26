package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LooseFallingBlock extends FallingBlock {
    public final Block mortaredVariant;

    public LooseFallingBlock(Block mortaredVariant, Settings settings) {
        super(settings);
        this.mortaredVariant = mortaredVariant;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (isSupported(state, world, pos)) {
            world.scheduleBlockTick(pos,this,this.getSupportedFallDelay());
        } else {
            world.scheduleBlockTick(pos, this, this.getFallDelay());
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (isSupported(state, (World) world, pos)) {
            world.scheduleBlockTick(pos,this,this.getSupportedFallDelay());
        } else {
            world.scheduleBlockTick(pos, this, this.getFallDelay());
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    protected int getSupportedFallDelay() {
        return 60;
    }

    protected boolean isSupported(BlockState state, World world, BlockPos pos) {
        return world.getBlockState(pos.up()).isOf(this.mortaredVariant) || world.getBlockState(pos.south()).isOf(this.mortaredVariant) || world.getBlockState(pos.west()).isOf(this.mortaredVariant) || world.getBlockState(pos.north()).isOf(this.mortaredVariant) || world.getBlockState(pos.east()).isOf(this.mortaredVariant);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isIn(Tags.MORTAR_ITEMS)) {
            world.setBlockState(pos, this.mortaredVariant.getDefaultState(), Block.NOTIFY_ALL);
            itemStack.decrement(1);
            player.setStackInHand(hand, itemStack);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
}
