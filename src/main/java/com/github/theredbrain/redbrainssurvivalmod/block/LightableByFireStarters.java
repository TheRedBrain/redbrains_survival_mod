package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface LightableByFireStarters {
    boolean tryLighting(World world, BlockState blockState, BlockPos blockPos);
}
