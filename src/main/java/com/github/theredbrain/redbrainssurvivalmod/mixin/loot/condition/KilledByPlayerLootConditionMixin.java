package com.github.theredbrain.redbrainssurvivalmod.mixin.loot.condition;

import com.github.theredbrain.redbrainssurvivalmod.registry.StatusEffectsRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KilledByPlayerLootCondition.class)
public class KilledByPlayerLootConditionMixin {

    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void testPlayerKillEffect(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(lootContext.hasParameter(LootContextParameters.LAST_DAMAGE_PLAYER) || ((LivingEntity) lootContext.get(LootContextParameters.THIS_ENTITY)).hasStatusEffect(StatusEffectsRegistry.PLAYER_KILL));
    }
}
