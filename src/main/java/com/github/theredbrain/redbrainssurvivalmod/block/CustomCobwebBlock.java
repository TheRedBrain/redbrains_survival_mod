package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.item.ChiselItem;
import com.github.theredbrain.redbrainssurvivalmod.item.CraftingChiselItem;
import com.github.theredbrain.redbrainssurvivalmod.item.CustomAxeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomCobwebBlock extends Block {

    public static IntProperty CHIPPED_STATE;
    public static VoxelShape CHIPPED_STATE_1_SHAPE;
    public static VoxelShape CHIPPED_STATE_2_SHAPE;
    public static VoxelShape CHIPPED_STATE_3_SHAPE;
    public static VoxelShape CHIPPED_STATE_4_SHAPE;

    public CustomCobwebBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(CHIPPED_STATE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{CHIPPED_STATE});
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (tool.getItem() instanceof ChiselItem || tool.getItem() instanceof CraftingChiselItem) {
            if (state.get(CHIPPED_STATE) == 0) {
                world.setBlockState(pos, state.with(CHIPPED_STATE, 1));
            } else if (state.get(CHIPPED_STATE) == 1) {
                world.setBlockState(pos, state.with(CHIPPED_STATE, 2));
            } else if (state.get(CHIPPED_STATE) == 2) {
                world.setBlockState(pos, state.with(CHIPPED_STATE, 3));
            } else if (state.get(CHIPPED_STATE) == 3) {
                world.setBlockState(pos, state.with(CHIPPED_STATE, 4));
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof SpiderEntity)) {
            entity.slowMovement(state, new Vec3d(0.25, 0.05f, 0.25));
        }
    }

    static {
        CHIPPED_STATE = IntProperty.of("chipped_state", 0, 4);
        CHIPPED_STATE_1_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        CHIPPED_STATE_2_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        CHIPPED_STATE_3_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        CHIPPED_STATE_4_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
