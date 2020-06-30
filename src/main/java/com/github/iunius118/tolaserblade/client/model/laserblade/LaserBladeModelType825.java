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

public class LaserBladeModelType825 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_825.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
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
        Vector3f v21 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
        Vector3f v20 = new Vector3f(0.062500F, 2.375000F, -0.062500F);
        Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
        Vector3f v29 = new Vector3f(0.125000F, 0.375000F, -0.125000F);
        Vector3f v100 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
        Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
        Vector3f v32 = new Vector3f(-0.187500F, 2.500000F, 0.187500F);
        Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
        Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
        Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v33 = new Vector3f(0.187500F, 2.500000F, 0.187500F);
        Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
        Vector3f v105 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v31 = new Vector3f(-0.125000F, 0.375000F, -0.125000F);
        Vector3f v35 = new Vector3f(-0.187500F, 0.375000F, 0.187500F);
        Vector3f v23 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
        Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
        Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v101 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v19 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
        Vector3f v102 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v103 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
        Vector3f v24 = new Vector3f(-0.125000F, 2.437500F, 0.125000F);
        Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v18 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
        Vector3f v38 = new Vector3f(-0.187500F, 2.500000F, -0.187500F);
        Vector3f v26 = new Vector3f(0.125000F, 0.375000F, 0.125000F);
        Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v37 = new Vector3f(0.187500F, 0.375000F, -0.187500F);
        Vector3f v36 = new Vector3f(0.187500F, 2.500000F, -0.187500F);
        Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
        Vector3f v34 = new Vector3f(0.187500F, 0.375000F, 0.187500F);
        Vector3f v27 = new Vector3f(-0.125000F, 0.375000F, 0.125000F);
        Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
        Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
        Vector3f v25 = new Vector3f(0.125000F, 2.437500F, 0.125000F);
        Vector3f v30 = new Vector3f(-0.125000F, 2.437500F, -0.125000F);
        Vector3f v16 = new Vector3f(-0.062500F, 2.375000F, 0.062500F);
        Vector3f v17 = new Vector3f(0.062500F, 2.375000F, 0.062500F);
        Vector3f v28 = new Vector3f(0.125000F, 2.437500F, -0.125000F);
        Vector3f v22 = new Vector3f(-0.062500F, 2.375000F, -0.062500F);
        Vector3f v39 = new Vector3f(-0.187500F, 0.375000F, -0.187500F);
        Vector3f v104 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
        Vector2f v61 = new Vector2f(0.06250F, 0.00000F);
        Vector2f v85 = new Vector2f(0.18750F, 0.15625F);
        Vector2f v42 = new Vector2f(0.50000F, 0.12500F);
        Vector2f v48 = new Vector2f(0.31250F, 0.09375F);
        Vector2f v97 = new Vector2f(0.46875F, 0.12500F);
        Vector2f v67 = new Vector2f(0.12500F, 0.21875F);
        Vector2f v98 = new Vector2f(0.56250F, 0.12500F);
        Vector2f v90 = new Vector2f(0.75000F, 0.78125F);
        Vector2f v82 = new Vector2f(0.12500F, 0.75000F);
        Vector2f v40 = new Vector2f(0.40625F, 0.09375F);
        Vector2f v96 = new Vector2f(0.56250F, 0.78125F);
        Vector2f v65 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v77 = new Vector2f(0.37500F, 0.21875F);
        Vector2f v99 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v95 = new Vector2f(0.56250F, 0.21875F);
        Vector2f v55 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v44 = new Vector2f(0.12500F, 0.09375F);
        Vector2f v74 = new Vector2f(0.06250F, 0.21875F);
        Vector2f v72 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v56 = new Vector2f(0.06250F, 0.03125F);
        Vector2f v92 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v91 = new Vector2f(0.65625F, 0.78125F);
        Vector2f v89 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v73 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v88 = new Vector2f(0.65625F, 0.21875F);
        Vector2f v63 = new Vector2f(0.12500F, 0.03125F);
        Vector2f v60 = new Vector2f(0.09375F, 0.00000F);
        Vector2f v71 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v52 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v46 = new Vector2f(0.21875F, 0.12500F);
        Vector2f v41 = new Vector2f(0.50000F, 0.09375F);
        Vector2f v43 = new Vector2f(0.40625F, 0.12500F);
        Vector2f v86 = new Vector2f(0.25000F, 0.15625F);
        Vector2f v70 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v58 = new Vector2f(0.09375F, 0.03125F);
        Vector2f v75 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v54 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v83 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v81 = new Vector2f(0.18750F, 0.75000F);
        Vector2f v68 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v80 = new Vector2f(0.18750F, 0.21875F);
        Vector2f v79 = new Vector2f(0.31250F, 0.75000F);
        Vector2f v78 = new Vector2f(0.37500F, 0.75000F);
        Vector2f v53 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v62 = new Vector2f(0.21875F, 0.00000F);
        Vector2f v45 = new Vector2f(0.21875F, 0.09375F);
        Vector2f v51 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v76 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v84 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v94 = new Vector2f(0.37500F, 0.78125F);
        Vector2f v69 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v93 = new Vector2f(0.46875F, 0.78125F);
        Vector2f v64 = new Vector2f(0.12500F, 0.18750F);
        Vector2f v57 = new Vector2f(0.06250F, 0.18750F);
        Vector2f v50 = new Vector2f(0.40625F, 0.00000F);
        Vector2f v87 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v59 = new Vector2f(0.09375F, 0.18750F);
        Vector2f v66 = new Vector2f(0.09375F, 0.21875F);
        Vector2f v49 = new Vector2f(0.31250F, 0.12500F);
        Vector2f v47 = new Vector2f(0.12500F, 0.12500F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v43, v100, v2, c0, v42, v100, v1, c0, v41, v100, v0, c0, v40, v100));
        builder.add(new SimpleQuad(v2, c0, v47, v101, v5, c0, v46, v101, v4, c0, v45, v101, v1, c0, v44, v101));
        builder.add(new SimpleQuad(v5, c0, v46, v102, v7, c0, v49, v102, v6, c0, v48, v102, v4, c0, v45, v102));
        builder.add(new SimpleQuad(v7, c0, v49, v103, v3, c0, v43, v103, v0, c0, v40, v103, v6, c0, v48, v103));
        builder.add(new SimpleQuad(v7, c0, v40, v104, v5, c0, v48, v104, v2, c0, v51, v104, v3, c0, v50, v104));
        builder.add(new SimpleQuad(v11, c0, v55, v101, v10, c0, v54, v101, v9, c0, v53, v101, v8, c0, v52, v101));
        builder.add(new SimpleQuad(v10, c0, v54, v102, v13, c0, v57, v102, v12, c0, v56, v102, v9, c0, v53, v102));
        builder.add(new SimpleQuad(v13, c0, v57, v103, v15, c0, v59, v103, v14, c0, v58, v103, v12, c0, v56, v103));
        builder.add(new SimpleQuad(v13, c0, v58, v104, v10, c0, v56, v104, v11, c0, v61, v104, v15, c0, v60, v104));
        builder.add(new SimpleQuad(v0, c0, v51, v105, v1, c0, v62, v105, v4, c0, v45, v105, v6, c0, v48, v105));
        builder.add(new SimpleQuad(v15, c0, v59, v100, v11, c0, v64, v100, v8, c0, v63, v100, v14, c0, v58, v100));
        builder.add(new SimpleQuad(v9, c0, v53, v105, v12, c0, v56, v105, v14, c0, v61, v105, v8, c0, v65, v105));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c1, v69, v100, v18, c1, v68, v100, v17, c1, v67, v100, v16, c1, v66, v100));
        builder.add(new SimpleQuad(v18, c1, v73, v101, v21, c1, v72, v101, v20, c1, v71, v101, v17, c1, v70, v101));
        builder.add(new SimpleQuad(v21, c1, v72, v102, v23, c1, v75, v102, v22, c1, v74, v102, v20, c1, v71, v102));
        builder.add(new SimpleQuad(v23, c1, v75, v103, v19, c1, v69, v103, v16, c1, v66, v103, v22, c1, v74, v103));
        builder.add(new SimpleQuad(v16, c1, v57, v105, v17, c1, v54, v105, v20, c1, v71, v105, v22, c1, v74, v105));
        builder.add(new SimpleQuad(v19, c1, v59, v104, v23, c1, v66, v104, v21, c1, v74, v104, v18, c1, v57, v104));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c2, v79, v100, v26, c2, v78, v100, v25, c2, v77, v100, v24, c2, v76, v100));
        builder.add(new SimpleQuad(v26, c2, v82, v101, v29, c2, v81, v101, v28, c2, v80, v101, v25, c2, v67, v101));
        builder.add(new SimpleQuad(v29, c2, v81, v102, v31, c2, v84, v102, v30, c2, v83, v102, v28, c2, v80, v102));
        builder.add(new SimpleQuad(v31, c2, v84, v103, v27, c2, v79, v103, v24, c2, v76, v103, v30, c2, v83, v103));
        builder.add(new SimpleQuad(v24, c2, v86, v105, v25, c2, v85, v105, v28, c2, v80, v105, v30, c2, v83, v105));
        builder.add(new SimpleQuad(v26, c2, v86, v104, v27, c2, v87, v104, v31, c2, v76, v104, v29, c2, v83, v104));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c3, v91, v100, v34, c3, v90, v100, v33, c3, v89, v100, v32, c3, v88, v100));
        builder.add(new SimpleQuad(v34, c3, v94, v101, v37, c3, v93, v101, v36, c3, v92, v101, v33, c3, v77, v101));
        builder.add(new SimpleQuad(v37, c3, v93, v102, v39, c3, v96, v102, v38, c3, v95, v102, v36, c3, v92, v102));
        builder.add(new SimpleQuad(v39, c3, v96, v103, v35, c3, v91, v103, v32, c3, v88, v103, v38, c3, v95, v103));
        builder.add(new SimpleQuad(v32, c3, v98, v105, v33, c3, v97, v105, v36, c3, v92, v105, v38, c3, v95, v105));
        builder.add(new SimpleQuad(v39, c3, v88, v104, v37, c3, v95, v104, v34, c3, v98, v104, v35, c3, v99, v104));
        BLADE_OUT_QUADS = builder.build();
    }
}
