package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import com.google.common.collect.BiMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Shadow
    private Optional<BlockState> getStrippedState(BlockState state) {
        throw new AssertionError();
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void canBePlacedOnBlocks(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState1 = world.getBlockState(blockPos);
        Optional<BlockState> optional = this.getStrippedState(blockState1);
        Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(blockState1);
        Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(blockState1.getBlock())).map((block) -> block.getStateWithProperties(blockState1));
        ItemStack itemStack = context.getStack();
        Optional<BlockState> optional4 = Optional.empty();

        BlockState blockState2 = null;
        if (blockState1.isIn(BlockTags.AXE_MINEABLE) && playerEntity.isSneaking() && blockState1.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
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
            if (optional.isPresent()) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                optional4 = optional;
            } else if (optional2.isPresent()) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(playerEntity, 3005, blockPos, 0);
                optional4 = optional2;
            } else if (optional3.isPresent()) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(playerEntity, 3004, blockPos, 0);
                optional4 = optional3;
            }

            if (optional4.isPresent()) {
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);
                }

                world.setBlockState(blockPos, (BlockState) optional4.get(), 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, (BlockState) optional4.get()));
                if (playerEntity != null) {
                    itemStack.damage(1, playerEntity, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }

                cir.setReturnValue(ActionResult.success(world.isClient));
            }
            cir.setReturnValue(ActionResult.PASS);
        }
        cir.cancel();
    }
}
