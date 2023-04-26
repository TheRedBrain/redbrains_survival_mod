package com.github.theredbrain.redbrainssurvivalmod.mixin.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.AffectsVelocityOnCollision;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getVelocityMultiplier", at = @At("RETURN"), cancellable = true)
    protected void getVelocityMultiplierOnCollision(CallbackInfoReturnable<Float> cir) {
        BlockState blockState1 = this.world.getBlockState(this.getBlockPos());
        float f = cir.getReturnValue();
        if (blockState1.getBlock() instanceof AffectsVelocityOnCollision) {
            float f1 = f * ((AffectsVelocityOnCollision)blockState1.getBlock()).getVelocityMultiplierOnCollision();
            cir.setReturnValue(f1);
            cir.cancel();
        }
    }
}