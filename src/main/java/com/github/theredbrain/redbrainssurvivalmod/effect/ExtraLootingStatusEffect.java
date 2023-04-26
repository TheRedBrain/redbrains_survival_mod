package com.github.theredbrain.redbrainssurvivalmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ExtraLootingStatusEffect extends StatusEffect {
    public ExtraLootingStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 3381504 /*TODO correct color*/);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }
}
