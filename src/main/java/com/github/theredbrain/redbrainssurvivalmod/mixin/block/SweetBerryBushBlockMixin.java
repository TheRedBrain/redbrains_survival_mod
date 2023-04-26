package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends PlantBlock {

    @Shadow
    @Final
    public static IntProperty AGE;

    public SweetBerryBushBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void gatherCustomBerries(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if ((Integer)state.get(AGE) == 3) {
            int j = 1 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(ItemsRegistry.SWEET_BERRIES.get(), j));
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState blockState = (BlockState)state.with(AGE, 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            cir.setReturnValue(ActionResult.success(world.isClient));
        } else {
            cir.setReturnValue(super.onUse(state, world, pos, player, hand, hit));
        }
        cir.cancel();
    }

    @Inject(method = "getPickStack", at = @At("RETURN"), cancellable = true)
    public void pickBushBlock(BlockView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(new ItemStack(Blocks.SWEET_BERRY_BUSH));
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void takesLongerToGrow(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        int i = (Integer)state.get(AGE);
        BlockState blockState = world.getBlockState(pos.down());
        if (i < 3 && random.nextInt(20) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            BlockState newState = (BlockState)state.with(AGE, i + 1);
            world.setBlockState(pos, newState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(newState));
        }
        ci.cancel();
    }

    @Inject(method = "isFertilizable", at = @At("RETURN"), cancellable = true)
    public void isNotFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "canGrow", at = @At("RETURN"), cancellable = true)
    public void canNotGrow(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

}
