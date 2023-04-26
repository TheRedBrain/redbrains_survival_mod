package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;

public abstract class AbstractAxleWithPowerSourceBlock extends BlockWithEntity {
    public static final EnumProperty<Direction.Axis> AXIS;
    public static final IntProperty GENERATED_POWER;

    protected AbstractAxleWithPowerSourceBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AXIS, GENERATED_POWER});
    }

    static {
        AXIS = Properties.AXIS;
        // 0 = no power generation, 1 = stable power generation from waterwheel or windmill, 2 = breaking power generation from windmill in bad weather
        GENERATED_POWER = IntProperty.of("generated_power", 0, 2);
    }
}
