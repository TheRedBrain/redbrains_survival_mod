package com.github.theredbrain.redbrainssurvivalmod.entity.mob;
//
//import net.minecraft.entity.*;
//import net.minecraft.entity.attribute.DefaultAttributeContainer;
//import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.effect.StatusEffectInstance;
//import net.minecraft.entity.effect.StatusEffects;
//import net.minecraft.entity.mob.SpiderEntity;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.world.LocalDifficulty;
//import net.minecraft.world.ServerWorldAccess;
//import net.minecraft.world.World;
//import org.jetbrains.annotations.Nullable;
//
//public class JungleSpiderEntity extends SpiderEntity {
//    public JungleSpiderEntity(EntityType<? extends JungleSpiderEntity> entityType, World world) {
//        super(entityType, world);
//    }
//
//    public static DefaultAttributeContainer.Builder createJungleSpiderAttributes() {
//        return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D);
//    }
//
//    public boolean tryAttack(Entity target) {
//        if (super.tryAttack(target)) {
//            if (target instanceof LivingEntity) {
//                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0), this);
//            }
//
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Nullable
//    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
//        return entityData;
//    }
//
//    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
//        return 0.45F;
//    }
//}
