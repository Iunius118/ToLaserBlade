package com.github.iunius118.tolaserblade.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        var tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        renderQuad(builder, camera, vertices[1], vertices[3], vertices[2], vertices[0]);
        renderQuad(builder, camera, vertices[3], vertices[5], vertices[4], vertices[2]);
        renderQuad(builder, camera, vertices[5], vertices[7], vertices[6], vertices[4]);
        renderQuad(builder, camera, vertices[7], vertices[1], vertices[0], vertices[6]);
        renderQuad(builder, camera, vertices[0], vertices[2], vertices[4], vertices[6]);
        renderQuad(builder, camera, vertices[7], vertices[5], vertices[3], vertices[1]);

        MeshData meshData = builder.build();

        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }
    }

    private void renderQuad(VertexConsumer vertexConsumer,  Camera camera, Vector3f... v) {
        if (v.length < 4) return;
        var cameraPos = camera.getPosition();

        for (int i = 0; i < 4; i++) {
            Vector3f vertex = new Vector3f(v[i]);
            getRotation().transform(vertex);
            vertex.add((float) (x - cameraPos.x), (float) (y - cameraPos.y), (float) (z - cameraPos.z));
            vertexConsumer.addVertex(vertex.x(), vertex.y(), vertex.z()).setColor(rCol, gCol, bCol, alpha);
        }
    }

    private final static Quaternionf Q_X = new Quaternionf();
    private final static Quaternionf Q_Y = new Quaternionf(0, 0, Math.sin(Math.toRadians(45f)), Math.cos(Math.toRadians(45f)));
    private final static Quaternionf Q_Z = new Quaternionf(0, Math.sin(Math.toRadians(45f)), 0, Math.cos(Math.toRadians(45f)));

    private Quaternionf getRotation() {
        return switch (axis) {
            case Y -> Q_Y;
            case Z -> Q_Z;
            default -> Q_X;
        };
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
