package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class LaserBladeModelType305 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_305.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, hiltQuads, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            renderQuads(matrixStack, currentBuffer, bladeOffQuads, 0xFFCCCCCC, lightmapCoord, overlayColor);
            return;
        }

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderQuads(matrixStack, currentBuffer, bladeMidQuads, color.simpleOuterColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, bladeInQuads, color.simpleInnerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, bladeOutQuads, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v13 = new Vector3f(-0.031250F, 0.312500F, -0.093750F);
    private static final Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.062500F);
    private static final Vector3f v27 = new Vector3f(-0.025000F, 0.381250F, 0.050000F);
    private static final Vector3f v9 = new Vector3f(0.031250F, 0.375000F, -0.093750F);
    private static final Vector3f v29 = new Vector3f(0.025000F, 0.381250F, -0.050000F);
    private static final Vector3f v32 = new Vector3f(-0.062500F, 1.468750F, 0.093750F);
    private static final Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.062500F);
    private static final Vector3f v34 = new Vector3f(0.062500F, 0.375000F, 0.093750F);
    private static final Vector3f v120 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v20 = new Vector3f(-0.031250F, 1.437500F, 0.062500F);
    private static final Vector3f v22 = new Vector3f(0.031250F, 1.437500F, -0.062500F);
    private static final Vector3f v17 = new Vector3f(0.031250F, 0.375000F, 0.062500F);
    private static final Vector3f v122 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v10 = new Vector3f(0.031250F, 0.312500F, -0.093750F);
    private static final Vector3f v30 = new Vector3f(-0.025000F, 1.431250F, -0.050000F);
    private static final Vector3f v18 = new Vector3f(0.031250F, 0.375000F, -0.062500F);
    private static final Vector3f v4 = new Vector3f(-0.031250F, 0.312500F, -0.062500F);
    private static final Vector3f v1 = new Vector3f(0.031250F, 0.312500F, -0.062500F);
    private static final Vector3f v16 = new Vector3f(-0.031250F, 0.375000F, 0.062500F);
    private static final Vector3f v28 = new Vector3f(0.025000F, 1.431250F, -0.050000F);
    private static final Vector3f v124 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v123 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.062500F);
    private static final Vector3f v24 = new Vector3f(-0.025000F, 1.431250F, 0.050000F);
    private static final Vector3f v119 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v14 = new Vector3f(-0.031250F, 0.375000F, 0.093750F);
    private static final Vector3f v35 = new Vector3f(-0.062500F, 0.375000F, 0.093750F);
    private static final Vector3f v39 = new Vector3f(-0.062500F, 0.375000F, -0.093750F);
    private static final Vector3f v26 = new Vector3f(0.025000F, 0.381250F, 0.050000F);
    private static final Vector3f v8 = new Vector3f(0.031250F, 0.375000F, 0.093750F);
    private static final Vector3f v12 = new Vector3f(-0.031250F, 0.375000F, -0.093750F);
    private static final Vector3f v38 = new Vector3f(-0.062500F, 1.468750F, -0.093750F);
    private static final Vector3f v11 = new Vector3f(0.031250F, 0.312500F, 0.093750F);
    private static final Vector3f v36 = new Vector3f(0.062500F, 1.468750F, -0.093750F);
    private static final Vector3f v31 = new Vector3f(-0.025000F, 0.381250F, -0.050000F);
    private static final Vector3f v33 = new Vector3f(0.062500F, 1.468750F, 0.093750F);
    private static final Vector3f v21 = new Vector3f(0.031250F, 1.437500F, 0.062500F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, 0.312500F, 0.093750F);
    private static final Vector3f v0 = new Vector3f(0.031250F, 0.312500F, 0.062500F);
    private static final Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.062500F);
    private static final Vector3f v6 = new Vector3f(-0.031250F, 0.312500F, 0.062500F);
    private static final Vector3f v23 = new Vector3f(-0.031250F, 1.437500F, -0.062500F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, 0.375000F, -0.062500F);
    private static final Vector3f v37 = new Vector3f(0.062500F, 0.375000F, -0.093750F);
    private static final Vector3f v121 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v25 = new Vector3f(0.025000F, 1.431250F, 0.050000F);
    private static final Vec2f v51 = new Vec2f(0.18750F, 0.06250F);
    private static final Vec2f v79 = new Vec2f(0.78125F, 0.28125F);
    private static final Vec2f v48 = new Vec2f(0.12500F, 0.00000F);
    private static final Vec2f v88 = new Vec2f(0.25000F, 0.81250F);
    private static final Vec2f v60 = new Vec2f(0.40625F, 0.12500F);
    private static final Vec2f v91 = new Vec2f(0.28125F, 0.28125F);
    private static final Vec2f v113 = new Vec2f(0.53125F, 0.28125F);
    private static final Vec2f v80 = new Vec2f(0.78125F, 0.81250F);
    private static final Vec2f v62 = new Vec2f(0.31250F, 0.00000F);
    private static final Vec2f v89 = new Vec2f(0.18750F, 0.81250F);
    private static final Vec2f v68 = new Vec2f(0.31250F, 0.01562F);
    private static final Vec2f v74 = new Vec2f(0.84375F, 0.81250F);
    private static final Vec2f v115 = new Vec2f(0.46875F, 0.18750F);
    private static final Vec2f v46 = new Vec2f(0.15625F, 0.06250F);
    private static final Vec2f v56 = new Vec2f(0.18750F, 0.12500F);
    private static final Vec2f v55 = new Vec2f(0.28125F, 0.12500F);
    private static final Vec2f v47 = new Vec2f(0.15625F, 0.21875F);
    private static final Vec2f v54 = new Vec2f(0.28125F, 0.09375F);
    private static final Vec2f v114 = new Vec2f(0.53125F, 0.84375F);
    private static final Vec2f v42 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v84 = new Vec2f(0.37500F, 0.81250F);
    private static final Vec2f v73 = new Vec2f(0.87500F, 0.81250F);
    private static final Vec2f v77 = new Vec2f(0.75000F, 0.81250F);
    private static final Vec2f v66 = new Vec2f(0.28125F, 0.07812F);
    private static final Vec2f v64 = new Vec2f(0.43750F, 0.09375F);
    private static final Vec2f v96 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v75 = new Vec2f(0.68750F, 0.28125F);
    private static final Vec2f v107 = new Vec2f(0.62500F, 0.28125F);
    private static final Vec2f v58 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f v98 = new Vec2f(0.15625F, 0.81250F);
    private static final Vec2f v94 = new Vec2f(0.31250F, 0.28125F);
    private static final Vec2f v76 = new Vec2f(0.75000F, 0.28125F);
    private static final Vec2f v90 = new Vec2f(0.18750F, 0.28125F);
    private static final Vec2f v100 = new Vec2f(0.06250F, 0.28125F);
    private static final Vec2f v85 = new Vec2f(0.34375F, 0.81250F);
    private static final Vec2f v111 = new Vec2f(0.46875F, 0.84375F);
    private static final Vec2f v92 = new Vec2f(0.28125F, 0.81250F);
    private static final Vec2f v118 = new Vec2f(0.59375F, 0.18750F);
    private static final Vec2f v81 = new Vec2f(0.75000F, 0.21875F);
    private static final Vec2f v109 = new Vec2f(0.62500F, 0.84375F);
    private static final Vec2f v112 = new Vec2f(0.37500F, 0.84375F);
    private static final Vec2f v40 = new Vec2f(0.00000F, 0.06250F);
    private static final Vec2f v78 = new Vec2f(0.68750F, 0.81250F);
    private static final Vec2f v116 = new Vec2f(0.53125F, 0.18750F);
    private static final Vec2f v86 = new Vec2f(0.34375F, 0.28125F);
    private static final Vec2f v108 = new Vec2f(0.68750F, 0.84375F);
    private static final Vec2f v63 = new Vec2f(0.34375F, 0.09375F);
    private static final Vec2f v117 = new Vec2f(0.59375F, 0.28125F);
    private static final Vec2f v93 = new Vec2f(0.28125F, 0.21875F);
    private static final Vec2f v95 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v105 = new Vec2f(0.12500F, 0.28125F);
    private static final Vec2f v65 = new Vec2f(0.43750F, 0.12500F);
    private static final Vec2f v104 = new Vec2f(0.09375F, 0.81250F);
    private static final Vec2f v52 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v67 = new Vec2f(0.31250F, 0.07812F);
    private static final Vec2f v101 = new Vec2f(0.06250F, 0.81250F);
    private static final Vec2f v99 = new Vec2f(0.00000F, 0.28125F);
    private static final Vec2f v106 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v102 = new Vec2f(0.00000F, 0.81250F);
    private static final Vec2f v43 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v103 = new Vec2f(0.09375F, 0.28125F);
    private static final Vec2f v71 = new Vec2f(0.84375F, 0.28125F);
    private static final Vec2f v50 = new Vec2f(0.12500F, 0.06250F);
    private static final Vec2f v72 = new Vec2f(0.87500F, 0.28125F);
    private static final Vec2f v97 = new Vec2f(0.15625F, 0.28125F);
    private static final Vec2f v70 = new Vec2f(0.28125F, 0.00000F);
    private static final Vec2f v41 = new Vec2f(0.06250F, 0.06250F);
    private static final Vec2f v57 = new Vec2f(0.31250F, 0.09375F);
    private static final Vec2f v87 = new Vec2f(0.25000F, 0.28125F);
    private static final Vec2f v45 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v44 = new Vec2f(0.09375F, 0.06250F);
    private static final Vec2f v69 = new Vec2f(0.28125F, 0.01562F);
    private static final Vec2f v53 = new Vec2f(0.18750F, 0.09375F);
    private static final Vec2f v110 = new Vec2f(0.46875F, 0.28125F);
    private static final Vec2f v49 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v61 = new Vec2f(0.34375F, 0.00000F);
    private static final Vec2f v83 = new Vec2f(0.37500F, 0.28125F);
    private static final Vec2f v82 = new Vec2f(0.78125F, 0.21875F);
    private static final Vec2f v59 = new Vec2f(0.40625F, 0.09375F);

    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v43, v119, v2, c, v42, v119, v1, c, v41, v119, v0, c, v40, v119));
        builder.add(new SimpleQuad(v2, c, v42, v120, v5, c, v45, v120, v4, c, v44, v120, v1, c, v41, v120));
        builder.add(new SimpleQuad(v5, c, v45, v121, v7, c, v47, v121, v6, c, v46, v121, v4, c, v44, v121));
        builder.add(new SimpleQuad(v5, c, v50, v122, v2, c, v44, v122, v3, c, v49, v122, v7, c, v48, v122));
        builder.add(new SimpleQuad(v7, c, v47, v123, v3, c, v52, v123, v0, c, v51, v123, v6, c, v46, v123));
        builder.add(new SimpleQuad(v11, c, v56, v119, v10, c, v55, v119, v9, c, v54, v119, v8, c, v53, v119));
        builder.add(new SimpleQuad(v10, c, v55, v120, v13, c, v58, v120, v12, c, v57, v120, v9, c, v54, v120));
        builder.add(new SimpleQuad(v13, c, v58, v121, v15, c, v60, v121, v14, c, v59, v121, v12, c, v57, v121));
        builder.add(new SimpleQuad(v13, c, v63, v122, v10, c, v57, v122, v11, c, v62, v122, v15, c, v61, v122));
        builder.add(new SimpleQuad(v15, c, v60, v123, v11, c, v65, v123, v8, c, v64, v123, v14, c, v59, v123));
        builder.add(new SimpleQuad(v12, c, v57, v124, v19, c, v67, v124, v18, c, v66, v124, v9, c, v54, v124));
        builder.add(new SimpleQuad(v8, c, v70, v124, v17, c, v69, v124, v16, c, v68, v124, v14, c, v62, v124));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOffQuads = getBladeOffQuads();

    public static List<SimpleQuad> getBladeOffQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v16, c, v74, v123, v17, c, v73, v123, v21, c, v72, v123, v20, c, v71, v123));
        builder.add(new SimpleQuad(v17, c, v78, v119, v18, c, v77, v119, v22, c, v76, v119, v21, c, v75, v119));
        builder.add(new SimpleQuad(v18, c, v77, v120, v19, c, v80, v120, v23, c, v79, v120, v22, c, v76, v120));
        builder.add(new SimpleQuad(v19, c, v80, v121, v16, c, v74, v121, v20, c, v71, v121, v23, c, v79, v121));
        builder.add(new SimpleQuad(v20, c, v82, v124, v21, c, v81, v124, v22, c, v76, v124, v23, c, v79, v124));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeMidQuads = getBladeMidQuads();

    public static List<SimpleQuad> getBladeMidQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c, v86, v123, v19, c, v85, v123, v18, c, v84, v123, v22, c, v83, v123));
        builder.add(new SimpleQuad(v20, c, v90, v119, v16, c, v89, v119, v19, c, v88, v119, v23, c, v87, v119));
        builder.add(new SimpleQuad(v21, c, v87, v120, v17, c, v88, v120, v16, c, v92, v120, v20, c, v91, v120));
        builder.add(new SimpleQuad(v22, c, v91, v121, v18, c, v92, v121, v17, c, v85, v121, v21, c, v86, v121));
        builder.add(new SimpleQuad(v20, c, v95, v122, v23, c, v94, v122, v22, c, v91, v122, v21, c, v93, v122));
        builder.add(new SimpleQuad(v17, c, v96, v124, v18, c, v87, v124, v19, c, v91, v124, v16, c, v93, v124));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c, v98, v123, v26, c, v89, v123, v25, c, v90, v123, v24, c, v97, v123));
        builder.add(new SimpleQuad(v26, c, v102, v119, v29, c, v101, v119, v28, c, v100, v119, v25, c, v99, v119));
        builder.add(new SimpleQuad(v29, c, v101, v120, v31, c, v104, v120, v30, c, v103, v120, v28, c, v100, v120));
        builder.add(new SimpleQuad(v31, c, v104, v121, v27, c, v98, v121, v24, c, v97, v121, v30, c, v103, v121));
        builder.add(new SimpleQuad(v24, c, v45, v124, v25, c, v42, v124, v28, c, v100, v124, v30, c, v103, v124));
        builder.add(new SimpleQuad(v27, c, v106, v122, v31, c, v105, v122, v29, c, v103, v122, v26, c, v45, v122));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c, v109, v123, v34, c, v108, v123, v33, c, v75, v123, v32, c, v107, v123));
        builder.add(new SimpleQuad(v34, c, v112, v119, v37, c, v111, v119, v36, c, v110, v119, v33, c, v83, v119));
        builder.add(new SimpleQuad(v37, c, v111, v120, v39, c, v114, v120, v38, c, v113, v120, v36, c, v110, v120));
        builder.add(new SimpleQuad(v39, c, v114, v121, v35, c, v109, v121, v32, c, v107, v121, v38, c, v113, v121));
        builder.add(new SimpleQuad(v32, c, v116, v124, v33, c, v115, v124, v36, c, v110, v124, v38, c, v113, v124));
        builder.add(new SimpleQuad(v34, c, v116, v122, v35, c, v118, v122, v39, c, v117, v122, v37, c, v113, v122));
        return builder.build();
    }
}
