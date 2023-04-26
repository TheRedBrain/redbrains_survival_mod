package com.github.theredbrain.redbrainssurvivalmod.block.plants;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.item.ItemConvertible;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.WorldView;
//
//public class RiceUpperCropBlock extends CustomUpperCropBlock {
//
//    public static final IntProperty AGE;
//
//    public RiceUpperCropBlock(Settings settings) {
//        super(settings);
//    }
//
//    public IntProperty getAgeProperty() {
//        return AGE;
//    }
//
//    public int getMaxAge() {
//        return 3;
//    }
//
//    @Override
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
//        builder.add(AGE);
//    }
//
//    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//        return super.canPlaceAt(state, world, pos) && world.isAir(pos);
//    }
//
//    public Block getLowerCrop() {
//        return BlocksRegistry.RICE_CROP;
//    }
//
//    protected ItemConvertible getSeedsItem() {
//        return BlocksRegistry.RICE_CROP;
//    }
//
//    static {
//        AGE = Properties.AGE_3;
//        AGE_TO_SHAPE = new VoxelShape[]{
//                Block.createCuboidShape(3.d, .0d, 3.d, 13.d, 8.d, 13.d),
//                Block.createCuboidShape(3.d, .0d, 3.d, 13.d, 10.d, 13.d),
//                Block.createCuboidShape(2.d, .0d, 2.d, 14.d, 12.d, 14.d),
//                Block.createCuboidShape(1.d, .0d, 1.d, 15.d, 16.d, 15.d)
//        };
//    }
//}