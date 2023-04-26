package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class SoulforgeBlockEntity extends BlockEntity {

    private final DefaultedList<ItemStack> inventory;

    public SoulforgeBlockEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.SOULFORGE_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(16, ItemStack.EMPTY);
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory.clear();
        Inventories.readNbt(nbt, this.inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory, true);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        return nbtCompound;
    }
}
