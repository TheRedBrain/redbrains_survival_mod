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
//@Mixin(PumpkinBlock.class)
//public abstract class PumpkinBlockMixin extends GourdBlock {
//
//    public PumpkinBlockMixin(Settings settings) {
//        super(settings);
//    }
//
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        ItemStack itemStack = player.getStackInHand(hand);
//        if (itemStack.isIn(Tags.KNIVES)) {
//            if (!world.isClient) {
//                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D, new ItemStack(ItemsRegistry.PUMPKIN_SLICE, 4));
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
//        } else if (itemStack.isOf(Items.SHEARS)) {
//            if (!world.isClient) {
//                Direction direction = hit.getSide();
//                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
//                world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                world.setBlockState(pos, (BlockState) Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction2), 11);
//                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5D + (double)direction2.getOffsetX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction2.getOffsetZ() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
//                itemEntity.setVelocity(0.05D * (double)direction2.getOffsetX() + world.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction2.getOffsetZ() + world.random.nextDouble() * 0.02D);
//                world.spawnEntity(itemEntity);
//                itemStack.damage(1, player, (playerx) -> {
//                    playerx.sendToolBreakStatus(hand);
//                });
//                world.emitGameEvent(player, GameEvent.SHEAR, pos);
//                player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
//            }
//
//            return ActionResult.success(world.isClient);
//        } else {
//            return super.onUse(state, world, pos, player, hand, hit);
//        }
//    }
//}
