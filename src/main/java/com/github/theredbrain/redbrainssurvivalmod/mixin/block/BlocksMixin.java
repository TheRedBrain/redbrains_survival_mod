package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
/*

import com.github.theredbrain.redbrainssurvivalmod.block.CustomDirtBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.CustomLogBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.CustomStoneBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.CustomStrippedLogBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.plants.CustomFernBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.plants.CustomTallPlantBlock;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Shadow
    @Final
    @Mutable
    public static final Block STONE = (Block) MutableRegistry.set(Registries.BLOCK, "stone", new CustomStoneBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6.0f).velocityMultiplier(2.0f*/
/*Constants.VELOCITY_MULTIPLYER_1_1*//*
)));


//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 0
//            )
//    )
//    private static Block redirectedStone(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 1
//            )
//    )
//    private static Block redirectedGranite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 2
//            )
//    )
//    private static Block redirectedPolishedGranite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 3
//            )
//    )
//    private static Block redirectedDiorite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 4
//            )
//    )
//    private static Block redirectedPolishedDiorite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 5
//            )
//    )
//    private static Block redirectedAndesite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 6
//            )
//    )
//    private static Block redirectedPolishedAndesite(Block.Settings settings) {
//        return new CustomStoneBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/Block",
//                    ordinal = 7
//            )
//    )
//    private static Block redirectedDirt(Block.Settings settings) {
//        return new CustomDirtBlock(settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 0
//            )
//    )
//    private static ExperienceDroppingBlock redirectedGoldOre(Block.Settings settings) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 1
//            )
//    )
//    private static ExperienceDroppingBlock redirectedDeepslateGoldOre(Block.Settings settings) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 2
//            )
//    )
//    private static ExperienceDroppingBlock redirectedIronOre(Block.Settings settings) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 3
//            )
//    )
//    private static ExperienceDroppingBlock redirectedDeepslateIronOre(Block.Settings settings) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 4
//            )
//    )
//    private static ExperienceDroppingBlock redirectedCoalOre(AbstractBlock.Settings settings, IntProvider experience) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/ExperienceDroppingBlock",
//                    ordinal = 5
//            )
//    )
//    private static ExperienceDroppingBlock redirectedDeepslateCoalOre(AbstractBlock.Settings settings, IntProvider experience) {
//        return new ExperienceDroppingBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @ModifyConstant(method = "<clinit>",
//            constant = @Constant(
//                    classValue = Block.class,
//                    ordinal = 46
//            )
//    )
//    private static Block redirectedOakLog(Class constant) {
//        return new CustomLogBlock(Blocks.STRIPPED_OAK_LOG, FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.OAK_TAN : MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD));
//    }
//
////    @Redirect(method = "<clinit>",
////            at = @At(value = "INVOKE",
////                    target = "Lnet/minecraft/block/Blocks;createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;)Lnet/minecraft/block/Pillarblock;",
////                    ordinal = 0
////            )
////    )
////    private static PillarBlock redirectedOakLog(AbstractBlock.Settings settings) {
////        return new CustomLogBlock(Blocks.STRIPPED_OAK_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
////    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 1
//            )
//    )
//    private static PillarBlock redirectedSpruceLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_SPRUCE_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 2
//            )
//    )
//    private static PillarBlock redirectedBirchLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_BIRCH_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 3
//            )
//    )
//    private static PillarBlock redirectedJungleLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_JUNGLE_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 4
//            )
//    )
//    private static PillarBlock redirectedAcaciaLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_ACACIA_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 5
//            )
//    )
//    private static PillarBlock redirectedCherryLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_CHERRY_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 6
//            )
//    )
//    private static PillarBlock redirectedDarkOakLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_DARK_OAK_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 7
//            )
//    )
//    private static PillarBlock redirectedMangroveLog(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_MANGROVE_LOG, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 10
//            )
//    )
//    private static PillarBlock redirectedStrippedSpruceLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 11
//            )
//    )
//    private static PillarBlock redirectedStrippedBirchLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 12
//            )
//    )
//    private static PillarBlock redirectedStrippedJungleLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 13
//            )
//    )
//    private static PillarBlock redirectedStrippedAcaciaLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 14
//            )
//    )
//    private static PillarBlock redirectedStrippedCherryLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 15
//            )
//    )
//    private static PillarBlock redirectedStrippedDarkOakLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 16
//            )
//    )
//    private static PillarBlock redirectedStrippedOakLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 17
//            )
//    )
//    private static PillarBlock redirectedStrippedMangroveLog(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 19
//            )
//    )
//
//    private static PillarBlock redirectedOakWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_OAK_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 20
//            )
//    )
//    private static PillarBlock redirectedSpruceWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_SPRUCE_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 21
//            )
//    )
//    private static PillarBlock redirectedBirchWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_BIRCH_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 22
//            )
//    )
//    private static PillarBlock redirectedJungleWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_JUNGLE_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 23
//            )
//    )
//    private static PillarBlock redirectedAcaciaWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_ACACIA_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 24
//            )
//    )
//    private static PillarBlock redirectedCherryWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_CHERRY_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 25
//            )
//    )
//    private static PillarBlock redirectedDarkOakWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_DARK_OAK_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 26
//            )
//    )
//    private static PillarBlock redirectedMangroveWood(AbstractBlock.Settings settings) {
//        return new CustomLogBlock(Blocks.STRIPPED_MANGROVE_WOOD, settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 27
//            )
//    )
//    private static PillarBlock redirectedStrippedOakWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=stripped_spruce_wood")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 28
//            )
//    )
//    private static PillarBlock redirectedStrippedSpruceWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=stripped_birch_wood")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 29
//            )
//    )
//    private static PillarBlock redirectedStrippedBirchWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 30
//            )
//    )
//    private static PillarBlock redirectedStrippedJungleWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 31
//            )
//    )
//    private static PillarBlock redirectedStrippedAcaciaWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 32
//            )
//    )
//    private static PillarBlock redirectedStrippedCherryWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 33
//            )
//    )
//    private static PillarBlock redirectedStrippedDarkOakWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/PillarBlock",
//                    ordinal = 34
//            )
//    )
//    private static PillarBlock redirectedStrippedMangroveWood(AbstractBlock.Settings settings) {
//        return new CustomStrippedLogBlock(settings.velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/FernBlock",
//                    ordinal = 0
//            )
//    )
//    private static FernBlock redirectedGrass(Block.Settings settings) {
//        return new CustomFernBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/FernBlock",
//                    ordinal = 1
//            )
//    )
//    private static FernBlock redirectedFern(Block.Settings settings) {
//        return new CustomFernBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/TallPlantBlock",
//                    ordinal = 0
//            )
//    )
//    private static TallPlantBlock redirectedTallGrass(Block.Settings settings) {
//        return new CustomTallPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
//    }
//
//    @Redirect(method = "<clinit>",
//            at = @At(value = "NEW",
//                    target = "net/minecraft/block/TallPlantBlock",
//                    ordinal = 1
//            )
//    )
//    private static TallPlantBlock redirectedLargeFern(Block.Settings settings) {
//        return new CustomTallPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
//    }
//
//    @Redirect(
//            method = "<clinit>",
//            at = @At(
//                    value = "NEW",
//                    target = "net/minecraft/block/SnowyBlock",
//                    ordinal = 0
//            ),
//            slice = @Slice(
//                    from = @At(
//                            value = "CONSTANT",
//                            args = "stringValue=podzol"
//                    )
//            )
//    )
//    private static SnowyBlock podzol(Block.Settings settings) {
//        return new SoilBlock(settings);
//    }
}
*/
