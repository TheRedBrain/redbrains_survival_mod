package com.github.theredbrain.redbrainssurvivalmod.mixin.screen;

import net.minecraft.block.Blocks;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Shadow
    @Final
    private ScreenHandlerContext context;

    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    public void canUseAlternativeCraftingTables(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (context.get((world, blockPos) -> world.getBlockState(blockPos).getBlock() instanceof CraftingTableBlock, true)) {
            cir.setReturnValue(true);
        }
    }
}
