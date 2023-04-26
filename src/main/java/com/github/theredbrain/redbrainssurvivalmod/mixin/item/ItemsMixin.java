package com.github.theredbrain.redbrainssurvivalmod.mixin.item;
//
//import com.github.theredbrain.redbrainssurvivalmod.item.CustomToolMaterials;
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.Block;
//import net.minecraft.item.*;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//import org.spongepowered.asm.mixin.injection.Slice;
//
//@Mixin(Items.class)
//public class ItemsMixin {
//
//    // TODO reduce stack size of all vanilla food items
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=brick")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedBrick(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.BRICK.get(), settings);
////    }
////
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=nether_brick")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedNetherBrick(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.NETHER_BRICK.get(), settings);
////    }
////
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_ingot")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedIronIngot(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.IRON_INGOT.get(), settings);
////    }
////
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=copper_ingot")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedCopperIngot(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.COPPER_INGOT.get(), settings);
////    }
////
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=gold_ingot")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedGoldIngot(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.GOLD_INGOT.get(), settings);
////    }
////
////    @Redirect(method = "<clinit>",
////            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_ingot")),
////            at = @At(value = "NEW",
////                    target = "net/minecraft/item/Item",
////                    ordinal = 0
////            )
////    )
////    private static Item redirectedNetheriteIngot(Item.Settings settings) {
////        return new AliasedBlockItem(BlocksRegistry.NETHERITE_INGOT.get(), settings.fireproof());
////    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=beetroot_seeds")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AliasedBlockItem",
//                    ordinal = 0
//            )
//    )
//    private static AliasedBlockItem redirectedBeetrootSeeds(Block block, Item.Settings settings) {
//        return new AliasedBlockItem(BlocksRegistry.CUSTOM_BEETROOTS.get(), settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=carrot")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AliasedBlockItem",
//                    ordinal = 0
//            )
//    )
//    private static AliasedBlockItem redirectedCarrot(Block block, Item.Settings settings) {
//        return new AliasedBlockItem(BlocksRegistry.CUSTOM_CARROTS.get(), settings.food(FoodComponents.CARROT));
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=potato")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AliasedBlockItem",
//                    ordinal = 0
//            )
//    )
//    private static AliasedBlockItem redirectedPotato(Block block, Item.Settings settings) {
//        return new AliasedBlockItem(BlocksRegistry.CUSTOM_POTATOES.get(), settings.food(FoodComponents.POTATO));
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=wheat_seeds")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AliasedBlockItem",
//                    ordinal = 0
//            )
//    )
//    private static AliasedBlockItem redirectedWheatSeeds(Block block, Item.Settings settings) {
//        return new AliasedBlockItem(BlocksRegistry.CUSTOM_WHEAT.get(), settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=stone_shovel")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/ShovelItem",
//                    ordinal = 0
//            )
//    )
//    private static ShovelItem redirectedStoneShovel(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new ShovelItem(CustomToolMaterials.STONE, 2.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=stone_pickaxe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/PickaxeItem",
//                    ordinal = 0
//            )
//    )
//    private static PickaxeItem redirectedStonePickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new PickaxeItem(CustomToolMaterials.STONE, 3, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=stone_axe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AxeItem",
//                    ordinal = 0
//            )
//    )
//    private static AxeItem redirectedStoneAxe(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new AxeItem(CustomToolMaterials.STONE, 0.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=golden_sword")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/SwordItem",
//                    ordinal = 0
//            )
//    )
//    private static SwordItem redirectedGoldenSword(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new SwordItem(CustomToolMaterials.GOLD, 4, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=golden_shovel")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/ShovelItem",
//                    ordinal = 0
//            )
//    )
//    private static ShovelItem redirectedGoldenShovel(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new ShovelItem(CustomToolMaterials.GOLD, 1.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=golden_pickaxe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/PickaxeItem",
//                    ordinal = 0
//            )
//    )
//    private static PickaxeItem redirectedGoldenPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new PickaxeItem(CustomToolMaterials.GOLD, 2, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=golden_axe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AxeItem",
//                    ordinal = 0
//            )
//    )
//    private static AxeItem redirectedGoldenAxe(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new AxeItem(CustomToolMaterials.GOLD, 0.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=golden_hoe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/HoeItem",
//                    ordinal = 0
//            )
//    )
//    private static HoeItem redirectedGoldenHoe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new HoeItem(CustomToolMaterials.GOLD, 1, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_sword")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/SwordItem",
//                    ordinal = 0
//            )
//    )
//    private static SwordItem redirectedIronSword(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new SwordItem(CustomToolMaterials.IRON, 6, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_shovel")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/ShovelItem",
//                    ordinal = 0
//            )
//    )
//    private static ShovelItem redirectedIronShovel(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new ShovelItem(CustomToolMaterials.IRON, 3.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_pickaxe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/PickaxeItem",
//                    ordinal = 0
//            )
//    )
//    private static PickaxeItem redirectedIronPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new PickaxeItem(CustomToolMaterials.IRON, 4, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_axe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AxeItem",
//                    ordinal = 0
//            )
//    )
//    private static AxeItem redirectedIronAxe(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new AxeItem(CustomToolMaterials.IRON, 0.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=iron_hoe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/HoeItem",
//                    ordinal = 0
//            )
//    )
//    private static HoeItem redirectedIronHoe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new HoeItem(CustomToolMaterials.IRON, 3, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=diamond_sword")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/SwordItem",
//                    ordinal = 0
//            )
//    )
//    private static SwordItem redirectedDiamondSword(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new SwordItem(CustomToolMaterials.DIAMOND, 7, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=diamond_shovel")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/ShovelItem",
//                    ordinal = 0
//            )
//    )
//    private static ShovelItem redirectedDiamondShovel(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new ShovelItem(CustomToolMaterials.DIAMOND, 4.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=diamond_pickaxe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/PickaxeItem",
//                    ordinal = 0
//            )
//    )
//    private static PickaxeItem redirectedDiamondPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new PickaxeItem(CustomToolMaterials.DIAMOND, 5, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=diamond_axe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AxeItem",
//                    ordinal = 0
//            )
//    )
//    private static AxeItem redirectedDiamondAxe(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new AxeItem(CustomToolMaterials.DIAMOND, 0.0F, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=diamond_hoe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/HoeItem",
//                    ordinal = 0
//            )
//    )
//    private static HoeItem redirectedDiamondHoe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new HoeItem(CustomToolMaterials.DIAMOND, 4, -3.0F, settings);
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_sword")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/SwordItem",
//                    ordinal = 0
//            )
//    )
//    private static SwordItem redirectedNetheriteSword(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new SwordItem(CustomToolMaterials.NETHERITE, 8, -3.0F, settings.fireproof());
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_shovel")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/ShovelItem",
//                    ordinal = 0
//            )
//    )
//    private static ShovelItem redirectedNetheriteShovel(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new ShovelItem(CustomToolMaterials.NETHERITE, 5.0F, -3.0F, settings.fireproof());
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_pickaxe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/PickaxeItem",
//                    ordinal = 0
//            )
//    )
//    private static PickaxeItem redirectedNetheritePickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new PickaxeItem(CustomToolMaterials.NETHERITE, 6, -3.0F, settings.fireproof());
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_axe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/AxeItem",
//                    ordinal = 0
//            )
//    )
//    private static AxeItem redirectedNetheriteAxe(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
//        return new AxeItem(CustomToolMaterials.NETHERITE, 7.0F, -3.0F, settings.fireproof());
//    }
//
//    @Redirect(method = "<clinit>",
//            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=netherite_hoe")),
//            at = @At(value = "NEW",
//                    target = "net/minecraft/item/HoeItem",
//                    ordinal = 0
//            )
//    )
//    private static HoeItem redirectedNetheriteHoe(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
//        return new HoeItem(CustomToolMaterials.NETHERITE, 5, -3.0F, settings.fireproof());
//    }
//}
