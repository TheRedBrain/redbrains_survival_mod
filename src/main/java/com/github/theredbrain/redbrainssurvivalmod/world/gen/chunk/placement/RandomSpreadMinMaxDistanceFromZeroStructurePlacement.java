package com.github.theredbrain.redbrainssurvivalmod.world.gen.chunk.placement;

import com.github.theredbrain.redbrainssurvivalmod.registry.StructurePlacementTypeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;

import java.util.Optional;

public class RandomSpreadMinMaxDistanceFromZeroStructurePlacement extends StructurePlacement {
    public static final Codec<RandomSpreadMinMaxDistanceFromZeroStructurePlacement> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.INT.fieldOf("spacing").forGetter(
                RandomSpreadMinMaxDistanceFromZeroStructurePlacement::getSpacing
        ), Codec.INT.fieldOf("separation").forGetter(
                RandomSpreadMinMaxDistanceFromZeroStructurePlacement::getSeparation
        ), SpreadType.CODEC.optionalFieldOf("spreadType", SpreadType.LINEAR).forGetter(
                RandomSpreadMinMaxDistanceFromZeroStructurePlacement::getSpreadType
        ), Codec.INT.fieldOf("minDistance").forGetter(
                RandomSpreadMinMaxDistanceFromZeroStructurePlacement::getMinDistance
        ), Codec.INT.fieldOf("maxDistance").forGetter(
                RandomSpreadMinMaxDistanceFromZeroStructurePlacement::getMaxDistance
        ), Vec3i.createOffsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(
                StructurePlacement::getLocateOffset
        ), FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(
                StructurePlacement::getFrequencyReductionMethod
        ), Codec.floatRange(0.0f, 1.0f).optionalFieldOf("frequency", 1.0f).forGetter(
                StructurePlacement::getFrequency
        ), (Codecs.NONNEGATIVE_INT.fieldOf("salt")).forGetter(
                StructurePlacement::getSalt
        ), ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(
                StructurePlacement::getExclusionZone
        )).apply(instance, RandomSpreadMinMaxDistanceFromZeroStructurePlacement::new);
    });

    private final int spacing;
    private final int separation;
    private final SpreadType spreadType;
    private final int minDistance;
    private final int maxDistance;

    public RandomSpreadMinMaxDistanceFromZeroStructurePlacement(int spacing, int separation, SpreadType spreadType, int minDistance, int maxDistance, Vec3i locateOffset, StructurePlacement.FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
        this.spacing = spacing;
        this.separation = separation;
        this.spreadType = spreadType;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public int getSpacing() {
        return this.spacing;
    }

    public int getSeparation() {
        return this.separation;
    }

    public SpreadType getSpreadType() {
        return this.spreadType;
    }

    public int getMinDistance() {
        return this.minDistance;
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }

    public ChunkPos getStartChunk(long seed, int chunkX, int chunkZ) {
        int i = Math.floorDiv(chunkX, this.spacing);
        int j = Math.floorDiv(chunkZ, this.spacing);
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(0L));
        chunkRandom.setRegionSeed(seed, i, j, this.getSalt());
        int k = this.spacing - this.separation;
        int l = this.spreadType.get(chunkRandom, k);
        int m = this.spreadType.get(chunkRandom, k);
        return new ChunkPos(i * this.spacing + l, j * this.spacing + m);
    }

    @Override
    protected boolean isStartChunk(StructurePlacementCalculator calculator, int chunkX, int chunkZ) {
        ChunkPos chunkPos = this.getStartChunk(calculator.getStructureSeed(), chunkX, chunkZ);
        return chunkPos.x == chunkX && chunkPos.z == chunkZ && chunkPos.getStartPos().getSquaredDistance(Vec3d.ZERO) >= MathHelper.square(this.minDistance) && chunkPos.getStartPos().getSquaredDistance(Vec3d.ZERO) <= MathHelper.square(this.maxDistance);
    }

    @Override
    public StructurePlacementType<?> getType() {
        return StructurePlacementTypeRegistry.RANDOM_SPREAD_MIN_MAX_DISTANCE_FROM_ZERO;
    }
}
