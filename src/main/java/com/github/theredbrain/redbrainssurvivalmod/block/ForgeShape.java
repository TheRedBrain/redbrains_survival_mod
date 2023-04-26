package com.github.theredbrain.redbrainssurvivalmod.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public interface ForgeShape {
    VoxelShape FOOT_EAST_WEST = Block.createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 2.0D, 12.0D);
    VoxelShape FOOT_NORTH_SOUTH = Block.createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 2.0D, 16.0D);
    VoxelShape LEG = Block.createCuboidShape(6.0D, 2.0D, 6.0D, 10.0D, 8.0D, 10.0D);
    VoxelShape LOWER_BODY_NORTH = Block.createCuboidShape(6.0D, 8.0D, 4.0D, 10.0D, 11.0D, 10.0D);
    VoxelShape LOWER_BODY_EAST = Block.createCuboidShape(6.0D, 8.0D, 6.0D, 12.0D, 11.0D, 10.0D);
    VoxelShape LOWER_BODY_SOUTH = Block.createCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 11.0D, 12.0D);
    VoxelShape LOWER_BODY_WEST = Block.createCuboidShape(4.0D, 8.0D, 6.0D, 10.0D, 11.0D, 10.0D);
    VoxelShape MIDDLE_BODY_NORTH = Block.createCuboidShape(6.0D, 11.0D, 3.0D, 10.0D, 13.0D, 11.0D);
    VoxelShape MIDDLE_BODY_EAST = Block.createCuboidShape(5.0D, 11.0D, 6.0D, 13.0D, 13.0D, 10.0D);
    VoxelShape MIDDLE_BODY_SOUTH = Block.createCuboidShape(6.0D, 11.0D, 5.0D, 10.0D, 13.0D, 13.0D);
    VoxelShape MIDDLE_BODY_WEST = Block.createCuboidShape(3.0D, 11.0D, 6.0D, 11.0D, 13.0D, 10.0D);

    VoxelShape UPPER_BODY_NORTH = Block.createCuboidShape(6.0D, 13.0D, 2.0D, 10.0D, 16.0D, 11.0D);
    VoxelShape UPPER_BODY_EAST = Block.createCuboidShape(5.0D, 13.0D, 6.0D, 14.0D, 16.0D, 10.0D);
    VoxelShape UPPER_BODY_SOUTH = Block.createCuboidShape(6.0D, 13.0D, 5.0D, 10.0D, 16.0D, 14.0D);
    VoxelShape UPPER_BODY_WEST = Block.createCuboidShape(2.0D, 13.0D, 6.0D, 11.0D, 16.0D, 10.0D);

    VoxelShape PLATE_NORTH = Block.createCuboidShape(5.0D, 15.0D, 4.0D, 11.0D, 16.0D, 16.0D);
    VoxelShape PLATE_EAST = Block.createCuboidShape(0.0D, 15.0D, 5.0D, 12.0D, 16.0D, 11.0D);
    VoxelShape PLATE_SOUTH = Block.createCuboidShape(5.0D, 15.0D, 0.0D, 11.0D, 16.0D, 12.0D);
    VoxelShape PLATE_WEST = Block.createCuboidShape(4.0D, 15.0D, 5.0D, 16.0D, 16.0D, 11.0D);
    VoxelShape NOSE_NORTH = Block.createCuboidShape(7.0D, 14.0D, 0.0D, 9.0D, 16.0D, 2.0D);
    VoxelShape NOSE_EAST = Block.createCuboidShape(14.0D, 14.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    VoxelShape NOSE_SOUTH = Block.createCuboidShape(7.0D, 14.0D, 14.0D, 9.0D, 16.0D, 16.0D);
    VoxelShape NOSE_WEST = Block.createCuboidShape(0.0D, 14.0D, 7.0D, 2.0D, 16.0D, 9.0D);
    VoxelShape COMPLETE_SHAPE_NORTH = VoxelShapes.union(FOOT_NORTH_SOUTH, LEG, LOWER_BODY_NORTH, MIDDLE_BODY_NORTH, UPPER_BODY_NORTH, PLATE_NORTH, NOSE_NORTH);
    VoxelShape COMPLETE_SHAPE_EAST = VoxelShapes.union(FOOT_EAST_WEST, LEG, LOWER_BODY_EAST, MIDDLE_BODY_EAST, UPPER_BODY_EAST, PLATE_EAST, NOSE_EAST);
    VoxelShape COMPLETE_SHAPE_SOUTH = VoxelShapes.union(FOOT_NORTH_SOUTH, LEG, LOWER_BODY_SOUTH, MIDDLE_BODY_SOUTH, UPPER_BODY_SOUTH, PLATE_SOUTH, NOSE_SOUTH);
    VoxelShape COMPLETE_SHAPE_WEST = VoxelShapes.union(FOOT_EAST_WEST, LEG, LOWER_BODY_WEST, MIDDLE_BODY_WEST, UPPER_BODY_WEST, PLATE_WEST, NOSE_WEST);
}
