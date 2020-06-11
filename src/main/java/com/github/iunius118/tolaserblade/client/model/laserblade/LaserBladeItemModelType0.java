package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.SimpleItemModel;
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

public class LaserBladeItemModelType0 extends SimpleItemModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_0.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderFaces(matrixStack, currentBuffer, hiltFaces, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderFaces(matrixStack, currentBuffer, bladeInFaces, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderFaces(matrixStack, currentBuffer, bladeMidFaces, color.outerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderFaces(matrixStack, currentBuffer, bladeOutFaces, color.outerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v23 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v18 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v13 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v20 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v31 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v9 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v101 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v17 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v99 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v11 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v22 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v27 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v10 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v14 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v24 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
    private static final Vector3f v100 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v98 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v97 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v96 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v35 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
    private static final Vector3f v21 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v33 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
    private static final Vector3f v25 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
    private static final Vector3f v34 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
    private static final Vector3f v30 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
    private static final Vector3f v29 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v5 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v28 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
    private static final Vector3f v4 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v0 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v26 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v16 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v7 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v2 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v8 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v32 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
    private static final Vector3f v3 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vec2f v89 = new Vec2f(0.46875F, 0.78125F);
    private static final Vec2f v55 = new Vec2f(0.09375F, 0.18750F);
    private static final Vec2f v93 = new Vec2f(0.46875F, 0.12500F);
    private static final Vec2f v94 = new Vec2f(0.56250F, 0.12500F);
    private static final Vec2f v58 = new Vec2f(0.21875F, 0.00000F);
    private static final Vec2f v95 = new Vec2f(0.65625F, 0.12500F);
    private static final Vec2f v77 = new Vec2f(0.18750F, 0.75000F);
    private static final Vec2f v50 = new Vec2f(0.03125F, 0.18750F);
    private static final Vec2f v72 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v59 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v91 = new Vec2f(0.56250F, 0.21875F);
    private static final Vec2f v40 = new Vec2f(0.12500F, 0.09375F);
    private static final Vec2f v44 = new Vec2f(0.31250F, 0.09375F);
    private static final Vec2f v42 = new Vec2f(0.21875F, 0.12500F);
    private static final Vec2f v90 = new Vec2f(0.37500F, 0.78125F);
    private static final Vec2f v69 = new Vec2f(0.00000F, 0.71875F);
    private static final Vec2f v36 = new Vec2f(0.40625F, 0.09375F);
    private static final Vec2f v45 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f v61 = new Vec2f(0.03125F, 0.00000F);
    private static final Vec2f v82 = new Vec2f(0.25000F, 0.15625F);
    private static final Vec2f v78 = new Vec2f(0.12500F, 0.75000F);
    private static final Vec2f v86 = new Vec2f(0.75000F, 0.78125F);
    private static final Vec2f v62 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v46 = new Vec2f(0.40625F, 0.00000F);
    private static final Vec2f v41 = new Vec2f(0.21875F, 0.09375F);
    private static final Vec2f v71 = new Vec2f(0.06250F, 0.71875F);
    private static final Vec2f v84 = new Vec2f(0.65625F, 0.21875F);
    private static final Vec2f v83 = new Vec2f(0.31250F, 0.15625F);
    private static final Vec2f v81 = new Vec2f(0.18750F, 0.15625F);
    private static final Vec2f v80 = new Vec2f(0.25000F, 0.75000F);
    private static final Vec2f v39 = new Vec2f(0.40625F, 0.12500F);
    private static final Vec2f v76 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v53 = new Vec2f(0.06250F, 0.18750F);
    private static final Vec2f v51 = new Vec2f(0.00000F, 0.18750F);
    private static final Vec2f v79 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v66 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v92 = new Vec2f(0.56250F, 0.78125F);
    private static final Vec2f v75 = new Vec2f(0.31250F, 0.75000F);
    private static final Vec2f v47 = new Vec2f(0.31250F, 0.00000F);
    private static final Vec2f v70 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v43 = new Vec2f(0.12500F, 0.12500F);
    private static final Vec2f v68 = new Vec2f(0.03125F, 0.71875F);
    private static final Vec2f v67 = new Vec2f(0.03125F, 0.21875F);
    private static final Vec2f v85 = new Vec2f(0.75000F, 0.21875F);
    private static final Vec2f v73 = new Vec2f(0.37500F, 0.21875F);
    private static final Vec2f v48 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v74 = new Vec2f(0.37500F, 0.75000F);
    private static final Vec2f v38 = new Vec2f(0.50000F, 0.12500F);
    private static final Vec2f v65 = new Vec2f(0.09375F, 0.71875F);
    private static final Vec2f v88 = new Vec2f(0.46875F, 0.21875F);
    private static final Vec2f v87 = new Vec2f(0.65625F, 0.78125F);
    private static final Vec2f v56 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v63 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v52 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v64 = new Vec2f(0.12500F, 0.71875F);
    private static final Vec2f v54 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v57 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v49 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v60 = new Vec2f(0.12500F, 0.18750F);
    private static final Vec2f v37 = new Vec2f(0.50000F, 0.09375F);

    public static final List<FaceData> hiltFaces = getHiltFaces();

    public static List<FaceData> getHiltFaces() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v3, c, v39, v96, v2, c, v38, v96, v1, c, v37, v96, v0, c, v36, v96));
        builder.add(new FaceData(v2, c, v43, v97, v5, c, v42, v97, v4, c, v41, v97, v1, c, v40, v97));
        builder.add(new FaceData(v5, c, v42, v98, v7, c, v45, v98, v6, c, v44, v98, v4, c, v41, v98));
        builder.add(new FaceData(v7, c, v45, v99, v3, c, v39, v99, v0, c, v36, v99, v6, c, v44, v99));
        builder.add(new FaceData(v7, c, v36, v100, v5, c, v44, v100, v2, c, v47, v100, v3, c, v46, v100));
        builder.add(new FaceData(v11, c, v51, v97, v10, c, v50, v97, v9, c, v49, v97, v8, c, v48, v97));
        builder.add(new FaceData(v10, c, v50, v98, v13, c, v53, v98, v12, c, v52, v98, v9, c, v49, v98));
        builder.add(new FaceData(v13, c, v53, v99, v15, c, v55, v99, v14, c, v54, v99, v12, c, v52, v99));
        builder.add(new FaceData(v13, c, v54, v100, v10, c, v52, v100, v11, c, v57, v100, v15, c, v56, v100));
        builder.add(new FaceData(v0, c, v47, v101, v1, c, v58, v101, v4, c, v41, v101, v6, c, v44, v101));
        builder.add(new FaceData(v15, c, v55, v96, v11, c, v60, v96, v8, c, v59, v96, v14, c, v54, v96));
        builder.add(new FaceData(v9, c, v49, v101, v12, c, v52, v101, v14, c, v57, v101, v8, c, v61, v101));
        return builder.build();
    }

    public static final List<FaceData> bladeInFaces = getBladeInFaces();

    public static List<FaceData> getBladeInFaces() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.9F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v19, c, v65, v96, v18, c, v64, v96, v17, c, v63, v96, v16, c, v62, v96));
        builder.add(new FaceData(v18, c, v69, v97, v21, c, v68, v97, v20, c, v67, v97, v17, c, v66, v97));
        builder.add(new FaceData(v21, c, v68, v98, v23, c, v71, v98, v22, c, v70, v98, v20, c, v67, v98));
        builder.add(new FaceData(v23, c, v71, v99, v19, c, v65, v99, v16, c, v62, v99, v22, c, v70, v99));
        builder.add(new FaceData(v16, c, v53, v101, v17, c, v50, v101, v20, c, v67, v101, v22, c, v70, v101));
        builder.add(new FaceData(v19, c, v55, v100, v23, c, v62, v100, v21, c, v70, v100, v18, c, v53, v100));
        return builder.build();
    }

    public static final List<FaceData> bladeMidFaces = getBladeMidFaces();

    public static List<FaceData> getBladeMidFaces() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.5F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v27, c, v75, v96, v26, c, v74, v96, v25, c, v73, v96, v24, c, v72, v96));
        builder.add(new FaceData(v26, c, v78, v97, v29, c, v77, v97, v28, c, v76, v97, v25, c, v63, v97));
        builder.add(new FaceData(v29, c, v77, v98, v31, c, v80, v98, v30, c, v79, v98, v28, c, v76, v98));
        builder.add(new FaceData(v31, c, v80, v99, v27, c, v75, v99, v24, c, v72, v99, v30, c, v79, v99));
        builder.add(new FaceData(v24, c, v82, v101, v25, c, v81, v101, v28, c, v76, v101, v30, c, v79, v101));
        builder.add(new FaceData(v26, c, v82, v100, v27, c, v83, v100, v31, c, v72, v100, v29, c, v79, v100));
        return builder.build();
    }

    public static final List<FaceData> bladeOutFaces = getBladeOutFaces();

    public static List<FaceData> getBladeOutFaces() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 0.25F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v0, c, v87, v96, v1, c, v86, v96, v33, c, v85, v96, v32, c, v84, v96));
        builder.add(new FaceData(v1, c, v90, v97, v4, c, v89, v97, v34, c, v88, v97, v33, c, v73, v97));
        builder.add(new FaceData(v4, c, v89, v98, v6, c, v92, v98, v35, c, v91, v98, v34, c, v88, v98));
        builder.add(new FaceData(v6, c, v92, v99, v0, c, v87, v99, v32, c, v84, v99, v35, c, v91, v99));
        builder.add(new FaceData(v32, c, v94, v101, v33, c, v93, v101, v34, c, v88, v101, v35, c, v91, v101));
        builder.add(new FaceData(v6, c, v84, v100, v4, c, v91, v100, v1, c, v94, v100, v0, c, v95, v100));
        return builder.build();
    }
}
