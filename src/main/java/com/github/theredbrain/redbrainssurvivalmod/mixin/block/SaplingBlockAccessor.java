package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SaplingBlock.class)
public interface SaplingBlockAccessor {
    @Accessor("generator")
    SaplingGenerator getGenerator();
}
