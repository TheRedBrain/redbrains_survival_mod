package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomShearsItem extends Item {

    protected static Map<Item, List<Item>> SHEARING_ITEMS = new ImmutableMap.Builder<Item, List<Item>>()
            .put(Items.LEATHER, ImmutableList.of(ItemsRegistry.LEATHER_CUT.get(), ItemsRegistry.LEATHER_CUT.get()))
            .put(ItemsRegistry.LEATHER_SCOURED.get(), ImmutableList.of(ItemsRegistry.LEATHER_SCOURED_CUT.get(), ItemsRegistry.LEATHER_SCOURED_CUT.get()))
            .put(ItemsRegistry.LEATHER_TANNED.get(), ImmutableList.of(ItemsRegistry.LEATHER_TANNED_CUT.get(), ItemsRegistry.LEATHER_TANNED_CUT.get()))
            .put(ItemsRegistry.LEATHER_TANNED_CUT.get(), ImmutableList.of(ItemsRegistry.STRAP.get(), ItemsRegistry.STRAP.get())).build();

    private final boolean craftingDamage;
    protected final TagKey<Block> effectiveBlocks;
    protected final TagKey<Block> mineableBlocks;
    public CustomShearsItem(boolean craftingDamage, @Nullable TagKey<Block> effectiveBlocks, @Nullable TagKey<Block> mineableBlocks, Settings settings) {
        super(settings);
        this.craftingDamage = craftingDamage;
        this.effectiveBlocks = effectiveBlocks;
        this.mineableBlocks = mineableBlocks;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB) || state.isIn(BlockTags.LEAVES)) {
            return 15.0f;
        }
        if (state.isIn(BlockTags.WOOL)) {
            return 5.0f;
        }
        if (state.isOf(Blocks.VINE) || state.isOf(Blocks.GLOW_LICHEN)) {
            return 2.0f;
        }
        return super.getMiningSpeedMultiplier(stack, state);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.isIn(this.mineableBlocks);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }
        ItemStack itemStack = slot.getStack();
        Optional<List<Item>> optional = this.getShearingItems(itemStack);
        if (optional.isPresent()) {
            itemStack.decrement(1);
            for (Item item : optional.get()) {
                player.getInventory().offer(item.getDefaultStack(), true);
            }
            player.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0f, 1.0f); // TODO better sound
            if (this.craftingDamage && player instanceof ServerPlayerEntity) {
                stack.damage(1, player.getRandom(), (ServerPlayerEntity) player);
                if (stack.getDamage() >= stack.getMaxDamage()) {
                    stack.decrement(1);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        AbstractPlantStemBlock abstractPlantStemBlock;
        BlockPos blockPos;
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
        Block block = blockState.getBlock();
        if (block instanceof AbstractPlantStemBlock && !(abstractPlantStemBlock = (AbstractPlantStemBlock)block).hasMaxAge(blockState)) {
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }
            world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            BlockState blockState2 = abstractPlantStemBlock.withMaxAge(blockState);
            world.setBlockState(blockPos, blockState2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(context.getPlayer(), blockState2));
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, player -> player.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(this.effectiveBlocks);
    }

    private Optional<List<Item>> getShearingItems(ItemStack itemStack) {
        return Optional.ofNullable(SHEARING_ITEMS.get(itemStack.getItem()));
    }
}
