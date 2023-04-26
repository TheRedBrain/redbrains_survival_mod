package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CraftingChiselItem extends Item {
    protected static final Map<Item, List<Item>> CHISELED_ITEMS = new ImmutableMap.Builder<Item, List<Item>>()
            .put(BlocksRegistry.STONE.get().asItem(), ImmutableList.of(ItemsRegistry.STONE_BRICK.get(), ItemsRegistry.STONE_BRICK.get(), ItemsRegistry.STONE_BRICK.get(), ItemsRegistry.STONE_BRICK.get()))
            .put(ItemsRegistry.STONE_BRICK.get(), ImmutableList.of(ItemsRegistry.LOOSE_STONE.get(), ItemsRegistry.LOOSE_STONE.get()))
            .put(BlocksRegistry.ACACIA_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_ACACIA_LOG.get().asItem(), ItemsRegistry.ACACIA_BARK.get()))
            .put(BlocksRegistry.ACACIA_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_ACACIA_WOOD.get().asItem(), ItemsRegistry.ACACIA_BARK.get()))
            .put(BlocksRegistry.BIRCH_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_BIRCH_LOG.get().asItem(), ItemsRegistry.BIRCH_BARK.get()))
            .put(BlocksRegistry.BIRCH_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_BIRCH_WOOD.get().asItem(), ItemsRegistry.BIRCH_BARK.get()))
//            .put(BlocksRegistry.CRIMSON_STEM.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_CRIMSON_STEM.get().asItem(), ItemsRegistry.CRIMSON_BARK.get()))
//            .put(BlocksRegistry.CRIMSON_HYPHAE.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_CRIMSON_HYPHAE.get().asItem(), ItemsRegistry.CRIMSON_BARK.get()))
            .put(BlocksRegistry.DARK_OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_DARK_OAK_LOG.get().asItem(), ItemsRegistry.DARK_OAK_BARK.get()))
            .put(BlocksRegistry.DARK_OAK_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_DARK_OAK_WOOD.get().asItem(), ItemsRegistry.DARK_OAK_BARK.get()))
            .put(BlocksRegistry.JUNGLE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_JUNGLE_LOG.get().asItem(), ItemsRegistry.JUNGLE_BARK.get()))
            .put(BlocksRegistry.JUNGLE_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_JUNGLE_WOOD.get().asItem(), ItemsRegistry.JUNGLE_BARK.get()))
            .put(BlocksRegistry.MANGROVE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_MANGROVE_LOG.get().asItem(), ItemsRegistry.MANGROVE_BARK.get()))
            .put(BlocksRegistry.MANGROVE_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_MANGROVE_WOOD.get().asItem(), ItemsRegistry.MANGROVE_BARK.get()))
            .put(BlocksRegistry.OAK_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_OAK_LOG.get().asItem(), ItemsRegistry.OAK_BARK.get()))
            .put(BlocksRegistry.OAK_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_OAK_WOOD.get().asItem(), ItemsRegistry.OAK_BARK.get()))
            .put(BlocksRegistry.SPRUCE_LOG.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_SPRUCE_LOG.get().asItem(), ItemsRegistry.SPRUCE_BARK.get()))
            .put(BlocksRegistry.SPRUCE_WOOD.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_SPRUCE_WOOD.get().asItem(), ItemsRegistry.SPRUCE_BARK.get()))
//            .put(BlocksRegistry.WARPED_STEM.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_WARPED_STEM.get().asItem(), ItemsRegistry.WARPED_BARK.get()))
//            .put(BlocksRegistry.WARPED_HYPHAE.get().asItem(), ImmutableList.of(BlocksRegistry.STRIPPED_WARPED_HYPHAE.get().asItem(), ItemsRegistry.WARPED_BARK.get()))
            .build();

    private final TagKey<Block> effectiveBlocks;
    private final TagKey<Block> mineableBlocks;
    protected final float miningSpeed;
    private final boolean craftingDamage;

    public CraftingChiselItem(boolean craftingDamage, float miningSpeed, TagKey<Block> effectiveBlocks, TagKey<Block> mineableBlocks, Settings settings) {
        super(settings);
        this.effectiveBlocks = effectiveBlocks;
        this.mineableBlocks = mineableBlocks;
        this.miningSpeed = miningSpeed;
        this.craftingDamage = craftingDamage;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.isIn(this.mineableBlocks);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState1 = world.getBlockState(blockPos);

        BlockState blockState2 = null;
        if ((this.effectiveBlocks != null && blockState1.isIn(this.effectiveBlocks)) && playerEntity != null && playerEntity.isSneaking() && blockState1.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            blockState2 = BlocksRegistry.PLACED_TOOL.get().getPlacementState(new ItemPlacementContext(context));
        }

        if (blockState2 != null) {
            if (!world.isClient) {
                world.setBlockState(blockPos.up(), blockState2, 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));

                PlacedToolEntity blockEntity = (PlacedToolEntity) world.getBlockEntity(blockPos.up());
                if (blockEntity != null) {
                    blockEntity.setStack(0, context.getStack().split(1));
                    playerEntity.getStackInHand(context.getHand()).split(1);
                }

            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean bl = false;
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            if (bl) {
                stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            } else {
                stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }
        ItemStack itemStack = slot.getStack();
        if (itemStack.isIn(Tags.CHISEL_SPLITTING_ITEMS)) {
            Optional<List<Item>> optional = this.getChiseledItems(itemStack);
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

    private Optional<List<Item>> getChiseledItems(ItemStack itemStack) {
        return Optional.ofNullable(CHISELED_ITEMS.get(itemStack.getItem()));
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(this.effectiveBlocks);
    }
}
