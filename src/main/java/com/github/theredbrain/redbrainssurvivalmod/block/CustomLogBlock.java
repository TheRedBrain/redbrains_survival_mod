package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.item.ChiselItem;
import com.github.theredbrain.redbrainssurvivalmod.item.CraftingChiselItem;
import com.github.theredbrain.redbrainssurvivalmod.item.CustomAxeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomLogBlock extends PillarBlock {
    private final Block strippedLog;
    public CustomLogBlock(Block strippedLog, Settings settings) {
        super(settings);
        this.strippedLog = strippedLog;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (!(tool.getItem() instanceof CustomAxeItem)) {
            world.setBlockState(pos, CustomStrippedLogBlock.getChippedStates(pos, strippedLog.getDefaultState().with(AXIS, state.get(AXIS)), world));
        }
    }
}
