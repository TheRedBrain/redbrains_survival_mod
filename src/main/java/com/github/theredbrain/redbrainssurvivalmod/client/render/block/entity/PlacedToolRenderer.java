package com.github.theredbrain.redbrainssurvivalmod.client.render.block.entity;

import com.github.theredbrain.redbrainssurvivalmod.block.entity.PlacedToolEntity;
import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class PlacedToolRenderer implements BlockEntityRenderer<PlacedToolEntity> {

    public PlacedToolRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override // TODO use tags instead of instanceof checks
    public void render(PlacedToolEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!blockEntity.isEmpty() && blockEntity.getWorld() != null) {
            BlockState blockState = blockEntity.getWorld().getBlockState(blockEntity.getPos());
            if (!blockState.isAir()) {
                Direction blockDirection = blockState.get(HorizontalFacingBlock.FACING);
                matrices.push();
                if (blockEntity.getStack(0).isIn(ItemTags.AXES)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.55D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((320F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.45D);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((320F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.45D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((320F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.55D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((320F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    }
                } else if (blockEntity.getStack(0).isIn(ItemTags.HOES)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.55D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.45D);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.45D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.55D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    }
                } else if (blockEntity.getStack(0).isIn(ItemTags.PICKAXES)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.55D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.4D, 0.45D);
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.45D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.55D, 0.4D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((335F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((180F)));
                    }
                } else if (blockEntity.getStack(0).isIn(ItemTags.SHOVELS)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.325D, 0.3D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.675D, 0.275D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.25D, 0.325D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.225D, 0.675D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    }
                } else if (blockEntity.getStack(0).isIn(ItemTags.SWORDS)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.25D, 0.3D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.75D, 0.3D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.3D, 0.25D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.3D, 0.75D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    }
                } else if (blockEntity.getStack(0).isIn(Tags.CRAFTING_CHISELS)) {
                    if (blockDirection == Direction.NORTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.25D, 0.3D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((180F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.SOUTH) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.75D, 0.3D, 0.5D);
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.EAST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.3D, 0.25D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((90F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    } else if (blockDirection == Direction.WEST) {
                        matrices.scale(1.0F, 1.0F, 1.0F);
                        matrices.translate(0.5D, 0.3D, 0.75D);
                        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((350F)));
                        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((270F)));
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((90F)));
                    }
                }
                MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(0), ModelTransformationMode.THIRD_PERSON_LEFT_HAND, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(),
                        (int) blockEntity.getPos().asLong());
                matrices.pop();
            }
        }
    }
}
