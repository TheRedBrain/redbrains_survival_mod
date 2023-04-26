package com.github.theredbrain.redbrainssurvivalmod.mixin.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

    @Shadow
    @Final
    private Fluid fluid;

    public BucketItemMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        throw new AssertionError();
    }

    @Shadow
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        throw new AssertionError();
    }

    @Shadow
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        throw new AssertionError();
    }

    @Shadow
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        throw new AssertionError();
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void canNotPickUpLava(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            cir.setReturnValue(TypedActionResult.pass(itemStack));
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            cir.setReturnValue(TypedActionResult.pass(itemStack));
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
                BlockState blockState;
                if (this.fluid == Fluids.EMPTY) {
                    blockState = world.getBlockState(blockPos);
                    if (blockState.isOf(Blocks.WATER)) {
//                    if (blockState.getBlock() instanceof FluidDrainable) {
                        FluidDrainable fluidDrainable = (FluidDrainable)blockState.getBlock();
                        ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
                        if (!itemStack2.isEmpty()) {
                            user.incrementStat(Stats.USED.getOrCreateStat(this));
                            fluidDrainable.getBucketFillSound().ifPresent((sound) -> {
                                user.playSound(sound, 1.0F, 1.0F);
                            });
                            world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                            if (!world.isClient) {
                                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
                            }

                            cir.setReturnValue(TypedActionResult.success(itemStack3, world.isClient()));
                        } else {
                            cir.setReturnValue(TypedActionResult.fail(itemStack));
                        }
                    } else {
                        cir.setReturnValue(TypedActionResult.fail(itemStack));
                    }

                } else {
                    blockState = world.getBlockState(blockPos);
                    BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
                    if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
                        this.onEmptied(user, world, itemStack, blockPos3);
                        if (user instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
                        }

                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        cir.setReturnValue(TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient()));
                    } else {
                        cir.setReturnValue(TypedActionResult.fail(itemStack));
                    }
                }
            } else {
                cir.setReturnValue(TypedActionResult.fail(itemStack));
            }
        }
        cir.cancel();
    }

    @Inject(method = "placeFluid", at = @At("HEAD"), cancellable = true)
    void doNotPlaceSource(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir) {
        if (!(this.fluid instanceof FlowableFluid)) {
            cir.setReturnValue(false);
        } else {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBucketPlace(this.fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
            if (!bl2) {
                cir.setReturnValue(false);
            } else if (world.getDimension().ultrawarm() && this.fluid.isIn(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                cir.setReturnValue(true);
            } else if (block instanceof FluidFillable && this.fluid == Fluids.WATER) {
                cir.setReturnValue(false);
            } else {
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }

                if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !blockState.getFluidState().isStill()) {
                    cir.setReturnValue(false);
                } else {
                    // TODO find way to set the fluid level higher
                    if(this.fluid == Fluids.WATER) {
                        if(player.isCreative()) {
                            world.setBlockState(pos, Blocks.WATER.getDefaultState());
                        } else {
                            world.setBlockState(pos, Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 6));
                        }
                    } else if(this.fluid == Fluids.LAVA) {
                        if(player.isCreative()) {
                            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                        } else {
                            world.setBlockState(pos, Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, 6));
                        }
                    }
                    this.playEmptyingSound(player, world, pos);
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
