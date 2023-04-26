package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.block.*;
import com.github.theredbrain.redbrainssurvivalmod.block.CauldronBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.plants.*;
import com.github.theredbrain.redbrainssurvivalmod.block.subblocks.CornerBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.subblocks.EdgeBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.subblocks.SidingBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.subblocks.StairBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.SoulforgeBlock;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.function.Supplier;

public enum BlocksRegistry {
    // mechanical power
    AXLE("axle", true, () -> new AxleBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    BELLOWS("bellows", true, () -> new BellowsBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOL))),
    CREATIVE_MECHANICAL_SOURCE("creative_mechanical_source", true, () -> new CreativeMechanicalSourceBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK))),
    GEAR_BOX("gear_box", true, () -> new GearBoxBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    REDSTONE_GEAR_BOX("redstone_gear_box", true, () -> new RedstoneGearBoxBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    HAND_CRANK("hand_crank", true, () -> new HandCrankBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE))),
    MILLSTONE("millstone", true, 2, () -> new MillstoneBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE))),
    TURNTABLE("turntable", true, 1, () -> new TurntableBlock(FabricBlockSettings.of(Material.STONE, MapColor.OAK_TAN).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE))),
    WATER_WHEEL("water_wheel", true, () -> new AxleWithWaterWheelBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    WIND_MILL("wind_mill", true, () -> new AxleWithWindMillBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    VERTICAL_WIND_MILL("vertical_wind_mill", true, () -> new AxleWithVerticalWindMillBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().hardness(2.f).resistance(2.f).sounds(BlockSoundGroup.WOOD))),
    SAW("saw", true, 1, () -> new SawBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 6.0F).sounds(BlockSoundGroup.WOOD))),
    CHOPPING_BLOCK("chopping_block", true, () -> new ChoppingBlock(false, FabricBlockSettings.of(Material.STONE).strength(2.0F, 6.0F))),
    CHOPPING_BLOCK_BLOODY("chopping_block_bloody", true, () -> new ChoppingBlock(true, FabricBlockSettings.of(Material.STONE).strength(2.0F, 6.0F))),

    // redstone blocks
    HIBACHI("hibachi", true, () -> new HibachiBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE))),
    HIBACHI_FIRE("hibachi_fire", false, 1, () -> new HibachiFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.BRIGHT_RED).noCollision().breakInstantly().luminance(state -> 15).sounds(BlockSoundGroup.WOOL))),

    // work station blocks
    ACACIA_CRAFTING_TABLE("acacia_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    BIRCH_CRAFTING_TABLE("birch_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    CHERRY_CRAFTING_TABLE("cherry_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    DARK_OAK_CRAFTING_TABLE("dark_oak_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    JUNGLE_CRAFTING_TABLE("jungle_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    OAK_CRAFTING_TABLE("oak_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    SPRUCE_CRAFTING_TABLE("spruce_crafting_table", true, () -> new CraftingTableBlock(FabricBlockSettings.of(Material.WOOD).requiresTool().strength(50.0F, 6.0F))),
    ANVIL("anvil", true, () -> new CustomAnvilBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(8.0F, 6.0F))),
    SOULFORGE("soulforge", true, () -> new SoulforgeBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(10.0F, 6.0F))),
    DORMANT_SOULFORGE("dormant_soulforge", true, () -> new DormantSoulforgeBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(10.0F, 6.0F))),

    BRICK_OVEN("brick_oven", true, 1, () -> new BrickOvenBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).luminance(state -> state.get(BrickOvenBlock.LIT) ? (state.get(BrickOvenBlock.FUEL) == 0 ? 7 : 13) : 0).strength(2.0F))),
    // TODO campfire

    CAULDRON("cauldron", true, () -> new CauldronBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(2.0F))),
    CRUCIBLE("crucible", true, () -> new CrucibleBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(2.0F))),
    PLANTER_EMPTY("planter_empty", true, () -> new PlanterBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(2.0F))),
    URN("urn", true, () -> new UrnBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(2.0F))),

//    NETHERITE_CAULDRON("netherite_cauldron", true, 1, () -> new NetheriteCauldronBlock(FabricBlockSettings.of(Material.METAL, MapColor.STONE_GRAY).requiresTool().strength(2.0F).nonOpaque())),
//    NETHERITE_LAVA_CAULDRON("netherite_lava_cauldron", false, 1, () -> new NetheriteLavaCauldronBlock(FabricBlockSettings.copy(NETHERITE_CAULDRON.get()).luminance((state) -> {
//        return 15;
//    }))),

    // food blocks
    OVEN_READY_PUMPKIN_PIE("oven_ready_pumpkin_pie", true, 1, () -> new UnbakedCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5f).sounds(BlockSoundGroup.WOOL))),
    CAKE_BATTER("cake_batter", true, 1, () -> new UnbakedCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5f).sounds(BlockSoundGroup.WOOL))),
    CAKE("cake", true, 1, () -> new CustomCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5f).sounds(BlockSoundGroup.WOOL))),
    BREAD_DOUGH("bread_dough", true, 1, () -> new UnbakedBreadBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5f).sounds(BlockSoundGroup.WOOL))),
    COOKIE_DOUGH("cookie_dough", true, 1, () -> new UnbakedCookiesBlock(FabricBlockSettings.of(Material.CAKE).strength(0.5f).sounds(BlockSoundGroup.WOOL))),

    // storage blocks
    PLACED_TOOL("placed_tool", false, 1, () -> new PlacedToolBlock(FabricBlockSettings.of(Material.WOOD).noCollision().nonOpaque())),
    SOUL_FORGED_STEEL_BLOCK("soul_forged_steel_block", false, 1, () -> new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK))),

    // wool blocks
    // TODO wool blocks if necessary
    //  velocity multiplier?

    // decoration blocks
    COBWEB("cobweb", true, 1, () -> new CustomCobwebBlock(FabricBlockSettings.of(Material.COBWEB).noCollision().requiresTool().strength(4.0f))),

    // torches
    TORCH_UNLIT("torch_unlit", false, 1, () -> new UnlitTorchBlock(false, false, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD))),
    WALL_TORCH_UNLIT("wall_torch_unlit", false, 1, () -> new UnlitWallTorchBlock(false, false, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD).dropsLike(TORCH_UNLIT.get()))),
    CRUDE_TORCH("crude_torch", false, 1, () -> new CrudeTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> 14).sounds(BlockSoundGroup.WOOD))),
    CRUDE_WALL_TORCH("crude_wall_torch", false, 1, () -> new CrudeWallTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD).dropsLike(CRUDE_TORCH.get()))),
    CRUDE_TORCH_UNLIT("crude_torch_unlit", false, 1, () -> new UnlitTorchBlock(false, true, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD))),
    CRUDE_WALL_TORCH_UNLIT("crude_wall_torch_unlit", false, 1, () -> new UnlitWallTorchBlock(false, true, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD).dropsLike(CRUDE_TORCH_UNLIT.get()))),
    CRUDE_TORCH_BURNED("crude_torch_burned", false, 1, () -> new UnlitTorchBlock(true, true, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD))),
    CRUDE_WALL_TORCH_BURNED("crude_wall_torch_burned", false, 1, () -> new UnlitWallTorchBlock(true, true, FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD).dropsLike(CRUDE_TORCH_UNLIT.get()))),

    VASE_PLAIN("vase_plain", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_WHITE("vase_white", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_ORANGE("vase_orange", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_MAGENTA("vase_magenta", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_LIGHT_BLUE("vase_light_blue", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_YELLOW("vase_yellow", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_LIME("vase_lime", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_PINK("vase_pink", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_GRAY("vase_gray", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_LIGHT_GRAY("vase_light_gray", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_CYAN("vase_cyan", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_PURPLE("vase_purple", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_BLUE("vase_blue", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_BROWN("vase_brown", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_GREEN("vase_green", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_RED("vase_red", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    VASE_BLACK("vase_black", true, () -> new VaseBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 0.0f))),
    // TODO glazed vases

    // pottery
    UNFIRED_MOULD("unfired_mould", true, 1, () -> new UnfiredMouldBlock(Blocks.AIR, true, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),
    UNFIRED_URN("unfired_urn", true, 1, () -> new UnfiredUrnBlock(UNFIRED_MOULD.get(), false, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),
    UNFIRED_VASE("unfired_vase", true, 1, () -> new UnfiredVaseBlock(UNFIRED_URN.get(), true, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),
    UNFIRED_PLANTER("unfired_planter", true, 1, () -> new UnfiredPlanterBlock(UNFIRED_VASE.get(), true, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),
    UNFIRED_CRUCIBLE("unfired_crucible", true, 1, () -> new UnfiredCrucibleBlock(UNFIRED_PLANTER.get(), true, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),
    UNFIRED_CLAY("unfired_clay", true, () -> new UnfiredPotteryBlock(UNFIRED_CRUCIBLE.get(), false, FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6f).sounds(BlockSoundGroup.GRAVEL))),

    // soil blocks
    GRASS_BLOCK("grass_block", true, 2, () -> new CustomGrassBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    GRASS_SLAB("grass_slab", true, 2, () -> new GrassSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    GRASS_BLOCK_SPARSE("grass_block_sparse", true, 2, () -> new SparseGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK))),
    GRASS_SLAB_SPARSE("grass_slab_sparse", true, 2, () -> new SparseGrassSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    DIRT("dirt", true, () -> new CustomDirtBlock(FabricBlockSettings.copyOf(Blocks.DIRT))),
    DIRT_SLAB("dirt_slab", true, () -> new DirtSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).ticksRandomly().strength(0.5F).sounds(BlockSoundGroup.GRAVEL))),
    LOOSE_DIRT("loose_dirt", true, () -> new LooseDirtBlock(FabricBlockSettings.copyOf(Blocks.DIRT).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8))),
    LOOSE_DIRT_SLAB("loose_dirt_slab", true, () -> new LooseDirtSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN)/*TODO.ticksRandomly()*/.strength(0.5F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRAVEL))),
    FARMLAND("farmland", false, () -> new CustomFarmlandBlock(FabricBlockSettings.copyOf(Blocks.FARMLAND).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8))),
    COARSE_DIRT("coarse_dirt", true, () -> new CustomDirtBlock(FabricBlockSettings.copyOf(Blocks.COARSE_DIRT))),
    COARSE_DIRT_SLAB("coarse_dirt_slab", true, () -> new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL))),
    PODZOL("podzol", true, () -> new CustomPodzolBlock(FabricBlockSettings.copyOf(Blocks.PODZOL))),
    PODZOL_SLAB("podzol_slab", true, () -> new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL))),
    MYCELIUM("mycelium", true, () -> new CustomMyceliumBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    MYCELIUM_SLAB("mycelium_slab", true, () -> new MyceliumSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    MYCELIUM_SPARSE("mycelium_sparse", true, () -> new SparseMyceliumBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    MYCELIUM_SLAB_SPARSE("mycelium_slab_sparse", true, () -> new SparseMyceliumSlabBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PURPLE).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRASS))),
    ROOTED_DIRT("rooted_dirt", true, () -> new CustomDirtBlock(FabricBlockSettings.copyOf(Blocks.ROOTED_DIRT))),
    ROOTED_DIRT_SLAB("rooted_dirt_slab", true, () -> new SoilSlabBlock(FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.ROOTED_DIRT))),
    DIRT_PATH("dirt_path", true, () -> new CustomDirtPathBlock(FabricBlockSettings.of(Material.SOIL).strength(0.65F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.GRASS).blockVision((state, world, pos) -> true).suffocates((state, world, pos) -> true))),
    DIRT_PATH_SLAB("dirt_path_slab", true, () -> new DirtPathSlabBlock(FabricBlockSettings.of(Material.SOIL).strength(0.65F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.GRASS).blockVision((state, world, pos) -> true).suffocates((state, world, pos) -> true))),
    SAND("sand", true, () -> new SandBlock(14406560, FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(0.5F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.SAND))),
    SAND_SLAB("sand_slab", true, () -> new FallingSlabBlock(14406560, FabricBlockSettings.of(Material.AGGREGATE, MapColor.PALE_YELLOW).strength(0.5F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.SAND))),
    RED_SAND("red_sand", true, () -> new FallingSlabBlock(11098145, FabricBlockSettings.of(Material.AGGREGATE, MapColor.ORANGE).strength(0.5F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.SAND))),
    RED_SAND_SLAB("red_sand_slab", true, () -> new FallingSlabBlock(11098145, FabricBlockSettings.of(Material.AGGREGATE, MapColor.ORANGE).strength(0.5F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.SAND))),
    GRAVEL("gravel", true, () -> new GravelBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.6F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.GRAVEL))),
    GRAVEL_SLAB("gravel_slab", true, () -> new FallingSlabBlock(-8356741, FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.6F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.GRAVEL))),
    MUD("mud", true, () -> new MudBlock(AbstractBlock.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(BlocksRegistry::always).solidBlock(BlocksRegistry::always).blockVision(BlocksRegistry::always).suffocates(BlocksRegistry::always).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.MUD))),
    MUD_SLAB("mud_slab", true, () -> new MudSlabBlock(AbstractBlock.Settings.copy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).allowsSpawning(BlocksRegistry::always).solidBlock(BlocksRegistry::always).blockVision(BlocksRegistry::always).suffocates(BlocksRegistry::always).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.MUD))),
    // TODO mud slab

    SNOW_BLOCK("snow_block", true, () -> new Block(AbstractBlock.Settings.of(Material.SNOW_BLOCK).requiresTool().strength(0.2f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.SNOW))),

    CLAY_ORE("clay_ore", true, () -> new Block(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6F).sounds(BlockSoundGroup.GRAVEL))),

    // brick blocks
    WET_CLAY_BRICK("wet_clay_brick", true, 1, () -> new WetClayBrickBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6F).sounds(BlockSoundGroup.GRAVEL))),
    BRICK("brick", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE))),
    UNCOOKED_NETHER_BRICK("uncooked_nether_brick", true, 1, () -> new UncookedNetherBrickBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT).strength(0.6F).sounds(BlockSoundGroup.GRAVEL))),
//    NETHER_BRICK("nether_brick", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS))),
//    DIAMOND_INGOT("diamond_ingot", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL))),
//    IRON_INGOT("iron_ingot", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL))),
//    COPPER_INGOT("copper_ingot", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER))),
//    GOLD_INGOT("gold_ingot", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL))),
//    NETHERITE_INGOT("netherite_ingot", true, 1, () -> new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(50.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE))),

    // terracotta
    TERRACOTTA("terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    WHITE_TERRACOTTA("white_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.WHITE_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ORANGE_TERRACOTTA("orange_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.ORANGE_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    MAGENTA_TERRACOTTA("magenta_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.MAGENTA_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    LIGHT_BLUE_TERRACOTTA("light_blue_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    YELLOW_TERRACOTTA("yellow_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.YELLOW_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    LIME_TERRACOTTA("lime_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.LIME_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    PINK_TERRACOTTA("pink_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.PINK_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRAY_TERRACOTTA("gray_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.GRAY_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    LIGHT_GRAY_TERRACOTTA("light_gray_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CYAN_TERRACOTTA("cyan_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.CYAN_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    PURPLE_TERRACOTTA("purple_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.PURPLE_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    BLUE_TERRACOTTA("blue_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.BLUE_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    BROWN_TERRACOTTA("brown_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.BROWN_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GREEN_TERRACOTTA("green_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.GREEN_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    RED_TERRACOTTA("red_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.RED_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    BLACK_TERRACOTTA("black_terracotta", true, () -> new Block(FabricBlockSettings.copy(Blocks.BLACK_TERRACOTTA).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    // stones
    SMOOTH_STONE("smooth_stone", true, () -> new Block(FabricBlockSettings.copy(Blocks.STONE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    STONE("stone", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.STONE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE("granite", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.GRANITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE("diorite", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.DIORITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE("andesite", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.ANDESITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    TUFF("tuff", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.TUFF).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DEEPSLATE("deepslate", true, () -> new CustomStonePillarBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE("calcite", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.CALCITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    SMOOTH_BASALT("smooth_basalt", true, () -> new CustomStoneBlock(FabricBlockSettings.copy(Blocks.SMOOTH_BASALT).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    SANDSTONE("sandstone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).strength(0.8f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    RED_SANDSTONE("red_sandstone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).strength(0.8f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    END_STONE("end_stone", true, () -> new Block(FabricBlockSettings.copy(Blocks.END_STONE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    // cobble blocks
    COBBLESTONE("cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    MOSSY_COBBLESTONE("mossy_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_COBBLESTONE("granite_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.DIRT_BROWN).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_COBBLESTONE("diorite_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_COBBLESTONE("andesite_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    TUFF_COBBLESTONE("tuff_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_GRAY).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    CALCITE_COBBLESTONE("calcite_cobblestone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    COBBLED_DEEPSLATE("cobbled_deepslate", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(3.5f, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.DEEPSLATE))),
    COBBLED_END_STONE("cobbled_end_stone", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    // bricks blocks
    BRICKS("bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_BRICKS("granite_bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.DIRT_BROWN).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_BRICKS("diorite_bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_BRICKS("andesite_bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    TUFF_BRICKS("tuff_bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_GRAY).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    CALCITE_BRICKS("calcite_bricks", true, () -> new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(2.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    END_STONE_BRICKS("end_stone_bricks", true, () -> new Block(FabricBlockSettings.copyOf(Blocks.END_STONE_BRICKS).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    // loose cobble blocks
    COBBLESTONE_LOOSE("cobblestone_loose", true, () -> new LooseFallingBlock(Blocks.COBBLESTONE, FabricBlockSettings.copyOf(Blocks.COBBLESTONE))),
    GRANITE_COBBLESTONE_LOOSE("granite_cobblestone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.GRANITE_COBBLESTONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.DIRT_BROWN).strength(2.0F, 6.0F))),
    DIORITE_COBBLESTONE_LOOSE("diorite_cobblestone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.DIORITE_COBBLESTONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).strength(2.0F, 6.0F))),
    ANDESITE_COBBLESTONE_LOOSE("andesite_cobblestone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.ANDESITE_COBBLESTONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(2.0F, 6.0F))),
    TUFF_COBBLESTONE_LOOSE("tuff_cobblestone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.TUFF_COBBLESTONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_GRAY).strength(2.0F, 6.0F).sounds(BlockSoundGroup.TUFF))),
    CALCITE_COBBLESTONE_LOOSE("calcite_cobblestone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.CALCITE_COBBLESTONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(2.0F, 6.0F).sounds(BlockSoundGroup.CALCITE))),
    COBBLED_DEEPSLATE_LOOSE("cobbled_deepslate_loose", true, () -> new LooseFallingBlock(Blocks.COBBLED_DEEPSLATE, FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(3.5f, 6.0f).sounds(BlockSoundGroup.DEEPSLATE))),
    COBBLED_END_STONE_LOOSE("cobbled_end_stone_loose", true, () -> new LooseFallingBlock(BlocksRegistry.COBBLED_END_STONE.get(), FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).strength(2.0F, 6.0F))),

    // loose bricks blocks
    BRICKS_LOOSE("bricks_loose", true, () -> new LooseFallingBlock(Blocks.BRICKS, FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0F))),
    STONE_BRICKS_LOOSE("stone_bricks_loose", true, () -> new LooseFallingBlock(Blocks.STONE_BRICKS, FabricBlockSettings.of(Material.STONE).strength(1.5f, 6.0F))),
    GRANITE_BRICKS_LOOSE("granite_bricks_loose", true, () -> new LooseFallingBlock(BlocksRegistry.GRANITE_BRICKS.get(), FabricBlockSettings.of(Material.STONE, MapColor.DIRT_BROWN).strength(1.5f, 6.0F))),
    DIORITE_BRICKS_LOOSE("diorite_bricks_loose", true, () -> new LooseFallingBlock(BlocksRegistry.DIORITE_BRICKS.get(), FabricBlockSettings.of(Material.STONE, MapColor.OFF_WHITE).strength(1.5f, 6.0F))),
    ANDESITE_BRICKS_LOOSE("andesite_bricks_loose", true, () -> new LooseFallingBlock(BlocksRegistry.ANDESITE_BRICKS.get(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).strength(1.5f, 6.0F))),
    TUFF_BRICKS_LOOSE("tuff_bricks_loose", true, () -> new LooseFallingBlock(BlocksRegistry.TUFF_BRICKS.get(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_GRAY).strength(1.5f, 6.0F).sounds(BlockSoundGroup.TUFF))),
    CALCITE_BRICKS_LOOSE("calcite_bricks_loose", true, () -> new LooseFallingBlock(BlocksRegistry.CALCITE_BRICKS.get(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_WHITE).strength(1.5f, 6.0F).sounds(BlockSoundGroup.CALCITE))),
    DEEPSLATE_BRICKS_LOOSE("deepslate_bricks_loose", true, () -> new LooseFallingBlock(Blocks.DEEPSLATE_BRICKS, FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(3.0f, 6.0f).sounds(BlockSoundGroup.DEEPSLATE))),
    END_STONE_BRICKS_LOOSE("end_stone_bricks_loose", true, () -> new LooseFallingBlock(Blocks.END_STONE_BRICKS, FabricBlockSettings.of(Material.STONE, MapColor.PALE_YELLOW).strength(1.5f, 6.0F))),

    // ore chunks
    IRON_ORE_CHUNK("iron_ore_chunk", true, 1, () -> new OreChunkBlock(FabricBlockSettings.of(Material.STONE, MapColor.RAW_IRON_PINK).breakInstantly())),
    COPPER_ORE_CHUNK("copper_ore_chunk", true, 1, () -> new OreChunkBlock(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).breakInstantly())),
    GOLD_ORE_CHUNK("gold_ore_chunk", true, 1, () -> new OreChunkBlock(FabricBlockSettings.of(Material.STONE, MapColor.GOLD).breakInstantly())),

    // ores // TODO custom ore blocks to have stages
    GOLD_ORE("gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_GOLD_ORE("granite_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_GOLD_ORE("diorite_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_GOLD_ORE("andesite_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_GOLD_ORE("calcite_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.GOLD_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_GOLD_ORE("tuff_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_GOLD_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_GOLD_ORE("deepslate_gold_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_GOLD_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    IRON_ORE("iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_IRON_ORE("granite_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_IRON_ORE("diorite_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_IRON_ORE("andesite_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_IRON_ORE("calcite_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.IRON_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_IRON_ORE("tuff_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_IRON_ORE("deepslate_iron_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    COAL_ORE("coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_COAL_ORE("granite_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_COAL_ORE("diorite_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_COAL_ORE("andesite_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_COAL_ORE("calcite_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COAL_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_COAL_ORE("tuff_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_COAL_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_COAL_ORE("deepslate_coal_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_COAL_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    COPPER_ORE("copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_COPPER_ORE("granite_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_COPPER_ORE("diorite_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_COPPER_ORE("andesite_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_COPPER_ORE("calcite_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.COPPER_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_COPPER_ORE("tuff_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_COPPER_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_COPPER_ORE("deepslate_copper_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_COPPER_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    LAPIS_ORE("lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_LAPIS_ORE("granite_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_LAPIS_ORE("diorite_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_LAPIS_ORE("andesite_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_LAPIS_ORE("calcite_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.LAPIS_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_LAPIS_ORE("tuff_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_LAPIS_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_LAPIS_ORE("deepslate_lapis_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_LAPIS_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    DIAMOND_ORE("diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_DIAMOND_ORE("granite_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_DIAMOND_ORE("diorite_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_DIAMOND_ORE("andesite_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_DIAMOND_ORE("calcite_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DIAMOND_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_DIAMOND_ORE("tuff_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_DIAMOND_ORE("deepslate_diamond_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_DIAMOND_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    REDSTONE_ORE("redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_REDSTONE_ORE("granite_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_REDSTONE_ORE("diorite_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_REDSTONE_ORE("andesite_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_REDSTONE_ORE("calcite_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.REDSTONE_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_REDSTONE_ORE("tuff_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_REDSTONE_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_REDSTONE_ORE("deepslate_redstone_ore", true, 1, () -> new RedstoneOreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_REDSTONE_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    EMERALD_ORE("emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    GRANITE_EMERALD_ORE("granite_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).mapColor(MapColor.DIRT_BROWN).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    DIORITE_EMERALD_ORE("diorite_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).mapColor(MapColor.OFF_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    ANDESITE_EMERALD_ORE("andesite_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).mapColor(MapColor.STONE_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),
    CALCITE_EMERALD_ORE("calcite_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.EMERALD_ORE).mapColor(MapColor.TERRACOTTA_WHITE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CALCITE))),
    TUFF_EMERALD_ORE("tuff_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_EMERALD_ORE).mapColor(MapColor.TERRACOTTA_GRAY).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.TUFF))),
    DEEPSLATE_EMERALD_ORE("deepslate_emerald_ore", true, 1, () -> new ExperienceDroppingBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_EMERALD_ORE).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1))),

    // infested stones
    INFESTED_STONE("infested_stone", true, () -> new InfestedBlock(BlocksRegistry.STONE.get(), FabricBlockSettings.copy(BlocksRegistry.STONE.get()))),
    INFESTED_GRANITE("infested_granite", true, () -> new InfestedBlock(BlocksRegistry.GRANITE.get(), FabricBlockSettings.copy(BlocksRegistry.GRANITE.get()))),
    INFESTED_DIORITE("infested_diorite", true, () -> new InfestedBlock(BlocksRegistry.DIORITE.get(), FabricBlockSettings.copy(BlocksRegistry.DIORITE.get()))),
    INFESTED_ANDESITE("infested_andesite", true, () -> new InfestedBlock(BlocksRegistry.ANDESITE.get(), FabricBlockSettings.copy(BlocksRegistry.ANDESITE.get()))),
    INFESTED_TUFF("infested_tuff", true, () -> new InfestedBlock(BlocksRegistry.TUFF.get(), FabricBlockSettings.copy(BlocksRegistry.TUFF.get()))),
    INFESTED_DEEPSLATE("infested_deepslate", true, () -> new RotatedInfestedBlock(BlocksRegistry.DEEPSLATE.get(), FabricBlockSettings.copy(BlocksRegistry.DEEPSLATE.get()))),
    INFESTED_CALCITE("infested_calcite", true, () -> new InfestedBlock(BlocksRegistry.CALCITE.get(), FabricBlockSettings.copy(BlocksRegistry.CALCITE.get()))),

    // leaves
    OAK_LEAVES("oak_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    SPRUCE_LEAVES("spruce_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    BIRCH_LEAVES("birch_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    JUNGLE_LEAVES("jungle_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    ACACIA_LEAVES("acacia_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    CHERRY_LEAVES("cherry_leaves", true, () -> new CherryLeavesBlock(AbstractBlock.Settings.of(Material.LEAVES, MapColor.PINK).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.CHERRY_LEAVES).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    DARK_OAK_LEAVES("dark_oak_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    MANGROVE_LEAVES("mangrove_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    AZALEA_LEAVES("azalea_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),
    FLOWERING_AZALEA_LEAVES("flowering_azalea_leaves", true, () -> new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().velocityMultiplier(Constants.VELOCITY_MULTIPLYER_0_8).sounds(BlockSoundGroup.AZALEA_LEAVES).nonOpaque().allowsSpawning(BlocksRegistry::canSpawnOnLeaves).suffocates(BlocksRegistry::never).blockVision(BlocksRegistry::never))),

    // planks
    OAK_PLANKS("oak_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    SPRUCE_PLANKS("spruce_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    BIRCH_PLANKS("birch_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.PALE_YELLOW).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    JUNGLE_PLANKS("jungle_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    ACACIA_PLANKS("acacia_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.ORANGE).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    CHERRY_PLANKS("cherry_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.TERRACOTTA_WHITE).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.CHERRY_WOOD))),
    DARK_OAK_PLANKS("dark_oak_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    MANGROVE_PLANKS("mangrove_planks", true, () -> new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.RED).strength(2.0f, 3.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),

    // plant blocks
    CACTUS_ROOT("cactus_root", true, 1, () -> new CactusRootBlock(FabricBlockSettings.of(Material.CACTUS).ticksRandomly().strength(1.0F).sounds(BlockSoundGroup.WOOL))),
    SUGAR_CANE_ROOT("sugar_cane_root", true, 1, () -> new SugarCaneRootBlock(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().strength(1.0F).sounds(BlockSoundGroup.GRASS))),
    WEED("weed", false, 1, () -> new WeedBlock(FabricBlockSettings.copyOf(Blocks.WHEAT).offset(AbstractBlock.OffsetType.NONE))),
    BEETROOT_CROP("beetroot_crop", false, 1, () -> new BeetrootsCropBlock(FabricBlockSettings.copyOf(Blocks.BEETROOTS))),
    CARROT_CROP("carrot_crop", false, 1, () -> new CarrotsCropBlock(FabricBlockSettings.copyOf(Blocks.CARROTS))),
    POTATO_CROP("potato_crop", false, 1, () -> new PotatoesCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES))),
    WHEAT_CROP("wheat_crop", false, 1, () -> new WheatCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT))),
    WHEAT_UPPER_CROP("wheat_upper_crop", false, 1, () -> new WheatUpperCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT))),
    HEMP_CROP("hemp_crop", false, 1, () -> new HempCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT))),
    HEMP_UPPER_CROP("hemp_upper_crop", false, 1, () -> new HempUpperCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT))),
    BROWN_MUSHROOM("brown_mushroom", false, 1, () -> new BrownMushroomPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.BROWN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(BlocksRegistry::always), TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM)),
    RED_MUSHROOM("red_mushroom", false, 1, () -> new RedMushroomPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.RED).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS).postProcess(BlocksRegistry::always), TreeConfiguredFeatures.HUGE_RED_MUSHROOM)),
    GRASS("grass", true, 2, () -> new CustomFernBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))),
    FERN("fern", true, 2, () -> new CustomFernBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))),
    TALL_GRASS("tall_grass", true, 2, () -> new CustomTallPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))),
    LARGE_FERN("large_fern", true, 2, () -> new CustomTallPlantBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS))),
    // TODO custom cocoa block
    COCOA("cocoa", false, 1, () -> new CocoaBlock(FabricBlockSettings.of(Material.PLANT).ticksRandomly().strength(0.2f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque())),
    PUMPKIN("pumpkin", true, 1, () -> new CustomPumpkinBlock(FabricBlockSettings.of(Material.GOURD, MapColor.ORANGE).strength(1.0f).sounds(BlockSoundGroup.WOOD))),
    CARVED_PUMPKIN("carved_pumpkin", true, 1, () -> new CustomCarvedPumpkinBlock(false, FabricBlockSettings.of(Material.GOURD, MapColor.ORANGE).strength(1.0f).sounds(BlockSoundGroup.WOOD).allowsSpawning(BlocksRegistry::always))),
    JACK_O_LANTERN("jack_o_lantern", true, 1, () -> new CustomCarvedPumpkinBlock(true, FabricBlockSettings.of(Material.GOURD, MapColor.ORANGE).strength(1.0f).sounds(BlockSoundGroup.WOOD).luminance(state -> 15).allowsSpawning(BlocksRegistry::always))),
    ATTACHED_PUMPKIN_STEM("attached_pumpkin_stem", false, 1, () -> new CustomAttachedStemBlock((FallingGourdBlock) PUMPKIN.get(), ItemsRegistry.PUMPKIN_SEEDS::get, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD))),
    PUMPKIN_STEM("pumpkin_stem", false, 1, () -> new CustomStemBlock((FallingGourdBlock) PUMPKIN.get(), ItemsRegistry.PUMPKIN_SEEDS::get, FabricBlockSettings.of(Material.PLANT).ticksRandomly().strength(0.2f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque())),
    MELON("melon", true, 1, () -> new CustomMelonBlock(FabricBlockSettings.of(Material.GOURD, MapColor.LIME).strength(1.0f).sounds(BlockSoundGroup.WOOD))),
    ATTACHED_MELON_STEM("attached_melon_stem", false, 1, () -> new CustomAttachedStemBlock((FallingGourdBlock) MELON.get(), ItemsRegistry.MELON_SEEDS::get, AbstractBlock.Settings.of(Material.PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD))),
    MELON_STEM("melon_stem", false, 1, () -> new CustomStemBlock((FallingGourdBlock) MELON.get(), ItemsRegistry.MELON_SEEDS::get, FabricBlockSettings.of(Material.PLANT).ticksRandomly().strength(0.2f, 3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque())),

    // corners
    STONE_CORNER("stone_corner", true, 1, () -> new CornerBlock(BlocksRegistry.STONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.STONE.get()))),
    COBBLESTONE_CORNER("cobblestone_corner", true, 1, () -> new CornerBlock(BlocksRegistry.COBBLESTONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.COBBLESTONE.get()))),
    OAK_PLANKS_CORNER("oak_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.OAK_PLANKS.get()))),
    SPRUCE_PLANKS_CORNER("spruce_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.SPRUCE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.SPRUCE_PLANKS.get()))),
    BIRCH_PLANKS_CORNER("birch_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.BIRCH_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.BIRCH_PLANKS.get()))),
    JUNGLE_PLANKS_CORNER("jungle_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.JUNGLE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.JUNGLE_PLANKS.get()))),
    ACACIA_PLANKS_CORNER("acacia_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.ACACIA_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.ACACIA_PLANKS.get()))),
    CHERRY_PLANKS_CORNER("cherry_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.CHERRY_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.CHERRY_PLANKS.get()))),
    DARK_OAK_PLANKS_CORNER("dark_oak_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.DARK_OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.DARK_OAK_PLANKS.get()))),
    MANGROVE_PLANKS_CORNER("mangrove_planks_corner", true, 1, () -> new CornerBlock(BlocksRegistry.MANGROVE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.MANGROVE_PLANKS.get()))),

    // edges
    STONE_EDGE("stone_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.STONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.STONE.get()))),
    COBBLESTONE_EDGE("cobblestone_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.COBBLESTONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.COBBLESTONE.get()))),
    OAK_PLANKS_EDGE("oak_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.OAK_PLANKS.get()))),
    SPRUCE_PLANKS_EDGE("spruce_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.SPRUCE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.SPRUCE_PLANKS.get()))),
    BIRCH_PLANKS_EDGE("birch_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.BIRCH_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.BIRCH_PLANKS.get()))),
    JUNGLE_PLANKS_EDGE("jungle_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.JUNGLE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.JUNGLE_PLANKS.get()))),
    ACACIA_PLANKS_EDGE("acacia_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.ACACIA_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.ACACIA_PLANKS.get()))),
    CHERRY_PLANKS_EDGE("cherry_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.CHERRY_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.CHERRY_PLANKS.get()))),
    DARK_OAK_PLANKS_EDGE("dark_oak_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.DARK_OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.DARK_OAK_PLANKS.get()))),
    MANGROVE_PLANKS_EDGE("mangrove_planks_edge", true, 1, () -> new EdgeBlock(BlocksRegistry.MANGROVE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.MANGROVE_PLANKS.get()))),

    // sidings
    STONE_SIDING("stone_siding", true, 1, () -> new SidingBlock(BlocksRegistry.STONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.STONE.get()))),
    COBBLESTONE_SIDING("cobblestone_siding", true, 1, () -> new SidingBlock(BlocksRegistry.COBBLESTONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.COBBLESTONE.get()))),
    OAK_PLANKS_SIDING("oak_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.OAK_PLANKS.get()))),
    SPRUCE_PLANKS_SIDING("spruce_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.SPRUCE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.SPRUCE_PLANKS.get()))),
    BIRCH_PLANKS_SIDING("birch_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.BIRCH_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.BIRCH_PLANKS.get()))),
    JUNGLE_PLANKS_SIDING("jungle_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.JUNGLE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.JUNGLE_PLANKS.get()))),
    ACACIA_PLANKS_SIDING("acacia_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.ACACIA_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.ACACIA_PLANKS.get()))),
    CHERRY_PLANKS_SIDING("cherry_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.CHERRY_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.CHERRY_PLANKS.get()))),
    DARK_OAK_PLANKS_SIDING("dark_oak_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.DARK_OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.DARK_OAK_PLANKS.get()))),
    MANGROVE_PLANKS_SIDING("mangrove_planks_siding", true, 1, () -> new SidingBlock(BlocksRegistry.MANGROVE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.MANGROVE_PLANKS.get()))),

    // stairs
    STONE_STAIR("stone_stair", true, 1, () -> new StairBlock(BlocksRegistry.STONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.STONE.get()))),
    COBBLESTONE_STAIR("cobblestone_stair", true, 1, () -> new StairBlock(BlocksRegistry.COBBLESTONE.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.COBBLESTONE.get()))),
    OAK_PLANKS_STAIR("oak_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.OAK_PLANKS.get()))),
    SPRUCE_PLANKS_STAIR("spruce_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.SPRUCE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.SPRUCE_PLANKS.get()))),
    BIRCH_PLANKS_STAIR("birch_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.BIRCH_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.BIRCH_PLANKS.get()))),
    JUNGLE_PLANKS_STAIR("jungle_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.JUNGLE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.JUNGLE_PLANKS.get()))),
    ACACIA_PLANKS_STAIR("acacia_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.ACACIA_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.ACACIA_PLANKS.get()))),
    CHERRY_PLANKS_STAIR("cherry_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.CHERRY_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.CHERRY_PLANKS.get()))),
    DARK_OAK_PLANKS_STAIR("dark_oak_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.DARK_OAK_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.DARK_OAK_PLANKS.get()))),
    MANGROVE_PLANKS_STAIR("mangrove_planks_stair", true, 1, () -> new StairBlock(BlocksRegistry.MANGROVE_PLANKS.get().getDefaultState(), FabricBlockSettings.copyOf(BlocksRegistry.MANGROVE_PLANKS.get()))),

    // tree stump blocks
    ACACIA_STUMP("acacia_stump", true, () -> new TreeStumpBlock(ACACIA_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    BIRCH_STUMP("birch_stump", true, () -> new TreeStumpBlock(BIRCH_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    DARK_OAK_STUMP("dark_oak_stump", true, () -> new TreeStumpBlock(DARK_OAK_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    CHERRY_STUMP("cherry_stump", true, () -> new TreeStumpBlock(CHERRY_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    JUNGLE_STUMP("jungle_stump", true, () -> new TreeStumpBlock(JUNGLE_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    OAK_STUMP("oak_stump", true, () -> new TreeStumpBlock(OAK_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    SPRUCE_STUMP("spruce_stump", true, () -> new TreeStumpBlock(SPRUCE_CRAFTING_TABLE.get(), FabricBlockSettings.of(Material.WOOD).strength(6.0F, 6.0F).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),

    // logs
    STRIPPED_OAK_WOOD("stripped_oak_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_SPRUCE_WOOD("stripped_spruce_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_BIRCH_WOOD("stripped_birch_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_JUNGLE_WOOD("stripped_jungle_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_ACACIA_WOOD("stripped_acacia_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.ORANGE).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_CHERRY_WOOD("stripped_cherry_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_PINK).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_DARK_OAK_WOOD("stripped_dark_oak_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_MANGROVE_WOOD("stripped_mangrove_wood", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.RED).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    
    STRIPPED_SPRUCE_LOG("stripped_spruce_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_BIRCH_LOG("stripped_birch_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_JUNGLE_LOG("stripped_jungle_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_ACACIA_LOG("stripped_acacia_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.ORANGE).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_CHERRY_LOG("stripped_cherry_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_WHITE : MapColor.TERRACOTTA_PINK).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_DARK_OAK_LOG("stripped_dark_oak_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_OAK_LOG("stripped_oak_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    STRIPPED_MANGROVE_LOG("stripped_mangrove_log", true, () -> new CustomStrippedLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.RED).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    // TODO update 1.20
//    STRIPPED_BAMBOO_BLOCK("stripped_bamboo_block", true, () -> new CustomLogBlock(FabricBlockSettings.of(Material.WOOD, MapColor.YELLOW).strength(2.0f).sounds(BlockSoundGroup.BAMBOO_WOOD))),
    
    OAK_LOG("oak_log", true, () -> new CustomLogBlock(STRIPPED_OAK_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.OAK_TAN : MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    SPRUCE_LOG("spruce_log", true, () -> new CustomLogBlock(STRIPPED_SPRUCE_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.SPRUCE_BROWN : MapColor.BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    BIRCH_LOG("birch_log", true, () -> new CustomLogBlock(STRIPPED_BIRCH_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.PALE_YELLOW : MapColor.OFF_WHITE).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    JUNGLE_LOG("jungle_log", true, () -> new CustomLogBlock(STRIPPED_JUNGLE_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.DIRT_BROWN : MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    ACACIA_LOG("acacia_log", true, () -> new CustomLogBlock(STRIPPED_ACACIA_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.ORANGE : MapColor.STONE_GRAY).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    CHERRY_LOG("cherry_log", true, () -> new CustomLogBlock(STRIPPED_CHERRY_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_WHITE : MapColor.TERRACOTTA_GRAY).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    DARK_OAK_LOG("dark_oak_log", true, () -> new CustomLogBlock(STRIPPED_DARK_OAK_LOG.get(), FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    MANGROVE_LOG("mangrove_log", true, () -> new CustomLogBlock(STRIPPED_MANGROVE_LOG.get(), FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.RED : MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),

//    MANGROVE_ROOTS("", true, () -> Blocks.register("mangrove_roots", new MangroveRootsBlock(FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(0.7f).ticksRandomly().sounds(BlockSoundGroup.MANGROVE_ROOTS).nonOpaque().suffocates(Blocks::never).blockVision(Blocks::never).nonOpaque()))),
//    MUDDY_MANGROVE_ROOTS("", true, () -> Blocks.register("muddy_mangrove_roots", new PillarBlock(FabricBlockSettings.of(Material.SOIL, MapColor.SPRUCE_BROWN).strength(0.7f).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)))),

    // TODO update 1.20
//    BAMBOO_BLOCK("bamboo_block", true, () -> new CustomLogBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.YELLOW : MapColor.DARK_GREEN).strength(2.0f).sounds(BlockSoundGroup.BAMBOO_WOOD))),
    OAK_WOOD("oak_wood", true, () -> new CustomLogBlock(STRIPPED_OAK_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.OAK_TAN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    SPRUCE_WOOD("spruce_wood", true, () -> new CustomLogBlock(STRIPPED_SPRUCE_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.SPRUCE_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    BIRCH_WOOD("birch_wood", true, () -> new CustomLogBlock(STRIPPED_BIRCH_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.PALE_YELLOW).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    JUNGLE_WOOD("jungle_wood", true, () -> new CustomLogBlock(STRIPPED_JUNGLE_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.DIRT_BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    ACACIA_WOOD("acacia_wood", true, () -> new CustomLogBlock(STRIPPED_ACACIA_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.GRAY).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    CHERRY_WOOD("cherry_wood", true, () -> new CustomLogBlock(STRIPPED_MANGROVE_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.TERRACOTTA_GRAY).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    DARK_OAK_WOOD("dark_oak_wood", true, () -> new CustomLogBlock(STRIPPED_DARK_OAK_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.BROWN).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD))),
    MANGROVE_WOOD("mangrove_wood", true, () -> new CustomLogBlock(STRIPPED_MANGROVE_WOOD.get(), FabricBlockSettings.of(Material.WOOD, MapColor.RED).strength(2.0f).velocityMultiplier(Constants.VELOCITY_MULTIPLYER_1_1).sounds(BlockSoundGroup.WOOD)));
    
    private static FlammableBlockRegistry.Entry flammable(int burnChance, @SuppressWarnings("SameParameterValue") int spreadChance) {
        return new FlammableBlockRegistry.Entry(burnChance, spreadChance);
    }

    private static boolean isValidFlammableEntry(FlammableBlockRegistry.Entry flammableRate) {
        return flammableRate != null && flammableRate.getBurnChance() > 0 && flammableRate.getSpreadChance() > 0;
    }

    private final String pathName;
    private final Supplier<Block> blockSupplier;
    private final FlammableBlockRegistry.Entry flammableRate;
    private final int fuelValue;
    private final boolean hasItem;
    private final int cutout;
    private Block block;

    BlocksRegistry(String pathName, boolean hasItem, Supplier<Block> blockSupplier) {
        this(pathName, 0, hasItem, 0, blockSupplier, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, int fuelValue, boolean hasItem, Supplier<Block> blockSupplier) {
        this(pathName, fuelValue, hasItem, 0, blockSupplier, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, boolean hasItem, int cutout, Supplier<Block> blockSupplier) {
        this(pathName, 0, hasItem, cutout, blockSupplier, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, int fuelValue, boolean hasItem, int cutout, Supplier<Block> blockSupplier) {
        this(pathName, fuelValue, hasItem, cutout, blockSupplier, new FlammableBlockRegistry.Entry(0, 0));
    }

    BlocksRegistry(String pathName, int fuelValue, boolean hasItem, int cutout, Supplier<Block> blockSupplier, FlammableBlockRegistry.Entry flammableRate) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.flammableRate = flammableRate;
        this.fuelValue = fuelValue;
        this.hasItem = hasItem;
        this.cutout = cutout;
    }

    public static void registerAll() {
        for (BlocksRegistry value : values()) {
            Block block = value.get();
            Registry.register(Registries.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, value.pathName), block);
            if (isValidFlammableEntry(value.flammableRate)) {
                FlammableBlockRegistry.getDefaultInstance().add(block, value.flammableRate);
            }
            if (value.hasItem) {
                Item item = new BlockItem(block, new FabricItemSettings());
                Registry.register(Registries.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, value.pathName), item);
                ItemGroupEvents.modifyEntriesEvent(RedBrainsSurvivalMod.SURVIVAL_BLOCKS).register(content -> {
                    // misc
                    content.add(item);
                });
                if (value.fuelValue > 0) {
                    FuelRegistry.INSTANCE.add(item, value.fuelValue);
                }
            }
        }
    }
    
    public static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return true;
    }

    public static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT; // TODO add jungle spider
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (BlocksRegistry value : values()) {
            if (value.cutout == 1) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutout());
            } else if (value.cutout == 2) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderLayer.getCutoutMipped());
            }
        }
    }

    public Block get() {
        if (block == null) {
            block = blockSupplier.get();
        }

        return block;
    }
}
