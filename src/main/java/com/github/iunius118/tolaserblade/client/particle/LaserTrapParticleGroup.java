package com.github.iunius118.tolaserblade.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.ParticleGroupRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LaserTrapParticleGroup extends ParticleGroup<LaserTrapParticle> {
    public LaserTrapParticleGroup(ParticleEngine particleEngine) {
        super(particleEngine);
    }

    @Override
    public ParticleGroupRenderState extractRenderState(Frustum frustum, Camera camera, float f) {
        return new LaserTrapParticleGroup.State(
                this.particles
                        .stream()
                        .map(particle -> LaserTrapParticleGroup.LaserTrapParticleRenderState.fromParticle(particle, camera))
                        .toList()
        );
    }

    record LaserTrapParticleRenderState(Model<Unit> model, PoseStack poseStack, RenderType renderType, int color) {
        public static LaserTrapParticleGroup.LaserTrapParticleRenderState fromParticle(LaserTrapParticle particle, Camera camera) {
            var poseStack = new PoseStack();
            Vec3 pos = particle.getPosition().subtract(camera.position());
            poseStack.translate(pos.x, pos.y, pos.z);
            int color = ARGB.colorFromFloat(1F, particle.rCol, particle.gCol, particle.bCol);
            return new LaserTrapParticleGroup.LaserTrapParticleRenderState(particle.model, poseStack, particle.model.renderType(), color);
        }
    }

    record State(List<LaserTrapParticleGroup.LaserTrapParticleRenderState> states) implements ParticleGroupRenderState {
        @Override
        public void submit(SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
            for (LaserTrapParticleGroup.LaserTrapParticleRenderState state : this.states) {
                submitNodeCollector.submitModel(
                        state.model,
                        Unit.INSTANCE,
                        state.poseStack,
                        state.renderType,
                        0xF000F0,
                        OverlayTexture.NO_OVERLAY,
                        state.color,
                        null,
                        0,
                        null
                );
            }
        }
    }
}
