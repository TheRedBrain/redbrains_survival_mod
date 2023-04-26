package com.github.theredbrain.redbrainssurvivalmod.block.plants;
//
//import com.github.theredbrain.redbrainssurvivalmod.farmersdelightfork.util.BlockStateUtils;
//import net.minecraft.block.*;
//import net.minecraft.item.ItemPlacementContext;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//
//import java.util.Set;
//
//public class WildPatchBlock extends PlantBlock implements Fertilizable {
//
//    public static final Set<Block> PLANT_ON_LIST = Set.of(Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND);
//
//    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.d, .0d, 2.d, 14.d, 13.d, 14.d);
//
//    public WildPatchBlock(Settings settings) {
//        super(settings);
//    }
//
//    @Override
//    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
//        return true;
//    }
//
//    @Override
//    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
//        return (double) random.nextFloat() < .8f;
//    }
//
//    @Override
//    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
//        int wildCropLimit = 10;
//
//        for (BlockPos blockpos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
//            if (world.getBlockState(blockpos).isOf(this)) {
//                --wildCropLimit;
//                if (wildCropLimit <= 0) {
//                    return;
//                }
//            }
//        }
//
//        BlockPos neighborsBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
//
//        for (int k = 0; k < 4; ++k) {
//            if (world.isAir(neighborsBlockPos) && state.canPlaceAt(world, neighborsBlockPos)) {
//                pos = neighborsBlockPos;
//            }
//
//            neighborsBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
//        }
//
//        if (world.isAir(neighborsBlockPos) && state.canPlaceAt(world, neighborsBlockPos)) {
//            world.setBlockState(neighborsBlockPos, state, BlockStateUtils.BLOCK_UPDATE);
//        }
//    }
//
//    @Override
//    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
//        return PLANT_ON_LIST.contains(floor.getBlock());
//    }
//
//    @Override
//    public boolean canReplace(BlockState state, ItemPlacementContext context) {
//        return false;
//    }
//
//    @Override
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return SHAPE;
//    }
//
//}