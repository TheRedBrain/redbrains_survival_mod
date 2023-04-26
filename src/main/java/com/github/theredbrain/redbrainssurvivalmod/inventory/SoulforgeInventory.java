package com.github.theredbrain.redbrainssurvivalmod.inventory;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

public class SoulforgeInventory extends CraftingInventory {
    private final DefaultedList<ItemStack> stacks;

    public SoulforgeInventory(ScreenHandler handler, DefaultedList<ItemStack> stacks, int width, int height) {
        super(handler, width, height);
        this.stacks = stacks;
    }

    @Override
    public int size() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= this.size()) {
            return ItemStack.EMPTY;
        }
        return this.stacks.get(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        ((DuckCraftingInventoryMixin)this).getHandler().onContentChanged(this);
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    public void clearButMouldsStay() {
        for (int i = 0; i < this.stacks.size(); i++) {
            if (!this.stacks.get(i).isOf(ItemsRegistry.MOULD.get())) {
                this.stacks.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.stacks) {
            if (!itemStack.isOf(ItemsRegistry.MOULD.get())) {
                finder.addUnenchantedInput(itemStack);
            }
        }
    }
}