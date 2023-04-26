package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CustomToolItem extends Item {
    protected final int enchantability;
    protected final TagKey<Block> effectiveBlocks;
    protected final TagKey<Block> mineableBlocks;
    protected final TagKey<Block> noMiningDamageBlocks;
    protected final float miningSpeed;
    protected final float attackDamage;
    protected final boolean isTool;
    protected final boolean isWeapon;
    protected final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public CustomToolItem(boolean isTool, boolean isWeapon, int enchantability, float attackDamage, float attackSpeed, float miningSpeed, @Nullable TagKey<Block> effectiveBlocks, @Nullable TagKey<Block> mineableBlocks, @Nullable TagKey<Block> noMiningDamageBlocks, Settings settings) {
        super(settings);
        this.enchantability = enchantability;
        this.effectiveBlocks = effectiveBlocks;
        this.mineableBlocks = mineableBlocks;
        this.noMiningDamageBlocks = noMiningDamageBlocks;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.isTool = isTool;
        this.isWeapon = isWeapon;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return (this.effectiveBlocks != null && state.isIn(this.effectiveBlocks)) ? this.miningSpeed : 1.0f;
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
        stack.damage(isWeapon ? 1 : 2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && !(this.noMiningDamageBlocks != null && state.isIn(this.noMiningDamageBlocks))) {
            stack.damage(isTool ? 1 : 2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
//        int i = this.getMaterial().getMiningLevel();
//        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
//            return false;
//        }
//        if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
//            return false;
//        }
//        if (i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
//            return false;
//        }
        return state.isIn(this.effectiveBlocks);
    }
}
