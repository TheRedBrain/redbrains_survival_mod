package com.github.theredbrain.redbrainssurvivalmod.entity.ai.goal;
//
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.ai.goal.Goal;
//import net.minecraft.entity.mob.DrownedEntity;
//import net.minecraft.entity.mob.MobEntity;
//import net.minecraft.entity.vehicle.BoatEntity;
//
//public class DrownedAttackBoatGoal extends Goal {
//
//    private final DrownedEntity mob;
//    private BoatEntity target;
//    private int cooldown;
//
//    public DrownedAttackBoatGoal(DrownedEntity mob) {
//        this.mob = mob;
//    }
//
//    public boolean canStart() {
//        BoatEntity boatEntity = this.mob.getBoatTarget();
//        if (boatEntity == null) {
//            return false;
//        } else {
//            this.target = boatEntity;
//            return true;
//        }
//    }
//
//    public boolean shouldContinue() {
//        if (!this.target.isAlive()) {
//            return false;
//        } else if (this.mob.squaredDistanceTo(this.target) > 225.0D) {
//            return false;
//        } else {
//            return !this.mob.getNavigation().isIdle() || this.canStart();
//        }
//    }
//
//    public void stop() {
//        this.target = null;
//        this.mob.getNavigation().stop();
//    }
//
//    public boolean shouldRunEveryTick() {
//        return true;
//    }
//
//    public void tick() {
//        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
//        double d = (double)(this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F);
//        double e = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
//        double f = 0.8D;
//        if (e > d && e < 16.0D) {
//            f = 1.33D;
//        } else if (e < 225.0D) {
//            f = 0.6D;
//        }
//
//        this.mob.getNavigation().startMovingTo(this.target, f);
//        this.cooldown = Math.max(this.cooldown - 1, 0);
//        if (!(e > d)) {
//            if (this.cooldown <= 0) {
//                this.cooldown = 20;
//                this.mob.tryAttack(this.target);
//            }
//        }
//    }
//}
