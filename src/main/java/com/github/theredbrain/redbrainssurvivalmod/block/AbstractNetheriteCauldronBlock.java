package com.github.theredbrain.redbrainssurvivalmod.block;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.PointedDripstoneBlock;
//import net.minecraft.block.ShapeContext;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.ai.pathing.NavigationType;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.fluid.Fluids;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.function.BooleanBiFunction;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.random.Random;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.util.shape.VoxelShapes;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//
//import java.util.Map;
//
//public abstract class AbstractNetheriteCauldronBlock extends Block {
//    private static final VoxelShape RAYCAST_SHAPE = createCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
//    protected static final VoxelShape OUTLINE_SHAPE;
//    private final Map<Item, NetheriteCauldronBehaviour> behaviorMap;
//
//    public AbstractNetheriteCauldronBlock(Settings settings, Map<Item, NetheriteCauldronBehaviour> behaviorMap) {
//        super(settings);
//        this.behaviorMap = behaviorMap;
//    }
//
//    protected double getFluidHeight(BlockState state) {
//        return 0.0D;
//    }
//
//    protected boolean isEntityTouchingFluid(BlockState state, BlockPos pos, Entity entity) {
//        return entity.getY() < (double)pos.getY() + this.getFluidHeight(state) && entity.getBoundingBox().maxY > (double)pos.getY() + 0.25D;
//    }
//
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        ItemStack itemStack = player.getStackInHand(hand);
//        NetheriteCauldronBehaviour NetheriteCauldronBehaviour = (NetheriteCauldronBehaviour)this.behaviorMap.get(itemStack.getItem());
//        return NetheriteCauldronBehaviour.interact(state, world, pos, player, hand, itemStack);
//    }
//
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return OUTLINE_SHAPE;
//    }
//
//    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
//        return RAYCAST_SHAPE;
//    }
//
//    public boolean hasComparatorOutput(BlockState state) {
//        return true;
//    }
//
//    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
//        return false;
//    }
//
//    public abstract boolean isFull(BlockState state);
//
//    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        BlockPos blockPos = PointedDripstoneBlock.getDripPos(world, pos);
//        if (blockPos != null) {
//            Fluid fluid = PointedDripstoneBlock.getDripFluid(world, blockPos);
//            if (fluid != Fluids.EMPTY && this.canBeFilledByDripstone(fluid)) {
//                this.fillFromDripstone(state, world, pos, fluid);
//            }
//
//        }
//    }
//
//    public boolean canBeFilledByDripstone(Fluid fluid) {
//        return false;
//    }
//
//    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
//    }
//
//    static {
//        OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), new VoxelShape[]{createCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), RAYCAST_SHAPE}), BooleanBiFunction.ONLY_FIRST);
//    }
//}
