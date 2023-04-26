package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.passive;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {
    protected CowEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void temptedByGrassGoal(CallbackInfo ci) {
        this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(ItemsRegistry.STRAW.get()), false));
    }

    // TODO when panicking damage and knockback every living entity in range
}
