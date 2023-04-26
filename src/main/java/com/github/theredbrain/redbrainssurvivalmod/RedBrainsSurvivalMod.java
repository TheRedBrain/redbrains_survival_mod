package com.github.theredbrain.redbrainssurvivalmod;

import com.github.theredbrain.redbrainssurvivalmod.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedBrainsSurvivalMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "redbrainssurvivalmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ItemGroup SURVIVAL_BLOCKS = FabricItemGroup.builder(new Identifier(MOD_ID, "survival_blocks"))
			.icon(() -> new ItemStack(BlocksRegistry.OAK_CRAFTING_TABLE.get()))
			.build();

	public static final ItemGroup SURVIVAL_ITEMS = FabricItemGroup.builder(new Identifier(MOD_ID, "survival_items"))
			.icon(() -> new ItemStack(ItemsRegistry.IRON_CHISEL.get()))
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		BlocksRegistry.registerAll();
		new EntitiesRegistry();
		ItemsRegistry.registerAll();
//		new ItemsRegistry();
		RecipeTypesRegistry.registerAll();
		ScreenHandlerTypesRegistry.registerAll();
		new StatusEffectsRegistry();
		StructurePlacementTypeRegistry.register();
		new TreeDecoratorTypes();
		LOGGER.info("Hello Fabric world!");
	}
}
