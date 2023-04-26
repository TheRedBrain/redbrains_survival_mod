package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class CustomMelonBlock extends FallingGourdBlock {
    public CustomMelonBlock(Settings settings) {
        super(settings);
    }

    @Override
    public CustomStemBlock getStem() {
        return (CustomStemBlock) BlocksRegistry.MELON_STEM.get();
    }

    @Override
    public CustomAttachedStemBlock getAttachedStem() {
        return (CustomAttachedStemBlock) BlocksRegistry.ATTACHED_MELON_STEM.get();
    }

    // TODO custom event
    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(WorldEvents.ANVIL_LANDS, pos, 0);
        }
    }

    // TODO custom event
    @Override
    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, pos, 0);
        }
    }
}
