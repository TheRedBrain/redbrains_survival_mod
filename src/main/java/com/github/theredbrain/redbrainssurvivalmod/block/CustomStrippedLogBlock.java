package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.item.CustomAxeItem;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CustomStrippedLogBlock extends PillarBlock {

    public static final IntProperty CHIPPED_STATE;
    public static final BooleanProperty CONNECT_TO_NEIGHBOUR_1;
    public static final BooleanProperty CONNECT_TO_NEIGHBOUR_2;
    public static final VoxelShape CHIPPED_TRUE_1_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_1_FALSE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 10.0D, 14.0D, 14.0D),
            Block.createCuboidShape(10.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_1_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 4.0D, 4.0D, 6.0D, 12.0D, 12.0D),
            Block.createCuboidShape(6.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_1_FALSE_X_AXIS_SHAPE = Block.createCuboidShape(0.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);

    public static final VoxelShape CHIPPED_TRUE_2_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 2.0D, 2.0D, 4.0D, 14.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(12.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
    public static final VoxelShape CHIPPED_TRUE_2_FALSE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 2.0D, 2.0D, 4.0D, 14.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 10.0D, 12.0D, 12.0D),
            Block.createCuboidShape(10.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_2_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D),
            Block.createCuboidShape(6.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(12.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
    public static final VoxelShape CHIPPED_FALSE_2_FALSE_X_AXIS_SHAPE = Block.createCuboidShape(0.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D);

    public static final VoxelShape CHIPPED_TRUE_3_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 4.0D, 4.0D, 6.0D, 12.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(10.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    public static final VoxelShape CHIPPED_TRUE_3_FALSE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 4.0D, 4.0D, 6.0D, 12.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 8.0D, 10.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_3_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(8.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(10.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_3_FALSE_X_AXIS_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);

    public static final VoxelShape CHIPPED_TRUE_4_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 4.0D, 4.0D, 4.0D, 12.0D, 12.0D),
            Block.createCuboidShape(4.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D),
            Block.createCuboidShape(12.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    public static final VoxelShape CHIPPED_TRUE_4_FALSE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 4.0D, 4.0D, 4.0D, 12.0D, 12.0D),
            Block.createCuboidShape(4.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_4_TRUE_X_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(10.0D, 6.0D, 6.0D, 12.0D, 10.0D, 10.0D),
            Block.createCuboidShape(12.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_4_FALSE_X_AXIS_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    
    
    public static final VoxelShape CHIPPED_TRUE_1_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_1_FALSE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 10.0D, 14.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_1_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(2.0D, 6.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_1_FALSE_Y_AXIS_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public static final VoxelShape CHIPPED_TRUE_2_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_TRUE_2_FALSE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_2_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.createCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_FALSE_2_FALSE_Y_AXIS_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public static final VoxelShape CHIPPED_TRUE_3_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_TRUE_3_FALSE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_3_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_3_FALSE_Y_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);

    public static final VoxelShape CHIPPED_TRUE_4_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.createCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_TRUE_4_FALSE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D));
    public static final VoxelShape CHIPPED_FALSE_4_TRUE_Y_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.createCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_4_FALSE_Y_AXIS_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);


    public static final VoxelShape CHIPPED_TRUE_1_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_1_FALSE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 10.0D),
            Block.createCuboidShape(4.0D, 4.0D, 10.0D, 12.0D, 12.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_1_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 6.0D),
            Block.createCuboidShape(2.0D, 2.0D, 6.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_1_FALSE_Z_AXIS_SHAPE = Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 16.0D);

    public static final VoxelShape CHIPPED_TRUE_2_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 4.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 2.0D, 12.0D, 14.0D, 14.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_2_FALSE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 4.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 10.0D),
            Block.createCuboidShape(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 12.0D));
    public static final VoxelShape CHIPPED_FALSE_2_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 6.0D),
            Block.createCuboidShape(4.0D, 4.0D, 6.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 2.0D, 12.0D, 14.0D, 14.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_2_FALSE_Z_AXIS_SHAPE = Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 16.0D);

    public static final VoxelShape CHIPPED_TRUE_3_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 6.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 4.0D, 10.0D, 12.0D, 12.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_3_FALSE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 6.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 8.0D));
    public static final VoxelShape CHIPPED_FALSE_3_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 6.0D, 8.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 4.0D, 10.0D, 12.0D, 12.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_3_FALSE_Z_AXIS_SHAPE = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);

    public static final VoxelShape CHIPPED_TRUE_4_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D),
            Block.createCuboidShape(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 12.0D),
            Block.createCuboidShape(4.0D, 4.0D, 12.0D, 12.0D, 12.0D, 16.0D));
    public static final VoxelShape CHIPPED_TRUE_4_FALSE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 4.0D),
            Block.createCuboidShape(6.0D, 6.0D, 4.0D, 10.0D, 10.0D, 6.0D));
    public static final VoxelShape CHIPPED_FALSE_4_TRUE_Z_AXIS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 12.0D),
            Block.createCuboidShape(4.0D, 4.0D, 12.0D, 12.0D, 12.0D, 16.0D));
    public static final VoxelShape CHIPPED_FALSE_4_FALSE_Z_AXIS_SHAPE = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);

    //    public static final IntProperty NEIGHBOUR_1_CHIPPED_STATE;
//    public static final IntProperty NEIGHBOUR_2_CHIPPED_STATE;
    /*public static final VoxelShape CHIPPED_01_1_01_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_01_1_25_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_25_1_01_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
            Block.createCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D));
    public static final VoxelShape CHIPPED_25_1_25_SHAPE_Y_AXIS = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public static final VoxelShape CHIPPED_02_2_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_02_2_34_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_34_2_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_35_2_35_SHAPE_Y_AXIS = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final VoxelShape CHIPPED_02_2_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.createCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D));
    public static final VoxelShape CHIPPED_5_2_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.createCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.createCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_34_2_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D));
    public static final VoxelShape CHIPPED_5_2_34_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.createCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D));

    public static final VoxelShape CHIPPED_02_3_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.createCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 14.0D, 12.0D),
            Block.createCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_02_3_34_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.createCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_34_3_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 14.0D, 12.0D),
            Block.createCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_34_3_34_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_02_3_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.createCuboidShape(4.0D, 2.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    public static final VoxelShape CHIPPED_5_3_02_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 14.0D, 12.0D),
            Block.createCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D));
    public static final VoxelShape CHIPPED_34_3_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.createCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    public static final VoxelShape CHIPPED_5_3_34_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D),
            Block.createCuboidShape(4.0D, 10.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_5_3_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.createCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D));

    public static final VoxelShape CHIPPED_03_4_03_SHAPE_Y_AXIS = CHIPPED_5_3_5_SHAPE_Y_AXIS;
    public static final VoxelShape CHIPPED_03_4_4_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 16.0D, 10.0D));
    public static final VoxelShape CHIPPED_4_4_03_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.createCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_4_4_4_SHAPE_Y_AXIS = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    public static final VoxelShape CHIPPED_03_4_5_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.createCuboidShape(6.0D, 4.0D, 6.0D, 10.0D, 6.0D, 10.0D));
    public static final VoxelShape CHIPPED_5_4_03_SHAPE_Y_AXIS = VoxelShapes.union(
            Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 12.0D, 10.0D),
            Block.createCuboidShape(4.0D, 12.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    public static final VoxelShape CHIPPED_4_4_5_SHAPE_Y_AXIS = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
    public static final VoxelShape CHIPPED_5_4_4_SHAPE_Y_AXIS = Block.createCuboidShape(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    public static final VoxelShape CHIPPED_5_4_5_SHAPE_Y_AXIS = CHIPPED_4_4_4_SHAPE_Y_AXIS;
*/
    public CustomStrippedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(CHIPPED_STATE, 0).with(CONNECT_TO_NEIGHBOUR_1, false).with(CONNECT_TO_NEIGHBOUR_2, false)/*.with(NEIGHBOUR_1_CHIPPED_STATE, 0).with(NEIGHBOUR_2_CHIPPED_STATE, 0)*/);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CHIPPED_STATE, CONNECT_TO_NEIGHBOUR_1, CONNECT_TO_NEIGHBOUR_2/*, NEIGHBOUR_1_CHIPPED_STATE, NEIGHBOUR_2_CHIPPED_STATE*/});
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!(tool.getItem() instanceof CustomAxeItem)) {
            if (state.get(CHIPPED_STATE) == 0) {
                world.setBlockState(pos, state.with(CONNECT_TO_NEIGHBOUR_1, state.get(CONNECT_TO_NEIGHBOUR_1)).with(CHIPPED_STATE, 1).with(CONNECT_TO_NEIGHBOUR_2, state.get(CONNECT_TO_NEIGHBOUR_2)));
            } else if (state.get(CHIPPED_STATE) == 1) {
                world.setBlockState(pos, state.with(CONNECT_TO_NEIGHBOUR_1, state.get(CONNECT_TO_NEIGHBOUR_1)).with(CHIPPED_STATE, 2).with(CONNECT_TO_NEIGHBOUR_2, state.get(CONNECT_TO_NEIGHBOUR_2)));
            } else if (state.get(CHIPPED_STATE) == 2) {
                world.setBlockState(pos, state.with(CONNECT_TO_NEIGHBOUR_1, state.get(CONNECT_TO_NEIGHBOUR_1)).with(CHIPPED_STATE, 3).with(CONNECT_TO_NEIGHBOUR_2, state.get(CONNECT_TO_NEIGHBOUR_2)));
            }/* else if (state.get(CHIPPED_STATE) == 3) {
                world.setBlockState(pos, state.with(CONNECT_TO_NEIGHBOUR_1, state.get(CONNECT_TO_NEIGHBOUR_1)).with(CHIPPED_STATE, 4).with(CONNECT_TO_NEIGHBOUR_2, state.get(CONNECT_TO_NEIGHBOUR_2)));
            }*/
        }
    }

    public static BlockState getChippedStates(BlockPos blockPos, BlockState blockState, World world) {
        Direction.Axis axis = blockState.get(AXIS);
        BlockPos neighbour_pos_1 = axis == Direction.Axis.X ? blockPos.west() : axis == Direction.Axis.Y ? blockPos.down() : blockPos.north();
        BlockPos neighbour_pos_2 = axis == Direction.Axis.X ? blockPos.east() : axis == Direction.Axis.Y ? blockPos.up() : blockPos.south();
        BlockState neighbour_state_1 = world.getBlockState(neighbour_pos_1);
        BlockState neighbour_state_2 = world.getBlockState(neighbour_pos_2);

        boolean connect_to_neighbour_1 = ((neighbour_state_1.getBlock() instanceof CustomStrippedLogBlock || neighbour_state_1.getBlock() instanceof CustomLogBlock) && neighbour_state_1.get(AXIS) == axis) || (neighbour_state_1.getBlock() instanceof TreeStumpBlock && axis.isVertical()); // ? neighbour_state_1.get(CHIPPED_STATE) : neighbour_state_1.isSideSolidFullSquare(world, neighbour_pos_1, axis == Direction.Axis.X ? Direction.EAST : axis == Direction.Axis.Y ? Direction.UP : Direction.SOUTH) ? 0 : 5;
        boolean connect_to_neighbour_2 = (neighbour_state_2.getBlock() instanceof CustomStrippedLogBlock || neighbour_state_2.getBlock() instanceof CustomLogBlock) && neighbour_state_2.get(AXIS) == axis; //) ? neighbour_state_2.get(CHIPPED_STATE) : neighbour_state_2.isSideSolidFullSquare(world, neighbour_pos_2, axis == Direction.Axis.X ? Direction.WEST : axis == Direction.Axis.Y ? Direction.DOWN : Direction.NORTH) ? 0 : 5;
        return blockState.with(AXIS, axis).with(CONNECT_TO_NEIGHBOUR_1, connect_to_neighbour_1).with(CONNECT_TO_NEIGHBOUR_2, connect_to_neighbour_2);
//        int neighbour_chipped_state_1 = (neighbour_state_1.getBlock() instanceof CustomStrippedLogBlock && neighbour_state_1.get(AXIS) == axis || neighbour_state_1.getBlock() instanceof TreeStumpBlock && axis.isVertical()) ? neighbour_state_1.get(CHIPPED_STATE) : neighbour_state_1.isSideSolidFullSquare(world, neighbour_pos_1, axis == Direction.Axis.X ? Direction.EAST : axis == Direction.Axis.Y ? Direction.UP : Direction.SOUTH) ? 0 : 5;
//        int neighbour_chipped_state_2 = (neighbour_state_2.getBlock() instanceof CustomStrippedLogBlock && neighbour_state_2.get(AXIS) == axis) ? neighbour_state_2.get(CHIPPED_STATE) : neighbour_state_2.isSideSolidFullSquare(world, neighbour_pos_2, axis == Direction.Axis.X ? Direction.WEST : axis == Direction.Axis.Y ? Direction.DOWN : Direction.NORTH) ? 0 : 5;
//        return blockState.with(AXIS, axis).with(NEIGHBOUR_1_CHIPPED_STATE, neighbour_chipped_state_1).with(NEIGHBOUR_2_CHIPPED_STATE, neighbour_chipped_state_2);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction.Axis axis = ctx.getSide().getAxis();
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState blockState = this.getDefaultState().with(AXIS, axis).with(CHIPPED_STATE, 0);
        return getChippedStates(blockPos, blockState, world);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(AXIS) == Direction.Axis.X) {
            if (state.get(CHIPPED_STATE) == 1) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_TRUE_X_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_FALSE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_TRUE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_FALSE_X_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 2) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_TRUE_X_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_FALSE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_TRUE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_FALSE_X_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 3) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_TRUE_X_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_FALSE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_TRUE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_FALSE_X_AXIS_SHAPE;
                }
            }/* else if (state.get(CHIPPED_STATE) == 4) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_TRUE_X_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_FALSE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_TRUE_X_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_FALSE_X_AXIS_SHAPE;
                }
            }*/
        } else if (state.get(AXIS) == Direction.Axis.Y) {
            if (state.get(CHIPPED_STATE) == 1) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_TRUE_Y_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_FALSE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_TRUE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_FALSE_Y_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 2) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_TRUE_Y_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_FALSE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_TRUE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_FALSE_Y_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 3) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_TRUE_Y_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_FALSE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_TRUE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_FALSE_Y_AXIS_SHAPE;
                }
            }/* else if (state.get(CHIPPED_STATE) == 4) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_TRUE_Y_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_FALSE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_TRUE_Y_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_FALSE_Y_AXIS_SHAPE;
                }
            }*/
        } else {
            if (state.get(CHIPPED_STATE) == 1) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_TRUE_Z_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_1_FALSE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_TRUE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_1_FALSE_Z_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 2) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_TRUE_Z_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_2_FALSE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_TRUE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_2_FALSE_Z_AXIS_SHAPE;
                }
            } else if (state.get(CHIPPED_STATE) == 3) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_TRUE_Z_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_3_FALSE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_TRUE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_3_FALSE_Z_AXIS_SHAPE;
                }
            }/* else if (state.get(CHIPPED_STATE) == 4) {
                if (state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_TRUE_Z_AXIS_SHAPE;
                } else if (state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_TRUE_4_FALSE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_TRUE_Z_AXIS_SHAPE;
                } else if (!state.get(CONNECT_TO_NEIGHBOUR_1) && !state.get(CONNECT_TO_NEIGHBOUR_2)) {
                    return CHIPPED_FALSE_4_FALSE_Z_AXIS_SHAPE;
                }
            }*/
        }
        return VoxelShapes.fullCube();
    }

//    @Override
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        if (state.get(AXIS) == Direction.Axis.X) {
//        } else if (state.get(AXIS) == Direction.Axis.Y) {
//            if (state.get(CHIPPED_STATE) == 1) {
//                if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1)) {
//                    return CHIPPED_01_1_01_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_01_1_25_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1)) {
//                    return CHIPPED_25_1_01_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_25_1_25_SHAPE_Y_AXIS;
//                }
//            } else if (state.get(CHIPPED_STATE) == 2) {
//                if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_02_2_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_02_2_34_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_34_2_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_35_2_35_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_02_2_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_5_2_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_34_2_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_5_2_34_SHAPE_Y_AXIS;
//                }
//            } else if (state.get(CHIPPED_STATE) == 3) {
//                if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_02_3_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_02_3_34_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_34_3_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_34_3_34_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_02_3_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2)) {
//                    return CHIPPED_5_3_02_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_34_3_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_5_3_34_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_5_3_5_SHAPE_Y_AXIS;
//                }
//            } else if (state.get(CHIPPED_STATE) == 4) {
//                if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3)) {
//                    return CHIPPED_03_4_03_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_03_4_4_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3)) {
//                    return CHIPPED_4_4_03_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_4_4_4_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_1_CHIPPED_STATE) == 3)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_03_4_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 0 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 1 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 2 || state.get(NEIGHBOUR_2_CHIPPED_STATE) == 3)) {
//                    return CHIPPED_5_4_03_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 4)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_4_4_5_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 4)) {
//                    return CHIPPED_5_4_4_SHAPE_Y_AXIS;
//                } else if ((state.get(NEIGHBOUR_1_CHIPPED_STATE) == 5)
//                        && (state.get(NEIGHBOUR_2_CHIPPED_STATE) == 5)) {
//                    return CHIPPED_5_4_5_SHAPE_Y_AXIS;
//                }
//            }
//        } else {
//
//        }
//        return VoxelShapes.fullCube();
//    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(CHIPPED_STATE) != 0;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

        Direction.Axis axis = state.get(AXIS);
        if ((axis == Direction.Axis.X && (direction == Direction.WEST || direction == Direction.EAST)) || (axis == Direction.Axis.Y && (direction == Direction.DOWN || direction == Direction.UP)) || (axis == Direction.Axis.Z && (direction == Direction.NORTH || direction == Direction.SOUTH))) {
            return getChippedStates(pos, state, (World) world);
        }
        return state;
    }

    static {
        CHIPPED_STATE = IntProperty.of("chipped_state", 0, 3);
        CONNECT_TO_NEIGHBOUR_1 = BooleanProperty.of("connect_to_neighbour_1");
        CONNECT_TO_NEIGHBOUR_2 = BooleanProperty.of("connect_to_neighbour_2");
//        NEIGHBOUR_1_CHIPPED_STATE = IntProperty.of("neighbour_1_chipped_state", 0, 5);
//        NEIGHBOUR_2_CHIPPED_STATE = IntProperty.of("neighbour_2_chipped_state", 0, 5);
    }
}
