package com.github.theredbrain.redbrainssurvivalmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class HandicraftItem extends Item {

    private final List<Item> products;
    private final int maxCraftingSteps;

    public HandicraftItem(List<Item> products, int maxCraftingSteps, Settings settings) {
        super(settings);
        this.products = products;
        this.maxCraftingSteps = maxCraftingSteps;
    }

//    /**
//     * Called on both the server and the client every tick while an entity uses
//     * the item. Currently used by {@link CrossbowItem} to charge the crossbow.
//     * If this is overridden, {@link #getMaxUseTime} should also be overridden to
//     * return a positive value.
//     *
//     * @see #finishUsing
//     * @see #use
//     *
//     * @param remainingUseTicks how long it's left until the entity finishes using the item, in ticks
//     */
//    @Override
//    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
////        if (user instanceof PlayerEntity) {
////            ItemStack itemStack = user.getMainHandStack();
////            int craftTime = HandicraftItem.getCraftTime(itemStack);
////            if (craftTime < this.maxCraftTime) {
////                HandicraftItem.setCraftTime(itemStack);
////            } else {
////                itemStack.decrement(1);
////                for (Item item : this.products) {
////                    ((PlayerEntity)user).getInventory().offer(item.getDefaultStack(), true);
////                }
////            }
////        }
//    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack itemStack = user.getStackInHand(hand);
//        int craftTime = HandicraftItem.getCraftingStep(itemStack);
//        if (craftTime < this.maxCraftingSteps) {
//            HandicraftItem.setCraftingStep(itemStack);
//        } else {
//            itemStack.decrement(1);
//            for (Item item : this.products) {
//                user.getInventory().offer(item.getDefaultStack(), true);
//            }
//        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity) {
            int craftTime = HandicraftItem.getCraftingStep(stack);
            if (craftTime < this.maxCraftingSteps) {
                HandicraftItem.setCraftingStep(stack);
            } else {
                stack.decrement(1);
                for (Item item : this.products) {
                    ((PlayerEntity)user).getInventory().offer(item.getDefaultStack(), true);
                }
            }
        }
        return stack;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

//    public int getItemBarStep(ItemStack stack) {
////        return Math.round((float)((DuckItemStackMixin) (Object) stack).getCraftTime() * 13.0f / (float)((DuckItemStackMixin) (Object) stack).getMaxCraftTime());
//        return Math.min(((DuckItemStackMixin) (Object) stack).getCraftTime() * 13 / ((DuckItemStackMixin) (Object) stack).getMaxCraftTime(), 13);
//    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.min(1 + 12 * HandicraftItem.getCraftingStep(stack) / this.maxCraftingSteps, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float f = Math.max(0.0f, ((float)this.maxCraftingSteps - (float)HandicraftItem.getCraftingStep(stack)) / (float)this.maxCraftingSteps);
        return MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
//        return MathHelper.packRgb(0.4f, 0.4f, 1.0f);
    }

    /**
     * {@return the use action the item should perform}
     */
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    /**
     * {@return the maximum use (right-click) time of this item, in ticks}
     * Once a player has used an item for said number of ticks, they stop using it, and {@link Item#finishUsing} is called.
     */
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    public static int getCraftingStep(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.contains("CraftingStep", NbtElement.INT_TYPE) ? nbt.getInt("CraftingStep") : 0;
    }

    public static void setCraftingStep(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("CraftingStep", NbtElement.INT_TYPE)) {
            nbtCompound.putInt("CraftingStep", 0);
        }
        int oldCraftingStep = nbtCompound.getInt("CraftingStep");
        nbtCompound.putInt("CraftingStep", oldCraftingStep + 1);
    }
}
