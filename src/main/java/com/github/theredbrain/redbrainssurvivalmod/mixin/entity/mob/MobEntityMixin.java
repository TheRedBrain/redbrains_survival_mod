package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.mob;

import com.github.theredbrain.redbrainssurvivalmod.entity.ai.goal.EatCropsGoal;
import com.github.theredbrain.redbrainssurvivalmod.entity.ai.goal.GroundFoodMateGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;initGoals()V", shift = At.Shift.AFTER))
    private void initEatingGoals(EntityType<? extends MobEntity> entityType, World world, CallbackInfo ci) {
        // animals
        if(((Object) this) instanceof AnimalEntity && !(((Object) this) instanceof BeeEntity)) {
            int matePriority = -1;

            for(PrioritizedGoal goal : this.goalSelector.getGoals()) {
                if (goal.getGoal() instanceof AnimalMateGoal) {
                    matePriority = goal.getPriority();
                    break;
                }
            }
            if(matePriority > -1) {
                this.goalSelector.add(matePriority, new EatCropsGoal((AnimalEntity) (Object) this));
                this.goalSelector.add(matePriority, new GroundFoodMateGoal((AnimalEntity) (Object) this));
            }
        }
    }

    @Inject(method = "getDropChance", at = @At("HEAD"), cancellable = true)
    private void alwaysDropEquipment(EquipmentSlot slot, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1.0F);
    }
}
