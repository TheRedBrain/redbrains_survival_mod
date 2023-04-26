package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.recipe.*;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum RecipeTypesRegistry {
    COOKING_RECIPE_SERIALIZER("cooking", CookingRecipe.class, CookingRecipeSerializer::new),
//    CUTTING_RECIPE_SERIALIZER("cutting", CuttingBoardRecipe.class, CuttingBoardRecipeSerializer::new),
    GRINDING_RECIPE_SERIALIZER("grinding", GrindingRecipe.class, GrindingRecipeSerializer::new),
    RENDERING_RECIPE_SERIALIZER("rendering", RenderingRecipe.class, RenderingRecipeSerializer::new),
    CRUCIBLE_SMELTING_RECIPE_SERIALIZER("crucible_smelting", CrucibleSmeltingRecipe.class, CrucibleSmeltingRecipeSerializer::new);
    // TODO refactor
    private final String pathName;
    private final Class<? extends Recipe<? extends Inventory>> recipeClass;
    private final Supplier<RecipeSerializer<? extends Recipe<? extends Inventory>>> recipeSerializerSupplier;
    private RecipeSerializer<? extends Recipe<? extends Inventory>> serializer;
    private RecipeType<? extends Recipe<? extends Inventory>> type;

    RecipeTypesRegistry(String pathName, Class<? extends Recipe<? extends Inventory>> recipeClass,
            Supplier<RecipeSerializer<? extends Recipe<? extends Inventory>>> recipeSerializerSupplier) {
        this.pathName = pathName;
        this.recipeClass = recipeClass;
        this.recipeSerializerSupplier = recipeSerializerSupplier;
    }

    public static void registerAll() {
        for (RecipeTypesRegistry value : values()) {
            Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(RedBrainsSurvivalMod.MOD_ID, value.pathName), value.serializer());
            value.type = RecipeType.register(new Identifier(RedBrainsSurvivalMod.MOD_ID, value.pathName).toString());
        }
    }

    public RecipeSerializer<? extends Recipe<? extends Inventory>> serializer() {
        if (serializer == null) {
            serializer = recipeSerializerSupplier.get();
        }

        return serializer;
    }

    @SuppressWarnings("unchecked")
    public <T extends Recipe<? extends Inventory>> RecipeType<T> type() {
        return (RecipeType<T>) type(recipeClass);
    }

    @SuppressWarnings({"unchecked","unused"})
    private <T extends Recipe<? extends Inventory>> RecipeType<T> type(Class<T> clazz) {
        if (type == null) {
            type = RecipeType.register(new Identifier(RedBrainsSurvivalMod.MOD_ID, pathName).toString());
        }
        return (RecipeType<T>) type;
    }
}