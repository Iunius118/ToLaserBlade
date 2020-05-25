package com.github.iunius118.tolaserblade.client.renderer.entity;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.github.iunius118.tolaserblade.client.renderer.entity.model.LaserTrapModel;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class LaserTrapEntityRenderer extends EntityRenderer<LaserTrapEntity> {
    private static final int FULL_LIGHT = 0xF000F0;
    private static final LaserTrapModel LASER_TRAP_MODEL = new LaserTrapModel();
    private static final ResourceLocation LASER_TRAP_TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/entity/laser_trap/laser_trap.png");

    public LaserTrapEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(LaserTrapEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();

        Color4f c = new Color4f(entityIn.getColor());
        LASER_TRAP_MODEL.setRotationAngles(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        // Render shape (write depth)
        IVertexBuilder buffer = bufferIn.getBuffer(LaserBladeRenderType.getTrapRenderType(LASER_TRAP_TEXTURE));
        LASER_TRAP_MODEL.render(matrixStackIn, buffer, FULL_LIGHT, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);
        // Render color
        buffer = bufferIn.getBuffer(RenderType.getEyes(LASER_TRAP_TEXTURE));
        //LASER_TRAP_MODEL.render(matrixStackIn, buffer, FULL_LIGHT, OverlayTexture.NO_OVERLAY, c.r, c.g, c.b, c.a);

        matrixStackIn.pop();
    }

    private static class Color4f {
        public final float b;
        public final float g;
        public final float r;
        public final float a;

        public Color4f(int color) {
            b = (float)(color & 0xFF) / 0xFF;
            g = (float)((color >>> 8) & 0xFF) / 0xFF;
            r = (float)((color >>> 16) & 0xFF) / 0xFF;
            a = (float)((color >>> 24) & 0xFF) / 0xFF;
        }
    }

    @Override
    protected int getBlockLight(LaserTrapEntity entityIn, float partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(LaserTrapEntity entity) {
        return LASER_TRAP_TEXTURE;
    }
}