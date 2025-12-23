package com.github.iunius118.tolaserblade.mixin.client;

import com.github.iunius118.tolaserblade.client.renderer.LaserBladePipelines;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.MeshData;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.lwjgl.opengl.GL14;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin {
    @Shadow
    public RenderPipeline pipeline() {
        throw new AssertionError();
    }

    @Inject(method = "draw(Lcom/mojang/blaze3d/vertex/MeshData;)V", at = @At("HEAD"))
    private void onDrawMethodHead(MeshData meshData, CallbackInfo ci) {
        if (pipeline() == LaserBladePipelines.SUB) {
            // Set blend equation to reverse subtract before rendering laser blade parts with subtractive blending
            GL14.glBlendEquation(GL14.GL_FUNC_REVERSE_SUBTRACT);
        }
    }

    @Inject(method = "draw(Lcom/mojang/blaze3d/vertex/MeshData;)V", at = @At("RETURN"))
    private void onDrawBeforeReturn(MeshData meshData, CallbackInfo ci) {
        if (pipeline() == LaserBladePipelines.SUB) {
            // Restore blend equation to default (add) after rendering laser blade parts with subtractive blending
            GL14.glBlendEquation(GL14.GL_FUNC_ADD);
        }
    }
}
