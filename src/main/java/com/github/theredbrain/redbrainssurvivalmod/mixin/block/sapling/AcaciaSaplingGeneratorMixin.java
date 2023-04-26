package com.github.theredbrain.redbrainssurvivalmod.mixin.block.sapling;

import com.github.theredbrain.redbrainssurvivalmod.registry.CustomTreeConfiguredFeatures;
import net.minecraft.block.sapling.AcaciaSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AcaciaSaplingGenerator.class)
public class AcaciaSaplingGeneratorMixin {

    @Inject(method = "getTreeFeature", at = @At("HEAD"), cancellable = true)
    protected void getCustomTreeFeature(Random random, boolean bees, CallbackInfoReturnable<RegistryKey<ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(CustomTreeConfiguredFeatures.CUSTOM_ACACIA);
    }
}
