package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.passive;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {

    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void temptedByGrassGoal(CallbackInfo ci) {
        this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(new ItemConvertible[]{ItemsRegistry.STRAW.get()}), true));
    }
}
