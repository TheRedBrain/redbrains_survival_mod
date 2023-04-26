package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomAxeItem extends CustomToolItem {
    protected static Map<Item, List<Item>> SPLITTING_ITEMS_STICKS = new ImmutableMap.Builder<Item, List<Item>>()
            .put(BlocksRegistry.OAK_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.OAK_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.BIRCH_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.BIRCH_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.SPRUCE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SPRUCE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.JUNGLE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.JUNGLE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.ACACIA_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.ACACIA_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.DARK_OAK_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.DARK_OAK_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.MANGROVE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.MANGROVE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_OAK_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_BIRCH_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_SPRUCE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_JUNGLE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_ACACIA_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_DARK_OAK_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_MANGROVE_LOG.get().asItem(), ImmutableList.of(Items.STICK, Items.STICK, ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .build();
    protected static Map<Item, List<Item>> SPLITTING_ITEMS_PLANKS = new ImmutableMap.Builder<Item, List<Item>>()
            .put(BlocksRegistry.OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), ItemsRegistry.OAK_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.BIRCH_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), ItemsRegistry.BIRCH_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.SPRUCE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), ItemsRegistry.SPRUCE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.JUNGLE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), ItemsRegistry.JUNGLE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.ACACIA_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), ItemsRegistry.ACACIA_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.DARK_OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), ItemsRegistry.DARK_OAK_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.MANGROVE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), ItemsRegistry.MANGROVE_BARK.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_BIRCH_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_SPRUCE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_JUNGLE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_ACACIA_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_DARK_OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .put(BlocksRegistry.STRIPPED_MANGROVE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get()))
            .build();
    private final boolean craftingDamage;
    private final boolean splittingIntoPlanks;

    public CustomAxeItem(boolean craftingDamage, boolean splittingIntoPlanks, boolean isTool, boolean isWeapon, int enchantability, float attackDamage, float attackSpeed, float miningSpeed, TagKey<Block> effectiveBlocks, TagKey<Block> mineableBlocks, @Nullable TagKey<Block> noMiningDamageBlocks, Settings settings) {
        super(isTool, isWeapon, enchantability, attackDamage, attackSpeed, miningSpeed, effectiveBlocks, mineableBlocks, noMiningDamageBlocks, settings);
        this.craftingDamage = craftingDamage;
        this.splittingIntoPlanks = splittingIntoPlanks;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }
        ItemStack itemStack = slot.getStack();
        if (itemStack.isIn(Tags.AXE_SPLITTING_ITEMS)) {
            Optional<List<Item>> optional = this.getSplittingItems(itemStack);
            if (optional.isPresent()) {
                itemStack.decrement(1);
                for (Item item : optional.get()) {
                    player.getInventory().offer(item.getDefaultStack(), true);
                }
                player.playSound(SoundEvents.ITEM_AXE_STRIP, 1.0f, 1.0f); // TODO better sound
                if (this.craftingDamage && player instanceof ServerPlayerEntity) {
                    stack.damage(1, player.getRandom(), (ServerPlayerEntity) player);
                    if (stack.getDamage() >= stack.getMaxDamage()) {
                        stack.decrement(1);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Optional<List<Item>> getSplittingItems(ItemStack itemStack) {
        return this.splittingIntoPlanks ? Optional.ofNullable(SPLITTING_ITEMS_PLANKS.get(itemStack.getItem())) : Optional.ofNullable(SPLITTING_ITEMS_STICKS.get(itemStack.getItem()));
    }
}
