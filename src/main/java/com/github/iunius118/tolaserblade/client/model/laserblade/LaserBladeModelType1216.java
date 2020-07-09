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

public class LaserBladeModelType1216 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1216.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        if (transformType == ItemCameraTransforms.TransformType.FIXED || transformType == ItemCameraTransforms.TransformType.GUI) {
            matrixStack.translate(0.0D, 0.0625D, 0.0D);
        }

        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v23 = new Vector3f(-0.062500F, 0.312500F, -0.062500F);
        Vector3f v29 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
        Vector3f v19 = new Vector3f(-0.062500F, 0.312500F, 0.062500F);
        Vector3f v8 = new Vector3f(-0.062500F, 0.000000F, 0.062500F);
        Vector3f v28 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v42 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v20 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
        Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v9 = new Vector3f(0.062500F, 0.000000F, 0.062500F);
        Vector3f v24 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v119 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v12 = new Vector3f(0.062500F, 0.000000F, -0.062500F);
        Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v30 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v21 = new Vector3f(0.062500F, 0.312500F, -0.062500F);
        Vector3f v117 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v18 = new Vector3f(0.062500F, 0.312500F, 0.062500F);
        Vector3f v1 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v31 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
        Vector3f v22 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
        Vector3f v116 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v115 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v41 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector3f v43 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v40 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v10 = new Vector3f(0.062500F, -0.062500F, 0.062500F);
        Vector3f v37 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v11 = new Vector3f(-0.062500F, -0.062500F, 0.062500F);
        Vector3f v36 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v15 = new Vector3f(-0.062500F, -0.062500F, -0.062500F);
        Vector3f v39 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v17 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
        Vector3f v26 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
        Vector3f v38 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v34 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v13 = new Vector3f(0.062500F, -0.062500F, -0.062500F);
        Vector3f v35 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v33 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v32 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v6 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v16 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
        Vector3f v120 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v27 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
        Vector3f v0 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v25 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v118 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v14 = new Vector3f(-0.062500F, 0.000000F, -0.062500F);
        Vector2f v61 = new Vector2f(0.37500F, 0.06250F);
        Vector2f v78 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v49 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v113 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v45 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v101 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v58 = new Vector2f(0.62500F, 0.06250F);
        Vector2f v54 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v51 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v100 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v108 = new Vector2f(0.46875F, 0.78125F);
        Vector2f v62 = new Vector2f(0.43750F, 0.06250F);
        Vector2f v47 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v93 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v73 = new Vector2f(0.18750F, 0.06250F);
        Vector2f v70 = new Vector2f(0.31250F, 0.06250F);
        Vector2f v53 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v63 = new Vector2f(0.43750F, 0.09375F);
        Vector2f v56 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v50 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v91 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v103 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v104 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v64 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v76 = new Vector2f(0.25000F, 0.06250F);
        Vector2f v65 = new Vector2f(0.50000F, 0.06250F);
        Vector2f v98 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v109 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v112 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v68 = new Vector2f(0.50000F, 0.00000F);
        Vector2f v79 = new Vector2f(0.25000F, 0.00000F);
        Vector2f v46 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v71 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v106 = new Vector2f(0.65625F, 0.78125F);
        Vector2f v90 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v77 = new Vector2f(0.25000F, 0.09375F);
        Vector2f v110 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v85 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v60 = new Vector2f(0.56250F, 0.09375F);
        Vector2f v92 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v89 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v75 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v44 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v74 = new Vector2f(0.18750F, 0.09375F);
        Vector2f v96 = new Vector2f(0.18750F, 0.75000F);
        Vector2f v87 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v114 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v102 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v97 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v55 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v67 = new Vector2f(0.56250F, 0.00000F);
        Vector2f v107 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v82 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v83 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v105 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v81 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v111 = new Vector2f(0.56250F, 0.78125F);
        Vector2f v99 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v48 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v69 = new Vector2f(0.43750F, 0.00000F);
        Vector2f v86 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v88 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v57 = new Vector2f(0.56250F, 0.06250F);
        Vector2f v84 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v80 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v95 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v94 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v52 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v72 = new Vector2f(0.12500F, 0.06250F);
        Vector2f v66 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v59 = new Vector2f(0.62500F, 0.09375F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v47, v115, v2, c0, v46, v115, v1, c0, v45, v115, v0, c0, v44, v115));
        builder.add(new SimpleQuad(v2, c0, v46, v116, v5, c0, v49, v116, v4, c0, v48, v116, v1, c0, v45, v116));
        builder.add(new SimpleQuad(v5, c0, v49, v117, v7, c0, v51, v117, v6, c0, v50, v117, v4, c0, v48, v117));
        builder.add(new SimpleQuad(v5, c0, v50, v118, v2, c0, v48, v118, v3, c0, v53, v118, v7, c0, v52, v118));
        builder.add(new SimpleQuad(v7, c0, v51, v119, v3, c0, v55, v119, v0, c0, v54, v119, v6, c0, v50, v119));
        builder.add(new SimpleQuad(v1, c0, v45, v120, v4, c0, v48, v120, v6, c0, v53, v120, v0, c0, v56, v120));
        builder.add(new SimpleQuad(v11, c0, v60, v119, v10, c0, v59, v119, v9, c0, v58, v119, v8, c0, v57, v119));
        builder.add(new SimpleQuad(v10, c0, v64, v115, v13, c0, v63, v115, v12, c0, v62, v115, v9, c0, v61, v115));
        builder.add(new SimpleQuad(v13, c0, v63, v116, v15, c0, v66, v116, v14, c0, v65, v116, v12, c0, v62, v116));
        builder.add(new SimpleQuad(v15, c0, v66, v117, v11, c0, v60, v117, v8, c0, v57, v117, v14, c0, v65, v117));
        builder.add(new SimpleQuad(v15, c0, v57, v118, v13, c0, v65, v118, v10, c0, v68, v118, v11, c0, v67, v118));
        builder.add(new SimpleQuad(v8, c0, v68, v120, v9, c0, v69, v120, v12, c0, v62, v120, v14, c0, v65, v120));
        builder.add(new SimpleQuad(v19, c0, v71, v119, v18, c0, v64, v119, v17, c0, v61, v119, v16, c0, v70, v119));
        builder.add(new SimpleQuad(v18, c0, v75, v115, v21, c0, v74, v115, v20, c0, v73, v115, v17, c0, v72, v115));
        builder.add(new SimpleQuad(v21, c0, v74, v116, v23, c0, v77, v116, v22, c0, v76, v116, v20, c0, v73, v116));
        builder.add(new SimpleQuad(v23, c0, v77, v117, v19, c0, v71, v117, v16, c0, v70, v117, v22, c0, v76, v117));
        builder.add(new SimpleQuad(v23, c0, v70, v118, v21, c0, v76, v118, v18, c0, v79, v118, v19, c0, v78, v118));
        builder.add(new SimpleQuad(v16, c0, v79, v120, v17, c0, v80, v120, v20, c0, v73, v120, v22, c0, v76, v120));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c1, v84, v119, v26, c1, v83, v119, v25, c1, v82, v119, v24, c1, v81, v119));
        builder.add(new SimpleQuad(v26, c1, v88, v115, v29, c1, v87, v115, v28, c1, v86, v115, v25, c1, v85, v115));
        builder.add(new SimpleQuad(v29, c1, v87, v116, v31, c1, v90, v116, v30, c1, v89, v116, v28, c1, v86, v116));
        builder.add(new SimpleQuad(v31, c1, v90, v117, v27, c1, v84, v117, v24, c1, v81, v117, v30, c1, v89, v117));
        builder.add(new SimpleQuad(v24, c1, v49, v120, v25, c1, v46, v120, v28, c1, v86, v120, v30, c1, v89, v120));
        builder.add(new SimpleQuad(v27, c1, v51, v118, v31, c1, v81, v118, v29, c1, v89, v118, v26, c1, v49, v118));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v16, c2, v94, v119, v17, c2, v93, v119, v33, c2, v92, v119, v32, c2, v91, v119));
        builder.add(new SimpleQuad(v17, c2, v97, v115, v20, c2, v96, v115, v34, c2, v95, v115, v33, c2, v82, v115));
        builder.add(new SimpleQuad(v20, c2, v96, v116, v22, c2, v99, v116, v35, c2, v98, v116, v34, c2, v95, v116));
        builder.add(new SimpleQuad(v22, c2, v99, v117, v16, c2, v94, v117, v32, c2, v91, v117, v35, c2, v98, v117));
        builder.add(new SimpleQuad(v32, c2, v101, v120, v33, c2, v100, v120, v34, c2, v95, v120, v35, c2, v98, v120));
        builder.add(new SimpleQuad(v17, c2, v101, v118, v16, c2, v102, v118, v22, c2, v91, v118, v20, c2, v98, v118));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v39, c3, v106, v119, v38, c3, v105, v119, v37, c3, v104, v119, v36, c3, v103, v119));
        builder.add(new SimpleQuad(v38, c3, v109, v115, v41, c3, v108, v115, v40, c3, v107, v115, v37, c3, v92, v115));
        builder.add(new SimpleQuad(v41, c3, v108, v116, v43, c3, v111, v116, v42, c3, v110, v116, v40, c3, v107, v116));
        builder.add(new SimpleQuad(v43, c3, v111, v117, v39, c3, v106, v117, v36, c3, v103, v117, v42, c3, v110, v117));
        builder.add(new SimpleQuad(v36, c3, v113, v120, v37, c3, v112, v120, v40, c3, v107, v120, v42, c3, v110, v120));
        builder.add(new SimpleQuad(v43, c3, v103, v118, v41, c3, v110, v118, v38, c3, v113, v118, v39, c3, v114, v118));
        BLADE_OUT_QUADS = builder.build();
    }
}
