package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;

public class AxleBlock extends Block {
    public static final DirectionProperty FACING;
    public static final IntProperty GENERATED_POWER;
    public static final IntProperty POWER;
    protected static final VoxelShape X_AXIS;
    protected static final VoxelShape Y_AXIS;
    protected static final VoxelShape Z_AXIS;

    public AxleBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState().with(FACING, Direction.EAST).with(GENERATED_POWER, 0).with(POWER, 0)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, GENERATED_POWER, POWER});
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);

        return switch (facing) {
            case EAST, WEST -> X_AXIS;
            case UP, DOWN -> Y_AXIS;
            default -> Z_AXIS;
        };
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState_1, WorldAccess world, BlockPos pos, BlockPos neighborPos_1) {

        if (state.get(FACING) == direction) {
            return calculatePowerLevel(state, direction, world, pos);
        } else {
            return state;
        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {

        return calculatePowerLevel(this.getDefaultState().with(FACING, ctx.getSide().getOpposite()), ctx.getSide().getOpposite(), ctx.getWorld(), ctx.getBlockPos());
    }

    private BlockState calculatePowerLevel(BlockState state, Direction facing, WorldAccess world, BlockPos pos) {

        if (state.isOf(this)) {
            int newGeneratedPower = 0;
            int newPower = 0;
            Direction.Axis axis = facing.getAxis();
            BlockState neighborState = world.getBlockState(pos.offset(facing));

            if ((neighborState.isOf(BlocksRegistry.WATER_WHEEL.get()) && neighborState.get(AbstractAxleWithPowerSourceBlock.AXIS) == axis)
                    || (neighborState.isOf(BlocksRegistry.WIND_MILL.get()) && neighborState.get(AbstractAxleWithPowerSourceBlock.AXIS) == axis)
                    || (neighborState.isOf(BlocksRegistry.VERTICAL_WIND_MILL.get()) && neighborState.get(AbstractAxleWithPowerSourceBlock.AXIS) == axis)) {
                newGeneratedPower = neighborState.get(GENERATED_POWER);
                newPower = 2;
            } else if ((neighborState.isOf(BlocksRegistry.GEAR_BOX.get()) && neighborState.get(GearBoxBlock.MECHANICAL_POWERED) == 1)
                    || (neighborState.isOf(BlocksRegistry.REDSTONE_GEAR_BOX.get()) && neighborState.get(RedstoneGearBoxBlock.MECHANICAL_POWERED) == 1 && !(neighborState.get(RedstoneGearBoxBlock.REDSTONE_POWERED)))
                    || (neighborState.isOf(BlocksRegistry.CREATIVE_MECHANICAL_SOURCE.get()))) {

                newGeneratedPower = 1;
                newPower = 1;
            } else if (neighborState.isOf(this) && neighborState.get(FACING) == facing && !(neighborState.get(POWER) == 0) && !(neighborState.get(POWER) > 3)) {

                newGeneratedPower = neighborState.get(GENERATED_POWER);
                newPower = neighborState.get(POWER) + 1;
            }
            world.getBlockTickScheduler().scheduleTick(OrderedTick.create(this, pos));
            state = state.with(GENERATED_POWER, newGeneratedPower).with(POWER, newPower);
        }
        return state;
    }

    // FIXME cycles only through 3 states
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && player.isSneaking() && player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
            BlockState newState = state.cycle(FACING);
            
            world.setBlockState(pos, calculatePowerLevel(newState, newState.get(FACING), world, pos));

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWER) == 4) {
            world.breakBlock(pos, true);
        }
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        // only gets mechanical power from the side it's facing
        FACING = Properties.FACING;
        // 0 = no power generation, 1 = stable power generation from waterwheel or windmill, 2 = breaking power generation from windmill in bad weather
        GENERATED_POWER = IntProperty.of("generated_power", 0, 2);
        // 0 = unpowered, 1 - 3 = stable power, 4 = breaking power
        POWER = IntProperty.of("power", 0,4);
        X_AXIS = Block.createCuboidShape(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
        Y_AXIS = Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        Z_AXIS = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    }
}
