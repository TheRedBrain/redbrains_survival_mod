package com.github.theredbrain.redbrainssurvivalmod.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.item.CustomTorchItem;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CrudeTorchBlockEntity extends BlockEntity {

    private static final int MAX_BURN_TIME = 24000;
    protected int burnTime;
    public CrudeTorchBlockEntity(BlockPos pos, BlockState state) {
        super(EntitiesRegistry.CRUDE_TORCH_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, CrudeTorchBlockEntity crudeTorch) {
        crudeTorch.burnTime++;
        if (crudeTorch.burnTime >= MAX_BURN_TIME) {
            world.setBlockState(pos, BlocksRegistry.CRUDE_TORCH_BURNED.get().getDefaultState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        CrudeTorchBlockEntity.writeBurntimeToNbt(this.burnTime, nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.contains("BurnTime", NbtElement.INT_TYPE) ? nbt.getInt("BurnTime") : 0;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public static void writeBurntimeToNbt(int burnTime, NbtCompound nbt) {
        nbt.putInt("BurnTime", burnTime);
    }

    public ItemStack asStack() {
        ItemStack itemStack = new ItemStack(BlocksRegistry.CRUDE_TORCH.get());
        NbtCompound nbtCompound = new NbtCompound();
        CrudeTorchBlockEntity.writeBurntimeToNbt(this.burnTime, nbtCompound);
        BlockItem.setBlockEntityNbt(itemStack, EntitiesRegistry.CRUDE_TORCH_ENTITY, nbtCompound);
        CustomTorchItem.setBurnTime(itemStack, this.burnTime);
        return itemStack;
    }

    public void getBurnTimeFromStack(ItemStack stack) {
        this.burnTime = CustomTorchItem.getBurnTime(stack);
    }
}
