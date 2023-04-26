package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.mob;
//
//import com.github.theredbrain.redbrainssurvivalmod.entity.ai.goal.DrownedAttackBoatGoal;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.ai.goal.ActiveTargetGoal;
//import net.minecraft.entity.mob.DrownedEntity;
//import net.minecraft.entity.mob.ZombieEntity;
//import net.minecraft.entity.passive.IronGolemEntity;
//import net.minecraft.entity.vehicle.BoatEntity;
//import net.minecraft.world.World;
//import org.jetbrains.annotations.Nullable;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(DrownedEntity.class)
//public class DrownedEntityMixin extends ZombieEntity {
//
//    @Nullable
//    private BoatEntity boatTarget;
//
//    public DrownedEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) {
//        super(entityType, world);
//    }
//
//    @Inject(method = "initCustomGoals", at = @At("TAIL"))
//    protected void initCustomGoals(CallbackInfo ci) {
//        this.goalSelector.add(2, new DrownedAttackBoatGoal((DrownedEntity) this));
//        this.targetSelector.add(4, new ActiveTargetGoal(this, BoatEntity.class, true));
//    }
//
//    @Nullable
//    public BoatEntity getBoatTarget() {
//        return this.boatTarget;
//    }
//}
