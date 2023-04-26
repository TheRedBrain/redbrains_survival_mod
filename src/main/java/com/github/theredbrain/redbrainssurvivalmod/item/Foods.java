package com.github.theredbrain.redbrainssurvivalmod.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class Foods {
    // basic
    public static final FoodComponent BROWN_MUSHROOM = (new FoodComponent.Builder()).hunger(1).saturationModifier(0f).build();
    public static final FoodComponent RED_MUSHROOM = (new FoodComponent.Builder()).hunger(1).saturationModifier(0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1.f).build();
    public static final FoodComponent PUMPKIN_SEEDS = (new FoodComponent.Builder()).hunger(1).saturationModifier(0f).build();
    public static final FoodComponent COCOA_BEANS = (new FoodComponent.Builder()).hunger(1).saturationModifier(0f).build();
    public static final FoodComponent CHICKEN = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).meat().build();
    public static final FoodComponent COOKED_CHICKEN = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).meat().build();
    public static final FoodComponent MUTTON = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_MUTTON = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).meat().build();
    public static final FoodComponent BEEF = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_BEEF = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).meat().build();
    public static final FoodComponent PORK_CHOP = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).meat().build();
    public static final FoodComponent COOKED_PORK_CHOP = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).build();
    public static final FoodComponent WOLF_CHOP = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).meat().build();
    public static final FoodComponent COOKED_WOLF_CHOP = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).build();
    public static final FoodComponent CURED_MEAT = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent BURNED_MEAT = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent ROTTEN_FLESH = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .8f).build();
    public static final FoodComponent BAT_WING = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent SPIDER_EYE = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1.f).build();
    public static final FoodComponent CREEPER_OYSTER = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 1.f).build();

    // iron age
    public static final FoodComponent MASHED_MELON = (new FoodComponent.Builder()).hunger(1).saturationModifier(0f).build();
    public static final FoodComponent MELON_SLICE = (new FoodComponent.Builder()).hunger(2).saturationModifier(0f).build();
    public static final FoodComponent MILK = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent CHOCOLATE_MILK = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).build();
    public static final FoodComponent COD = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_COD = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).build();
    public static final FoodComponent SALMON = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_SALMON = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).build();
    public static final FoodComponent RAW_EGG = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).snack().build();
    public static final FoodComponent FRIED_EGG = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).build();
    public static final FoodComponent SCRAMBLED_EGG = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_SCRAMBLED_EGG = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).build();
    public static final FoodComponent MUSHROOM_OMELET = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_MUSHROOM_OMELET = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).build();
    public static final FoodComponent HAM_AND_EGGS = (new FoodComponent.Builder()).hunger(18).saturationModifier(.33f).build();

    // cauldron age
    public static final FoodComponent CHOCOLATE = (new FoodComponent.Builder()).hunger(6).saturationModifier(.66f).alwaysEdible().build();
    public static final FoodComponent CREAM_OF_MUSHROOM = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).alwaysEdible().build();
    public static final FoodComponent POACHED_EGG = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).build();
    public static final FoodComponent CHOWDER = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).build();
    public static final FoodComponent KIBBLE = (new FoodComponent.Builder()).hunger(9).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent FOUL_FOOD = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .8f).build();

    // domesticated crops
    public static final FoodComponent BREAD = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).alwaysEdible().build();
    public static final FoodComponent TASTY_SANDWICH = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).build();
    public static final FoodComponent COOKIE = (new FoodComponent.Builder()).hunger(3).saturationModifier(1.33f).build();
    public static final FoodComponent PUMPKIN_PIE = (new FoodComponent.Builder()).hunger(6).saturationModifier(3.33f).build();
    public static final FoodComponent DONUT = (new FoodComponent.Builder()).hunger(3).saturationModifier(.66f).build();
    public static final FoodComponent POTATO = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).build();
    public static final FoodComponent POISONOUS_POTATO = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), .6f).build();
    public static final FoodComponent BAKED_POTATO = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent BOILED_POTATO = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent STEAK_AND_POTATOES = (new FoodComponent.Builder()).hunger(18).saturationModifier(.33f).build();
    public static final FoodComponent CARROT = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).build();
    public static final FoodComponent COOKED_CARROT = (new FoodComponent.Builder()).hunger(6).saturationModifier(.33f).build();
    public static final FoodComponent RAW_KEBAB = (new FoodComponent.Builder()).hunger(18).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent KEBAB = (new FoodComponent.Builder()).hunger(24).saturationModifier(.33f).build();
    public static final FoodComponent STEAK_DINNER = (new FoodComponent.Builder()).hunger(24).saturationModifier(.33f).build();
    public static final FoodComponent PORK_DINNER = (new FoodComponent.Builder()).hunger(24).saturationModifier(.33f).build();
    public static final FoodComponent WOLF_DINNER = (new FoodComponent.Builder()).hunger(24).saturationModifier(.33f).build();
    public static final FoodComponent CHICKEN_SOUP = (new FoodComponent.Builder()).hunger(24).saturationModifier(.33f).build();
    public static final FoodComponent HEARTY_STEW = (new FoodComponent.Builder()).hunger(30).saturationModifier(.33f).build();

    // other foods
    public static final FoodComponent APPLE = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).alwaysEdible().build();
    public static final FoodComponent GOLDEN_APPLE = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).build();
    public static final FoodComponent ENCHANTED_GOLDEN_APPLE = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).build();
    public static final FoodComponent GOLDEN_CARROT = (new FoodComponent.Builder()).hunger(3).saturationModifier(0f).build();
    public static final FoodComponent MYSTERY_MEAT = (new FoodComponent.Builder()).hunger(12).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_MYSTERY_MEAT = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).build();
    public static final FoodComponent LIVER_OF_THE_BEAST = (new FoodComponent.Builder()).hunger(15).saturationModifier(.33f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), .3f).build();
    public static final FoodComponent COOKED_LIVER = (new FoodComponent.Builder()).hunger(18).saturationModifier(.33f).build();
    
}