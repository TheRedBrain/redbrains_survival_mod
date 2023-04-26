package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class CustomDirtBlock extends Block {

    public CustomDirtBlock(Settings settings) {
        super(settings);
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        CustomDirtBlock.loosenNeighboringDirt(world, pos);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (tool.isIn(ItemTags.HOES)) {
            world.setBlockState(pos, BlocksRegistry.LOOSE_DIRT.get().getDefaultState(), NOTIFY_ALL);
        } else if (!tool.isIn(Tags.PROPER_DIGGING_TOOLS)) {
            CustomDirtBlock.loosenNeighboringDirt(world, pos);
        }
    }

    public static void loosenNeighboringDirt(World world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 1, 1)).iterator();

        do {
            if (!var2.hasNext()) {
                break;
            }

            BlockPos blockPos = (BlockPos)var2.next();
            if (world.getBlockState(blockPos).isOf(BlocksRegistry.DIRT.get())) {
                world.setBlockState(blockPos, BlocksRegistry.LOOSE_DIRT.get().getDefaultState());
            } else if (world.getBlockState(blockPos).isOf(BlocksRegistry.DIRT_SLAB.get())) {
                world.setBlockState(blockPos, BlocksRegistry.LOOSE_DIRT_SLAB.get().getDefaultState());
            }
        } while(var2.hasNext());
    }
}
