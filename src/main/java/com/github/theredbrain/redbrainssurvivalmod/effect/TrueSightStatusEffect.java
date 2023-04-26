package com.github.theredbrain.redbrainssurvivalmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class TrueSightStatusEffect extends StatusEffect {
    public TrueSightStatusEffect() {super(StatusEffectCategory.BENEFICIAL, 3381504 /*TODO correct color*/);}

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        int posX = entity.getBlockX();
        int posY = entity.getBlockY();
        int posZ = entity.getBlockZ();
        World world = entity.getWorld();
        int radius = 3 * (1 + amplifier);

        for (int i = posX - radius; i <= posX + radius; i++) {
            for (int j = posY - radius; j <= posY + radius; j++) {
                for (int k = posZ - radius; k <= posZ + radius; k++) {
                    if (world.getBlockState(new BlockPos(i, j, k)).isAir() && world.getBlockState(new BlockPos(i, j-1, k)).isSolidBlock(world, new BlockPos(i, j-1, k)) && (world.getLightLevel(LightType.BLOCK, new BlockPos(i, j, k)) <= 0)) {
                        if (Math.random() <= 0.1)
                        world.addParticle(ParticleTypes.SOUL, i + 0.2 + (Math.random() * 0.6), j, k + 0.2 + (Math.random() * 0.6), 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }
}
