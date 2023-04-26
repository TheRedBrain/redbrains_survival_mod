package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class StatusEffectsRegistry {

    // low hunger bar
    public static final StatusEffect PECKISH = register("peckish", new PeckishStatusEffect());
    public static final StatusEffect HUNGRY = register("hungry", new HungryStatusEffect());
    public static final StatusEffect FAMISHED = register("famished", new FamishedStatusEffect());
    public static final StatusEffect STARVING = register("starving", new StarvingStatusEffect());
    public static final StatusEffect DYING = register("dying", new DyingStatusEffect());

    // low health bar
    public static final StatusEffect HURT = register("hurt", new HurtStatusEffect());
    public static final StatusEffect INJURED = register("injured", new InjuredStatusEffect());
    public static final StatusEffect WOUNDED = register("wounded", new WoundedStatusEffect());
    public static final StatusEffect CRIPPLED = register("crippled", new CrippledStatusEffect());
    public static final StatusEffect AGONIZING = register("agonizing", new AgonizingStatusEffect());

    // high fat/saturation bar
    public static final StatusEffect PLUMP = register("plump", new PlumpStatusEffect());
    public static final StatusEffect CHUBBY = register("chubby", new ChubbyStatusEffect());
    public static final StatusEffect FAT = register("fat", new FatStatusEffect());
    public static final StatusEffect OBESE = register("obese", new ObeseStatusEffect());

    // beacon effects
    public static final StatusEffect EXTRA_LOOTING = register("extra_looting", new ExtraLootingStatusEffect());
    public static final StatusEffect PLAYER_KILL = register("player_kill", new PlayerKillStatusEffect());
    public static final StatusEffect TRUE_SIGHT = register("true_sight", new TrueSightStatusEffect());

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, id), entry);
    }
//
//    public static void registerEffects() {
//        // low hunger bar
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "peckish"), PECKISH);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "hungry"), HUNGRY);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "famished"), FAMISHED);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "starving"), STARVING);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "dying"), DYING);
//
//        // low health bar
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "hurt"), HURT);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "injured"), INJURED);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "wounded"), WOUNDED);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "crippled"), CRIPPLED);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "agonizing"), AGONIZING);
//
//        // high fat/saturation bar
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "plump"), PLUMP);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "chubby"), CHUBBY);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "fat"), FAT);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "obese"), OBESE);
//
////        // food
////        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "farmersdelight_comfort"), COMFORT);
////        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "farmersdelight_nourished"), NOURISHED);
//
//        // beacon effects
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "extra_looting"), EXTRA_LOOTING);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "player_kill"), PLAYER_KILL);
//        Registry.register(Registry.STATUS_EFFECT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "true_sight"), TRUE_SIGHT);
//    }
}
