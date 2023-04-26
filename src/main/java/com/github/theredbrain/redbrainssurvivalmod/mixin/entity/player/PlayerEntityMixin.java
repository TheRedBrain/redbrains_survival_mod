package com.github.theredbrain.redbrainssurvivalmod.mixin.entity.player;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    protected HungerManager hungerManager;

    @Shadow
    @Final
    private PlayerAbilities abilities;

    @Shadow
    public boolean canModifyBlocks() {
        throw new AssertionError();
    }

    @Shadow public abstract PlayerInventory getInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(method = "isBlockBreakingRestricted", at = @At("TAIL"), cancellable = true)
//    public void canMineRestrictsBlockBreaking(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(cir.getReturnValue() || this.getMainHandStack().getItem().canMine(world.getBlockState(pos), world, pos, ((PlayerEntity) (Object) this)));
//        cir.cancel();
//    }

    @Inject(method = "canConsume", at = @At("HEAD"), cancellable = true)
    public void canConsume(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((this.abilities.invulnerable || ignoreHunger || this.hungerManager.isNotFull()) && !this.hasStatusEffect(StatusEffects.HUNGER));
    }

//    @Inject(at = @At("RETURN"), method = "canFoodHeal", cancellable = true)
//    private void hardcoreHealing(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(false);
//    }

//    @ModifyVariable(at = @At("HEAD"), method = "damage", argsOnly = true)
//    private float takeDamage(float amount, DamageSource source, float originalAmount) {
//        Entity attacker = source.getAttacker();
//        if (attacker instanceof PlayerEntity player) {
//            ItemStack weapon = player.getMainHandStack();
//            int level = EnchantmentHelper.getLevel(EnchantmentsRegistry.BACKSTABBING.get(), weapon);
//            if (level > 0 && BackstabbingEnchantment.isLookingBehindTarget((LivingEntity)(Object)this, source.getPosition())) {
//                World world = attacker.getEntityWorld();
//                if (!world.isClient()) {
//                    world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.BLOCKS, 1.f, 1.f);
//
//                    return BackstabbingEnchantment.getBackstabbingDamagePerLevel(originalAmount, level);
//                }
//            }
//        }
//
//        return amount;
//    }
//
//    @Inject(method = "findRespawnPosition", at = @At("HEAD"), cancellable = true)
//    private static void checkIfRespawnAnchorHasPyramid(ServerWorld world, BlockPos pos, float angle, boolean forced, boolean alive, CallbackInfoReturnable<Optional<Vec3d>> cir) {
//        BlockState blockState = world.getBlockState(pos);
//        Block block = blockState.getBlock();
//        if (block instanceof RespawnAnchorBlock && (Integer)blockState.get(RespawnAnchorBlock.CHARGES) > 0 /*&& RespawnAnchorBlock.isNether(world) */&& ((RespawnAnchorBlockMixinDuck)block).isStillValid(world, pos)) {
//            Optional<Vec3d> optional = RespawnAnchorBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
//            if (!alive && optional.isPresent()) {
//                world.setBlockState(pos, (BlockState)blockState.with(RespawnAnchorBlock.CHARGES, (Integer)blockState.get(RespawnAnchorBlock.CHARGES) - 1), 3);
//            }
//
//            cir.setReturnValue(optional);
//        } else if (block instanceof BedBlock && BedBlock.isBedWorking(world)) {
//            cir.setReturnValue(BedBlock.findWakeUpPosition(EntityType.PLAYER, world, pos, angle));
//        } else if (!forced) {
//            cir.setReturnValue(Optional.empty());
//        } else {
//            boolean bl = block.canMobSpawnInside();
//            boolean bl2 = world.getBlockState(pos.up()).getBlock().canMobSpawnInside();
//            cir.setReturnValue(bl && bl2 ? Optional.of(new Vec3d((double)pos.getX() + 0.5D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D)) : Optional.empty());
//        }
//    }

//    /**
//     * Determines whether the player is able to harvest drops from the specified block state.
//     * If a block requires a special tool, it will check
//     * whether the held item is effective for that block, otherwise
//     * it returns {@code true}.
//     * @author TheRedBrain
//     * @see net.minecraft.item.Item#isSuitableFor(BlockState)
//     */
//    @Overwrite
//    public boolean canHarvest(BlockState state) {
//        return !state.isIn(Tags.NEED_TOOL_FOR_MINING) || /*(state.isToolRequired() && */this.getInventory().getMainHandStack().isSuitableFor(state)/*)*/;
//    }
}
