package com.github.theredbrain.redbrainssurvivalmod.mixin.loot.condition;

import com.github.theredbrain.redbrainssurvivalmod.registry.StatusEffectsRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RandomChanceWithLootingLootCondition.class)
public class RandomChanceWithLootingLootConditionMixin {

    @Shadow
    @Final
    float chance;

    @Shadow
    @Final
    float lootingMultiplier;

    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void testExtraLootingEffect(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
//        Entity killedEntity = (Entity) lootContext.get(LootContextParameters.THIS_ENTITY);
//        if (killedEntity instanceof LivingEntity) {
//            int extraLooting = 0;
//            if (((LivingEntity) killedEntity).hasStatusEffect(Beacoverhaul.EXTRA_LOOTING)) {
//                extraLooting = ((LivingEntity) killedEntity).getStatusEffect(Beacoverhaul.EXTRA_LOOTING).getAmplifier();
//            }
//            i = i + extraLooting;
//        }
//        cir.setReturnValue(lootContext.getRandom().nextFloat() < this.chance + (float)i * this.lootingMultiplier);


        Entity killerEntity = (Entity)lootContext.get(LootContextParameters.KILLER_ENTITY);
        Entity killedEntity = (Entity) lootContext.get(LootContextParameters.THIS_ENTITY);
        int i = 0;
        if (killerEntity instanceof LivingEntity) {
            i = EnchantmentHelper.getLooting((LivingEntity)killerEntity);
        }
        if (killedEntity instanceof LivingEntity) {
            int extraLooting = 0;
            if (((LivingEntity) killedEntity).hasStatusEffect(StatusEffectsRegistry.EXTRA_LOOTING)) {
                extraLooting = ((LivingEntity) killedEntity).getStatusEffect(StatusEffectsRegistry.EXTRA_LOOTING).getAmplifier();
            }
            i = i + extraLooting;
        }

        cir.setReturnValue(lootContext.getRandom().nextFloat() < this.chance + (float)i * this.lootingMultiplier);
//        return lootContext.getRandom().nextFloat() < this.chance + (float)i * this.lootingMultiplier;
    }
}
