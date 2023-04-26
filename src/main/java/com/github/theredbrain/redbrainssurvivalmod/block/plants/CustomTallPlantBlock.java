package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.AffectsVelocityOnCollision;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;

public class CustomTallPlantBlock extends TallPlantBlock implements AffectsVelocityOnCollision {
    public static final BooleanProperty SNOWY;

    public CustomTallPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(SNOWY, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{SNOWY});
    }

    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW) {
            world.setBlockState(pos, this.getDefaultState().with(SNOWY, true));
            world.emitGameEvent((Entity)null, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public float getVelocityMultiplierOnCollision() {
        return Constants.VELOCITY_MULTIPLYER_0_8;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(SNOWY) && world.getLightLevel(LightType.BLOCK, pos) > 11) {
            world.setBlockState(pos, this.getDefaultState());
            world.emitGameEvent((Entity)null, GameEvent.BLOCK_CHANGE, pos);
        } else if (!state.get(SNOWY) && world.getLightLevel(LightType.BLOCK, pos) < 12) {
            if (world.getBiome(pos).isIn(Tags.SNOW_PRECIPITATION) && world.isRaining() && world.isSkyVisible(pos)) {
                world.setBlockState(pos, this.getDefaultState().with(SNOWY, true));
                world.emitGameEvent((Entity)null, GameEvent.BLOCK_CHANGE, pos);
            }
        }
    }

    static {
        SNOWY = BooleanProperty.of("snowy");
    }
}
