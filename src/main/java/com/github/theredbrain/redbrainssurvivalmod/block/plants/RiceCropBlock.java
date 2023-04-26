package com.github.theredbrain.redbrainssurvivalmod.block.plants;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.*;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.fluid.FluidState;
//import net.minecraft.fluid.Fluids;
//import net.minecraft.item.ItemPlacementContext;
//import net.minecraft.item.ItemStack;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.BooleanProperty;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Properties;
//import net.minecraft.tag.FluidTags;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.WorldAccess;
//import net.minecraft.world.WorldView;
//import net.minecraft.world.tick.OrderedTick;
//import org.jetbrains.annotations.Nullable;
//
//public class RiceCropBlock extends PlantBlock implements FluidFillable {
//
//    public static final IntProperty AGE;
//    public static final int MAX_AGE = 3;
//    public static final BooleanProperty POLLINATED;
//    private static final BooleanProperty SUPPORTING;
//
//    private static final VoxelShape[] AGE_TO_SHAPE;
//
//    public RiceCropBlock(Settings settings) {
//        super(settings);
//        setDefaultState(getStateManager().getDefaultState().with(AGE, 0).with(POLLINATED, false).with(SUPPORTING, false));
//    }
//
//    @Override
//    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
//        return false;
//    }
//
//    @Override
//    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
//        return false;
//    }
//
//    @Override
//    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//        FluidState fluid = world.getFluidState(pos);
//        return super.canPlaceAt(state, world, pos) && fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8;
//    }
//
//    @Override
//    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
//        return super.canPlantOnTop(floor, world, pos) || floor.isOf(BlocksRegistry.RICH_SOIL);
//    }
//
//    @Override
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//        super.appendProperties(builder);
//        builder.add(AGE, POLLINATED, SUPPORTING);
//    }
//
//    @Override
//    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
//        return new ItemStack(BlocksRegistry.RICE_CROP);
//    }
//
//    @Nullable
//    @Override
//    public BlockState getPlacementState(ItemPlacementContext context) {
//        FluidState fluid = context.getWorld().getFluidState(context.getBlockPos());
//        return fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8 ? super.getPlacementState(context) : null;
//    }
//
//    @Override
//    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos,
//            BlockPos posFrom) {
//        BlockState superState = super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
//        if (!superState.isAir()) {
//            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
////            if (direction == Direction.UP) {
////                return superState.with(SUPPORTING, isSupportingRiceUpper(newState));
////            }
//        }
//
//        return superState;
//    }
//
//    @Override
//    public FluidState getFluidState(BlockState state) {
//        return Fluids.WATER.getStill(false);
//    }
//
//    @Override
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//
//        if (world.isSkyVisible(pos) && world.getBaseLightLevel(pos.up(), 0) >= 9) {
//            int i = this.getAge(state);
//            if (i < MAX_AGE - 1) {
//                if (random.nextInt((int)(state.get(POLLINATED) ? 25.0F : 50.0F) + 1) == 0) {
//                    world.setBlockState(pos, this.withAge(i + 1).with(POLLINATED, false), 2);
//                }
//            } else if (i == MAX_AGE - 1) {
//                if (random.nextInt((int)(state.get(POLLINATED) ? 25.0F : 50.0F) + 1) == 0 && BlocksRegistry.RICE_UPPER_CROP.withAge(0).canPlaceAt(world, pos.up())) {
//                    world.setBlockState(pos, this.withAge(i + 1).with(POLLINATED, false), 2);
//                    world.setBlockState(pos.up(), BlocksRegistry.WHEAT_UPPER_CROP.withAge(0).with(CustomUpperCropBlock.POLLINATED, false));
//                }
//            }
//        }
//    }
//
//    @Override
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return AGE_TO_SHAPE[getAge(state)];
//    }
//
//    public BlockState withAge(int age) {
//        return getDefaultState().with(AGE, age);
//    }
//
//    protected int getAge(BlockState state) {
//        return state.get(AGE);
//    }
//
//    static {
//        AGE = Properties.AGE_3;
//        POLLINATED = BooleanProperty.of("pollinated");
//        SUPPORTING = BooleanProperty.of("supporting");
//        AGE_TO_SHAPE = new VoxelShape[] {
//                Block.createCuboidShape(3.d, .0d, 3.d, 13.d, 8.d, 13.d),
//                Block.createCuboidShape(3.d, .0d, 3.d, 13.d, 10.d, 13.d),
//                Block.createCuboidShape(2.d, .0d, 2.d, 14.d, 12.d, 14.d),
//                Block.createCuboidShape(1.d, .0d, 1.d, 15.d, 16.d, 15.d)
//        };
//    }
//}