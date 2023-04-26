package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;

public class ChoppingBlock extends Block {
    private final boolean bloody;

    public ChoppingBlock(boolean bloody, Settings settings) {
        super(settings);
        this.bloody = bloody;
    }
}
