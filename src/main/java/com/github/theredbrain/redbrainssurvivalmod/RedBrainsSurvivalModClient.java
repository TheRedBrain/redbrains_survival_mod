package com.github.theredbrain.redbrainssurvivalmod;

import com.github.theredbrain.redbrainssurvivalmod.client.render.block.entity.PlacedToolRenderer;
import com.github.theredbrain.redbrainssurvivalmod.client.screen.SoulforgeScreen;
import com.github.theredbrain.redbrainssurvivalmod.item.ColouredHandicraftItem;
import com.github.theredbrain.redbrainssurvivalmod.item.ColouredItem;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ScreenHandlerTypesRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class RedBrainsSurvivalModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerBlockColors();
        registerItemColors();
        registerRenderer();
        BlocksRegistry.registerRenderLayer();
        registerScreens();
//        registerSeasons();
//        registerTransparency();
    }

    private void registerBlockColors() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 14188339, BlocksRegistry.WET_CLAY_BRICK.get());

        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : -1, BlocksRegistry.SUGAR_CANE_ROOT.get());

//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.ACACIA_STUMP.get());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.BIRCH_STUMP.get());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.DARK_OAK_STUMP.get());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.JUNGLE_STUMP.get());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.OAK_STUMP.get());
//        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.SPRUCE_STUMP.get());

        ColorProviderRegistry.BLOCK.register(((state, view, pos, tintIndex) -> view != null && pos != null ? BiomeColors.getGrassColor(view, pos) : GrassColors.getColor(0.5D, 1.0D)),
                BlocksRegistry.GRASS_BLOCK.get(), BlocksRegistry.GRASS_SLAB.get(), BlocksRegistry.GRASS_BLOCK_SPARSE.get(), BlocksRegistry.GRASS_SLAB_SPARSE.get(),
                BlocksRegistry.GRASS.get(), BlocksRegistry.FERN.get(), BlocksRegistry.TALL_GRASS.get(), BlocksRegistry.LARGE_FERN.get());
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor()), BlocksRegistry.SPRUCE_LEAVES.get());
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) -> FoliageColors.getBirchColor()), BlocksRegistry.BIRCH_LEAVES.get());

        // TODO when custom vines -> add here
        ColorProviderRegistry.BLOCK.register(((state, world, pos, tintIndex) ->  world == null || pos == null ? FoliageColors.getDefaultColor() : BiomeColors.getFoliageColor(world, pos)),
                BlocksRegistry.OAK_LEAVES.get(), BlocksRegistry.JUNGLE_LEAVES.get(), BlocksRegistry.ACACIA_LEAVES.get(),
                BlocksRegistry.DARK_OAK_LEAVES.get(), BlocksRegistry.MANGROVE_LEAVES.get());
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> 14731036, BlocksRegistry.ATTACHED_MELON_STEM.get(), BlocksRegistry.ATTACHED_PUMPKIN_STEM.get());
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            int i = state.get(StemBlock.AGE);
            int j = i * 32;
            int k = 255 - i * 8;
            int l = i * 4;
            return j << 16 | k << 8 | l;
        }, BlocksRegistry.MELON_STEM.get(), BlocksRegistry.PUMPKIN_STEM.get());
    }

    private void registerItemColors() {
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.ACACIA_STUMP.get());
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.BIRCH_STUMP.get());
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.DARK_OAK_STUMP.get());
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.JUNGLE_STUMP.get());
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.OAK_STUMP.get());
//        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D), BlocksRegistry.SPRUCE_STUMP.get());

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D),
                BlocksRegistry.GRASS_BLOCK.get(), BlocksRegistry.GRASS_SLAB.get(), BlocksRegistry.GRASS_BLOCK_SPARSE.get(), BlocksRegistry.GRASS_SLAB_SPARSE.get(),
                BlocksRegistry.GRASS.get(), BlocksRegistry.FERN.get(), BlocksRegistry.TALL_GRASS.get(), BlocksRegistry.LARGE_FERN.get(),
                BlocksRegistry.OAK_LEAVES.get(), BlocksRegistry.JUNGLE_LEAVES.get(), BlocksRegistry.ACACIA_LEAVES.get(),
                BlocksRegistry.DARK_OAK_LEAVES.get(), BlocksRegistry.MANGROVE_LEAVES.get());
        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> FoliageColors.getSpruceColor()), BlocksRegistry.SPRUCE_LEAVES.get());
        ColorProviderRegistry.ITEM.register(((stack, tintIndex) -> FoliageColors.getBirchColor()), BlocksRegistry.BIRCH_LEAVES.get());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((ColouredItem)stack.getItem()).getColour(),
                ItemsRegistry.WOOL_KNIT_WHITE.get(), ItemsRegistry.WOOL_KNIT_ORANGE.get(), ItemsRegistry.WOOL_KNIT_MAGENTA.get(), ItemsRegistry.WOOL_KNIT_LIGHT_BLUE.get(),
                ItemsRegistry.WOOL_KNIT_YELLOW.get(), ItemsRegistry.WOOL_KNIT_LIME.get(), ItemsRegistry.WOOL_KNIT_PINK.get(), ItemsRegistry.WOOL_KNIT_GRAY.get(),
                ItemsRegistry.WOOL_KNIT_LIGHT_GRAY.get(), ItemsRegistry.WOOL_KNIT_CYAN.get(), ItemsRegistry.WOOL_KNIT_PURPLE.get(), ItemsRegistry.WOOL_KNIT_BLUE.get(),
                ItemsRegistry.WOOL_KNIT_BROWN.get(), ItemsRegistry.WOOL_KNIT_GREEN.get(), ItemsRegistry.WOOL_KNIT_RED.get(), ItemsRegistry.WOOL_KNIT_BLACK.get(),
                ItemsRegistry.WOOL_WHITE.get(), ItemsRegistry.WOOL_ORANGE.get(), ItemsRegistry.WOOL_MAGENTA.get(), ItemsRegistry.WOOL_LIGHT_BLUE.get(),
                ItemsRegistry.WOOL_YELLOW.get(), ItemsRegistry.WOOL_LIME.get(), ItemsRegistry.WOOL_PINK.get(), ItemsRegistry.WOOL_GRAY.get(),
                ItemsRegistry.WOOL_LIGHT_GRAY.get(), ItemsRegistry.WOOL_CYAN.get(), ItemsRegistry.WOOL_PURPLE.get(), ItemsRegistry.WOOL_BLUE.get(),
                ItemsRegistry.WOOL_BROWN.get(), ItemsRegistry.WOOL_GREEN.get(), ItemsRegistry.WOOL_RED.get(), ItemsRegistry.WOOL_BLACK.get());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((ColouredHandicraftItem)stack.getItem()).getColour(),
                ItemsRegistry.KNITTING_WHITE.get(), ItemsRegistry.KNITTING_ORANGE.get(), ItemsRegistry.KNITTING_MAGENTA.get(), ItemsRegistry.KNITTING_LIGHT_BLUE.get(),
                ItemsRegistry.KNITTING_YELLOW.get(), ItemsRegistry.KNITTING_LIME.get(), ItemsRegistry.KNITTING_PINK.get(), ItemsRegistry.KNITTING_GRAY.get(),
                ItemsRegistry.KNITTING_LIGHT_GRAY.get(), ItemsRegistry.KNITTING_CYAN.get(), ItemsRegistry.KNITTING_PURPLE.get(), ItemsRegistry.KNITTING_BLUE.get(),
                ItemsRegistry.KNITTING_BROWN.get(), ItemsRegistry.KNITTING_GREEN.get(), ItemsRegistry.KNITTING_RED.get(), ItemsRegistry.KNITTING_BLACK.get());
    }

    private void registerRenderer() {
        BlockEntityRendererRegistry.register(EntitiesRegistry.PLACED_TOOL_ENTITY, PlacedToolRenderer::new);
//        BlockEntityRendererRegistry.register(EntitiesRegistry.TRIGGERED_ENTITY, TriggeredBlockRenderer::new);
//        BlockEntityRendererRegistry.register(EntitiesRegistry.CUTTING_BOARD_ENTITY, CuttingBoardBlockEntityRenderer::new);
    }

    private void registerScreens() {
//        HandledScreens.<TriggeredBlockScreenHandler, TriggeredBlockScreen>register(ExtendedScreenTypesRegistry.TRIGGERED_SCREEN_HANDLER, (gui, inventory, title) -> new TriggeredBlockScreen(gui, inventory.player, title));
        HandledScreens.register(ScreenHandlerTypesRegistry.SOULFORGE_SCREEN_HANDLER, SoulforgeScreen::new);
    }
//
//    private void registerSeasons() {
//        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SeasonGrassColormapResourceSupplier());
//        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SeasonFoliageColormapResourceSupplier());
//
//        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
//            SeasonsRegistry.SEEDS_MAP.clear();
//            Registry.ITEM.forEach(item -> {
//                if(item instanceof BlockItem) {
//                    Block block = ((BlockItem) item).getBlock();
//                    if(block instanceof CropBlock || block instanceof StemBlock || block instanceof CocoaBlock || block instanceof SaplingBlock) {
//                        SeasonsRegistry.SEEDS_MAP.put(item, ((BlockItem) item).getBlock());
//                    }
//                }
//            });
//        });
//
//        ClientTickEvents.END_WORLD_TICK.register((clientWorld) -> {
//            if(SeasonsRegistry.getCurrentSeason(clientWorld) != lastRenderedSeasonMap.get(clientWorld.getRegistryKey())) {
//                lastRenderedSeasonMap.put(clientWorld.getRegistryKey(), SeasonsRegistry.getCurrentSeason(clientWorld));
//                MinecraftClient.getInstance().worldRenderer.reload();
//            }
//        });
//    }
//
//    private void registerTransparency() {
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CACTUS_ROOT, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.SUGAR_CANE_ROOT, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.BROWN_MUSHROOM_COLONY, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.RED_MUSHROOM_COLONY, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_CABBAGES, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_ONIONS, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_TOMATOES, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_CARROTS, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_POTATOES, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_BEETROOTS, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WILD_RICE, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CABBAGE_CROP, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.ONION_CROP, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.TOMATO_CROP, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.RICE_CROP, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.RICE_UPPER_CROP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WEED, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CUSTOM_BEETROOTS, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CUSTOM_CARROTS, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CUSTOM_POTATOES, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CUSTOM_WHEAT, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WHEAT_UPPER_CROP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.BROWN_MUSHROOM, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.RED_MUSHROOM, RenderLayer.getCutout());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.WET_CLAY_BRICK, RenderLayer.getCutout());
//
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.ROAST_CHICKEN, RenderLayer.getCutout());
////
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.ROPE, RenderLayer.getCutout());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.NETHERITE_CAULDRON, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.NETHERITE_LAVA_CAULDRON, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.COOKING_POT, RenderLayer.getCutoutMipped());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.BASKET, RenderLayer.getCutoutMipped());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.CUTTING_BOARD, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.ACACIA_STUMP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.BIRCH_STUMP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.DARK_OAK_STUMP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.JUNGLE_STUMP, RenderLayer.getCutout());
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.OAK_STUMP, RenderLayer.getCutout());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.GRASS_SLAB, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.PLACED_TOOL, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.TRIGGERED, RenderLayer.getCutout());
////        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.STRAW_BALE, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.STONE_CORNER, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.STONE_EDGE, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.STONE_SIDING, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.STONE_STAIR, RenderLayer.getCutoutMipped());
//
//        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.MILLSTONE, RenderLayer.getCutoutMipped());
//    }
}
