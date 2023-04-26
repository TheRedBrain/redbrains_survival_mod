package com.github.theredbrain.redbrainssurvivalmod.mixin.world.gen.feature;

import com.github.theredbrain.redbrainssurvivalmod.registry.CustomTreeConfiguredFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConfiguredFeatures.class)
public class ConfiguredFeaturesMixin {

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void bootstrapCustomFeatures(Registerable<ConfiguredFeature<?, ?>> featureRegisterable, CallbackInfo ci) {
        CustomTreeConfiguredFeatures.bootstrap(featureRegisterable);
    }
}
