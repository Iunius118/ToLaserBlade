package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LaserBladeModelType1221 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1221.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_NO_TINT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_1_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_2_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_3_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_4_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int offColor = 0xFFCCCCCC;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);
        renderQuads(matrices, currentBuffer, HILT_NO_TINT_QUADS, offColor, light, overlay);

        if (color.isBroken) {
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlay);

        // Twinkle outer blades
        float f = (Util.getMillis() & 0x7FF) / 1024F - 1;
        float alpha = 0.875F - (f * f * f - f) * 0.325F;
        int outerColor = (int) ((color.outerColor >>> 24) * alpha) << 24 | (color.outerColor & 0xFFFFFF);

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrices, currentBuffer, BLADE_OUT_1_QUADS, outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_OUT_2_QUADS, outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_OUT_3_QUADS, outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_OUT_4_QUADS, outerColor, fullLight, overlay);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // Hilt color
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // HiltNoTint color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.9F); // BladeIn color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.2F); // BladeOut1 color
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.2F); // BladeOut2 color
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.15F); // BladeOut3 color
        Vector4f c6 = new Vector4f(1F, 1F, 1F, 0.15F); // BladeOut4 color

        Vector3f v8 = new Vector3f(-0.0625000F, 0.3750000F, 0.0625000F);
        Vector3f v105 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v21 = new Vector3f(0.0312500F, 0.3750000F, -0.0312500F);
        Vector3f v27 = new Vector3f(-0.0390625F, 0.3750000F, 0.0390625F);
        Vector3f v14 = new Vector3f(-0.0625000F, 0.3750000F, -0.0625000F);
        Vector3f v43 = new Vector3f(-0.0546875F, 0.3750000F, 0.0546875F);
        Vector3f v45 = new Vector3f(0.0546875F, 0.3750000F, -0.0546875F);
        Vector3f v15 = new Vector3f(-0.0625000F, 0.3125000F, -0.0625000F);
        Vector3f v28 = new Vector3f(0.0390625F, 1.4453125F, -0.0390625F);
        Vector3f v12 = new Vector3f(0.0625000F, 0.3750000F, -0.0625000F);
        Vector3f v19 = new Vector3f(-0.0312500F, 0.3750000F, 0.0312500F);
        Vector3f v101 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v103 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v102 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v104 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v1 = new Vector3f(0.0312500F, 0.3125000F, -0.0312500F);
        Vector3f v100 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v51 = new Vector3f(-0.0625000F, 1.4687500F, -0.0625000F);
        Vector3f v3 = new Vector3f(0.0312500F, -0.0625000F, 0.0312500F);
        Vector3f v20 = new Vector3f(0.0312500F, 1.4375000F, -0.0312500F);
        Vector3f v38 = new Vector3f(-0.0468750F, 1.4531250F, -0.0468750F);
        Vector3f v17 = new Vector3f(0.0312500F, 1.4375000F, 0.0312500F);
        Vector3f v22 = new Vector3f(-0.0312500F, 1.4375000F, -0.0312500F);
        Vector3f v24 = new Vector3f(-0.0390625F, 1.4453125F, 0.0390625F);
        Vector3f v34 = new Vector3f(0.0468750F, 0.3750000F, 0.0468750F);
        Vector3f v2 = new Vector3f(0.0312500F, -0.0625000F, -0.0312500F);
        Vector3f v6 = new Vector3f(-0.0312500F, 0.3125000F, 0.0312500F);
        Vector3f v4 = new Vector3f(-0.0312500F, 0.3125000F, -0.0312500F);
        Vector3f v13 = new Vector3f(0.0625000F, 0.3125000F, -0.0625000F);
        Vector3f v48 = new Vector3f(-0.0625000F, 1.4687500F, 0.0625000F);
        Vector3f v47 = new Vector3f(-0.0546875F, 0.3750000F, -0.0546875F);
        Vector3f v46 = new Vector3f(-0.0546875F, 1.4609375F, -0.0546875F);
        Vector3f v40 = new Vector3f(-0.0546875F, 1.4609375F, 0.0546875F);
        Vector3f v39 = new Vector3f(-0.0468750F, 0.3750000F, -0.0468750F);
        Vector3f v35 = new Vector3f(-0.0468750F, 0.3750000F, 0.0468750F);
        Vector3f v32 = new Vector3f(-0.0468750F, 1.4531250F, 0.0468750F);
        Vector3f v10 = new Vector3f(0.0625000F, 0.3125000F, 0.0625000F);
        Vector3f v44 = new Vector3f(0.0546875F, 1.4609375F, -0.0546875F);
        Vector3f v42 = new Vector3f(0.0546875F, 0.3750000F, 0.0546875F);
        Vector3f v41 = new Vector3f(0.0546875F, 1.4609375F, 0.0546875F);
        Vector3f v30 = new Vector3f(-0.0390625F, 1.4453125F, -0.0390625F);
        Vector3f v25 = new Vector3f(0.0390625F, 1.4453125F, 0.0390625F);
        Vector3f v50 = new Vector3f(0.0625000F, 1.4687500F, -0.0625000F);
        Vector3f v26 = new Vector3f(0.0390625F, 0.3750000F, 0.0390625F);
        Vector3f v37 = new Vector3f(0.0468750F, 0.3750000F, -0.0468750F);
        Vector3f v7 = new Vector3f(-0.0312500F, -0.0625000F, 0.0312500F);
        Vector3f v36 = new Vector3f(0.0468750F, 1.4531250F, -0.0468750F);
        Vector3f v33 = new Vector3f(0.0468750F, 1.4531250F, 0.0468750F);
        Vector3f v16 = new Vector3f(-0.0312500F, 1.4375000F, 0.0312500F);
        Vector3f v23 = new Vector3f(-0.0312500F, 0.3750000F, -0.0312500F);
        Vector3f v31 = new Vector3f(-0.0390625F, 0.3750000F, -0.0390625F);
        Vector3f v29 = new Vector3f(0.0390625F, 0.3750000F, -0.0390625F);
        Vector3f v0 = new Vector3f(0.0312500F, 0.3125000F, 0.0312500F);
        Vector3f v9 = new Vector3f(0.0625000F, 0.3750000F, 0.0625000F);
        Vector3f v18 = new Vector3f(0.0312500F, 0.3750000F, 0.0312500F);
        Vector3f v49 = new Vector3f(0.0625000F, 1.4687500F, 0.0625000F);
        Vector3f v5 = new Vector3f(-0.0312500F, -0.0625000F, -0.0312500F);
        Vector3f v11 = new Vector3f(-0.0625000F, 0.3125000F, 0.0625000F);
        Vector2f v74 = new Vector2f(0.25000F, 0.09375F);
        Vector2f v69 = new Vector2f(0.12500F, 0.06250F);
        Vector2f v65 = new Vector2f(0.31250F, 0.06250F);
        Vector2f v68 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v75 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v78 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v89 = new Vector2f(0.37500F, 0.25000F);
        Vector2f v77 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v56 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v98 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v52 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v95 = new Vector2f(0.25000F, 0.25000F);
        Vector2f v81 = new Vector2f(0.09375F, 0.78125F);
        Vector2f v99 = new Vector2f(0.31250F, 0.18750F);
        Vector2f v86 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v92 = new Vector2f(0.18750F, 0.25000F);
        Vector2f v91 = new Vector2f(0.31250F, 0.81250F);
        Vector2f v94 = new Vector2f(0.12500F, 0.81250F);
        Vector2f v63 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v93 = new Vector2f(0.18750F, 0.81250F);
        Vector2f v59 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v64 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v61 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v70 = new Vector2f(0.18750F, 0.06250F);
        Vector2f v85 = new Vector2f(0.00000F, 0.78125F);
        Vector2f v80 = new Vector2f(0.12500F, 0.78125F);
        Vector2f v60 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v66 = new Vector2f(0.37500F, 0.06250F);
        Vector2f v62 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v96 = new Vector2f(0.25000F, 0.81250F);
        Vector2f v90 = new Vector2f(0.37500F, 0.81250F);
        Vector2f v55 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v57 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v73 = new Vector2f(0.25000F, 0.06250F);
        Vector2f v87 = new Vector2f(0.06250F, 0.78125F);
        Vector2f v72 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v97 = new Vector2f(0.18750F, 0.18750F);
        Vector2f v67 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v84 = new Vector2f(0.03125F, 0.78125F);
        Vector2f v82 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v79 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v88 = new Vector2f(0.31250F, 0.25000F);
        Vector2f v76 = new Vector2f(0.25000F, 0.00000F);
        Vector2f v53 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v58 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v54 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v71 = new Vector2f(0.18750F, 0.09375F);
        Vector2f v83 = new Vector2f(0.03125F, 0.25000F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v55, v100, v2, c0, v54, v100, v1, c0, v53, v100, v0, c0, v52, v100));
        builder.add(new SimpleQuad(v2, c0, v54, v101, v5, c0, v57, v101, v4, c0, v56, v101, v1, c0, v53, v101));
        builder.add(new SimpleQuad(v5, c0, v57, v102, v7, c0, v59, v102, v6, c0, v58, v102, v4, c0, v56, v102));
        builder.add(new SimpleQuad(v5, c0, v58, v103, v2, c0, v56, v103, v3, c0, v61, v103, v7, c0, v60, v103));
        builder.add(new SimpleQuad(v7, c0, v59, v104, v3, c0, v63, v104, v0, c0, v62, v104, v6, c0, v58, v104));
        builder.add(new SimpleQuad(v0, c0, v64, v105, v1, c0, v53, v105, v4, c0, v56, v105, v6, c0, v61, v105));
        HILT_QUADS = builder.build();

        // HiltNoTint
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v11, c1, v68, v104, v10, c1, v67, v104, v9, c1, v66, v104, v8, c1, v65, v104));
        builder.add(new SimpleQuad(v10, c1, v72, v100, v13, c1, v71, v100, v12, c1, v70, v100, v9, c1, v69, v100));
        builder.add(new SimpleQuad(v13, c1, v71, v101, v15, c1, v74, v101, v14, c1, v73, v101, v12, c1, v70, v101));
        builder.add(new SimpleQuad(v15, c1, v74, v102, v11, c1, v68, v102, v8, c1, v65, v102, v14, c1, v73, v102));
        builder.add(new SimpleQuad(v8, c1, v76, v105, v9, c1, v75, v105, v12, c1, v70, v105, v14, c1, v73, v105));
        builder.add(new SimpleQuad(v10, c1, v76, v103, v11, c1, v77, v103, v15, c1, v65, v103, v13, c1, v73, v103));
        HILT_NO_TINT_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c2, v81, v104, v18, c2, v80, v104, v17, c2, v79, v104, v16, c2, v78, v104));
        builder.add(new SimpleQuad(v18, c2, v85, v100, v21, c2, v84, v100, v20, c2, v83, v100, v17, c2, v82, v100));
        builder.add(new SimpleQuad(v21, c2, v84, v101, v23, c2, v87, v101, v22, c2, v86, v101, v20, c2, v83, v101));
        builder.add(new SimpleQuad(v23, c2, v87, v102, v19, c2, v81, v102, v16, c2, v78, v102, v22, c2, v86, v102));
        builder.add(new SimpleQuad(v16, c2, v57, v105, v17, c2, v54, v105, v20, c2, v83, v105, v22, c2, v86, v105));
        builder.add(new SimpleQuad(v19, c2, v59, v103, v23, c2, v78, v103, v21, c2, v86, v103, v18, c2, v57, v103));
        BLADE_IN_QUADS = builder.build();

        // BladeOut1
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c3, v91, v104, v26, c3, v90, v104, v25, c3, v89, v104, v24, c3, v88, v104));
        builder.add(new SimpleQuad(v26, c3, v94, v100, v29, c3, v93, v100, v28, c3, v92, v100, v25, c3, v79, v100));
        builder.add(new SimpleQuad(v29, c3, v93, v101, v31, c3, v96, v101, v30, c3, v95, v101, v28, c3, v92, v101));
        builder.add(new SimpleQuad(v31, c3, v96, v102, v27, c3, v91, v102, v24, c3, v88, v102, v30, c3, v95, v102));
        builder.add(new SimpleQuad(v24, c3, v98, v105, v25, c3, v97, v105, v28, c3, v92, v105, v30, c3, v95, v105));
        builder.add(new SimpleQuad(v26, c3, v98, v103, v27, c3, v99, v103, v31, c3, v88, v103, v29, c3, v95, v103));
        BLADE_OUT_1_QUADS = builder.build();

        // BladeOut2
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c4, v91, v104, v34, c4, v90, v104, v33, c4, v89, v104, v32, c4, v88, v104));
        builder.add(new SimpleQuad(v34, c4, v94, v100, v37, c4, v93, v100, v36, c4, v92, v100, v33, c4, v79, v100));
        builder.add(new SimpleQuad(v37, c4, v93, v101, v39, c4, v96, v101, v38, c4, v95, v101, v36, c4, v92, v101));
        builder.add(new SimpleQuad(v39, c4, v96, v102, v35, c4, v91, v102, v32, c4, v88, v102, v38, c4, v95, v102));
        builder.add(new SimpleQuad(v32, c4, v98, v105, v33, c4, v97, v105, v36, c4, v92, v105, v38, c4, v95, v105));
        builder.add(new SimpleQuad(v34, c4, v98, v103, v35, c4, v99, v103, v39, c4, v88, v103, v37, c4, v95, v103));
        BLADE_OUT_2_QUADS = builder.build();

        // BladeOut3
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v43, c5, v91, v104, v42, c5, v90, v104, v41, c5, v89, v104, v40, c5, v88, v104));
        builder.add(new SimpleQuad(v42, c5, v94, v100, v45, c5, v93, v100, v44, c5, v92, v100, v41, c5, v79, v100));
        builder.add(new SimpleQuad(v45, c5, v93, v101, v47, c5, v96, v101, v46, c5, v95, v101, v44, c5, v92, v101));
        builder.add(new SimpleQuad(v47, c5, v96, v102, v43, c5, v91, v102, v40, c5, v88, v102, v46, c5, v95, v102));
        builder.add(new SimpleQuad(v40, c5, v98, v105, v41, c5, v97, v105, v44, c5, v92, v105, v46, c5, v95, v105));
        builder.add(new SimpleQuad(v42, c5, v98, v103, v43, c5, v99, v103, v47, c5, v88, v103, v45, c5, v95, v103));
        BLADE_OUT_3_QUADS = builder.build();

        // BladeOut4
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v8, c6, v91, v104, v9, c6, v90, v104, v49, c6, v89, v104, v48, c6, v88, v104));
        builder.add(new SimpleQuad(v9, c6, v94, v100, v12, c6, v93, v100, v50, c6, v92, v100, v49, c6, v79, v100));
        builder.add(new SimpleQuad(v12, c6, v93, v101, v14, c6, v96, v101, v51, c6, v95, v101, v50, c6, v92, v101));
        builder.add(new SimpleQuad(v14, c6, v96, v102, v8, c6, v91, v102, v48, c6, v88, v102, v51, c6, v95, v102));
        builder.add(new SimpleQuad(v48, c6, v98, v105, v49, c6, v97, v105, v50, c6, v92, v105, v51, c6, v95, v105));
        builder.add(new SimpleQuad(v9, c6, v98, v103, v8, c6, v99, v103, v14, c6, v88, v103, v12, c6, v95, v103));
        BLADE_OUT_4_QUADS = builder.build();
    }
}
