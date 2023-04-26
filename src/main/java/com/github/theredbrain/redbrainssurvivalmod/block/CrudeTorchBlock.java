package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.CrudeTorchBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.MillstoneBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.item.CustomTorchItem;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DecoratedPotBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class CrudeTorchBlock extends BlockWithEntity {

    protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

    public CrudeTorchBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity;
        if (!world.isClient && !state.isOf(newState.getBlock()) && !newState.isOf(BlocksRegistry.CRUDE_TORCH_BURNED.get()) && (blockEntity = world.getBlockEntity(pos)) instanceof CrudeTorchBlockEntity) {
            ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ((CrudeTorchBlockEntity)blockEntity).asStack());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return TorchBlock.sideCoversSmallSquare(world, pos.down(), Direction.UP) && world.getFluidState(pos).isEmpty();
    }

//    @Override
//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
////        boolean bl2;
//        world.scheduleBlockTick(pos, this, 100); // TODO 1000
////        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
////            return;
////        }
//        if (!state.canPlaceAt(world, pos)) {
//            world.removeBlock(pos, false);
//        }
//        if (state.isOf(this)) {
//            int age = state.get(CrudeTorchBlock.AGE);
//            if (age >= 23) {
//                world.setBlockState(pos, BlocksRegistry.CRUDE_TORCH_BURNED.get().getDefaultState(), NOTIFY_ALL);
//            } else {
//                world.setBlockState(pos, state.with(AGE, age + 1), NOTIFY_ALL);
//            }
//        }
//        // TODO fire spread
////        BlockState blockState = world.getBlockState(pos.down());
////        boolean bl = blockState.isIn(world.getDimension().infiniburn());
//////        int i = state.get(AGE);
//////        if (!bl && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2f + (float)i * 0.03f) {
//////            world.removeBlock(pos, false);
//////            return;
//////        }
//////        int j = Math.min(15, i + random.nextInt(3) / 2);
//////        if (i != j) {
//////            state = (BlockState)state.with(AGE, j);
//////            world.setBlockState(pos, state, Block.NO_REDRAW);
//////        }
////        if (!bl) {
////            if (!this.areBlocksAroundFlammable(world, pos)) {
////                BlockPos blockPos = pos.down();
////                if (!world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || i > 3) {
////                    world.removeBlock(pos, false);
////                }
////                return;
////            }
////            if (i == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
////                world.removeBlock(pos, false);
////                return;
////            }
////        }
////        int k = (bl2 = world.getBiome(pos).isIn(BiomeTags.INCREASED_FIRE_BURNOUT)) ? -50 : 0;
////        this.trySpreadingFire(world, pos.east(), 300 + k, random, i);
////        this.trySpreadingFire(world, pos.west(), 300 + k, random, i);
////        this.trySpreadingFire(world, pos.down(), 250 + k, random, i);
////        this.trySpreadingFire(world, pos.up(), 250 + k, random, i);
////        this.trySpreadingFire(world, pos.north(), 300 + k, random, i);
////        this.trySpreadingFire(world, pos.south(), 300 + k, random, i);
////        BlockPos.Mutable mutable = new BlockPos.Mutable();
////        for (int l = -1; l <= 1; ++l) {
////            for (int m = -1; m <= 1; ++m) {
////                for (int n = -1; n <= 4; ++n) {
////                    if (l == 0 && n == 0 && m == 0) continue;
////                    int o = 100;
////                    if (n > 1) {
////                        o += (n - 1) * 100;
////                    }
////                    mutable.set(pos, l, n, m);
////                    int p = this.getBurnChance(world, mutable);
////                    if (p <= 0) continue;
////                    int q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
////                    if (bl2) {
////                        q /= 2;
////                    }
////                    if (q <= 0 || random.nextInt(o) > q || world.isRaining() && this.isRainingAround(world, mutable)) continue;
////                    int r = Math.min(15, i + random.nextInt(5) / 4);
////                    world.setBlockState(mutable, this.getStateWithAge(world, mutable, r), Block.NOTIFY_ALL);
////                }
////            }
////        }
//    }

//    @Override
//    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
//        world.scheduleBlockTick(pos, this, 100); // TODO 1000
//    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity;
        if (world.getBlockState(pos).isOf(BlocksRegistry.CRUDE_TORCH.get()) && (blockEntity = world.getBlockEntity(pos)) instanceof CrudeTorchBlockEntity && itemStack.getItem() instanceof CustomTorchItem) {
            ((CrudeTorchBlockEntity)blockEntity).getBurnTimeFromStack(itemStack);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrudeTorchBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, EntitiesRegistry.CRUDE_TORCH_ENTITY, CrudeTorchBlockEntity::tick);
    }
}
