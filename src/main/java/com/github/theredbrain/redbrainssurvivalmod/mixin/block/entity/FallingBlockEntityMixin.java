package com.github.theredbrain.redbrainssurvivalmod.mixin.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.plants.FallingGourdBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LandingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {

    @Shadow private boolean hurtEntities;

    @Shadow private BlockState block;

    @Shadow private boolean destroyedOnLanding;

    @Shadow private float fallHurtAmount;

    @Shadow private int fallHurtMax;

    public FallingBlockEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        DamageSource damageSource2;
        Predicate<Entity> predicate;
        if (!this.hurtEntities) {
            return false;
        }
        int i = MathHelper.ceil(fallDistance - 1.0f);
        if (i < 0) {
            return false;
        }
        if (this.block.getBlock() instanceof LandingBlock) {
            LandingBlock landingBlock = (LandingBlock)((Object)this.block.getBlock());
            predicate = landingBlock.getEntityPredicate();
            damageSource2 = landingBlock.getDamageSource(this);
        } else {
            predicate = EntityPredicates.EXCEPT_SPECTATOR;
            damageSource2 = this.getDamageSources().fallingBlock(this);
        }
        float f = Math.min(MathHelper.floor((float)i * this.fallHurtAmount), this.fallHurtMax);
        this.world.getOtherEntities(this, this.getBoundingBox(), predicate).forEach(entity -> entity.damage(damageSource2, f));
        boolean bl = this.block.getBlock() instanceof FallingGourdBlock;
        if (bl && f > 5.0f && this.random.nextFloat() < (float)(f - 5) * 0.1f) {
            this.destroyedOnLanding = true;
        }
        return false;
    }
}
