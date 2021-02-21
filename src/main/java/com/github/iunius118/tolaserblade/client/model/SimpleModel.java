package com.github.iunius118.tolaserblade.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class SimpleModel {
    public static class SimpleVertex {
        public final Vector3f pos;
        public final Vector4f color;
        public final Vector2f uv;
        public final Vector3f normal;

        public SimpleVertex(Vector3f posXYZ, Vector4f colorRGBA, Vector2f texUV, Vector3f normalXYZ) {
            pos = posXYZ;
            color = colorRGBA;
            uv = texUV;
            normal = normalXYZ;
        }
    }

    public static class SimpleQuad {
        public static final Vector4f COLOR_WHITE = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
        public final SimpleVertex[] vertices;

        public SimpleQuad(SimpleVertex vertex1, SimpleVertex vertex2, SimpleVertex vertex3, SimpleVertex vertex4) {
            this.vertices = new SimpleVertex[]{vertex1, vertex2, vertex3, vertex4};
        }

        public SimpleQuad(Vector3f posXYZ1, Vector4f colorRGBA1, Vector2f texUV1, Vector3f normalXYZ1,
                          Vector3f posXYZ2, Vector4f colorRGBA2, Vector2f texUV2, Vector3f normalXYZ2,
                          Vector3f posXYZ3, Vector4f colorRGBA3, Vector2f texUV3, Vector3f normalXYZ3,
                          Vector3f posXYZ4, Vector4f colorRGBA4, Vector2f texUV4, Vector3f normalXYZ4) {
            this(new SimpleVertex(posXYZ1, colorRGBA1, texUV1, normalXYZ1),
                    new SimpleVertex(posXYZ2, colorRGBA2, texUV2, normalXYZ2),
                    new SimpleVertex(posXYZ3, colorRGBA3, texUV3, normalXYZ3),
                    new SimpleVertex(posXYZ4, colorRGBA4, texUV4, normalXYZ4));
        }

        public SimpleQuad(Vector3f posXYZ1, Vector4f colorRGBA1, Vector2f texUV1,
                          Vector3f posXYZ2, Vector4f colorRGBA2, Vector2f texUV2,
                          Vector3f posXYZ3, Vector4f colorRGBA3, Vector2f texUV3,
                          Vector3f posXYZ4, Vector4f colorRGBA4, Vector2f texUV4,
                          Vector3f normalXYZ) {
            this(new SimpleVertex(posXYZ1, colorRGBA1, texUV1, normalXYZ),
                    new SimpleVertex(posXYZ2, colorRGBA2, texUV2, normalXYZ),
                    new SimpleVertex(posXYZ3, colorRGBA3, texUV3, normalXYZ),
                    new SimpleVertex(posXYZ4, colorRGBA4, texUV4, normalXYZ));
        }

        public SimpleQuad(Vector3f posXYZ1, Vector2f texUV1,
                          Vector3f posXYZ2, Vector2f texUV2,
                          Vector3f posXYZ3, Vector2f texUV3,
                          Vector3f posXYZ4, Vector2f texUV4,
                          Vector4f colorRGBA, Vector3f normalXYZ) {
            this(new SimpleVertex(posXYZ1, colorRGBA, texUV1, normalXYZ),
                    new SimpleVertex(posXYZ2, colorRGBA, texUV2, normalXYZ),
                    new SimpleVertex(posXYZ3, colorRGBA, texUV3, normalXYZ),
                    new SimpleVertex(posXYZ4, colorRGBA, texUV4, normalXYZ));
        }

        public SimpleQuad(Vector3f posXYZ1, Vector2f texUV1,
                          Vector3f posXYZ2, Vector2f texUV2,
                          Vector3f posXYZ3, Vector2f texUV3,
                          Vector3f posXYZ4, Vector2f texUV4,
                          Vector3f normalXYZ) {
            this(new SimpleVertex(posXYZ1, COLOR_WHITE, texUV1, normalXYZ),
                    new SimpleVertex(posXYZ2, COLOR_WHITE, texUV2, normalXYZ),
                    new SimpleVertex(posXYZ3, COLOR_WHITE, texUV3, normalXYZ),
                    new SimpleVertex(posXYZ4, COLOR_WHITE, texUV4, normalXYZ));
        }

        public SimpleQuad mirror() {
            return new SimpleQuad(this.vertices[3], this.vertices[2], this.vertices[1], this.vertices[0]);
        }
    }

    protected void renderQuads(MatrixStack matrixStack, IVertexBuilder buffer, List<SimpleQuad> quads, int color, int lightmapCoord, int overlayColor) {
        float alpha = (float)(color >>> 24 & 255) / 255.0F;
        float red   = (float)(color >>> 16 & 255) / 255.0F;
        float green = (float)(color >>> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;
        renderQuads(matrixStack, buffer, quads, lightmapCoord, overlayColor, red, green, blue, alpha);
    }

    protected void renderQuads(MatrixStack matrixStack, IVertexBuilder buffer, List<SimpleQuad> quads, int lightmapCoord, int overlayColor, float red, float green, float blue, float alpha) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();

        for (SimpleQuad face : quads) {
            for (int i = 0; i < 4; i++) {
                SimpleVertex vertex = face.vertices[i];

                Vector4f pos = new Vector4f(vertex.pos);
                pos.transform(matrixEntry.getMatrix());

                Vector4f vColor = vertex.color;
                Vector2f uv = vertex.uv;

                Vector3f normal = vertex.normal.copy();
                normal.transform(matrixEntry.getNormal());

                buffer.addVertex(pos.getX(), pos.getY(), pos.getZ(),
                        red * vColor.getX(), green * vColor.getY(), blue * vColor.getZ(), alpha * vColor.getW(),
                        uv.x, uv.y, overlayColor, lightmapCoord,
                        normal.getX(), normal.getY(), normal.getZ());
            }
        }
    }
}
