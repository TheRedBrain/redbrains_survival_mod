package com.github.theredbrain.redbrainssurvivalmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class CrucibleSmeltingRecipeSerializer implements RecipeSerializer<CrucibleSmeltingRecipe> {
    private static DefaultedList<Ingredient> readIngredients(JsonArray ingredientArray) {
        DefaultedList<Ingredient> ingredientList = DefaultedList.of();

        for (int i = 0; i < ingredientArray.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
            ingredientList.add(ingredient);
        }

        return ingredientList;
    }

    private static DefaultedList<Ingredient> readAdditionalOutputs(JsonArray additionalOutputArray) {
        DefaultedList<Ingredient> additionalOutputList = DefaultedList.of();

        for (int i = 0; i < additionalOutputArray.size(); ++i) {
            Ingredient additionalOutput = Ingredient.fromJson(additionalOutputArray.get(i));
            additionalOutputList.add(additionalOutput);
        }

        return additionalOutputList;
    }

    @Override
    public CrucibleSmeltingRecipe read(Identifier id, JsonObject json) {
        final String groupIn = JsonHelper.getString(json, "group", "");
        DefaultedList<Ingredient> inputItems = readIngredients(JsonHelper.getArray(json, "ingredients"));
        if (inputItems.size() > CookingRecipe.INPUT_SLOTS) {
            throw new JsonParseException("Too many ingredients for cooking recipe! The max is " + CookingRecipe.INPUT_SLOTS);
        } else {
            final JsonObject jsonResult = JsonHelper.getObject(json, "result");
            final ItemStack outputIn = new ItemStack(JsonHelper.getItem(jsonResult, "item"), JsonHelper.getInt(jsonResult, "count", 1));
            DefaultedList<Ingredient> additionalOutputs = readAdditionalOutputs(JsonHelper.getArray(json, "additionaloutputs"));
            final int cookTimeIn = JsonHelper.getInt(json, "cookingtime", 200);
            final int priority = JsonHelper.getInt(json, "priority", 2);

            return new CrucibleSmeltingRecipe(id, groupIn, inputItems, outputIn, additionalOutputs, cookTimeIn, priority);
        }
    }

    @Override
    public CrucibleSmeltingRecipe read(Identifier id, PacketByteBuf buf) {
        String groupIn = buf.readString(32767);

        int ingredientSize = buf.readVarInt();
        DefaultedList<Ingredient> ingredientList = DefaultedList.ofSize(ingredientSize, Ingredient.EMPTY);
        for (int j = 0; j < ingredientList.size(); ++j) {
            ingredientList.set(j, Ingredient.fromPacket(buf));
        }

        ItemStack outputIn = buf.readItemStack();
        int additionalOutputSize = buf.readVarInt();
        DefaultedList<Ingredient> additionalOutputIn = DefaultedList.ofSize(additionalOutputSize, Ingredient.EMPTY);
        for (int j = 0; j < additionalOutputIn.size(); ++j) {
            additionalOutputIn.set(j, Ingredient.fromPacket(buf));
        }

        int cookTimeIn = buf.readVarInt();
        int priority = buf.readVarInt();

        return new CrucibleSmeltingRecipe(id, groupIn, ingredientList, outputIn, additionalOutputIn, cookTimeIn, priority);
    }

    @Override
    public void write(PacketByteBuf buf, CrucibleSmeltingRecipe recipe) {
        buf.writeString(recipe.getGroup());
        buf.writeVarInt(recipe.getIngredients().size());

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buf);
        }

        buf.writeItemStack(recipe.output);
        buf.writeVarInt(recipe.additionalOutput.size());
        for (Ingredient ingredient : recipe.additionalOutput) {
            ingredient.write(buf);
        }
        buf.writeVarInt(recipe.cookTime);
        buf.writeVarInt(recipe.priority);
    }
}