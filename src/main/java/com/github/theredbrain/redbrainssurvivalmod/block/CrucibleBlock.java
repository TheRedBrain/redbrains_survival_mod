package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.BrickOvenEntity;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.CrucibleEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HEATED = BooleanProperty.of("heated");
    public static final VoxelShape SHAPE_COMPLETE;
    public static final VoxelShape SHAPE_POSITIVE;
    public static final VoxelShape SHAPE_NEGATIVE;

    public CrucibleBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, HEATED});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.CRUCIBLE_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(HEATED) ? checkType(type, EntitiesRegistry.CRUCIBLE_ENTITY, CrucibleEntity::clientTick) : null;
        } else {
            return state.get(HEATED) ? checkType(type, EntitiesRegistry.CRUCIBLE_ENTITY, CrucibleEntity::serverTick) : null;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_COMPLETE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    static {
        SHAPE_POSITIVE = VoxelShapes.union(Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), Block.createCuboidShape(0.0, 2.0, 0.0, 16.0, 14.0, 16.0));
        SHAPE_NEGATIVE = Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 16.0, 13.0);
        SHAPE_COMPLETE = VoxelShapes.combineAndSimplify(SHAPE_POSITIVE, SHAPE_NEGATIVE, BooleanBiFunction.ONLY_FIRST);
    }
}
