package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.player;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.collection.DefaultedList;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(PlayerInventory.class)
//public class PlayerInventoryMixin {
//
//    @Shadow
//    @Final
//    public DefaultedList<ItemStack> main;
//
//    @Shadow
//    public int selectedSlot;
//
//    @Shadow
//    @Final
//    public PlayerEntity player;
//
//    @Inject(method = "getMainHandStack", at = @At("HEAD"), cancellable = true)
//    public void bam$getMainHandStack(CallbackInfoReturnable<ItemStack> cir) {
//        if (!player.isCreative()) {
//            ItemStack stack = ItemStack.EMPTY;
//            if (PlayerInventory.isValidHotbarIndex(this.selectedSlot)) {
//                stack = this.main.get(this.selectedSlot);
//            }
//            cir.setReturnValue(stack.isEmpty() ? ItemsRegistry.EMPTY_HAND.getDefaultStack() : stack);
//            cir.cancel();
//        }
//    }
//}
