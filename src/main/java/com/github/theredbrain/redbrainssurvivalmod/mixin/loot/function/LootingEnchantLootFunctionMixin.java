package com.github.theredbrain.redbrainssurvivalmod.mixin.loot.function;

import com.github.theredbrain.redbrainssurvivalmod.registry.StatusEffectsRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.provider.number.LootNumberProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootingEnchantLootFunction.class)
public class LootingEnchantLootFunctionMixin {

    @Shadow
    @Final
    LootNumberProvider countRange;

    @Shadow
    boolean hasLimit() {
        throw new AssertionError();
    }
    @Shadow
    @Final
    int limit;

    @Inject(method = "process", at = @At("HEAD"), cancellable = true)
    public void processExtraLootingEffect(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        Entity killerEntity = (Entity)context.get(LootContextParameters.KILLER_ENTITY);
        Entity killedEntity = (Entity)context.get(LootContextParameters.THIS_ENTITY);
        int i = 0;
        if (killerEntity instanceof LivingEntity) {
            i = i + EnchantmentHelper.getLooting((LivingEntity) killerEntity);
        }
        if (killedEntity instanceof LivingEntity && ((LivingEntity) killedEntity).hasStatusEffect(StatusEffectsRegistry.EXTRA_LOOTING)) {
            i = i + ((LivingEntity) killedEntity).getStatusEffect(StatusEffectsRegistry.EXTRA_LOOTING).getAmplifier();
        }

        if (i == 0) {
            cir.setReturnValue(stack);
        }

        float f = (float)i * this.countRange.nextFloat(context);
        stack.increment(Math.round(f));
        if (this.hasLimit() && stack.getCount() > this.limit) {
            stack.setCount(this.limit);
        }

        cir.setReturnValue(stack);
    }
}
