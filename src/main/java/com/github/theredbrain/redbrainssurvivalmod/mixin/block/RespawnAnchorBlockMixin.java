package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.RespawnAnchorBlockMixinDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorBlockMixin implements RespawnAnchorBlockMixinDuck {

    @Shadow
    @Final
    public static IntProperty CHARGES;

    @Shadow
    private static boolean isChargeItem(ItemStack stack) {
        throw new AssertionError();
    }

    @Shadow
    public static void charge(@Nullable Entity charger, World world, BlockPos pos, BlockState state) {
        throw new AssertionError();
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void checkAlsoForPyramid(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND && !isChargeItem(itemStack) && isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
            cir.setReturnValue(ActionResult.PASS);
        } else if (isChargeItem(itemStack) && canChargeAndHasPyramid(state, world, pos)) {
            charge(player, world, pos, state);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            cir.setReturnValue(ActionResult.success(world.isClient));
        } else if ((Integer)state.get(CHARGES) == 0) {
            cir.setReturnValue(ActionResult.PASS);
        } else {
            if (!world.isClient) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
                if (serverPlayerEntity.getSpawnPointDimension() != world.getRegistryKey() || !pos.equals(serverPlayerEntity.getSpawnPointPosition())) {
                    serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), pos, 0.0F, false, true);
                    world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }

            cir.setReturnValue(ActionResult.CONSUME);
        }
    }

    private static boolean canChargeAndHasPyramid(BlockState state, World world, BlockPos pos) {
        return ((Integer)state.get(CHARGES) < 4 && hasPyramid(world, pos));
    }

    private static boolean hasPyramid(World world, BlockPos pos) {
        int i = 0;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if(!(world.getBlockState(new BlockPos(x, y-1, z)).isOf(Blocks.NETHERITE_BLOCK))) {
            return false;
        }

        for(int j = 1; j <= 4; i = j++) {
            int k = y - j;
            if (k < world.getBottomY()) {
                break;
            }

            boolean bl = true;

            for(int l = x - j; l <= x + j && bl; ++l) {
                for(int m = z - j; m <= z + j; ++m) {
                    if (!world.getBlockState(new BlockPos(l, k, m)).isOf(Blocks.NETHERITE_BLOCK)) {
                        bl = false;
                        break;
                    }
                }
            }

            if (!bl) {
                break;
            }
        }

        return i == 4;
    }

    @Override
    public boolean isStillValid(World world, BlockPos pos) {
        return hasPyramid(world, pos);
    }
}
