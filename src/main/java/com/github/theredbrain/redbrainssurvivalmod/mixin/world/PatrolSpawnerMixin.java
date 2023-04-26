package com.github.theredbrain.redbrainssurvivalmod.mixin.world;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {

    @Shadow
    private int cooldown;

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    public void spawnCustomPatrols(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
        if (!spawnMonsters) {
            cir.setReturnValue(0);
        } else if (!world.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
            cir.setReturnValue(0);
        } else {
            Random random = world.random;
            --this.cooldown;
            if (this.cooldown > 0) {
                cir.setReturnValue(0);
            } else {
                this.cooldown += 12000 + random.nextInt(1200);
                long l = world.getTimeOfDay() / 24000L;
                if (l >= 5L && world.isDay()) {
                    if (random.nextInt(5) != 0) {
                        cir.setReturnValue(0);
                    } else {
                        int i = world.getPlayers().size();
                        if (i < 1) {
                            cir.setReturnValue(0);
                        } else {
                            PlayerEntity playerEntity = (PlayerEntity)world.getPlayers().get(random.nextInt(i));
                            if (playerEntity.isSpectator()) {
                                cir.setReturnValue(0);
                            } else if (world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), 2)) {
                                cir.setReturnValue(0);
                            } else if (!(((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader().get(Identifier.tryParse("redbrainssurvivalmod:adventure/so_it_begins"))).isDone())) {
                                cir.setReturnValue(0);
                            } else {
                                int j = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                BlockPos.Mutable mutable = playerEntity.getBlockPos().mutableCopy().move(j, 0, k);
//                                int m = true;
                                if (!world.isRegionLoaded(mutable.getX() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getZ() + 10)) {
                                    cir.setReturnValue(0);
                                } else {
                                    RegistryEntry<Biome> registryEntry = world.getBiome(mutable);
                                    if (registryEntry.isIn(BiomeTags.WITHOUT_PATROL_SPAWNS)) {
                                        cir.setReturnValue(0);
                                    } else {
                                        int n = 0;
                                        int o = 2;
                                        if (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                                                .get(Identifier.tryParse("minecraft:adventure/voluntary_exile"))).isDone()) {

                                            o = o + (int)Math.ceil((double)world.getLocalDifficulty(mutable).getLocalDifficulty()) + 1;
                                            if (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                                                    .get(Identifier.tryParse("redbrainssurvivalmod:adventure/loot_mansion"))).isDone()) {
                                                o = o + 3;
                                            }
                                            if (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                                                    .get(Identifier.tryParse("minecraft:adventure/hero_of_the_village"))).isDone()) {
                                                o = o + 3;
                                            }
                                        }
                                        for(int p = 0; p < o; ++p) {
                                            ++n;
                                            mutable.setY(world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
                                            if (p == 0) {
                                                if (!this.spawnPatrolMember(world, mutable, random, playerEntity, true)) {
                                                    break;
                                                }
                                            } else {
                                                this.spawnPatrolMember(world, mutable, random, playerEntity, false);
                                            }

                                            mutable.setX(mutable.getX() + random.nextInt(5) - random.nextInt(5));
                                            mutable.setZ(mutable.getZ() + random.nextInt(5) - random.nextInt(5));
                                        }

                                        cir.setReturnValue(n);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    cir.setReturnValue(0);
                }
            }
        }
        cir.cancel();
    }

    private boolean spawnPatrolMember(ServerWorld world, BlockPos pos, Random random, PlayerEntity playerEntity, boolean captain) {
        BlockState blockState = world.getBlockState(pos);
        PatrolEntity patrolEntity = null;
        RavagerEntity ravagerEntity = null;
        int i = random.nextInt(10);

        // evoker
        if ((captain  && (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                .get(Identifier.tryParse("redbrainssurvivalmod:adventure/loot_mansion"))).isDone()))
            || ((i < 1) && (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                .get(Identifier.tryParse("minecraft:adventure/hero_of_the_village"))).isDone()))) {

            if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.EVOKER)
                    && !PatrolEntity.canSpawn(EntityType.EVOKER, world, SpawnReason.PATROL, pos, random)) {

                return false;
            } else {

                patrolEntity = (PatrolEntity) EntityType.EVOKER.create(world);
            }
        }
        // vindicator
        else  if ((captain  && (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                .get(Identifier.tryParse("redbrainssurvivalmod:adventure/loot_outpost"))).isDone()))
                || ((i < 4) && (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                .get(Identifier.tryParse("redbrainssurvivalmod:adventure/kill_vindicator"))).isDone()))) {

            if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.VINDICATOR)
                    && !PatrolEntity.canSpawn(EntityType.VINDICATOR, world, SpawnReason.PATROL, pos, random)) {

                return false;
            } else {

                patrolEntity = (PatrolEntity) EntityType.VINDICATOR.create(world);
            }
        }
        // normal pillager
        else {
            if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.PILLAGER)
                    && !PatrolEntity.canSpawn(EntityType.PILLAGER, world, SpawnReason.PATROL, pos, random)) {

                return false;
            } else {

                patrolEntity = (PatrolEntity) EntityType.PILLAGER.create(world);
            }
        }

        // ravager (only for captain & after killing a ravager)
        if (captain && (((ServerPlayerEntity) playerEntity).getAdvancementTracker().getProgress(world.getServer().getAdvancementLoader()
                .get(Identifier.tryParse("redbrainssurvivalmod:adventure/kill_ravager"))).isDone())) {

            if (!SpawnHelper.isClearForSpawn(world, pos, blockState, blockState.getFluidState(), EntityType.RAVAGER)
                    && !PatrolEntity.canSpawn(EntityType.RAVAGER, world, SpawnReason.PATROL, pos, random)) {

                return false;
            } else {

                ravagerEntity = (RavagerEntity) EntityType.RAVAGER.create(world);
            }
        }

        if (patrolEntity != null) {
            if (captain) {
                if (ravagerEntity != null) {
                    ravagerEntity.setPosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
                    ravagerEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, (EntityData)null, (NbtCompound)null);
                    world.spawnEntityAndPassengers(ravagerEntity);
                }
                patrolEntity.setPatrolLeader(true);
                patrolEntity.setRandomPatrolTarget();
            }
            patrolEntity.setPosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            patrolEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, (EntityData)null, (NbtCompound)null);
            world.spawnEntityAndPassengers(patrolEntity);

            if (ravagerEntity != null) {
                patrolEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                patrolEntity.startRiding(ravagerEntity);
            }
            return true;
        } else {
            return false;
        }
    }
}
