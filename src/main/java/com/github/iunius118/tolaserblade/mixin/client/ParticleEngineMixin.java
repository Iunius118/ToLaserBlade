package com.github.iunius118.tolaserblade.mixin.client;

import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticleGroup;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.ParticlesRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

/**
 * Temporary private hook for ParticleEngine until NeoForge's hook is implemented.
 */
@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
    @Shadow
    @Final
    private Map<ParticleRenderType, ParticleGroup<?>> particles;

    @Inject(method = "createParticleGroup", at = @At(value = "NEW", target = "(Lnet/minecraft/client/particle/ParticleEngine;Lnet/minecraft/client/particle/ParticleRenderType;)Lnet/minecraft/client/particle/QuadParticleGroup;"), cancellable = true)
    private void onCreateParticleGroup(ParticleRenderType particleRenderType, CallbackInfoReturnable<ParticleGroup<?>> cir) {
        if (particleRenderType == LaserTrapParticle.PARTICLE_RENDER_TYPE) {
            cir.setReturnValue(new LaserTrapParticleGroup((ParticleEngine) (Object) this));
        }
    }

    @Inject(method = "extract", at = @At("TAIL"))
    private void onExtract(ParticlesRenderState particlesRenderState, Frustum frustum, Camera camera, float partialTick, CallbackInfo ci) {
        ParticleGroup<?> particleGroup = this.particles.get(LaserTrapParticle.PARTICLE_RENDER_TYPE);

        if (particleGroup != null && !particleGroup.isEmpty()) {
            particlesRenderState.add(particleGroup.extractRenderState(frustum, camera, partialTick));
        }
    }
}
