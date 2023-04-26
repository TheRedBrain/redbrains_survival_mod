package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.CrudeTorchBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CrudeWallTorchBlock extends CrudeTorchBlock {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final Map<Direction, VoxelShape> BOUNDING_SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0), Direction.SOUTH, Block.createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0), Direction.WEST, Block.createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5), Direction.EAST, Block.createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)));

    public CrudeWallTorchBlock(/*Block litVariant, */AbstractBlock.Settings settings) {
        super(/*litVariant, */settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return WallTorchBlock.getBoundingShape(state);
    }

    public static VoxelShape getBoundingShape(BlockState state) {
        return BOUNDING_SHAPES.get(state.get(FACING));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction) && world.getFluidState(pos).isEmpty();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction[] directions;
        BlockState blockState = this.getDefaultState();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        for (Direction direction : directions = ctx.getPlacementDirections()) {
            Direction direction2;
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(FACING, direction2 = direction.getOpposite())).canPlaceAt(worldView, blockPos)) continue;
            return blockState;
        }
        return null;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity;
        if (!world.isClient && !state.isOf(newState.getBlock()) && !newState.isOf(BlocksRegistry.CRUDE_WALL_TORCH_BURNED.get()) && (blockEntity = world.getBlockEntity(pos)) instanceof CrudeTorchBlockEntity) {
            ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), ((CrudeTorchBlockEntity)blockEntity).asStack());
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

//    @Override
//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
////        boolean bl2;
//        world.scheduleBlockTick(pos, this, 1000);
////        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
////            return;
////        }
//        if (!state.canPlaceAt(world, pos)) {
//            world.removeBlock(pos, false);
//        }
//        if (state.isOf(this)) {
//            int age = state.get(CrudeTorchBlock.AGE);
//            if (age >= 24) {
//                world.setBlockState(pos, BlocksRegistry.CRUDE_TORCH_BURNED.get().getDefaultState().with(FACING, state.get(FACING)), NOTIFY_ALL);
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
//        world.scheduleBlockTick(pos, this, 1000);
//    }
}

