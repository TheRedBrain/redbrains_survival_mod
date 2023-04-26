package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class AxleWithVerticalWindMillBlock extends AbstractAxleWithPowerSourceBlock{
    public AxleWithVerticalWindMillBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
