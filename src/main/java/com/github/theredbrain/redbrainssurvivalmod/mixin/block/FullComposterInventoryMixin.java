package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.ComposterBlock;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.math.Direction;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(ComposterBlock.FullComposterInventory.class)
//public class FullComposterInventoryMixin {
//
//    @Shadow
//    private boolean dirty;
//
//    @Inject(method = "canExtract", at = @At("RETURN"), cancellable = true)
//    public void canExtract(int slot, ItemStack stack, Direction dir, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(!this.dirty && dir == Direction.DOWN && stack.isOf(Item.fromBlock(BlocksRegistry.ORGANIC_COMPOST)));
//    }
//}
