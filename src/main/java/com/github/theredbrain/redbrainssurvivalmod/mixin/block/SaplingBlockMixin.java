package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin extends PlantBlock {
    public SaplingBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    @Final
    @Mutable
    public static IntProperty STAGE = IntProperty.of("stage", 0, 4);

    @Shadow
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        throw new AssertionError();
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void saplingsGrowMuchSlower(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(63) == 0) {
            this.generate(world, pos, state, random);
        }
        ci.cancel();
    }

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    public void generateMoreStages(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {
        if ((Integer)state.get(STAGE) <= 3) {
            world.setBlockState(pos, (BlockState)state.with(STAGE, state.get(STAGE) + 1));
        } else {
            ((SaplingBlockAccessor)this).getGenerator().generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
        ci.cancel();
    }

    @Inject(method = "isFertilizable", at = @At("RETURN"), cancellable = true)
    public void isNotFertilizable (WorldView world, BlockPos pos, BlockState state, boolean isClient, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "canGrow", at = @At("RETURN"), cancellable = true)
    public void canNotGrow(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, Constants.PLANTS_VELOCITY_MULTIPLYER);

        super.onEntityCollision(state, world, pos, entity);
    }
}
