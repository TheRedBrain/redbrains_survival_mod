package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.AttachedStemBlock;
//import net.minecraft.block.BlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.BlockView;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//// Farmers Delight
//@Mixin(AttachedStemBlock.class)
//public class AttachedStemBlockMixin {
//    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
//    private void attachedStemBlockCanPlantOnTopOfRichSoil(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(cir.getReturnValue() || floor.isOf(BlocksRegistry.RICH_SOIL_FARMLAND));
//    }
//}