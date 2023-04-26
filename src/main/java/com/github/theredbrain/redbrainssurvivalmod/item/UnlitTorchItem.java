package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class UnlitTorchItem extends VerticallyAttachableBlockItem {

    protected final Item litVariant;

    public UnlitTorchItem(Item litVariant, Block standingBlock, Block wallBlock, Settings settings, Direction verticalAttachmentDirection) {
        super(standingBlock, wallBlock, settings, verticalAttachmentDirection);
        this.litVariant = litVariant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos;
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity != null && ((blockState.isIn(Tags.CAN_LIT_TORCHES) && (blockState.getOrEmpty(Properties.LIT).isEmpty() || blockState.get(Properties.LIT))) || (world.getFluidState(blockPos.offset(context.getSide())).isOf(Fluids.LAVA)))) {
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }
            itemStack.decrement(1);
            playerEntity.getInventory().offer(this.litVariant.getDefaultStack(), true);
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}
