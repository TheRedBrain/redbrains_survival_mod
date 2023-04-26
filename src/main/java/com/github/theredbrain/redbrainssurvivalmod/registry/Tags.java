package com.github.theredbrain.redbrainssurvivalmod.registry;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class Tags {

    public static final TagKey<Biome> NO_PRECIPITATION = TagKey.of(RegistryKeys.BIOME, new Identifier(RedBrainsSurvivalMod.MOD_ID, "no_precipitation"));
    public static final TagKey<Biome> SNOW_PRECIPITATION = TagKey.of(RegistryKeys.BIOME, new Identifier(RedBrainsSurvivalMod.MOD_ID, "snow_precipitation"));
    public static final TagKey<Biome> UNAFFECTED_BY_SEASONS = TagKey.of(RegistryKeys.BIOME, new Identifier(RedBrainsSurvivalMod.MOD_ID, "unaffected_by_seasons"));

    public static final TagKey<Block> COPPER_BLOCKS_FOR_BEACON = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "copper_blocks_for_beacon"));
    public static final TagKey<Block> FARM_LAND = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "farm_land"));
    public static final TagKey<Block> FARM_LAND_PLANTER = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "farm_land_planter"));
    public static final TagKey<Block> FERTILIZABLE_FARM_LAND = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "fertilizable_farm_land"));
    public static final TagKey<Block> IGNITABLE_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "ignitable_blocks"));
    public static final TagKey<Block> MECHANICAL_POWER_TRANSMITTER = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mechanical_power_transmitter"));
    public static final TagKey<Block> HEAT_SOURCES_HOT = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "heat_sources_hot"));
    public static final TagKey<Block> HEAT_SOURCES_VERY_HOT = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "heat_sources_very_hot"));
    public static final TagKey<Block> NON_FERTILIZABLE_FARM_LAND = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "non_fertilizable_farm_land"));
    public static final TagKey<Block> NO_WEED_FARM_LAND = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "no_weed_farm_land"));
    public static final TagKey<Block> HEAT_SOURCES = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "heat_sources"));
    public static final TagKey<Block> POWERABLE_BY_HAND_CRANK = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "powerable_by_hand_crank"));
//    public static final TagKey<Block> AXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/axe_mineable"));
    public static final TagKey<Block> NEED_TOOL_FOR_MINING = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/need_tool_for_mining"));
    public static final TagKey<Block> BLOCKS_HIBACHI_FIRE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "kiln_products"));
    public static final TagKey<Block> NOT_ROTATABLE_BY_TURNTABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "not_rotatable_by_turntable"));
    public static final TagKey<Block> DESTROYED_BY_FALLING_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "destroyed_by_falling_blocks"));
    public static final TagKey<Block> BROKEN_BY_SAW = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "broken_by_saw"));
    public static final TagKey<Block> REPLACED_BY_GOURDS = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "replaced_by_gourds"));

    // effective
    public static final TagKey<Block> POINTY_STICK_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/pointy_stick_effective"));
    public static final TagKey<Block> SHARP_STONE_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/sharp_stone_effective"));
    public static final TagKey<Block> IRON_CHISEL_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/iron_chisel_effective"));
    public static final TagKey<Block> DIAMOND_CHISEL_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/diamond_chisel_effective"));
    public static final TagKey<Block> AXE_EFFECTIVE_TIER_1 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/axe_effective_tier_1"));
    public static final TagKey<Block> AXE_EFFECTIVE_TIER_2 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/axe_effective_tier_2"));
    public static final TagKey<Block> HOE_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/hoe_effective"));
    public static final TagKey<Block> PICKAXE_EFFECTIVE_TIER_1 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/pickaxe_effective_tier_1"));
    public static final TagKey<Block> PICKAXE_EFFECTIVE_TIER_2 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/pickaxe_effective_tier_2"));
    public static final TagKey<Block> PICKAXE_EFFECTIVE_TIER_3 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/pickaxe_effective_tier_3"));
    public static final TagKey<Block> PICKAXE_EFFECTIVE_TIER_4 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/pickaxe_effective_tier_4"));
    public static final TagKey<Block> SHEARS_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/shears_effective"));
    public static final TagKey<Block> SHOVEL_EFFECTIVE_TIER_1 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/shovel_effective_tier_1"));
    public static final TagKey<Block> SHOVEL_EFFECTIVE_TIER_2 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/shovel_effective_tier_2"));
    public static final TagKey<Block> SWORD_EFFECTIVE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "effective/sword_effective"));

    // mineable
    public static final TagKey<Block> POINTY_STICK_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/pointy_stick_mineable"));
    public static final TagKey<Block> SHARP_STONE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/sharp_stone_mineable"));
    public static final TagKey<Block> IRON_CHISEL_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/iron_chisel_mineable"));
    public static final TagKey<Block> DIAMOND_CHISEL_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/diamond_chisel_mineable"));
    public static final TagKey<Block> AXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/axe_mineable"));
    public static final TagKey<Block> PICKAXE_MINEABLE_TIER_1 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/pickaxe_mineable_tier_1"));
    public static final TagKey<Block> PICKAXE_MINEABLE_TIER_2 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/pickaxe_mineable_tier_2"));
    public static final TagKey<Block> PICKAXE_MINEABLE_TIER_3 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/pickaxe_mineable_tier_3"));
    public static final TagKey<Block> PICKAXE_MINEABLE_TIER_4 = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/pickaxe_mineable_tier_4"));
//    public static final TagKey<Block> SHOVEL_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/shovel_mineable"));
//    public static final TagKey<Block> SWORD_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/sword_mineable"));
    public static final TagKey<Block> NO_DAMAGE_AXE_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/no_damage_axe_mineable"));
    public static final TagKey<Block> HAND_MINEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mineable/hand_mineable"));
    
    public static final TagKey<Block> CAN_LIT_TORCHES = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "can_lit_torches"));
    public static final TagKey<Block> TURNTABLE_ROTATABLE_BLOCKS_ATTACHED = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "turntable_rotatable_blocks_attached"));
    public static final TagKey<Block> KILN_PRODUCTS = TagKey.of(RegistryKeys.BLOCK, new Identifier(RedBrainsSurvivalMod.MOD_ID, "blocks_hibachi_fire"));
    public static final TagKey<Item> FERTILIZERS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "fertilizers"));
    public static final TagKey<Item> PROPER_DIGGING_TOOLS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "tools/proper_digging_tools"));
    public static final TagKey<Item> CHISEL_SPLITTING_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "chisel_splitting_items"));
    public static final TagKey<Item> AXE_SPLITTING_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "axe_splitting_items"));
    public static final TagKey<Item> MORTAR_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "mortar_items"));
    public static final TagKey<Item> CRAFTING_CHISELS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "tools/crafting_chisels"));
    public static final TagKey<Item> LOOSE_STONE_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "loose_stone_items"));
    public static final TagKey<Item> STONE_BRICK_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "stone_brick_items"));
    public static final TagKey<Item> VERY_LIGHT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "very_light_items"));
    public static final TagKey<Item> FAIRLY_LIGHT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "fairly_light_items"));
    public static final TagKey<Item> LIGHT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "light_items"));
    public static final TagKey<Item> BREAKS_VASES = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "breaks_vases"));
    public static final TagKey<Item> FIRE_STARTERS = TagKey.of(RegistryKeys.ITEM, new Identifier(RedBrainsSurvivalMod.MOD_ID, "fire_starters"));
    public static final TagKey<EntityType<?>> STARTS_PATROLS_WHEN_KILLED = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(RedBrainsSurvivalMod.MOD_ID, "starts_patrols_when_killed"));
}
