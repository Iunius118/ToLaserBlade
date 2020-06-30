package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType903 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_903.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_NO_TINT_QUADS;
    public static final List<SimpleQuad> BLADE_OFF_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int noTintColor = 0xFFFFFFFF;
        final int offColor = 0xFFCCCCCC;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, HILT_NO_TINT_QUADS, noTintColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, BLADE_OFF_QUADS, offColor, lightmapCoord, overlayColor);
            return;
        }

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.simpleOuterColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.simpleInnerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v29 = new Vector3f(0.031250F, 1.437500F, 0.062500F);
        Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.062500F);
        Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.062500F);
        Vector3f v139 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v11 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v16 = new Vector3f(0.031250F, -0.062500F, 0.062500F);
        Vector3f v41 = new Vector3f(0.062500F, 1.468750F, 0.093750F);
        Vector3f v21 = new Vector3f(-0.031250F, 0.375000F, 0.062500F);
        Vector3f v143 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v9 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v44 = new Vector3f(0.062500F, 1.468750F, -0.093750F);
        Vector3f v12 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v31 = new Vector3f(-0.031250F, 1.437500F, -0.062500F);
        Vector3f v45 = new Vector3f(0.062500F, 0.406250F, -0.093750F);
        Vector3f v19 = new Vector3f(-0.031250F, -0.062500F, -0.062500F);
        Vector3f v42 = new Vector3f(0.062500F, 0.406250F, 0.093750F);
        Vector3f v1 = new Vector3f(0.031250F, 0.312500F, -0.062500F);
        Vector3f v23 = new Vector3f(-0.031250F, 0.375000F, -0.062500F);
        Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.062500F);
        Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.312500F, 0.062500F);
        Vector3f v141 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v142 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.062500F);
        Vector3f v144 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v140 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v39 = new Vector3f(-0.025000F, 0.443750F, -0.050000F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.312500F, -0.062500F);
        Vector3f v27 = new Vector3f(-0.031250F, 0.437500F, -0.062500F);
        Vector3f v37 = new Vector3f(0.025000F, 0.443750F, -0.050000F);
        Vector3f v47 = new Vector3f(-0.062500F, 0.406250F, -0.093750F);
        Vector3f v46 = new Vector3f(-0.062500F, 1.468750F, -0.093750F);
        Vector3f v30 = new Vector3f(0.031250F, 1.437500F, -0.062500F);
        Vector3f v43 = new Vector3f(-0.062500F, 0.406250F, 0.093750F);
        Vector3f v40 = new Vector3f(-0.062500F, 1.468750F, 0.093750F);
        Vector3f v13 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v18 = new Vector3f(0.031250F, -0.062500F, -0.062500F);
        Vector3f v24 = new Vector3f(-0.031250F, 0.437500F, 0.062500F);
        Vector3f v36 = new Vector3f(0.025000F, 1.431250F, -0.050000F);
        Vector3f v35 = new Vector3f(-0.025000F, 0.443750F, 0.050000F);
        Vector3f v32 = new Vector3f(-0.025000F, 1.431250F, 0.050000F);
        Vector3f v33 = new Vector3f(0.025000F, 1.431250F, 0.050000F);
        Vector3f v20 = new Vector3f(0.031250F, 0.375000F, 0.062500F);
        Vector3f v34 = new Vector3f(0.025000F, 0.443750F, 0.050000F);
        Vector3f v38 = new Vector3f(-0.025000F, 1.431250F, -0.050000F);
        Vector3f v26 = new Vector3f(0.031250F, 0.437500F, -0.062500F);
        Vector3f v28 = new Vector3f(-0.031250F, 1.437500F, 0.062500F);
        Vector3f v14 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v25 = new Vector3f(0.031250F, 0.437500F, 0.062500F);
        Vector3f v22 = new Vector3f(0.031250F, 0.375000F, -0.062500F);
        Vector3f v8 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v15 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v0 = new Vector3f(0.031250F, 0.312500F, 0.062500F);
        Vector3f v17 = new Vector3f(-0.031250F, -0.062500F, 0.062500F);
        Vector2f v71 = new Vector2f(0.93750F, 0.06250F);
        Vector2f v97 = new Vector2f(0.84375F, 0.28125F);
        Vector2f v109 = new Vector2f(0.18750F, 0.78125F);
        Vector2f v73 = new Vector2f(0.90625F, 0.09375F);
        Vector2f v125 = new Vector2f(0.12500F, 0.28125F);
        Vector2f v101 = new Vector2f(0.68750F, 0.28125F);
        Vector2f v87 = new Vector2f(0.62500F, 0.09375F);
        Vector2f v110 = new Vector2f(0.18750F, 0.28125F);
        Vector2f v48 = new Vector2f(0.00000F, 0.06250F);
        Vector2f v99 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v51 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v81 = new Vector2f(0.87500F, 0.00000F);
        Vector2f v53 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v69 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v130 = new Vector2f(0.46875F, 0.28125F);
        Vector2f v56 = new Vector2f(0.18750F, 0.06250F);
        Vector2f v127 = new Vector2f(0.62500F, 0.28125F);
        Vector2f v137 = new Vector2f(0.59375F, 0.28125F);
        Vector2f v74 = new Vector2f(0.90625F, 0.06250F);
        Vector2f v98 = new Vector2f(0.87500F, 0.28125F);
        Vector2f v95 = new Vector2f(0.87500F, 0.78125F);
        Vector2f v84 = new Vector2f(0.71875F, 0.09375F);
        Vector2f v57 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v75 = new Vector2f(0.81250F, 0.06250F);
        Vector2f v54 = new Vector2f(0.15625F, 0.06250F);
        Vector2f v77 = new Vector2f(0.75000F, 0.09375F);
        Vector2f v118 = new Vector2f(0.15625F, 0.78125F);
        Vector2f v83 = new Vector2f(0.87500F, 0.06250F);
        Vector2f v132 = new Vector2f(0.37500F, 0.81250F);
        Vector2f v86 = new Vector2f(0.62500F, 0.06250F);
        Vector2f v93 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v58 = new Vector2f(0.18750F, 0.09375F);
        Vector2f v92 = new Vector2f(0.75000F, 0.28125F);
        Vector2f v133 = new Vector2f(0.53125F, 0.28125F);
        Vector2f v50 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v61 = new Vector2f(0.18750F, 0.12500F);
        Vector2f v105 = new Vector2f(0.34375F, 0.78125F);
        Vector2f v64 = new Vector2f(0.46875F, 0.09375F);
        Vector2f v128 = new Vector2f(0.68750F, 0.81250F);
        Vector2f v88 = new Vector2f(0.56250F, 0.06250F);
        Vector2f v52 = new Vector2f(0.09375F, 0.06250F);
        Vector2f v60 = new Vector2f(0.28125F, 0.12500F);
        Vector2f v107 = new Vector2f(0.25000F, 0.28125F);
        Vector2f v89 = new Vector2f(0.65625F, 0.06250F);
        Vector2f v72 = new Vector2f(0.93750F, 0.09375F);
        Vector2f v55 = new Vector2f(0.15625F, 0.21875F);
        Vector2f v104 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v103 = new Vector2f(0.37500F, 0.28125F);
        Vector2f v135 = new Vector2f(0.46875F, 0.18750F);
        Vector2f v126 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v80 = new Vector2f(0.84375F, 0.09375F);
        Vector2f v123 = new Vector2f(0.09375F, 0.28125F);
        Vector2f v138 = new Vector2f(0.59375F, 0.18750F);
        Vector2f v136 = new Vector2f(0.53125F, 0.18750F);
        Vector2f v129 = new Vector2f(0.62500F, 0.81250F);
        Vector2f v82 = new Vector2f(0.84375F, 0.00000F);
        Vector2f v131 = new Vector2f(0.46875F, 0.81250F);
        Vector2f v91 = new Vector2f(0.78125F, 0.28125F);
        Vector2f v115 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v124 = new Vector2f(0.09375F, 0.78125F);
        Vector2f v62 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v66 = new Vector2f(0.46875F, 0.00000F);
        Vector2f v121 = new Vector2f(0.06250F, 0.78125F);
        Vector2f v120 = new Vector2f(0.06250F, 0.28125F);
        Vector2f v96 = new Vector2f(0.84375F, 0.78125F);
        Vector2f v63 = new Vector2f(0.37500F, 0.12500F);
        Vector2f v117 = new Vector2f(0.15625F, 0.28125F);
        Vector2f v116 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v76 = new Vector2f(0.81250F, 0.09375F);
        Vector2f v67 = new Vector2f(0.37500F, 0.00000F);
        Vector2f v78 = new Vector2f(0.75000F, 0.06250F);
        Vector2f v106 = new Vector2f(0.34375F, 0.28125F);
        Vector2f v108 = new Vector2f(0.25000F, 0.78125F);
        Vector2f v111 = new Vector2f(0.28125F, 0.28125F);
        Vector2f v65 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v90 = new Vector2f(0.65625F, 0.09375F);
        Vector2f v79 = new Vector2f(0.84375F, 0.06250F);
        Vector2f v70 = new Vector2f(0.28125F, 0.00000F);
        Vector2f v112 = new Vector2f(0.28125F, 0.78125F);
        Vector2f v94 = new Vector2f(0.78125F, 0.21875F);
        Vector2f v49 = new Vector2f(0.06250F, 0.06250F);
        Vector2f v68 = new Vector2f(0.56250F, 0.09375F);
        Vector2f v122 = new Vector2f(0.00000F, 0.78125F);
        Vector2f v102 = new Vector2f(0.78125F, 0.78125F);
        Vector2f v113 = new Vector2f(0.28125F, 0.21875F);
        Vector2f v100 = new Vector2f(0.68750F, 0.78125F);
        Vector2f v119 = new Vector2f(0.00000F, 0.28125F);
        Vector2f v59 = new Vector2f(0.28125F, 0.09375F);
        Vector2f v114 = new Vector2f(0.31250F, 0.28125F);
        Vector2f v134 = new Vector2f(0.53125F, 0.81250F);
        Vector2f v85 = new Vector2f(0.71875F, 0.06250F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v51, v139, v2, c0, v50, v139, v1, c0, v49, v139, v0, c0, v48, v139));
        builder.add(new SimpleQuad(v2, c0, v50, v140, v5, c0, v53, v140, v4, c0, v52, v140, v1, c0, v49, v140));
        builder.add(new SimpleQuad(v5, c0, v53, v141, v7, c0, v55, v141, v6, c0, v54, v141, v4, c0, v52, v141));
        builder.add(new SimpleQuad(v7, c0, v55, v142, v3, c0, v57, v142, v0, c0, v56, v142, v6, c0, v54, v142));
        builder.add(new SimpleQuad(v11, c0, v61, v139, v10, c0, v60, v139, v9, c0, v59, v139, v8, c0, v58, v139));
        builder.add(new SimpleQuad(v10, c0, v60, v140, v13, c0, v63, v140, v12, c0, v62, v140, v9, c0, v59, v140));
        builder.add(new SimpleQuad(v13, c0, v63, v141, v15, c0, v65, v141, v14, c0, v64, v141, v12, c0, v62, v141));
        builder.add(new SimpleQuad(v13, c0, v64, v143, v10, c0, v62, v143, v11, c0, v67, v143, v15, c0, v66, v143));
        builder.add(new SimpleQuad(v15, c0, v65, v142, v11, c0, v69, v142, v8, c0, v68, v142, v14, c0, v64, v142));
        builder.add(new SimpleQuad(v8, c0, v70, v144, v9, c0, v59, v144, v12, c0, v62, v144, v14, c0, v67, v144));
        HILT_QUADS = builder.build();

        // HiltNoTint
        Vector4f c1 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v7, c1, v74, v142, v17, c1, v73, v142, v16, c1, v72, v142, v3, c1, v71, v142));
        builder.add(new SimpleQuad(v3, c1, v78, v139, v16, c1, v77, v139, v18, c1, v76, v139, v2, c1, v75, v139));
        builder.add(new SimpleQuad(v2, c1, v75, v140, v18, c1, v76, v140, v19, c1, v80, v140, v5, c1, v79, v140));
        builder.add(new SimpleQuad(v5, c1, v79, v141, v19, c1, v80, v141, v17, c1, v73, v141, v7, c1, v74, v141));
        builder.add(new SimpleQuad(v19, c1, v83, v143, v18, c1, v79, v143, v16, c1, v82, v143, v17, c1, v81, v143));
        builder.add(new SimpleQuad(v24, c1, v85, v142, v21, c1, v84, v142, v20, c1, v77, v142, v25, c1, v78, v142));
        builder.add(new SimpleQuad(v25, c1, v88, v139, v20, c1, v68, v139, v22, c1, v87, v139, v26, c1, v86, v139));
        builder.add(new SimpleQuad(v26, c1, v86, v140, v22, c1, v87, v140, v23, c1, v90, v140, v27, c1, v89, v140));
        builder.add(new SimpleQuad(v27, c1, v89, v141, v23, c1, v90, v141, v21, c1, v84, v141, v24, c1, v85, v141));
        HILT_NO_TINT_QUADS = builder.build();

        // BladeOff
        Vector4f c2 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v28, c2, v94, v144, v29, c2, v93, v144, v30, c2, v92, v144, v31, c2, v91, v144));
        builder.add(new SimpleQuad(v29, c2, v98, v142, v28, c2, v97, v142, v24, c2, v96, v142, v25, c2, v95, v142));
        builder.add(new SimpleQuad(v30, c2, v92, v139, v29, c2, v101, v139, v25, c2, v100, v139, v26, c2, v99, v139));
        builder.add(new SimpleQuad(v31, c2, v91, v140, v30, c2, v92, v140, v26, c2, v99, v140, v27, c2, v102, v140));
        builder.add(new SimpleQuad(v28, c2, v97, v141, v31, c2, v91, v141, v27, c2, v102, v141, v24, c2, v96, v141));
        BLADE_OFF_QUADS = builder.build();

        // BladeMid
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v31, c3, v106, v142, v27, c3, v105, v142, v26, c3, v104, v142, v30, c3, v103, v142));
        builder.add(new SimpleQuad(v28, c3, v110, v139, v24, c3, v109, v139, v27, c3, v108, v139, v31, c3, v107, v139));
        builder.add(new SimpleQuad(v29, c3, v107, v140, v25, c3, v108, v140, v24, c3, v112, v140, v28, c3, v111, v140));
        builder.add(new SimpleQuad(v30, c3, v111, v141, v26, c3, v112, v141, v25, c3, v105, v141, v29, c3, v106, v141));
        builder.add(new SimpleQuad(v28, c3, v115, v143, v31, c3, v114, v143, v30, c3, v111, v143, v29, c3, v113, v143));
        builder.add(new SimpleQuad(v25, c3, v116, v144, v26, c3, v107, v144, v27, c3, v111, v144, v24, c3, v113, v144));
        BLADE_MID_QUADS = builder.build();

        // BladeIn
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c4, v118, v142, v34, c4, v109, v142, v33, c4, v110, v142, v32, c4, v117, v142));
        builder.add(new SimpleQuad(v34, c4, v122, v139, v37, c4, v121, v139, v36, c4, v120, v139, v33, c4, v119, v139));
        builder.add(new SimpleQuad(v37, c4, v121, v140, v39, c4, v124, v140, v38, c4, v123, v140, v36, c4, v120, v140));
        builder.add(new SimpleQuad(v39, c4, v124, v141, v35, c4, v118, v141, v32, c4, v117, v141, v38, c4, v123, v141));
        builder.add(new SimpleQuad(v32, c4, v53, v144, v33, c4, v50, v144, v36, c4, v120, v144, v38, c4, v123, v144));
        builder.add(new SimpleQuad(v35, c4, v126, v143, v39, c4, v125, v143, v37, c4, v123, v143, v34, c4, v53, v143));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v43, c5, v129, v142, v42, c5, v128, v142, v41, c5, v101, v142, v40, c5, v127, v142));
        builder.add(new SimpleQuad(v42, c5, v132, v139, v45, c5, v131, v139, v44, c5, v130, v139, v41, c5, v103, v139));
        builder.add(new SimpleQuad(v45, c5, v131, v140, v47, c5, v134, v140, v46, c5, v133, v140, v44, c5, v130, v140));
        builder.add(new SimpleQuad(v47, c5, v134, v141, v43, c5, v129, v141, v40, c5, v127, v141, v46, c5, v133, v141));
        builder.add(new SimpleQuad(v40, c5, v136, v144, v41, c5, v135, v144, v44, c5, v130, v144, v46, c5, v133, v144));
        builder.add(new SimpleQuad(v42, c5, v136, v143, v43, c5, v138, v143, v47, c5, v137, v143, v45, c5, v133, v143));
        BLADE_OUT_QUADS = builder.build();
    }
}
