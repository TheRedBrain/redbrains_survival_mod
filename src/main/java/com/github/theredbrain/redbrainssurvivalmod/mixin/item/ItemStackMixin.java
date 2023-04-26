package com.github.theredbrain.redbrainssurvivalmod.mixin.item;
//
//import com.github.theredbrain.redbrainssurvivalmod.item.DuckItemStackMixin;
//import com.github.theredbrain.redbrainssurvivalmod.item.HandicraftItem;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemConvertible;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.nbt.NbtElement;
//import net.minecraft.util.Hand;
//import net.minecraft.util.TypedActionResult;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.List;
//
//@Mixin(ItemStack.class)
//public abstract class ItemStackMixin implements DuckItemStackMixin {
//
//    @Shadow public abstract Item getItem();
//
//    @Shadow public abstract void decrement(int amount);
//
//    private int craftTime;
//    private int maxCraftTime;
//
//    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
//    public void ItemStack(ItemConvertible item, int count, CallbackInfo ci) {
//        if (this.getItem() instanceof HandicraftItem) {
//            this.maxCraftTime = ((HandicraftItem)this.getItem()).getMaxCraftTime();
//            this.craftTime = this.maxCraftTime;
//        }
//    }
//
//    @Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
//    private void ItemStack(NbtCompound nbt, CallbackInfo ci) {
//        if (this.getItem() instanceof HandicraftItem) {
//            if (nbt.contains("CraftTime", NbtElement.INT_TYPE)) {
//                this.craftTime = nbt.getInt("CraftTime");
//            }
//            if (nbt.contains("MaxCraftTime", NbtElement.INT_TYPE)) {
//                this.maxCraftTime = nbt.getInt("MaxCraftTime");
//            }
//        }
//    }
//
//    @Inject(method = "use", at = @At("HEAD"))
//    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//        if (this.getItem() instanceof HandicraftItem) {
//            this.craftTime--;
//            if (this.craftTime <= 0) {
//                List<Item> products = ((HandicraftItem)this.getItem()).getProducts();
//                this.decrement(1);
//                for (Item item : products) {
//                    user.getInventory().offer(item.getDefaultStack(), true);
//                }
//            }
//        }
//    }
//
////    @Inject(method = "writeNbt", at = @At("TAIL"))
////    public void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
////        if (this.getItem() instanceof HandicraftItem) {
////            nbt.putInt("CraftTime", this.craftTime);
////            nbt.putInt("MaxCraftTime", this.maxCraftTime);
////        }
////    }
//
////    @Inject(method = "onCraft", at = @At("HEAD"))
////    public void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
////        if (this.getItem() instanceof HandicraftItem) {
////            this.craftTime = 0;
////            this.maxCraftTime = ((HandicraftItem)this.getItem()).getMaxCraftTime();
////        }
////    }
//
//    public int getMaxCraftTime() {
//        return maxCraftTime;
//    }
//}
