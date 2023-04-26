package com.github.theredbrain.redbrainssurvivalmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class GrindingRecipeSerializer implements RecipeSerializer<GrindingRecipe> {
//    private static DefaultedList<Ingredient> readIngredients(JsonArray ingredientArray) {
//        DefaultedList<Ingredient> ingredientList = DefaultedList.of();
//
//        for (JsonElement ingredientJson : ingredientArray) {
//            Ingredient ingredient = Ingredient.fromJson(ingredientJson);
//            if (ingredient.getMatchingStacks() != null && ingredient.getMatchingStacks().length > 0) {
//                ingredientList.add(ingredient);
//            }
//        }
//
//        return ingredientList;
//    }

    private static DefaultedList<ItemStack> readResultList(JsonElement resultArray) {
        DefaultedList<ItemStack> resultList = DefaultedList.of();

        JsonObject jsonResult = resultArray.getAsJsonObject();
        resultList.add(new ItemStack(JsonHelper.getItem(jsonResult, "item"), JsonHelper.getInt(jsonResult, "count", 1)));
        return resultList;
    }

    @Override
    public GrindingRecipe read(Identifier id, JsonObject json) {
        String group = JsonHelper.getString(json, "group", "");
        JsonElement jsonElement = JsonHelper.hasArray(json, "ingredient") ? JsonHelper.getArray(json, "ingredient") : JsonHelper.getObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson((JsonElement)jsonElement);
        if (ingredient.isEmpty()) {
            throw new JsonParseException("No ingredients for cooking recipe");
        } else {
            int time = JsonHelper.getInt(json, "grindingTime", 200);
            DefaultedList<ItemStack> resultList = readResultList(JsonHelper.hasArray(json, "results") ? JsonHelper.getArray(json, "results") : JsonHelper.getObject(json, "results"));
            String soundId = JsonHelper.getString(json, "sound", "");
            return new GrindingRecipe(id, group, ingredient, time, resultList, soundId);
        }
    }

    @Override
    public GrindingRecipe read(Identifier id, PacketByteBuf buf) {
        String groupIn = buf.readString(32767);
        Ingredient input = Ingredient.fromPacket(buf);
        Integer time = buf.readVarInt();
        int resultSize = buf.readVarInt();
        DefaultedList<ItemStack> resultList = DefaultedList.ofSize(resultSize, ItemStack.EMPTY);
        for (int j = 0; j < resultList.size(); ++j) {
            resultList.set(j, buf.readItemStack());
        }
        String soundEvent = buf.readString();

        return new GrindingRecipe(id, groupIn, input, time, resultList, soundEvent);
    }

    @Override
    public void write(PacketByteBuf buf, GrindingRecipe recipe) {
        buf.writeString(recipe.getGroup());
        recipe.getIngredients().get(0).write(buf);
        buf.writeVarInt(recipe.getGrindingTime());
        buf.writeVarInt(recipe.getResultList().size());
        for (ItemStack result : recipe.getResultList()) {
            buf.writeItemStack(result);
        }

        buf.writeString(recipe.getSoundEvent());
    }
}