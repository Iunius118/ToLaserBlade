package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.github.iunius118.tolaserblade.common.util.Color4F;
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
    public static final List<SimpleQuad> BLADE_OUT_1_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_2_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_3_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_4_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int noTintColor = 0xFFFFFFFF;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);
        renderQuads(matrices, currentBuffer, HILT_NO_TINT_QUADS, noTintColor, light, overlay);

        if (color.isBroken) {
            return;
        }

        // Twinkle outer blades
        Color4F innerColor = Color4F.of(color.innerColor);
        Color4F outerColor = Color4F.of(color.outerColor);
        float f = (Util.getMillis() & 0x7FF) / 1024F - 1;   // -1 <= f <= 1
        float alpha = (0.875F - (f * f * f - f) * 0.325F) * outerColor.a();

        // Blend colors
        float outRed   = outerColor.r();
        float outGreen = outerColor.g();
        float outBlue  = outerColor.b();
        float inRed = innerColor.r();
        float inGreen = innerColor.g();
        float inBlue = innerColor.b();

        float r1 = Math.min(outRed   + inRed   / 2F, 1F);
        float g1 = Math.min(outGreen + inGreen / 2F, 1F);
        float b1 = Math.min(outBlue  + inBlue  / 2F, 1F);
        float r2 = Math.min(outRed   + inRed   / 4F, 1F);
        float g2 = Math.min(outGreen + inGreen / 4F, 1F);
        float b2 = Math.min(outBlue  + inBlue  / 4F, 1F);
        float r3 = Math.min(outRed   + inRed   / 8F, 1F);
        float g3 = Math.min(outGreen + inGreen / 8F, 1F);
        float b3 = Math.min(outBlue  + inBlue  / 8F, 1F);

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrices, currentBuffer, BLADE_OUT_1_QUADS, fullLight, overlay, outRed, outGreen, outBlue, alpha);
        renderQuads(matrices, currentBuffer, BLADE_OUT_2_QUADS, fullLight, overlay, r3, g3, b3, alpha);
        renderQuads(matrices, currentBuffer, BLADE_OUT_3_QUADS, fullLight, overlay, r2, g2, b2, alpha);
        renderQuads(matrices, currentBuffer, BLADE_OUT_4_QUADS, fullLight, overlay, r1, g1, b1, alpha);

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, fullLight, overlay, inRed, inGreen, inBlue, innerColor.a());
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // Hilt color
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // HiltNoTint color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.2F); // BladeOut1 color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.2F); // BladeOut2 color
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.25F); // BladeOut3 color
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.25F); // BladeOut4 color
        Vector4f c6 = new Vector4f(1F, 1F, 1F, 0.9F); // BladeIn color

        Vector3f v17 = new Vector3f(-0.0625F, 1.470703125F, -0.0625F);
        Vector3f v35 = new Vector3f(0.046875F, 1.44921875F, -0.046875F);
        Vector3f v29 = new Vector3f(0.0546875F, 0.3828125F, -0.0546875F);
        Vector3f v38 = new Vector3f(0.046875F, 0.3828125F, -0.046875F);
        Vector3f v4 = new Vector3f(0.03125F, 0.3125F, -0.03125F);
        Vector3f v0 = new Vector3f(-0.03125F, 0.3125F, 0.03125F);
        Vector3f v33 = new Vector3f(0.0546875F, 0.3828125F, 0.0546875F);
        Vector3f v28 = new Vector3f(-0.0546875F, 0.3828125F, -0.0546875F);
        Vector3f v53 = new Vector3f(-0.03125F, 1.4296875F, 0.03125F);
        Vector3f v21 = new Vector3f(-0.0625F, 0.3828125F, 0.0625F);
        Vector3f v123 = new Vector3f(0.12309F, 0.98473F, -0.12309F);
        Vector3f v34 = new Vector3f(0F, 1.47265625F, 0F);
        Vector3f v15 = new Vector3f(-0.0625F, 0.3125F, -0.0625F);
        Vector3f v52 = new Vector3f(0F, 1.44921875F, 0F);
        Vector3f v135 = new Vector3f(0.19245F, 0.96225F, -0.19245F);
        Vector3f v46 = new Vector3f(-0.0390625F, 0.3828125F, -0.0390625F);
        Vector3f v39 = new Vector3f(-0.046875F, 1.44921875F, 0.046875F);
        Vector3f v118 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v27 = new Vector3f(-0.0546875F, 1.458984375F, -0.0546875F);
        Vector3f v13 = new Vector3f(0.0625F, 0.3125F, -0.0625F);
        Vector3f v24 = new Vector3f(0F, 0.3671875F, 0F);
        Vector3f v10 = new Vector3f(0.0625F, 0.3125F, 0.0625F);
        Vector3f v12 = new Vector3f(0.0625F, 0.375F, -0.0625F);
        Vector3f v44 = new Vector3f(0.0390625F, 1.439453125F, -0.0390625F);
        Vector3f v36 = new Vector3f(-0.046875F, 1.44921875F, -0.046875F);
        Vector3f v8 = new Vector3f(-0.0625F, 0.375F, 0.0625F);
        Vector3f v14 = new Vector3f(-0.0625F, 0.375F, -0.0625F);
        Vector3f v1 = new Vector3f(0.03125F, 0.3125F, 0.03125F);
        Vector3f v137 = new Vector3f(-0.19245F, 0.96225F, 0.19245F);
        Vector3f v54 = new Vector3f(0.03125F, 1.4296875F, 0.03125F);
        Vector3f v133 = new Vector3f(-0.16222F, 0.97333F, 0.16222F);
        Vector3f v130 = new Vector3f(0.14003F, 0.98020F, 0.14003F);
        Vector3f v139 = new Vector3f(0.23570F, -0.94281F, 0.23570F);
        Vector3f v142 = new Vector3f(-0.23570F, -0.94281F, 0.23570F);
        Vector3f v43 = new Vector3f(0F, 1.4609375F, 0F);
        Vector3f v119 = new Vector3f(-0.12309F, -0.98473F, -0.12309F);
        Vector3f v26 = new Vector3f(0.0546875F, 1.458984375F, -0.0546875F);
        Vector3f v131 = new Vector3f(0.16222F, 0.97333F, -0.16222F);
        Vector3f v141 = new Vector3f(-0.23570F, -0.94281F, -0.23570F);
        Vector3f v140 = new Vector3f(0.23570F, -0.94281F, -0.23570F);
        Vector3f v7 = new Vector3f(-0.03125F, -0.0625F, -0.03125F);
        Vector3f v5 = new Vector3f(0.03125F, -0.0625F, -0.03125F);
        Vector3f v6 = new Vector3f(-0.03125F, 0.3125F, -0.03125F);
        Vector3f v57 = new Vector3f(0.03125F, 1.4296875F, -0.03125F);
        Vector3f v138 = new Vector3f(0.19245F, 0.96225F, 0.19245F);
        Vector3f v61 = new Vector3f(0F, 1.4375F, 0F);
        Vector3f v55 = new Vector3f(0.03125F, 0.3828125F, 0.03125F);
        Vector3f v129 = new Vector3f(-0.14003F, 0.98020F, 0.14003F);
        Vector3f v134 = new Vector3f(0.16222F, 0.97333F, 0.16222F);
        Vector3f v20 = new Vector3f(-0.0625F, 1.470703125F, 0.0625F);
        Vector3f v30 = new Vector3f(-0.0546875F, 1.458984375F, 0.0546875F);
        Vector3f v132 = new Vector3f(-0.16222F, 0.97333F, -0.16222F);
        Vector3f v37 = new Vector3f(-0.046875F, 0.3828125F, -0.046875F);
        Vector3f v31 = new Vector3f(-0.0546875F, 0.3828125F, 0.0546875F);
        Vector3f v47 = new Vector3f(0.0390625F, 0.3828125F, -0.0390625F);
        Vector3f v127 = new Vector3f(0.14003F, 0.98020F, -0.14003F);
        Vector3f v116 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v58 = new Vector3f(0.03125F, 0.3828125F, -0.03125F);
        Vector3f v126 = new Vector3f(0.12309F, 0.98473F, 0.12309F);
        Vector3f v2 = new Vector3f(0.03125F, -0.0625F, 0.03125F);
        Vector3f v25 = new Vector3f(0F, 1.486328125F, 0F);
        Vector3f v32 = new Vector3f(0.0546875F, 1.458984375F, 0.0546875F);
        Vector3f v120 = new Vector3f(0.12309F, -0.98473F, -0.12309F);
        Vector3f v125 = new Vector3f(-0.12309F, 0.98473F, 0.12309F);
        Vector3f v56 = new Vector3f(-0.03125F, 0.3828125F, 0.03125F);
        Vector3f v122 = new Vector3f(-0.12309F, -0.98473F, 0.12309F);
        Vector3f v121 = new Vector3f(0.12309F, -0.98473F, 0.12309F);
        Vector3f v18 = new Vector3f(-0.0625F, 0.3828125F, -0.0625F);
        Vector3f v115 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v19 = new Vector3f(0.0625F, 0.3828125F, -0.0625F);
        Vector3f v3 = new Vector3f(-0.03125F, -0.0625F, 0.03125F);
        Vector3f v113 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v136 = new Vector3f(-0.19245F, 0.96225F, -0.19245F);
        Vector3f v23 = new Vector3f(0.0625F, 0.3828125F, 0.0625F);
        Vector3f v60 = new Vector3f(-0.03125F, 0.3828125F, -0.03125F);
        Vector3f v51 = new Vector3f(0.0390625F, 0.3828125F, 0.0390625F);
        Vector3f v16 = new Vector3f(0.0625F, 1.470703125F, -0.0625F);
        Vector3f v124 = new Vector3f(-0.12309F, 0.98473F, -0.12309F);
        Vector3f v22 = new Vector3f(0.0625F, 1.470703125F, 0.0625F);
        Vector3f v117 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v48 = new Vector3f(-0.0390625F, 1.439453125F, 0.0390625F);
        Vector3f v11 = new Vector3f(-0.0625F, 0.3125F, 0.0625F);
        Vector3f v49 = new Vector3f(-0.0390625F, 0.3828125F, 0.0390625F);
        Vector3f v50 = new Vector3f(0.0390625F, 1.439453125F, 0.0390625F);
        Vector3f v45 = new Vector3f(-0.0390625F, 1.439453125F, -0.0390625F);
        Vector3f v9 = new Vector3f(0.0625F, 0.375F, 0.0625F);
        Vector3f v40 = new Vector3f(-0.046875F, 0.3828125F, 0.046875F);
        Vector3f v114 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v59 = new Vector3f(-0.03125F, 1.4296875F, -0.03125F);
        Vector3f v128 = new Vector3f(-0.14003F, 0.98020F, -0.14003F);
        Vector3f v42 = new Vector3f(0.046875F, 0.3828125F, 0.046875F);
        Vector3f v41 = new Vector3f(0.046875F, 1.44921875F, 0.046875F);
        Vector2f v99 = new Vector2f(0.43750F, 0.21875F);
        Vector2f v67 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v94 = new Vector2f(0.25000F, 0.28125F);
        Vector2f v102 = new Vector2f(0.18750F, 0.28125F);
        Vector2f v80 = new Vector2f(0.18750F, 0.09375F);
        Vector2f v71 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v77 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v84 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v76 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v93 = new Vector2f(0.25000F, 0.81250F);
        Vector2f v82 = new Vector2f(0.25000F, 0.06250F);
        Vector2f v100 = new Vector2f(0.34375F, 0.25000F);
        Vector2f v85 = new Vector2f(0.25000F, 0.00000F);
        Vector2f v112 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v111 = new Vector2f(0.15625F, 0.25000F);
        Vector2f v74 = new Vector2f(0.31250F, 0.06250F);
        Vector2f v110 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v62 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v72 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v87 = new Vector2f(0.50000F, 0.28125F);
        Vector2f v108 = new Vector2f(0.12500F, 0.28125F);
        Vector2f v66 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v83 = new Vector2f(0.25000F, 0.09375F);
        Vector2f v107 = new Vector2f(0.00000F, 0.81250F);
        Vector2f v90 = new Vector2f(0.43750F, 0.28125F);
        Vector2f v75 = new Vector2f(0.37500F, 0.06250F);
        Vector2f v103 = new Vector2f(0.18750F, 0.81250F);
        Vector2f v97 = new Vector2f(0.40625F, 0.25000F);
        Vector2f v105 = new Vector2f(0.06250F, 0.28125F);
        Vector2f v96 = new Vector2f(0.37500F, 0.81250F);
        Vector2f v92 = new Vector2f(0.31250F, 0.81250F);
        Vector2f v86 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v68 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v69 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v81 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v104 = new Vector2f(0.00000F, 0.28125F);
        Vector2f v65 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v101 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v78 = new Vector2f(0.12500F, 0.06250F);
        Vector2f v98 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v63 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v106 = new Vector2f(0.06250F, 0.81250F);
        Vector2f v95 = new Vector2f(0.37500F, 0.28125F);
        Vector2f v70 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v91 = new Vector2f(0.31250F, 0.28125F);
        Vector2f v73 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v64 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v88 = new Vector2f(0.50000F, 0.81250F);
        Vector2f v89 = new Vector2f(0.43750F, 0.81250F);
        Vector2f v109 = new Vector2f(0.12500F, 0.81250F);
        Vector2f v79 = new Vector2f(0.18750F, 0.06250F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v65, v113, v2, c0, v64, v113, v1, c0, v63, v113, v0, c0, v62, v113));
        builder.add(new SimpleQuad(v2, c0, v69, v114, v5, c0, v68, v114, v4, c0, v67, v114, v1, c0, v66, v114));
        builder.add(new SimpleQuad(v5, c0, v68, v115, v7, c0, v71, v115, v6, c0, v70, v115, v4, c0, v67, v115));
        builder.add(new SimpleQuad(v7, c0, v71, v116, v3, c0, v65, v116, v0, c0, v62, v116, v6, c0, v70, v116));
        builder.add(new SimpleQuad(v7, c0, v62, v117, v5, c0, v70, v117, v2, c0, v73, v117, v3, c0, v72, v117));
        HILT_QUADS = builder.build();

        // HiltNoTint
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v11, c1, v77, v113, v10, c1, v76, v113, v9, c1, v75, v113, v8, c1, v74, v113));
        builder.add(new SimpleQuad(v10, c1, v81, v114, v13, c1, v80, v114, v12, c1, v79, v114, v9, c1, v78, v114));
        builder.add(new SimpleQuad(v13, c1, v80, v115, v15, c1, v83, v115, v14, c1, v82, v115, v12, c1, v79, v115));
        builder.add(new SimpleQuad(v15, c1, v83, v116, v11, c1, v77, v116, v8, c1, v74, v116, v14, c1, v82, v116));
        builder.add(new SimpleQuad(v8, c1, v85, v118, v9, c1, v84, v118, v12, c1, v79, v118, v14, c1, v82, v118));
        builder.add(new SimpleQuad(v11, c1, v86, v117, v15, c1, v74, v117, v13, c1, v82, v117, v10, c1, v85, v117));
        HILT_NO_TINT_QUADS = builder.build();

        // BladeOut1
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v17, c2, v90, v113, v18, c2, v89, v113, v19, c2, v88, v113, v16, c2, v87, v113));
        builder.add(new SimpleQuad(v20, c2, v94, v114, v21, c2, v93, v114, v18, c2, v92, v114, v17, c2, v91, v114));
        builder.add(new SimpleQuad(v22, c2, v91, v115, v23, c2, v92, v115, v21, c2, v96, v115, v20, c2, v95, v115));
        builder.add(new SimpleQuad(v16, c2, v95, v116, v19, c2, v96, v116, v23, c2, v89, v116, v22, c2, v90, v116));
        builder.add(new SimpleQuad(v21, c2, v99, v123, v23, c2, v98, v124, v24, c2, v97, v118, v24, c2, v97, v118));
        builder.add(new SimpleQuad(v23, c2, v98, v124, v19, c2, v95, v125, v24, c2, v97, v118, v24, c2, v97, v118));
        builder.add(new SimpleQuad(v19, c2, v95, v125, v18, c2, v90, v126, v24, c2, v97, v118, v24, c2, v97, v118));
        builder.add(new SimpleQuad(v18, c2, v90, v126, v21, c2, v99, v123, v24, c2, v97, v118, v24, c2, v97, v118));
        builder.add(new SimpleQuad(v16, c2, v91, v122, v22, c2, v101, v119, v25, c2, v100, v117, v25, c2, v100, v117));
        builder.add(new SimpleQuad(v22, c2, v101, v119, v20, c2, v98, v120, v25, c2, v100, v117, v25, c2, v100, v117));
        builder.add(new SimpleQuad(v20, c2, v98, v120, v17, c2, v95, v121, v25, c2, v100, v117, v25, c2, v100, v117));
        builder.add(new SimpleQuad(v17, c2, v95, v121, v16, c2, v91, v122, v25, c2, v100, v117, v25, c2, v100, v117));
        BLADE_OUT_1_QUADS = builder.build();

        // BladeOut2
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c3, v90, v113, v28, c3, v89, v113, v29, c3, v88, v113, v26, c3, v87, v113));
        builder.add(new SimpleQuad(v30, c3, v94, v114, v31, c3, v93, v114, v28, c3, v92, v114, v27, c3, v91, v114));
        builder.add(new SimpleQuad(v32, c3, v91, v115, v33, c3, v92, v115, v31, c3, v96, v115, v30, c3, v95, v115));
        builder.add(new SimpleQuad(v26, c3, v95, v116, v29, c3, v96, v116, v33, c3, v89, v116, v32, c3, v90, v116));
        builder.add(new SimpleQuad(v31, c3, v99, v127, v33, c3, v98, v128, v24, c3, v97, v118, v24, c3, v97, v118));
        builder.add(new SimpleQuad(v33, c3, v98, v128, v29, c3, v95, v129, v24, c3, v97, v118, v24, c3, v97, v118));
        builder.add(new SimpleQuad(v29, c3, v95, v129, v28, c3, v90, v130, v24, c3, v97, v118, v24, c3, v97, v118));
        builder.add(new SimpleQuad(v28, c3, v90, v130, v31, c3, v99, v127, v24, c3, v97, v118, v24, c3, v97, v118));
        builder.add(new SimpleQuad(v26, c3, v91, v122, v32, c3, v101, v119, v34, c3, v100, v117, v34, c3, v100, v117));
        builder.add(new SimpleQuad(v32, c3, v101, v119, v30, c3, v98, v120, v34, c3, v100, v117, v34, c3, v100, v117));
        builder.add(new SimpleQuad(v30, c3, v98, v120, v27, c3, v95, v121, v34, c3, v100, v117, v34, c3, v100, v117));
        builder.add(new SimpleQuad(v27, c3, v95, v121, v26, c3, v91, v122, v34, c3, v100, v117, v34, c3, v100, v117));
        BLADE_OUT_2_QUADS = builder.build();

        // BladeOut3
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v36, c4, v90, v113, v37, c4, v89, v113, v38, c4, v88, v113, v35, c4, v87, v113));
        builder.add(new SimpleQuad(v39, c4, v94, v114, v40, c4, v93, v114, v37, c4, v92, v114, v36, c4, v91, v114));
        builder.add(new SimpleQuad(v41, c4, v91, v115, v42, c4, v92, v115, v40, c4, v96, v115, v39, c4, v95, v115));
        builder.add(new SimpleQuad(v35, c4, v95, v116, v38, c4, v96, v116, v42, c4, v89, v116, v41, c4, v90, v116));
        builder.add(new SimpleQuad(v40, c4, v99, v131, v42, c4, v98, v132, v24, c4, v97, v118, v24, c4, v97, v118));
        builder.add(new SimpleQuad(v42, c4, v98, v132, v38, c4, v95, v133, v24, c4, v97, v118, v24, c4, v97, v118));
        builder.add(new SimpleQuad(v38, c4, v95, v133, v37, c4, v90, v134, v24, c4, v97, v118, v24, c4, v97, v118));
        builder.add(new SimpleQuad(v37, c4, v90, v134, v40, c4, v99, v131, v24, c4, v97, v118, v24, c4, v97, v118));
        builder.add(new SimpleQuad(v35, c4, v91, v122, v41, c4, v101, v119, v43, c4, v100, v117, v43, c4, v100, v117));
        builder.add(new SimpleQuad(v41, c4, v101, v119, v39, c4, v98, v120, v43, c4, v100, v117, v43, c4, v100, v117));
        builder.add(new SimpleQuad(v39, c4, v98, v120, v36, c4, v95, v121, v43, c4, v100, v117, v43, c4, v100, v117));
        builder.add(new SimpleQuad(v36, c4, v95, v121, v35, c4, v91, v122, v43, c4, v100, v117, v43, c4, v100, v117));
        BLADE_OUT_3_QUADS = builder.build();

        // BladeOut4
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v45, c5, v90, v113, v46, c5, v89, v113, v47, c5, v88, v113, v44, c5, v87, v113));
        builder.add(new SimpleQuad(v48, c5, v94, v114, v49, c5, v93, v114, v46, c5, v92, v114, v45, c5, v91, v114));
        builder.add(new SimpleQuad(v50, c5, v91, v115, v51, c5, v92, v115, v49, c5, v96, v115, v48, c5, v95, v115));
        builder.add(new SimpleQuad(v44, c5, v95, v116, v47, c5, v96, v116, v51, c5, v89, v116, v50, c5, v90, v116));
        builder.add(new SimpleQuad(v49, c5, v99, v135, v51, c5, v98, v136, v24, c5, v97, v118, v24, c5, v97, v118));
        builder.add(new SimpleQuad(v51, c5, v98, v136, v47, c5, v95, v137, v24, c5, v97, v118, v24, c5, v97, v118));
        builder.add(new SimpleQuad(v47, c5, v95, v137, v46, c5, v90, v138, v24, c5, v97, v118, v24, c5, v97, v118));
        builder.add(new SimpleQuad(v46, c5, v90, v138, v49, c5, v99, v135, v24, c5, v97, v118, v24, c5, v97, v118));
        builder.add(new SimpleQuad(v44, c5, v91, v122, v50, c5, v101, v119, v52, c5, v100, v117, v52, c5, v100, v117));
        builder.add(new SimpleQuad(v50, c5, v101, v119, v48, c5, v98, v120, v52, c5, v100, v117, v52, c5, v100, v117));
        builder.add(new SimpleQuad(v48, c5, v98, v120, v45, c5, v95, v121, v52, c5, v100, v117, v52, c5, v100, v117));
        builder.add(new SimpleQuad(v45, c5, v95, v121, v44, c5, v91, v122, v52, c5, v100, v117, v52, c5, v100, v117));
        BLADE_OUT_4_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v56, c6, v103, v113, v55, c6, v93, v113, v54, c6, v94, v113, v53, c6, v102, v113));
        builder.add(new SimpleQuad(v55, c6, v107, v114, v58, c6, v106, v114, v57, c6, v105, v114, v54, c6, v104, v114));
        builder.add(new SimpleQuad(v58, c6, v106, v115, v60, c6, v109, v115, v59, c6, v108, v115, v57, c6, v105, v115));
        builder.add(new SimpleQuad(v60, c6, v109, v116, v56, c6, v103, v116, v53, c6, v102, v116, v59, c6, v108, v116));
        builder.add(new SimpleQuad(v59, c6, v108, v124, v53, c6, v64, v125, v61, c6, v110, v118, v61, c6, v110, v118));
        builder.add(new SimpleQuad(v53, c6, v64, v125, v54, c6, v71, v126, v61, c6, v110, v118, v61, c6, v110, v118));
        builder.add(new SimpleQuad(v54, c6, v71, v126, v57, c6, v105, v123, v61, c6, v110, v118, v61, c6, v110, v118));
        builder.add(new SimpleQuad(v57, c6, v105, v123, v59, c6, v108, v124, v61, c6, v110, v118, v61, c6, v110, v118));
        builder.add(new SimpleQuad(v58, c6, v108, v140, v55, c6, v64, v139, v24, c6, v111, v117, v24, c6, v111, v117));
        builder.add(new SimpleQuad(v55, c6, v64, v139, v56, c6, v112, v142, v24, c6, v111, v117, v24, c6, v111, v117));
        builder.add(new SimpleQuad(v56, c6, v112, v142, v60, c6, v102, v141, v24, c6, v111, v117, v24, c6, v111, v117));
        builder.add(new SimpleQuad(v60, c6, v102, v141, v58, c6, v108, v140, v24, c6, v111, v117, v24, c6, v111, v117));
        BLADE_IN_QUADS = builder.build();
    }
}
