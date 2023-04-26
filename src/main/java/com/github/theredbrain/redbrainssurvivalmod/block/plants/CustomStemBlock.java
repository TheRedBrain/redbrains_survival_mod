package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.CustomFarmlandBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Supplier;

public class CustomStemBlock extends CustomCropBlock {
    public static final IntProperty AGE = Properties.AGE_7;
    protected static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 2.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 4.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 6.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 8.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 10.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 12.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 14.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)};
    private final FallingGourdBlock gourdBlock;
    private final Supplier<Item> pickBlockItem;

    public CustomStemBlock(FallingGourdBlock gourdBlock, Supplier<Item> pickBlockItem, AbstractBlock.Settings settings) {
        super(settings);
        this.gourdBlock = gourdBlock;
        this.pickBlockItem = pickBlockItem;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0).with(HAS_CROP_GROWN, false).with(HAS_WEED_GROWN, false).with(POLLINATED, false).with(WEED_AGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(AGE)];
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        if ((world.isSkyVisible(pos) || (state.isOf(Blocks.REDSTONE_LAMP) && state.get(Properties.LIT))) && blockState.isIn(Tags.FARM_LAND)) {
            int age = this.getAge(state);
            float moisture = getAvailableMoisture(world, pos);
            boolean fertilizedFarmland = (blockState.isIn(Tags.FERTILIZABLE_FARM_LAND) && blockState.get(CustomFarmlandBlock.FERTILIZED) || blockState.isIn(Tags.NON_FERTILIZABLE_FARM_LAND));

            // crop growing
            if (age < this.getMaxAge() && state.get(WEED_AGE) == 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 50.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 100.0F : 200.0F) / moisture) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(age + 1).with(HAS_CROP_GROWN, true).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
                }
            } else if (world.isNight() && state.get(HAS_CROP_GROWN)) {
                world.setBlockState(pos, state.with(HAS_CROP_GROWN, false), 2);
            }

            // weed growing
            if (!blockState.isIn(Tags.NO_WEED_FARM_LAND) && world.isNight() && !state.get(HAS_WEED_GROWN)) {
                int j = state.get(WEED_AGE);
                if (j < 3) {
                    if (random.nextInt(25) == 0) {
                        world.setBlockState(pos, state.with(HAS_WEED_GROWN, true).with(WEED_AGE, state.get(WEED_AGE) + 1), 2);
                    }
                } else {
                    world.setBlockState(pos, BlocksRegistry.WEED.get().getDefaultState().with(WeedBlock.AGE, 4));
                }
            } else if (!world.isNight() && state.get(HAS_WEED_GROWN)) {
                world.setBlockState(pos, state.with(HAS_WEED_GROWN, false));
            }

            // gourd block growing
            if (age >= this.getMaxAge() && state.get(WEED_AGE) == 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                boolean bl = false;
                BlockPos.Mutable mutable = pos.mutableCopy();
                // TODO random direction
                for (Direction direction : Direction.values()) {
                    BlockState blockState1 = world.getBlockState(mutable);
                    if (direction == Direction.DOWN || direction == Direction.UP || !blockState1.isIn(Tags.REPLACED_BY_GOURDS) || !world.getBlockState(mutable.down()).isSideSolidFullSquare(world, mutable.down(), Direction.UP))
                        continue;
                    world.setBlockState(mutable, this.gourdBlock.getDefaultState());
                    world.setBlockState(pos, (BlockState) this.gourdBlock.getAttachedStem().getDefaultState().with(HorizontalFacingBlock.FACING, direction));
                    bl = true;
                    break;
                }
                if (!bl) {
                    world.setBlockState(pos, this.gourdBlock.getDefaultState());
                }
            }
        }
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return pickBlockItem.get();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockState blockState = world.getBlockState(pos.down());
        if (!((newState.isOf(this) || newState.isOf(this.getGourdBlock().getAttachedStem())) || newState.isOf(BlocksRegistry.WEED.get())) && blockState.isIn(Tags.FARM_LAND)) {
            if (blockState.isOf(BlocksRegistry.FARMLAND.get())) {
                world.setBlockState(pos.down(), BlocksRegistry.LOOSE_DIRT.get().getDefaultState());
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    public FallingGourdBlock getGourdBlock() {
        return this.gourdBlock;
    }
}

