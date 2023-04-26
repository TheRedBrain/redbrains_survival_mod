package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WetClayBrickBlock extends AbstractBrickBlock {

    protected static final IntProperty DRYNESS;

    public WetClayBrickBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(DRYNESS, 1));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        super.appendProperties(builder);
        builder.add(new Property[]{DRYNESS});
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

        this.tryBreakBrick(world, state, pos, entity);
        super.onSteppedOn(world, pos, state, entity);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {

        this.tryBreakBrick(world, state, pos, entity);
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    private void tryBreakBrick(World world, BlockState state, BlockPos pos, Entity entity) {
        if (this.breaksBrick(world, entity)) {
            if (!world.isClient && state.isOf(BlocksRegistry.WET_CLAY_BRICK.get())) {
                this.breakBrick(world, pos, state);
            }

        }
    }

    private void breakBrick(World world, BlockPos pos, BlockState state) {

        world.removeBlock(pos, false);
        world.emitGameEvent(null, GameEvent.BLOCK_DESTROY, pos);
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.CLAY_BALL));
        world.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F); // TODO find better sound

    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if ((!world.getLevelProperties().isRaining() || world.getBiome(pos).isIn(Tags.NO_PRECIPITATION)) && world.getLightLevel(LightType.SKY, pos.up()) >= 15 && random.nextInt(7) == 0) {
            if (state.get(DRYNESS) <= 7) {
                world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_GRAVEL_PLACE, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F); // TODO find better sound
                world.setBlockState(pos, (BlockState) state.with(DRYNESS, state.get(DRYNESS) + 1));
            } else {
                world.setBlockState(pos, BlocksRegistry.BRICK.get().getDefaultState().with(IngotBlock.INGOTS, 1).with(FACING, state.get(FACING)));
            }
        } else if (!(world.getBiome(pos).isIn(Tags.NO_PRECIPITATION)) && world.getLevelProperties().isRaining() && world.getLightLevel(LightType.SKY, pos.up()) >= 15) {
            if (state.get(DRYNESS) >= 2) {
                world.setBlockState(pos, (BlockState) state.with(DRYNESS, state.get(DRYNESS) - 1));
            }
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        return blockState.isOf(this) ? (BlockState)blockState.with(FACING, blockState.get(FACING)) : super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (Direction)state.get(FACING) == Direction.NORTH || (Direction)state.get(FACING) == Direction.SOUTH ? SHAPE_1_SN : SHAPE_1_EW;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!(itemStack.isOf(this.asItem()))) {

            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            if (!player.isCreative()) {

                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
                world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F); // TODO find better sound
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {

        super.afterBreak(world, player, pos, state, blockEntity, stack);
        this.breakBrick(world, pos, state);
    }

    private boolean breaksBrick(World world, Entity entity) {

        return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
    }

    public boolean hasRandomTicks(BlockState state) {

        return true;
    }

    static {
        DRYNESS = IntProperty.of("dryness", 1, 8);
    }
}
