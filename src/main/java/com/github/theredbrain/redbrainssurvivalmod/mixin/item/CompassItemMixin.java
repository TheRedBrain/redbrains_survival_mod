package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import com.github.theredbrain.redbrainssurvivalmod.block.BlockMixinDuck;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin extends Item {

    public CompassItemMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public static boolean hasLodestone(ItemStack stack) {
        throw new AssertionError();
    }

    @Shadow
    private static Optional<RegistryKey<World>> getLodestoneDimension(NbtCompound nbt) {
        throw new AssertionError();
    }

//    public CompassItemMixin(Settings settings) {
//        super(settings);
//    }

    @Inject(method = "inventoryTick", at = @At("HEAD"), cancellable = true)
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!world.isClient) {
            if (hasLodestone(stack)) {
                NbtCompound nbtCompound = stack.getOrCreateNbt();
                if (nbtCompound.contains("LodestoneTracked") && !nbtCompound.getBoolean("LodestoneTracked")) {
                    ci.cancel();
                }

                Optional<RegistryKey<World>> optional = getLodestoneDimension(nbtCompound);
                if (optional.isPresent() && optional.get() == world.getRegistryKey() && nbtCompound.contains("LodestonePos")) {
                    BlockPos blockPos = NbtHelper.toBlockPos(nbtCompound.getCompound("LodestonePos"));
                    if (!world.isInBuildLimit(blockPos) || !((ServerWorld)world).getPointOfInterestStorage().hasTypeAt(PointOfInterestTypes.LODESTONE, blockPos) || !((BlockMixinDuck)(world.getBlockState(blockPos).getBlock())).hasPyramid(world, blockPos, Blocks.IRON_BLOCK)) {
                        nbtCompound.remove("LodestonePos");
                    }
                }
            }

        }
        ci.cancel();
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnLodeStoneBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        if (!world.getBlockState(blockPos).isOf(Blocks.LODESTONE)) {
            cir.setReturnValue(super.useOnBlock(context));
        } else if (((BlockMixinDuck)world.getBlockState(blockPos).getBlock()).hasPyramid(world, blockPos, Blocks.IRON_BLOCK)) {
            world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            boolean bl = !playerEntity.getAbilities().creativeMode && itemStack.getCount() == 1;
            if (bl) {
                ((CompassItemInvoker)this).invokeWriteNbt(world.getRegistryKey(), blockPos, itemStack.getOrCreateNbt());
            } else {
                ItemStack itemStack2 = new ItemStack(Items.COMPASS, 1);
                NbtCompound nbtCompound = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
                itemStack2.setNbt(nbtCompound);
                if (!playerEntity.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                ((CompassItemInvoker)this).invokeWriteNbt(world.getRegistryKey(), blockPos, nbtCompound);
                if (!playerEntity.getInventory().insertStack(itemStack2)) {
                    playerEntity.dropItem(itemStack2, false);
                }
            }

            cir.setReturnValue(ActionResult.success(world.isClient));
        } else {
            cir.setReturnValue(super.useOnBlock(context));
        }
    }
}
