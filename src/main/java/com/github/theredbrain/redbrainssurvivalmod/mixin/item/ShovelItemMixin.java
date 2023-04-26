package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import com.github.theredbrain.redbrainssurvivalmod.block.CustomSlabBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.mixin.content.registry.ShovelItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    private static final Map<Block, BlockState> PATH_SLAB_STATES;
    private static final Map<Block, BlockState> PATH_DOUBLE_SLAB_STATES;
    private static final Map<Block, BlockState> PATH_WATERLOGGED_SLAB_STATES;

    @Shadow
    @Final
    protected static Map<Block, BlockState> PATH_STATES;

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void canBePlacedOnBlocks(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity playerEntity = context.getPlayer();

        BlockState blockState2 = null;
        if (blockState.isIn(BlockTags.SHOVEL_MINEABLE) && playerEntity.isSneaking() && blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            blockState2 = BlocksRegistry.PLACED_TOOL.get().getPlacementState(new ItemPlacementContext(context));
        }

        if (blockState2 != null) {
            if (!world.isClient) {
                world.setBlockState(blockPos.up(), blockState2, 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));

                PlacedToolEntity blockEntity = (PlacedToolEntity) world.getBlockEntity(blockPos.up());
                if (blockEntity != null) {
                    blockEntity.setStack(0, context.getStack().split(1));
                    context.getPlayer().getStackInHand(context.getHand()).split(1);//.getStack().decrement(1);
                }

            }
            cir.setReturnValue(ActionResult.success(world.isClient));
        } else {
            if (context.getSide() == Direction.DOWN) {
                cir.setReturnValue(ActionResult.PASS);
            } else {
                if (blockState.getBlock() instanceof CustomSlabBlock) {
                    if ((Boolean)(blockState.get(CustomSlabBlock.getType()) == SlabType.DOUBLE)) {
                        blockState2 = (BlockState)PATH_DOUBLE_SLAB_STATES.get(blockState.getBlock());
                    }
                    else if ((Boolean)blockState.get(CustomSlabBlock.getWaterlogged())) {
                        blockState2 = (BlockState)PATH_WATERLOGGED_SLAB_STATES.get(blockState.getBlock());
                    } else {
                        blockState2 = (BlockState)PATH_SLAB_STATES.get(blockState.getBlock());
                    }
                } else {
                    blockState2 = (BlockState) ShovelItemAccessor.getPathStates().get(blockState.getBlock());
                }

                BlockState blockState3 = null;
                if (blockState2 != null && (world.getBlockState(blockPos.up()).isAir() || world.getBlockState(blockPos.up()).isIn(BlockTags.FENCE_GATES))) {
                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    blockState3 = blockState2;
                } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean)blockState.get(CampfireBlock.LIT)) {
                    if (!world.isClient()) {
                        world.syncWorldEvent((PlayerEntity)null, 1009, blockPos, 0);
                    }

                    CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
                    blockState3 = (BlockState)blockState.with(CampfireBlock.LIT, false);
                }

                if (blockState3 != null) {
                    if (!world.isClient) {
                        world.setBlockState(blockPos, blockState3, 11);
                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState3));
                        if (playerEntity != null) {
                            context.getStack().damage(1, playerEntity, (p) -> {
                                p.sendToolBreakStatus(context.getHand());
                            });
                        }
                    }

                    cir.setReturnValue(ActionResult.success(world.isClient));
                } else {
                    cir.setReturnValue(ActionResult.PASS);
                }
            }
        }
        cir.cancel();
    }

    static {
        PATH_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(BlocksRegistry.GRASS_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .put(BlocksRegistry.DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .put(BlocksRegistry.PODZOL_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .put(BlocksRegistry.COARSE_DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .put(BlocksRegistry.MYCELIUM_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .put(BlocksRegistry.ROOTED_DIRT_SLAB, BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState())
                .build());
        PATH_DOUBLE_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(BlocksRegistry.GRASS_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(BlocksRegistry.DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(BlocksRegistry.PODZOL_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(BlocksRegistry.COARSE_DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(BlocksRegistry.MYCELIUM_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .put(BlocksRegistry.ROOTED_DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getType(), SlabType.DOUBLE))
                .build());
        PATH_WATERLOGGED_SLAB_STATES = Maps.newHashMap((new ImmutableMap.Builder())
                .put(BlocksRegistry.GRASS_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(BlocksRegistry.DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(BlocksRegistry.PODZOL_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(BlocksRegistry.COARSE_DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(BlocksRegistry.MYCELIUM_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .put(BlocksRegistry.ROOTED_DIRT_SLAB.get(), BlocksRegistry.DIRT_PATH_SLAB.get().getDefaultState().with(CustomSlabBlock.getWaterlogged(), true))
                .build());
    }
}
