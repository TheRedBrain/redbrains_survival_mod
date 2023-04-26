package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;

public class LooseDirtSlabBlock extends FallingSlabBlock {
    // TODO find correct color
    public LooseDirtSlabBlock(Settings settings) {
        super(14406560,  settings);
    }

//    @Override // TODO
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        super.randomTick(state, world, pos, random);
//        if (LooseDirtBlock.turnsToMudOnAnySide(world, pos) && random.nextInt(5) == 0) {
//            world.setBlockState(pos, BlocksRegistry.MUD.get().getDefaultState(), Block.NOTIFY_ALL);
//        }
//    }
}
