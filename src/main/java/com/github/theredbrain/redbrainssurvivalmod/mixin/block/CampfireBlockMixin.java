package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import com.google.common.collect.Sets;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.CampfireBlock;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.Set;
//
//@Mixin(CampfireBlock.class)
//public class CampfireBlockMixin {
//
//    @Inject(at = @At("HEAD"), method = "isSignalFireBaseBlock", cancellable = true)
//    public void doesBlockCauseSignalFire(BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        Set<Block> hayBales = Sets.newHashSet(BlocksRegistry.STRAW_BALE, BlocksRegistry.RICE_BALE);
//        if (hayBales.contains(state.getBlock())) {
//            cir.setReturnValue(true);
//        }
//    }
//
//    // TODO IDEA campfires spread fire
////    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
////        world.scheduleBlockTick(pos, this, getFireTickDelay(world.random));
////        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
////            if (!state.canPlaceAt(world, pos)) {
////                world.removeBlock(pos, false);
////            }
////
////            BlockState blockState = world.getBlockState(pos.down());
////            boolean bl = blockState.isIn(world.getDimension().getInfiniburnBlocks());
////            int i = (Integer)state.get(AGE);
////            if (!bl && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2F + (float)i * 0.03F) {
////                world.removeBlock(pos, false);
////            } else {
////                int j = Math.min(15, i + random.nextInt(3) / 2);
////                if (i != j) {
////                    state = (BlockState)state.with(AGE, j);
////                    world.setBlockState(pos, state, 4);
////                }
////
////                if (!bl) {
////                    if (!this.areBlocksAroundFlammable(world, pos)) {
////                        BlockPos blockPos = pos.down();
////                        if (!world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || i > 3) {
////                            world.removeBlock(pos, false);
////                        }
////
////                        return;
////                    }
////
////                    if (i == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
////                        world.removeBlock(pos, false);
////                        return;
////                    }
////                }
////
////                boolean bl2 = world.hasHighHumidity(pos);
////                int k = bl2 ? -50 : 0;
////                this.trySpreadingFire(world, pos.east(), 300 + k, random, i);
////                this.trySpreadingFire(world, pos.west(), 300 + k, random, i);
////                this.trySpreadingFire(world, pos.down(), 250 + k, random, i);
////                this.trySpreadingFire(world, pos.up(), 250 + k, random, i);
////                this.trySpreadingFire(world, pos.north(), 300 + k, random, i);
////                this.trySpreadingFire(world, pos.south(), 300 + k, random, i);
////                BlockPos.Mutable mutable = new BlockPos.Mutable();
////
////                for(int l = -1; l <= 1; ++l) {
////                    for(int m = -1; m <= 1; ++m) {
////                        for(int n = -1; n <= 4; ++n) {
////                            if (l != 0 || n != 0 || m != 0) {
////                                int o = 100;
////                                if (n > 1) {
////                                    o += (n - 1) * 100;
////                                }
////
////                                mutable.set(pos, l, n, m);
////                                int p = this.getBurnChance(world, mutable);
////                                if (p > 0) {
////                                    int q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
////                                    if (bl2) {
////                                        q /= 2;
////                                    }
////
////                                    if (q > 0 && random.nextInt(o) <= q && (!world.isRaining() || !this.isRainingAround(world, mutable))) {
////                                        int r = Math.min(15, i + random.nextInt(5) / 4);
////                                        world.setBlockState(mutable, this.getStateWithAge(world, mutable, r), 3);
////                                    }
////                                }
////                            }
////                        }
////                    }
////                }
////
////            }
////        }
////    }
//
////    private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {
////        int i = this.getSpreadChance(world.getBlockState(pos));
////        if (rand.nextInt(spreadFactor) < i) {
////            BlockState blockState = world.getBlockState(pos);
////            if (rand.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
////                int j = Math.min(currentAge + rand.nextInt(5) / 4, 15);
////                world.setBlockState(pos, this.getStateWithAge(world, pos, j), 3);
////            } else {
////                world.removeBlock(pos, false);
////            }
////
////            Block block = blockState.getBlock();
////            if (block instanceof TntBlock) {
////                TntBlock var10000 = (TntBlock)block;
////                TntBlock.primeTnt(world, pos);
////            }
////        }
////
////    }
//}
