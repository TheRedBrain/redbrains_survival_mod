package com.github.theredbrain.redbrainssurvivalmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChiselItem extends Item {
    private final TagKey<Block> effectiveBlocks;
    private final TagKey<Block> mineableBlocks;
    protected final float miningSpeed;

    public ChiselItem(float miningSpeed, TagKey<Block> effectiveBlocks, TagKey<Block> mineableBlocks, Settings settings) {
        super(settings);
        this.effectiveBlocks = effectiveBlocks;
        this.mineableBlocks = mineableBlocks;
        this.miningSpeed = miningSpeed;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.isIn(this.mineableBlocks);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(this.effectiveBlocks);
    }
}
