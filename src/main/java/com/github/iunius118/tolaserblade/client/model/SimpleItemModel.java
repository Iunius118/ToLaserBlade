package com.github.iunius118.tolaserblade.client.model;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.math.Vec2f;

public interface SimpleItemModel {
    class VertexData {
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

    class FaceData {
        public final VertexData[] vertices;

        public FaceData(VertexData vertex1, VertexData vertex2, VertexData vertex3, VertexData vertex4) {
            this.vertices = new VertexData[]{vertex1, vertex2, vertex3, vertex4};
        }

        public FaceData(Vector3f posXYZ1, Vector4f colorRGBA1, Vec2f texUV1, Vector3f normalXYZ1,
                        Vector3f posXYZ2, Vector4f colorRGBA2, Vec2f texUV2, Vector3f normalXYZ2,
                        Vector3f posXYZ3, Vector4f colorRGBA3, Vec2f texUV3, Vector3f normalXYZ3,
                        Vector3f posXYZ4, Vector4f colorRGBA4, Vec2f texUV4, Vector3f normalXYZ4) {
            this.vertices = new VertexData[]{
                    new VertexData(posXYZ1, colorRGBA1, texUV1, normalXYZ1),
                    new VertexData(posXYZ2, colorRGBA2, texUV2, normalXYZ2),
                    new VertexData(posXYZ3, colorRGBA3, texUV3, normalXYZ3),
                    new VertexData(posXYZ4, colorRGBA4, texUV4, normalXYZ4),
            };
        }

        public FaceData invert() {
            return new FaceData(this.vertices[3], this.vertices[2], this.vertices[1], this.vertices[0]);
        }
    }
}
