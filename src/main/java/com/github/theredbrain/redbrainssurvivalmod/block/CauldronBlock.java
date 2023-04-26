package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.CauldronEntity;
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

public class CauldronBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty TEMPERATURE = IntProperty.of("temperature", 0, 2);
    public static final VoxelShape SHAPE_COMPLETE;
    public static final VoxelShape SHAPE_POSITIVE;
    public static final VoxelShape SHAPE_NEGATIVE;

    public CauldronBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, TEMPERATURE});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.CAULDRON_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(TEMPERATURE) == 2 ? checkType(type, EntitiesRegistry.CAULDRON_ENTITY, CauldronEntity::renderingClientTick) : state.get(TEMPERATURE) == 1 ? checkType(type, EntitiesRegistry.CAULDRON_ENTITY, CauldronEntity::cookingClientTick) : null;
        } else {
            return state.get(TEMPERATURE) == 2 ? checkType(type, EntitiesRegistry.CAULDRON_ENTITY, CauldronEntity::renderingServerTick) : state.get(TEMPERATURE) == 1 ? checkType(type, EntitiesRegistry.CAULDRON_ENTITY, CauldronEntity::cookingServerTick) : null;
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
