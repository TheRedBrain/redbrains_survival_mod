package com.github.theredbrain.redbrainssurvivalmod.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class InjuredStatusEffect extends StatusEffect {
    public InjuredStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 3381504 /*TODO correct color*/);
    }
}
