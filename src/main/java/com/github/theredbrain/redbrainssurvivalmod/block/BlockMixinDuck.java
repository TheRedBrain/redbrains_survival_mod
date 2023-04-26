package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockMixinDuck {
    boolean hasPyramid(World world, BlockPos pos, Block pyramidMaterial);
}
