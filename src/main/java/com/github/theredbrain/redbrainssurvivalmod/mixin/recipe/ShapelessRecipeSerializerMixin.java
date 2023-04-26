package com.github.theredbrain.redbrainssurvivalmod.mixin.recipe;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParseException;
//import net.minecraft.item.ItemStack;
//import net.minecraft.recipe.Ingredient;
//import net.minecraft.recipe.ShapedRecipe;
//import net.minecraft.recipe.ShapelessRecipe;
//import net.minecraft.recipe.book.CraftingRecipeCategory;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.JsonHelper;
//import net.minecraft.util.collection.DefaultedList;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//
//@Mixin(ShapelessRecipe.Serializer.class)
//public class ShapelessRecipeSerializerMixin {
//
//    private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
//        throw new AssertionError();
//    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
//        String string = JsonHelper.getString(jsonObject, "group", "");
//        CraftingRecipeCategory craftingRecipeCategory = CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CraftingRecipeCategory.MISC);
//        DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
//        if (defaultedList.isEmpty()) {
//            throw new JsonParseException("No ingredients for shapeless recipe");
//        }
//        if (defaultedList.size() > 16) {
//            throw new JsonParseException("Too many ingredients for shapeless recipe");
//        }
//        ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
//        return new ShapelessRecipe(identifier, string, craftingRecipeCategory, itemStack, defaultedList);
//    }
//}
