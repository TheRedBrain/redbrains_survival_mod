package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.shape.VoxelShape;

public class AbstractChippedLogBlock extends PillarBlock {
    public static IntProperty CHIPPED_STATE;
    public static VoxelShape CHIPPED_STATE_1_SHAPE;
    public static VoxelShape CHIPPED_STATE_2_SHAPE;
    public static VoxelShape CHIPPED_STATE_3_SHAPE;
    public static VoxelShape CHIPPED_STATE_4_SHAPE;

    public AbstractChippedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(CHIPPED_STATE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CHIPPED_STATE});
    }

    static {
        CHIPPED_STATE = IntProperty.of("chipped_state", 0, 4);
        CHIPPED_STATE_1_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_2_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_3_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_4_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }
}
