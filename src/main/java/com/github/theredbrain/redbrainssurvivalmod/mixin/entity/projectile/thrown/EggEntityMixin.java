package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.projectile.thrown;


import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EggEntity.class)
public abstract class EggEntityMixin extends ThrownItemEntity {

    protected EggEntityMixin(EntityType<? extends EggEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("HEAD"), method = "onCollision", cancellable = true)
    protected void spawnRawEggOrChick(HitResult hitResult, CallbackInfo ci) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (this.random.nextInt(8) == 0) {
                ChickenEntity chickenEntity = (ChickenEntity)EntityType.CHICKEN.create(this.world);
                if (chickenEntity != null) {
                    chickenEntity.setBreedingAge(-24000);
                    chickenEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                    this.world.spawnEntity(chickenEntity);
                }
            }
            else {
                this.dropItem(ItemsRegistry.RAW_EGG.get());
            }

            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
            ci.cancel();
        }

    }
}
