package com.github.theredbrain.redbrainssurvivalmod.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.BlockState;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.fluid.Fluids;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//
//public class NetheriteCauldronBlock extends AbstractNetheriteCauldronBlock {
//    public NetheriteCauldronBlock(Settings settings) {
//        super(settings, NetheriteCauldronBehaviour.EMPTY_NETHERITE_CAULDRON_BEHAVIOR);
//    }
//
//    public boolean isFull(BlockState state) {
//        return false;
//    }
//
//    public boolean canBeFilledByDripstone(Fluid fluid) {
//        return true;
//    }
//
//    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
//        BlockState blockState;
//         if (fluid == Fluids.LAVA) {
//            blockState = BlocksRegistry.NETHERITE_LAVA_CAULDRON.get().getDefaultState();
//            world.setBlockState(pos, blockState);
//            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
//            world.syncWorldEvent(1046, pos, 0);
//        }
//
//    }
//}
