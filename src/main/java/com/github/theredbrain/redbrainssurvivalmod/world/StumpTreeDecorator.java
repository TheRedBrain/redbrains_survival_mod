package com.github.theredbrain.redbrainssurvivalmod.world;

import com.github.theredbrain.redbrainssurvivalmod.registry.TreeDecoratorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class StumpTreeDecorator extends TreeDecorator {

    public static final Codec<StumpTreeDecorator> CODEC;
    private final BlockStateProvider provider;

    public StumpTreeDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorTypes.STUMP;
    }

    @Override
    public void generate(Generator generator) {

        List<BlockPos> list = generator.getLogPositions();
        generator.replace((BlockPos)list.get(1), provider.get(generator.getRandom(), (BlockPos)list.get(1)));
    }

    static {
        CODEC = BlockStateProvider.TYPE_CODEC.fieldOf("stump_provider").xmap(StumpTreeDecorator::new, (decorator) -> decorator.provider).codec();
    }
}
