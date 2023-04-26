package com.github.theredbrain.redbrainssurvivalmod.mixin.block.sapling;

import com.github.theredbrain.redbrainssurvivalmod.registry.CustomTreeConfiguredFeatures;
import net.minecraft.block.sapling.DarkOakSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DarkOakSaplingGenerator.class)
public class DarkOakSaplingGeneratorMixin {

    @Inject(method = "getLargeTreeFeature", at = @At("HEAD"), cancellable = true)
    protected void getLargeCustomTreeFeature(Random random, CallbackInfoReturnable<RegistryKey<ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(CustomTreeConfiguredFeatures.CUSTOM_DARK_OAK);
    }
}
