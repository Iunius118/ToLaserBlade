package com.github.iunius118.tolaserblade.client.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

import java.util.HashSet;
import java.util.Set;

public class LaserBladePipelines {
    // Set to hold all registered mod pipelines for NeoForge's RegisterRenderPipelinesEvent
    private static final Set<RenderPipeline> MOD_PIPELINES = new HashSet<>();
    public static final RenderPipeline TRANSLUCENT = register(
            // Use the entity snippet
            RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
                    .withLocation("pipeline/tlb_translucent")
                    .withShaderDefine("ALPHA_CUTOUT", 0.1F)
                    .withSampler("Sampler1")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .build()
    );
    // Unlit item render pipeline
    public static final RenderPipeline.Snippet UNLIT_SNIPPET = RenderPipeline
            .builder(RenderPipelines.MATRICES_COLOR_FOG_SNIPPET)
            // Use the beacon beam shaders
            .withVertexShader("core/rendertype_beacon_beam")
            .withFragmentShader("core/rendertype_beacon_beam")
            .withSampler("Sampler0")
            .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS)
            .buildSnippet();
    public static final RenderPipeline UNLIT_TRANSLUCENT = register(
            RenderPipeline.builder(UNLIT_SNIPPET)
                    .withLocation("pipeline/tlb_unlit_translucent")
                    .withBlend(BlendFunction.TRANSLUCENT)
                    .build()
    );
    public static final RenderPipeline ADD = register(
            RenderPipeline.builder(UNLIT_SNIPPET)
                    .withLocation("pipeline/tlb_add")
                    // Use the lightning blend function (additive and alpha)
                    .withBlend(BlendFunction.LIGHTNING)
                    .build()
    );

    private static RenderPipeline register(RenderPipeline renderPipeline) {
        MOD_PIPELINES.add(renderPipeline);
        return renderPipeline;
    }

    public static void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
        MOD_PIPELINES.forEach(event::registerPipeline);
    }

    private LaserBladePipelines() {}
}
