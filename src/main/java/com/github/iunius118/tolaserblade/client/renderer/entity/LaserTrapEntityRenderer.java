package com.github.iunius118.tolaserblade.client.renderer.entity;

import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class LaserTrapEntityRenderer extends EntityRenderer<LaserTrapEntity> {
    public LaserTrapEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(LaserTrapEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();

        Vec3d look = entityIn.getLookVec();
        Direction.Axis axis = Direction.getFacingFromVector((float)look.x, (float)look.y, (float)look.z).getAxis();
        AxisAlignedBB laserBoundingBox = new AxisAlignedBB(BlockPos.ZERO); // entityIn.getBoundingBox();

        if (axis == Direction.Axis.Y) {
            laserBoundingBox = laserBoundingBox.grow(-0.4375D, 0.0D, -0.4375D);
        } else if (axis == Direction.Axis.X) {
            laserBoundingBox = laserBoundingBox.grow(0.0D, -0.4375D, -0.4375D);
        } else {
            laserBoundingBox = laserBoundingBox.grow(-0.4375D, -0.4375D, 0.0D);
        }

        renderLaserTrap(laserBoundingBox.offset(-0.5D, 0.0D, -0.5D), matrixStackIn, bufferIn, entityIn.getColor());

        matrixStackIn.pop();
    }

    private void renderLaserTrap(AxisAlignedBB boundingBox, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int color) {
        Color4f c = new Color4f(color);
        Matrix4f matrix = matrixStackIn.getLast().getMatrix();
        float minX = (float)boundingBox.minX;
        float maxX = (float)boundingBox.maxX;
        float minY = (float)boundingBox.minY;
        float maxY = (float)boundingBox.maxY;
        float minZ = (float)boundingBox.minZ;
        float maxZ = (float)boundingBox.maxZ;

        IVertexBuilder bufferBuilder = bufferIn.getBuffer(LaserBladeRenderType.LASER_TRAP);

        // Down
        bufferBuilder.pos(matrix, minX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        // Up
        bufferBuilder.pos(matrix, minX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        // North
        bufferBuilder.pos(matrix, maxX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        // South
        bufferBuilder.pos(matrix, minX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        // West
        bufferBuilder.pos(matrix, minX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, minX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        // East
        bufferBuilder.pos(matrix, maxX, minY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, maxY, minZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, maxY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
        bufferBuilder.pos(matrix, maxX, minY, maxZ).color(c.r, c.g, c.b, c.a).lightmap(240, 240).endVertex();
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
    public ResourceLocation getEntityTexture(LaserTrapEntity entity) {
        return null;
    }
}