package com.github.theredbrain.redbrainssurvivalmod.world;

import com.github.theredbrain.redbrainssurvivalmod.registry.TreeDecoratorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class DarkOakStumpTreeDecorator extends TreeDecorator {

    public static final Codec<DarkOakStumpTreeDecorator> CODEC;
    private final BlockStateProvider provider;

    public DarkOakStumpTreeDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }
    @Override
    protected TreeDecoratorType<?> getType() {
        return TreeDecoratorTypes.DARK_OAK_STUMP;
    }

    @Override
    public void generate(Generator generator) {

        List<BlockPos> list = generator.getLogPositions();
        int stumpLevel = list.get(4).getY();
        int i = 4;
        while (list.get(i).getY() == stumpLevel) {
            generator.replace((BlockPos) list.get(i), provider.get(generator.getRandom(), (BlockPos) list.get(i)));
            i++;
        }
    }

    static {
        CODEC = BlockStateProvider.TYPE_CODEC.fieldOf("stump_provider").xmap(DarkOakStumpTreeDecorator::new, (decorator) -> decorator.provider).codec();
    }
}
