package com.github.theredbrain.redbrainssurvivalmod.mixin.client.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private long heartJumpEndTick;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Shadow private int ticks;

    @Shadow private int lastHealthValue;

    @Shadow private long lastHealthCheckTime;

    @Shadow private int renderHealthValue;

    @Shadow @Final private Random random;

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking);

    @Shadow protected abstract LivingEntity getRiddenEntity();

    @Shadow protected abstract int getHeartCount(LivingEntity entity);

    @Shadow protected abstract int getHeartRows(int heartCount);
    /**
     * @author TheRedBrain
     */
    @Overwrite
    private void renderStatusBars(MatrixStack matrices) {
        int ac;
        int ab;
        int aa;
        int z;
        int y;
        int x;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        int i = MathHelper.ceil(playerEntity.getHealth());
        boolean bl = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long l = Util.getMeasuringTimeMs();
        if (i < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = this.ticks + 20;
        } else if (i > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
            this.heartJumpEndTick = this.ticks + 10;
        }
        if (l - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = i;
            this.renderHealthValue = i;
            this.lastHealthCheckTime = l;
        }
        this.lastHealthValue = i;
        int j = this.renderHealthValue;
        this.random.setSeed(this.ticks * 312871);
        HungerManager hungerManager = playerEntity.getHungerManager();
        int g = (int) hungerManager.getSaturationLevel();
        int k = hungerManager.getFoodLevel();
        int m = this.scaledWidth / 2 - 91;
        int n = this.scaledWidth / 2 + 91;
        int o = this.scaledHeight - 39;
        float f = Math.max((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), (float)Math.max(j, i));
        int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
        int q = MathHelper.ceil((f + (float)p) / 2.0f / 10.0f);
        int r = Math.max(10 - (q - 2), 3);
        int s = o - (q - 1) * r - 10;
        int t = o - 10;
        int u = playerEntity.getArmor();
        int v = -1;
        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
            v = this.ticks % MathHelper.ceil(f + 5.0f);
        }
        this.client.getProfiler().push("armor");
        for (int w = 0; w < 10; ++w) {
            if (u <= 0) continue;
            x = m + w * 8;
            if (w * 2 + 1 < u) {
                InGameHud.drawTexture(matrices, x, s, 34, 9, 9, 9);
            }
            if (w * 2 + 1 == u) {
                InGameHud.drawTexture(matrices, x, s, 25, 9, 9, 9);
            }
            if (w * 2 + 1 <= u) continue;
            InGameHud.drawTexture(matrices, x, s, 16, 9, 9, 9);
        }
        this.client.getProfiler().swap("health");
        this.renderHealthBar(matrices, playerEntity, m, o, r, v, f, i, j, p, bl);
        LivingEntity livingEntity = this.getRiddenEntity();
        x = this.getHeartCount(livingEntity);
        if (x == 0) {
            this.client.getProfiler().swap("food");
            for (y = 0; y < 10; ++y) {
                z = o;
                aa = 0;
                ab = 0;
                if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                    aa += 54;
                    ab = 13;
                }
//                if (playerEntity.getHungerManager().getSaturationLevel() <= 0.0f && this.ticks % (k * 3 + 1) == 0) {
//                    z += this.random.nextInt(3) - 1;
//                }
                // TODO shake hungerBar when losing food/fat
                ac = n - y * 8 - 9;
                InGameHud.drawTexture(matrices, ac, z, 16 + ab * 9, 27, 9, 9);
                if (y * 6 + 5 < k) {
                    InGameHud.drawTexture(matrices, ac, z, aa, 112, 9, 9);
                } else if (y * 6 + 5 == k) {
                    InGameHud.drawTexture(matrices, ac, z, aa + 9, 112, 9, 9);
                } else if (y * 6 + 4 == k) {
                    InGameHud.drawTexture(matrices, ac, z, aa + 18, 112, 9, 9);
                } else if (y * 6 + 3 == k) {
                    InGameHud.drawTexture(matrices, ac, z, aa + 27, 112, 9, 9);
                } else if (y * 6 + 2 == k) {
                    InGameHud.drawTexture(matrices, ac, z, aa + 36, 112, 9, 9);
                } else if (y * 6 + 1 == k) {
                    InGameHud.drawTexture(matrices, ac, z, aa + 45, 112, 9, 9);
                }
                // TODO fat
                if (y * 8 + 7 < g) {
                    InGameHud.drawTexture(matrices, ac, z, 0, 121, 9, 9);
                } else if (y * 8 + 7 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 9, 121, 9, 9);
                } else if (y * 8 + 6 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 18, 121, 9, 9);
                } else if (y * 8 + 5 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 27, 121, 9, 9);
                } else if (y * 8 + 4 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 36, 121, 9, 9);
                } else if (y * 8 + 3 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 45, 121, 9, 9);
                } else if (y * 8 + 2 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 54, 121, 9, 9);
                } else if (y * 8 + 1 == g) {
                    InGameHud.drawTexture(matrices, ac, z, 63, 121, 9, 9);
                }
            }
            t -= 10;
        }
        this.client.getProfiler().swap("air");
        y = playerEntity.getMaxAir();
        z = Math.min(playerEntity.getAir(), y);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
            aa = this.getHeartRows(x) - 1;
            t -= aa * 10;
            ab = MathHelper.ceil((double)(z - 2) * 10.0 / (double)y);
            ac = MathHelper.ceil((double)z * 10.0 / (double)y) - ab;
            for (int ad = 0; ad < ab + ac; ++ad) {
                if (ad < ab) {
                    InGameHud.drawTexture(matrices, n - ad * 8 - 9, t, 16, 18, 9, 9);
                    continue;
                }
                InGameHud.drawTexture(matrices, n - ad * 8 - 9, t, 25, 18, 9, 9);
            }
        }
        this.client.getProfiler().pop();
    }
}
