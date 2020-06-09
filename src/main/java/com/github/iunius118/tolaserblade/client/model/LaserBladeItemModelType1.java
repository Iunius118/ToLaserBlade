package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
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

public class LaserBladeItemModelType1 extends SimpleItemModel {
    private final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_1.png");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderFaces(matrixStack, currentBuffer, hiltFaces, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = buffer.getBuffer(LaserBladeRenderType.LASER_FLAT);
        renderFaces(matrixStack, currentBuffer, bladeOutFaces, color.simpleOuterColor, fullLight, overlayColor);
        renderFaces(matrixStack, currentBuffer, bladeInFaces, color.simpleInnerColor, fullLight, overlayColor);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    private static final Vector3f v31 = new Vector3f(-0.062400F, 0.375000F, -0.062600F);
    private static final Vector3f v18 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v0 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v17 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v92 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v29 = new Vector3f(0.062600F, 0.375000F, 0.062400F);
    private static final Vector3f v93 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v13 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v4 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v26 = new Vector3f(0.062500F, 1.437500F, 0.062500F);
    private static final Vector3f v33 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v3 = new Vector3f(0.062550F, 0.375000F, -0.062550F);
    private static final Vector3f v8 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v14 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v5 = new Vector3f(-0.062450F, 0.375000F, -0.062550F);
    private static final Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v25 = new Vector3f(-0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v20 = new Vector3f(0.062500F, 1.437500F, -0.062500F);
    private static final Vector3f v21 = new Vector3f(-0.062500F, 1.437500F, -0.062500F);
    private static final Vector3f v11 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v91 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v7 = new Vector3f(-0.062450F, 0.375000F, 0.062450F);
    private static final Vector3f v22 = new Vector3f(-0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v23 = new Vector3f(0.062500F, 0.375000F, -0.062500F);
    private static final Vector3f v95 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v96 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v94 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v37 = new Vector3f(0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v38 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v39 = new Vector3f(-0.031250F, 0.375000F, -0.031250F);
    private static final Vector3f v36 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v34 = new Vector3f(0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v32 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
    private static final Vector3f v35 = new Vector3f(-0.031250F, 0.375000F, 0.031250F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v2 = new Vector3f(0.062550F, 0.375000F, 0.062450F);
    private static final Vector3f v28 = new Vector3f(-0.062400F, 0.375000F, 0.062400F);
    private static final Vector3f v27 = new Vector3f(0.062500F, 0.375000F, 0.062500F);
    private static final Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v24 = new Vector3f(-0.062500F, 1.437500F, 0.062500F);
    private static final Vector3f v9 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v16 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v30 = new Vector3f(0.062600F, 0.375000F, -0.062600F);
    private static final Vec2f v56 = new Vec2f(0.40625F, 0.00000F);
    private static final Vec2f v44 = new Vec2f(0.31250F, 0.09375F);
    private static final Vec2f v48 = new Vec2f(0.40625F, 0.09375F);
    private static final Vec2f v43 = new Vec2f(0.234375F, 0.078125F);
    private static final Vec2f v47 = new Vec2f(0.296875F, 0.015625F);
    private static final Vec2f v75 = new Vec2f(0.12500F, 0.75000F);
    private static final Vec2f v77 = new Vec2f(0.25000F, 0.21875F);
    private static final Vec2f v81 = new Vec2f(0.18750F, 0.15625F);
    private static final Vec2f v55 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f v74 = new Vec2f(0.18750F, 0.75000F);
    private static final Vec2f v52 = new Vec2f(0.12500F, 0.09375F);
    private static final Vec2f v65 = new Vec2f(0.06250F, 0.03125F);
    private static final Vec2f v61 = new Vec2f(0.00000F, 0.03125F);
    private static final Vec2f v54 = new Vec2f(0.12500F, 0.12500F);
    private static final Vec2f v42 = new Vec2f(0.234375F, 0.015625F);
    private static final Vec2f v78 = new Vec2f(0.25000F, 0.75000F);
    private static final Vec2f v46 = new Vec2f(0.31250F, 0.00000F);
    private static final Vec2f v86 = new Vec2f(0.03125F, 0.21875F);
    private static final Vec2f v90 = new Vec2f(0.06250F, 0.71875F);
    private static final Vec2f v60 = new Vec2f(0.09375F, 0.18750F);
    private static final Vec2f v50 = new Vec2f(0.50000F, 0.12500F);
    private static final Vec2f v89 = new Vec2f(0.06250F, 0.21875F);
    private static final Vec2f v88 = new Vec2f(0.00000F, 0.71875F);
    private static final Vec2f v40 = new Vec2f(0.21875F, 0.09375F);
    private static final Vec2f v87 = new Vec2f(0.03125F, 0.71875F);
    private static final Vec2f v85 = new Vec2f(0.00000F, 0.21875F);
    private static final Vec2f v84 = new Vec2f(0.09375F, 0.71875F);
    private static final Vec2f v80 = new Vec2f(0.31250F, 0.15625F);
    private static final Vec2f v49 = new Vec2f(0.50000F, 0.09375F);
    private static final Vec2f v83 = new Vec2f(0.12500F, 0.71875F);
    private static final Vec2f v82 = new Vec2f(0.09375F, 0.21875F);
    private static final Vec2f v73 = new Vec2f(0.18750F, 0.21875F);
    private static final Vec2f v58 = new Vec2f(0.12500F, 0.03125F);
    private static final Vec2f v59 = new Vec2f(0.12500F, 0.18750F);
    private static final Vec2f v63 = new Vec2f(0.03125F, 0.18750F);
    private static final Vec2f v69 = new Vec2f(0.37500F, 0.21875F);
    private static final Vec2f v72 = new Vec2f(0.31250F, 0.21875F);
    private static final Vec2f v76 = new Vec2f(0.12500F, 0.21875F);
    private static final Vec2f v41 = new Vec2f(0.21875F, 0.00000F);
    private static final Vec2f v53 = new Vec2f(0.21875F, 0.12500F);
    private static final Vec2f v71 = new Vec2f(0.31250F, 0.75000F);
    private static final Vec2f v70 = new Vec2f(0.37500F, 0.75000F);
    private static final Vec2f v68 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v67 = new Vec2f(0.09375F, 0.00000F);
    private static final Vec2f v66 = new Vec2f(0.06250F, 0.18750F);
    private static final Vec2f v57 = new Vec2f(0.09375F, 0.03125F);
    private static final Vec2f v51 = new Vec2f(0.40625F, 0.12500F);
    private static final Vec2f v64 = new Vec2f(0.00000F, 0.18750F);
    private static final Vec2f v79 = new Vec2f(0.25000F, 0.15625F);
    private static final Vec2f v62 = new Vec2f(0.03125F, 0.03125F);
    private static final Vec2f v45 = new Vec2f(0.296875F, 0.078125F);

    public static final List<FaceData> hiltFaces = getHiltFaces();

    public static List<FaceData> getHiltFaces() {
        Vector4f c = new Vector4f(0.8F, 0.8F, 0.8F, 1F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v3, c, v43, v91, v2, c, v42, v91, v1, c, v41, v91, v0, c, v40, v91));
        builder.add(new FaceData(v5, c, v45, v91, v3, c, v43, v91, v0, c, v40, v91, v4, c, v44, v91));
        builder.add(new FaceData(v2, c, v42, v91, v7, c, v47, v91, v6, c, v46, v91, v1, c, v41, v91));
        builder.add(new FaceData(v6, c, v46, v91, v7, c, v47, v91, v5, c, v45, v91, v4, c, v44, v91));
        builder.add(new FaceData(v9, c, v51, v92, v8, c, v50, v92, v1, c, v49, v92, v6, c, v48, v92));
        builder.add(new FaceData(v8, c, v54, v93, v10, c, v53, v93, v0, c, v40, v93, v1, c, v52, v93));
        builder.add(new FaceData(v10, c, v53, v94, v11, c, v55, v94, v4, c, v44, v94, v0, c, v40, v94));
        builder.add(new FaceData(v11, c, v55, v95, v9, c, v51, v95, v6, c, v48, v95, v4, c, v44, v95));
        builder.add(new FaceData(v11, c, v48, v96, v10, c, v44, v96, v8, c, v46, v96, v9, c, v56, v96));
        builder.add(new FaceData(v15, c, v60, v92, v14, c, v59, v92, v13, c, v58, v92, v12, c, v57, v92));
        builder.add(new FaceData(v14, c, v64, v93, v17, c, v63, v93, v16, c, v62, v93, v13, c, v61, v93));
        builder.add(new FaceData(v17, c, v63, v94, v19, c, v66, v94, v18, c, v65, v94, v16, c, v62, v94));
        builder.add(new FaceData(v19, c, v66, v95, v15, c, v60, v95, v12, c, v57, v95, v18, c, v65, v95));
        builder.add(new FaceData(v19, c, v57, v96, v17, c, v65, v96, v14, c, v68, v96, v15, c, v67, v96));
        return builder.build();
    }

    public static final List<FaceData> bladeOutFaces = getBladeOutFaces();

    public static List<FaceData> getBladeOutFaces() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v21, c, v72, v92, v22, c, v71, v92, v23, c, v70, v92, v20, c, v69, v92));
        builder.add(new FaceData(v24, c, v76, v93, v25, c, v75, v93, v22, c, v74, v93, v21, c, v73, v93));
        builder.add(new FaceData(v26, c, v73, v94, v27, c, v74, v94, v25, c, v78, v94, v24, c, v77, v94));
        builder.add(new FaceData(v20, c, v77, v95, v23, c, v78, v95, v27, c, v71, v95, v26, c, v72, v95));
        builder.add(new FaceData(v24, c, v80, v96, v21, c, v72, v96, v20, c, v77, v96, v26, c, v79, v96));
        builder.add(new FaceData(v29, c, v81, v91, v30, c, v73, v91, v31, c, v77, v91, v28, c, v79, v91));
        return builder.build();
    }

    public static final List<FaceData> bladeInFaces = getBladeInFaces();

    public static List<FaceData> getBladeInFaces() {
        Vector4f c = new Vector4f(1F, 1F, 1F, 1F);

        ImmutableList.Builder<FaceData> builder = ImmutableList.builder();
        builder.add(new FaceData(v35, c, v84, v92, v34, c, v83, v92, v33, c, v76, v92, v32, c, v82, v92));
        builder.add(new FaceData(v34, c, v88, v93, v37, c, v87, v93, v36, c, v86, v93, v33, c, v85, v93));
        builder.add(new FaceData(v37, c, v87, v94, v39, c, v90, v94, v38, c, v89, v94, v36, c, v86, v94));
        builder.add(new FaceData(v39, c, v90, v95, v35, c, v84, v95, v32, c, v82, v95, v38, c, v89, v95));
        builder.add(new FaceData(v32, c, v66, v91, v33, c, v63, v91, v36, c, v86, v91, v38, c, v89, v91));
        builder.add(new FaceData(v35, c, v60, v96, v39, c, v82, v96, v37, c, v89, v96, v34, c, v66, v96));
        return builder.build();
    }
}
