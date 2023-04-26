package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.passive;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillageGossipType;
import net.minecraft.village.VillagerGossips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    /*
    TODO
     overhaul trading
     - only trades of current tier progress villager level
     - when level xp is full, special trade unlocks to progress to next tier
     - normal trades can only be traded once and get replaced by another one
     - some trades are permanent
     - prevent trading loops (eg. one prof sells glass, another buys glass panes) with a profit for the player
     permanent professions and caste system
     - each villager has a permanent profession determined by parents or zombie villager
     - breeding only inside caste
    */
    @Shadow
    protected abstract void clearSpecialPrices();

    @Shadow
    public abstract VillagerGossips getGossip();

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void forgetGossip(DamageSource source, CallbackInfo ci) {

        this.clearSpecialPrices();

        this.getGossip().remove(VillageGossipType.MAJOR_POSITIVE);
        this.getGossip().remove(VillageGossipType.MINOR_POSITIVE);
        this.getGossip().remove(VillageGossipType.MAJOR_NEGATIVE);
        this.getGossip().remove(VillageGossipType.MINOR_NEGATIVE);
        this.getGossip().remove(VillageGossipType.TRADING);
    }

    @Inject(at = @At("HEAD"), method = "isReadyToBreed", cancellable = true)
    private void isNotReadyToBreed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
