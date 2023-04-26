package com.github.theredbrain.redbrainssurvivalmod.recipe;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.RecipeTypesRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class GrindingRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final String group;
    private final Ingredient input;
    private final int grindingTime;
    private final DefaultedList<ItemStack> resultList;
    private final String soundEvent;

    public GrindingRecipe(Identifier id, String group, Ingredient input, int grindingTime, DefaultedList<ItemStack> resultList, String soundEvent) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.grindingTime = grindingTime;
        this.resultList = resultList;
        this.soundEvent = soundEvent;
    }

    public ItemStack createIcon() {
        return new ItemStack(BlocksRegistry.MILLSTONE.get());
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        if (inv.isEmpty()) {
            return false;
        } else {
            return input.test(inv.getStack(0));
        }
    }

    @Override
    public ItemStack craft(Inventory inv, DynamicRegistryManager registryManager) {
        return getOutput(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return resultList.get(0);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.GRINDING_RECIPE_SERIALIZER.serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypesRegistry.GRINDING_RECIPE_SERIALIZER.type();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> ingredient = DefaultedList.of();
        ingredient.add(input);

        return ingredient;
    }

    public DefaultedList<ItemStack> getResultList() {
        return resultList;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public int getGrindingTime() {
        return grindingTime;
    }

    public String getSoundEvent() {
        return soundEvent;
    }
}
