package com.github.theredbrain.redbrainssurvivalmod.mixin.world;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.WorldView;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(WanderingTraderManager.class)
public class WanderingTraderManagerMixin {

    @Shadow
    @Final
    private Random random;

    @Shadow
    @Final
    private ServerWorldProperties properties;

    @Shadow
    private boolean doesNotSuffocateAt(BlockView world, BlockPos pos) {
        throw new AssertionError();
    }

    @Shadow
    private void spawnLlama(ServerWorld world, WanderingTraderEntity wanderingTrader, int range) {
        throw new AssertionError();
    }

    @Shadow
    private BlockPos getNearbySpawnPos(WorldView world, BlockPos pos, int range) {
        throw new AssertionError();
    }

    @Inject(method = "trySpawn", at = @At("HEAD"), cancellable = true)
    private void trySpawnOnlyNearBell(ServerWorld world, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity playerEntity = world.getRandomAlivePlayer();
        if (playerEntity == null) {
            cir.setReturnValue(true);
        } else if (this.random.nextInt(10) != 0) {
            cir.setReturnValue(false);
        } else {
            BlockPos blockPos = playerEntity.getBlockPos();
//            int i = true;
            PointOfInterestStorage pointOfInterestStorage = world.getPointOfInterestStorage();
            Optional<BlockPos> optional = pointOfInterestStorage.getPosition((registryEntry) -> {
                return registryEntry.matchesKey(PointOfInterestTypes.MEETING);
            }, (pos) -> {
                return true;
            }, blockPos, 48, PointOfInterestStorage.OccupationStatus.ANY);
            BlockPos blockPos2 = (BlockPos) optional.orElse(null);
            if (blockPos2 != null) {
                BlockPos blockPos3 = this.getNearbySpawnPos(world, blockPos2, 48);
                if (blockPos3 != null && this.doesNotSuffocateAt(world, blockPos3)) {
                    if (world.getBiome(blockPos3).isIn(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
                        cir.setReturnValue(false);
                    }

                    WanderingTraderEntity wanderingTraderEntity = (WanderingTraderEntity) EntityType.WANDERING_TRADER.spawn(world, blockPos3, SpawnReason.EVENT);
                    if (wanderingTraderEntity != null) {
                        for (int j = 0; j < 2; ++j) {
                            this.spawnLlama(world, wanderingTraderEntity, 4);
                        }

                        this.properties.setWanderingTraderId(wanderingTraderEntity.getUuid());
                        wanderingTraderEntity.setDespawnDelay(48000);
                        wanderingTraderEntity.setWanderTarget(blockPos2);
                        wanderingTraderEntity.setPositionTarget(blockPos2, 16);
                        cir.setReturnValue(true);
                    }
                }

            }
            cir.setReturnValue(false);
        }
    }
}
