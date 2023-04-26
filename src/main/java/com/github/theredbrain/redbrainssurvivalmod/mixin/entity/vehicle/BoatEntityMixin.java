package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.vehicle;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.damage.DamageSource;
//import net.minecraft.entity.mob.DrownedEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.vehicle.BoatEntity;
//import net.minecraft.world.GameRules;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(BoatEntity.class)
//public abstract class BoatEntityMixin extends Entity {
//
//    public BoatEntityMixin(EntityType<?> type, World world) {
//        super(type, world);
//    }
//
//    @Shadow
//    protected void dropItems(DamageSource source) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public void setDamageWobbleStrength(float wobbleStrength) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public float getDamageWobbleStrength() {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public void setDamageWobbleTicks(int wobbleTicks) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public void setDamageWobbleSide(int side) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public int getDamageWobbleSide() {
//        throw new AssertionError();
//    }
//
//    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
//    public void damageFromDrowned(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
//        if (this.isInvulnerableTo(source)) {
//            cir.setReturnValue(false);
//        } else if (!this.world.isClient && !this.isRemoved()) {
//            this.setDamageWobbleSide(-this.getDamageWobbleSide());
//            this.setDamageWobbleTicks(10);
//            this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0F);
//            this.scheduleVelocityUpdate();
//            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
//            boolean bl = (source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).getAbilities().creativeMode || source.getAttacker() instanceof DrownedEntity);
//            if (bl || this.getDamageWobbleStrength() > 40.0F) {
//                if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
//                    this.dropItems(source);
//                }
//
//                this.discard();
//            }
//
//            cir.setReturnValue(true);
//        } else {
//            cir.setReturnValue(true);
//        }
//    }
//}
