package com.github.theredbrain.redbrainssurvivalmod.mixin.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.ComposterBlock;
//import net.minecraft.block.InventoryProvider;
//import net.minecraft.entity.ItemEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.inventory.SidedInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldAccess;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(ComposterBlock.class)
//public abstract class ComposterBlockMixin extends Block implements InventoryProvider {
//
//    @Shadow
//    @Final
//    public static IntProperty LEVEL;
//
//    public ComposterBlockMixin(Settings settings) {
//        super(settings);
//    }
//
//    @Shadow
//    static BlockState emptyComposter(BlockState state, WorldAccess world, BlockPos pos) {
//        throw new AssertionError();
//    }
//
//    @Inject(method = "emptyFullComposter", at = @At("HEAD"), cancellable = true)
//    private static void dropOrganicCompostWhenEmptyFullComposter(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
//        if (!world.isClient) {
//            float f = 0.7F;
//            double d = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
//            double e = (double)(world.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
//            double g = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
//            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, new ItemStack(BlocksRegistry.ORGANIC_COMPOST));
//            itemEntity.setToDefaultPickupDelay();
//            world.spawnEntity(itemEntity);
//        }
//
//        BlockState blockState = emptyComposter(state, world, pos);
//        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
//        cir.setReturnValue(blockState);
//    }
//
//    @Inject(method = "getInventory", at = @At("HEAD"), cancellable = true)
//    private void getInventoryWithOrganicCompost(BlockState state, WorldAccess world, BlockPos pos, CallbackInfoReturnable<SidedInventory> cir) {
//        int i = (Integer)state.get(LEVEL);
//        if (i == 8) {
//            cir.setReturnValue(new ComposterBlock.FullComposterInventory(state, world, pos, new ItemStack(BlocksRegistry.ORGANIC_COMPOST)));
//        } else {
//            cir.setReturnValue((SidedInventory)(i < 7 ? new ComposterBlock.ComposterInventory(state, world, pos) : new ComposterBlock.DummyInventory()));
//        }
//        cir.cancel();
//    }
//
//}
