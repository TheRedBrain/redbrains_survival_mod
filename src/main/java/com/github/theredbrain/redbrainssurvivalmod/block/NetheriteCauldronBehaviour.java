package com.github.theredbrain.redbrainssurvivalmod.block;
//
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import com.github.theredbrain.redbrainssurvivalmod.registry.ItemsRegistry;
//import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemUsage;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvent;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.stat.Stats;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.Util;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//
//import java.util.Map;
//import java.util.function.Predicate;
//
//public interface NetheriteCauldronBehaviour {
//    Map<Item, NetheriteCauldronBehaviour> EMPTY_NETHERITE_CAULDRON_BEHAVIOR = createMap();
//    Map<Item, NetheriteCauldronBehaviour> NETHERITE_LAVA_CAULDRON_BEHAVIOR = createMap();
//    NetheriteCauldronBehaviour FILL_WITH_LAVA = (state, world, pos, player, hand, stack) -> {
//        return fillCauldron(world, pos, player, hand, stack, BlocksRegistry.NETHERITE_LAVA_CAULDRON.get().getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
//    };
//
//    static Object2ObjectOpenHashMap<Item, NetheriteCauldronBehaviour> createMap() {
//        return Util.make(new Object2ObjectOpenHashMap<Item, NetheriteCauldronBehaviour>(), (map) -> {
//            map.defaultReturnValue((state, world, pos, player, hand, stack) -> ActionResult.PASS);
//        });
//    }
//
//    ActionResult interact(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack);
//
//    static void registerBehavior() {
//        registerBucketBehavior(EMPTY_NETHERITE_CAULDRON_BEHAVIOR);
//        NETHERITE_LAVA_CAULDRON_BEHAVIOR.put(ItemsRegistry.NETHERITE_BUCKET.get(), (state, world, pos, player, hand, stack) -> {
//            return emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(ItemsRegistry.NETHERITE_LAVA_BUCKET.get()), (statex) -> {
//                return true;
//            }, SoundEvents.ITEM_BUCKET_FILL_LAVA);
//        });
//    }
//
//    static void registerBucketBehavior(Map<Item, NetheriteCauldronBehaviour> behavior) {
//        behavior.put(ItemsRegistry.NETHERITE_LAVA_BUCKET.get(), FILL_WITH_LAVA);
//    }
//
//    static ActionResult emptyCauldron(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, ItemStack output, Predicate<BlockState> predicate, SoundEvent soundEvent) {
//        if (!predicate.test(state)) {
//            return ActionResult.PASS;
//        } else {
//            if (!world.isClient) {
//                Item item = stack.getItem();
//                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, output));
//                player.incrementStat(Stats.USE_CAULDRON);
//                player.incrementStat(Stats.USED.getOrCreateStat(item));
//                world.setBlockState(pos, BlocksRegistry.NETHERITE_CAULDRON.get().getDefaultState());
//                world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                world.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, pos);
//            }
//
//            return ActionResult.success(world.isClient);
//        }
//    }
//
//    static ActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
//        if (!world.isClient) {
//            Item item = stack.getItem();
//            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ItemsRegistry.NETHERITE_BUCKET.get())));
//            player.incrementStat(Stats.FILL_CAULDRON);
//            player.incrementStat(Stats.USED.getOrCreateStat(item));
//            world.setBlockState(pos, state);
//            world.playSound((PlayerEntity)null, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
//            world.emitGameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
//        }
//
//        return ActionResult.success(world.isClient);
//    }
//}
