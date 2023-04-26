package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class LooseDirtBlock extends FallingBlock {

    public LooseDirtBlock(Settings settings) {
        super(settings);
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        CustomDirtBlock.loosenNeighboringDirt(world, pos);
    }

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (tool.isIn(ItemTags.HOES)) {
            world.setBlockState(pos, BlocksRegistry.FARMLAND.get().getDefaultState(), NOTIFY_ALL);
        } else if (!tool.isIn(Tags.PROPER_DIGGING_TOOLS)) {
            CustomDirtBlock.loosenNeighboringDirt(world, pos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (LooseDirtBlock.turnsToMudOnAnySide(world, pos) && random.nextInt(5) == 0) {
            world.setBlockState(pos, BlocksRegistry.MUD.get().getDefaultState(), Block.NOTIFY_ALL);
        }
    }

    public static boolean turnsToMudOnAnySide(BlockView world, BlockPos pos) {
        boolean bl = false;
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction == Direction.DOWN && !LooseDirtBlock.turnsToMudIn(blockState)) continue;
            mutable.set((Vec3i)pos, direction);
            blockState = world.getBlockState(mutable);
            if (!LooseDirtBlock.turnsToMudIn(blockState) || blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) continue;
            bl = true;
            break;
        }
        return bl;
    }

    private static boolean turnsToMudIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

}
