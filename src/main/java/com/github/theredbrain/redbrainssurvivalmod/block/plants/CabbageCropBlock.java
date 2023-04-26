package com.github.theredbrain.redbrainssurvivalmod.block.plants;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.item.ItemConvertible;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.state.property.Property;
//import net.minecraft.util.shape.VoxelShape;
//
//public class CabbageCropBlock extends CustomCropBlock {
//
//    private static final IntProperty AGE;
//
//    public CabbageCropBlock(Settings settings) {
//        super(settings);
//    }
//
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
//        builder.add(new Property[]{AGE});
//    }
//
//    public IntProperty getAgeProperty() {
//        return AGE;
//    }
//
//    public int getMaxAge() {
//        return 7;
//    }
//
//    protected ItemConvertible getSeedsItem() {
//        return BlocksRegistry.CABBAGE_CROP;
//    }
//
//    static {
//        AGE = Properties.AGE_7;
//        AGE_TO_SHAPE = new VoxelShape[] {
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 2.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 3.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 5.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 7.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 8.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 9.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 9.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 10.d, 16.d)
//        };
//    }
//}