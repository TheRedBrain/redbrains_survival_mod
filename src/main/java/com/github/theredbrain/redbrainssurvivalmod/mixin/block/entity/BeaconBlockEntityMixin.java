package com.github.theredbrain.redbrainssurvivalmod.mixin.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.BeaconBlockEntityMixinDuck;
import com.github.theredbrain.redbrainssurvivalmod.registry.StatusEffectsRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity implements BeaconBlockEntityMixinDuck, NamedScreenHandlerFactory {

    public BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "updateLevel", at = @At("HEAD"), cancellable = true)
    private static void beaconsRequireConsistentMaterialPyramids(World world, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(checkForConsistentPyramidMaterial(world, x, y, z));
    }

    @Inject(method = "applyPlayerEffects", at = @At("HEAD"), cancellable = true)
    private static void applyPlayerEffects(World world, BlockPos pos, int beaconLevel, StatusEffect primaryEffect, StatusEffect secondaryEffect, CallbackInfo ci) {
        if (!world.isClient) {

            // effect range
            double d = 1;
            for (int i = 0; i < beaconLevel; i++) {
                d *= 2;
            }
            d *= 10;

            BlockState beaconBase = world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ()));

            // effect level
            int level = 0;
            if (beaconLevel >= 4) {
                level = 1;
            }

            // effect duration
            int duration = (9 + beaconLevel * 2) * 20;

            Box box = (new Box(pos)).expand(d).stretch(0.0D, (double)world.getHeight(), 0.0D);

            box.contains(pos.getX(), pos.getY(), pos.getZ());
            StatusEffect beaconEffect = null;
            boolean onlyPlayer = false;

            if (beaconBase.isOf(Blocks.GOLD_BLOCK)) {
                beaconEffect = StatusEffects.HASTE;
                onlyPlayer = true;
            } else if (beaconBase.isOf(Blocks.MAGMA_BLOCK)) {
                beaconEffect = StatusEffects.FIRE_RESISTANCE;
                onlyPlayer = true;
            } else if (beaconBase.isOf(Blocks.SEA_LANTERN)) {
                beaconEffect = StatusEffects.NIGHT_VISION;
                onlyPlayer = true;
            } else if (beaconBase.isOf(Blocks.COPPER_BLOCK) || beaconBase.isOf(Blocks.WAXED_COPPER_BLOCK)) {
                beaconEffect = StatusEffects.JUMP_BOOST;
                onlyPlayer = true;
            } else if (beaconBase.isOf(Blocks.LAPIS_BLOCK)) {
                beaconEffect = StatusEffectsRegistry.TRUE_SIGHT;
                onlyPlayer = true;
            } else if (beaconBase.isOf(Blocks.DIAMOND_BLOCK)) {
                beaconEffect = StatusEffectsRegistry.EXTRA_LOOTING;
                onlyPlayer = false;
            } else if (beaconBase.isOf(Blocks.EMERALD_BLOCK)) {
                beaconEffect = StatusEffectsRegistry.PLAYER_KILL;
                onlyPlayer = false;
            }

            if (beaconEffect != null && onlyPlayer) {
                List<PlayerEntity> playerList = world.getNonSpectatingEntities(PlayerEntity.class, box);
                Iterator var11 = playerList.iterator();

                PlayerEntity playerEntity;
                while (var11.hasNext()) {
                    playerEntity = (PlayerEntity) var11.next();
                    playerEntity.addStatusEffect(new StatusEffectInstance(beaconEffect, duration, level, true, false, true));
                }
            } else if (beaconEffect != null) {
                List<LivingEntity> entityList = world.getNonSpectatingEntities(LivingEntity.class, box);
                Iterator iterator = entityList.iterator();

                LivingEntity livingEntity;
                while (iterator.hasNext()) {
                    livingEntity = (LivingEntity) iterator.next();
                    if (!(livingEntity instanceof PlayerEntity)) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(beaconEffect, duration, level, true, false, false));
                    }
                }
            }
        }
        ci.cancel();
    }

    private static int checkForConsistentPyramidMaterial(World world, int x, int y, int z) {
        int i = 0;
        boolean vanity = false;
        boolean copperBeacon = false;
        BlockState beaconBase = world.getBlockState(new BlockPos(x, y-1, z));
        if(!(beaconBase.isIn(BlockTags.BEACON_BASE_BLOCKS))) {
            return i;
        }
        if (beaconBase.isIn(BlockTags.IMPERMEABLE)) {
            vanity = true;
        }
        if (beaconBase.isOf(Blocks.COPPER_BLOCK) || beaconBase.isOf(Blocks.WAXED_COPPER_BLOCK)) {
            copperBeacon = true;
        }

        Block currentBeaconType = beaconBase.getBlock();

        for(int j = 1; j <= 4; i = j++) {
            int k = y - j;
            if (k < world.getBottomY()) {
                break;
            }

            boolean bl = true;

            for(int l = x - j; l <= x + j && bl; ++l) {
                for(int m = z - j; m <= z + j; ++m) {
                    if (!(world.getBlockState(new BlockPos(l, k, m)).isOf(currentBeaconType) || (vanity && world.getBlockState(new BlockPos(l, k, m)).isIn(BlockTags.IMPERMEABLE)) || (copperBeacon && (world.getBlockState(new BlockPos(l, k, m)).isOf(Blocks.COPPER_BLOCK) || world.getBlockState(new BlockPos(l, k, m)).isOf(Blocks.WAXED_COPPER_BLOCK))))) {
                        bl = false;
                        break;
                    }
                }
            }

            if (!bl) {
                break;
            }
        }

        return i;
    }

    @Override
    public int getCryingObsidianBeaconLevel(World world, BlockPos pos) {
        int i = 0;
        if (world.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).isOf(Blocks.CRYING_OBSIDIAN)) {
            i = (checkForConsistentPyramidMaterial(world, pos.getX(), pos.getY(), pos.getZ()));
        }
        return i;
    }

    @Override
    public Text getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }
}
