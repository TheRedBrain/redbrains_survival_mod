package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.block.BrickOvenBlock;
import com.github.theredbrain.redbrainssurvivalmod.block.LightableByFireStarters;
import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class CustomTorchItem extends VerticallyAttachableBlockItem {

    private static final int MAX_BURN_TIME = 24000;
    protected final boolean burnsOut;
    protected final boolean isCrude;

    public CustomTorchItem(boolean burnsOut, boolean isCrude, Block standingBlock, Block wallBlock, Settings settings, Direction verticalAttachmentDirection) {
        super(standingBlock, wallBlock, settings, verticalAttachmentDirection);
        this.burnsOut = burnsOut;
        this.isCrude = isCrude;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return isCrude && getBurnTime(stack) > 0;
    }

    public int getItemBarStep(ItemStack stack) {
        return Math.round(13.0f - (float)CustomTorchItem.getBurnTime(stack) * 13.0f / MAX_BURN_TIME );
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float f = Math.max(0.0f, (MAX_BURN_TIME - (float)CustomTorchItem.getBurnTime(stack)) / MAX_BURN_TIME);
        return MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }
        if (entity.isSubmergedInWater()) {
            int i = stack.getCount();
            stack.decrement(i);
            if (!this.isCrude && entity instanceof PlayerEntity) {
                ItemStack itemStack = ItemsRegistry.TORCH_UNLIT.get().getDefaultStack();
                itemStack.setCount(i);
                ((PlayerEntity) entity).getInventory().offer(itemStack, true);
            }
        }
        if (burnsOut && entity instanceof PlayerEntity) {
            int burnTime = CustomTorchItem.getBurnTime(stack);
            if (burnTime < MAX_BURN_TIME) {
                CustomTorchItem.setBurnTime(stack);
            } else {
                stack.decrement(1);
            }
        }
    }

    public static int getBurnTime(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.contains("BurnTime", NbtElement.INT_TYPE) ? nbt.getInt("BurnTime") : 0;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity != null) {
            playerEntity.setCurrentHand(context.getHand());
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (BrickOvenBlock.canBeLit(state)) {
//            if (state instanceof LightableByFireStarters && ((LightableByFireStarters)state.getBlock()).tryLighting(world, state, pos)) {
                world.playSound(playerEntity, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                world.setBlockState(pos, (BlockState)state.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                world.emitGameEvent((Entity)playerEntity, GameEvent.BLOCK_CHANGE, pos);
                return ActionResult.success(world.isClient);
            }
        }
        return super.useOnBlock(context);
    }

    public static void setBurnTime(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("BurnTime", NbtElement.INT_TYPE)) {
            nbtCompound.putInt("BurnTime", 0);
        }
        int oldBurnTime = nbtCompound.getInt("BurnTime");
        nbtCompound.putInt("BurnTime", oldBurnTime + 1);
    }

    public static void setBurnTime(ItemStack stack, int burnTime) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("BurnTime", NbtElement.INT_TYPE)) {
            nbtCompound.putInt("BurnTime", 0);
        }
        nbtCompound.putInt("BurnTime", burnTime);
    }
}
