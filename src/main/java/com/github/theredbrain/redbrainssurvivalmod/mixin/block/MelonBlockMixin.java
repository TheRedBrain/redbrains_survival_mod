package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
//import com.github.theredbrain.redbrainssurvivalmod.tags.Tags;
//import net.minecraft.block.*;
//import net.minecraft.entity.ItemEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.stat.Stats;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//import org.spongepowered.asm.mixin.Mixin;
//
//@Mixin(MelonBlock.class)
//public abstract class MelonBlockMixin extends GourdBlock {
//
//    public MelonBlockMixin(Settings settings) {
//        super(settings);
//    }
//
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        ItemStack itemStack = player.getStackInHand(hand);
//        if (itemStack.isIn(Tags.KNIVES)) {
//            if (!world.isClient) {
//                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D, new ItemStack(Items.MELON_SLICE, 9));
//                itemEntity.setVelocity(0.05D + world.random.nextDouble() * 0.02D, 0.05D, 0.05D + world.random.nextDouble() * 0.02D);
//                world.spawnEntity(itemEntity);
//                itemStack.damage(1, player, (playerx) -> {
//                    playerx.sendToolBreakStatus(hand);
//                });
//                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
//                player.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
//            }
//
//            return ActionResult.success(world.isClient);
//        } else {
//            return super.onUse(state, world, pos, player, hand, hit);
//        }
//    }
//}
