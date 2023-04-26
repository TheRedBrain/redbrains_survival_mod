package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.world.DarkOakStumpTreeDecorator;
import com.github.theredbrain.redbrainssurvivalmod.world.GiantStumpTreeDecorator;
import com.github.theredbrain.redbrainssurvivalmod.world.StumpTreeDecorator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class TreeDecoratorTypes {

    public static final TreeDecoratorType<StumpTreeDecorator> DARK_OAK_STUMP = Registry.register(Registries.TREE_DECORATOR_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "dark_oak_stump"), new TreeDecoratorType(DarkOakStumpTreeDecorator.CODEC));
    public static final TreeDecoratorType<StumpTreeDecorator> GIANT_STUMP = Registry.register(Registries.TREE_DECORATOR_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "giant_stump"), new TreeDecoratorType(GiantStumpTreeDecorator.CODEC));
    public static final TreeDecoratorType<StumpTreeDecorator> STUMP = Registry.register(Registries.TREE_DECORATOR_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "stump"), new TreeDecoratorType(StumpTreeDecorator.CODEC));
}
