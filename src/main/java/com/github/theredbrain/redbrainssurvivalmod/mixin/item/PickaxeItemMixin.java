package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PickaxeItem.class)
public class PickaxeItemMixin {

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState1 = world.getBlockState(blockPos);
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState2 = null;
        if (blockState1.isIn(BlockTags.PICKAXE_MINEABLE) && playerEntity.isSneaking() && blockState1.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
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
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }
}
