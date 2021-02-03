package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
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

public class LaserBladeModelType606 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_606.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(getHiltRenderType());
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = buffer.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = buffer.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v20 = new Vector3f(0.043750F, 0.312500F, 0.000000F);
        Vector3f v13 = new Vector3f(-0.021875F, 0.000000F, -0.040625F);
        Vector3f v23 = new Vector3f(-0.043750F, 0.000000F, 0.000000F);
        Vector3f v49 = new Vector3f(0.065625F, 1.437500F, 0.118750F);
        Vector3f v10 = new Vector3f(0.021875F, 0.000000F, -0.040625F);
        Vector3f v160 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v50 = new Vector3f(0.065625F, 1.437500F, -0.118750F);
        Vector3f v36 = new Vector3f(-0.043750F, 1.406250F, 0.078125F);
        Vector3f v9 = new Vector3f(0.021875F, 0.312500F, -0.040625F);
        Vector3f v0 = new Vector3f(-0.065625F, 0.375000F, 0.118750F);
        Vector3f v5 = new Vector3f(0.065625F, 0.312500F, -0.118750F);
        Vector3f v166 = new Vector3f(-0.87251F, 0.00000F, 0.48860F);
        Vector3f v15 = new Vector3f(-0.021875F, 0.000000F, 0.040625F);
        Vector3f v52 = new Vector3f(0.131250F, 1.437500F, 0.000000F);
        Vector3f v2 = new Vector3f(0.065625F, 0.312500F, 0.118750F);
        Vector3f v32 = new Vector3f(0.043750F, 1.375000F, 0.000000F);
        Vector3f v41 = new Vector3f(0.043750F, 0.375000F, -0.078125F);
        Vector3f v163 = new Vector3f(0.87251F, 0.00000F, 0.48860F);
        Vector3f v30 = new Vector3f(-0.021875F, 1.375000F, -0.040625F);
        Vector3f v17 = new Vector3f(0.131250F, 0.312500F, 0.000000F);
        Vector3f v37 = new Vector3f(0.043750F, 1.406250F, 0.078125F);
        Vector3f v8 = new Vector3f(0.021875F, 0.312500F, 0.040625F);
        Vector3f v21 = new Vector3f(0.043750F, 0.000000F, 0.000000F);
        Vector3f v153 = new Vector3f(-0.87524F, 0.00000F, -0.48369F);
        Vector3f v42 = new Vector3f(-0.043750F, 1.406250F, -0.078125F);
        Vector3f v48 = new Vector3f(-0.065625F, 1.437500F, 0.118750F);
        Vector3f v33 = new Vector3f(0.043750F, 0.375000F, 0.000000F);
        Vector3f v162 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v11 = new Vector3f(0.021875F, 0.000000F, 0.040625F);
        Vector3f v1 = new Vector3f(0.065625F, 0.375000F, 0.118750F);
        Vector3f v152 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v53 = new Vector3f(-0.131250F, 1.437500F, 0.000000F);
        Vector3f v157 = new Vector3f(0.88047F, 0.00000F, -0.47410F);
        Vector3f v27 = new Vector3f(-0.021875F, 0.375000F, 0.040625F);
        Vector3f v155 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v28 = new Vector3f(0.021875F, 1.375000F, -0.040625F);
        Vector3f v51 = new Vector3f(-0.065625F, 1.437500F, -0.118750F);
        Vector3f v39 = new Vector3f(-0.043750F, 0.375000F, 0.078125F);
        Vector3f v12 = new Vector3f(-0.021875F, 0.312500F, -0.040625F);
        Vector3f v26 = new Vector3f(0.021875F, 0.375000F, 0.040625F);
        Vector3f v46 = new Vector3f(-0.087500F, 1.406250F, 0.000000F);
        Vector3f v151 = new Vector3f(0.87524F, 0.00000F, -0.48369F);
        Vector3f v31 = new Vector3f(-0.021875F, 0.375000F, -0.040625F);
        Vector3f v6 = new Vector3f(-0.065625F, 0.375000F, -0.118750F);
        Vector3f v19 = new Vector3f(-0.131250F, 0.312500F, 0.000000F);
        Vector3f v35 = new Vector3f(-0.043750F, 0.375000F, 0.000000F);
        Vector3f v47 = new Vector3f(-0.087500F, 0.375000F, 0.000000F);
        Vector3f v38 = new Vector3f(0.043750F, 0.375000F, 0.078125F);
        Vector3f v40 = new Vector3f(0.043750F, 1.406250F, -0.078125F);
        Vector3f v7 = new Vector3f(-0.065625F, 0.312500F, -0.118750F);
        Vector3f v3 = new Vector3f(-0.065625F, 0.312500F, 0.118750F);
        Vector3f v165 = new Vector3f(-0.87251F, 0.00000F, -0.48860F);
        Vector3f v164 = new Vector3f(0.87251F, 0.00000F, -0.48860F);
        Vector3f v18 = new Vector3f(-0.131250F, 0.375000F, 0.000000F);
        Vector3f v161 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v24 = new Vector3f(-0.021875F, 1.375000F, 0.040625F);
        Vector3f v25 = new Vector3f(0.021875F, 1.375000F, 0.040625F);
        Vector3f v22 = new Vector3f(-0.043750F, 0.312500F, 0.000000F);
        Vector3f v45 = new Vector3f(0.087500F, 0.375000F, 0.000000F);
        Vector3f v14 = new Vector3f(-0.021875F, 0.312500F, 0.040625F);
        Vector3f v159 = new Vector3f(-0.88047F, 0.00000F, 0.47410F);
        Vector3f v156 = new Vector3f(0.88047F, 0.00000F, 0.47410F);
        Vector3f v4 = new Vector3f(0.065625F, 0.375000F, -0.118750F);
        Vector3f v34 = new Vector3f(-0.043750F, 1.375000F, 0.000000F);
        Vector3f v154 = new Vector3f(-0.87524F, 0.00000F, 0.48369F);
        Vector3f v150 = new Vector3f(0.87524F, 0.00000F, 0.48369F);
        Vector3f v149 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v43 = new Vector3f(-0.043750F, 0.375000F, -0.078125F);
        Vector3f v29 = new Vector3f(0.021875F, 0.375000F, -0.040625F);
        Vector3f v16 = new Vector3f(0.131250F, 0.375000F, 0.000000F);
        Vector3f v158 = new Vector3f(-0.88047F, 0.00000F, -0.47410F);
        Vector3f v44 = new Vector3f(0.087500F, 1.406250F, 0.000000F);
        Vector2f v58 = new Vector2f(0.218750F, 0.093750F);
        Vector2f v101 = new Vector2f(0.031250F, 0.718750F);
        Vector2f v86 = new Vector2f(0.062500F, 0.015625F);
        Vector2f v142 = new Vector2f(0.609375F, 0.781250F);
        Vector2f v90 = new Vector2f(0.218750F, 0.046875F);
        Vector2f v56 = new Vector2f(0.500000F, 0.125000F);
        Vector2f v136 = new Vector2f(0.562500F, 0.781250F);
        Vector2f v98 = new Vector2f(0.031250F, 0.218750F);
        Vector2f v130 = new Vector2f(0.656250F, 0.218750F);
        Vector2f v115 = new Vector2f(0.187500F, 0.218750F);
        Vector2f v141 = new Vector2f(0.609375F, 0.218750F);
        Vector2f v100 = new Vector2f(0.062500F, 0.718750F);
        Vector2f v120 = new Vector2f(0.156250F, 0.750000F);
        Vector2f v84 = new Vector2f(0.078125F, 0.031250F);
        Vector2f v112 = new Vector2f(0.375000F, 0.218750F);
        Vector2f v61 = new Vector2f(0.218750F, 0.125000F);
        Vector2f v89 = new Vector2f(0.062500F, 0.000000F);
        Vector2f v106 = new Vector2f(0.078125F, 0.218750F);
        Vector2f v97 = new Vector2f(0.093750F, 0.718750F);
        Vector2f v67 = new Vector2f(0.125000F, 0.031250F);
        Vector2f v145 = new Vector2f(0.562500F, 0.125000F);
        Vector2f v118 = new Vector2f(0.187500F, 0.750000F);
        Vector2f v95 = new Vector2f(0.125000F, 0.218750F);
        Vector2f v93 = new Vector2f(0.031250F, 0.000000F);
        Vector2f v96 = new Vector2f(0.125000F, 0.718750F);
        Vector2f v66 = new Vector2f(0.093750F, 0.031250F);
        Vector2f v114 = new Vector2f(0.312500F, 0.750000F);
        Vector2f v104 = new Vector2f(0.000000F, 0.718750F);
        Vector2f v80 = new Vector2f(0.015625F, 0.031250F);
        Vector2f v54 = new Vector2f(0.406250F, 0.093750F);
        Vector2f v60 = new Vector2f(0.312500F, 0.125000F);
        Vector2f v85 = new Vector2f(0.078125F, 0.187500F);
        Vector2f v59 = new Vector2f(0.312500F, 0.093750F);
        Vector2f v81 = new Vector2f(0.015625F, 0.187500F);
        Vector2f v70 = new Vector2f(0.171875F, 0.093750F);
        Vector2f v134 = new Vector2f(0.468750F, 0.218750F);
        Vector2f v83 = new Vector2f(0.000000F, 0.031250F);
        Vector2f v74 = new Vector2f(0.359375F, 0.093750F);
        Vector2f v92 = new Vector2f(0.031250F, 0.015625F);
        Vector2f v55 = new Vector2f(0.500000F, 0.093750F);
        Vector2f v75 = new Vector2f(0.359375F, 0.125000F);
        Vector2f v94 = new Vector2f(0.093750F, 0.218750F);
        Vector2f v147 = new Vector2f(0.656250F, 0.171875F);
        Vector2f v133 = new Vector2f(0.656250F, 0.781250F);
        Vector2f v123 = new Vector2f(0.281250F, 0.750000F);
        Vector2f v144 = new Vector2f(0.468750F, 0.125000F);
        Vector2f v143 = new Vector2f(0.468750F, 0.171875F);
        Vector2f v102 = new Vector2f(0.015625F, 0.218750F);
        Vector2f v131 = new Vector2f(0.750000F, 0.218750F);
        Vector2f v135 = new Vector2f(0.562500F, 0.218750F);
        Vector2f v138 = new Vector2f(0.421875F, 0.218750F);
        Vector2f v124 = new Vector2f(0.187500F, 0.187500F);
        Vector2f v137 = new Vector2f(0.468750F, 0.781250F);
        Vector2f v139 = new Vector2f(0.421875F, 0.781250F);
        Vector2f v82 = new Vector2f(0.000000F, 0.187500F);
        Vector2f v146 = new Vector2f(0.562500F, 0.171875F);
        Vector2f v78 = new Vector2f(0.406250F, 0.000000F);
        Vector2f v129 = new Vector2f(0.312500F, 0.156250F);
        Vector2f v140 = new Vector2f(0.375000F, 0.781250F);
        Vector2f v62 = new Vector2f(0.031250F, 0.031250F);
        Vector2f v122 = new Vector2f(0.281250F, 0.218750F);
        Vector2f v110 = new Vector2f(0.093750F, 0.203125F);
        Vector2f v132 = new Vector2f(0.750000F, 0.781250F);
        Vector2f v128 = new Vector2f(0.312500F, 0.187500F);
        Vector2f v127 = new Vector2f(0.250000F, 0.187500F);
        Vector2f v72 = new Vector2f(0.125000F, 0.125000F);
        Vector2f v77 = new Vector2f(0.406250F, 0.046875F);
        Vector2f v65 = new Vector2f(0.031250F, 0.187500F);
        Vector2f v125 = new Vector2f(0.187500F, 0.156250F);
        Vector2f v57 = new Vector2f(0.406250F, 0.125000F);
        Vector2f v63 = new Vector2f(0.062500F, 0.031250F);
        Vector2f v87 = new Vector2f(0.093750F, 0.015625F);
        Vector2f v121 = new Vector2f(0.125000F, 0.750000F);
        Vector2f v119 = new Vector2f(0.156250F, 0.218750F);
        Vector2f v68 = new Vector2f(0.125000F, 0.187500F);
        Vector2f v88 = new Vector2f(0.093750F, 0.000000F);
        Vector2f v76 = new Vector2f(0.312500F, 0.046875F);
        Vector2f v105 = new Vector2f(0.000000F, 0.218750F);
        Vector2f v91 = new Vector2f(0.218750F, 0.000000F);
        Vector2f v111 = new Vector2f(0.312500F, 0.218750F);
        Vector2f v73 = new Vector2f(0.125000F, 0.093750F);
        Vector2f v117 = new Vector2f(0.250000F, 0.750000F);
        Vector2f v126 = new Vector2f(0.250000F, 0.156250F);
        Vector2f v116 = new Vector2f(0.250000F, 0.218750F);
        Vector2f v79 = new Vector2f(0.312500F, 0.000000F);
        Vector2f v113 = new Vector2f(0.375000F, 0.750000F);
        Vector2f v103 = new Vector2f(0.015625F, 0.718750F);
        Vector2f v108 = new Vector2f(0.031250F, 0.203125F);
        Vector2f v71 = new Vector2f(0.171875F, 0.125000F);
        Vector2f v107 = new Vector2f(0.078125F, 0.718750F);
        Vector2f v109 = new Vector2f(0.062500F, 0.203125F);
        Vector2f v148 = new Vector2f(0.656250F, 0.125000F);
        Vector2f v99 = new Vector2f(0.062500F, 0.218750F);
        Vector2f v69 = new Vector2f(0.093750F, 0.187500F);
        Vector2f v64 = new Vector2f(0.062500F, 0.187500F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v57, v149, v2, c0, v56, v149, v1, c0, v55, v149, v0, c0, v54, v149));
        builder.add(new SimpleQuad(v5, c0, v61, v152, v7, c0, v60, v152, v6, c0, v59, v152, v4, c0, v58, v152));
        builder.add(new SimpleQuad(v10, c0, v65, v152, v13, c0, v64, v152, v12, c0, v63, v152, v9, c0, v62, v152));
        builder.add(new SimpleQuad(v15, c0, v69, v149, v11, c0, v68, v149, v8, c0, v67, v149, v14, c0, v66, v149));
        builder.add(new SimpleQuad(v17, c0, v71, v161, v5, c0, v61, v151, v4, c0, v58, v151, v16, c0, v70, v161));
        builder.add(new SimpleQuad(v1, c0, v73, v150, v2, c0, v72, v150, v17, c0, v71, v161, v16, c0, v70, v161));
        builder.add(new SimpleQuad(v19, c0, v75, v162, v3, c0, v57, v154, v0, c0, v54, v154, v18, c0, v74, v162));
        builder.add(new SimpleQuad(v6, c0, v59, v153, v7, c0, v60, v153, v19, c0, v75, v162, v18, c0, v74, v162));
        builder.add(new SimpleQuad(v19, c0, v77, v155, v7, c0, v54, v155, v5, c0, v59, v155, v17, c0, v76, v155));
        builder.add(new SimpleQuad(v2, c0, v79, v155, v3, c0, v78, v155, v19, c0, v77, v155, v17, c0, v76, v155));
        builder.add(new SimpleQuad(v21, c0, v81, v161, v10, c0, v65, v157, v9, c0, v62, v157, v20, c0, v80, v161));
        builder.add(new SimpleQuad(v8, c0, v83, v156, v11, c0, v82, v156, v21, c0, v81, v161, v20, c0, v80, v161));
        builder.add(new SimpleQuad(v23, c0, v85, v162, v15, c0, v69, v159, v14, c0, v66, v159, v22, c0, v84, v162));
        builder.add(new SimpleQuad(v12, c0, v63, v158, v13, c0, v64, v158, v23, c0, v85, v162, v22, c0, v84, v162));
        builder.add(new SimpleQuad(v23, c0, v87, v155, v13, c0, v66, v155, v10, c0, v63, v155, v21, c0, v86, v155));
        builder.add(new SimpleQuad(v11, c0, v89, v155, v15, c0, v88, v155, v23, c0, v87, v155, v21, c0, v86, v155));
        builder.add(new SimpleQuad(v18, c0, v76, v160, v0, c0, v79, v160, v1, c0, v91, v160, v16, c0, v90, v160));
        builder.add(new SimpleQuad(v4, c0, v58, v160, v6, c0, v59, v160, v18, c0, v76, v160, v16, c0, v90, v160));
        builder.add(new SimpleQuad(v20, c0, v92, v160, v9, c0, v62, v160, v12, c0, v63, v160, v22, c0, v86, v160));
        builder.add(new SimpleQuad(v14, c0, v89, v160, v8, c0, v93, v160, v20, c0, v92, v160, v22, c0, v86, v160));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c1, v97, v149, v26, c1, v96, v149, v25, c1, v95, v149, v24, c1, v94, v149));
        builder.add(new SimpleQuad(v29, c1, v101, v152, v31, c1, v100, v152, v30, c1, v99, v152, v28, c1, v98, v152));
        builder.add(new SimpleQuad(v33, c1, v103, v161, v29, c1, v101, v157, v28, c1, v98, v157, v32, c1, v102, v161));
        builder.add(new SimpleQuad(v25, c1, v105, v156, v26, c1, v104, v156, v33, c1, v103, v161, v32, c1, v102, v161));
        builder.add(new SimpleQuad(v35, c1, v107, v162, v27, c1, v97, v159, v24, c1, v94, v159, v34, c1, v106, v162));
        builder.add(new SimpleQuad(v30, c1, v99, v158, v31, c1, v100, v158, v35, c1, v107, v162, v34, c1, v106, v162));
        builder.add(new SimpleQuad(v34, c1, v109, v160, v24, c1, v64, v160, v25, c1, v65, v160, v32, c1, v108, v160));
        builder.add(new SimpleQuad(v28, c1, v98, v160, v30, c1, v99, v160, v34, c1, v109, v160, v32, c1, v108, v160));
        builder.add(new SimpleQuad(v35, c1, v110, v155, v31, c1, v94, v155, v29, c1, v99, v155, v33, c1, v109, v155));
        builder.add(new SimpleQuad(v26, c1, v64, v155, v27, c1, v69, v155, v35, c1, v110, v155, v33, c1, v109, v155));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v39, c2, v114, v149, v38, c2, v113, v149, v37, c2, v112, v149, v36, c2, v111, v149));
        builder.add(new SimpleQuad(v41, c2, v118, v152, v43, c2, v117, v152, v42, c2, v116, v152, v40, c2, v115, v152));
        builder.add(new SimpleQuad(v45, c2, v120, v161, v41, c2, v118, v164, v40, c2, v115, v164, v44, c2, v119, v161));
        builder.add(new SimpleQuad(v37, c2, v95, v163, v38, c2, v121, v163, v45, c2, v120, v161, v44, c2, v119, v161));
        builder.add(new SimpleQuad(v47, c2, v123, v162, v39, c2, v114, v166, v36, c2, v111, v166, v46, c2, v122, v162));
        builder.add(new SimpleQuad(v42, c2, v116, v165, v43, c2, v117, v165, v47, c2, v123, v162, v46, c2, v122, v162));
        builder.add(new SimpleQuad(v46, c2, v127, v160, v36, c2, v126, v160, v37, c2, v125, v160, v44, c2, v124, v160));
        builder.add(new SimpleQuad(v40, c2, v115, v160, v42, c2, v116, v160, v46, c2, v127, v160, v44, c2, v124, v160));
        builder.add(new SimpleQuad(v45, c2, v127, v155, v38, c2, v126, v155, v39, c2, v129, v155, v47, c2, v128, v155));
        builder.add(new SimpleQuad(v43, c2, v111, v155, v41, c2, v116, v155, v45, c2, v127, v155, v47, c2, v128, v155));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v0, c3, v133, v149, v1, c3, v132, v149, v49, c3, v131, v149, v48, c3, v130, v149));
        builder.add(new SimpleQuad(v4, c3, v137, v152, v6, c3, v136, v152, v51, c3, v135, v152, v50, c3, v134, v152));
        builder.add(new SimpleQuad(v16, c3, v139, v161, v4, c3, v137, v151, v50, c3, v134, v151, v52, c3, v138, v161));
        builder.add(new SimpleQuad(v49, c3, v112, v150, v1, c3, v140, v150, v16, c3, v139, v161, v52, c3, v138, v161));
        builder.add(new SimpleQuad(v18, c3, v142, v162, v0, c3, v133, v154, v48, c3, v130, v154, v53, c3, v141, v162));
        builder.add(new SimpleQuad(v51, c3, v135, v153, v6, c3, v136, v153, v18, c3, v142, v162, v53, c3, v141, v162));
        builder.add(new SimpleQuad(v53, c3, v146, v160, v48, c3, v145, v160, v49, c3, v144, v160, v52, c3, v143, v160));
        builder.add(new SimpleQuad(v50, c3, v134, v160, v51, c3, v135, v160, v53, c3, v146, v160, v52, c3, v143, v160));
        builder.add(new SimpleQuad(v18, c3, v147, v155, v6, c3, v130, v155, v4, c3, v135, v155, v16, c3, v146, v155));
        builder.add(new SimpleQuad(v1, c3, v145, v155, v0, c3, v148, v155, v18, c3, v147, v155, v16, c3, v146, v155));
        BLADE_OUT_QUADS = builder.build();
    }
}
