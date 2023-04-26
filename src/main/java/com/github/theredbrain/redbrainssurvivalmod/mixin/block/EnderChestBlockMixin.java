package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.block.entity.BeaconBlockEntityMixinDuck;
//import com.github.theredbrain.redbrainssurvivalmod.components.RedBrainsSurvivalModComponents;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.block.EnderChestBlock;
//import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.block.entity.EnderChestBlockEntity;
//import net.minecraft.entity.mob.PiglinBrain;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.inventory.EnderChestInventory;
//import net.minecraft.screen.GenericContainerScreenHandler;
//import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
//import net.minecraft.stat.Stats;
//import net.minecraft.state.property.DirectionProperty;
//import net.minecraft.text.Text;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.List;
//
//@Mixin(EnderChestBlock.class)
//public class EnderChestBlockMixin {
//    /*
//    FIXME
//     - nbt data is not saved correctly
//     - worldSharedEnderChestInventory can only be accessed by multiple players simultanously when they are all in the same dimension
//     - animation (open/close) is bugged
//    */
//
//    @Shadow
//    @Final
//    static Text CONTAINER_NAME;
//
//    @Shadow
//    @Final
//    static DirectionProperty FACING;
//
//    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
//    private void accessCorrectInventory(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
//
//        if (world.isClient) {
//            cir.setReturnValue(ActionResult.SUCCESS);
//        } else {
//            BlockPos blockPos = pos.up();
//
//            if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
//                cir.setReturnValue(ActionResult.success(world.isClient));
//            } else {
//
//                int beaconLevel = 0;
//
//                int beaconX = pos.getX();
//                int beaconY = pos.getY();
//                int beaconZ = pos.getZ();
//
//                for (int i = 0; i <= 3; i++) {
//                    beaconY = pos.getY() + i;
//                    switch (state.get(FACING)) {
//                        case NORTH:
//                            beaconZ = pos.getZ() + 1 + i;
//                            break;
//                        case EAST:
//                            beaconX = pos.getX() - 1 - i;
//                            break;
//                        case SOUTH:
//                            beaconZ = pos.getZ() - 1 - i;
//                            break;
//                        case WEST:
//                            beaconX = pos.getX() + 1 + i;
//                            break;
//                        default:
//                            break;
//                    }
//                    BlockPos beaconPos = new BlockPos(beaconX, beaconY, beaconZ);
//                    if (world.getBlockState(beaconPos).isOf(Blocks.BEACON) && (((BeaconBlockEntityMixinDuck) (world.getBlockEntity(beaconPos))).getCryingObsidianBeaconLevel(world, beaconPos) >= i + 1)) {
//                        beaconLevel = i + 1;
//                        break;
//                    }
//                }
//                EnderChestInventory enderChestInventory;
//
//                /*
//                List<EnderChestInventory> sharedEnderChestInventories = RedBrainsSurvivalModComponents.SHARED_ENDER_CHEST_INVENTORIES.get(world.getLevelProperties()).getSharedEnderChestInventories();
//
//                switch (beaconLevel) {
//                    case 1:
//                        enderChestInventory = world.getRegistryKey() == World.OVERWORLD ? sharedEnderChestInventories.get(1) : world.getRegistryKey() == World.NETHER ? sharedEnderChestInventories.get(3) : sharedEnderChestInventories.get(5);
//                        break;
//                    case 2:
//                        enderChestInventory = world.getRegistryKey() == World.OVERWORLD ? sharedEnderChestInventories.get(2) : world.getRegistryKey() == World.NETHER ? sharedEnderChestInventories.get(4) : sharedEnderChestInventories.get(6);
//                        break;
//                    case 3:
//                        enderChestInventory = sharedEnderChestInventories.get(0);
//                        break;
//                    case 4:
//                        enderChestInventory = player.getEnderChestInventory();
//                        break;
//                    default:
//                        enderChestInventory = null;
//                        break;
//
//                }*/
//                // temporary
//                enderChestInventory = player.getEnderChestInventory();
//
//                BlockEntity blockEntity = world.getBlockEntity(pos);
//                if (enderChestInventory != null && blockEntity instanceof EnderChestBlockEntity) {
//                    EnderChestBlockEntity enderChestBlockEntity = (EnderChestBlockEntity) blockEntity;
//                    enderChestInventory.setActiveBlockEntity(enderChestBlockEntity);
//                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, playerx) -> {
//                        return GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, enderChestInventory);
//                    }, CONTAINER_NAME));
//                    player.incrementStat(Stats.OPEN_ENDERCHEST);
//                    PiglinBrain.onGuardedBlockInteracted(player, true);
//                    cir.setReturnValue(ActionResult.CONSUME);
////            }
//                } else {
//                    cir.setReturnValue(ActionResult.success(world.isClient));
//                }
//            }
//        }
//    }
//}
