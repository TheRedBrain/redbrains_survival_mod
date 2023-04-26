package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.MillstoneBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class BellowsBlock extends Block {
    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty MECHANICAL_POWERED;
    protected static final VoxelShape SHAPE_POWERED;
    protected static final VoxelShape SHAPE_UNPOWERED;

    public BellowsBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(MECHANICAL_POWERED, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, MECHANICAL_POWERED});
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState newState = this.getDefaultState();
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockState neighborState_west = ctx.getWorld().getBlockState(ctx.getBlockPos().west());
        BlockState neighborState_north = ctx.getWorld().getBlockState(ctx.getBlockPos().north());
        BlockState neighborState_east = ctx.getWorld().getBlockState(ctx.getBlockPos().east());
        BlockState neighborState_south = ctx.getWorld().getBlockState(ctx.getBlockPos().south());
        boolean bl = (neighborState_west.isOf(BlocksRegistry.AXLE.get()) && neighborState_west.get(AxleBlock.FACING) == Direction.EAST && direction != Direction.WEST)
                || (neighborState_north.isOf(BlocksRegistry.AXLE.get()) && neighborState_north.get(AxleBlock.FACING) == Direction.SOUTH && direction != Direction.NORTH)
                || (neighborState_east.isOf(BlocksRegistry.AXLE.get()) && neighborState_east.get(AxleBlock.FACING) == Direction.WEST && direction != Direction.EAST)
                || (neighborState_south.isOf(BlocksRegistry.AXLE.get()) && neighborState_south.get(AxleBlock.FACING) == Direction.NORTH && direction != Direction.SOUTH);

        return newState.with(FACING, direction).with(MECHANICAL_POWERED, bl);
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(BellowsBlock.MECHANICAL_POWERED) ? SHAPE_POWERED : SHAPE_UNPOWERED;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

        if (state.isOf(this)) {
            if (neighborState.isOf(BlocksRegistry.AXLE.get()) && (direction != state.get(FACING))) {
                if ((direction == Direction.WEST && neighborState.get(AxleBlock.FACING) == Direction.WEST && neighborState.get(AxleBlock.POWER) >= 1)
                        || (direction == Direction.NORTH && neighborState.get(AxleBlock.FACING) == Direction.NORTH && neighborState.get(AxleBlock.POWER) >= 1)
                        || (direction == Direction.EAST && neighborState.get(AxleBlock.FACING) == Direction.EAST && neighborState.get(AxleBlock.POWER) >= 1)
                        || (direction == Direction.SOUTH && neighborState.get(AxleBlock.FACING) == Direction.SOUTH && neighborState.get(AxleBlock.POWER) >= 1)) {
                    return state.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.POWER) >= 1);
                }
            }
            Direction[] directions = {Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};
            for (Direction direction1 : directions) {
                if (direction1 != state.get(BellowsBlock.FACING) && direction1 != direction) {
                    BlockState blockState = world.getBlockState(pos.offset(direction1));
                    if (blockState.isOf(BlocksRegistry.AXLE.get()) && blockState.get(AxleBlock.FACING) == direction1 && blockState.get(AxleBlock.POWER) >= 1) {
                        return state.with(MECHANICAL_POWERED, true);
                    }
                }
            }
            return state.with(MECHANICAL_POWERED, false);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, posFrom);
    }

    public static void spawnAirParticles(World world, BlockPos pos, ItemStack stack, Random random, int count) {
        for (int i = 0; i < count; ++i) {
            Vec3d vec3d = new Vec3d((random.nextFloat() - .5d) * .1d, Math.random() * .1d + .1d,
                    (random.nextFloat() - .5d) * .1d);
            int offsetX = random.nextInt(1);
            int offsetZ = random.nextInt(1);
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), pos.getX() + offsetX,
                        pos.getY() + .1f, pos.getZ() + offsetZ, 1, vec3d.x, vec3d.y + .05d, vec3d.z, .0d);
            } else {
                world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), pos.getX() + offsetX, pos.getY() + .1f,
                        pos.getZ() + offsetZ, vec3d.x, vec3d.y + .05d, vec3d.z);
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, moved);
        } else {
            if (state.isOf(this) && newState.isOf(this) && !state.get(BellowsBlock.MECHANICAL_POWERED) && newState.get(BellowsBlock.MECHANICAL_POWERED)) {
                BlockPos firstPos;
                BlockPos firstRightPos;
                BlockPos firstLeftPos;
                BlockPos secondPos;
                BlockPos secondRightPos;
                BlockPos secondLeftPos;
                BlockPos thirdPos;
                BlockPos thirdRightPos;
                BlockPos thirdLeftPos;
                if (state.get(BellowsBlock.FACING) == Direction.SOUTH) {
                    firstPos = pos.south();
                    firstRightPos = firstPos.west();
                    firstLeftPos = firstPos.east();
                    secondPos = firstPos.south();
                    secondRightPos = secondPos.west();
                    secondLeftPos = secondPos.east();
                    thirdPos = secondPos.south();
                    thirdRightPos = thirdPos.west();
                    thirdLeftPos = thirdPos.east();
                } else if (state.get(BellowsBlock.FACING) == Direction.WEST) {
                    firstPos = pos.west();
                    firstRightPos = firstPos.north();
                    firstLeftPos = firstPos.south();
                    secondPos = firstPos.west();
                    secondRightPos = secondPos.north();
                    secondLeftPos = secondPos.south();
                    thirdPos = secondPos.west();
                    thirdRightPos = thirdPos.north();
                    thirdLeftPos = thirdPos.south();
                } else if (state.get(BellowsBlock.FACING) == Direction.NORTH) {
                    firstPos = pos.north();
                    firstRightPos = firstPos.east();
                    firstLeftPos = firstPos.west();
                    secondPos = firstPos.north();
                    secondRightPos = secondPos.east();
                    secondLeftPos = secondPos.west();
                    thirdPos = secondPos.north();
                    thirdRightPos = thirdPos.east();
                    thirdLeftPos = thirdPos.west();
                } else {
                    firstPos = pos.east();
                    firstRightPos = firstPos.south();
                    firstLeftPos = firstPos.north();
                    secondPos = firstPos.east();
                    secondRightPos = secondPos.south();
                    secondLeftPos = secondPos.north();
                    thirdPos = secondPos.east();
                    thirdRightPos = thirdPos.south();
                    thirdLeftPos = thirdPos.north();
                }
                List<BlockPos> blockPosList = List.of(firstPos, firstRightPos, firstLeftPos, secondPos, secondRightPos, secondLeftPos, thirdPos, thirdRightPos, thirdLeftPos);
                for (BlockPos blockPos : blockPosList) {
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.isOf(Blocks.FIRE) || blockState.isOf(Blocks.SOUL_FIRE)) {
                        world.removeBlock(blockPos, false);
                    } else if (blockState.isOf(BlocksRegistry.HIBACHI_FIRE.get())) {
                        world.setBlockState(blockPos, BlocksRegistry.HIBACHI_FIRE.get().getDefaultState().with(HibachiFireBlock.STOKED, true).with(HibachiFireBlock.AGE, 0), Block.NOTIFY_ALL);
                        world.scheduleBlockTick(blockPos, BlocksRegistry.HIBACHI_FIRE.get(), 5);
                    }
                }
                Box lightItemsBox = new Box(firstPos);
                Box fairlyLightItemsBox = new Box(secondPos).union(lightItemsBox);
                Box veryLightItemsBox = new Box(thirdPos).union(fairlyLightItemsBox);
                List<ItemEntity> listFirst = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), lightItemsBox, EntityPredicates.VALID_ENTITY);
                List<ItemEntity> listSecond = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), fairlyLightItemsBox, EntityPredicates.VALID_ENTITY);
                List<ItemEntity> listThird = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), veryLightItemsBox, EntityPredicates.VALID_ENTITY);

                if (state.get(BellowsBlock.FACING) == Direction.SOUTH) {
                    for (ItemEntity entity : listFirst) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, (0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                    for (ItemEntity entity : listSecond) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.FAIRLY_LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, (0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                    for (ItemEntity entity : listThird) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.VERY_LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, (0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                } else if (state.get(BellowsBlock.FACING) == Direction.WEST) {
                    for (ItemEntity entity : listFirst) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.LIGHT_ITEMS)) {
                            entity.addVelocity((0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                    for (ItemEntity entity : listSecond) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.FAIRLY_LIGHT_ITEMS)) {
                            entity.addVelocity((0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                    for (ItemEntity entity : listThird) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.VERY_LIGHT_ITEMS)) {
                            entity.addVelocity((0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                } else if (state.get(BellowsBlock.FACING) == Direction.NORTH) {
                    for (ItemEntity entity : listFirst) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, -(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                    for (ItemEntity entity : listSecond) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.FAIRLY_LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, -(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                    for (ItemEntity entity : listThird) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.VERY_LIGHT_ITEMS)) {
                            entity.addVelocity(0.0, 0.0, -(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))));
                        }
                    }
                } else {
                    for (ItemEntity entity : listFirst) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.LIGHT_ITEMS)) {
                            entity.addVelocity(-(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                    for (ItemEntity entity : listSecond) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.FAIRLY_LIGHT_ITEMS)) {
                            entity.addVelocity(-(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                    for (ItemEntity entity : listThird) {
                        if (((ItemEntity)entity).getStack().isIn(Tags.VERY_LIGHT_ITEMS)) {
                            entity.addVelocity(-(0.4 + (double) (0.01 * world.random.nextBetween(-20, 20))), 0.0, 0.0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    
    static {
        FACING = HorizontalFacingBlock.FACING;
        MECHANICAL_POWERED = BooleanProperty.of("mechanical_power");
        SHAPE_POWERED = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);
        SHAPE_UNPOWERED = VoxelShapes.fullCube();
    }
}
