package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.CustomFarmlandBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.event.GameEvent;

public class BeetrootsCropBlock extends CustomCropBlock {

    private static final IntProperty AGE;

    public BeetrootsCropBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{AGE});
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected ItemConvertible getSeedsItem() {
        return Items.BEETROOT_SEEDS;
    }

    // crop growth is affected by pollination from bees, fertilization of farmland and moisturized farmland
    // each of these factors doubles the chance of the crop growing
    // growth is paused, when the weed is present
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        if ((world.isSkyVisible(pos) || (state.isOf(Blocks.REDSTONE_LAMP) && state.get(Properties.LIT))) && blockState.isIn(Tags.FARM_LAND)) {
            int age = this.getAge(state);
            float moisture = getAvailableMoisture(world, pos);
            boolean fertilizedFarmland = (blockState.isIn(Tags.FERTILIZABLE_FARM_LAND) && blockState.get(CustomFarmlandBlock.FERTILIZED) || blockState.isIn(Tags.NON_FERTILIZABLE_FARM_LAND));
            // the random int makes somewhat up to the fact that beetroot got only 4 stages
            if (age < this.getMaxAge() && state.get(WEED_AGE) == 0 && random.nextInt(3) != 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 50.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 100.0F : 200.0F) / moisture) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(age + 1).with(HAS_CROP_GROWN, true).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
                }
            } else if (world.isNight() && state.get(HAS_CROP_GROWN)) {
                world.setBlockState(pos, state.with(HAS_CROP_GROWN, false), 2);
            }

            if (!blockState.isIn(Tags.NO_WEED_FARM_LAND) && world.isNight() && !state.get(HAS_WEED_GROWN)) {
                int j = state.get(WEED_AGE);
                if (j < 3) {
                    if (random.nextInt(100) == 0) {
                        world.setBlockState(pos, withAge(this.getAge(state)).with(HAS_WEED_GROWN, true).with(POLLINATED, state.get(POLLINATED)).with(WEED_AGE, state.get(WEED_AGE) + 1), 2);
                    }
                } else {
                    world.setBlockState(pos, BlocksRegistry.WEED.get().getDefaultState().with(WeedBlock.AGE, 4));
                }
            } else if (!world.isNight() && state.get(HAS_WEED_GROWN)) {
                world.setBlockState(pos, state.with(HAS_WEED_GROWN, false));
            }
        }

    }

    static {
        AGE = Properties.AGE_3;
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)
        };
    }
}
