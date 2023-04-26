package com.github.theredbrain.redbrainssurvivalmod.client.render.block.entity;
//
//import com.github.theredbrain.redbrainssurvivalmod.block.entity.TriggeredBlockEntity;
//import com.github.theredbrain.redbrainssurvivalmod.registry.BlocksRegistry;
//import net.minecraft.block.BlockState;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.render.RenderLayer;
//import net.minecraft.client.render.VertexConsumerProvider;
//import net.minecraft.client.render.block.entity.BlockEntityRenderer;
//import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
//import net.minecraft.client.render.model.BakedModel;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.util.math.BlockPos;
//
//public class TriggeredBlockRenderer implements BlockEntityRenderer<TriggeredBlockEntity> {
//
//    public TriggeredBlockRenderer(BlockEntityRendererFactory.Context ctx) {
//    }
//
//    @Override
//    public void render(TriggeredBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//        // TODO get blockModels of copied blocks
//        if (blockEntity.getWorld() != null) {
//            BlockPos blockPos = blockEntity.getPos();
////            BlockPos copiedBlockPos;
//            BlockState blockState = blockEntity.getWorld().getBlockState(blockPos);
//            if (blockState.isOf(BlocksRegistry.TRIGGERED_BLOCK)) {
////                copiedBlockPos = blockEntity.getCopiedBlockPos();
//                matrices.push();
////                BakedModelManagerHelper.getModel(MinecraftClient.getInstance().getBakedModelManager())
//                BakedModel copiedBlockModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(blockEntity.getWorld().getBlockState(blockEntity.getCopiedBlockPos()));
//                MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(blockEntity.getWorld(), copiedBlockModel, blockState, blockPos, matrices, vertexConsumers.getBuffer(RenderLayer.getCutoutMipped()), true, blockEntity.getWorld().random, 0, overlay);
////                        .getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, light, overlay, matrices, vertexConsumers,
////                                (int) blockEntity.getPos().asLong());
//                matrices.pop();
//            }
//        }
//    }
//}
