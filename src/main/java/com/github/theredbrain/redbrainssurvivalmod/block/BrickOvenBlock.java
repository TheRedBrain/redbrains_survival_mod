package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.RedBrainsSurvivalMod;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.BrickOvenEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Pair;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BrickOvenBlock extends BlockWithEntity {

    private static final int MAX_FUEL_TIME = 8;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty FUEL = IntProperty.of("fuel", 0, 8);
    public static final BooleanProperty LIT = Properties.LIT;
//    public static final BooleanProperty SMOULDERING = BooleanProperty.of("smouldering");
    protected static final VoxelShape NEGATIVE_SHAPE_NORTH = Block.createCuboidShape(4.0D, 6.0D, 0.0D, 12.0D, 12.0D, 12.0D);
    protected static final VoxelShape NEGATIVE_SHAPE_EAST = Block.createCuboidShape(4.0D, 6.0D, 4.0D, 16.0D, 12.0D, 12.0D);
    protected static final VoxelShape NEGATIVE_SHAPE_SOUTH = Block.createCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 12.0D, 16.0D);
    protected static final VoxelShape NEGATIVE_SHAPE_WEST = Block.createCuboidShape(0.0D, 6.0D, 4.0D, 12.0D, 12.0D, 12.0D);
    protected static final VoxelShape SHAPE_NORTH = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), NEGATIVE_SHAPE_NORTH, BooleanBiFunction.ONLY_FIRST);
    protected static final VoxelShape SHAPE_EAST = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), NEGATIVE_SHAPE_EAST, BooleanBiFunction.ONLY_FIRST);
    protected static final VoxelShape SHAPE_SOUTH = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), NEGATIVE_SHAPE_SOUTH, BooleanBiFunction.ONLY_FIRST);
    protected static final VoxelShape SHAPE_WEST = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), NEGATIVE_SHAPE_WEST, BooleanBiFunction.ONLY_FIRST);

    public BrickOvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(FUEL, 0).with(LIT, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, FUEL, LIT});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.BRICK_OVEN_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return state.get(LIT) ? checkType(type, EntitiesRegistry.BRICK_OVEN_ENTITY, BrickOvenEntity::clientTick) : null;
        } else {
            return state.get(LIT) ? checkType(type, EntitiesRegistry.BRICK_OVEN_ENTITY, BrickOvenEntity::serverTick) : null;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());

    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BrickOvenBlock.FACING)) {
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

//        if (state.isOf(this) && neighborState.isOf(BlocksRegistry.AXLE.get()) && ((direction == Direction.UP && neighborState.get(AxleBlock.FACING) == Direction.UP) || (direction == Direction.DOWN && neighborState.get(AxleBlock.FACING) == Direction.DOWN))) {
//            return state.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.POWER) >= 1);
//        } else if (state.isOf(this) && (direction == Direction.SOUTH || direction == Direction.WEST || direction == Direction.NORTH || direction == Direction.EAST) && neighborState.isOf(BlocksRegistry.HAND_CRANK.get())) {
//            if (neighborState.get(HandCrankBlock.POWERED)) {
//                return state.with(MECHANICAL_POWERED, true);
//            }
//            return state.with(MECHANICAL_POWERED, false);
//        }
        return state;
    }

    public static void spawnGrindingParticles(World world, BlockPos pos, ItemStack stack, Random random, int count) {
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BrickOvenEntity brickOvenEntity && world.getBlockState(pos.offset(state.get(FACING))).isOf(Blocks.AIR)) {
            ItemStack heldItemStack = player.getStackInHand(hand);
             if (heldItemStack.isIn(Tags.FIRE_STARTERS)) {
                return ActionResult.PASS;
            }

            Optional<Integer> optionalFuelTime = brickOvenEntity.getFuelTimeFor(heldItemStack);

            // data-driven recipes
//            Optional<SmeltingRecipe> optionalRecipe = brickOvenEntity.getRecipeFor(heldItemStack);

            // hard-coded recipes
            Optional<Pair<Pair<Item, Integer>, Integer>> optionalSmeltingResult = brickOvenEntity.getSmeltingResult(heldItemStack);

            // data-driven recipes
            /*if (optionalRecipe.isPresent()) {
                if (!world.isClient && brickOvenEntity.addItem(player, player.getAbilities().creativeMode ? heldItemStack.copy() : heldItemStack, ((SmeltingRecipe) optionalRecipe.get()).getCookTime())) {
//                    player.incrementStat(Stats.INTERACT_WITH_MILLSTONE); // TODO create stat INTERACT_WITH_MILLSTONE
                    return ActionResult.SUCCESS;
                }*/

            // hard-coded recipes
            if (optionalSmeltingResult.isPresent()) {
                if (!world.isClient && brickOvenEntity.getContent().get(0).isEmpty() && brickOvenEntity.addItem(player, player.getAbilities().creativeMode ? heldItemStack.copy() : heldItemStack, optionalSmeltingResult.get().getRight())) {
//                    player.incrementStat(Stats.INTERACT_WITH_MILLSTONE); // TODO create stat INTERACT_WITH_MILLSTONE
                    return ActionResult.SUCCESS;
                }
            } else if (optionalFuelTime.isPresent()) {
                if (!world.isClient && this.addFuel(world, state, pos, player.getAbilities().creativeMode ? heldItemStack.copy() : heldItemStack, optionalFuelTime.get())) {
//                    player.incrementStat(Stats.INTERACT_WITH_MILLSTONE); // TODO create stat INTERACT_WITH_MILLSTONE
                    return ActionResult.SUCCESS;
                }
            }
            if (brickOvenEntity.removeItem(player)) {
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

//    @Override
//    public boolean tryLighting(World world, BlockState blockState, BlockPos blockPos) {
//        BlockEntity blockEntity = world.getBlockEntity(blockPos);
//        RedBrainsSurvivalMod.LOGGER.info("brick oven clicked by fire starter");
//        if (blockEntity instanceof BrickOvenEntity brickOvenEntity && blockState.isOf(this)) {
//            if (brickOvenEntity.getFuelTime() > 0 && !blockState.get(Properties.LIT) && !blockState.get(BrickOvenBlock.SMOULDERING)) {
//                RedBrainsSurvivalMod.LOGGER.info("should ignite");
////                world.setBlockState(blockPos, blockState.with(LIT, true), Block.NOTIFY_ALL);
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BrickOvenEntity) {
                ItemScatterer.spawn(world, pos, ((BrickOvenEntity) blockEntity).getContent());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public static boolean canBeLit(BlockState blockState) {
//        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        return blockState.isOf(BlocksRegistry.BRICK_OVEN.get()) && !blockState.get(LIT) && blockState.get(FUEL) > 0;
    }

    public boolean addFuel(World world, BlockState blockState, BlockPos blockPos, ItemStack itemStack, int fuelTime) {
        if (!itemStack.isEmpty() && world != null && !world.isClient && blockState.isOf(this)) {
            int oldFuelTime = blockState.get(FUEL);
            if (oldFuelTime + fuelTime <= MAX_FUEL_TIME) {
                world.setBlockState(blockPos, blockState.with(FUEL, oldFuelTime + fuelTime), Block.NOTIFY_ALL);
                itemStack.decrement(1);
                return true;
            }
        }
        return false;
    }
}