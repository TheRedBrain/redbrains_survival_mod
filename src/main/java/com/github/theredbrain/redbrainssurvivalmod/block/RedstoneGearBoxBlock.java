package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class RedstoneGearBoxBlock extends GearBoxBlock {
    public static final BooleanProperty REDSTONE_POWERED;

    public RedstoneGearBoxBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(FACING, Direction.DOWN).with(MECHANICAL_POWERED, 0).with(REDSTONE_POWERED, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{REDSTONE_POWERED});
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return calculatePowerLevel(this.getDefaultState().with(FACING, ctx.getSide().getOpposite()).with(REDSTONE_POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())), ctx.getSide().getOpposite(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    protected BlockState calculatePowerLevel(BlockState state, Direction facing, WorldAccess world, BlockPos pos) {
        if (state.isOf(this) && state.get(FACING) == facing) {

            BlockState newState = state.with(MECHANICAL_POWERED, 0);
            BlockState neighborState = world.getBlockState(pos.offset(state.get(FACING)));

            if (!state.get(REDSTONE_POWERED) && neighborState.isOf(BlocksRegistry.AXLE.get()) && neighborState.get(FACING) == facing && neighborState.get(AxleBlock.POWER) >= 1) {

                newState = state.with(MECHANICAL_POWERED, neighborState.get(AxleBlock.GENERATED_POWER));
            }

            world.scheduleBlockTick(pos, this, 100);
            return newState;
        }
        return state;
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(REDSTONE_POWERED);
            if (bl != world.isReceivingRedstonePower(pos)) {
                if (bl) {
                    world.scheduleBlockTick(pos, this, 4);
                } else {
                    world.setBlockState(pos, (BlockState)state.cycle(REDSTONE_POWERED), 2);
                }
            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((Boolean)state.get(REDSTONE_POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, (BlockState)state.cycle(REDSTONE_POWERED), 2);
        }
        if (state.get(MECHANICAL_POWERED) == 4 && !state.get(REDSTONE_POWERED)) {
            world.breakBlock(pos, true);
        }

    }

    private static void spawnParticles(BlockState state, WorldAccess world, BlockPos pos, float alpha) {
        Direction direction = ((Direction)state.get(FACING)).getOpposite();
        double d = (double)pos.getX() + 0.5D + 0.1D * (double)direction.getOffsetX();
        double e = (double)pos.getY() + 0.5D + 0.1D * (double)direction.getOffsetY();
        double f = (double)pos.getZ() + 0.5D + 0.1D * (double)direction.getOffsetZ();
        world.addParticle(new DustParticleEffect(DustParticleEffect.RED, alpha), d, e, f, 0.0D, 0.0D, 0.0D);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean)state.get(REDSTONE_POWERED) && random.nextFloat() < 0.25F) {
            spawnParticles(state, world, pos, 0.5F);
        }

    }

    static {
        REDSTONE_POWERED = BooleanProperty.of("redstone_powered");
    }
}
