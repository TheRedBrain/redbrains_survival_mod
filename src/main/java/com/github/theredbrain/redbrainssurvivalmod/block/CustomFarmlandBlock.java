package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.block.plants.WeedBlock;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;

import java.util.Iterator;

public class CustomFarmlandBlock extends FallingBlock {
    public static final BooleanProperty FERTILIZED; // TODO maybe can grow crop or weed only once per day
    public static final IntProperty MOISTURE;
    protected static final VoxelShape SHAPE;
    public static final int MAX_MOISTURE = 7;

    public CustomFarmlandBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FERTILIZED, false).with(MOISTURE, 0));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getStackInHand(hand);
        if (!world.isClient()) {
            if (itemstack.isIn(Tags.FERTILIZERS) && world.getBlockState(pos).isOf(this) && !world.getBlockState(pos).get(FERTILIZED)) {
                world.setBlockState(pos, state.with(MOISTURE, state.get(MOISTURE)).with(FERTILIZED, true));
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        return !blockState.getMaterial().isSolid() || blockState.getBlock() instanceof FenceGateBlock || blockState.getBlock() instanceof PistonExtensionBlock;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return !this.getDefaultState().canPlaceAt(ctx.getWorld(), ctx.getBlockPos()) ? BlocksRegistry.LOOSE_DIRT.get().getDefaultState() : super.getPlacementState(ctx);
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, BlocksRegistry.LOOSE_DIRT.get().getDefaultState());
            this.configureFallingBlockEntity(fallingBlockEntity);
        } else if (!state.canPlaceAt(world, pos)) {
            setToDirt(state, world, pos);
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(MOISTURE);
        if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
            if (i > 0) {
                world.setBlockState(pos, (BlockState)state.with(MOISTURE, i - 1).with(FERTILIZED, state.get(FERTILIZED)), 2);
            } else {
                setToDirt(state, world, pos);
            }
        } else if (i < 7) {
            world.setBlockState(pos, (BlockState)state.with(MOISTURE, 7).with(FERTILIZED, state.get(FERTILIZED)), 2);
        } else if (i == 7 && world.isNight() && world.getBlockState(pos.up()).isAir()) {
            if (random.nextInt(100) == 0) {
                world.setBlockState(pos.up(), BlocksRegistry.WEED.get().getDefaultState().with(WeedBlock.AGE, 1).with(WeedBlock.HAS_WEED_GROWN, true), 2);
            }
        }

    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            setToDirt(state, world, pos);
        }

        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    public static void setToDirt(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, pushEntitiesUpBeforeBlockChange(state, BlocksRegistry.LOOSE_DIRT.get().getDefaultState(), world, pos));
    }

    private static boolean isWaterNearby(WorldView world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!world.getFluidState(blockPos).isIn(FluidTags.WATER));

        return true;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(new Property[]{FERTILIZED, MOISTURE});
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        FERTILIZED = BooleanProperty.of("fertilized");
        MOISTURE = Properties.MOISTURE;
        SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    }
}
