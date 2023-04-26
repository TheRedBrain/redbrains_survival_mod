package com.github.theredbrain.redbrainssurvivalmod.block.subblocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class CornerBlock extends Block implements Waterloggable {
    public static DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    public static BooleanProperty WATERLOGGED;
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    private final Block baseBlock;
    private final BlockState baseBlockState;

    public CornerBlock(BlockState baseBlockState, Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.SOUTH)).with(HALF, BlockHalf.BOTTOM)).with(WATERLOGGED, false));
        this.baseBlock = baseBlockState.getBlock();
        this.baseBlockState = baseBlockState;
}

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, HALF, WATERLOGGED});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);
        BlockHalf half = state.get(HALF);

        return switch (facing) {
            case WEST -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_WEST_CORNER_SHAPE : TOP_NORTH_WEST_CORNER_SHAPE;
            case NORTH -> half == BlockHalf.BOTTOM ? BOTTOM_NORTH_EAST_CORNER_SHAPE : TOP_NORTH_EAST_CORNER_SHAPE;
            case EAST -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_EAST_CORNER_SHAPE : TOP_SOUTH_EAST_CORNER_SHAPE;
            default -> half == BlockHalf.BOTTOM ? BOTTOM_SOUTH_WEST_CORNER_SHAPE : TOP_SOUTH_WEST_CORNER_SHAPE;
        };
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        Vec3d hitPos = ctx.getHitPos();

        Direction hitPosToFacing;

//        RedBrainsSurvivalMod.LOGGER.info("X: " + (hitPos.getX() - (double)blockPos.getX()));
//        RedBrainsSurvivalMod.LOGGER.info("Y: " + (hitPos.getY() - (double)blockPos.getY()));
//        RedBrainsSurvivalMod.LOGGER.info("Z: " + (hitPos.getZ() - (double)blockPos.getZ()));

        switch (direction) {
            case UP:
//                RedBrainsSurvivalMod.LOGGER.info("UP");
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            case DOWN:
//                RedBrainsSurvivalMod.LOGGER.info("DOWN");
                if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getX() - (double)blockPos.getX()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            case SOUTH:
//                RedBrainsSurvivalMod.LOGGER.info("SOUTH Y X");
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                }
            case WEST:
//                RedBrainsSurvivalMod.LOGGER.info("WEST");
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.NORTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                }
            case NORTH:
//                RedBrainsSurvivalMod.LOGGER.info("NORTH Y X");
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) < 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getX() - (double)blockPos.getX()) > 0.5) {
                    hitPosToFacing = Direction.EAST;
                    break;
                }
            case EAST:
//                RedBrainsSurvivalMod.LOGGER.info("EAST");
                if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) > 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) < 0.5) {
                    hitPosToFacing = Direction.WEST;
                    break;
                } else if ((hitPos.getY() - (double)blockPos.getY()) < 0.5 && (hitPos.getZ() - (double)blockPos.getZ()) > 0.5) {
                    hitPosToFacing = Direction.SOUTH;
                    break;
                }
            default:
                hitPosToFacing = Direction.SOUTH;
        }

        BlockHalf newHalf = direction != Direction.DOWN && (direction == Direction.UP || !(hitPos.y - (double)blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP;
        return (BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, hitPosToFacing)).with(HALF, newHalf)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        //TODO
        // can replace corners and mouldings of same base block
        return false;
    }

    //-----------------------waterlogging stuff-----------------------

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch(type) {
            case LAND:
                return false;
            case WATER:
                return world.getFluidState(pos).isIn(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        }
    }

    //-----------------------inheriting base block stats-----------------------

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        this.baseBlock.randomDisplayTick(state, world, pos, random);
    }

    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        this.baseBlockState.onBlockBreakStart(world, pos, player);
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        this.baseBlock.onBroken(world, pos, state);
    }

    public float getBlastResistance() {
        return this.baseBlock.getBlastResistance();
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(state.getBlock())) {
            world.updateNeighbor(this.baseBlockState, pos, Blocks.AIR, pos, false);
            this.baseBlock.onBlockAdded(this.baseBlockState, world, pos, oldState, false);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            this.baseBlockState.onStateReplaced(world, pos, newState, moved);
        }
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        this.baseBlock.onSteppedOn(world, pos, state, entity);
    }

    public boolean hasRandomTicks(BlockState state) {
        return this.baseBlock.hasRandomTicks(state);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.randomTick(state, world, pos, random);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.baseBlock.scheduledTick(state, world, pos, random);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return this.baseBlockState.onUse(world, player, hand, hit);
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        this.baseBlock.onDestroyedByExplosion(world, pos, explosion);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        WATERLOGGED = Properties.WATERLOGGED;

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
    }
}
