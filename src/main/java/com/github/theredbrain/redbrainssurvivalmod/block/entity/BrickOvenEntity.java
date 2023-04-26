package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.BrickOvenBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BrickOvenEntity extends BlockEntity {
    // TODO balance fuel and smelt times
    private static final Map<Item, Integer> FUEL_TIMES = new ImmutableMap.Builder<Item, Integer>()
            .put(BlocksRegistry.ACACIA_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_ACACIA_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.ACACIA_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_ACACIA_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.ACACIA_PLANKS.get().asItem(), 2400)
            .put(Blocks.ACACIA_SLAB.asItem(), 1200)
            .put(BlocksRegistry.ACACIA_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.BIRCH_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_BIRCH_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.BIRCH_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_BIRCH_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.BIRCH_PLANKS.get().asItem(), 2400)
            .put(Blocks.BIRCH_SLAB.asItem(), 1200)
            .put(BlocksRegistry.BIRCH_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.CHERRY_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_CHERRY_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.CHERRY_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_CHERRY_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.CHERRY_PLANKS.get().asItem(), 2400)
            .put(Blocks.CHERRY_SLAB.asItem(), 1200)
            .put(BlocksRegistry.CHERRY_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.DARK_OAK_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_DARK_OAK_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.DARK_OAK_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_DARK_OAK_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), 2400)
            .put(Blocks.DARK_OAK_SLAB.asItem(), 1200)
            .put(BlocksRegistry.DARK_OAK_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.JUNGLE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_JUNGLE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.JUNGLE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_JUNGLE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), 2400)
            .put(Blocks.JUNGLE_SLAB.asItem(), 1200)
            .put(BlocksRegistry.JUNGLE_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.MANGROVE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_MANGROVE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.MANGROVE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_MANGROVE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), 2400)
            .put(Blocks.MANGROVE_SLAB.asItem(), 1200)
            .put(BlocksRegistry.MANGROVE_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.OAK_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_OAK_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.OAK_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_OAK_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.OAK_PLANKS.get().asItem(), 2400)
            .put(Blocks.OAK_SLAB.asItem(), 1200)
            .put(BlocksRegistry.OAK_PLANKS_SIDING.get().asItem(), 1200)
            .put(BlocksRegistry.SPRUCE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_SPRUCE_LOG.get().asItem(), 9600)
            .put(BlocksRegistry.SPRUCE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.STRIPPED_SPRUCE_WOOD.get().asItem(), 9600)
            .put(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), 2400)
            .put(Blocks.SPRUCE_SLAB.asItem(), 1200)
            .put(BlocksRegistry.SPRUCE_PLANKS_SIDING.get().asItem(), 1200)
            .put(ItemsRegistry.SPRUCE_BARK.get(), 9600)
            .put(Items.COAL, 9600)
            .put(Items.CHARCOAL, 9600)
            .put(ItemsRegistry.COAL_DUST.get(), 9600)
            .put(ItemsRegistry.SAW_DUST.get(), 150)
            .build();
    protected static final Map<Item, Pair<Pair<Item, Integer>, Integer>> SMELTING_RESULTS = new ImmutableMap.Builder<Item, Pair<Pair<Item, Integer>, Integer>>()
            // takes a full oven
            .put(BlocksRegistry.IRON_ORE_CHUNK.get().asItem(), new Pair<>(new Pair<>(Items.IRON_NUGGET, 1), 9600))
            .put(BlocksRegistry.COPPER_ORE_CHUNK.get().asItem(), new Pair<>(new Pair<>(ItemsRegistry.COPPER_NUGGET.get(), 1), 9600))
            .put(BlocksRegistry.GOLD_ORE_CHUNK.get().asItem(), new Pair<>(new Pair<>(Items.GOLD_NUGGET, 1), 9600))
            .put(BlocksRegistry.BREAD_DOUGH.get().asItem(), new Pair<>(new Pair<>(ItemsRegistry.BREAD.get(), 1), 1200))
            .put(BlocksRegistry.CAKE_BATTER.get().asItem(), new Pair<>(new Pair<>(BlocksRegistry.CAKE.get().asItem(), 1), 1200))
            .put(BlocksRegistry.OVEN_READY_PUMPKIN_PIE.get().asItem(), new Pair<>(new Pair<>(ItemsRegistry.PUMPKIN_PIE.get(), 1), 1200))
            .put(BlocksRegistry.COOKIE_DOUGH.get().asItem(), new Pair<>(new Pair<>(ItemsRegistry.COOKIE.get(), 8), 1200))
            .put(ItemsRegistry.CHICKEN.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_CHICKEN.get(), 1), 1200))
            .put(ItemsRegistry.PORK_CHOP.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_PORK_CHOP.get(), 1), 1200))
            .put(ItemsRegistry.WOLF_CHOP.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_WOLF_CHOP.get(), 1), 1200))
            .put(ItemsRegistry.BEEF.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_BEEF.get(), 1), 1200))
            .put(ItemsRegistry.MUTTON.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_MUTTON.get(), 1), 1200))
            .put(ItemsRegistry.MYSTERY_MEAT.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_MYSTERY_MEAT.get(), 1), 1200))
            .put(ItemsRegistry.LIVER_OF_THE_BEAST.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_LIVER.get(), 1), 1200))
            .put(ItemsRegistry.COD.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_COD.get(), 1), 1200))
            .put(ItemsRegistry.SALMON.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_SALMON.get(), 1), 1200))
            .put(ItemsRegistry.RAW_EGG.get(), new Pair<>(new Pair<>(ItemsRegistry.FRIED_EGG.get(), 1), 1200))
            .put(ItemsRegistry.RAW_KEBAB.get(), new Pair<>(new Pair<>(ItemsRegistry.KEBAB.get(), 1), 1200))
            .put(ItemsRegistry.POTATO.get(), new Pair<>(new Pair<>(ItemsRegistry.BAKED_POTATO.get(), 1), 1200))
            .put(ItemsRegistry.CARROT.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_CARROT.get(), 1), 1200))
            .put(ItemsRegistry.SCRAMBLED_EGG.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_SCRAMBLED_EGG.get(), 1), 1200))
            .put(ItemsRegistry.MUSHROOM_OMELET.get(), new Pair<>(new Pair<>(ItemsRegistry.COOKED_MUSHROOM_OMELET.get(), 1), 1200))
            .build();

    // TODO balance times
    private static final int MAX_FUEL_TIME = 1200;
    private static final int MAX_SMOULDERING_TIME = 100;
    private int smeltingTime;
    private int smeltingTimeTotal;
    private int fuelTime;
    private final DefaultedList<ItemStack> content;
    private final RecipeManager.MatchGetter<Inventory, SmeltingRecipe> matchGetter;

    public BrickOvenEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.BRICK_OVEN_ENTITY, pos, state);
        this.content = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.smeltingTime = 0;
        this.smeltingTimeTotal = 0;
        this.fuelTime = 0;
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, BrickOvenEntity brickOven) {
        boolean bl = false;
        boolean shouldSmelt;
        BlockState newBlockState;

        brickOven.fuelTime++;
        if (state.get(BrickOvenBlock.FUEL) > 0) {
            shouldSmelt = true;
            if (brickOven.fuelTime >= MAX_FUEL_TIME) {
                brickOven.fuelTime = 0;
                newBlockState = state.with(BrickOvenBlock.FUEL, state.get(BrickOvenBlock.FUEL) - 1);
                world.setBlockState(pos, newBlockState, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newBlockState));
            }
        } else {
            shouldSmelt = false;
            if (brickOven.fuelTime >= MAX_SMOULDERING_TIME) {
                brickOven.fuelTime = 0;
                newBlockState = state.with(BrickOvenBlock.LIT, false);
                world.setBlockState(pos, newBlockState, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newBlockState));
            }
        }

        if (shouldSmelt) {
            ItemStack itemStack = (ItemStack) brickOven.content.get(0);
            ItemStack itemStack2;

            // using data-driven recipes
//                    Inventory inventory = new SimpleInventory(itemStack);
//                    itemStack2 = /*ItemStack.EMPTY;*/(ItemStack) brickOven.matchGetter.getFirstMatch(inventory, world).map((recipe) -> {
//                        return recipe.craft(inventory, world.getRegistryManager());
//                    }).orElse(ItemStack.EMPTY);

            // hard-coded recipes
            Optional<Pair<Pair<Item, Integer>, Integer>> optional = brickOven.getSmeltingResult(itemStack);
            itemStack2 = optional.isPresent() ? optional.get().getLeft().getLeft().getDefaultStack() : ItemStack.EMPTY;

            if (!itemStack2.isEmpty()) {
                itemStack2.setCount(optional.isPresent() ? optional.get().getLeft().getRight() : 1);
                bl = true;
                brickOven.smeltingTime++;
                if (brickOven.smeltingTime >= brickOven.smeltingTimeTotal) {

                    ItemStack itemStack3;
                    Optional<Pair<Pair<Item, Integer>, Integer>> optional2 = brickOven.getSmeltingResult(itemStack2);
                    itemStack3 = optional2.isPresent() ? optional2.get().getLeft().getLeft().getDefaultStack() : ItemStack.EMPTY;

                    if (optional2.isPresent()) {
                        itemStack3.setCount(optional2.get().getLeft().getRight());
                        brickOven.addItem(null, itemStack3, optional2.get().getRight());
                    } else {
                        brickOven.content.set(0, itemStack2);
                        brickOven.smeltingTime = 0;
                        brickOven.smeltingTimeTotal = 0;
                        world.updateListeners(pos, state, state, 3);
                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
                    }
                }
            }
        }

        if (bl) {
            markDirty(world, pos, state);
        }

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, BrickOvenEntity brickOven) {

        if (world.random.nextFloat() < 0.11F && state.get(BrickOvenBlock.LIT)) {
            // TODO spawn spark particles at front side
//            for(int i = 0; i < world.random.nextInt(2) + 2; ++i) {
//                MillstoneBlock.spawnGrindingParticles(world, pos, brickOven.getItemBeingGround().get(0), world.random, 1);
//            }
        }
    }

    public DefaultedList<ItemStack> getContent() {
        return this.content;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public void resetFuelTime() {
        this.fuelTime = 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.content.clear();
        Inventories.readNbt(nbt, this.content);
        int is;
        if (nbt.contains("FuelTime", 11)) {
            is = nbt.getInt("FuelTime");
            this.fuelTime = is;
        }

        if (nbt.contains("SmeltingTime", 11)) {
            is = nbt.getInt("SmeltingTime");
            this.smeltingTime = is;
        }

        if (nbt.contains("SmeltingTimeTotal", 11)) {
            is = nbt.getInt("SmeltingTimeTotal");
            this.smeltingTimeTotal = is;
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.content, true);
        nbt.putInt("FuelTime", fuelTime);
        nbt.putInt("SmeltingTime", smeltingTime);
        nbt.putInt("SmeltingTimeTotal", smeltingTimeTotal);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.content, true);
        return nbtCompound;
    }

    public Optional<SmeltingRecipe> getRecipeFor(ItemStack stack) {
        return this.content.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.matchGetter.getFirstMatch(new SimpleInventory(new ItemStack[]{stack}), this.world);
    }

    public Optional<Pair<Pair<Item, Integer>, Integer>> getSmeltingResult(ItemStack itemStack) {
        return Optional.ofNullable(SMELTING_RESULTS.get(itemStack.getItem()));
    }

    public Optional<Integer> getFuelTimeFor(ItemStack stack) {
        return Optional.ofNullable(FUEL_TIMES.get(stack.getItem()));

        // from fabric api
//        return Optional.ofNullable(FuelRegistry.INSTANCE.get(stack.getItem()));
    }

    public boolean addItem(@Nullable Entity user, ItemStack stack, int smeltingTimeTotal) {
        World world = this.getWorld();
        if (world != null && !world.isClient) {
            this.smeltingTimeTotal = smeltingTimeTotal;
            this.smeltingTime = 0;
            this.content.set(0, stack.split(1));
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(user, this.getCachedState()));
            this.updateListeners();
            return true;
        }
        return false;
    }

    public boolean removeItem(Entity user) {
        ItemStack itemStack = (ItemStack) this.content.get(0);
        World world = this.getWorld();
        if (!itemStack.isEmpty() && world != null && !world.isClient) {
            this.smeltingTimeTotal = 0;
            this.smeltingTime = 0;
            BlockPos blockPos = this.pos.offset(world.getBlockState(this.getPos()).get(BrickOvenBlock.FACING));
            ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
            this.content.set(0, ItemStack.EMPTY);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(user, this.getCachedState()));
            this.updateListeners();
            return true;
        }
        return false;
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

//    public void clear() {
//        this.itemBeingGround.clear();
//    }
//
//    public void spawnItemBeingGround() {
//        if (this.world != null) {
//            this.updateListeners();
//        }
//
//    }
}
