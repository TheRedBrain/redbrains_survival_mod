package com.github.theredbrain.redbrainssurvivalmod.recipe;

import com.github.theredbrain.redbrainssurvivalmod.registry.RecipeTypesRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class CookingRecipe extends CauldronRecipe {

    public CookingRecipe(Identifier id, String group, DefaultedList<Ingredient> ingredientList, ItemStack output, DefaultedList<Ingredient> additionalOutput, int cookTime, int priority) {
        super(id, group, ingredientList, output, additionalOutput, cookTime, priority, RecipeTypesRegistry.COOKING_RECIPE_SERIALIZER.serializer(), RecipeTypesRegistry.COOKING_RECIPE_SERIALIZER.type());
    }
}