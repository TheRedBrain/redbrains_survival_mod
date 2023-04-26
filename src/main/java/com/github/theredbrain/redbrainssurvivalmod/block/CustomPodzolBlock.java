package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class CustomPodzolBlock extends Block {
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    public CustomPodzolBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(SNOWY, false));
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        CustomDirtBlock.loosenNeighboringDirt(world, pos);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (tool.isIn(ItemTags.HOES)) {
            world.setBlockState(pos, BlocksRegistry.LOOSE_DIRT.get().getDefaultState(), NOTIFY_ALL);
        } else if (!tool.isIn(Tags.PROPER_DIGGING_TOOLS)) {
            CustomDirtBlock.loosenNeighboringDirt(world, pos);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP) {
            return (BlockState)state.with(SNOWY, CustomPodzolBlock.isSnow(neighborState));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return (BlockState)this.getDefaultState().with(SNOWY, CustomPodzolBlock.isSnow(blockState));
    }

    private static boolean isSnow(BlockState state) {
        return state.isIn(BlockTags.SNOW);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }
}
