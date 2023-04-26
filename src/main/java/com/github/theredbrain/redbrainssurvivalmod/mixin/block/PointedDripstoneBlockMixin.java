package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

    @Shadow
    private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        throw new AssertionError();
    }

    @Shadow
    private static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
        throw new AssertionError();
    }

    @Inject(method = "getCauldronPos", at = @At("HEAD"), cancellable = true)
    private static void getCauldronPos(World world, BlockPos pos, Fluid fluid, CallbackInfoReturnable<BlockPos> cir) {
        Predicate<BlockState> predicate = (state) -> {
            return ((state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlockInvoker)state.getBlock()).canBeFilledByDripstone(fluid))/* || (state.getBlock() instanceof AbstractNetheriteCauldronBlock && ((AbstractNetheriteCauldronBlock)state.getBlock()).canBeFilledByDripstone(fluid))*/);
        };
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
            return canDripThrough(world, posx, state);
        };
        cir.setReturnValue((BlockPos)searchInDirection(world, pos, Direction.DOWN.getDirection(), biPredicate, predicate, 11).orElse((BlockPos) null));
    }
}
