package com.github.theredbrain.redbrainssurvivalmod.mixin.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public static String[] getPattern(JsonArray json) {
        String[] strings = new String[json.size()];
        if (strings.length > 4) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 4 is maximum");
        }
        if (strings.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }
        for (int i = 0; i < strings.length; ++i) {
            String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
            if (string.length() > 4) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, 4 is maximum");
            }
            if (i > 0 && strings[0].length() != string.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            strings[i] = string;
        }
        return strings;
    }
}
