package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.BrickOvenBlock;
import com.github.theredbrain.redbrainssurvivalmod.recipe.CrucibleSmeltingRecipe;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.RecipeTypesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public class CrucibleEntity extends BlockEntity {
    private int smeltingTime;
    private int smeltingTimeTotal;
    private final DefaultedList<ItemStack> content;
    private final RecipeManager.MatchGetter<Inventory, CrucibleSmeltingRecipe> matchGetter;

    public CrucibleEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.CRUCIBLE_ENTITY, pos, state);
        this.content = DefaultedList.ofSize(27, ItemStack.EMPTY);
        this.smeltingTime = 0;
        this.smeltingTimeTotal = 0;
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeTypesRegistry.CRUCIBLE_SMELTING_RECIPE_SERIALIZER.type());
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, CrucibleEntity crucible) {
//        boolean bl = false;
//        boolean shouldSmelt;
//        BlockState newBlockState;
//
//        brickOven.fuelTime++;
//        if (state.get(BrickOvenBlock.FUEL) > 0) {
//            shouldSmelt = true;
//            if (brickOven.fuelTime >= MAX_FUEL_TIME) {
//                brickOven.fuelTime = 0;
//                newBlockState = state.with(BrickOvenBlock.FUEL, state.get(BrickOvenBlock.FUEL) - 1);
//                world.setBlockState(pos, newBlockState, Block.NOTIFY_ALL);
//                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newBlockState));
//            }
//        } else {
//            shouldSmelt = false;
//            if (brickOven.fuelTime >= MAX_SMOULDERING_TIME) {
//                brickOven.fuelTime = 0;
//                newBlockState = state.with(BrickOvenBlock.LIT, false);
//                world.setBlockState(pos, newBlockState, Block.NOTIFY_ALL);
//                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newBlockState));
//            }
//        }
//
//        if (shouldSmelt) {
//            ItemStack itemStack = (ItemStack) brickOven.content.get(0);
//            ItemStack itemStack2;
//
//            // using data-driven recipes
////                    Inventory inventory = new SimpleInventory(itemStack);
////                    itemStack2 = /*ItemStack.EMPTY;*/(ItemStack) brickOven.matchGetter.getFirstMatch(inventory, world).map((recipe) -> {
////                        return recipe.craft(inventory, world.getRegistryManager());
////                    }).orElse(ItemStack.EMPTY);
//
//            // hard-coded recipes
//            Optional<Pair<Pair<Item, Integer>, Integer>> optional = brickOven.getSmeltingResult(itemStack);
//            itemStack2 = optional.isPresent() ? optional.get().getLeft().getLeft().getDefaultStack() : ItemStack.EMPTY;
//
//            if (!itemStack2.isEmpty()) {
//                itemStack2.setCount(optional.isPresent() ? optional.get().getLeft().getRight() : 1);
//                bl = true;
//                brickOven.smeltingTime++;
//                if (brickOven.smeltingTime >= brickOven.smeltingTimeTotal) {
//
//                    ItemStack itemStack3;
//                    Optional<Pair<Pair<Item, Integer>, Integer>> optional2 = brickOven.getSmeltingResult(itemStack2);
//                    itemStack3 = optional2.isPresent() ? optional2.get().getLeft().getLeft().getDefaultStack() : ItemStack.EMPTY;
//
//                    if (optional2.isPresent()) {
//                        itemStack3.setCount(optional2.get().getLeft().getRight());
//                        brickOven.addItem(null, itemStack3, optional2.get().getRight());
//                    } else {
//                        brickOven.content.set(0, itemStack2);
//                        brickOven.smeltingTime = 0;
//                        brickOven.smeltingTimeTotal = 0;
//                        world.updateListeners(pos, state, state, 3);
//                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
//                    }
//                }
//            }
//        }
//
//        if (bl) {
//            markDirty(world, pos, state);
//        }

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, CrucibleEntity crucible) {

//        if (world.random.nextFloat() < 0.11F && state.get(BrickOvenBlock.LIT)) {
//            // TODO spawn spark particles at front side
////            for(int i = 0; i < world.random.nextInt(2) + 2; ++i) {
////                MillstoneBlock.spawnGrindingParticles(world, pos, brickOven.getItemBeingGround().get(0), world.random, 1);
////            }
//        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.content.clear();
        Inventories.readNbt(nbt, this.content);
        int is;

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

    public Optional<CrucibleSmeltingRecipe> getRecipeFor(ItemStack stack) {
        return this.content.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.matchGetter.getFirstMatch(new SimpleInventory(new ItemStack[]{stack}), this.world);
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }
}
