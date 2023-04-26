package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.item.CustomAxeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomStonePillarBlock extends PillarBlock {

    public static final IntProperty CHIPPED_STATE = IntProperty.of("chipped_state", 0, 4);

    public CustomStonePillarBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(CHIPPED_STATE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CHIPPED_STATE});
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!(tool.getItem() instanceof CustomAxeItem)) { // CustomPickaxeItem
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
}
