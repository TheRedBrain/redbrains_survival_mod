package com.github.theredbrain.redbrainssurvivalmod.recipe;

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
// TODO
public class CauldronRecipe implements Recipe<Inventory> {

    //TODO
    // multiple or secondary output items

    public static final int INPUT_SLOTS = 27;

    protected final Identifier id;
    protected final String group;
    protected final DefaultedList<Ingredient> ingredientList;
    protected final ItemStack output;
    protected final DefaultedList<Ingredient> additionalOutput;
    protected final int cookTime;
    protected final int priority;
    protected final RecipeSerializer<?> recipeSerializer;
    protected final RecipeType<?> recipeType;

    public CauldronRecipe(Identifier id, String group, DefaultedList<Ingredient> ingredientList, ItemStack output, DefaultedList<Ingredient> additionalOutput, int cookTime, int priority, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType) {
        this.id = id;
        this.group = group;
        this.ingredientList = ingredientList;
        this.output = output;
        this.additionalOutput = additionalOutput;
        this.cookTime = cookTime;
        this.priority = priority;
        this.recipeSerializer = recipeSerializer;
        this.recipeType = recipeType;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        DefaultedList<Ingredient> ingredients = ingredientList;

        for (Ingredient ingredient : ingredients) {

            for (int i = 0; i < inv.size(); i++) {
                if (ingredient.test(inv.getStack(i))) {
                    if (inv.getStack(i).getCount() > 1) {
                        inv.getStack(i).setCount(inv.getStack(i).getCount() - 1);
                        ingredient = Ingredient.empty();
                    } else if (inv.getStack(i).getCount() == 1) {
                        inv.setStack(i, ItemStack.EMPTY);
                        ingredient = Ingredient.empty();
                    }
                }
            }
            if (ingredient != null) {
                if(!ingredient.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inv, DynamicRegistryManager registryManager) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= ingredientList.size();
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipeSerializer;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredientList;
    }

    @Override
    public String getGroup() {
        return group;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getPriority() {
        return priority;
    }

    public DefaultedList<Ingredient> getAdditionalOutput() {
        return additionalOutput;
    }
}