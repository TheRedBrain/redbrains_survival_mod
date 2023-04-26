package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.item.CraftingChiselItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TreeStumpBlock extends Block {
    public static final IntProperty CHIPPED_STATE;
    public static final IntProperty CHISELED_STATE;
    public static final VoxelShape CHIPPED_STATE_1_SHAPE;
    public static final VoxelShape CHIPPED_STATE_2_SHAPE;
    public static final VoxelShape CHIPPED_STATE_3_SHAPE;
    public static final VoxelShape CHIPPED_STATE_4_SHAPE;
    private final Block workStump;

    public TreeStumpBlock(Block workStump, Settings settings) {
        super(settings);
        this.workStump = workStump;
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(CHIPPED_STATE, 0).with(CHISELED_STATE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CHIPPED_STATE, CHISELED_STATE});
    }

    @Deprecated
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);

        if (tool.getItem() instanceof CraftingChiselItem && state.get(TreeStumpBlock.CHIPPED_STATE) == 0) {
            if (state.get(TreeStumpBlock.CHISELED_STATE) < 1) {
                world.setBlockState(pos, this.getDefaultState().with(TreeStumpBlock.CHISELED_STATE, 1), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            } else {
                world.setBlockState(pos, workStump.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            }
        } else {
            if (state.get(TreeStumpBlock.CHIPPED_STATE) == 0) {
                world.setBlockState(pos, state.with(TreeStumpBlock.CHIPPED_STATE, 1), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            } else if (state.get(TreeStumpBlock.CHIPPED_STATE) == 1) {
                world.setBlockState(pos, state.with(TreeStumpBlock.CHIPPED_STATE, 2), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            } else if (state.get(TreeStumpBlock.CHIPPED_STATE) == 2) {
                world.setBlockState(pos, state.with(TreeStumpBlock.CHIPPED_STATE, 3), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            } else if (state.get(TreeStumpBlock.CHIPPED_STATE) == 3) {
                world.setBlockState(pos, state.with(TreeStumpBlock.CHIPPED_STATE, 4), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    // TODO
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(CHIPPED_STATE) == 1) {
            return CHIPPED_STATE_1_SHAPE;
        } else if (state.get(CHIPPED_STATE) == 2) {
            return CHIPPED_STATE_2_SHAPE;
        } else if (state.get(CHIPPED_STATE) == 3) {
            return CHIPPED_STATE_3_SHAPE;
        } else if (state.get(CHIPPED_STATE) == 4) {
            return CHIPPED_STATE_4_SHAPE;
        } else {
            return VoxelShapes.fullCube();
        }
    }

    static {
        CHIPPED_STATE = IntProperty.of("chipped_state", 0, 4);
        CHISELED_STATE = IntProperty.of("chiseled_state", 0, 1);
        CHIPPED_STATE_1_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_2_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_3_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        CHIPPED_STATE_4_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    }
}
