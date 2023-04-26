package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.world.gen.chunk.placement.RandomSpreadMaxDistanceFromZeroStructurePlacement;
import com.github.theredbrain.redbrainssurvivalmod.world.gen.chunk.placement.RandomSpreadMinDistanceFromZeroStructurePlacement;
import com.github.theredbrain.redbrainssurvivalmod.world.gen.chunk.placement.RandomSpreadMinMaxDistanceFromZeroStructurePlacement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;

public class StructurePlacementTypeRegistry {
    public static StructurePlacementType<RandomSpreadMinDistanceFromZeroStructurePlacement> RANDOM_SPREAD_MIN_DISTANCE_FROM_ZERO = () -> RandomSpreadMinDistanceFromZeroStructurePlacement.CODEC;
    public static StructurePlacementType<RandomSpreadMaxDistanceFromZeroStructurePlacement> RANDOM_SPREAD_MAX_DISTANCE_FROM_ZERO = () -> RandomSpreadMaxDistanceFromZeroStructurePlacement.CODEC;
    public static StructurePlacementType<RandomSpreadMinMaxDistanceFromZeroStructurePlacement> RANDOM_SPREAD_MIN_MAX_DISTANCE_FROM_ZERO = () -> RandomSpreadMinMaxDistanceFromZeroStructurePlacement.CODEC;

    public static void register() {
        Registry.register(Registries.STRUCTURE_PLACEMENT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "random_spread_min_distance_from_zero"), RANDOM_SPREAD_MIN_DISTANCE_FROM_ZERO);
        Registry.register(Registries.STRUCTURE_PLACEMENT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "random_spread_max_distance_from_zero"), RANDOM_SPREAD_MAX_DISTANCE_FROM_ZERO);
        Registry.register(Registries.STRUCTURE_PLACEMENT, new Identifier(RedBrainsSurvivalMod.MOD_ID, "random_spread_min_max_distance_from_zero"), RANDOM_SPREAD_MIN_MAX_DISTANCE_FROM_ZERO);
    }
}
