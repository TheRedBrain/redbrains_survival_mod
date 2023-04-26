package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;

public abstract class FallingGourdBlock extends FallingBlock {
    public FallingGourdBlock(Settings settings) {
        super(settings);
    }

    public abstract CustomStemBlock getStem();

    public abstract CustomAttachedStemBlock getAttachedStem();

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(1.0f, 16);
    }
}
