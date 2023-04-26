package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntitiesRegistry {

    public static final BlockEntityType<PlacedToolEntity> PLACED_TOOL_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "placed_tool"), FabricBlockEntityTypeBuilder.create(PlacedToolEntity::new, BlocksRegistry.PLACED_TOOL.get()).build(null));
    // TODO overhaul cooking pot
//    public static final BlockEntityType<CookingPotBlockEntity> COOKING_POT_ENTITY = FabricBlockEntityTypeBuilder.create(CookingPotBlockEntity::new, BlocksRegistry.COOKING_POT).build(null);
//    public static final BlockEntityType<BasketBlockEntity> BASKET_ENTITY = FabricBlockEntityTypeBuilder.create(BasketBlockEntity::new, BlocksRegistry.BASKET).build(null);
//    public static final BlockEntityType<CuttingBoardBlockEntity> CUTTING_BOARD_ENTITY = FabricBlockEntityTypeBuilder.create(CuttingBoardBlockEntity::new, BlocksRegistry.CUTTING_BOARD).build(null);
//    public static final BlockEntityType<PantryBlockEntity> PANTRY_ENTITY = FabricBlockEntityTypeBuilder.create(PantryBlockEntity::new, BlocksRegistry.OAK_PANTRY).addBlock(BlocksRegistry.BIRCH_PANTRY).addBlock(BlocksRegistry.SPRUCE_PANTRY).addBlock(BlocksRegistry.JUNGLE_PANTRY).addBlock(BlocksRegistry.ACACIA_PANTRY).addBlock(BlocksRegistry.DARK_OAK_PANTRY).build(null);
    public static final BlockEntityType<MillstoneBlockEntity> MILLSTONE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "millstone"), FabricBlockEntityTypeBuilder.create(MillstoneBlockEntity::new, BlocksRegistry.MILLSTONE.get()).build(null));
    public static final BlockEntityType<BrickOvenEntity> BRICK_OVEN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "brick_oven"), FabricBlockEntityTypeBuilder.create(BrickOvenEntity::new, BlocksRegistry.BRICK_OVEN.get()).build(null));
    public static final BlockEntityType<CauldronEntity> CAULDRON_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "cauldron"), FabricBlockEntityTypeBuilder.create(CauldronEntity::new, BlocksRegistry.CAULDRON.get()).build(null));
    public static final BlockEntityType<CrucibleEntity> CRUCIBLE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "crucible"), FabricBlockEntityTypeBuilder.create(CrucibleEntity::new, BlocksRegistry.CRUCIBLE.get()).build(null));
    public static final BlockEntityType<SoulforgeBlockEntity> SOULFORGE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "soulforge"), FabricBlockEntityTypeBuilder.create(SoulforgeBlockEntity::new, BlocksRegistry.SOULFORGE.get()).build(null));
    public static final BlockEntityType<HibachiBlockEntity> HIBACHI_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "hibachi"), FabricBlockEntityTypeBuilder.create(HibachiBlockEntity::new, BlocksRegistry.HIBACHI.get()).build(null));
    public static final BlockEntityType<SawBlockEntity> SAW_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "saw"), FabricBlockEntityTypeBuilder.create(SawBlockEntity::new, BlocksRegistry.SAW.get()).build(null));
    public static final BlockEntityType<CrudeTorchBlockEntity> CRUDE_TORCH_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "crude_torch"), FabricBlockEntityTypeBuilder.create(CrudeTorchBlockEntity::new, BlocksRegistry.CRUDE_TORCH.get()).build(null));
    public static final BlockEntityType<VaseBlockEntity> VASE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "vase"), FabricBlockEntityTypeBuilder.create(VaseBlockEntity::new, BlocksRegistry.VASE_PLAIN.get(), BlocksRegistry.VASE_WHITE.get(), BlocksRegistry.VASE_ORANGE.get(), BlocksRegistry.VASE_MAGENTA.get(), BlocksRegistry.VASE_LIGHT_BLUE.get(), BlocksRegistry.VASE_YELLOW.get(), BlocksRegistry.VASE_LIME.get(), BlocksRegistry.VASE_PINK.get(), BlocksRegistry.VASE_GRAY.get(), BlocksRegistry.VASE_LIGHT_GRAY.get(), BlocksRegistry.VASE_CYAN.get(), BlocksRegistry.VASE_PURPLE.get(), BlocksRegistry.VASE_BLUE.get(), BlocksRegistry.VASE_BROWN.get(), BlocksRegistry.VASE_GREEN.get(), BlocksRegistry.VASE_RED.get(), BlocksRegistry.VASE_BLACK.get()).build(null));

//    private static BlockEntity registerBlockEntity(String id, Entity entry) {
//        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, id), PLACED_TOOL_ENTITY);
//    }
//
//    public static void registerBlockEntities() {
//        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "placed_tool"), PLACED_TOOL_ENTITY);
////        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "cooking_pot"), COOKING_POT_ENTITY);
////        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "basket"), BASKET_ENTITY);
////        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "cutting_board"), CUTTING_BOARD_ENTITY);
////        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "pantry"), PANTRY_ENTITY);
//        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "millstone"), MILLSTONE_ENTITY);
////        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "triggered_block"), TRIGGERED_BLOCK_ENTITY);
//    }
}
