package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.player;

import com.github.theredbrain.redbrainssurvivalmod.registry.StatusEffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

	@Shadow
	private int foodLevel;

	@Shadow
	private float saturationLevel;

	@Shadow
	private float exhaustion;

	@Shadow
	private int foodTickTimer;

	@Shadow
	private int prevFoodLevel;

	int regenTick = 0;

	/**
	 * @author TheRedBrain
	 */
	@Inject(method = "<init>", at = @At("TAIL"))
	public void HungerManager(CallbackInfo ci) {
		this.foodLevel = 60;
		this.saturationLevel = 0;
		// inject into a constructor
	}

	@Inject(method = "add", at = @At("HEAD"), cancellable = true)
	public void addExcessiveFoodToSaturation(int food, float saturationModifier, CallbackInfo ci) {
		float foodToSaturation = -60 + this.foodLevel + food;
		this.foodLevel = Math.min(food + this.foodLevel, 60);
		this.saturationLevel = Math.min(this.saturationLevel + Math.max(foodToSaturation * saturationModifier, 0), (float)this.foodLevel);
		ci.cancel();
	}

	@Inject(method = "isNotFull", at = @At("RETURN"), cancellable = true)
	public void isNotFullForLonger(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(this.foodLevel < 60);
		cir.cancel();
	}

	/**
	 * @author TheRedBrain
	 */
	@Overwrite
	public void update(PlayerEntity player) {
		Difficulty difficulty = player.world.getDifficulty();
		this.prevFoodLevel = this.foodLevel;
		if (this.exhaustion > 4.0F) {
			this.exhaustion -= 4.0F;
			if ((this.saturationLevel > 72 && this.foodLevel <= 54)
					|| (this.saturationLevel > 64 && this.foodLevel <= 48)
					|| (this.saturationLevel > 56 && this.foodLevel <= 42)
					|| (this.saturationLevel > 48 && this.foodLevel <= 36)
					|| (this.saturationLevel > 40 && this.foodLevel <= 30)
					|| (this.saturationLevel > 32 && this.foodLevel <= 24)
					|| (this.saturationLevel > 24 && this.foodLevel <= 18)
					|| (this.saturationLevel > 16 && this.foodLevel <= 12)
					|| (this.saturationLevel > 8 && this.foodLevel <= 6)
					|| (this.saturationLevel > 0 && this.foodLevel <= 0)) {
				this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
			} else if (difficulty != Difficulty.PEACEFUL) {
				this.foodLevel = Math.max(this.foodLevel - 1, 0);
			}
		}

		if (this.saturationLevel >= 72) {
			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
			player.removeStatusEffect(StatusEffectsRegistry.FAT);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.OBESE, -1, 0, true, false, false));
		} else if (this.saturationLevel >= 64) {
			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FAT, -1, 0, true, false, false));
		} else if (this.saturationLevel >= 56) {
			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
			player.removeStatusEffect(StatusEffectsRegistry.FAT);
			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.CHUBBY, -1, 0, true, false, false));
		} else if (this.saturationLevel >= 48) {
			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
			player.removeStatusEffect(StatusEffectsRegistry.FAT);
			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.PLUMP, -1, 0, true, false, false));
		} else {
			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
			player.removeStatusEffect(StatusEffectsRegistry.FAT);
			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
		}

		boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);

		if (this.foodLevel > 24) {
			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);

			if(bl && player.getHealth() < player.getMaxHealth()) {
				++this.foodTickTimer;
				++this.regenTick;
				if (this.regenTick >= 600) {
					this.regenTick = 0;
					player.heal(1);
				}
			}
		} else if (this.foodLevel > 18) {
			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.PECKISH, -1, 0, true, false, false));
		} else if (this.foodLevel > 12) {
			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.HUNGRY, -1, 0, true, false, false));
		}  else if (this.foodLevel > 6) {
			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FAMISHED, -1, 0, true, false, false));
		} else if (this.foodLevel > 0) {
			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.STARVING, -1, 0, true, false, false));
		} else if (this.foodLevel <= 0 && this.saturationLevel <= 0) {
			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.DYING, -1, 0, true, false, false));
			++this.foodTickTimer;
			if (this.foodTickTimer >= 80) {
//				if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
//					player.damage(player.getDamageSources().starve(), 1.0F);
//				}
				player.damage(player.getDamageSources().starve(), 1.0F);

				this.foodTickTimer = 0;
			}
			this.regenTick = 0;
		} else {
			this.foodTickTimer = 0;
			this.regenTick = 0;
		}

		float currentHealth = player.getHealth();
		if (currentHealth <= 2) {
			player.removeStatusEffect(StatusEffectsRegistry.HURT);
			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.DYING, -1, 0, true, false, false));
		} else if (currentHealth <= 4) {
			player.removeStatusEffect(StatusEffectsRegistry.HURT);
			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.CRIPPLED, -1, 0, true, false, false));
		} else if (currentHealth <= 6) {
			player.removeStatusEffect(StatusEffectsRegistry.HURT);
			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.WOUNDED, -1, 0, true, false, false));
		} else if (currentHealth <= 8) {
			player.removeStatusEffect(StatusEffectsRegistry.HURT);
			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.INJURED, -1, 0, true, false, false));
		} else if (currentHealth <= 10) {
			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.HURT, -1, 0, true, false, false));
		} else if (currentHealth >= 11) {
			player.removeStatusEffect(StatusEffectsRegistry.HURT);
			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
			player.removeStatusEffect(StatusEffectsRegistry.DYING);
		}
	}

//	@Inject(at = @At("HEAD"), method = "update", cancellable = true)
//	private void hardcoreRegeneration(PlayerEntity player, CallbackInfo ci) {
//		Difficulty difficulty = player.world.getDifficulty();
//		this.prevFoodLevel = this.foodLevel;
//		if (this.exhaustion > 4.0F) {
//			this.exhaustion -= 4.0F;
//			if (this.saturationLevel > this.foodLevel) {
//				this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
//			} else if (difficulty != Difficulty.PEACEFUL) {
//				this.foodLevel = Math.max(this.foodLevel - 1, 0);
//			}
//		}
//
//		if (this.saturationLevel >= 72) {
//			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
//			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAT);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.OBESE, -1, 0, true, false, false));
//		} else if (this.saturationLevel >= 64) {
//			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
//			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
//			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FAT, -1, 0, true, false, false));
//		} else if (this.saturationLevel >= 56) {
//			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
//			player.removeStatusEffect(StatusEffectsRegistry.FAT);
//			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.CHUBBY, -1, 0, true, false, false));
//		} else if (this.saturationLevel >= 48) {
//			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAT);
//			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.PLUMP, -1, 0, true, false, false));
//		} else {
//			player.removeStatusEffect(StatusEffectsRegistry.PLUMP);
//			player.removeStatusEffect(StatusEffectsRegistry.CHUBBY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAT);
//			player.removeStatusEffect(StatusEffectsRegistry.OBESE);
//		}
//
//		boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
//
//		if (this.foodLevel > 24) {
//			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
//			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
//			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//
//			if(bl && player.getHealth() < player.getMaxHealth()) {
//				++this.foodTickTimer;
//				++this.regenTick;
//				if (this.regenTick >= 600) {
//					this.regenTick = 0;
//					player.heal(1);
//				}
//			}
//		} else if (this.foodLevel > 18) {
//			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
//			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.PECKISH, -1, 0, true, false, false));
//		} else if (this.foodLevel > 12) {
//			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
//			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
//			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.HUNGRY, -1, 0, true, false, false));
//		}  else if (this.foodLevel > 6) {
//			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
//			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
//			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.FAMISHED, -1, 0, true, false, false));
//		} else if (this.foodLevel > 0) {
//			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
//			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.STARVING, -1, 0, true, false, false));
//		} else if (this.foodLevel <= 0 && this.saturationLevel <= 0) {
//			player.removeStatusEffect(StatusEffectsRegistry.PECKISH);
//			player.removeStatusEffect(StatusEffectsRegistry.HUNGRY);
//			player.removeStatusEffect(StatusEffectsRegistry.FAMISHED);
//			player.removeStatusEffect(StatusEffectsRegistry.STARVING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.DYING, -1, 0, true, false, false));
//			++this.foodTickTimer;
//			if (this.foodTickTimer >= 80) {
////				if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
////					player.damage(player.getDamageSources().starve(), 1.0F);
////				}
//				player.damage(player.getDamageSources().starve(), 1.0F);
//
//				this.foodTickTimer = 0;
//			}
//			this.regenTick = 0;
//		} else {
//			this.foodTickTimer = 0;
//			this.regenTick = 0;
//		}
//
//		float currentHealth = player.getHealth();
//		if (currentHealth <= 2) {
//			player.removeStatusEffect(StatusEffectsRegistry.HURT);
//			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
//			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
//			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.DYING, -1, 0, true, false, false));
//		} else if (currentHealth <= 4) {
//			player.removeStatusEffect(StatusEffectsRegistry.HURT);
//			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
//			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.CRIPPLED, -1, 0, true, false, false));
//		} else if (currentHealth <= 6) {
//			player.removeStatusEffect(StatusEffectsRegistry.HURT);
//			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
//			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.WOUNDED, -1, 0, true, false, false));
//		} else if (currentHealth <= 8) {
//			player.removeStatusEffect(StatusEffectsRegistry.HURT);
//			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
//			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.INJURED, -1, 0, true, false, false));
//		} else if (currentHealth <= 10) {
//			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
//			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
//			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//			player.addStatusEffect(new StatusEffectInstance(StatusEffectsRegistry.HURT, -1, 0, true, false, false));
//		} else if (currentHealth >= 11) {
//			player.removeStatusEffect(StatusEffectsRegistry.HURT);
//			player.removeStatusEffect(StatusEffectsRegistry.INJURED);
//			player.removeStatusEffect(StatusEffectsRegistry.WOUNDED);
//			player.removeStatusEffect(StatusEffectsRegistry.CRIPPLED);
//			player.removeStatusEffect(StatusEffectsRegistry.DYING);
//		}
//		ci.cancel();
//	}
}
