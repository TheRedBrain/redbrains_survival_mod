package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {

//    @Shadow
//    @Final
//    @Mutable
//    private static Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{ItemsRegistry.BACON});

    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void temptedByMoreThingsGoal(CallbackInfo ci) {
        this.goalSelector.add(3, new TemptGoal(this, 1.1D, Ingredient.ofItems(new ItemConvertible[]{Items.BROWN_MUSHROOM,Items.RED_MUSHROOM,Items.CARROT, Items.POTATO, Items.BEETROOT/*, BlocksRegistry.BROWN_MUSHROOM_COLONY, BlocksRegistry.RED_MUSHROOM_COLONY, ItemsRegistry.CABBAGE, ItemsRegistry.TOMATO*/}), true));
    }
}
