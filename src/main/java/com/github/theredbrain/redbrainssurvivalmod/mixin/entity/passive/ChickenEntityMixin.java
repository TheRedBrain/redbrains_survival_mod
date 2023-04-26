package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.passive;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {

    @Shadow
    public float flapProgress;

    @Shadow
    public float maxWingDeviation;

    @Shadow
    public float prevMaxWingDeviation;

    @Shadow
    public float prevFlapProgress;

    @Shadow
    public float flapSpeed;

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {super(entityType, world);}

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    public void noRandomEggLaying(CallbackInfo ci) {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (this.onGround ? -1.0F : 4.0F) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.onGround && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed *= 0.9F;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
        ci.cancel();
    }

    // TODO delay between breeding and egg laying
    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
        if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
            serverPlayerEntity = other.getLovingPlayer();
        }

        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, null);
        }

        this.setBreedingAge(6000);
        other.setBreedingAge(6000);
        this.resetLoveTicks();
        other.resetLoveTicks();
        this.dropItem(Items.EGG);
    }

    // no more super annoying click sounds
    @Inject(method = "playStepSound", at = @At("HEAD"), cancellable = true)
    protected void doNotPlayStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        ci.cancel();
    }

    // TODO special breeding item
//    @Inject(method = "isBreedingItem", at = @At("TAIL"), cancellable = true)
//    private void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
//        if (!Boolean.TRUE.equals(cir.getReturnValue())) {
//            cir.setReturnValue(Ingredient.ofItems(
//                    BlocksRegistry.CABBAGE_CROP,
//                    BlocksRegistry.TOMATO_CROP,
//                    BlocksRegistry.RICE_CROP).test(stack));
//        }
//    }
}
