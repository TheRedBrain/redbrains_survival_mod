package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.block.SawBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SawBlockEntity extends BlockEntity {
    protected static final Map<Block, List<Item>> SAWED_BLOCKS = new ImmutableMap.Builder<Block, List<Item>>()
            // logs
            .put(BlocksRegistry.ACACIA_LOG.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.ACACIA_BARK.get()))
            .put(BlocksRegistry.ACACIA_WOOD.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), BlocksRegistry.ACACIA_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.ACACIA_BARK.get()))
            .put(BlocksRegistry.BIRCH_LOG.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.BIRCH_BARK.get()))
            .put(BlocksRegistry.BIRCH_WOOD.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), BlocksRegistry.BIRCH_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.BIRCH_BARK.get()))
            .put(BlocksRegistry.DARK_OAK_LOG.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.DARK_OAK_BARK.get()))
            .put(BlocksRegistry.DARK_OAK_WOOD.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.DARK_OAK_BARK.get()))
            .put(BlocksRegistry.JUNGLE_LOG.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.JUNGLE_BARK.get()))
            .put(BlocksRegistry.JUNGLE_WOOD.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), BlocksRegistry.JUNGLE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.JUNGLE_BARK.get()))
            .put(BlocksRegistry.MANGROVE_LOG.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.MANGROVE_BARK.get()))
            .put(BlocksRegistry.MANGROVE_WOOD.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), BlocksRegistry.MANGROVE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.MANGROVE_BARK.get()))
            .put(BlocksRegistry.OAK_LOG.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.OAK_BARK.get()))
            .put(BlocksRegistry.OAK_WOOD.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), BlocksRegistry.OAK_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.OAK_BARK.get()))
            .put(BlocksRegistry.SPRUCE_LOG.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SPRUCE_BARK.get()))
            .put(BlocksRegistry.SPRUCE_WOOD.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), BlocksRegistry.SPRUCE_PLANKS.get().asItem(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SAW_DUST.get(), ItemsRegistry.SPRUCE_BARK.get()))

            // wooden sub blocks
            .put(BlocksRegistry.ACACIA_PLANKS.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS_SIDING.get().asItem(), BlocksRegistry.ACACIA_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.ACACIA_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS_EDGE.get().asItem(), BlocksRegistry.ACACIA_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.ACACIA_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS_CORNER.get().asItem(), BlocksRegistry.ACACIA_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.ACACIA_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.ACACIA_PLANKS_EDGE.get().asItem(), BlocksRegistry.ACACIA_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.BIRCH_PLANKS.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS_SIDING.get().asItem(), BlocksRegistry.BIRCH_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.BIRCH_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS_EDGE.get().asItem(), BlocksRegistry.BIRCH_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.BIRCH_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS_CORNER.get().asItem(), BlocksRegistry.BIRCH_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.BIRCH_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.BIRCH_PLANKS_EDGE.get().asItem(), BlocksRegistry.BIRCH_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.DARK_OAK_PLANKS.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS_SIDING.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.DARK_OAK_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS_CORNER.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.DARK_OAK_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get().asItem(), BlocksRegistry.DARK_OAK_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.JUNGLE_PLANKS.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS_SIDING.get().asItem(), BlocksRegistry.JUNGLE_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.JUNGLE_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS_EDGE.get().asItem(), BlocksRegistry.JUNGLE_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.JUNGLE_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS_CORNER.get().asItem(), BlocksRegistry.JUNGLE_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.JUNGLE_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.JUNGLE_PLANKS_EDGE.get().asItem(), BlocksRegistry.JUNGLE_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.MANGROVE_PLANKS.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS_SIDING.get().asItem(), BlocksRegistry.MANGROVE_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.MANGROVE_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS_EDGE.get().asItem(), BlocksRegistry.MANGROVE_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.MANGROVE_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS_CORNER.get().asItem(), BlocksRegistry.MANGROVE_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.MANGROVE_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.MANGROVE_PLANKS_EDGE.get().asItem(), BlocksRegistry.MANGROVE_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.OAK_PLANKS.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS_SIDING.get().asItem(), BlocksRegistry.OAK_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.OAK_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS_EDGE.get().asItem(), BlocksRegistry.OAK_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.OAK_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS_CORNER.get().asItem(), BlocksRegistry.OAK_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.OAK_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.OAK_PLANKS_EDGE.get().asItem(), BlocksRegistry.OAK_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.SPRUCE_PLANKS.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS_SIDING.get().asItem(), BlocksRegistry.SPRUCE_PLANKS_SIDING.get().asItem()))
            .put(BlocksRegistry.SPRUCE_PLANKS_SIDING.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS_EDGE.get().asItem(), BlocksRegistry.SPRUCE_PLANKS_EDGE.get().asItem()))
            .put(BlocksRegistry.SPRUCE_PLANKS_EDGE.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS_CORNER.get().asItem(), BlocksRegistry.SPRUCE_PLANKS_CORNER.get().asItem()))
            .put(BlocksRegistry.SPRUCE_PLANKS_STAIR.get(), ImmutableList.of(BlocksRegistry.SPRUCE_PLANKS_EDGE.get().asItem(), BlocksRegistry.SPRUCE_PLANKS_CORNER.get().asItem()))
            .build();

    private final List<Item> dropsWhenBroken = ImmutableList.of(ItemsRegistry.GEAR.get(), Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET, Items.IRON_NUGGET, ItemsRegistry.STRAP.get(), ItemsRegistry.STRAP.get(), ItemsRegistry.STRAP.get());

//    protected static final Map<Block, List<Item>> FIRED_BLOCKS = new ImmutableMap.Builder<Block, List<Item>>()
//            // stone
//            .put(BlocksRegistry.STONE.get(), ImmutableList.of(Items.SMOOTH_STONE))
//            .put(BlocksRegistry.END_STONE.get(), ImmutableList.of(ItemsRegistry.ENDER_SLAG.get(), BlocksRegistry.COBBLED_END_STONE.get().asItem()))
//            // ore
//            .put(BlocksRegistry.IRON_ORE_CHUNK.get(), ImmutableList.of(Items.IRON_NUGGET))
//            .put(BlocksRegistry.COPPER_ORE_CHUNK.get(), ImmutableList.of(ItemsRegistry.COPPER_NUGGET.get()))
//            .put(BlocksRegistry.GOLD_ORE_CHUNK.get(), ImmutableList.of(Items.GOLD_NUGGET))
//            // pottery
//            .put(BlocksRegistry.UNFIRED_CLAY.get(), ImmutableList.of(BlocksRegistry.TERRACOTTA.get().asItem()))
//            .put(BlocksRegistry.UNFIRED_CRUCIBLE.get(), ImmutableList.of(BlocksRegistry.CRUCIBLE.get().asItem()))
//            .put(BlocksRegistry.UNFIRED_PLANTER.get(), ImmutableList.of(BlocksRegistry.PLANTER_EMPTY.get().asItem()))
//            .put(BlocksRegistry.UNFIRED_VASE.get(), ImmutableList.of(BlocksRegistry.VASE_PLAIN.get().asItem()))
//            .put(BlocksRegistry.UNFIRED_URN.get(), ImmutableList.of(BlocksRegistry.URN.get().asItem()))
//            .put(BlocksRegistry.UNFIRED_MOULD.get(), ImmutableList.of(ItemsRegistry.MOULD.get()))
//            // logs to charcoal
//            .put(BlocksRegistry.ACACIA_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.ACACIA_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.BIRCH_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.BIRCH_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.DARK_OAK_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.DARK_OAK_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.JUNGLE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.JUNGLE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.MANGROVE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.MANGROVE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.OAK_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.OAK_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.SPRUCE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.SPRUCE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_ACACIA_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_ACACIA_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_BIRCH_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_BIRCH_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_DARK_OAK_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_DARK_OAK_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_JUNGLE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_JUNGLE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_MANGROVE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_MANGROVE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_OAK_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_OAK_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_SPRUCE_LOG.get(), ImmutableList.of(Items.CHARCOAL))
//            .put(BlocksRegistry.STRIPPED_SPRUCE_WOOD.get(), ImmutableList.of(Items.CHARCOAL))
//            // food
//            .put(BlocksRegistry.CAKE_BATTER.get(), ImmutableList.of(Items.CAKE))
//            .put(BlocksRegistry.BREAD_DOUGH.get(), ImmutableList.of(Items.BREAD))
//            .put(BlocksRegistry.COOKIE_DOUGH.get(), ImmutableList.of(Items.COOKIE, Items.COOKIE, Items.COOKIE, Items.COOKIE, Items.COOKIE, Items.COOKIE, Items.COOKIE, Items.COOKIE)).build();

//    private int firingTime;
//    private int maxFiringTime;

    public SawBlockEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.SAW_ENTITY, pos, state);
//        this.firingTime = 0;
//        this.maxFiringTime = 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, SawBlockEntity saw) {
        if (world.getBlockState(pos).isOf(BlocksRegistry.SAW.get()) && world.getBlockEntity(pos).getType() == EntitiesRegistry.SAW_ENTITY && world.getBlockState(pos).get(SawBlock.MECHANICAL_POWERED)) {
            Direction facing = state.get(SawBlock.FACING);
            BlockPos facedPos = pos.offset(facing);
            BlockState facedBlockState = world.getBlockState(facedPos);
            if (facedBlockState.isIn(Tags.BROKEN_BY_SAW)) {
                Optional<List<Item>> optional = saw.getSawedItems(facedBlockState.getBlock());
                if (optional.isPresent()) {
                    if (!world.isClient()) {
                        RedBrainsSurvivalMod.LOGGER.info("saw block");
                        world.breakBlock(facedPos, false);
                        for (Item item : optional.get()) {
                            ItemScatterer.spawn(world, facedPos.getX(), facedPos.getY(), facedPos.getZ(), item.getDefaultStack());
                        }
                    }
                } else {
                    if (!world.isClient()) {
                        RedBrainsSurvivalMod.LOGGER.info("break block");
                        world.breakBlock(facedPos, true);
                    }
                    // TODO sound & particles
                }
//                if (world.getBlockState(pos.up()).get(HibachiFireBlock.STOKED)) {
//                    BlockPos firedBlockPos = pos.up(3);
//                    Block firedBlock = world.getBlockState(firedBlockPos).getBlock();
//                    Optional<List<Item>> optional = hibachi.getFiredItems(firedBlock);
//                    if (hibachi.isKilnPresent(world, pos) && optional.isPresent()) {
//                        hibachi.maxFiringTime = calculateMaxFiringTime(hibachi.getAdditionalStokedFire(world, pos));
//                        if (hibachi.firingTime < hibachi.maxFiringTime) {
//                            hibachi.firingTime++;
//                            if (world.isClient) {
//                                // TODO client side
//                                //  render overlay and particles
//                            }
//                        } else {
//                            if (!world.isClient()) {
//                                // TODO maybe change to blockState
//                                world.removeBlock(pos.up(3), false);
//                                for (Item item : optional.get()) {
//                                    ItemScatterer.spawn(world, (double) firedBlockPos.getX(), (double) firedBlockPos.getY(), (double) firedBlockPos.getZ(), item.getDefaultStack());
//                                }
//                            }
//                            hibachi.firingTime = 0;
//                        }
//                    } else {
//                        hibachi.firingTime = 0;
//                    }
//                }
            } else if (!(facedBlockState.isOf(BlocksRegistry.CHOPPING_BLOCK.get()) || facedBlockState.isOf(BlocksRegistry.CHOPPING_BLOCK_BLOODY.get()) || facedBlockState.isOf(Blocks.AIR))) {
                if (!world.isClient()) {
                    RedBrainsSurvivalMod.LOGGER.info("should break saw");
                    world.breakBlock(pos, false);
                    for (Item drop : saw.dropsWhenBroken) {
                        ItemScatterer.spawn(((World)world), pos.getX(), pos.getY(), pos.getZ(), drop.getDefaultStack());
                    }
                }
            }
        }
//        else {
//            if (!world.isClient() && world.getBlockState(pos.up()).isOf(BlocksRegistry.HIBACHI_FIRE.get())) {
//                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
//                world.updateListeners(pos.up(), state, state, 3);
//                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos.up(), GameEvent.Emitter.of(state));
//            }
//        }
    }
//
//    @Override
//    public void readNbt(NbtCompound nbt) {
//        super.readNbt(nbt);
//        int is;
//        if (nbt.contains("FiringTime", 11)) {
//            is = nbt.getInt("FiringTime");
//            this.firingTime = is;
//        }
//
//        if (nbt.contains("MaxFiringTime", 11)) {
//            is = nbt.getInt("MaxFiringTime");
//            this.maxFiringTime = is;
//        }
//    }
//
//    @Override
//    public void writeNbt(NbtCompound nbt) {
//        super.writeNbt(nbt);
//        nbt.putInt("FiringTime", firingTime);
//        nbt.putInt("MaxFiringTime", maxFiringTime);
//    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        return nbtCompound;
    }
//
//    // TODO balance
//    private static int calculateMaxFiringTime(int additionalStokedFiresPresent) {
//        return switch (additionalStokedFiresPresent) {
//            case 8 -> 200;
//            case 7 -> 300;
//            case 6 -> 400;
//            case 5 -> 500;
//            case 4 -> 600;
//            case 3 -> 700;
//            case 2 -> 800;
//            case 1 -> 900;
//            default -> 1000;
//        };
//    }
//
//    private int getAdditionalStokedFire(World world, BlockPos pos) {
//        int additionalStokedFire = 0;
//        BlockPos initialFirePos = pos.up();
//        List<BlockPos> additionalStokedFirePos = List.of(initialFirePos.north(), initialFirePos.east(), initialFirePos.east().north(), initialFirePos.east().south(), initialFirePos.south(), initialFirePos.west(), initialFirePos.west().north(), initialFirePos.west().south());
//        for (BlockPos blockPos : additionalStokedFirePos) {
//            if (world.getBlockState(blockPos).isOf(BlocksRegistry.HIBACHI_FIRE.get()) && world.getBlockState(blockPos).get(HibachiFireBlock.STOKED)) {
//                additionalStokedFire++;
//            }
//        }
//        return Math.min(additionalStokedFire, 8);
//    }
//
//    private boolean isKilnPresent(World world, BlockPos pos) {
//        BlockPos firePos = pos.up();
//        BlockPos lowerBricksPos = firePos.up();
//        BlockPos productPos = lowerBricksPos.up();
//        List<BlockPos> otherBricksPos = List.of(productPos.north(), productPos.east(), productPos.south(), productPos.west(), productPos.up());
//        int bricksCount = 0;
//        for (BlockPos blockPos : otherBricksPos) {
//            if (world.getBlockState(blockPos).isOf(Blocks.BRICKS)) {
//                bricksCount++;
//            }
//        }
//        return world.getBlockState(firePos).isOf(BlocksRegistry.HIBACHI_FIRE.get()) && world.getBlockState(firePos).get(HibachiFireBlock.STOKED) && world.getBlockState(lowerBricksPos).isOf(Blocks.BRICKS) && bricksCount >= 3;
//    }

//    private Optional<List<Item>> getFiredItems(Block block) {
//        return Optional.ofNullable(FIRED_BLOCKS.get(block));
//    }

    private Optional<List<Item>> getSawedItems(Block block) {
        return Optional.ofNullable(SAWED_BLOCKS.get(block));
    }
}
