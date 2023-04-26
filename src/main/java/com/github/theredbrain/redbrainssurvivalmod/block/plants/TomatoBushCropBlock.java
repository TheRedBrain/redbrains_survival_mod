package com.github.theredbrain.redbrainssurvivalmod.block.plants;
//
//import com.github.theredbrain.redbrainssurvivalmod.block.CustomFarmlandBlock;
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
//import com.github.theredbrain.redbrainssurvivalmod.registry.SoundsRegistry;
//import com.github.theredbrain.redbrainssurvivalmod.tags.Tags;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemConvertible;
//import net.minecraft.item.ItemStack;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.state.property.Property;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.World;
//
//public class TomatoBushCropBlock extends CustomCropBlock {
//
//    private static final IntProperty AGE;
//    private static IntProperty TOMATOES;
//
//    //TODO overhaul tomato crop
//    // when being rained on, has a chance to drop rotten tomatoes
//    // let it grow up to 3 blocks high
//    // with height > 2 blocks needs support from sticks or something
//
//    public TomatoBushCropBlock(Settings settings) {
//        super(settings);
//    }
//
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
//        builder.add(new Property[]{AGE, TOMATOES});
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
//        return BlocksRegistry.TOMATO_CROP;
//    }
//
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//
//        if (!world.isClient() && state.get(WEED_AGE) > 0) {
//
//            world.setBlockState(pos, this.withAge(this.getAge(state)).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, state.get(POLLINATED)).with(TOMATOES, state.get(TOMATOES)).with(WEED_AGE, 0), 2);
//            return ActionResult.SUCCESS;
//        } else if (!world.isClient() && state.get(TOMATOES) > 0 && state.get(WEED_AGE) == 0) {
//
//            dropStack(world, pos, new ItemStack(ItemsRegistry.TOMATO, 1));
//            world.playSound(null, pos, SoundsRegistry.ITEM_TOMATO_PICK_FROM_BUSH.get(), SoundCategory.BLOCKS, 1.f, .8f + world.getRandom().nextFloat() * .4f);
//            world.setBlockState(pos, this.withAge(this.getAge(state)).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, state.get(POLLINATED)).with(TOMATOES, state.get(TOMATOES) - 1).with(WEED_AGE, state.get(WEED_AGE)), 2);
//
//            return ActionResult.SUCCESS;
//
//        }
//
//        return ActionResult.PASS;
//    }
//
//    // crop growth is affected by pollination from bees, fertilization of farmland and moisturized farmland
//    // each of these factors doubles the chance of the crop growing
//    // growth is paused, when the weed is present
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        BlockState blockState = world.getBlockState(pos.down());
//        if (world.isSkyVisible(pos) && world.getBaseLightLevel(pos, 0) >= 9 && blockState.isIn(Tags.FARM_LAND)) {
//            int i = this.getAge(state);
//            float moisture = getAvailableMoisture(world, pos);
//            boolean fertilizedFarmland = (blockState.isIn(Tags.FERTILIZABLE_FARM_LAND) && blockState.get(CustomFarmlandBlock.FERTILIZED) || blockState.isIn(Tags.NON_FERTILIZABLE_FARM_LAND));
//            if (i < this.getMaxAge() && state.get(WEED_AGE) == 0) {
//                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 50.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 100.0F : 200.0F) / moisture) + 1) == 0) {
//                    world.setBlockState(pos, this.withAge(i + 1).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
//                }
//            } else if (i == this.getMaxAge() && state.get(WEED_AGE) == 0 && state.get(TOMATOES) <= 6) {
//                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 50.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 100.0F : 200.0F) / moisture) + 1) == 0) {
//                    world.setBlockState(pos, this.withAge(i).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(TOMATOES, state.get(TOMATOES) + 1).with(WEED_AGE, state.get(WEED_AGE)), 2);
//                }
//            }
//
//            if (!blockState.isIn(Tags.NO_WEED_FARM_LAND) && world.isNight() && !state.get(HAS_WEED_GROWN)) {
//                int j = state.get(WEED_AGE);
//                if (j <= 3) {
//                    if (random.nextInt(100) == 0) {
//                        world.setBlockState(pos, state.with(AGE, state.get(AGE)).with(HAS_WEED_GROWN, true).with(POLLINATED, state.get(POLLINATED)).with(WEED_AGE, state.get(WEED_AGE) + 1), 2);
//                    }
//                } else {
//                    world.setBlockState(pos, BlocksRegistry.WEED_BLOCK.getDefaultState().with(AGE, 4), 2);
//                }
//            } else if (!world.isNight() && state.get(HAS_WEED_GROWN)) {
//                world.setBlockState(pos, state.with(HAS_WEED_GROWN, false));
//            }
//        }
//
//    }
//
//    static {
//        AGE = Properties.AGE_7;
//        TOMATOES = IntProperty.of("tomatoes", 0, 3);
//        AGE_TO_SHAPE = new VoxelShape[]{
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 6.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 6.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 10.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 10.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 13.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 13.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 16.d, 16.d),
//                Block.createCuboidShape(.0d, .0d, .0d, 16.d, 16.d, 16.d)
//        };
//
//    }
//}