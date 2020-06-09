package com.github.iunius118.tolaserblade.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class SimpleItemModel {
    public static class VertexData {
        public final Vector3f pos;
        public final Vector4f color;
        public final Vec2f uv;
        public final Vector3f normal;

        public VertexData(Vector3f posXYZ, Vector4f colorRGBA, Vec2f texUV, Vector3f normalXYZ) {
            pos = posXYZ;
            color = colorRGBA;
            uv = texUV;
            normal = normalXYZ;
        }
    }

    public static class FaceData {
        public static final Vector4f COLOR_WHITE = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
        public final VertexData[] vertices;

        public FaceData(VertexData vertex1, VertexData vertex2, VertexData vertex3, VertexData vertex4) {
            this.vertices = new VertexData[]{vertex1, vertex2, vertex3, vertex4};
        }

        public FaceData(Vector3f posXYZ1, Vector4f colorRGBA1, Vec2f texUV1, Vector3f normalXYZ1,
                        Vector3f posXYZ2, Vector4f colorRGBA2, Vec2f texUV2, Vector3f normalXYZ2,
                        Vector3f posXYZ3, Vector4f colorRGBA3, Vec2f texUV3, Vector3f normalXYZ3,
                        Vector3f posXYZ4, Vector4f colorRGBA4, Vec2f texUV4, Vector3f normalXYZ4) {
            this(new VertexData(posXYZ1, colorRGBA1, texUV1, normalXYZ1),
                    new VertexData(posXYZ2, colorRGBA2, texUV2, normalXYZ2),
                    new VertexData(posXYZ3, colorRGBA3, texUV3, normalXYZ3),
                    new VertexData(posXYZ4, colorRGBA4, texUV4, normalXYZ4));
        }

        public FaceData(Vector3f posXYZ1, Vector4f colorRGBA1, Vec2f texUV1,
                        Vector3f posXYZ2, Vector4f colorRGBA2, Vec2f texUV2,
                        Vector3f posXYZ3, Vector4f colorRGBA3, Vec2f texUV3,
                        Vector3f posXYZ4, Vector4f colorRGBA4, Vec2f texUV4,
                        Vector3f normalXYZ) {
            this(new VertexData(posXYZ1, colorRGBA1, texUV1, normalXYZ),
                    new VertexData(posXYZ2, colorRGBA2, texUV2, normalXYZ),
                    new VertexData(posXYZ3, colorRGBA3, texUV3, normalXYZ),
                    new VertexData(posXYZ4, colorRGBA4, texUV4, normalXYZ));
        }

        public FaceData(Vector3f posXYZ1, Vec2f texUV1,
                        Vector3f posXYZ2, Vec2f texUV2,
                        Vector3f posXYZ3, Vec2f texUV3,
                        Vector3f posXYZ4, Vec2f texUV4,
                        Vector4f colorRGBA, Vector3f normalXYZ) {
            this(new VertexData(posXYZ1, colorRGBA, texUV1, normalXYZ),
                    new VertexData(posXYZ2, colorRGBA, texUV2, normalXYZ),
                    new VertexData(posXYZ3, colorRGBA, texUV3, normalXYZ),
                    new VertexData(posXYZ4, colorRGBA, texUV4, normalXYZ));
        }

        public FaceData(Vector3f posXYZ1, Vec2f texUV1,
                        Vector3f posXYZ2, Vec2f texUV2,
                        Vector3f posXYZ3, Vec2f texUV3,
                        Vector3f posXYZ4, Vec2f texUV4,
                        Vector3f normalXYZ) {
            this(new VertexData(posXYZ1, COLOR_WHITE, texUV1, normalXYZ),
                    new VertexData(posXYZ2, COLOR_WHITE, texUV2, normalXYZ),
                    new VertexData(posXYZ3, COLOR_WHITE, texUV3, normalXYZ),
                    new VertexData(posXYZ4, COLOR_WHITE, texUV4, normalXYZ));
        }

        public FaceData invert() {
            return new FaceData(this.vertices[3], this.vertices[2], this.vertices[1], this.vertices[0]);
        }
    }

    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {

    }


    protected void renderFaces(MatrixStack matrixStack, IVertexBuilder buffer, List<FaceData> faces, int color, int lightmapCoord, int overlayColor) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();
        float alpha = (float)(color >>> 24 & 255) / 255.0F;
        float red   = (float)(color >>> 16 & 255) / 255.0F;
        float green = (float)(color >>> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        for (SimpleItemModel.FaceData face : faces) {
            for (int i = 0; i < 4; i++) {
                SimpleItemModel.VertexData vertex = face.vertices[i];

                Vector4f pos = new Vector4f(vertex.pos);
                pos.transform(matrixEntry.getMatrix());

                Vector4f vColor = vertex.color;
                Vec2f uv = vertex.uv;

                Vector3f normal = vertex.normal.copy();
                normal.transform(matrixEntry.getNormal());

                buffer.addVertex(pos.getX(), pos.getY(), pos.getZ(),
                        red * vColor.getX(), green * vColor.getY(), blue * vColor.getZ(), alpha * vColor.getW(),
                        uv.x, uv.y, overlayColor, lightmapCoord,
                        normal.getX(), normal.getY(), normal.getZ());
            }
        }
    }

    public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("forge", "textures/white.png");

    public ResourceLocation getTexture() {
        return TEXTURE_WHITE;
    };
}
