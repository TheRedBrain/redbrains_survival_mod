package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class CustomCakeBlock extends CakeBlock {
    public CustomCakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Block block;
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (itemStack.isIn(ItemTags.CANDLES) && state.get(BITES) == 0 && (block = Block.getBlockFromItem(item)) instanceof CandleBlock) {
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(pos, CandleCakeBlock.getCandleCakeFromCandle(block));
            world.emitGameEvent((Entity)player, GameEvent.BLOCK_CHANGE, pos);
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.SUCCESS;
        }
        if (world.isClient) {
            if (CakeBlock.tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return CakeBlock.tryEat(world, pos, state, player);
    }

    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(true)) {
            return ActionResult.PASS;
        }
        player.incrementStat(Stats.EAT_CAKE_SLICE);
        player.getHungerManager().add(2, 8f);
        int i = state.get(BITES);
        world.emitGameEvent((Entity)player, GameEvent.EAT, pos);
        if (i < 6) {
            world.setBlockState(pos, (BlockState)state.with(BITES, i + 1), Block.NOTIFY_ALL);
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent((Entity)player, GameEvent.BLOCK_DESTROY, pos);
        }
        return ActionResult.SUCCESS;
    }
}
