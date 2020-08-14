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
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType913 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_913.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_OFF_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int offColor = 0xFFCCCCCC;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_OFF_QUADS, offColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        // Rotate blades
        float angle = Util.milliTime() % 250L * 1.44F;   // 240 rpm
        matrixStack.rotate(new Quaternion(Vector3f.YP, angle, true));

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB_INNER) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v80 = new Vector3f(-0.050000F, 1.387500F, -0.043750F);
        Vector3f v8 = new Vector3f(0.031250F, 1.000000F, 0.031250F);
        Vector3f v37 = new Vector3f(0.125000F, 1.062500F, -0.031250F);
        Vector3f v69 = new Vector3f(-0.043750F, 1.062500F, -0.137500F);
        Vector3f v59 = new Vector3f(0.050000F, 1.062500F, 0.043750F);
        Vector3f v87 = new Vector3f(-0.050000F, 1.062500F, 0.043750F);
        Vector3f v74 = new Vector3f(-0.043750F, 1.062500F, 0.137500F);
        Vector3f v32 = new Vector3f(0.062500F, 1.375000F, 0.031250F);
        Vector3f v149 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v49 = new Vector3f(-0.125000F, 1.375000F, -0.031250F);
        Vector3f v0 = new Vector3f(0.062500F, 0.625000F, 0.062500F);
        Vector3f v38 = new Vector3f(0.062500F, 1.375000F, -0.031250F);
        Vector3f v72 = new Vector3f(-0.043750F, 1.387500F, 0.050000F);
        Vector3f v39 = new Vector3f(0.062500F, 1.062500F, -0.031250F);
        Vector3f v68 = new Vector3f(-0.043750F, 1.387500F, -0.137500F);
        Vector3f v5 = new Vector3f(-0.062500F, 0.000000F, -0.062500F);
        Vector3f v23 = new Vector3f(-0.125000F, 1.000000F, 0.125000F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.625000F, 0.031250F);
        Vector3f v18 = new Vector3f(0.125000F, 1.000000F, -0.125000F);
        Vector3f v11 = new Vector3f(0.031250F, 0.625000F, 0.031250F);
        Vector3f v52 = new Vector3f(-0.125000F, 1.375000F, 0.031250F);
        Vector3f v58 = new Vector3f(0.137500F, 1.062500F, 0.043750F);
        Vector3f v7 = new Vector3f(-0.062500F, 0.000000F, 0.062500F);
        Vector3f v29 = new Vector3f(-0.031250F, 1.062500F, -0.125000F);
        Vector3f v44 = new Vector3f(0.031250F, 1.375000F, 0.125000F);
        Vector3f v13 = new Vector3f(-0.031250F, 0.625000F, -0.031250F);
        Vector3f v54 = new Vector3f(-0.062500F, 1.375000F, 0.031250F);
        Vector3f v82 = new Vector3f(-0.137500F, 1.062500F, -0.043750F);
        Vector3f v28 = new Vector3f(-0.031250F, 1.375000F, -0.125000F);
        Vector3f v21 = new Vector3f(-0.125000F, 1.000000F, -0.125000F);
        Vector3f v27 = new Vector3f(0.031250F, 1.062500F, -0.062500F);
        Vector3f v153 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v31 = new Vector3f(-0.031250F, 1.062500F, -0.062500F);
        Vector3f v25 = new Vector3f(0.031250F, 1.375000F, -0.125000F);
        Vector3f v41 = new Vector3f(-0.031250F, 1.375000F, 0.125000F);
        Vector3f v33 = new Vector3f(0.125000F, 1.375000F, 0.031250F);
        Vector3f v151 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v150 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v3 = new Vector3f(0.062500F, 0.000000F, 0.062500F);
        Vector3f v67 = new Vector3f(0.043750F, 1.062500F, -0.050000F);
        Vector3f v148 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v86 = new Vector3f(-0.050000F, 1.387500F, 0.043750F);
        Vector3f v85 = new Vector3f(-0.137500F, 1.062500F, 0.043750F);
        Vector3f v57 = new Vector3f(0.137500F, 1.387500F, 0.043750F);
        Vector3f v152 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v14 = new Vector3f(-0.031250F, 1.000000F, 0.031250F);
        Vector3f v81 = new Vector3f(-0.137500F, 1.387500F, -0.043750F);
        Vector3f v51 = new Vector3f(-0.062500F, 1.062500F, -0.031250F);
        Vector3f v79 = new Vector3f(0.043750F, 1.062500F, 0.050000F);
        Vector3f v78 = new Vector3f(0.043750F, 1.387500F, 0.050000F);
        Vector3f v77 = new Vector3f(0.043750F, 1.062500F, 0.137500F);
        Vector3f v26 = new Vector3f(0.031250F, 1.062500F, -0.125000F);
        Vector3f v76 = new Vector3f(0.043750F, 1.387500F, 0.137500F);
        Vector3f v35 = new Vector3f(0.062500F, 1.062500F, 0.031250F);
        Vector3f v2 = new Vector3f(0.062500F, 0.000000F, -0.062500F);
        Vector3f v1 = new Vector3f(0.062500F, 0.625000F, -0.062500F);
        Vector3f v16 = new Vector3f(0.125000F, 1.062500F, 0.125000F);
        Vector3f v75 = new Vector3f(-0.043750F, 1.062500F, 0.050000F);
        Vector3f v46 = new Vector3f(0.031250F, 1.375000F, 0.062500F);
        Vector3f v61 = new Vector3f(0.137500F, 1.062500F, -0.043750F);
        Vector3f v73 = new Vector3f(-0.043750F, 1.387500F, 0.137500F);
        Vector3f v66 = new Vector3f(0.043750F, 1.062500F, -0.137500F);
        Vector3f v10 = new Vector3f(0.031250F, 0.625000F, -0.031250F);
        Vector3f v24 = new Vector3f(0.031250F, 1.375000F, -0.062500F);
        Vector3f v64 = new Vector3f(0.043750F, 1.387500F, -0.050000F);
        Vector3f v4 = new Vector3f(-0.062500F, 0.625000F, -0.062500F);
        Vector3f v12 = new Vector3f(-0.031250F, 1.000000F, -0.031250F);
        Vector3f v63 = new Vector3f(0.050000F, 1.062500F, -0.043750F);
        Vector3f v71 = new Vector3f(-0.043750F, 1.062500F, -0.050000F);
        Vector3f v65 = new Vector3f(0.043750F, 1.387500F, -0.137500F);
        Vector3f v70 = new Vector3f(-0.043750F, 1.387500F, -0.050000F);
        Vector3f v62 = new Vector3f(0.050000F, 1.387500F, -0.043750F);
        Vector3f v48 = new Vector3f(-0.062500F, 1.375000F, -0.031250F);
        Vector3f v60 = new Vector3f(0.137500F, 1.387500F, -0.043750F);
        Vector3f v22 = new Vector3f(-0.125000F, 1.062500F, 0.125000F);
        Vector3f v84 = new Vector3f(-0.137500F, 1.387500F, 0.043750F);
        Vector3f v36 = new Vector3f(0.125000F, 1.375000F, -0.031250F);
        Vector3f v55 = new Vector3f(-0.062500F, 1.062500F, 0.031250F);
        Vector3f v40 = new Vector3f(-0.031250F, 1.375000F, 0.062500F);
        Vector3f v56 = new Vector3f(0.050000F, 1.387500F, 0.043750F);
        Vector3f v34 = new Vector3f(0.125000F, 1.062500F, 0.031250F);
        Vector3f v6 = new Vector3f(-0.062500F, 0.625000F, 0.062500F);
        Vector3f v43 = new Vector3f(-0.031250F, 1.062500F, 0.062500F);
        Vector3f v42 = new Vector3f(-0.031250F, 1.062500F, 0.125000F);
        Vector3f v17 = new Vector3f(0.125000F, 1.062500F, -0.125000F);
        Vector3f v83 = new Vector3f(-0.050000F, 1.062500F, -0.043750F);
        Vector3f v9 = new Vector3f(0.031250F, 1.000000F, -0.031250F);
        Vector3f v50 = new Vector3f(-0.125000F, 1.062500F, -0.031250F);
        Vector3f v20 = new Vector3f(-0.125000F, 1.062500F, -0.125000F);
        Vector3f v19 = new Vector3f(0.125000F, 1.000000F, 0.125000F);
        Vector3f v45 = new Vector3f(0.031250F, 1.062500F, 0.125000F);
        Vector3f v53 = new Vector3f(-0.125000F, 1.062500F, 0.031250F);
        Vector3f v30 = new Vector3f(-0.031250F, 1.375000F, -0.062500F);
        Vector3f v47 = new Vector3f(0.031250F, 1.062500F, 0.062500F);
        Vector2f v135 = new Vector2f(0.12500F, 0.40625F);
        Vector2f v126 = new Vector2f(0.00000F, 0.40625F);
        Vector2f v115 = new Vector2f(0.50000F, 0.15625F);
        Vector2f v116 = new Vector2f(0.37500F, 0.15625F);
        Vector2f v101 = new Vector2f(0.28125F, 0.06250F);
        Vector2f v109 = new Vector2f(0.31250F, 0.03125F);
        Vector2f v95 = new Vector2f(0.18750F, 0.37500F);
        Vector2f v147 = new Vector2f(0.15625F, 0.37500F);
        Vector2f v108 = new Vector2f(0.34375F, 0.03125F);
        Vector2f v94 = new Vector2f(0.18750F, 0.06250F);
        Vector2f v96 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v146 = new Vector2f(0.25000F, 0.56250F);
        Vector2f v123 = new Vector2f(0.87500F, 0.12500F);
        Vector2f v89 = new Vector2f(0.06250F, 0.06250F);
        Vector2f v112 = new Vector2f(0.28125F, 0.03125F);
        Vector2f v145 = new Vector2f(0.25000F, 0.40625F);
        Vector2f v144 = new Vector2f(0.21875F, 0.37500F);
        Vector2f v143 = new Vector2f(0.21875F, 0.56250F);
        Vector2f v142 = new Vector2f(0.21875F, 0.40625F);
        Vector2f v141 = new Vector2f(0.18750F, 0.56250F);
        Vector2f v140 = new Vector2f(0.18750F, 0.40625F);
        Vector2f v119 = new Vector2f(0.75000F, 0.12500F);
        Vector2f v136 = new Vector2f(0.12500F, 0.56250F);
        Vector2f v98 = new Vector2f(0.25000F, 0.06250F);
        Vector2f v133 = new Vector2f(0.09375F, 0.56250F);
        Vector2f v106 = new Vector2f(0.34375F, 0.06250F);
        Vector2f v138 = new Vector2f(0.15625F, 0.40625F);
        Vector2f v102 = new Vector2f(0.28125F, 0.25000F);
        Vector2f v128 = new Vector2f(0.03125F, 0.56250F);
        Vector2f v103 = new Vector2f(0.25000F, 0.25000F);
        Vector2f v137 = new Vector2f(0.03125F, 0.37500F);
        Vector2f v114 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v139 = new Vector2f(0.15625F, 0.56250F);
        Vector2f v134 = new Vector2f(0.09375F, 0.37500F);
        Vector2f v92 = new Vector2f(0.12500F, 0.06250F);
        Vector2f v90 = new Vector2f(0.06250F, 0.37500F);
        Vector2f v99 = new Vector2f(0.25000F, 0.37500F);
        Vector2f v132 = new Vector2f(0.09375F, 0.40625F);
        Vector2f v124 = new Vector2f(0.87500F, 0.15625F);
        Vector2f v104 = new Vector2f(0.31250F, 0.06250F);
        Vector2f v131 = new Vector2f(0.06250F, 0.56250F);
        Vector2f v130 = new Vector2f(0.06250F, 0.40625F);
        Vector2f v107 = new Vector2f(0.34375F, 0.25000F);
        Vector2f v93 = new Vector2f(0.12500F, 0.37500F);
        Vector2f v127 = new Vector2f(0.03125F, 0.40625F);
        Vector2f v105 = new Vector2f(0.31250F, 0.25000F);
        Vector2f v125 = new Vector2f(0.50000F, 0.00000F);
        Vector2f v118 = new Vector2f(0.62500F, 0.15625F);
        Vector2f v121 = new Vector2f(0.75000F, 0.00000F);
        Vector2f v120 = new Vector2f(0.75000F, 0.15625F);
        Vector2f v122 = new Vector2f(0.62500F, 0.00000F);
        Vector2f v100 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v117 = new Vector2f(0.62500F, 0.12500F);
        Vector2f v88 = new Vector2f(0.00000F, 0.06250F);
        Vector2f v129 = new Vector2f(0.00000F, 0.56250F);
        Vector2f v113 = new Vector2f(0.37500F, 0.12500F);
        Vector2f v97 = new Vector2f(0.12500F, 0.00000F);
        Vector2f v111 = new Vector2f(0.37500F, 0.25000F);
        Vector2f v110 = new Vector2f(0.37500F, 0.06250F);
        Vector2f v91 = new Vector2f(0.00000F, 0.37500F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v91, v148, v2, c0, v90, v148, v1, c0, v89, v148, v0, c0, v88, v148));
        builder.add(new SimpleQuad(v2, c0, v90, v149, v5, c0, v93, v149, v4, c0, v92, v149, v1, c0, v89, v149));
        builder.add(new SimpleQuad(v5, c0, v93, v150, v7, c0, v95, v150, v6, c0, v94, v150, v4, c0, v92, v150));
        builder.add(new SimpleQuad(v5, c0, v94, v151, v2, c0, v92, v151, v3, c0, v97, v151, v7, c0, v96, v151));
        builder.add(new SimpleQuad(v7, c0, v95, v152, v3, c0, v99, v152, v0, c0, v98, v152, v6, c0, v94, v152));
        builder.add(new SimpleQuad(v0, c0, v100, v153, v1, c0, v89, v153, v4, c0, v92, v153, v6, c0, v97, v153));
        HILT_QUADS = builder.build();

        // BladeOff
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v11, c1, v103, v148, v10, c1, v102, v148, v9, c1, v101, v148, v8, c1, v98, v148));
        builder.add(new SimpleQuad(v10, c1, v102, v149, v13, c1, v105, v149, v12, c1, v104, v149, v9, c1, v101, v149));
        builder.add(new SimpleQuad(v13, c1, v105, v150, v15, c1, v107, v150, v14, c1, v106, v150, v12, c1, v104, v150));
        builder.add(new SimpleQuad(v13, c1, v106, v151, v10, c1, v104, v151, v11, c1, v109, v151, v15, c1, v108, v151));
        builder.add(new SimpleQuad(v15, c1, v107, v152, v11, c1, v111, v152, v8, c1, v110, v152, v14, c1, v106, v152));
        builder.add(new SimpleQuad(v8, c1, v112, v153, v9, c1, v101, v153, v12, c1, v104, v153, v14, c1, v109, v153));
        builder.add(new SimpleQuad(v19, c1, v116, v148, v18, c1, v115, v148, v17, c1, v114, v148, v16, c1, v113, v148));
        builder.add(new SimpleQuad(v18, c1, v115, v149, v21, c1, v118, v149, v20, c1, v117, v149, v17, c1, v114, v149));
        builder.add(new SimpleQuad(v21, c1, v118, v150, v23, c1, v120, v150, v22, c1, v119, v150, v20, c1, v117, v150));
        builder.add(new SimpleQuad(v21, c1, v119, v151, v18, c1, v117, v151, v19, c1, v122, v151, v23, c1, v121, v151));
        builder.add(new SimpleQuad(v23, c1, v120, v152, v19, c1, v124, v152, v16, c1, v123, v152, v22, c1, v119, v152));
        builder.add(new SimpleQuad(v16, c1, v125, v153, v17, c1, v114, v153, v20, c1, v117, v153, v22, c1, v122, v153));
        BLADE_OFF_QUADS = builder.build();

        // BladeIn
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c2, v129, v148, v26, c2, v128, v148, v25, c2, v127, v148, v24, c2, v126, v148));
        builder.add(new SimpleQuad(v26, c2, v128, v149, v29, c2, v131, v149, v28, c2, v130, v149, v25, c2, v127, v149));
        builder.add(new SimpleQuad(v29, c2, v131, v150, v31, c2, v133, v150, v30, c2, v132, v150, v28, c2, v130, v150));
        builder.add(new SimpleQuad(v29, c2, v132, v151, v26, c2, v130, v151, v27, c2, v90, v151, v31, c2, v134, v151));
        builder.add(new SimpleQuad(v31, c2, v133, v152, v27, c2, v136, v152, v24, c2, v135, v152, v30, c2, v132, v152));
        builder.add(new SimpleQuad(v24, c2, v137, v153, v25, c2, v127, v153, v28, c2, v130, v153, v30, c2, v90, v153));
        builder.add(new SimpleQuad(v35, c2, v129, v152, v34, c2, v128, v152, v33, c2, v127, v152, v32, c2, v126, v152));
        builder.add(new SimpleQuad(v34, c2, v128, v148, v37, c2, v131, v148, v36, c2, v130, v148, v33, c2, v127, v148));
        builder.add(new SimpleQuad(v37, c2, v131, v149, v39, c2, v133, v149, v38, c2, v132, v149, v36, c2, v130, v149));
        builder.add(new SimpleQuad(v37, c2, v132, v151, v34, c2, v130, v151, v35, c2, v90, v151, v39, c2, v134, v151));
        builder.add(new SimpleQuad(v39, c2, v133, v150, v35, c2, v136, v150, v32, c2, v135, v150, v38, c2, v132, v150));
        builder.add(new SimpleQuad(v32, c2, v137, v153, v33, c2, v127, v153, v36, c2, v130, v153, v38, c2, v90, v153));
        builder.add(new SimpleQuad(v43, c2, v129, v150, v42, c2, v128, v150, v41, c2, v127, v150, v40, c2, v126, v150));
        builder.add(new SimpleQuad(v42, c2, v128, v152, v45, c2, v131, v152, v44, c2, v130, v152, v41, c2, v127, v152));
        builder.add(new SimpleQuad(v45, c2, v131, v148, v47, c2, v133, v148, v46, c2, v132, v148, v44, c2, v130, v148));
        builder.add(new SimpleQuad(v45, c2, v132, v151, v42, c2, v130, v151, v43, c2, v90, v151, v47, c2, v134, v151));
        builder.add(new SimpleQuad(v47, c2, v133, v149, v43, c2, v136, v149, v40, c2, v135, v149, v46, c2, v132, v149));
        builder.add(new SimpleQuad(v40, c2, v137, v153, v41, c2, v127, v153, v44, c2, v130, v153, v46, c2, v90, v153));
        builder.add(new SimpleQuad(v51, c2, v129, v149, v50, c2, v128, v149, v49, c2, v127, v149, v48, c2, v126, v149));
        builder.add(new SimpleQuad(v50, c2, v128, v150, v53, c2, v131, v150, v52, c2, v130, v150, v49, c2, v127, v150));
        builder.add(new SimpleQuad(v53, c2, v131, v152, v55, c2, v133, v152, v54, c2, v132, v152, v52, c2, v130, v152));
        builder.add(new SimpleQuad(v53, c2, v132, v151, v50, c2, v130, v151, v51, c2, v90, v151, v55, c2, v134, v151));
        builder.add(new SimpleQuad(v55, c2, v133, v148, v51, c2, v136, v148, v48, c2, v135, v148, v54, c2, v132, v148));
        builder.add(new SimpleQuad(v48, c2, v137, v153, v49, c2, v127, v153, v52, c2, v130, v153, v54, c2, v90, v153));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v59, c3, v136, v152, v58, c3, v139, v152, v57, c3, v138, v152, v56, c3, v135, v152));
        builder.add(new SimpleQuad(v58, c3, v139, v148, v61, c3, v141, v148, v60, c3, v140, v148, v57, c3, v138, v148));
        builder.add(new SimpleQuad(v61, c3, v141, v149, v63, c3, v143, v149, v62, c3, v142, v149, v60, c3, v140, v149));
        builder.add(new SimpleQuad(v61, c3, v142, v151, v58, c3, v140, v151, v59, c3, v95, v151, v63, c3, v144, v151));
        builder.add(new SimpleQuad(v63, c3, v143, v150, v59, c3, v146, v150, v56, c3, v145, v150, v62, c3, v142, v150));
        builder.add(new SimpleQuad(v56, c3, v147, v153, v57, c3, v138, v153, v60, c3, v140, v153, v62, c3, v95, v153));
        builder.add(new SimpleQuad(v67, c3, v136, v148, v66, c3, v139, v148, v65, c3, v138, v148, v64, c3, v135, v148));
        builder.add(new SimpleQuad(v66, c3, v139, v149, v69, c3, v141, v149, v68, c3, v140, v149, v65, c3, v138, v149));
        builder.add(new SimpleQuad(v69, c3, v141, v150, v71, c3, v143, v150, v70, c3, v142, v150, v68, c3, v140, v150));
        builder.add(new SimpleQuad(v69, c3, v142, v151, v66, c3, v140, v151, v67, c3, v95, v151, v71, c3, v144, v151));
        builder.add(new SimpleQuad(v71, c3, v143, v152, v67, c3, v146, v152, v64, c3, v145, v152, v70, c3, v142, v152));
        builder.add(new SimpleQuad(v64, c3, v147, v153, v65, c3, v138, v153, v68, c3, v140, v153, v70, c3, v95, v153));
        builder.add(new SimpleQuad(v75, c3, v136, v150, v74, c3, v139, v150, v73, c3, v138, v150, v72, c3, v135, v150));
        builder.add(new SimpleQuad(v74, c3, v139, v152, v77, c3, v141, v152, v76, c3, v140, v152, v73, c3, v138, v152));
        builder.add(new SimpleQuad(v77, c3, v141, v148, v79, c3, v143, v148, v78, c3, v142, v148, v76, c3, v140, v148));
        builder.add(new SimpleQuad(v77, c3, v142, v151, v74, c3, v140, v151, v75, c3, v95, v151, v79, c3, v144, v151));
        builder.add(new SimpleQuad(v79, c3, v143, v149, v75, c3, v146, v149, v72, c3, v145, v149, v78, c3, v142, v149));
        builder.add(new SimpleQuad(v72, c3, v147, v153, v73, c3, v138, v153, v76, c3, v140, v153, v78, c3, v95, v153));
        builder.add(new SimpleQuad(v83, c3, v136, v149, v82, c3, v139, v149, v81, c3, v138, v149, v80, c3, v135, v149));
        builder.add(new SimpleQuad(v82, c3, v139, v150, v85, c3, v141, v150, v84, c3, v140, v150, v81, c3, v138, v150));
        builder.add(new SimpleQuad(v85, c3, v141, v152, v87, c3, v143, v152, v86, c3, v142, v152, v84, c3, v140, v152));
        builder.add(new SimpleQuad(v85, c3, v142, v151, v82, c3, v140, v151, v83, c3, v95, v151, v87, c3, v144, v151));
        builder.add(new SimpleQuad(v87, c3, v143, v148, v83, c3, v146, v148, v80, c3, v145, v148, v86, c3, v142, v148));
        builder.add(new SimpleQuad(v80, c3, v147, v153, v81, c3, v138, v153, v84, c3, v140, v153, v86, c3, v95, v153));
        BLADE_OUT_QUADS = builder.build();
    }
}
