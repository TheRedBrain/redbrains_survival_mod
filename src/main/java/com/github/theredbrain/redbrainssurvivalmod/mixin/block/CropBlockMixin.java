package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.CropBlock;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(CropBlock.class)
//public class CropBlockMixin extends Block{
//
//    public CropBlockMixin(Settings settings) {
//        super(settings);
//    }
//
//    @Shadow
//    public int getMaxAge() {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    protected int getAge(BlockState state) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public BlockState withAge(int age) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public boolean isMature(BlockState state) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
//        throw new AssertionError();
//    }
//
//    @Inject(method = "hasRandomTicks", at = @At("RETURN"), cancellable = true)
//    public void hasSometimesRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(!this.isMature(state) && !(this.getAge(state) == 0));
//    }
//
//    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
//    public void waitsForFertilization(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
//        if (world.getBaseLightLevel(pos, 0) >= 9) {
//            int i = this.getAge(state);
//            if (i > 0 && i < this.getMaxAge()) {
//                float f = getAvailableMoisture(this, world, pos);
//                if (random.nextInt((int)(100.0F / f) + 1) == 0) {
//                    world.setBlockState(pos, this.withAge(i + 1), 2);
//                }
//            }
//        }
//        ci.cancel();
//    }
//
//    @Inject(method = "isFertilizable", at = @At("RETURN"), cancellable = true)
//    public void isFertilizableSometimes(BlockView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(this.getAge(state) == 0);
//    }
//
//    @Inject(method = "canGrow", at = @At("RETURN"), cancellable = true)
//    public void canGrowSometimes(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(this.getAge(state) == 0);
//    }
////    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
////    private void cropBlockCanPlantOnTopOfRichSoil(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
////        cir.setReturnValue(cir.getReturnValue() || floor.isOf(BlocksRegistry.RICH_SOIL_FARMLAND));
////    }
//}
