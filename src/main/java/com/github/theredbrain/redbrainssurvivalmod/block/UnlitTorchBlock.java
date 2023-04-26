package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class UnlitTorchBlock extends Block implements LightableByFireStarters {
//    protected final Block litVariant;
    protected final boolean burned;
    protected final boolean crude;
    protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

    public UnlitTorchBlock(/*Block litVariant, */boolean burned, boolean crude, AbstractBlock.Settings settings) {
        super(settings);
        this.burned = burned;
        this.crude = crude;
//        this.litVariant = litVariant;
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return this.getDroppedTorch();
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient && !state.isOf(newState.getBlock())) {
            ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), this.getDroppedTorch());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    private ItemStack getDroppedTorch() {
        return this.crude ? (this.burned ? ItemStack.EMPTY : ItemsRegistry.CRUDE_TORCH_UNLIT.get().getDefaultStack()) : ItemsRegistry.TORCH_UNLIT.get().getDefaultStack();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOUNDING_SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !this.canPlaceAt(state, world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    // TODO
    //  onUse

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return TorchBlock.sideCoversSmallSquare(world, pos.down(), Direction.UP) && world.getFluidState(pos).isEmpty();
    }

    @Override
    public boolean tryLighting(World world, BlockState blockState, BlockPos blockPos) {
        return true;
    }
}

