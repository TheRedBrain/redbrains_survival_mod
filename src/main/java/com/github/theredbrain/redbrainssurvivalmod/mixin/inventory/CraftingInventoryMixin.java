package com.github.theredbrain.redbrainssurvivalmod.mixin.inventory;

import com.github.theredbrain.redbrainssurvivalmod.inventory.DuckCraftingInventoryMixin;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CraftingInventory.class)
public class CraftingInventoryMixin implements DuckCraftingInventoryMixin {
    @Shadow @Final private ScreenHandler handler;

    @Override
    public ScreenHandler getHandler() {
        return handler;
    }
}
