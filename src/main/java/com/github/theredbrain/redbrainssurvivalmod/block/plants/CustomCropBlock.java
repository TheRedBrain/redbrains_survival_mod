package com.github.theredbrain.redbrainssurvivalmod.block.plants;

import com.github.theredbrain.redbrainssurvivalmod.block.AffectsVelocityOnCollision;
import com.github.theredbrain.redbrainssurvivalmod.block.CustomFarmlandBlock;
import com.github.theredbrain.redbrainssurvivalmod.util.Constants;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public abstract class CustomCropBlock extends PlantBlock implements AffectsVelocityOnCollision {

    protected static VoxelShape[] AGE_TO_SHAPE;
    public static final BooleanProperty HAS_CROP_GROWN;
    public static final BooleanProperty HAS_WEED_GROWN;
    public static final BooleanProperty POLLINATED;
    public static final IntProperty WEED_AGE;


    public CustomCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.withAge(0).with(HAS_CROP_GROWN, false).with(HAS_WEED_GROWN, false).with(POLLINATED, false).with(WEED_AGE, 0)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && player.getStackInHand(hand).isIn(ItemTags.HOES)) {
            world.setBlockState(pos, this.withAge(this.getAge(state)).with(HAS_CROP_GROWN, state.get(HAS_CROP_GROWN)).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, state.get(POLLINATED)).with(WEED_AGE, 0), 2);
        }

        return ActionResult.PASS;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(Tags.FARM_LAND) || floor.isIn(Tags.FARM_LAND_PLANTER);
    }

    public abstract IntProperty getAgeProperty();

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[(Integer)world.getBlockState(pos).get(this.getAgeProperty())];
    }

    public abstract int getMaxAge();

    protected int getAge(BlockState state) {
        return (Integer)state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return (BlockState)this.stateManager.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    // crop growth is affected by pollination from bees, fertilization of farmland and moisturized farmland
    // each of these factors doubles the chance of the crop growing
    // growth is paused, when the weed is present
    //TODO
    // implement season dependent growth for all crops and growing plants
    // maybe some stages can only be reached in certain seasons
    // no growth at all in winter, maybe even decay
    // maybe growth is not affected by biomes but by temperature and maybe downfall
    // change chance based growing completely to a daily basis
    // more internal stages
    // being on moist and/or fertilized ground, or being pollinated grows more stages at once
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        if (world.isSkyVisible(pos) && world.getBaseLightLevel(pos, 0) >= 9 && blockState.isIn(Tags.FARM_LAND)) {
            int age = this.getAge(state);
            float moisture = getAvailableMoisture(world, pos);
            boolean fertilizedFarmland = (blockState.isIn(Tags.FERTILIZABLE_FARM_LAND) && blockState.get(CustomFarmlandBlock.FERTILIZED) || blockState.isIn(Tags.NON_FERTILIZABLE_FARM_LAND));

            // crop growing
            if (age < this.getMaxAge() && state.get(WEED_AGE) == 0 && !world.isNight() && !state.get(HAS_CROP_GROWN)) {
                if (random.nextInt((int)(((fertilizedFarmland && state.get(POLLINATED)) ? 50.0F : (fertilizedFarmland || state.get(POLLINATED)) ? 100.0F : 200.0F) / moisture) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(age + 1).with(HAS_CROP_GROWN, true).with(HAS_WEED_GROWN, state.get(HAS_WEED_GROWN)).with(POLLINATED, false).with(WEED_AGE, state.get(WEED_AGE)), 2);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
                }
            } else if (world.isNight() && state.get(HAS_CROP_GROWN)) {
                world.setBlockState(pos, state.with(HAS_CROP_GROWN, false), 2);
            }

            // weed growing
            if (!blockState.isIn(Tags.NO_WEED_FARM_LAND) && world.isNight() && !state.get(HAS_WEED_GROWN)) {
                int j = state.get(WEED_AGE);
                if (j < 3) {
                    if (random.nextInt(25) == 0) {
                        world.setBlockState(pos, state.with(HAS_WEED_GROWN, true).with(WEED_AGE, state.get(WEED_AGE) + 1), 2);
                    }
                } else {
                    world.setBlockState(pos, BlocksRegistry.WEED.get().getDefaultState().with(WeedBlock.AGE, 4));
                }
            } else if (!world.isNight() && state.get(HAS_WEED_GROWN)) {
                world.setBlockState(pos, state.with(HAS_WEED_GROWN, false));
            }
        }

    }

    protected static float getAvailableMoisture(BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(Tags.FARM_LAND) && blockState.get(FarmlandBlock.MOISTURE) >= 0) {
            f = 2.0F;
        }

        return f;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }

        super.onEntityCollision(state, world, pos, entity);
    }



    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        BlockState blockState = world.getBlockState(pos.down());
        if (!(newState.isOf(this) || newState.isOf(BlocksRegistry.WEED.get())) && blockState.isIn(Tags.FARM_LAND)) {
            if (blockState.isOf(BlocksRegistry.FARMLAND.get())) {
                world.setBlockState(pos.down(), BlocksRegistry.LOOSE_DIRT.get().getDefaultState());
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    protected abstract ItemConvertible getSeedsItem();

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeedsItem());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{HAS_CROP_GROWN, HAS_WEED_GROWN, POLLINATED, WEED_AGE});
    }

    @Override
    public float getVelocityMultiplierOnCollision() {
        return Constants.VELOCITY_MULTIPLYER_0_8;
    }

    static {
        HAS_CROP_GROWN = BooleanProperty.of("has_crop_grown");
        HAS_WEED_GROWN = BooleanProperty.of("has_weed_grown");
        POLLINATED = BooleanProperty.of("pollinated");
        WEED_AGE = IntProperty.of("weed_age", 0, 3);
    }
}
