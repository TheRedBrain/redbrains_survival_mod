package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.MillstoneBlockEntity;
import com.github.theredbrain.redbrainssurvivalmod.recipe.GrindingRecipe;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
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
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MillstoneBlock extends BlockWithEntity {
    public static final BooleanProperty MECHANICAL_POWERED;
    protected static final VoxelShape SHAPE_PART_1;
    protected static final VoxelShape SHAPE_PART_2;
    protected static final VoxelShape SHAPE_PART_3;
    protected static final VoxelShape SHAPE_PART_4;
    protected static final VoxelShape SHAPE_FULL;

    public MillstoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(MECHANICAL_POWERED, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{MECHANICAL_POWERED});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.MILLSTONE_ENTITY.instantiate(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return (Boolean)state.get(MECHANICAL_POWERED) ? checkType(type, EntitiesRegistry.MILLSTONE_ENTITY, MillstoneBlockEntity::clientTick) : null;
        } else {
            return (Boolean)state.get(MECHANICAL_POWERED) ? checkType(type, EntitiesRegistry.MILLSTONE_ENTITY, MillstoneBlockEntity::poweredServerTick) : null;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState newState = this.getDefaultState();
        BlockState neighborState_1 = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        BlockState neighborState_2 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if ((neighborState_1.isOf(BlocksRegistry.AXLE.get()) && neighborState_1.get(AxleBlock.FACING) == Direction.UP) || (neighborState_2.isOf(BlocksRegistry.AXLE.get()) && neighborState_2.get(AxleBlock.FACING) == Direction.DOWN)) {
            newState = newState.with(MECHANICAL_POWERED, neighborState_1.isOf(BlocksRegistry.AXLE.get()) ? neighborState_1.get(AxleBlock.POWER) >= 1 : neighborState_2.isOf(BlocksRegistry.AXLE.get()) && neighborState_2.get(AxleBlock.POWER) >= 1);
        }

        return newState;
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_FULL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos posFrom) {

        if (state.isOf(this) && neighborState.isOf(BlocksRegistry.AXLE.get()) && ((direction == Direction.UP && neighborState.get(AxleBlock.FACING) == Direction.UP) || (direction == Direction.DOWN && neighborState.get(AxleBlock.FACING) == Direction.DOWN))) {
            return state.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.POWER) >= 1);
        } else if (state.isOf(this) && (direction == Direction.SOUTH || direction == Direction.WEST || direction == Direction.NORTH || direction == Direction.EAST) && neighborState.isOf(BlocksRegistry.HAND_CRANK.get())) {
            if (neighborState.get(HandCrankBlock.POWERED)) {
                return state.with(MECHANICAL_POWERED, true);
            }
            return state.with(MECHANICAL_POWERED, false);
        }
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
        if (blockEntity instanceof MillstoneBlockEntity) {
            MillstoneBlockEntity millstoneBlockEntity = (MillstoneBlockEntity) blockEntity;
            ItemStack heldItem = player.getStackInHand(hand);
            Optional<GrindingRecipe> optional = millstoneBlockEntity.getRecipeFor(heldItem);
            if (optional.isPresent()) {
                if (!world.isClient && millstoneBlockEntity.addItem(player, player.getAbilities().creativeMode ? heldItem.copy() : heldItem, ((GrindingRecipe)optional.get()).getGrindingTime())) {
//                    player.incrementStat(Stats.INTERACT_WITH_MILLSTONE); // TODO create stat INTERACT_WITH_MILLSTONE
                    return ActionResult.SUCCESS;
                }

                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MillstoneBlockEntity) {
                ItemScatterer.spawn(world, pos, ((MillstoneBlockEntity)blockEntity).getItemBeingGround());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        MECHANICAL_POWERED = BooleanProperty.of("mechanical_power");
        SHAPE_PART_1 = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
        SHAPE_PART_2 = Block.createCuboidShape(1.0D, 9.0D, 1.0D, 15.0D, 11.0D, 15.0D);
        SHAPE_PART_3 = Block.createCuboidShape(0.0D, 11.0D, 0.0D, 16.0D, 15.0D, 16.0D);
        SHAPE_PART_4 = Block.createCuboidShape(1.0D, 15.0D, 1.0D, 15.0D, 16.0D, 15.0D);
        SHAPE_FULL = VoxelShapes.union(SHAPE_PART_1, SHAPE_PART_2, SHAPE_PART_3, SHAPE_PART_4);
    }
}
