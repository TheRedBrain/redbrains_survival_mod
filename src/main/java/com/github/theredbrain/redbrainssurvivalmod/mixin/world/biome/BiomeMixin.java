package com.github.theredbrain.redbrainssurvivalmod.mixin.world.biome;
//
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.SeasonModifiers;
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.colors.SeasonFoliageColors;
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.colors.SeasonGrassColors;
//import com.github.theredbrain.redbrainssurvivalmod.fabricseasonsfork.colors.ColorsCache;
//import com.github.theredbrain.redbrainssurvivalmod.registry.SeasonsRegistry;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.util.Identifier;
//import net.minecraft.util.registry.Registry;
//import net.minecraft.world.World;
//import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeEffects;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//import java.util.Optional;
//
//// Fabric Seasons
//@Mixin(Biome.class)
//public class BiomeMixin {
//
//    @SuppressWarnings("ConstantConditions")
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeEffects;getGrassColor()Ljava/util/Optional;"), method = "getGrassColorAt")
//    public Optional<Integer> getSeasonGrassColor(BiomeEffects effects) {
//        Biome biome = (Biome) ((Object) this);
//        if(ColorsCache.hasGrassCache(biome)) {
//            return ColorsCache.getGrassCache(biome);
//        }else {
//            Optional<Integer> returnColor = effects.getGrassColor();
//            World world = MinecraftClient.getInstance().world;
//            if(world != null) {
//                Identifier biomeIdentifier = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
//                Optional<Integer> seasonGrassColor = SeasonModifiers.getSeasonGrassColor(biome, biomeIdentifier, SeasonsRegistry.getCurrentSeason());
//                if(seasonGrassColor.isPresent()) {
//                    returnColor = seasonGrassColor;
//                }
//            }
//            ColorsCache.createGrassCache(biome, returnColor);
//            return returnColor;
//        }
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeEffects;getFoliageColor()Ljava/util/Optional;"), method = "getFoliageColor")
//    public Optional<Integer> getSeasonFoliageColor(BiomeEffects effects) {
//        Biome biome = (Biome) ((Object) this);
//        if(ColorsCache.hasFoliageCache(biome)) {
//            return ColorsCache.getFoliageCache(biome);
//        }else{
//            Optional<Integer> returnColor = effects.getFoliageColor();
//            World world = MinecraftClient.getInstance().world;
//            if(world != null) {
//                Identifier biomeIdentifier = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
//                Optional<Integer> seasonFoliageColor = SeasonModifiers.getSeasonFoliageColor(biome, biomeIdentifier, SeasonsRegistry.getCurrentSeason());
//                if(seasonFoliageColor.isPresent()) {
//                    returnColor = seasonFoliageColor;
//                }
//            }
//            ColorsCache.createFoliageCache(biome, returnColor);
//            return returnColor;
//        }
//
//    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeEffects$GrassColorModifier;getModifiedGrassColor(DDI)I"), method = "getGrassColorAt")
//    public int getSeasonModifiedGrassColor(BiomeEffects.GrassColorModifier gcm, double x, double z, int color) {
//        if(gcm == BiomeEffects.GrassColorModifier.SWAMP) {
//            int swampColor1 = SeasonModifiers.getMinecraftSwampGrass1().getColor(SeasonsRegistry.getCurrentSeason());
//            int swampColor2 = SeasonModifiers.getMinecraftSwampGrass2().getColor(SeasonsRegistry.getCurrentSeason());
//
//            double d = Biome.FOLIAGE_NOISE.sample(x * 0.0225D, z * 0.0225D, false);
//            return d < -0.1D ? swampColor1 : swampColor2;
//        }else{
//            return gcm.getModifiedGrassColor(x, z, color);
//        }
//    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/world/FoliageColors;getColor(DD)I"), method = "getDefaultFoliageColor")
//    public int getSeasonDefaultFoliageColor(double d, double e) {
//        return SeasonFoliageColors.getColor(SeasonsRegistry.getCurrentSeason(), d, e);
//    }
//
//    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/world/GrassColors;getColor(DD)I"), method = "getDefaultGrassColor")
//    public int getSeasonDefaultGrassColor(double d, double e) {
//        return SeasonGrassColors.getColor(SeasonsRegistry.getCurrentSeason(), d, e);
//    }
//
//}
