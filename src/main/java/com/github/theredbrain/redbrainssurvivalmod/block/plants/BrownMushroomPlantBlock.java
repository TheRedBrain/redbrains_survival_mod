package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class BrownMushroomPlantBlock extends MushroomPlantBlock {

    public BrownMushroomPlantBlock(Settings settings, RegistryKey<ConfiguredFeature<?, ?>> featureKey) {
        super(settings, featureKey);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.MYCELIUM)) {
            return true;
        } else {
            return world.getBaseLightLevel(pos, 0) == 0 && this.canPlantOnTop(blockState, world, blockPos);
        }
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)random.nextFloat() < 0.4 && canPlaceAt(state, world, pos);
    }
}
