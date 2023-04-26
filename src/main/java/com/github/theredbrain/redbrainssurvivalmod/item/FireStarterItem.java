package com.github.theredbrain.redbrainssurvivalmod.item;

import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SuspiciousSandBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FireStarterItem extends Item {

    private final int useTime;
    public FireStarterItem(int useTime, Settings settings) {
        super(settings);
        this.useTime = useTime;
    }

    // TODO make using not instantly
    // TODO implement chance for failure


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity != null) {
            playerEntity.setCurrentHand(context.getHand());
        }
        return ActionResult.CONSUME;
    }

    /*@Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos;
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
        if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) { // TODO torches, lanterns
//            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
//            world.setBlockState(blockPos, (BlockState)blockState.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
//            world.emitGameEvent((Entity)playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
//            if (playerEntity != null) {
//                context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
//            }
//            return ActionResult.success(world.isClient());
            return ActionResult.CONSUME;
        }
//        BlockPos blockPos2 = blockPos.offset(context.getSide());
//        if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getHorizontalPlayerFacing())) {
//            world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
//            BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
//            world.setBlockState(blockPos2, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
//            world.emitGameEvent((Entity)playerEntity, GameEvent.BLOCK_PLACE, blockPos);
//            ItemStack itemStack = context.getStack();
//            if (playerEntity instanceof ServerPlayerEntity) {
//                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos2, itemStack);
//                itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
//            }
//            return ActionResult.success(world.isClient());
//        }
        return ActionResult.FAIL;
    }*/

//    @Override
//    public UseAction getUseAction(ItemStack stack) {
//        return UseAction.BRUSH; // TODO new UseAction FIRE_STARTER_USE
//    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.useTime;
    }

    @Override
    public void usageTick(World world, LivingEntity user2, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks < 0 || !(user2 instanceof PlayerEntity)) {
            user2.stopUsingItem();
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user2;
        BlockHitResult blockHitResult = Item.raycast(world, playerEntity, RaycastContext.FluidHandling.NONE);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockHitResult.getType() == HitResult.Type.MISS || (blockHitResult.getType() == HitResult.Type.BLOCK && !blockState.isIn(Tags.IGNITABLE_BLOCKS))) {
            user2.stopUsingItem();
            return;
        }
        int i = this.getMaxUseTime(stack) - remainingUseTicks + 1;
        if (i == 1 || i % 10 == 0) {
            BlockState blockState2 = world.getBlockState(blockPos);
            if (CampfireBlock.canBeLit(blockState2) || CandleBlock.canBeLit(blockState2) || CandleCakeBlock.canBeLit(blockState2)) { // TODO torches, lanterns
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                world.setBlockState(blockPos, (BlockState)blockState2.with(Properties.LIT, true), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
                world.emitGameEvent((Entity)playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
                if (!world.isClient()/* && blockState.isOf(Blocks.SUSPICIOUS_SAND) && (blockEntity = world.getBlockEntity(blockPos)) instanceof SuspiciousSandBlockEntity && (bl = (suspiciousSandBlockEntity = (SuspiciousSandBlockEntity)blockEntity).brush(world.getTime(), playerEntity, blockHitResult.getSide()))*/) {
                    stack.damage(1, user2, user -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
//                if (playerEntity != null) {
//                    context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
//                }
//            return ActionResult.success(world.isClient());
//                return ActionResult.CONSUME;
            }
//            this.addDustParticles(world, blockHitResult, blockState, user2.getRotationVec(0.0f));
//            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_BRUSH_BRUSHING, SoundCategory.PLAYERS);
//            if (!world.isClient() && blockState.isOf(Blocks.SUSPICIOUS_SAND)/* && (blockEntity = world.getBlockEntity(blockPos)) instanceof SuspiciousSandBlockEntity && (bl = (suspiciousSandBlockEntity = (SuspiciousSandBlockEntity)blockEntity).brush(world.getTime(), playerEntity, blockHitResult.getSide()))*/) {
//                stack.damage(1, user2, user -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
//            }
        }
    }
//    // TODO
//    public void addDustParticles(World world, BlockHitResult hitResult, BlockState state, Vec3d userRotation) {
//        double d = 3.0;
//        int i = world.getRandom().nextBetweenExclusive(7, 12);
//        BlockStateParticleEffect blockStateParticleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, state);
//        Direction direction = hitResult.getSide();
//        BrushItem.DustParticlesOffset dustParticlesOffset = BrushItem.DustParticlesOffset.fromSide(userRotation, direction);
//        Vec3d vec3d = hitResult.getPos();
//        for (int j = 0; j < i; ++j) {
//            world.addParticle(blockStateParticleEffect, vec3d.x - (double)(direction == Direction.WEST ? 1.0E-6f : 0.0f), vec3d.y, vec3d.z - (double)(direction == Direction.NORTH ? 1.0E-6f : 0.0f), dustParticlesOffset.xd() * 3.0 * world.getRandom().nextDouble(), 0.0, dustParticlesOffset.zd() * 3.0 * world.getRandom().nextDouble());
//        }
//    }
//
//    record DustParticlesOffset(double xd, double yd, double zd) {
//        private static final double field_42685 = 1.0;
//        private static final double field_42686 = 0.1;
//
//        public static BrushItem.DustParticlesOffset fromSide(Vec3d userRotation, Direction side) {
//            double d = 0.0;
//            return switch (side) {
//                default -> throw new IncompatibleClassChangeError();
//                case Direction.DOWN -> new BrushItem.DustParticlesOffset(-userRotation.getX(), 0.0, userRotation.getZ());
//                case Direction.UP -> new BrushItem.DustParticlesOffset(userRotation.getZ(), 0.0, -userRotation.getX());
//                case Direction.NORTH -> new BrushItem.DustParticlesOffset(1.0, 0.0, -0.1);
//                case Direction.SOUTH -> new BrushItem.DustParticlesOffset(-1.0, 0.0, 0.1);
//                case Direction.WEST -> new BrushItem.DustParticlesOffset(-0.1, 0.0, -1.0);
//                case Direction.EAST -> new BrushItem.DustParticlesOffset(0.1, 0.0, 1.0);
//            };
//        }
//    }

//    @Override
//    public ActionResult useOnBlock(ItemUsageContext context) {
//        World world = context.getWorld();
//        BlockPos blockPos = context.getBlockPos();
//        BlockState blockState = world.getBlockState(blockPos);
//        boolean bl = false;
//        boolean success = false;
//        if(Random.create().nextInt(10) <= 0) {
//            success = true;
//        }
//        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
//            blockPos = blockPos.offset(context.getSide());
//            if (AbstractFireBlock.canPlaceAt(world, blockPos, context.getSide())) {
//                this.playUseSound(world, blockPos, success);
//                if (success) {
//                    world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
//                    world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
//                }
//                bl = true;
//            }
//        } else {
//            this.playUseSound(world, blockPos, success);
//            if (success) {
//                world.setBlockState(blockPos, (BlockState) blockState.with(Properties.LIT, true));
//                world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
//            }
//            bl = true;
//        }
//
//        if (bl) {
//            if (context.getPlayer() != null) {
//                context.getStack().damage(1, context.getPlayer(), (player) -> {
//                    player.sendToolBreakStatus(context.getHand());
//                });
//            }
//            return ActionResult.success(world.isClient);
//        } else {
//            return ActionResult.FAIL;
//        }
//    }

//    private void playUseSound(World world, BlockPos pos, Boolean success) {
//        Random random = world.getRandom();
//        if (success) {
//            world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
//        } else {
//            world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
//        }
//    }
}
