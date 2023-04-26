package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class CustomCarvedPumpkinBlock extends HorizontalFacingBlock
        implements Equipment {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
//    @Nullable
//    private BlockPattern snowGolemDispenserPattern;
//    @Nullable
//    private BlockPattern snowGolemPattern;
//    @Nullable
//    private BlockPattern ironGolemDispenserPattern;
//    @Nullable
//    private BlockPattern ironGolemPattern;
    private final boolean isLantern;
//    private static final Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE = state -> state != null && (state.isOf(Blocks.CARVED_PUMPKIN) || state.isOf(Blocks.JACK_O_LANTERN));

    public CustomCarvedPumpkinBlock(boolean isLantern, AbstractBlock.Settings settings) {
        super(settings);
        this.isLantern = isLantern;
        this.setDefaultState((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

//    @Override
//    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
//        if (oldState.isOf(state.getBlock())) {
//            return;
//        }
//        this.trySpawnEntity(world, pos);
//    }
//
//    public boolean canDispense(WorldView world, BlockPos pos) {
//        return this.getSnowGolemDispenserPattern().searchAround(world, pos) != null || this.getIronGolemDispenserPattern().searchAround(world, pos) != null;
//    }
//
//    private void trySpawnEntity(World world, BlockPos pos) {
//        BlockPattern.Result result = this.getSnowGolemPattern().searchAround(world, pos);
//        if (result != null) {
//            SnowGolemEntity snowGolemEntity = EntityType.SNOW_GOLEM.create(world);
//            if (snowGolemEntity != null) {
//                CarvedPumpkinBlock.spawnEntity(world, result, snowGolemEntity, result.translate(0, 2, 0).getBlockPos());
//            }
//        } else {
//            IronGolemEntity ironGolemEntity;
//            BlockPattern.Result result2 = this.getIronGolemPattern().searchAround(world, pos);
//            if (result2 != null && (ironGolemEntity = EntityType.IRON_GOLEM.create(world)) != null) {
//                ironGolemEntity.setPlayerCreated(true);
//                CarvedPumpkinBlock.spawnEntity(world, result2, ironGolemEntity, result2.translate(1, 2, 0).getBlockPos());
//            }
//        }
//    }
//
//    private static void spawnEntity(World world, BlockPattern.Result patternResult, Entity entity, BlockPos pos) {
//        CarvedPumpkinBlock.breakPatternBlocks(world, patternResult);
//        entity.refreshPositionAndAngles((double) pos.getX() + 0.5, (double) pos.getY() + 0.05, (double) pos.getZ() + 0.5, 0.0f, 0.0f);
//        world.spawnEntity(entity);
//        for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, entity.getBoundingBox().expand(5.0))) {
//            Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, entity);
//        }
//        CarvedPumpkinBlock.updatePatternBlocks(world, patternResult);
//    }
//
//    public static void breakPatternBlocks(World world, BlockPattern.Result patternResult) {
//        for (int i = 0; i < patternResult.getWidth(); ++i) {
//            for (int j = 0; j < patternResult.getHeight(); ++j) {
//                CachedBlockPosition cachedBlockPosition = patternResult.translate(i, j, 0);
//                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), Block.NOTIFY_LISTENERS);
//                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
//            }
//        }
//    }
//
//    public static void updatePatternBlocks(World world, BlockPattern.Result patternResult) {
//        for (int i = 0; i < patternResult.getWidth(); ++i) {
//            for (int j = 0; j < patternResult.getHeight(); ++j) {
//                CachedBlockPosition cachedBlockPosition = patternResult.translate(i, j, 0);
//                world.updateNeighbors(cachedBlockPosition.getBlockPos(), Blocks.AIR);
//            }
//        }
//    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World blockView = ctx.getWorld();
        BlockState blockState = blockView.getBlockState(blockPos);
        if (this.isLantern || CustomCarvedPumpkinBlock.touchesWater(blockView, blockPos, blockState)) {
            blockState = BlocksRegistry.CARVED_PUMPKIN.get().getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        } else {
            blockState = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
        return blockState;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.isLantern && CustomCarvedPumpkinBlock.touchesWater(world, pos, state)) {
            return BlocksRegistry.CARVED_PUMPKIN.get().getDefaultState().with(FACING, state.get(FACING));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private static boolean touchesWater(BlockView world, BlockPos pos, BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER) || touchesWaterOnAnySide(world, pos);
    }

    private static boolean touchesWaterOnAnySide(BlockView world, BlockPos pos) {
        boolean bl = false;
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction == Direction.DOWN && !(blockState.getFluidState().isIn(FluidTags.WATER))) continue;
            mutable.set((Vec3i)pos, direction);
            blockState = world.getBlockState(mutable);
            if (!(blockState.getFluidState().isIn(FluidTags.WATER)) || blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

//    private BlockPattern getSnowGolemDispenserPattern() {
//        if (this.snowGolemDispenserPattern == null) {
//            this.snowGolemDispenserPattern = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
//        }
//        return this.snowGolemDispenserPattern;
//    }
//
//    private BlockPattern getSnowGolemPattern() {
//        if (this.snowGolemPattern == null) {
//            this.snowGolemPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
//        }
//        return this.snowGolemPattern;
//    }
//
//    private BlockPattern getIronGolemDispenserPattern() {
//        if (this.ironGolemDispenserPattern == null) {
//            this.ironGolemDispenserPattern = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
//        }
//        return this.ironGolemDispenserPattern;
//    }
//
//    private BlockPattern getIronGolemPattern() {
//        if (this.ironGolemPattern == null) {
//            this.ironGolemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockPosition.matchesBlockState(IS_GOLEM_HEAD_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR))).build();
//        }
//        return this.ironGolemPattern;
//    }

    @Override
    public EquipmentSlot getSlotType() {
        return this.isLantern ? EquipmentSlot.MAINHAND : EquipmentSlot.HEAD;
    }
}