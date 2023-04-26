package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;

public class WheatUpperCropBlock extends CustomUpperCropBlock {

    public static final IntProperty AGE;

    public WheatUpperCropBlock(Settings settings) {
        super(settings);
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos,
                                                BlockPos posFrom) {
        BlockState superState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, posFrom);
        if (!superState.isAir()) {
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(BlocksRegistry.WHEAT_CROP.get(), pos.down()));
        }
        if (direction == Direction.DOWN && neighborState.isOf(BlocksRegistry.WHEAT_CROP.get())) {
            return neighborState.get(((WheatCropBlock)BlocksRegistry.WHEAT_CROP.get()).getAgeProperty()) == 7 ? state.with(AGE, 0) : neighborState.get(((WheatCropBlock)BlocksRegistry.WHEAT_CROP.get()).getAgeProperty()) == 8 ? state.with(AGE, 1) : neighborState.get(((WheatCropBlock)BlocksRegistry.WHEAT_CROP.get()).getAgeProperty()) == 9 ? state.with(AGE, 2) : neighborState.get(((WheatCropBlock)BlocksRegistry.WHEAT_CROP.get()).getAgeProperty()) == 10 ? state.with(AGE, 3) : Blocks.AIR.getDefaultState();
        } else {
            return state;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    public Block getLowerCrop() {
        return BlocksRegistry.WHEAT_CROP.get();
    }

    protected ItemConvertible getSeedsItem() {
        return BlocksRegistry.WHEAT_CROP.get();
    }

    static {
        AGE = Properties.AGE_3;
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
                Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
        };
    }
}
