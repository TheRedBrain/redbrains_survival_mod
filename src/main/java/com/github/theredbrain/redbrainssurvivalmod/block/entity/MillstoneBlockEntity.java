package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.MillstoneBlock;
import com.github.theredbrain.redbrainssurvivalmod.recipe.GrindingRecipe;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.RecipeTypesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MillstoneBlockEntity extends BlockEntity {

    private int grindingTime;
    private int grindingTimeTotal;
    private final DefaultedList<ItemStack> itemBeingGround;
    private final RecipeManager.MatchGetter<Inventory, GrindingRecipe> matchGetter;

    public MillstoneBlockEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.MILLSTONE_ENTITY, pos, state);
        this.itemBeingGround = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.grindingTime = 0;
        this.grindingTimeTotal = 0;
        this.matchGetter = RecipeManager.createCachedMatchGetter(RecipeTypesRegistry.GRINDING_RECIPE_SERIALIZER.type());
    }

    public static void poweredServerTick(World world, BlockPos pos, BlockState state, MillstoneBlockEntity millstone) {
        boolean bl = false;

        for(int i = 0; i < millstone.itemBeingGround.size(); ++i) {
            ItemStack itemStack = (ItemStack)millstone.itemBeingGround.get(0);
            if (!itemStack.isEmpty()) {
                bl = true;
                int var10002 = millstone.grindingTime++;
                if (millstone.grindingTime >= millstone.grindingTimeTotal) {
                    Inventory inventory = new SimpleInventory(new ItemStack[]{itemStack});
                    ItemStack itemStack2 = /*ItemStack.EMPTY;*/(ItemStack)millstone.matchGetter.getFirstMatch(inventory, world).map((recipe) -> {
                        return recipe.craft(inventory, world.getRegistryManager());
                    }).orElse(itemStack); // TODO
                    int offsetX = world.getRandom().nextInt(1);
                    int offsetZ = world.getRandom().nextInt(1);
                    ItemScatterer.spawn(world, (double)pos.getX() + offsetX, (double)pos.getY(), (double)pos.getZ() + offsetZ, itemStack2);
                    millstone.itemBeingGround.set(0, ItemStack.EMPTY);
                    world.updateListeners(pos, state, state, 3);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
                }
            }
        }

        if (bl) {
            markDirty(world, pos, state);
        }

    }

    public static void clientTick(World world, BlockPos pos, BlockState state, MillstoneBlockEntity millstone) {

        if (world.random.nextFloat() < 0.11F && !millstone.itemBeingGround.get(0).isEmpty()) {
            for(int i = 0; i < world.random.nextInt(2) + 2; ++i) {
                MillstoneBlock.spawnGrindingParticles(world, pos, millstone.getItemBeingGround().get(0), world.random, 1);
            }
        }
    }

    public DefaultedList<ItemStack> getItemBeingGround() {
        return this.itemBeingGround;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.itemBeingGround.clear();
        Inventories.readNbt(nbt, this.itemBeingGround);
        int is;
        if (nbt.contains("GrindingTime", 11)) {
            is = nbt.getInt("GrindingTime");
            this.grindingTime = is;
        }

        if (nbt.contains("GrindingTimeTotal", 11)) {
            is = nbt.getInt("GrindingTimeTotal");
            this.grindingTimeTotal = is;
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemBeingGround, true);
        nbt.putInt("GrindingTime", grindingTime);
        nbt.putInt("GrindingTimeTotal", grindingTimeTotal);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.itemBeingGround, true);
        return nbtCompound;
    }

    public Optional<GrindingRecipe> getRecipeFor(ItemStack stack) {
        return this.itemBeingGround.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.matchGetter.getFirstMatch(new SimpleInventory(new ItemStack[]{stack}), this.world);
    }

    public boolean addItem(@Nullable Entity user, ItemStack stack, int grindingTime) {
        for(int i = 0; i < 1; ++i) {
            ItemStack itemStack = (ItemStack) this.itemBeingGround.get(i);
            if (itemStack.isEmpty()) {
                this.grindingTimeTotal = grindingTime;
                this.grindingTime = 0;
                this.itemBeingGround.set(i, stack.split(1));
                this.world.emitGameEvent(GameEvent.BLOCK_CHANGE, this.getPos(), GameEvent.Emitter.of(user, this.getCachedState()));
                this.updateListeners();

                return true;
            }
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
