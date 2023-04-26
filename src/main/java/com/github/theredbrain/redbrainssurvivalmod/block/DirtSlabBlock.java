package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.Iterator;

public class DirtSlabBlock extends CustomSlabBlock {
    private static final VoxelShape BOTTOM_SHAPE;
    private static final VoxelShape FULL_SHAPE;
    public DirtSlabBlock(Settings settings) {
        super(settings);
    }

//    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
//        BlockPos blockPos = pos.up();
//        BlockState blockState = world.getBlockState(blockPos);
//        if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
//            return true;
//        } else if (blockState.getFluidState().getLevel() == 8) {
//            return false;
//        } else if ((state.isOf(BlocksRegistry.DIRT_SLAB) || state.isOf(BlocksRegistry.GRASS_SLAB)) && state.get(WATERLOGGED)) {
//            return false;
//        } else {
//            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
//            return i < world.getMaxLightLevel();
//        }
//    }

//    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
//        BlockPos blockPos = pos.up();
//        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
//    }
//
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        if (!canSurvive(state, world, pos)) {
//            world.setBlockState(pos, BlocksRegistry.DIRT_SLAB.getDefaultState().with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)));
//        } else {
//            if (world.getLightLevel(pos.up()) >= 9) {
////                BlockState fullBlockState = Blocks.GRASS_BLOCK.getDefaultState();
//                BlockState grassSlabBlockState = BlocksRegistry.GRASS_SLAB.getDefaultState();
//                BlockState myceliumSlabBlockState = BlocksRegistry.MYCELIUM_SLAB.getDefaultState();
//
//                for(int i = 0; i < 4; ++i) {
//                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
//
//                    if ((world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) || (world.getBlockState(blockPos).isOf(BlocksRegistry.GRASS_SLAB))) && canSpread(grassSlabBlockState, world, pos)) {
//                        world.setBlockState(pos, (BlockState)grassSlabBlockState.with(TYPE, world.getBlockState(pos).get(TYPE)).with(SnowyBlock.SNOWY, world.getBlockState(pos.up()).isOf(Blocks.SNOW) && world.getBlockState(pos).get(TYPE) == SlabType.DOUBLE));
////                    } else if (world.getBlockState(blockPos).isOf(RedBrainsSurvivalMod.DIRT_SLAB) && !(world.getBlockState(blockPos).get(WATERLOGGED)) && canSpread(slabBlockState, world, blockPos)) {
////                        world.setBlockState(blockPos, (BlockState)slabBlockState.with(TYPE, world.getBlockState(blockPos).get(TYPE)).with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW) && world.getBlockState(blockPos).get(TYPE) == SlabType.DOUBLE));
//                    } else if ((world.getBlockState(blockPos).isOf(Blocks.MYCELIUM) || (world.getBlockState(blockPos).isOf(BlocksRegistry.MYCELIUM_SLAB))) && canSpread(myceliumSlabBlockState, world, pos)) {
//                        world.setBlockState(pos, (BlockState) myceliumSlabBlockState.with(TYPE, world.getBlockState(pos).get(TYPE)).with(SnowyBlock.SNOWY, world.getBlockState(pos.up()).isOf(Blocks.SNOW) && world.getBlockState(pos).get(TYPE) == SlabType.DOUBLE));
//                    }
//                }
//            }
//
//        }
//    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        loosenNeighboringDirt(world, pos);
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.spawnBreakParticles(world, player, pos, state);

        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
        if (!itemStack.isEmpty() && (itemStack.isIn(Tags.PROPER_DIGGING_TOOLS))) {
            loosenNeighboringDirt(world, pos);
        } else if (!itemStack.isEmpty()) {
            boolean bl = false;
            if (itemStack.hasEnchantments()) {
                for (int i = 0; i <= itemStack.getEnchantments().size(); i++) {
                    if (itemStack.getEnchantments().get(i).equals((Object) Enchantments.SILK_TOUCH)) {
                        bl = true;
                    }
                }
            }
            if (!bl) {
                loosenNeighboringDirt(world, pos);
            }
        } else {
            loosenNeighboringDirt(world, pos);
        }

        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, state));
    }

    private void loosenNeighboringDirt(World world, BlockPos pos) {
        Iterator var2 = BlockPos.iterate(pos.add(-1, 0, -1), pos.add(1, 1, 1)).iterator();

        do {
            if (!var2.hasNext()) {
                break;
            }

            BlockPos blockPos = (BlockPos)var2.next();
            if (world.getBlockState(blockPos).isOf(Blocks.DIRT)) {
                world.setBlockState(blockPos, BlocksRegistry.LOOSE_DIRT.get().getDefaultState());
            } else if (world.getBlockState(blockPos).isOf(BlocksRegistry.DIRT_SLAB.get())) {
                world.setBlockState(blockPos, BlocksRegistry.LOOSE_DIRT_SLAB.get().getDefaultState());
            }
        } while(var2.hasNext());
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TYPE) == SlabType.DOUBLE) {
            return FULL_SHAPE;
        } else {
            return BOTTOM_SHAPE;
        }
    }

    static {
        TYPE = Properties.SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        FULL_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    }
}
