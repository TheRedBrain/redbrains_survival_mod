package com.github.theredbrain.redbrainssurvivalmod.mixin.client.network;
//
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.network.ClientPlayerInteractionManager;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(ClientPlayerInteractionManager.class)
//public class ClientPlayerInteractionManagerMixin {
//
//    @Shadow @Final private MinecraftClient client;
//
////    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
////    public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
////        if (this.client.player != null && this.client.world != null && !(this.client.player.getMainHandStack().getItem().canMine(this.client.world.getBlockState(pos), this.client.world, pos, this.client.player) || this.client.player.canHarvest(this.client.world.getBlockState(pos)))) {
////            cir.setReturnValue(false);
////            cir.cancel();
////        }
////    }
//}
