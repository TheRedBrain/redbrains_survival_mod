package com.github.theredbrain.redbrainssurvivalmod.screen;

import com.github.theredbrain.redbrainssurvivalmod.inventory.SoulforgeInventory;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ScreenHandlerTypesRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class SoulforgeScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
    public static final int field_30781 = 0;
    private static final int field_30782 = 1;
    private static final int field_30783 = 10;
    private static final int field_30784 = 10;
    private static final int field_30785 = 37;
    private static final int field_30786 = 37;
    private static final int field_30787 = 46;
//    private final SoulforgeInventory soulforgeInventory;
    private final CraftingInventory input = new CraftingInventory(this, 4, 4);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public SoulforgeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory/*, DefaultedList.ofSize(16, ItemStack.EMPTY)*/, ScreenHandlerContext.EMPTY);
    }

    public SoulforgeScreenHandler(int syncId, PlayerInventory playerInventory/*, DefaultedList<ItemStack> soulforgeInventoryStacks*/, ScreenHandlerContext context) {
        super(ScreenHandlerTypesRegistry.SOULFORGE_SCREEN_HANDLER, syncId);
        int j;
        int i;
        this.context = context;
        this.player = playerInventory.player;
//        this.soulforgeInventory = new SoulforgeInventory(this, soulforgeInventoryStacks, 4, 4); // TODO moulds stay in their slots
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 138, 45));
        for (i = 0; i < 4; ++i) {
            for (j = 0; j < 4; ++j) {
                this.addSlot(new Slot(this.input, j + i * 4, 18 + j * 18, 17 + i * 18));
            }
        }
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 160));
        }
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        ItemStack itemStack2;
        CraftingRecipe craftingRecipe;
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
        if (optional.isPresent() && resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe = optional.get()) && (itemStack2 = craftingRecipe.craft(craftingInventory, world.getRegistryManager())).isItemEnabled(world.getEnabledFeatures())) {
            itemStack = itemStack2;
        }
        resultInventory.setStack(0, itemStack);
        handler.setPreviousTrackedSlot(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> SoulforgeScreenHandler.updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
//        this.soulforgeInventory.clearButMouldsStay();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
//        this.context.run((world, pos) -> this.dropInventoryButMouldsStay(player, this.soulforgeInventory));
    }

    protected void dropInventoryButMouldsStay(PlayerEntity player, SoulforgeInventory soulforgeInventory) {
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            for (int i = 0; i < soulforgeInventory.size(); ++i) {
                if (soulforgeInventory.getStack(i).isOf(ItemsRegistry.MOULD.get())) continue;
                player.dropItem(soulforgeInventory.removeStack(i), false);
            }
            return;
        }
        for (int i = 0; i < soulforgeInventory.size(); ++i) {
            PlayerInventory playerInventory = player.getInventory();
            if (!(playerInventory.player instanceof ServerPlayerEntity)) continue;
            if (soulforgeInventory.getStack(i).isOf(ItemsRegistry.MOULD.get())) continue;
            playerInventory.offerOrDrop(soulforgeInventory.removeStack(i));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return SoulforgeScreenHandler.canUse(this.context, player, BlocksRegistry.SOULFORGE.get());
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot)this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, (World)world, player));
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot >= 10 && slot < 46 ? !this.insertItem(itemStack2, 1, 10, false) && (slot < 37 ? !this.insertItem(itemStack2, 37, 46, false) : !this.insertItem(itemStack2, 10, 37, false)) : !this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
            if (slot == 0) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return 17;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}

