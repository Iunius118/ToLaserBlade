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

public class LaserBladeModelType903 extends SimpleModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_903.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, hiltQuads, color.gripColor, lightmapCoord, overlayColor);
        renderQuads(matrixStack, currentBuffer, hiltNoTintQuads, -1, lightmapCoord, overlayColor);

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

    private static final Vector3f v1 = new Vector3f(0.031250F, 0.312500F, -0.062500F);
    private static final Vector3f v7 = new Vector3f(-0.031250F, 0.000000F, 0.062500F);
    private static final Vector3f v14 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, -0.062500F, -0.062500F);
    private static final Vector3f v4 = new Vector3f(-0.031250F, 0.312500F, -0.062500F);
    private static final Vector3f v39 = new Vector3f(-0.025000F, 0.443750F, -0.050000F);
    private static final Vector3f v32 = new Vector3f(-0.025000F, 1.431250F, 0.050000F);
    private static final Vector3f v23 = new Vector3f(-0.031250F, 0.375000F, -0.062500F);
    private static final Vector3f v37 = new Vector3f(0.025000F, 0.443750F, -0.050000F);
    private static final Vector3f v147 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v145 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v13 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v20 = new Vector3f(0.031250F, 0.375000F, 0.062500F);
    private static final Vector3f v42 = new Vector3f(0.062500F, 0.406250F, 0.093750F);
    private static final Vector3f v45 = new Vector3f(0.062500F, 0.406250F, -0.093750F);
    private static final Vector3f v146 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v30 = new Vector3f(0.031250F, 1.437500F, -0.062500F);
    private static final Vector3f v8 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v22 = new Vector3f(0.031250F, 0.375000F, -0.062500F);
    private static final Vector3f v15 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v144 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v9 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v143 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v27 = new Vector3f(-0.031250F, 0.437500F, -0.062500F);
    private static final Vector3f v47 = new Vector3f(-0.062500F, 0.406250F, -0.093750F);
    private static final Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v29 = new Vector3f(0.031250F, 1.437500F, 0.062500F);
    private static final Vector3f v44 = new Vector3f(0.062500F, 1.468750F, -0.093750F);
    private static final Vector3f v43 = new Vector3f(-0.062500F, 0.406250F, 0.093750F);
    private static final Vector3f v34 = new Vector3f(0.025000F, 0.443750F, 0.050000F);
    private static final Vector3f v18 = new Vector3f(0.031250F, -0.062500F, -0.062500F);
    private static final Vector3f v28 = new Vector3f(-0.031250F, 1.437500F, 0.062500F);
    private static final Vector3f v17 = new Vector3f(-0.031250F, -0.062500F, 0.062500F);
    private static final Vector3f v38 = new Vector3f(-0.025000F, 1.431250F, -0.050000F);
    private static final Vector3f v0 = new Vector3f(0.031250F, 0.312500F, 0.062500F);
    private static final Vector3f v148 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v3 = new Vector3f(0.031250F, 0.000000F, 0.062500F);
    private static final Vector3f v6 = new Vector3f(-0.031250F, 0.312500F, 0.062500F);
    private static final Vector3f v16 = new Vector3f(0.031250F, -0.062500F, 0.062500F);
    private static final Vector3f v41 = new Vector3f(0.062500F, 1.468750F, 0.093750F);
    private static final Vector3f v11 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v21 = new Vector3f(-0.031250F, 0.375000F, 0.062500F);
    private static final Vector3f v2 = new Vector3f(0.031250F, 0.000000F, -0.062500F);
    private static final Vector3f v5 = new Vector3f(-0.031250F, 0.000000F, -0.062500F);
    private static final Vector3f v25 = new Vector3f(0.031250F, 0.437500F, 0.062500F);
    private static final Vector3f v33 = new Vector3f(0.025000F, 1.431250F, 0.050000F);
    private static final Vector3f v36 = new Vector3f(0.025000F, 1.431250F, -0.050000F);
    private static final Vector3f v35 = new Vector3f(-0.025000F, 0.443750F, 0.050000F);
    private static final Vector3f v46 = new Vector3f(-0.062500F, 1.468750F, -0.093750F);
    private static final Vector3f v40 = new Vector3f(-0.062500F, 1.468750F, 0.093750F);
    private static final Vector3f v26 = new Vector3f(0.031250F, 0.437500F, -0.062500F);
    private static final Vector3f v31 = new Vector3f(-0.031250F, 1.437500F, -0.062500F);
    private static final Vector3f v24 = new Vector3f(-0.031250F, 0.437500F, 0.062500F);
    private static final Vector3f v12 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vec2f v86 = new Vec2f(0.75000F, 0.09447F);
    private static final Vec2f v139 = new Vec2f(0.46875F, 0.18750F);
    private static final Vec2f v121 = new Vec2f(0.15625F, 0.28125F);
    private static final Vec2f v96 = new Vec2f(0.75000F, 0.28125F);
    private static final Vec2f v77 = new Vec2f(0.75000F, 0.09375F);
    private static final Vec2f v125 = new Vec2f(0.06250F, 0.78125F);
    private static final Vec2f v56 = new Vec2f(0.18750F, 0.06250F);
    private static final Vec2f v81 = new Vec2f(0.87500F, 0.00000F);
    private static final Vec2f v140 = new Vec2f(0.53125F, 0.18750F);
    private static final Vec2f v115 = new Vec2f(0.28125F, 0.28125F);
    private static final Vec2f v83 = new Vec2f(0.84375F, 0.06250F);
    private static final Vec2f v109 = new Vec2f(0.34375F, 0.78125F);
    private static final Vec2f v82 = new Vec2f(0.84375F, 0.00000F);
    private static final Vec2f v106 = new Vec2f(0.78125F, 0.78083F);
    private static final Vec2f v111 = new Vec2f(0.25000F, 0.28125F);
    private static final Vec2f v108 = new Vec2f(0.37500F, 0.78125F);
    private static final Vec2f v126 = new Vec2f(0.00000F, 0.78125F);
    private static final Vec2f v70 = new Vec2f(0.28125F, 0.00000F);
    private static final Vec2f v141 = new Vec2f(0.59375F, 0.28125F);
    private static final Vec2f v69 = new Vec2f(0.56250F, 0.12500F);
    private static final Vec2f v137 = new Vec2f(0.53125F, 0.28125F);
    private static final Vec2f v128 = new Vec2f(0.09375F, 0.78125F);
    private static final Vec2f v84 = new Vec2f(0.87500F, 0.06250F);
    private static final Vec2f v60 = new Vec2f(0.28125F, 0.12500F);
    private static final Vec2f v85 = new Vec2f(0.75000F, 0.06250F);
    private static final Vec2f v107 = new Vec2f(0.37500F, 0.28125F);
    private static final Vec2f v76 = new Vec2f(0.81250F, 0.09375F);
    private static final Vec2f v71 = new Vec2f(0.93750F, 0.06178F);
    private static final Vec2f v118 = new Vec2f(0.31250F, 0.28125F);
    private static final Vec2f v100 = new Vec2f(0.84375F, 0.78082F);
    private static final Vec2f v65 = new Vec2f(0.46875F, 0.12500F);
    private static final Vec2f v55 = new Vec2f(0.15625F, 0.21875F);
    private static final Vec2f v92 = new Vec2f(0.56250F, 0.06250F);
    private static final Vec2f v99 = new Vec2f(0.87500F, 0.78053F);
    private static final Vec2f v134 = new Vec2f(0.46875F, 0.28125F);
    private static final Vec2f v58 = new Vec2f(0.18750F, 0.09375F);
    private static final Vec2f v54 = new Vec2f(0.15625F, 0.06250F);
    private static final Vec2f v142 = new Vec2f(0.59375F, 0.18750F);
    private static final Vec2f v73 = new Vec2f(0.90625F, 0.09375F);
    private static final Vec2f v57 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v138 = new Vec2f(0.53125F, 0.81250F);
    private static final Vec2f v110 = new Vec2f(0.34375F, 0.28125F);
    private static final Vec2f v68 = new Vec2f(0.56250F, 0.09375F);
    private static final Vec2f v120 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v135 = new Vec2f(0.46875F, 0.81250F);
    private static final Vec2f v72 = new Vec2f(0.93750F, 0.09375F);
    private static final Vec2f v133 = new Vec2f(0.62500F, 0.81250F);
    private static final Vec2f v132 = new Vec2f(0.68750F, 0.81250F);
    private static final Vec2f v131 = new Vec2f(0.62500F, 0.28125F);
    private static final Vec2f v51 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v103 = new Vec2f(0.75000F, 0.78053F);
    private static final Vec2f v62 = new Vec2f(0.37500F, 0.09375F);
    private static final Vec2f v50 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v136 = new Vec2f(0.37500F, 0.81250F);
    private static final Vec2f v102 = new Vec2f(0.87500F, 0.28125F);
    private static final Vec2f v127 = new Vec2f(0.09375F, 0.28125F);
    private static final Vec2f v67 = new Vec2f(0.37500F, 0.00000F);
    private static final Vec2f v104 = new Vec2f(0.68750F, 0.78053F);
    private static final Vec2f v79 = new Vec2f(0.84375F, 0.06208F);
    private static final Vec2f v124 = new Vec2f(0.06250F, 0.28125F);
    private static final Vec2f v123 = new Vec2f(0.00000F, 0.28125F);
    private static final Vec2f v122 = new Vec2f(0.15625F, 0.78125F);
    private static final Vec2f v94 = new Vec2f(0.65625F, 0.09447F);
    private static final Vec2f v119 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v93 = new Vec2f(0.65625F, 0.06280F);
    private static final Vec2f v53 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v90 = new Vec2f(0.62500F, 0.09447F);
    private static final Vec2f v116 = new Vec2f(0.28125F, 0.78125F);
    private static final Vec2f v114 = new Vec2f(0.18750F, 0.28125F);
    private static final Vec2f v112 = new Vec2f(0.25000F, 0.78125F);
    private static final Vec2f v113 = new Vec2f(0.18750F, 0.78125F);
    private static final Vec2f v117 = new Vec2f(0.28125F, 0.21875F);
    private static final Vec2f v95 = new Vec2f(0.78125F, 0.28125F);
    private static final Vec2f v105 = new Vec2f(0.68750F, 0.28125F);
    private static final Vec2f v63 = new Vec2f(0.37500F, 0.12500F);
    private static final Vec2f v64 = new Vec2f(0.46875F, 0.09375F);
    private static final Vec2f v66 = new Vec2f(0.46875F, 0.00000F);
    private static final Vec2f v52 = new Vec2f(0.09375F, 0.06250F);
    private static final Vec2f v89 = new Vec2f(0.62500F, 0.06250F);
    private static final Vec2f v97 = new Vec2f(0.75000F, 0.21875F);
    private static final Vec2f v130 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v129 = new Vec2f(0.12500F, 0.28125F);
    private static final Vec2f v101 = new Vec2f(0.84375F, 0.28125F);
    private static final Vec2f v48 = new Vec2f(0.00000F, 0.06250F);
    private static final Vec2f v61 = new Vec2f(0.18750F, 0.12500F);
    private static final Vec2f v80 = new Vec2f(0.84375F, 0.09375F);
    private static final Vec2f v59 = new Vec2f(0.28125F, 0.09375F);
    private static final Vec2f v74 = new Vec2f(0.90625F, 0.06207F);
    private static final Vec2f v75 = new Vec2f(0.81250F, 0.06178F);
    private static final Vec2f v98 = new Vec2f(0.78125F, 0.21875F);
    private static final Vec2f v78 = new Vec2f(0.75000F, 0.06178F);
    private static final Vec2f v49 = new Vec2f(0.06250F, 0.06250F);
    private static final Vec2f v91 = new Vec2f(0.56250F, 0.09447F);
    private static final Vec2f v88 = new Vec2f(0.71875F, 0.06279F);
    private static final Vec2f v87 = new Vec2f(0.71875F, 0.09447F);

    public static final List<SimpleQuad> hiltQuads = getHiltQuads();

    public static List<SimpleQuad> getHiltQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c, v51, v143, v2, c, v50, v143, v1, c, v49, v143, v0, c, v48, v143));
        builder.add(new SimpleQuad(v2, c, v50, v144, v5, c, v53, v144, v4, c, v52, v144, v1, c, v49, v144));
        builder.add(new SimpleQuad(v5, c, v53, v145, v7, c, v55, v145, v6, c, v54, v145, v4, c, v52, v145));
        builder.add(new SimpleQuad(v7, c, v55, v146, v3, c, v57, v146, v0, c, v56, v146, v6, c, v54, v146));
        builder.add(new SimpleQuad(v11, c, v61, v143, v10, c, v60, v143, v9, c, v59, v143, v8, c, v58, v143));
        builder.add(new SimpleQuad(v10, c, v60, v144, v13, c, v63, v144, v12, c, v62, v144, v9, c, v59, v144));
        builder.add(new SimpleQuad(v13, c, v63, v145, v15, c, v65, v145, v14, c, v64, v145, v12, c, v62, v145));
        builder.add(new SimpleQuad(v13, c, v64, v147, v10, c, v62, v147, v11, c, v67, v147, v15, c, v66, v147));
        builder.add(new SimpleQuad(v15, c, v65, v146, v11, c, v69, v146, v8, c, v68, v146, v14, c, v64, v146));
        builder.add(new SimpleQuad(v8, c, v70, v148, v9, c, v59, v148, v12, c, v62, v148, v14, c, v67, v148));
        return builder.build();
    }

    public static final List<SimpleQuad> hiltNoTintQuads = getHiltNoTintQuads();

    public static List<SimpleQuad> getHiltNoTintQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v7, c, v74, v146, v17, c, v73, v146, v16, c, v72, v146, v3, c, v71, v146));
        builder.add(new SimpleQuad(v3, c, v78, v143, v16, c, v77, v143, v18, c, v76, v143, v2, c, v75, v143));
        builder.add(new SimpleQuad(v2, c, v75, v144, v18, c, v76, v144, v19, c, v80, v144, v5, c, v79, v144));
        builder.add(new SimpleQuad(v5, c, v79, v145, v19, c, v80, v145, v17, c, v73, v145, v7, c, v74, v145));
        builder.add(new SimpleQuad(v19, c, v84, v147, v18, c, v83, v147, v16, c, v82, v147, v17, c, v81, v147));
        builder.add(new SimpleQuad(v24, c, v88, v146, v21, c, v87, v146, v20, c, v86, v146, v25, c, v85, v146));
        builder.add(new SimpleQuad(v25, c, v92, v143, v20, c, v91, v143, v22, c, v90, v143, v26, c, v89, v143));
        builder.add(new SimpleQuad(v26, c, v89, v144, v22, c, v90, v144, v23, c, v94, v144, v27, c, v93, v144));
        builder.add(new SimpleQuad(v27, c, v93, v145, v23, c, v94, v145, v21, c, v87, v145, v24, c, v88, v145));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOffQuads = getBladeOffQuads();

    public static List<SimpleQuad> getBladeOffQuads() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v28, c, v98, v148, v29, c, v97, v148, v30, c, v96, v148, v31, c, v95, v148));
        builder.add(new SimpleQuad(v29, c, v102, v146, v28, c, v101, v146, v24, c, v100, v146, v25, c, v99, v146));
        builder.add(new SimpleQuad(v30, c, v96, v143, v29, c, v105, v143, v25, c, v104, v143, v26, c, v103, v143));
        builder.add(new SimpleQuad(v31, c, v95, v144, v30, c, v96, v144, v26, c, v103, v144, v27, c, v106, v144));
        builder.add(new SimpleQuad(v28, c, v101, v145, v31, c, v95, v145, v27, c, v106, v145, v24, c, v100, v145));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeMidQuads = getBladeMidQuads();

    public static List<SimpleQuad> getBladeMidQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v31, c, v110, v146, v27, c, v109, v146, v26, c, v108, v146, v30, c, v107, v146));
        builder.add(new SimpleQuad(v28, c, v114, v143, v24, c, v113, v143, v27, c, v112, v143, v31, c, v111, v143));
        builder.add(new SimpleQuad(v29, c, v111, v144, v25, c, v112, v144, v24, c, v116, v144, v28, c, v115, v144));
        builder.add(new SimpleQuad(v30, c, v115, v145, v26, c, v116, v145, v25, c, v109, v145, v29, c, v110, v145));
        builder.add(new SimpleQuad(v28, c, v119, v147, v31, c, v118, v147, v30, c, v115, v147, v29, c, v117, v147));
        builder.add(new SimpleQuad(v25, c, v120, v148, v26, c, v111, v148, v27, c, v115, v148, v24, c, v117, v148));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeInQuads = getBladeInQuads();

    public static List<SimpleQuad> getBladeInQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c, v122, v146, v34, c, v113, v146, v33, c, v114, v146, v32, c, v121, v146));
        builder.add(new SimpleQuad(v34, c, v126, v143, v37, c, v125, v143, v36, c, v124, v143, v33, c, v123, v143));
        builder.add(new SimpleQuad(v37, c, v125, v144, v39, c, v128, v144, v38, c, v127, v144, v36, c, v124, v144));
        builder.add(new SimpleQuad(v39, c, v128, v145, v35, c, v122, v145, v32, c, v121, v145, v38, c, v127, v145));
        builder.add(new SimpleQuad(v32, c, v53, v148, v33, c, v50, v148, v36, c, v124, v148, v38, c, v127, v148));
        builder.add(new SimpleQuad(v35, c, v130, v147, v39, c, v129, v147, v37, c, v127, v147, v34, c, v53, v147));
        return builder.build();
    }

    public static final List<SimpleQuad> bladeOutQuads = getBladeOutQuads();

    public static List<SimpleQuad> getBladeOutQuads() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<SimpleQuad> builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v43, c, v133, v146, v42, c, v132, v146, v41, c, v105, v146, v40, c, v131, v146));
        builder.add(new SimpleQuad(v42, c, v136, v143, v45, c, v135, v143, v44, c, v134, v143, v41, c, v107, v143));
        builder.add(new SimpleQuad(v45, c, v135, v144, v47, c, v138, v144, v46, c, v137, v144, v44, c, v134, v144));
        builder.add(new SimpleQuad(v47, c, v138, v145, v43, c, v133, v145, v40, c, v131, v145, v46, c, v137, v145));
        builder.add(new SimpleQuad(v40, c, v140, v148, v41, c, v139, v148, v44, c, v134, v148, v46, c, v137, v148));
        builder.add(new SimpleQuad(v42, c, v140, v147, v43, c, v142, v147, v47, c, v141, v147, v45, c, v137, v147));
        return builder.build();
    }
}
