package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Shadow
    FlowableFluid fluid;

    @Shadow
    static IntProperty LEVEL;

    @Inject(method = "tryDrainFluid", at = @At("HEAD"), cancellable = true)
    void doNotDrainFluid(WorldAccess world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        if ((Integer)state.get(LEVEL) == 0) { // TODO every level
            cir.setReturnValue(new ItemStack(this.fluid.getBucketItem()));
        } else {
            cir.setReturnValue(ItemStack.EMPTY);
        }
        cir.cancel();
    }
}
