package com.github.iunius118.tolaserblade.mixin.client;

import com.github.iunius118.tolaserblade.client.renderer.LaserBladePipelines;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.lwjgl.opengl.GL14;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiBufferSource.BufferSource.class)
public abstract class BufferSourceMixin {
    @Inject(method = "endBatch(Lnet/minecraft/client/renderer/rendertype/RenderType;)V", at = @At("HEAD"))
    private void onEndBatchHead(RenderType renderType, CallbackInfo ci) {
        if (renderType.pipeline() == LaserBladePipelines.SUB) {
            // Set blend equation to reverse subtract before rendering laser blade parts with subtractive blending
            GL14.glBlendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
        }
    }

    @Inject(method = "endBatch(Lnet/minecraft/client/renderer/rendertype/RenderType;)V", at = @At("TAIL"))
    private void onEndBatchTail(RenderType renderType, CallbackInfo ci) {
        if (renderType.pipeline() == LaserBladePipelines.SUB) {
            // Restore blend equation to default (add) after rendering laser blade parts with subtractive blending
            GL14.glBlendEquation(GL14.GL_FUNC_ADD);
        }
    }
}
