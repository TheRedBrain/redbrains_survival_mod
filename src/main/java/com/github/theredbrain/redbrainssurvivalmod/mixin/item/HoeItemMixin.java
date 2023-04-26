package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.minecraft.item.HoeItem.createTillAction;
import static net.minecraft.item.HoeItem.createTillAndDropAction;

@Mixin(HoeItem.class)
public class HoeItemMixin {

    @Shadow
    @Final
    @Mutable
    protected static Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Pair.of(HoeItem::canTillFarmland, createTillAction(BlocksRegistry.LOOSE_DIRT.get().getDefaultState())), Blocks.DIRT_PATH, Pair.of(HoeItem::canTillFarmland, createTillAction(BlocksRegistry.LOOSE_DIRT.get().getDefaultState())), Blocks.DIRT, Pair.of(HoeItem::canTillFarmland, createTillAction(BlocksRegistry.LOOSE_DIRT.get().getDefaultState())), Blocks.COARSE_DIRT, Pair.of(HoeItem::canTillFarmland, createTillAction(Blocks.DIRT.getDefaultState())), Blocks.ROOTED_DIRT, Pair.of((itemUsageContext) -> {
        return true;
    }, createTillAndDropAction(BlocksRegistry.LOOSE_DIRT.get().getDefaultState(), Items.HANGING_ROOTS)), BlocksRegistry.LOOSE_DIRT.get(), Pair.of(HoeItem::canTillFarmland, createTillAction(BlocksRegistry.FARMLAND.get().getDefaultState()))));

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void canBePlacedOnBlocks(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState1 = world.getBlockState(blockPos);
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = (Pair)TILLING_ACTIONS.get(world.getBlockState(blockPos).getBlock());

        BlockState blockState2 = null;
        if ((blockState1.isIn(BlockTags.SHOVEL_MINEABLE) || blockState1.isIn(BlockTags.HOE_MINEABLE)) && playerEntity.isSneaking() && blockState1.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            blockState2 = BlocksRegistry.PLACED_TOOL.get().getPlacementState(new ItemPlacementContext(context));
        }

        if (blockState2 != null) {
            if (!world.isClient) {
                world.setBlockState(blockPos.up(), blockState2, 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));

                PlacedToolEntity blockEntity = (PlacedToolEntity) world.getBlockEntity(blockPos.up());
                if (blockEntity != null) {
                    blockEntity.setStack(0, context.getStack().split(1));
                    context.getPlayer().getStackInHand(context.getHand()).split(1);
                }

            }
            cir.setReturnValue(ActionResult.success(world.isClient));
        } else {
            if (pair == null) {
                cir.setReturnValue(ActionResult.PASS);
            } else {
                Predicate<ItemUsageContext> predicate = (Predicate)pair.getFirst();
                Consumer<ItemUsageContext> consumer = (Consumer)pair.getSecond();
                if (predicate.test(context)) {
                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isClient) {
                        consumer.accept(context);
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

}
