package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import net.minecraft.item.CompassItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CompassItem.class)
public interface CompassItemInvoker {

    @Invoker("writeNbt")
    void invokeWriteNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt);
}
