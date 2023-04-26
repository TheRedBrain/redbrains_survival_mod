package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.item.*;
import com.github.theredbrain.redbrainssurvivalmod.item.HandicraftItem;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;

public enum ItemsRegistry {

    // misc items
    FABRIC("fabric", () -> new Item(new FabricItemSettings())),
    PADDING("padding", () -> new Item(new FabricItemSettings())),
    MOULD("mould", () -> new Item(new FabricItemSettings())),
    GEAR("gear", () -> new Item(new FabricItemSettings())),
    STRAP("strap", () -> new Item(new FabricItemSettings())),
    BELT("belt", () -> new Item(new FabricItemSettings())),
    HAFT("haft", () -> new Item(new FabricItemSettings())),
    LEATHER_CUT("leather_cut", () -> new Item(new FabricItemSettings())),
    IRON_SCRAP("iron_scrap", () -> new Item(new FabricItemSettings())),
    COPPER_NUGGET("copper_nugget", () -> new Item(new FabricItemSettings())),
    HELLFIRE_DUST("hellfire_dust", () -> new Item(new FabricItemSettings())),
    BONE_FISHING_HOOK("bone_fishing_hook", () -> new Item(new FabricItemSettings().maxCount(1))),
    WOOL_KNIT_WHITE("wool_knit_white", () -> new ColouredItem(0xF9FFFE, new FabricItemSettings())),
    WOOL_KNIT_ORANGE("wool_knit_orange", () -> new ColouredItem(16351261, new FabricItemSettings())),
    WOOL_KNIT_MAGENTA("wool_knit_magenta", () -> new ColouredItem(13061821, new FabricItemSettings())),
    WOOL_KNIT_LIGHT_BLUE("wool_knit_light_blue", () -> new ColouredItem(3847130, new FabricItemSettings())),
    WOOL_KNIT_YELLOW("wool_knit_yellow", () -> new ColouredItem(16701501, new FabricItemSettings())),
    WOOL_KNIT_LIME("wool_knit_lime", () -> new ColouredItem(8439583, new FabricItemSettings())),
    WOOL_KNIT_PINK("wool_knit_pink", () -> new ColouredItem(15961002, new FabricItemSettings())),
    WOOL_KNIT_GRAY("wool_knit_gray", () -> new ColouredItem(4673362, new FabricItemSettings())),
    WOOL_KNIT_LIGHT_GRAY("wool_knit_light_gray", () -> new ColouredItem(0x9D9D97, new FabricItemSettings())),
    WOOL_KNIT_CYAN("wool_knit_cyan", () -> new ColouredItem(1481884, new FabricItemSettings())),
    WOOL_KNIT_PURPLE("wool_knit_purple", () -> new ColouredItem(8991416, new FabricItemSettings())),
    WOOL_KNIT_BLUE("wool_knit_blue", () -> new ColouredItem(3949738, new FabricItemSettings())),
    WOOL_KNIT_BROWN("wool_knit_brown", () -> new ColouredItem(8606770, new FabricItemSettings())),
    WOOL_KNIT_GREEN("wool_knit_green", () -> new ColouredItem(6192150, new FabricItemSettings())),
    WOOL_KNIT_RED("wool_knit_red", () -> new ColouredItem(11546150, new FabricItemSettings())),
    WOOL_KNIT_BLACK("wool_knit_black", () -> new ColouredItem(0x1D1D21, new FabricItemSettings())),
    WICKER("wicker", () -> new Item(new FabricItemSettings())),
    KNITTING_NEEDLES("knitting_needles", () -> new Item(new FabricItemSettings().maxCount(1))),
    DIAMOND_INGOT("diamond_ingot", () -> new Item(new FabricItemSettings())),
    REDSTONE_EYE("redstone_eye", () -> new Item(new FabricItemSettings())),

    // decoration
    TORCH("torch", () -> new CustomTorchItem(false, false, Blocks.TORCH, Blocks.WALL_TORCH, new FabricItemSettings(), Direction.DOWN)),
    TORCH_UNLIT("torch_unlit", () -> new UnlitTorchItem(TORCH.get(), BlocksRegistry.TORCH_UNLIT.get(), BlocksRegistry.WALL_TORCH_UNLIT.get(), new FabricItemSettings(), Direction.DOWN)),
    CRUDE_TORCH("crude_torch", () -> new CustomTorchItem(true, true, BlocksRegistry.CRUDE_TORCH.get(), BlocksRegistry.CRUDE_WALL_TORCH.get(), new FabricItemSettings(), Direction.DOWN)),
    CRUDE_TORCH_UNLIT("crude_torch_unlit", () -> new UnlitTorchItem(CRUDE_TORCH.get(), BlocksRegistry.CRUDE_TORCH_UNLIT.get(), BlocksRegistry.CRUDE_WALL_TORCH_UNLIT.get(), new FabricItemSettings(), Direction.DOWN)),

    // handicraft items
    UNFINISHED_FISH_HOOK("unfinished_fishing_hook", () -> new HandicraftItem(ImmutableList.of(BONE_FISHING_HOOK.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_WHITE("knitting_white", () -> new ColouredHandicraftItem(0xF9FFFE, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_WHITE.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_ORANGE("knitting_orange", () -> new ColouredHandicraftItem(16351261, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_ORANGE.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_MAGENTA("knitting_magenta", () -> new ColouredHandicraftItem(13061821, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_MAGENTA.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_LIGHT_BLUE("knitting_light_blue", () -> new ColouredHandicraftItem(3847130, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_LIGHT_BLUE.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_YELLOW("knitting_yellow", () -> new ColouredHandicraftItem(16701501, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_YELLOW.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_LIME("knitting_lime", () -> new ColouredHandicraftItem(8439583, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_LIME.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_PINK("knitting_pink", () -> new ColouredHandicraftItem(15961002, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_PINK.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_GRAY("knitting_gray", () -> new ColouredHandicraftItem(4673362, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_GRAY.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_LIGHT_GRAY("knitting_light_gray", () -> new ColouredHandicraftItem(0x9D9D97, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_LIGHT_GRAY.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_CYAN("knitting_cyan", () -> new ColouredHandicraftItem(1481884, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_CYAN.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_PURPLE("knitting_purple", () -> new ColouredHandicraftItem(8991416, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_PURPLE.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_BLUE("knitting_blue", () -> new ColouredHandicraftItem(3949738, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_BLUE.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_BROWN("knitting_brown", () -> new ColouredHandicraftItem(8606770, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_BROWN.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_GREEN("knitting_green", () -> new ColouredHandicraftItem(6192150, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_GREEN.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_RED("knitting_red", () -> new ColouredHandicraftItem(11546150, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_RED.get()), 10, new FabricItemSettings().maxCount(1))),
    KNITTING_BLACK("knitting_black", () -> new ColouredHandicraftItem(0x1D1D21, ImmutableList.of(KNITTING_NEEDLES.get(), WOOL_KNIT_BLACK.get()), 10, new FabricItemSettings().maxCount(1))),
    WICKER_WEAVING("wicker_weaving", () -> new HandicraftItem(ImmutableList.of(WICKER.get()), 10, new FabricItemSettings().maxCount(1))),

    // block drops
    STRAW("straw", () -> new Item(new FabricItemSettings())),
    HEMP("hemp", () -> new Item(new FabricItemSettings())),
    SAW_DUST("saw_dust", () -> new Item(new FabricItemSettings())),
    SOUL_DUST("soul_dust", () -> new Item(new FabricItemSettings())),

    // mob drops
    CHICKEN("chicken", () -> new Item(new FabricItemSettings().food(Foods.CHICKEN))),
    MUTTON("mutton", () -> new Item(new FabricItemSettings().food(Foods.MUTTON))),
    BEEF("beef", () -> new Item(new FabricItemSettings().food(Foods.BEEF))),
    PORK_CHOP("pork_chop", () -> new Item(new FabricItemSettings().food(Foods.PORK_CHOP))),
    WOLF_CHOP("wolf_chop", () -> new Item(new FabricItemSettings().food(Foods.WOLF_CHOP))),
    ROTTEN_FLESH("rotten_flesh", () -> new Item(new FabricItemSettings().food(Foods.ROTTEN_FLESH))),
    BAT_WING("bat_wing", () -> new Item(new FabricItemSettings().food(Foods.BAT_WING))),
    MYSTERY_MEAT("mystery_meat", () -> new Item(new FabricItemSettings().food(Foods.MYSTERY_MEAT))),
    LIVER_OF_THE_BEAST("liver_of_the_beast", () -> new Item(new FabricItemSettings().food(Foods.LIVER_OF_THE_BEAST))),
    CREEPER_OYSTERS("creeper_oysters", () -> new Item(new FabricItemSettings().food(Foods.CREEPER_OYSTER))),
    NITRE("nitre", () -> new Item(new FabricItemSettings())),
    SPIDER_EYE("spider_eye", () -> new Item(new FabricItemSettings().food(Foods.SPIDER_EYE))),
    WOOL_WHITE("wool_white", () -> new ColouredItem(0xF9FFFE, new FabricItemSettings())),
    WOOL_ORANGE("wool_orange", () -> new ColouredItem(16351261, new FabricItemSettings())),
    WOOL_MAGENTA("wool_magenta", () -> new ColouredItem(13061821, new FabricItemSettings())),
    WOOL_LIGHT_BLUE("wool_light_blue", () -> new ColouredItem(3847130, new FabricItemSettings())),
    WOOL_YELLOW("wool_yellow", () -> new ColouredItem(16701501, new FabricItemSettings())),
    WOOL_LIME("wool_lime", () -> new ColouredItem(8439583, new FabricItemSettings())),
    WOOL_PINK("wool_pink", () -> new ColouredItem(15961002, new FabricItemSettings())),
    WOOL_GRAY("wool_gray", () -> new ColouredItem(4673362, new FabricItemSettings())),
    WOOL_LIGHT_GRAY("wool_light_gray", () -> new ColouredItem(0x9D9D97, new FabricItemSettings())),
    WOOL_CYAN("wool_cyan", () -> new ColouredItem(1481884, new FabricItemSettings())),
    WOOL_PURPLE("wool_purple", () -> new ColouredItem(8991416, new FabricItemSettings())),
    WOOL_BLUE("wool_blue", () -> new ColouredItem(3949738, new FabricItemSettings())),
    WOOL_BROWN("wool_brown", () -> new ColouredItem(8606770, new FabricItemSettings())),
    WOOL_GREEN("wool_green", () -> new ColouredItem(6192150, new FabricItemSettings())),
    WOOL_RED("wool_red", () -> new ColouredItem(11546150, new FabricItemSettings())),
    WOOL_BLACK("wool_black", () -> new ColouredItem(0x1D1D21, new FabricItemSettings())),

    // grinding / millstone
    GROUND_NETHERRACK("ground_netherrack", () -> new Item(new FabricItemSettings())),
    COCOA_POWDER("cocoa_powder", () -> new Item(new FabricItemSettings())),
    FLOUR("flour", () -> new Item(new FabricItemSettings())),
    HEMP_FIBRE("hemp_fibre", () -> new Item(new FabricItemSettings())),
    LEATHER_SCOURED("leather_scoured", () -> new Item(new FabricItemSettings())),
    LEATHER_SCOURED_CUT("leather_scoured_cut", () -> new Item(new FabricItemSettings())),

    // cooking / unstoked cauldron
    NETHER_SLUDGE("nether_sludge", () -> new Item(new FabricItemSettings())),
    BLASTING_OIL("blasting_oil", () -> new Item(new FabricItemSettings())),
    NETHER_COAL("nether_coal", () -> new Item(new FabricItemSettings())),
    FUSE("fuse", () -> new Item(new FabricItemSettings())),
    FILAMENT("filament", () -> new Item(new FabricItemSettings())),
    ELEMENT("element", () -> new Item(new FabricItemSettings())),
    CONCENTRATED_HELLFIRE("concentrated_hellfire", () -> new Item(new FabricItemSettings())),
    LEATHER_TANNED("leather_tanned", () -> new Item(new FabricItemSettings())),
    LEATHER_TANNED_CUT("leather_tanned_cut", () -> new Item(new FabricItemSettings())),
    // TODO blood_wood_sapling
    // TODO cement_bucket
    // TODO nether_groth_spores

    // rendering / stoked cauldron
    POTASH("potash", () -> new Item(new FabricItemSettings())),
    SOUL_FLUX("soul_flux", () -> new Item(new FabricItemSettings())),
    BRIMSTONE("brimstone", () -> new Item(new FabricItemSettings())),
    TALLOW("tallow", () -> new Item(new FabricItemSettings())),
    SOAP("soap", () -> new Item(new FabricItemSettings())),
    GLUE("glue", () -> new Item(new FabricItemSettings())),

    // firing / kiln
    ENDER_SLAG("ender_slag", () -> new Item(new FabricItemSettings())),

    // food
    BEETROOT_SEEDS("beetroot_seeds", () -> new AliasedBlockItem(BlocksRegistry.BEETROOT_CROP.get(), new FabricItemSettings())),
    WHEAT_SEEDS("wheat_seeds", () -> new AliasedBlockItem(BlocksRegistry.WHEAT_CROP.get(), new FabricItemSettings())),
    HEMP_SEEDS("hemp_seeds", () -> new AliasedBlockItem(BlocksRegistry.HEMP_CROP.get(), new FabricItemSettings())),
    MELON_SEEDS("melon_seeds", () -> new AliasedBlockItem(BlocksRegistry.MELON_STEM.get(), new FabricItemSettings())),

    // basic food
    SWEET_BERRIES("sweet_berries", () -> new Item(new FabricItemSettings().maxCount(16).food(FoodComponents.SWEET_BERRIES))),
    RED_MUSHROOM("red_mushroom", () -> new AliasedBlockItem(BlocksRegistry.RED_MUSHROOM.get(), new FabricItemSettings().maxCount(16).food(Foods.RED_MUSHROOM))),
    BROWN_MUSHROOM("brown_mushroom", () -> new AliasedBlockItem(BlocksRegistry.BROWN_MUSHROOM.get(), new FabricItemSettings().maxCount(16).food(Foods.BROWN_MUSHROOM))),
    PUMPKIN_SEEDS("pumpkin_seeds", () -> new AliasedBlockItem(BlocksRegistry.PUMPKIN_STEM.get(), new FabricItemSettings().maxCount(16).food(Foods.PUMPKIN_SEEDS))),
    COCOA_BEANS("cocoa_beans", () -> new AliasedBlockItem(BlocksRegistry.COCOA.get(), new FabricItemSettings().maxCount(16).food(Foods.COCOA_BEANS))),
    COOKED_CHICKEN("cooked_chicken", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_CHICKEN))),
    COOKED_MUTTON("cooked_mutton", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_MUTTON))),
    COOKED_BEEF("cooked_beef", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_BEEF))),
    COOKED_PORK_CHOP("cooked_pork_chop", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_PORK_CHOP))),
    COOKED_WOLF_CHOP("cooked_wolf_chop", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_WOLF_CHOP))),
    CURED_MEAT("cured_meat", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.CURED_MEAT))),
    BURNED_MEAT("burned_meat", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.BURNED_MEAT))),

    // iron age food
    MASHED_MELON("mashed_melon", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.MASHED_MELON))),
    MELON_SLICE("melon_slice", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.MELON_SLICE))),
    MILK("milk", () -> new MilkBucketItem(new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).food(Foods.MILK))),
    CHOCOLATE_MILK("chocolate_milk", () -> new MilkBucketItem(new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1).food(Foods.CHOCOLATE_MILK))),
    COD("cod", () -> new Item(new FabricItemSettings().food(Foods.COD))),
    COOKED_COD("cooked_cod", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_COD))),
    SALMON("salmon", () -> new Item(new FabricItemSettings().food(Foods.SALMON))),
    COOKED_SALMON("cooked_salmon", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_SALMON))),
    RAW_EGG("raw_egg", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.RAW_EGG))),
    FRIED_EGG("fried_egg", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.FRIED_EGG))),
    SCRAMBLED_EGG("scrambled_egg", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.SCRAMBLED_EGG))),
    COOKED_SCRAMBLED_EGG("cooked_scrambled_egg", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_SCRAMBLED_EGG))),
    MUSHROOM_OMELET("mushroom_omelet", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.MUSHROOM_OMELET))),
    COOKED_MUSHROOM_OMELET("cooked_mushroom_omelet", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_MUSHROOM_OMELET))),
    HAM_AND_EGGS("ham_and_eggs", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.HAM_AND_EGGS))),

    // cauldron age food
    CHOCOLATE("chocolate", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.CHOCOLATE))),
    CREAM_OF_MUSHROOM("cream_of_mushroom", () -> new StewItem(new FabricItemSettings().maxCount(16).food(Foods.CREAM_OF_MUSHROOM))),
    POACHED_EGG("poached_egg", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.POACHED_EGG))),
    CHOWDER("chowder", () -> new StewItem(new FabricItemSettings().maxCount(16).food(Foods.CHOWDER))),
    KIBBLE("kibble", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.KIBBLE))),
    FOUL_FOOD("foul_food", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.FOUL_FOOD))),

    // domesticated crops food
    CARROT("carrot", () -> new AliasedBlockItem(BlocksRegistry.CARROT_CROP.get(), new FabricItemSettings().maxCount(16).food(Foods.CARROT))),
    POTATO("potato", () -> new AliasedBlockItem(BlocksRegistry.POTATO_CROP.get(), new FabricItemSettings().maxCount(16).food(Foods.POTATO))),
    BREAD("bread", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.BREAD))),
    TASTY_SANDWICH("tasty_sandwich", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.TASTY_SANDWICH))),
    COOKIE("cookie", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKIE))),
    PUMPKIN_PIE("pumpkin_pie", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.PUMPKIN_PIE))),
    DONUT("donut", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.DONUT))),
    POISONOUS_POTATO("poisonous_potato", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.POISONOUS_POTATO))),
    BAKED_POTATO("baked_potato", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.BAKED_POTATO))),
    BOILED_POTATO("boiled_potato", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.BOILED_POTATO))),
    STEAK_AND_POTATOES("steak_and_potatoes", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.STEAK_AND_POTATOES))),
    COOKED_CARROT("cooked_carrot", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_CARROT))),
    RAW_KEBAB("raw_kebab", () -> new KebabItem(new FabricItemSettings().maxCount(16).food(Foods.RAW_KEBAB))),
    KEBAB("kebab", () -> new KebabItem(new FabricItemSettings().maxCount(16).food(Foods.KEBAB))),
    STEAK_DINNER("steak_dinner", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.STEAK_DINNER))),
    PORK_DINNER("pork_dinner", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.PORK_DINNER))),
    WOLF_DINNER("wolf_dinner", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.WOLF_DINNER))),
    CHICKEN_SOUP("chicken_soup", () -> new StewItem(new FabricItemSettings().maxCount(16).food(Foods.CHICKEN_SOUP))),
    HEARTY_STEW("hearty_stew", () -> new StewItem(new FabricItemSettings().maxCount(16).food(Foods.HEARTY_STEW))),

    // other food
    APPLE("apple", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.APPLE))),
    GOLDEN_APPLE("golden_apple", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.GOLDEN_APPLE))),
    ENCHANTED_GOLDEN_APPLE("enchanted_golden_apple", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.ENCHANTED_GOLDEN_APPLE))),
    GOLDEN_CARROT("golden_carrot", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.GOLDEN_CARROT))),
    COOKED_MYSTERY_MEAT("cooked_mystery_meat", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_MYSTERY_MEAT))),
    COOKED_LIVER("cooked_liver", () -> new Item(new FabricItemSettings().maxCount(16).food(Foods.COOKED_LIVER))),

    // bark
    ACACIA_BARK("acacia_bark", () -> new Item(new FabricItemSettings())),
    BIRCH_BARK("birch_bark", () -> new Item(new FabricItemSettings())),
    CHERRY_BARK("cherry_bark", () -> new Item(new FabricItemSettings())),
    CRIMSON_BARK("crimson_bark", () -> new Item(new FabricItemSettings())),
    DARK_OAK_BARK("dark_oak_bark", () -> new Item(new FabricItemSettings())),
    JUNGLE_BARK("jungle_bark", () -> new Item(new FabricItemSettings())),
    MANGROVE_BARK("mangrove_bark", () -> new Item(new FabricItemSettings())),
    OAK_BARK("oak_bark", () -> new Item(new FabricItemSettings())),
    SPRUCE_BARK("spruce_bark", () -> new Item(new FabricItemSettings())),
    WARPED_BARK("warped_bark", () -> new Item(new FabricItemSettings())),

    // loose stones
    LOOSE_STONE("loose_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_ANDESITE_STONE("loose_andesite_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_DIORITE_STONE("loose_diorite_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_TUFF_STONE("loose_tuff_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_DEEPSLATE_STONE("loose_deepslate_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_GRANITE_STONE("loose_granite_stone", () -> new Item(new FabricItemSettings())),
    LOOSE_CALCITE_STONE("loose_calcite_stone", () -> new Item(new FabricItemSettings())),

    // stone brick
    STONE_BRICK("stone_brick", () -> new Item(new FabricItemSettings())),
    ANDESITE_BRICK("andesite_brick", () -> new Item(new FabricItemSettings())),
    DIORITE_BRICK("diorite_brick", () -> new Item(new FabricItemSettings())),
    TUFF_BRICK("tuff_brick", () -> new Item(new FabricItemSettings())),
    DEEPSLATE_BRICK("deepslate_brick", () -> new Item(new FabricItemSettings())),
    GRANITE_BRICK("granite_brick", () -> new Item(new FabricItemSettings())),

    // ore piles
    COAL_DUST("coal_dust", () -> new Item(new FabricItemSettings())),
    IRON_ORE_PILE("iron_ore_pile", () -> new Item(new FabricItemSettings())),
    COPPER_ORE_PILE("copper_ore_pile", () -> new Item(new FabricItemSettings())),
    GOLD_ORE_PILE("gold_ore_pile", () -> new Item(new FabricItemSettings())),

    // soil piles
    DIRT_PILE("dirt_pile", () -> new Item(new FabricItemSettings())),
    GRAVEL_PILE("gravel_pile", () -> new Item(new FabricItemSettings())),
    RED_SAND_PILE("red_sand_pile", () -> new Item(new FabricItemSettings())),
    SAND_PILE("sand_pile", () -> new Item(new FabricItemSettings())),
    SOUL_SAND_PILE("soul_sand_pile", () -> new Item(new FabricItemSettings())),

    FIRE_PLOUGH("fire_plough", () -> new FireStarterItem(300, new FabricItemSettings().maxCount(1).maxDamage(1))),
    BOW_DRILL("bow_drill", () -> new FireStarterItem(200, new FabricItemSettings().maxCount(1).maxDamage(6))),
    FLINT_AND_STEEL("flint_and_steel", () -> new FireStarterItem(100, new FabricItemSettings().maxCount(1).maxDamage(12))),

    IRON_SHEAR("iron_shears", () -> new CustomShearsItem(true, Tags.SHEARS_EFFECTIVE, Tags.HAND_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(238))),
    DIAMOND_SHEAR("diamond_shears", () -> new CustomShearsItem(false, Tags.SHEARS_EFFECTIVE, Tags.HAND_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(714))),

//    NETHERITE_BUCKET("netherite_bucket", () -> new NetheriteBucketItem(Fluids.EMPTY, new FabricItemSettings().maxCount(16).fireproof())),
//    NETHERITE_LAVA_BUCKET("netherite_lava_bucket", () -> new NetheriteBucketItem(Fluids.LAVA, new FabricItemSettings().recipeRemainder(NETHERITE_BUCKET.get()).maxCount(1).fireproof())),

    POINTY_STICK("pointy_stick", () -> new ChiselItem(1.5F, Tags.POINTY_STICK_EFFECTIVE, Tags.POINTY_STICK_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(2))),
    SHARP_STONE("sharp_stone", () -> new ChiselItem(2.0F, Tags.SHARP_STONE_EFFECTIVE, Tags.SHARP_STONE_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(8))),
    IRON_CHISEL("iron_chisel", () -> new CraftingChiselItem(true, 3.0F, Tags.IRON_CHISEL_EFFECTIVE, Tags.IRON_CHISEL_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(50))),
    DIAMOND_CHISEL("diamond_chisel", () -> new CraftingChiselItem(false, 4.0F, Tags.DIAMOND_CHISEL_EFFECTIVE, Tags.DIAMOND_CHISEL_MINEABLE, new FabricItemSettings().maxCount(1).maxDamage(150))),

    STONE_SHOVEL("stone_shovel", () -> new CustomToolItem(true, false, 5, 2.0f, -3.0f, 4.0f, Tags.SHOVEL_EFFECTIVE_TIER_1, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(50))),
    GOLDEN_SHOVEL("golden_shovel", () -> new CustomToolItem(true, false, 22, 1.0f, -3.0f, 12.0f, Tags.SHOVEL_EFFECTIVE_TIER_2, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(32))),
    IRON_SHOVEL("iron_shovel", () -> new CustomToolItem(true, false, 14, 3.0f, -3.0f, 6.0f, Tags.SHOVEL_EFFECTIVE_TIER_2, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(500))),
    DIAMOND_SHOVEL("diamond_shovel", () -> new CustomToolItem(true, false, 10, 4.0f, -3.0f, 8.0f, Tags.SHOVEL_EFFECTIVE_TIER_2, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(1561))),
    REFINED_SHOVEL("refined_shovel", () -> new CustomToolItem(true, false, 0, 5.0f, -3.0f, 9.0f, Tags.SHOVEL_EFFECTIVE_TIER_2, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(2250))),

    STONE_PICKAXE("stone_pickaxe", () -> new CustomToolItem(true, false, 5, 3.0f, -2.8f, 4.0f, Tags.PICKAXE_EFFECTIVE_TIER_1, Tags.PICKAXE_MINEABLE_TIER_1, null, new FabricItemSettings().maxDamage(50))),
    GOLDEN_PICKAXE("golden_pickaxe", () -> new CustomToolItem(true, false, 22, 2.0f, -2.8f, 12.0f, Tags.PICKAXE_EFFECTIVE_TIER_1, Tags.PICKAXE_MINEABLE_TIER_1, null, new FabricItemSettings().maxDamage(32))),
    IRON_PICKAXE("iron_pickaxe", () -> new CustomToolItem(true, false, 14, 4.0f, -2.8f, 6.0f, Tags.PICKAXE_EFFECTIVE_TIER_2, Tags.PICKAXE_MINEABLE_TIER_2, null, new FabricItemSettings().maxDamage(500))),
    DIAMOND_PICKAXE("diamond_pickaxe", () -> new CustomToolItem(true, false, 10, 5.0f, -2.8f, 8.0f, Tags.PICKAXE_EFFECTIVE_TIER_3, Tags.PICKAXE_MINEABLE_TIER_3, null, new FabricItemSettings().maxDamage(1561))),
    REFINED_PICKAXE("refined_pickaxe", () -> new CustomToolItem(true, false, 0, 6.0f, -2.8f, 9.0f, Tags.PICKAXE_EFFECTIVE_TIER_4, Tags.PICKAXE_MINEABLE_TIER_4, null, new FabricItemSettings().maxDamage(2250))),
    MATTOCK("mattock", () -> new CustomToolItem(true, false, 0, 6.0f, -2.8f, 9.0f, Tags.PICKAXE_EFFECTIVE_TIER_4, Tags.PICKAXE_MINEABLE_TIER_4, null, new FabricItemSettings().maxDamage(2250))),

    STONE_AXE("stone_axe", () -> new CustomAxeItem(true, false, true, false, 5, 4.0f, -3.2f, 4.0f, Tags.AXE_EFFECTIVE_TIER_1, Tags.AXE_MINEABLE, null, new FabricItemSettings().maxDamage(50))),
    GOLDEN_AXE("golden_axe", () -> new CustomAxeItem(true, false, true, false, 22, 3.0f, -3.2f, 12.0f, Tags.AXE_EFFECTIVE_TIER_2, Tags.AXE_MINEABLE, null, new FabricItemSettings().maxDamage(32))),
    IRON_AXE("iron_axe", () -> new CustomAxeItem(true, true, true, false, 14, 5.0f, -3.2f, 6.0f, Tags.AXE_EFFECTIVE_TIER_2, Tags.AXE_MINEABLE, null, new FabricItemSettings().maxDamage(500))),
    DIAMOND_AXE("diamond_axe", () -> new CustomAxeItem(false, true, true, false, 10, 6.0f, -3.2f, 8.0f, Tags.AXE_EFFECTIVE_TIER_2, Tags.AXE_MINEABLE, Tags.NO_DAMAGE_AXE_MINEABLE, new FabricItemSettings().maxDamage(1561))),
    REFINED_AXE("refined_axe", () -> new CustomAxeItem(false, true, true, false, 0, 7.0f, -3.2f, 9.0f, Tags.AXE_EFFECTIVE_TIER_2, Tags.AXE_MINEABLE, Tags.NO_DAMAGE_AXE_MINEABLE, new FabricItemSettings().maxDamage(2250))),
    BATTLE_AXE("battle_axe", () -> new CustomAxeItem(false, true, true, true, 0, 8.0f, -3.2f, 9.0f, Tags.AXE_EFFECTIVE_TIER_2, Tags.AXE_MINEABLE, Tags.NO_DAMAGE_AXE_MINEABLE, new FabricItemSettings().maxDamage(2250))),

    GOLDEN_HOE("golden_hoe", () -> new CustomToolItem(true, false, 22, 1.0f, -3.0f, 12.0f, Tags.HOE_EFFECTIVE,  Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(32))),
    IRON_HOE("iron_hoe", () -> new CustomToolItem(true, false, 14, 3.0f, -3.0f, 6.0f, Tags.HOE_EFFECTIVE,  Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(500))),
    DIAMOND_HOE("diamond_hoe", () -> new CustomToolItem(true, false, 10, 4.0f, -3.0f, 8.0f, Tags.HOE_EFFECTIVE,  Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(1561))),
    REFINED_HOE("refined_hoe", () -> new CustomToolItem(true, false, 0, 5.0f, -3.0f, 9.0f, Tags.HOE_EFFECTIVE,  Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(2250))),

    WOODEN_CLUB("wooden_club", () -> new CustomToolItem(false, true, 0, 2.0f, -2.4f, 1.0f, null, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(50))),
    BONE_CLUB("bone_club", () -> new CustomToolItem(false, true, 0, 4.0f, -2.4f, 1.0f, null, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(50))),
//    STONE_SWORD("stone_sword", () -> new CustomToolItem(false, true, 5, 2.0f, -2.4f, 4.0f, BlockTags.SWORD_MINEABLE, null, new FabricItemSettings().maxDamage(50))),
    GOLDEN_SWORD("golden_sword", () -> new CustomToolItem(false, true, 22, 4.0f, -2.4f, 15.0f, Tags.SWORD_EFFECTIVE, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(32))),
    IRON_SWORD("iron_sword", () -> new CustomToolItem(false, true, 14, 6.0f, -2.4f, 15.0f, Tags.SWORD_EFFECTIVE, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(500))),
    DIAMOND_SWORD("diamond_sword", () -> new CustomToolItem(false, true, 10, 7.0f, -2.4f, 15.0f, Tags.SWORD_EFFECTIVE, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(1561))),
    REFINED_SWORD("refined_sword", () -> new CustomToolItem(false, true, 0, 8.0f, -2.4f, 15.0f, Tags.SWORD_EFFECTIVE, Tags.HAND_MINEABLE, null, new FabricItemSettings().maxDamage(2250)));

    private final String pathName;
    private final Supplier<Item> itemSupplier;
    private Item item;
    
    ItemsRegistry(String pathName, Supplier<Item> itemSupplier) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
    }

    public static void registerAll() {
        for (ItemsRegistry value : values()) {
            Item item = value.get();
            Registry.register(Registries.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, value.pathName), item);
            ItemGroupEvents.modifyEntriesEvent(RedBrainsSurvivalMod.SURVIVAL_ITEMS).register(content -> {
                // misc
                content.add(item);
            });
        }
    }

    public Item get() {
        if (item == null) {
            item = itemSupplier.get();
        }

        return item;
    }
}
