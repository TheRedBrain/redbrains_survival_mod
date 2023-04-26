package com.github.theredbrain.redbrainssurvivalmod.registry;
//
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.Season;
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.WeatherCache;
//import com.github.theredbrain.redbrainssurvivalmod.mixin.world.biome.WeatherAccessor;
//import com.github.theredbrain.redbrainssurvivalmod.tags.Tags;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
//import net.minecraft.block.*;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.item.BlockItem;
//import net.minecraft.item.Item;
//import net.minecraft.tag.BiomeTags;
//import net.minecraft.tag.TagKey;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.registry.Registry;
//import net.minecraft.util.registry.RegistryEntry;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.dimension.DimensionTypes;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//
//public class SeasonsRegistry {
//
//    public static HashMap<Item, Block> SEEDS_MAP = new HashMap<>();
//    // 8 minecraft days (1 lunar cycle) per season
//    private static int SEASON_LENGTH = 192000;
//
//    public static void registerSeedMap() {
//
//        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
//            SEEDS_MAP.clear();
//            Registry.ITEM.forEach(item -> {
//                if(item instanceof BlockItem) {
//                    Block block = ((BlockItem) item).getBlock();
//                    if(block instanceof CropBlock || block instanceof StemBlock || block instanceof CocoaBlock || block instanceof SaplingBlock) {
//                        SEEDS_MAP.put(item, ((BlockItem) item).getBlock());
//                    }
//                }
//            });
//        });
//    }
//
//    public static Season getCurrentSeason(World world) {
//        if (world.getDimensionKey().getRegistry() == DimensionTypes.OVERWORLD.getRegistry()) {
//            int worldTime = Math.toIntExact(world.getTimeOfDay());
//            int seasonTime = (worldTime / SEASON_LENGTH);
//            return Season.values()[seasonTime % 12];
//        }
//        return Season.SPRING;
//    }
//
//    @Environment(EnvType.CLIENT)
//    public static Season getCurrentSeason() {
//        MinecraftClient client = MinecraftClient.getInstance();
//        ClientPlayerEntity player = client.player;
//        if(player != null && player.world != null) {
//            return getCurrentSeason(player.world);
//        }
//        return Season.SPRING;
//    }
//
//    // TODO maybe balance biome temperatures
//    public static void injectBiomeTemperature(RegistryEntry<Biome> biome, World world) {
//
//        List<TagKey<Biome>> ignoredCategories = Arrays.asList(BiomeTags.IS_NETHER, BiomeTags.IS_END, BiomeTags.IS_OCEAN);
//        if(ignoredCategories.stream().anyMatch(biome::isIn)) return;
//
//        Season season = getCurrentSeason(world);
//
//        Identifier biomeIdentifier = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome.value());
//        Biome.Weather currentWeather = biome.value().weather;
//
//        Biome.Weather originalWeather;
//        if (!WeatherCache.hasCache(biomeIdentifier)) {
//            originalWeather = new Biome.Weather(currentWeather.precipitation, currentWeather.temperature, currentWeather.temperatureModifier, currentWeather.downfall);
//            WeatherCache.setCache(biomeIdentifier, originalWeather);
//        } else {
//            originalWeather = WeatherCache.getCache(biomeIdentifier);
//        }
//
//        if(originalWeather == null) {
//            return;
//        }
//        float knittings = originalWeather.temperature;
//        if (biome.isIn(Tags.UNAFFECTED_BY_SEASONS)) {
//            // Cave Biomes are not affected by the seasons
//            ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//            ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//        } else if(biome.isIn(BiomeTags.IS_JUNGLE) || biome.isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
//            // Jungle & Swamp Biomes
//            if (season == Season.EARLY_WINTER || season == Season.WINTER || season == Season.LATE_WINTER) {
//                ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings-0.1f);
//            } else {
//                ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//            }
//        } else if(knittings <= 0.1) {
//            // Frozen Biomes
//            switch (season) {
//                case SUMMER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.RAIN);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.3f);
//                }
//                case WINTER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.SNOW);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings - 0.2f);
//                }
//                default -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//            }
//        } else if(knittings <= 0.3) {
//            // Cold Biomes
//            switch (season) {
//                case SPRING -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.RAIN);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//                case SUMMER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.RAIN);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.2f);
//                }
//                case WINTER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.SNOW);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings - 0.2f);
//                }
//                default -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//            }
//        } else if(knittings <= 0.95) {
//            // Temperate Biomes
//            switch (season) {
//                case SUMMER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.2f);
//                }
//                case FALL -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings - 0.1f);
//                }
//                case WINTER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.SNOW);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings - 0.7f);
//                }
//                default -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//            }
//        } else if(knittings <= 1.2) {
//            // Savanna Biomes
//            switch (season) {
//                case SUMMER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.2f);
//                }
//                case WINTER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(Biome.Precipitation.RAIN);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings - 0.2f);
//                }
//                default -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//            }
//        } else {
//            // Desert & Badlands
//            switch (season) {
//                case SUMMER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.2f);
//                }
//                case WINTER -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings);
//                }
//                default -> {
//                    ((WeatherAccessor) (Object) currentWeather).setPrecipitation(originalWeather.precipitation);
//                    ((WeatherAccessor) (Object) currentWeather).setTemperature(knittings + 0.1f);
//                }
//            }
//        }
//    }
//}
