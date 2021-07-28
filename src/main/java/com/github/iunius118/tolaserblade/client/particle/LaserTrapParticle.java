package com.github.iunius118.tolaserblade.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaserTrapParticle extends Particle {
    private final Direction.Axis axis;

    protected LaserTrapParticle(ClientLevel clientLevel, double x, double y, double z, float r, float g, float b, Direction.Axis trapAxis) {
        super(clientLevel, x, y, z);
        axis = trapAxis;
        setColor(r, g, b);
        setLifetime(3);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) remove();
    }

    private static final Vector3f[] vertices = {
            new Vector3f(-0.5F, 0.0625F, 0.0625F),
            new Vector3f(-0.5F, -0.0625F, 0.0625F),
            new Vector3f(0.5F, 0.0625F, 0.0625F),
            new Vector3f(0.5F, -0.0625F, 0.0625F),
            new Vector3f(0.5F, 0.0625F, -0.0625F),
            new Vector3f(0.5F, -0.0625F, -0.0625F),
            new Vector3f(-0.5F, 0.0625F, -0.0625F),
            new Vector3f(-0.5F, -0.0625F, -0.0625F)
    };

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableTexture();

        var bufferBuilder = (BufferBuilder) vertexConsumer;
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        renderQuad(bufferBuilder, camera, vertices[1], vertices[3], vertices[2], vertices[0]);
        renderQuad(bufferBuilder, camera, vertices[3], vertices[5], vertices[4], vertices[2]);
        renderQuad(bufferBuilder, camera, vertices[5], vertices[7], vertices[6], vertices[4]);
        renderQuad(bufferBuilder, camera, vertices[7], vertices[1], vertices[0], vertices[6]);
        renderQuad(bufferBuilder, camera, vertices[0], vertices[2], vertices[4], vertices[6]);
        renderQuad(bufferBuilder, camera, vertices[7], vertices[5], vertices[3], vertices[1]);

        var tesselator = Tesselator.getInstance();
        tesselator.end();
    }

    private void renderQuad(VertexConsumer vertexConsumer,  Camera camera, Vector3f... v) {
        if (v.length < 4) return;
        var cameraPos = camera.getPosition();

        for (int i = 0; i < 4; i++) {
            Vector3f vertex = v[i].copy();
            vertex.transform(getRotation());
            vertex.add((float) (x - cameraPos.x), (float) (y - cameraPos.y), (float) (z - cameraPos.z));
            vertexConsumer.vertex(vertex.x(), vertex.y(), vertex.z()).color(rCol, gCol, bCol, alpha).endVertex();
        }
    }

    private Quaternion getRotation() {
        switch (axis) {
            case Y -> {
                return Vector3f.ZP.rotationDegrees(90);
            }
            case Z -> {
                return Vector3f.YP.rotationDegrees(90);
            }
            default -> {
                return Quaternion.ONE;
            }
        }
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final Direction.Axis axis;

        public Provider(Direction.Axis trapAxis) {
            axis = trapAxis;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double dx, double dy, double dz, double dr, double dg, double db) {
            float r = Mth.clamp((float) dr, 0F, 1F);
            float g = Mth.clamp((float) dg, 0F, 1F);
            float b = Mth.clamp((float) db, 0F, 1F);
            return new LaserTrapParticle(clientLevel, dx, dy, dz, r, g, b, axis);
        }
    }
}
