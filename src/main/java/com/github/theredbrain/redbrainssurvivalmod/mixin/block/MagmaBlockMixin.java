package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MagmaBlock.class)
public abstract class MagmaBlockMixin extends Block {

    public MagmaBlockMixin(Settings settings) {
        super(settings);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            Material material = world.getBlockState(pos.down()).getMaterial();
            if (material.blocksMovement() || material.isLiquid()) {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
            }
        }
    }

}
