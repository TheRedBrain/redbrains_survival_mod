package com.github.theredbrain.redbrainssurvivalmod.client.render.entity;
//
//import com.github.theredbrain.redbrainssurvivalmod.entity.mob.JungleSpiderEntity;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.render.entity.EntityRendererFactory;
//import net.minecraft.client.render.entity.SpiderEntityRenderer;
//import net.minecraft.client.render.entity.model.EntityModelLayer;
//import net.minecraft.client.render.entity.model.EntityModelLayers;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.entity.mob.CaveSpiderEntity;
//import net.minecraft.util.Identifier;
//
//@Environment(EnvType.CLIENT)
//public class JungleSpiderEntityRenderer extends SpiderEntityRenderer<JungleSpiderEntity> {
//    public static final EntityModelLayer JUNGLE_SPIDER_MODEL_LAYER = new EntityModelLayer(new Identifier("redbrainssurvivalmod", "jungle_spider"), "main");
//    private static final Identifier JINGLE_SPIDER_TEXTURE = new Identifier("redbrainssurvivalmod:textures/entity/spider/jungle_spider.png");
//    private static final float SCALE = 0.7F;
//
//    public JungleSpiderEntityRenderer(EntityRendererFactory.Context context) {
//        super(context, new JungleSpiderEntityModel(context.getPart(JUNGLE_SPIDER_MODEL_LAYER)));
//        this.shadowRadius *= 0.7F;
//    }
//
//    protected void scale(JungleSpiderEntity jungleSpiderEntity, MatrixStack matrixStack, float f) {
//        matrixStack.scale(0.7F, 0.7F, 0.7F);
//    }
//
//    public Identifier getTexture(JungleSpiderEntity jungleSpiderEntity) {
//        return JINGLE_SPIDER_TEXTURE;
//    }
//}