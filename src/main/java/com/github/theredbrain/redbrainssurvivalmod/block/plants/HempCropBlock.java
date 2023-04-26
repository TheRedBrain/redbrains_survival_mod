package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.CustomFarmlandBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.OrderedTick;

public class HempCropBlock extends CustomCropBlock {

    public static final IntProperty AGE;

    public HempCropBlock(Settings settings) {
        super(settings);
//        setDefaultState(getStateManager().getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{AGE});
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 8;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ItemsRegistry.WHEAT_SEEDS.get();
    }

    public void scheduledTick(ServerWorld world, BlockPos pos, Random random) {
        BlockState stateAbove = world.getBlockState(pos.up());
        if (stateAbove.isOf(Blocks.AIR)) {
            world.breakBlock(pos, true);
        }
    }

//    @Override
//    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
//        BlockState superState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
//        if (!superState.isAir()) {
//            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.WHEAT_UPPER_CROP.get(), pos.up()));
//        }
//        if (direction == Direction.UP && neighborState.isOf(Blocks.AIR) && state.get(HempCropBlock.AGE) >= 8 && !(state.get(HempCropBlock.AGE) == 11)) {
//            return state.with(HempCropBlock.AGE, 11);
//        } else {
//            return state;
//        }
//    }

    // crop growth is affected by pollination from bees, fertilization of farmland and moisturized farmland
    // each of these factors doubles the chance of the crop growing
    // growth is paused, when the weed is present
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState1 = world.getBlockState(pos.up(2));
        if ((world.isSkyVisible(pos) || (blockState1.isOf(Blocks.REDSTONE_LAMP) && blockState1.get(Properties.LIT))) && blockState.isIn(Tags.FARM_LAND)) {
            int age = this.getAge(state);
            float moisture = getAvailableMoisture(world, pos);
            boolean fertilizedFarmland = blockState.isIn(Tags.FERTILIZABLE_FARM_LAND) && blockState.get(CustomFarmlandBlock.FERTILIZED);

            // crop growing
            if (age < 8 && state.get(WEED_AGE) == 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 25.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 50.0F : 100.0F) / moisture) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(age + 1).with(HAS_CROP_GROWN, true).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
                }
            } else if (age == 8 && state.get(WEED_AGE) == 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 25.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 50.0F : 100.0F) / moisture) + 1) == 0) {
                    // grow crop
                    world.setBlockState(pos, this.withAge(age + 1).with(HAS_CROP_GROWN, true).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
                    // plant upper crop
                    world.setBlockState(pos.up(), BlocksRegistry.HEMP_UPPER_CROP.get().getDefaultState(), 2);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
                }
            } else  if (world.isNight() && state.get(HAS_CROP_GROWN)) {
                world.setBlockState(pos, state.with(HAS_CROP_GROWN, false), 2);
            }

            // weed growing
            if (!blockState.isIn(Tags.NO_WEED_FARM_LAND) && world.isNight() && !state.get(HAS_WEED_GROWN)) {
                int j = state.get(WEED_AGE);
                if (j < 3) {
                    if (random.nextInt(3) == 0) {
                        world.setBlockState(pos, state.with(HAS_WEED_GROWN, true).with(WEED_AGE, state.get(WEED_AGE) + 1), 2);
                    } else {
                        world.setBlockState(pos, state.with(HAS_WEED_GROWN, true), 2);
                    }
                } else {
                    if (random.nextInt(3) == 0) {
                        world.setBlockState(pos, BlocksRegistry.WEED.get().getDefaultState().with(WeedBlock.AGE, 4));
                    } else {
                        world.setBlockState(pos, state.with(HAS_WEED_GROWN, true), 2);
                    }
                }
            } else if (!world.isNight() && state.get(HAS_WEED_GROWN)) {
                world.setBlockState(pos, state.with(HAS_WEED_GROWN, false));
            }
        }

    }

    static {
        AGE = IntProperty.of("age", 0, 9);
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
        };
    }
}
