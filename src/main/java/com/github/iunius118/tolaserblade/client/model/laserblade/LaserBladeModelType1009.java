package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType1009 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1009.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_OFF_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int offColor = 0xFFCCCCCC;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);
            renderQuads(matrixStack, currentBuffer, BLADE_OFF_QUADS, offColor, lightmapCoord, overlayColor);
            return;
        }

        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_OFF_QUADS, offColor, fullLight, overlayColor);

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v39 = new Vector3f(-0.093750F, -0.006250F, 0.125000F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v40 = new Vector3f(0.093750F, 1.437500F, -0.125000F);
        Vector3f v21 = new Vector3f(0.031250F, 0.375000F, -0.062500F);
        Vector3f v32 = new Vector3f(0.062500F, 1.406250F, -0.093750F);
        Vector3f v25 = new Vector3f(0.031250F, 1.375000F, 0.062500F);
        Vector3f v35 = new Vector3f(-0.062500F, 0.031250F, -0.093750F);
        Vector3f v24 = new Vector3f(-0.031250F, 1.375000F, 0.062500F);
        Vector3f v33 = new Vector3f(0.062500F, 0.031250F, -0.093750F);
        Vector3f v0 = new Vector3f(-0.031250F, 0.375000F, 0.118750F);
        Vector3f v38 = new Vector3f(0.093750F, -0.006250F, 0.125000F);
        Vector3f v2 = new Vector3f(0.031250F, 0.312500F, 0.118750F);
        Vector3f v30 = new Vector3f(0.062500F, 0.031250F, 0.093750F);
        Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v123 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v121 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v31 = new Vector3f(-0.062500F, 0.031250F, 0.093750F);
        Vector3f v18 = new Vector3f(0.031250F, 0.375000F, 0.062500F);
        Vector3f v29 = new Vector3f(0.062500F, 1.406250F, 0.093750F);
        Vector3f v26 = new Vector3f(0.031250F, 1.375000F, -0.062500F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v34 = new Vector3f(-0.062500F, 1.406250F, -0.093750F);
        Vector3f v20 = new Vector3f(0.031250F, 0.687500F, -0.062500F);
        Vector3f v122 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v5 = new Vector3f(0.031250F, 0.312500F, -0.118750F);
        Vector3f v41 = new Vector3f(0.093750F, -0.006250F, -0.125000F);
        Vector3f v42 = new Vector3f(-0.093750F, 1.437500F, -0.125000F);
        Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v43 = new Vector3f(-0.093750F, -0.006250F, -0.125000F);
        Vector3f v37 = new Vector3f(0.093750F, 1.437500F, 0.125000F);
        Vector3f v36 = new Vector3f(-0.093750F, 1.437500F, 0.125000F);
        Vector3f v124 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v23 = new Vector3f(-0.031250F, 0.375000F, -0.062500F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.375000F, -0.118750F);
        Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v4 = new Vector3f(0.031250F, 0.375000F, -0.118750F);
        Vector3f v19 = new Vector3f(-0.031250F, 0.375000F, 0.062500F);
        Vector3f v17 = new Vector3f(0.031250F, 0.687500F, 0.062500F);
        Vector3f v28 = new Vector3f(-0.062500F, 1.406250F, 0.093750F);
        Vector3f v1 = new Vector3f(0.031250F, 0.375000F, 0.118750F);
        Vector3f v27 = new Vector3f(-0.031250F, 1.375000F, -0.062500F);
        Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v7 = new Vector3f(-0.031250F, 0.312500F, -0.118750F);
        Vector3f v125 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v16 = new Vector3f(-0.031250F, 0.687500F, 0.062500F);
        Vector3f v22 = new Vector3f(-0.031250F, 0.687500F, -0.062500F);
        Vector3f v3 = new Vector3f(-0.031250F, 0.312500F, 0.118750F);
        Vector3f v120 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector2f v52 = new Vector2f(0.28125F, 0.09375F);
        Vector2f v59 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v48 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v107 = new Vector2f(0.84375F, 0.25000F);
        Vector2f v93 = new Vector2f(0.12500F, 0.25000F);
        Vector2f v105 = new Vector2f(0.40625F, 0.25000F);
        Vector2f v101 = new Vector2f(0.34375F, 0.25000F);
        Vector2f v45 = new Vector2f(0.43750F, 0.09375F);
        Vector2f v84 = new Vector2f(0.12500F, 0.59375F);
        Vector2f v82 = new Vector2f(0.09375F, 0.59375F);
        Vector2f v71 = new Vector2f(0.15625F, 0.65625F);
        Vector2f v63 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v68 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v74 = new Vector2f(0.15625F, 0.81250F);
        Vector2f v91 = new Vector2f(0.00000F, 0.59375F);
        Vector2f v72 = new Vector2f(0.18750F, 0.65625F);
        Vector2f v44 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v96 = new Vector2f(0.50000F, 0.96875F);
        Vector2f v62 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v55 = new Vector2f(0.28125F, 0.00000F);
        Vector2f v65 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v90 = new Vector2f(0.06250F, 0.25000F);
        Vector2f v76 = new Vector2f(0.06250F, 0.65625F);
        Vector2f v70 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v104 = new Vector2f(0.34375F, 0.15625F);
        Vector2f v77 = new Vector2f(0.06250F, 0.81250F);
        Vector2f v49 = new Vector2f(0.25000F, 0.09375F);
        Vector2f v118 = new Vector2f(0.81250F, 0.12500F);
        Vector2f v117 = new Vector2f(0.71875F, 0.12500F);
        Vector2f v64 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v116 = new Vector2f(0.62500F, 0.12500F);
        Vector2f v94 = new Vector2f(0.43750F, 0.25000F);
        Vector2f v115 = new Vector2f(0.71875F, 1.00000F);
        Vector2f v102 = new Vector2f(0.34375F, 0.96875F);
        Vector2f v56 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v89 = new Vector2f(0.00000F, 0.25000F);
        Vector2f v113 = new Vector2f(0.50000F, 1.00000F);
        Vector2f v57 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v112 = new Vector2f(0.62500F, 1.00000F);
        Vector2f v60 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v111 = new Vector2f(0.62500F, 0.25000F);
        Vector2f v110 = new Vector2f(0.84375F, 1.00000F);
        Vector2f v50 = new Vector2f(0.25000F, 0.12500F);
        Vector2f v51 = new Vector2f(0.12500F, 0.12500F);
        Vector2f v66 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v109 = new Vector2f(0.93750F, 1.00000F);
        Vector2f v108 = new Vector2f(0.93750F, 0.25000F);
        Vector2f v85 = new Vector2f(0.15625F, 0.25000F);
        Vector2f v83 = new Vector2f(0.12500F, 0.65625F);
        Vector2f v79 = new Vector2f(0.09375F, 0.65625F);
        Vector2f v103 = new Vector2f(0.28125F, 0.15625F);
        Vector2f v114 = new Vector2f(0.71875F, 0.25000F);
        Vector2f v97 = new Vector2f(0.43750F, 0.96875F);
        Vector2f v46 = new Vector2f(0.43750F, 0.12500F);
        Vector2f v75 = new Vector2f(0.00000F, 0.65625F);
        Vector2f v47 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v99 = new Vector2f(0.28125F, 0.96875F);
        Vector2f v92 = new Vector2f(0.09375F, 0.25000F);
        Vector2f v100 = new Vector2f(0.18750F, 0.96875F);
        Vector2f v78 = new Vector2f(0.00000F, 0.81250F);
        Vector2f v61 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v95 = new Vector2f(0.50000F, 0.25000F);
        Vector2f v106 = new Vector2f(0.40625F, 0.15625F);
        Vector2f v54 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v119 = new Vector2f(0.81250F, 0.25000F);
        Vector2f v53 = new Vector2f(0.28125F, 0.12500F);
        Vector2f v80 = new Vector2f(0.09375F, 0.81250F);
        Vector2f v81 = new Vector2f(0.06250F, 0.59375F);
        Vector2f v67 = new Vector2f(0.25000F, 0.00000F);
        Vector2f v73 = new Vector2f(0.18750F, 0.81250F);
        Vector2f v69 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v88 = new Vector2f(0.15625F, 0.59375F);
        Vector2f v87 = new Vector2f(0.18750F, 0.59375F);
        Vector2f v86 = new Vector2f(0.18750F, 0.25000F);
        Vector2f v98 = new Vector2f(0.28125F, 0.25000F);
        Vector2f v58 = new Vector2f(0.03125F, 0.03125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v47, v120, v2, c0, v46, v120, v1, c0, v45, v120, v0, c0, v44, v120));
        builder.add(new SimpleQuad(v2, c0, v51, v121, v5, c0, v50, v121, v4, c0, v49, v121, v1, c0, v48, v121));
        builder.add(new SimpleQuad(v5, c0, v50, v122, v7, c0, v53, v122, v6, c0, v52, v122, v4, c0, v49, v122));
        builder.add(new SimpleQuad(v7, c0, v53, v123, v3, c0, v47, v123, v0, c0, v44, v123, v6, c0, v52, v123));
        builder.add(new SimpleQuad(v7, c0, v56, v124, v5, c0, v52, v124, v2, c0, v55, v124, v3, c0, v54, v124));
        builder.add(new SimpleQuad(v11, c0, v60, v121, v10, c0, v59, v121, v9, c0, v58, v121, v8, c0, v57, v121));
        builder.add(new SimpleQuad(v10, c0, v59, v122, v13, c0, v62, v122, v12, c0, v61, v122, v9, c0, v58, v122));
        builder.add(new SimpleQuad(v13, c0, v62, v123, v15, c0, v64, v123, v14, c0, v63, v123, v12, c0, v61, v123));
        builder.add(new SimpleQuad(v13, c0, v63, v124, v10, c0, v61, v124, v11, c0, v66, v124, v15, c0, v65, v124));
        builder.add(new SimpleQuad(v0, c0, v55, v125, v1, c0, v67, v125, v4, c0, v49, v125, v6, c0, v52, v125));
        builder.add(new SimpleQuad(v15, c0, v64, v120, v11, c0, v69, v120, v8, c0, v68, v120, v14, c0, v63, v120));
        builder.add(new SimpleQuad(v9, c0, v58, v125, v12, c0, v61, v125, v14, c0, v66, v125, v8, c0, v70, v125));
        HILT_QUADS = builder.build();

        // BladeOff
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c1, v74, v120, v18, c1, v73, v120, v17, c1, v72, v120, v16, c1, v71, v120));
        builder.add(new SimpleQuad(v18, c1, v78, v121, v21, c1, v77, v121, v20, c1, v76, v121, v17, c1, v75, v121));
        builder.add(new SimpleQuad(v21, c1, v77, v122, v23, c1, v80, v122, v22, c1, v79, v122, v20, c1, v76, v122));
        builder.add(new SimpleQuad(v23, c1, v80, v123, v19, c1, v74, v123, v16, c1, v71, v123, v22, c1, v79, v123));
        builder.add(new SimpleQuad(v16, c1, v82, v125, v17, c1, v81, v125, v20, c1, v76, v125, v22, c1, v79, v125));
        builder.add(new SimpleQuad(v19, c1, v84, v124, v23, c1, v83, v124, v21, c1, v79, v124, v18, c1, v82, v124));
        BLADE_OFF_QUADS = builder.build();

        // BladeIn
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v16, c2, v88, v120, v17, c2, v87, v120, v25, c2, v86, v120, v24, c2, v85, v120));
        builder.add(new SimpleQuad(v17, c2, v91, v121, v20, c2, v81, v121, v26, c2, v90, v121, v25, c2, v89, v121));
        builder.add(new SimpleQuad(v20, c2, v81, v122, v22, c2, v82, v122, v27, c2, v92, v122, v26, c2, v90, v122));
        builder.add(new SimpleQuad(v22, c2, v82, v123, v16, c2, v88, v123, v24, c2, v85, v123, v27, c2, v92, v123));
        builder.add(new SimpleQuad(v24, c2, v64, v125, v25, c2, v62, v125, v26, c2, v90, v125, v27, c2, v92, v125));
        builder.add(new SimpleQuad(v16, c2, v69, v124, v22, c2, v93, v124, v20, c2, v92, v124, v17, c2, v64, v124));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v31, c3, v97, v120, v30, c3, v96, v120, v29, c3, v95, v120, v28, c3, v94, v120));
        builder.add(new SimpleQuad(v30, c3, v100, v121, v33, c3, v99, v121, v32, c3, v98, v121, v29, c3, v86, v121));
        builder.add(new SimpleQuad(v33, c3, v99, v122, v35, c3, v102, v122, v34, c3, v101, v122, v32, c3, v98, v122));
        builder.add(new SimpleQuad(v35, c3, v102, v123, v31, c3, v97, v123, v28, c3, v94, v123, v34, c3, v101, v123));
        builder.add(new SimpleQuad(v28, c3, v104, v125, v29, c3, v103, v125, v32, c3, v98, v125, v34, c3, v101, v125));
        builder.add(new SimpleQuad(v30, c3, v104, v124, v31, c3, v106, v124, v35, c3, v105, v124, v33, c3, v101, v124));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v39, c4, v110, v120, v38, c4, v109, v120, v37, c4, v108, v120, v36, c4, v107, v120));
        builder.add(new SimpleQuad(v38, c4, v113, v121, v41, c4, v112, v121, v40, c4, v111, v121, v37, c4, v95, v121));
        builder.add(new SimpleQuad(v41, c4, v112, v122, v43, c4, v115, v122, v42, c4, v114, v122, v40, c4, v111, v122));
        builder.add(new SimpleQuad(v43, c4, v115, v123, v39, c4, v110, v123, v36, c4, v107, v123, v42, c4, v114, v123));
        builder.add(new SimpleQuad(v36, c4, v117, v125, v37, c4, v116, v125, v40, c4, v111, v125, v42, c4, v114, v125));
        builder.add(new SimpleQuad(v43, c4, v119, v124, v41, c4, v114, v124, v38, c4, v117, v124, v39, c4, v118, v124));
        BLADE_OUT_QUADS = builder.build();
    }
}
