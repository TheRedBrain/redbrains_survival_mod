package com.github.theredbrain.redbrainssurvivalmod.mixin.server.network;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
//import net.minecraft.block.BlockState;
//import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
//import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
//import net.minecraft.server.network.ServerPlayNetworkHandler;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.server.network.ServerPlayerInteractionManager;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.GameMode;
//import org.slf4j.Logger;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//
//import java.util.Objects;
//
//@Mixin(ServerPlayerInteractionManager.class)
//public class ServerPlayerInteractionManagerMixin {
//
//    @Shadow
//    @Final
//    private static Logger LOGGER;
//
//    @Shadow
//    protected ServerWorld world;
//
//    @Shadow
//    @Final
//    protected ServerPlayerEntity player;
//
//    @Shadow
//    private GameMode gameMode;
//
//    @Shadow
//    private boolean mining;
//
//    @Shadow
//    private int startMiningTime;
//
//    @Shadow
//    private BlockPos miningPos;
//
//    @Shadow
//    private int tickCounter;
//
//    @Shadow
//    private boolean failedToMine;
//
//    @Shadow
//    private BlockPos failedMiningPos;
//
//    @Shadow
//    private int failedStartMiningTime;
//
//    @Shadow
//    private int blockBreakingProgress;
//
//    @Shadow
//    public boolean isCreative() {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    private void method_41250(BlockPos pos, boolean success, int sequence, String reason) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    public void finishMining(BlockPos pos, int sequence, String reason) {
//        throw new AssertionError();
//    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public void processBlockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight, int sequence) {
//        if (this.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(pos)) > ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) {
//            this.method_41250(pos, false, sequence, "too far");
//            return;
//        }
//        if (pos.getY() >= worldHeight) {
//            this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
//            this.method_41250(pos, false, sequence, "too high");
//            return;
//        }
//        if (action == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
//            if (!this.world.canPlayerModifyAt(this.player, pos)) {
//                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
//                this.method_41250(pos, false, sequence, "may not interact");
//                return;
//            }
//            if (this.isCreative()) {
//                this.finishMining(pos, sequence, "creative destroy");
//                return;
//            }
//            if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
//                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
//                this.method_41250(pos, false, sequence, "block action restricted");
//                return;
//            }
////            if (!(this.player.getMainHandStack().getItem().canMine(this.world.getBlockState(pos), this.world, pos, this.player) || this.player.canHarvest(this.world.getBlockState(pos)))) {
////                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
////                this.method_41250(pos, false, sequence, "can not mine block with item");
////                return;
////            }
////            if (!this.player.canHarvest(this.world.getBlockState(pos))) {
////                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
////                this.method_41250(pos, false, sequence, "can not mine block without tool");
////                return;
////            }
//            this.startMiningTime = this.tickCounter;
//            float f = 1.0f;
//            BlockState blockState = this.world.getBlockState(pos);
//            if (!blockState.isAir()) {
//                blockState.onBlockBreakStart(this.world, pos, this.player);
//                f = blockState.calcBlockBreakingDelta(this.player, this.player.world, pos);
//            }
//            if (!blockState.isAir() && f >= 1.0f) {
//                this.finishMining(pos, sequence, "insta mine");
//            } else {
//                if (this.mining) {
//                    this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos)));
//                    this.method_41250(pos, false, sequence, "abort destroying since another started (client insta mine, server disagreed)");
//                }
//                this.mining = true;
//                this.miningPos = pos.toImmutable();
//                int i = (int)(f * 10.0f);
//                this.world.setBlockBreakingInfo(this.player.getId(), pos, i);
//                this.method_41250(pos, true, sequence, "actual start of destroying");
//                this.blockBreakingProgress = i;
//            }
//        } else if (action == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
//            if (pos.equals(this.miningPos)) {
//                int j = this.tickCounter - this.startMiningTime;
//                BlockState blockState = this.world.getBlockState(pos);
//                if (!blockState.isAir()) {
//                    float g = blockState.calcBlockBreakingDelta(this.player, this.player.world, pos) * (float)(j + 1);
//                    if (g >= 0.7f) {
//                        this.mining = false;
//                        this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
//                        this.finishMining(pos, sequence, "destroyed");
//                        return;
//                    }
//                    if (!this.failedToMine) {
//                        this.mining = false;
//                        this.failedToMine = true;
//                        this.failedMiningPos = pos;
//                        this.failedStartMiningTime = this.startMiningTime;
//                    }
//                }
//            }
//            this.method_41250(pos, true, sequence, "stopped destroying");
//        } else if (action == PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK) {
//            this.mining = false;
//            if (!Objects.equals(this.miningPos, pos)) {
//                LOGGER.warn("Mismatch in destroy block pos: {} {}", (Object)this.miningPos, (Object)pos);
//                this.world.setBlockBreakingInfo(this.player.getId(), this.miningPos, -1);
//                this.method_41250(pos, true, sequence, "aborted mismatched destroying");
//            }
//            this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
//            this.method_41250(pos, true, sequence, "aborted destroying");
//        }
//    }
//}
