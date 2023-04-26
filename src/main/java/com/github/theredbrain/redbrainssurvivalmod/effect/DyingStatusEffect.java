package com.github.theredbrain.redbrainssurvivalmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DyingStatusEffect extends StatusEffect {
    public DyingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 3381504 /*TODO correct color*/);
    }
}
