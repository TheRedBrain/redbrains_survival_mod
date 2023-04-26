package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum CustomToolMaterials implements ToolMaterial {
    HAND(MiningLevels.HAND, 1, 1.0f, 0.0f, 0, Ingredient::empty),
    WOOD(0, 10, 2.0F, 0.0F, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
    BONE(0, 10, 2.0F, 0.0F, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
    STONE(1, 50, 4.0F, 0.0F, 5, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(2, 500, 6.0F, 0.0F, 14, () -> Ingredient.ofItems(Items.IRON_INGOT)),
    DIAMOND(3, 1561, 8.0F, 0.0F, 10, () -> Ingredient.ofItems(ItemsRegistry.DIAMOND_INGOT.get())),
    GOLD(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.ofItems(Items.GOLD_INGOT)),
    NETHERITE(4, 2250, 9.0F, 0.0F, 15, () -> Ingredient.ofItems(Items.NETHERITE_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    CustomToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<>(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}