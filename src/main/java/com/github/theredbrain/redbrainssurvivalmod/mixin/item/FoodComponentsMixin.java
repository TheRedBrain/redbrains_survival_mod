package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponent.Builder;
import net.minecraft.item.FoodComponents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoodComponents.class)
public class FoodComponentsMixin {

    // Forage
    @Shadow
    @Final
    @Mutable
    public static FoodComponent APPLE = (new Builder()).hunger(2).saturationModifier(0.2F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent BEEF = (new Builder()).hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent BEETROOT = (new Builder()).hunger(1).saturationModifier(0.2F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent CARROT = (new Builder()).hunger(1).saturationModifier(0.1F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent CHICKEN = (new Builder()).hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent CHORUS_FRUIT = (new Builder()).hunger(1).saturationModifier(0.1F).alwaysEdible().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COD = (new Builder()).hunger(4).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.7F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent GLOW_BERRIES = (new Builder()).hunger(1).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 0), 1.0F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent MUTTON = (new Builder()).hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent POISONOUS_POTATO = (new Builder()).hunger(2).saturationModifier(0.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent PORKCHOP = (new Builder()).hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent POTATO = (new Builder()).hunger(2).saturationModifier(0.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 2), 0.3F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent PUFFERFISH = (new Builder()).hunger(4).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 1.0F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent RABBIT = (new Builder()).hunger(4).saturationModifier(0.3F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent SALMON = (new Builder()).hunger(4).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.7F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent SWEET_BERRIES = (new Builder()).hunger(1).saturationModifier(0.1F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent TROPICAL_FISH = (new Builder()).hunger(4).saturationModifier(0.1F)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.7F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 1200, 1), 0.2F).build();

    // Basic Foods
    @Shadow
    @Final
    @Mutable
    public static FoodComponent BAKED_POTATO = (new Builder()).hunger(3).saturationModifier(0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent BREAD = (new Builder()).hunger(4).saturationModifier(0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_BEEF = (new Builder()).hunger(5).saturationModifier(0.6F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_CHICKEN = (new Builder()).hunger(5).saturationModifier(0.6F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_COD = (new Builder()).hunger(5).saturationModifier(0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_MUTTON = (new Builder()).hunger(5).saturationModifier(0.6F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_PORKCHOP = (new Builder()).hunger(5).saturationModifier(0.6F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_RABBIT = (new Builder()).hunger(5).saturationModifier(0.6F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKED_SALMON = (new Builder()).hunger(5).saturationModifier(0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent DRIED_KELP = (new Builder()).hunger(1).saturationModifier(0.1F).snack().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent MELON_SLICE = (new Builder()).hunger(2).saturationModifier(0.3F).build();

    // Sweets
    @Shadow
    @Final
    @Mutable
    public static FoodComponent COOKIE = (new Builder()).hunger(1).saturationModifier(1.5F).snack().alwaysEdible().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent HONEY_BOTTLE = (new Builder()).hunger(1).saturationModifier(1.0F).snack().alwaysEdible().build();

    // Bowl Foods
    @Shadow
    @Final
    @Mutable
    public static FoodComponent BEETROOT_SOUP = (new Builder()).hunger(6).saturationModifier(0.4f).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent MUSHROOM_STEW = (new Builder()).hunger(6).saturationModifier(0.4f).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent RABBIT_STEW = (new Builder()).hunger(10).saturationModifier(0.6F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent SUSPICIOUS_STEW = (new Builder()).hunger(4).saturationModifier(0.4f).alwaysEdible().build();

    // Other Foods
    @Shadow
    @Final
    @Mutable
    public static FoodComponent ENCHANTED_GOLDEN_APPLE = (new Builder()).hunger(2).saturationModifier(0.2F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1.0F).alwaysEdible().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent GOLDEN_APPLE = (new Builder()).hunger(2).saturationModifier(0.2F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent GOLDEN_CARROT = (new Builder()).hunger(1).saturationModifier(0.1F).build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent ROTTEN_FLESH = (new Builder()).hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 1.0F).meat().build();

    @Shadow
    @Final
    @Mutable
    public static FoodComponent SPIDER_EYE = (new Builder()).hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 1), 1.0F).build();
}
