package com.github.theredbrain.redbrainssurvivalmod.mixin.fluid;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
//import net.minecraft.fluid.LavaFluid;
//import net.minecraft.item.Item;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(LavaFluid.class)
//public class LavaFluidMixin {
//
//    @Inject(method = "getBucketItem", at = @At("HEAD"), cancellable = true)
//    public void getBucketItem(CallbackInfoReturnable<Item> cir) {
//        cir.setReturnValue(ItemsRegistry.NETHERITE_LAVA_BUCKET.get());
//    }
//}
