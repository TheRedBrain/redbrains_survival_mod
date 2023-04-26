package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.HibachiBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.SawBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SawBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty MECHANICAL_POWERED = BooleanProperty.of("mechanical_powered");

    public static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape SHAPE_UP = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    public static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
    public static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    public static final VoxelShape SHAPE_WEST = Block.createCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
//
//    protected static final Map<Block, List<ItemStack>> SAWED_BLOCKS = new ImmutableMap.Builder<Block, List<ItemStack>>()
//            // logs
//            .put(BlocksRegistry.ACACIA_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.ACACIA_BARK.get())))
//            .put(BlocksRegistry.ACACIA_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.ACACIA_BARK.get())))
//            .put(BlocksRegistry.BIRCH_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.BIRCH_BARK.get())))
//            .put(BlocksRegistry.BIRCH_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.BIRCH_BARK.get())))
//            .put(BlocksRegistry.DARK_OAK_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.DARK_OAK_BARK.get())))
//            .put(BlocksRegistry.DARK_OAK_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.DARK_OAK_BARK.get())))
//            .put(BlocksRegistry.JUNGLE_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.JUNGLE_BARK.get())))
//            .put(BlocksRegistry.JUNGLE_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.JUNGLE_BARK.get())))
//            .put(BlocksRegistry.MANGROVE_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.MANGROVE_BARK.get())))
//            .put(BlocksRegistry.MANGROVE_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.MANGROVE_BARK.get())))
//            .put(BlocksRegistry.OAK_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.OAK_BARK.get())))
//            .put(BlocksRegistry.OAK_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.OAK_BARK.get())))
//            .put(BlocksRegistry.SPRUCE_LOG.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.SPRUCE_BARK.get())))
//            .put(BlocksRegistry.SPRUCE_WOOD.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS.get().asItem(), 4), new ItemStack(ItemsRegistry.SAW_DUST.get(), 2), new ItemStack(ItemsRegistry.SPRUCE_BARK.get())))
//
//            // wooden sub blocks
//            .put(BlocksRegistry.ACACIA_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.ACACIA_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.ACACIA_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.ACACIA_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.ACACIA_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.ACACIA_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.BIRCH_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.BIRCH_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.BIRCH_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.BIRCH_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.BIRCH_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.BIRCH_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.DARK_OAK_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.DARK_OAK_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.DARK_OAK_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.DARK_OAK_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.DARK_OAK_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.JUNGLE_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.JUNGLE_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.JUNGLE_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.JUNGLE_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.JUNGLE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.JUNGLE_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.MANGROVE_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.MANGROVE_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.MANGROVE_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.MANGROVE_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.MANGROVE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.MANGROVE_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.OAK_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.OAK_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.OAK_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.OAK_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.OAK_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.OAK_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .put(BlocksRegistry.SPRUCE_PLANKS.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS_SIDING.get().asItem(), 2)))
//            .put(BlocksRegistry.SPRUCE_PLANKS_SIDING.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.SPRUCE_PLANKS_EDGE.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS_CORNER.get().asItem(), 2)))
//            .put(BlocksRegistry.SPRUCE_PLANKS_STAIR.get(), ImmutableList.of(new ItemStack(BlocksRegistry.SPRUCE_PLANKS_EDGE.get().asItem(), 2)))
//            .put(BlocksRegistry.SPRUCE_PLANKS_CORNER.get(), ImmutableList.of(new ItemStack(ItemsRegistry.GEAR.get(), 2)))
//            .build();

    public SawBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(FACING, Direction.NORTH).with(MECHANICAL_POWERED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, MECHANICAL_POWERED});
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case DOWN -> SHAPE_DOWN;
            case UP -> SHAPE_UP;
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getSide();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(facing.getOpposite()));
        boolean bl = blockState.isOf(BlocksRegistry.AXLE.get()) && blockState.get(AxleBlock.FACING) == facing.getOpposite() && blockState.get(AxleBlock.POWER) >= 1;
        return this.getDefaultState().with(FACING, facing).with(MECHANICAL_POWERED, bl);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

//        Direction direction1 = state.get(FACING);
        Direction direction2 = state.get(FACING).getOpposite();
        if (state.isOf(this)) {
            if (direction == direction2) {
                if (neighborState.isOf(BlocksRegistry.AXLE.get()) && neighborState.get(AxleBlock.FACING) == direction2) {
                    boolean bl = neighborState.get(AxleBlock.POWER) >= 1;
                    state = state.with(MECHANICAL_POWERED, bl);
                } else {
                    state = state.with(MECHANICAL_POWERED, false);
                }
            }
//            if (direction == direction1 && state.get(MECHANICAL_POWERED) && !(neighborState.isIn(Tags.BROKEN_BY_SAW) || neighborState.isOf(BlocksRegistry.CHOPPING_BLOCK.get()) || neighborState.isOf(BlocksRegistry.BLOODY_CHOPPING_BLOCK.get()) || neighborState.isOf(Blocks.AIR))) {
//
//                world.breakBlock(pos, false);
//                for (ItemStack drop : this.dropsWhenBroken) {
//                    ItemScatterer.spawn(((World)world), pos.getX(), pos.getY(), pos.getZ(), drop);
//                }
//                return Blocks.AIR.getDefaultState();
//            }
        }
        return state;
    }

//    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
//
//        if (sourcePos == pos.offset(state.get(FACING)) && state.get(MECHANICAL_POWERED)) {
//            BlockState neighborState = world.getBlockState(sourcePos);
//            if (neighborState.getBlock() == sourceBlock) {
//                Optional<List<ItemStack>> optional = SawBlock.getSawedItems(sourceBlock);
//                if (optional.isPresent()) {
//                    world.breakBlock(sourcePos, false);
//                    for (ItemStack itemStack : optional.get()) {
//                        ItemScatterer.spawn(world, sourcePos.getX(), sourcePos.getY(), sourcePos.getZ(), itemStack);
//                    }
//                } else {
//                    world.breakBlock(sourcePos, true);
//                    // TODO sound & particles
//                }
//            }
//        }
//        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
//    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.SAW_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return state.get(MECHANICAL_POWERED) ? checkType(type, EntitiesRegistry.SAW_ENTITY, SawBlockEntity::tick) : null;
    }

//    private static Optional<List<ItemStack>> getSawedItems(Block block) {
//        return Optional.ofNullable(SAWED_BLOCKS.get(block));
//    }
}
