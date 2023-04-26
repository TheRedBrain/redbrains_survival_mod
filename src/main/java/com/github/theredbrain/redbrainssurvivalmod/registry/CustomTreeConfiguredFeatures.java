package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.world.DarkOakStumpTreeDecorator;
import com.github.theredbrain.redbrainssurvivalmod.world.GiantStumpTreeDecorator;
import com.github.theredbrain.redbrainssurvivalmod.world.StumpTreeDecorator;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.*;

import java.util.List;
import java.util.OptionalInt;

public class CustomTreeConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_OAK = ConfiguredFeatures.of("custom_oak");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_DARK_OAK = ConfiguredFeatures.of("custom_dark_oak");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_BIRCH = ConfiguredFeatures.of("custom_birch");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_ACACIA = ConfiguredFeatures.of("custom_acacia");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_SPRUCE = ConfiguredFeatures.of("custom_spruce");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_FANCY_OAK = ConfiguredFeatures.of("custom_fancy_oak");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_JUNGLE_TREE_NO_VINE = ConfiguredFeatures.of("custom_jungle_tree_no_vine");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_MEGA_JUNGLE_TREE = ConfiguredFeatures.of("custom_mega_jungle_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_MEGA_SPRUCE = ConfiguredFeatures.of("custom_mega_spruce");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_MEGA_PINE = ConfiguredFeatures.of("custom_mega_pine");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_AZALEA_TREE = ConfiguredFeatures.of("custom_azalea_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_OAK_BEES_005 = ConfiguredFeatures.of("custom_oak_bees_005");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_BIRCH_BEES_005 = ConfiguredFeatures.of("custom_birch_bees_005");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CUSTOM_FANCY_OAK_BEES_005 = ConfiguredFeatures.of("custom_fancy_oak_bees_005");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_OAK, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.OAK_LOG.get()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.of(BlocksRegistry.OAK_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .decorators(List.of(new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.OAK_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_DARK_OAK, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.DARK_OAK_LOG.get()),
                        new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.of(BlocksRegistry.DARK_OAK_LEAVES.get()),
                        new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                        new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())))
                .ignoreVines()
                .decorators(List.of(new DarkOakStumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.DARK_OAK_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_BIRCH, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.BIRCH_LOG.get()),
                        new StraightTrunkPlacer(5, 2, 0), BlockStateProvider.of(BlocksRegistry.BIRCH_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .decorators(List.of(new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.BIRCH_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_ACACIA, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.ACACIA_LOG.get()),
                        new ForkingTrunkPlacer(5, 2, 2), BlockStateProvider.of(BlocksRegistry.ACACIA_LEAVES.get()),
                        new AcaciaFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                        new TwoLayersFeatureSize(1, 0, 2)))
                .ignoreVines()
                .decorators(List.of(
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.ACACIA_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_SPRUCE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.SPRUCE_LOG.get()),
                        new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.of(BlocksRegistry.SPRUCE_LEAVES.get()),
                        new SpruceFoliagePlacer(UniformIntProvider.create(2, 3), UniformIntProvider.create(0, 2), UniformIntProvider.create(1, 2)),
                        new TwoLayersFeatureSize(2, 0, 2))).ignoreVines()
                .decorators(List.of(
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.SPRUCE_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_FANCY_OAK, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.OAK_LOG.get()),
                        new LargeOakTrunkPlacer(3, 11, 0), BlockStateProvider.of(BlocksRegistry.OAK_LEAVES.get()),
                        new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines()
                .decorators(List.of(
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.OAK_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_JUNGLE_TREE_NO_VINE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.JUNGLE_LOG.get()),
                        new StraightTrunkPlacer(4, 8, 0), BlockStateProvider.of(BlocksRegistry.JUNGLE_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .ignoreVines()
                .decorators(List.of(
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.JUNGLE_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_MEGA_JUNGLE_TREE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.JUNGLE_LOG.get()),
                        new MegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.of(BlocksRegistry.JUNGLE_LEAVES.get()),
                        new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
                        new TwoLayersFeatureSize(1, 1, 2)))
                .decorators(ImmutableList.of(
                        TrunkVineTreeDecorator.INSTANCE, new LeavesVineTreeDecorator(0.25F),
                        new GiantStumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.JUNGLE_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_MEGA_SPRUCE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.SPRUCE_LOG.get()),
                        new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.of(BlocksRegistry.SPRUCE_LEAVES.get()),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                .decorators(ImmutableList.of(
                        new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)),
                        new GiantStumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.SPRUCE_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_MEGA_PINE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.SPRUCE_LOG.get()),
                        new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.of(BlocksRegistry.SPRUCE_LEAVES.get()),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(3, 7)),
                        new TwoLayersFeatureSize(1, 1, 2)))
                .decorators(ImmutableList.of(
                        new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)),
                        new GiantStumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.SPRUCE_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_AZALEA_TREE, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.OAK_LOG.get()),
                        new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)),
                        new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.AZALEA_LEAVES.get().getDefaultState(), 3).add(BlocksRegistry.FLOWERING_AZALEA_LEAVES.get().getDefaultState(), 1)),
                        new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .decorators(List.of(
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.OAK_STUMP.get()))))
                .dirtProvider(BlockStateProvider.of(BlocksRegistry.ROOTED_DIRT.get()))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_OAK_BEES_005, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.OAK_LOG.get()),
                        new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.of(BlocksRegistry.OAK_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .decorators(List.of(
                        new BeehiveTreeDecorator(0.05F),
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.OAK_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_BIRCH_BEES_005, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.BIRCH_LOG.get()),
                        new StraightTrunkPlacer(5, 2, 0), BlockStateProvider.of(BlocksRegistry.BIRCH_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)))
                .decorators(List.of(
                        new BeehiveTreeDecorator(0.05F),
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.BIRCH_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
        ConfiguredFeatures.register(featureRegisterable, CUSTOM_FANCY_OAK_BEES_005, Feature.TREE, (
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(BlocksRegistry.OAK_LOG.get()),
                        new LargeOakTrunkPlacer(3, 11, 0), BlockStateProvider.of(BlocksRegistry.OAK_LEAVES.get()),
                        new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))))
                .ignoreVines()
                .decorators(List.of(
                        new BeehiveTreeDecorator(0.05F),
                        new StumpTreeDecorator(BlockStateProvider.of(BlocksRegistry.OAK_STUMP.get()))))
                .dirtProvider(new WeightedBlockStateProvider(new DataPool.Builder().add(BlocksRegistry.DIRT.get().getDefaultState(), 3).add(BlocksRegistry.ROOTED_DIRT.get().getDefaultState(), 1)))
                .forceDirt()
                .build());
    }
}
