package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.screen.SoulforgeScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

// TODO revamp using libGUI
public class ScreenHandlerTypesRegistry {
//    COOKING_POT("cooking_pot", CookingPotScreenHandler.class, CookingPotScreenHandler::new),
//    TRIGGERED_BLOCK("triggered_block", TriggeredBlockScreenHandler.class, new ScreenHandlerType<>((syncId, inventory) -> new TriggeredBlockScreenHandler(syncId, inventory, new PropertyDelegate())));

//    private static final ScreenHandlerType<CookingPotScreenHandler> COOKING_POT_SCREEN_HANDLER = new ScreenHandlerType<>((syncId, inventory) -> new CookingPotScreenHandler(syncId, inventory));
//    public static final ScreenHandlerType<CookingPotScreenHandler> COOKING_POT_SCREEN_HANDLER = new ScreenHandlerType<>((syncId, inventory) -> new CookingPotScreenHandler(syncId, inventory, EntitiesRegistry.COOKING_POT_ENTITY, new PropertyDelegate()));
    public static final ScreenHandlerType<SoulforgeScreenHandler> SOULFORGE_SCREEN_HANDLER = new ScreenHandlerType<>(SoulforgeScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
//    public static final ScreenHandlerType<SoulforgeScreenHandler> SOULFORGE_SCREEN_HANDLER = new ScreenHandlerType<SoulforgeScreenHandler>((syncId, inventory) -> new SoulforgeScreenHandler(syncId, inventory), FeatureFlags.VANILLA_FEATURES);

    public static void registerAll() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(RedBrainsSurvivalMod.MOD_ID, "soulforge"), SOULFORGE_SCREEN_HANDLER);
    }

//    @SuppressWarnings("unchecked")
//    public <T extends ScreenHandler> ScreenHandlerType<T> get() {
//        return (ScreenHandlerType<T>) get(screenHandlerClass);
//    }
//
//    @SuppressWarnings({"unchecked","unused"})
//    private <T extends ScreenHandler> ScreenHandlerType<T> get(Class<T> clazz) {
//        if (screenHandlerType == null) {
//            screenHandlerType = new ExtendedScreenHandlerType<>((ScreenHandlerRegistry.ExtendedClientHandlerFactory<T>) screenHandlerFactory);
//        }
//
//        return (ScreenHandlerType<T>) screenHandlerType;
//    }
}