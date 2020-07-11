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
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.util.List;

public class LaserBladeModelType316 extends SimpleModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_316.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    private static final Quaternion fpRightHandRotation;
    private static final Quaternion fpLeftHandRotation;
    private static final Quaternion tpLeftHandRotation;
    private static final Quaternion guiRotation;
    static {
        fpRightHandRotation = new Quaternion(Vector3f.YP, 45.0F, true);
        fpRightHandRotation.multiply(new Quaternion(Vector3f.XP, 90.0F, true));
        fpLeftHandRotation = new Quaternion(Vector3f.ZP, 180.0F, true);

        fpLeftHandRotation.multiply(new Quaternion(Vector3f.YP, 45.0F, true));
        fpLeftHandRotation.multiply(new Quaternion(Vector3f.XP, -90.0F, true));

        tpLeftHandRotation = new Quaternion(Vector3f.ZP, 180.0F, true);

        guiRotation = new Quaternion(Vector3f.XP, -90.0F, true);
        guiRotation.multiply(new Quaternion(Vector3f.ZP, 180.0F, true));
    }

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        // Extra camera transforming
        if (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
            matrixStack.rotate(fpRightHandRotation);
            matrixStack.translate(0.0D, -0.3125D, 0.0D);
            matrixStack.scale(1.5F, 1.5F, 1.5F);

        } else if (transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
            matrixStack.translate(0.0D, 0.3125D, 0.0D);
            matrixStack.rotate(fpLeftHandRotation);
            matrixStack.translate(0.0D, -0.15625D, 0.3125D);
            matrixStack.scale(1.5F, 1.5F, 1.5F);

        } else if (transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            matrixStack.translate(0.0D, 0.3125D, 0.0D);
            matrixStack.rotate(tpLeftHandRotation);

        } else if (transformType == ItemCameraTransforms.TransformType.FIXED || transformType == ItemCameraTransforms.TransformType.GUI) {
            matrixStack.translate(0.15625D, 0.75D, -0.15625D);
            matrixStack.rotate(guiRotation);
        }

        // Render
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
        Vector3f v6 = new Vector3f(-0.156250F, 0.312500F, 0.093750F);
        Vector3f v12 = new Vector3f(0.343750F, 0.093750F, -0.031250F);
        Vector3f v4 = new Vector3f(-0.156250F, 0.312500F, 0.031250F);
        Vector3f v9 = new Vector3f(0.343750F, 0.218750F, -0.031250F);
        Vector3f v90 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v95 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v7 = new Vector3f(-0.156250F, 0.000000F, 0.093750F);
        Vector3f v33 = new Vector3f(0.187500F, -0.125000F, 0.500000F);
        Vector3f v11 = new Vector3f(0.156250F, 0.218750F, 0.156250F);
        Vector3f v13 = new Vector3f(0.343750F, 0.093750F, 0.156250F);
        Vector3f v94 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v1 = new Vector3f(0.156250F, 0.312500F, 0.031250F);
        Vector3f v93 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v35 = new Vector3f(0.187500F, 0.437500F, -0.625000F);
        Vector3f v14 = new Vector3f(0.156250F, 0.093750F, -0.031250F);
        Vector3f v20 = new Vector3f(0.281250F, -0.062500F, 0.437500F);
        Vector3f v38 = new Vector3f(0.312500F, 0.437500F, 0.500000F);
        Vector3f v24 = new Vector3f(0.203125F, 0.406250F, 0.468750F);
        Vector3f v25 = new Vector3f(0.203125F, -0.093750F, 0.468750F);
        Vector3f v16 = new Vector3f(0.218750F, 0.375000F, 0.437500F);
        Vector3f v8 = new Vector3f(0.156250F, 0.218750F, -0.031250F);
        Vector3f v31 = new Vector3f(0.296875F, 0.406250F, -0.593750F);
        Vector3f v26 = new Vector3f(0.203125F, -0.093750F, -0.593750F);
        Vector3f v22 = new Vector3f(0.281250F, 0.375000F, 0.437500F);
        Vector3f v39 = new Vector3f(0.312500F, 0.437500F, -0.625000F);
        Vector3f v29 = new Vector3f(0.296875F, -0.093750F, -0.593750F);
        Vector3f v21 = new Vector3f(0.281250F, -0.062500F, -0.562500F);
        Vector3f v37 = new Vector3f(0.312500F, -0.125000F, -0.625000F);
        Vector3f v36 = new Vector3f(0.312500F, -0.125000F, 0.500000F);
        Vector3f v92 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v34 = new Vector3f(0.187500F, -0.125000F, -0.625000F);
        Vector3f v17 = new Vector3f(0.218750F, -0.062500F, 0.437500F);
        Vector3f v32 = new Vector3f(0.187500F, 0.437500F, 0.500000F);
        Vector3f v15 = new Vector3f(0.156250F, 0.093750F, 0.156250F);
        Vector3f v0 = new Vector3f(0.156250F, 0.312500F, 0.093750F);
        Vector3f v3 = new Vector3f(0.156250F, 0.000000F, 0.093750F);
        Vector3f v2 = new Vector3f(0.156250F, 0.000000F, 0.031250F);
        Vector3f v5 = new Vector3f(-0.156250F, 0.000000F, 0.031250F);
        Vector3f v91 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v30 = new Vector3f(0.296875F, 0.406250F, 0.468750F);
        Vector3f v19 = new Vector3f(0.218750F, 0.375000F, -0.562500F);
        Vector3f v10 = new Vector3f(0.343750F, 0.218750F, 0.156250F);
        Vector3f v28 = new Vector3f(0.296875F, -0.093750F, 0.468750F);
        Vector3f v27 = new Vector3f(0.203125F, 0.406250F, -0.593750F);
        Vector3f v23 = new Vector3f(0.281250F, 0.375000F, -0.562500F);
        Vector3f v18 = new Vector3f(0.218750F, -0.062500F, -0.562500F);
        Vector2f v62 = new Vector2f(0.68750F, 0.09375F);
        Vector2f v59 = new Vector2f(0.46875F, 0.09375F);
        Vector2f v85 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v47 = new Vector2f(0.21875F, 0.18750F);
        Vector2f v65 = new Vector2f(0.59375F, 0.09375F);
        Vector2f v67 = new Vector2f(0.28125F, 0.21875F);
        Vector2f v54 = new Vector2f(0.62500F, 0.18750F);
        Vector2f v41 = new Vector2f(0.03125F, 0.03125F);
        Vector2f v63 = new Vector2f(0.53125F, 0.00000F);
        Vector2f v49 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v73 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v40 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v84 = new Vector2f(0.53125F, 0.71875F);
        Vector2f v89 = new Vector2f(0.96875F, 0.18750F);
        Vector2f v70 = new Vector2f(0.28125F, 0.71875F);
        Vector2f v51 = new Vector2f(0.37500F, 0.03125F);
        Vector2f v88 = new Vector2f(0.96875F, 0.21875F);
        Vector2f v87 = new Vector2f(0.75000F, 0.18750F);
        Vector2f v86 = new Vector2f(0.75000F, 0.71875F);
        Vector2f v53 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v52 = new Vector2f(0.37500F, 0.18750F);
        Vector2f v69 = new Vector2f(0.50000F, 0.71875F);
        Vector2f v57 = new Vector2f(0.62500F, 0.09375F);
        Vector2f v75 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v80 = new Vector2f(1.00000F, 0.21875F);
        Vector2f v44 = new Vector2f(0.18750F, 0.03125F);
        Vector2f v83 = new Vector2f(0.53125F, 0.21875F);
        Vector2f v68 = new Vector2f(0.50000F, 0.21875F);
        Vector2f v82 = new Vector2f(0.78125F, 0.71875F);
        Vector2f v79 = new Vector2f(0.78125F, 0.21875F);
        Vector2f v81 = new Vector2f(1.00000F, 0.71875F);
        Vector2f v78 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v76 = new Vector2f(0.25000F, 0.71875F);
        Vector2f v77 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v71 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v42 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v72 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v74 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v58 = new Vector2f(0.46875F, 0.18750F);
        Vector2f v60 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v48 = new Vector2f(0.34375F, 0.00000F);
        Vector2f v50 = new Vector2f(0.34375F, 0.03125F);
        Vector2f v46 = new Vector2f(0.21875F, 0.03125F);
        Vector2f v45 = new Vector2f(0.18750F, 0.18750F);
        Vector2f v64 = new Vector2f(0.46875F, 0.00000F);
        Vector2f v56 = new Vector2f(0.53125F, 0.09375F);
        Vector2f v43 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v66 = new Vector2f(0.59375F, 0.00000F);
        Vector2f v61 = new Vector2f(0.68750F, 0.18750F);
        Vector2f v55 = new Vector2f(0.53125F, 0.18750F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v43, v90, v2, c0, v42, v90, v1, c0, v41, v90, v0, c0, v40, v90));
        builder.add(new SimpleQuad(v2, c0, v42, v91, v5, c0, v45, v91, v4, c0, v44, v91, v1, c0, v41, v91));
        builder.add(new SimpleQuad(v5, c0, v45, v92, v7, c0, v47, v92, v6, c0, v46, v92, v4, c0, v44, v92));
        builder.add(new SimpleQuad(v5, c0, v50, v93, v2, c0, v44, v93, v3, c0, v49, v93, v7, c0, v48, v93));
        builder.add(new SimpleQuad(v7, c0, v47, v94, v3, c0, v52, v94, v0, c0, v51, v94, v6, c0, v46, v94));
        builder.add(new SimpleQuad(v1, c0, v41, v95, v4, c0, v44, v95, v6, c0, v49, v95, v0, c0, v53, v95));
        builder.add(new SimpleQuad(v11, c0, v57, v95, v10, c0, v56, v95, v9, c0, v55, v95, v8, c0, v54, v95));
        builder.add(new SimpleQuad(v10, c0, v56, v90, v13, c0, v59, v90, v12, c0, v58, v90, v9, c0, v55, v90));
        builder.add(new SimpleQuad(v13, c0, v59, v93, v15, c0, v60, v93, v14, c0, v52, v93, v12, c0, v58, v93));
        builder.add(new SimpleQuad(v15, c0, v62, v92, v11, c0, v57, v92, v8, c0, v54, v92, v14, c0, v61, v92));
        builder.add(new SimpleQuad(v15, c0, v64, v94, v13, c0, v59, v94, v10, c0, v56, v94, v11, c0, v63, v94));
        builder.add(new SimpleQuad(v8, c0, v66, v91, v9, c0, v65, v91, v12, c0, v56, v91, v14, c0, v63, v91));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v19, c1, v70, v92, v18, c1, v69, v92, v17, c1, v68, v92, v16, c1, v67, v92));
        builder.add(new SimpleQuad(v18, c1, v74, v93, v21, c1, v73, v93, v20, c1, v72, v93, v17, c1, v71, v93));
        builder.add(new SimpleQuad(v21, c1, v73, v90, v23, c1, v76, v90, v22, c1, v75, v90, v20, c1, v72, v90));
        builder.add(new SimpleQuad(v23, c1, v76, v95, v19, c1, v70, v95, v16, c1, v67, v95, v22, c1, v75, v95));
        builder.add(new SimpleQuad(v16, c1, v77, v94, v17, c1, v42, v94, v20, c1, v72, v94, v22, c1, v75, v94));
        builder.add(new SimpleQuad(v19, c1, v58, v91, v23, c1, v78, v91, v21, c1, v75, v91, v18, c1, v77, v91));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v27, c2, v82, v92, v26, c2, v81, v92, v25, c2, v80, v92, v24, c2, v79, v92));
        builder.add(new SimpleQuad(v26, c2, v69, v93, v29, c2, v84, v93, v28, c2, v83, v93, v25, c2, v68, v93));
        builder.add(new SimpleQuad(v29, c2, v84, v90, v31, c2, v86, v90, v30, c2, v85, v90, v28, c2, v83, v90));
        builder.add(new SimpleQuad(v31, c2, v86, v95, v27, c2, v82, v95, v24, c2, v79, v95, v30, c2, v85, v95));
        builder.add(new SimpleQuad(v24, c2, v87, v94, v25, c2, v55, v94, v28, c2, v83, v94, v30, c2, v85, v94));
        builder.add(new SimpleQuad(v27, c2, v89, v91, v31, c2, v88, v91, v29, c2, v85, v91, v26, c2, v87, v91));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v35, c3, v82, v92, v34, c3, v81, v92, v33, c3, v80, v92, v32, c3, v79, v92));
        builder.add(new SimpleQuad(v34, c3, v69, v93, v37, c3, v84, v93, v36, c3, v83, v93, v33, c3, v68, v93));
        builder.add(new SimpleQuad(v37, c3, v84, v90, v39, c3, v86, v90, v38, c3, v85, v90, v36, c3, v83, v90));
        builder.add(new SimpleQuad(v39, c3, v86, v95, v35, c3, v82, v95, v32, c3, v79, v95, v38, c3, v85, v95));
        builder.add(new SimpleQuad(v32, c3, v87, v94, v33, c3, v55, v94, v36, c3, v83, v94, v38, c3, v85, v94));
        builder.add(new SimpleQuad(v35, c3, v89, v91, v39, c3, v88, v91, v37, c3, v85, v91, v34, c3, v87, v91));
        BLADE_OUT_QUADS = builder.build();
    }
}
