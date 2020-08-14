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
    public static final List<SimpleQuad> BLADE_OUT_QUADS;
    public static final List<SimpleQuad> SHIELD_GRIP_QUADS;
    public static final List<SimpleQuad> SHIELD_IN_QUADS;
    public static final List<SimpleQuad> SHIELD_MID_QUADS;
    public static final List<SimpleQuad> SHIELD_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        if (itemStack.getItem().isBurnable()) { // TODO: isBurnable = isNotBurnable?
            renderSword(itemStack, transformType, matrixStack, buffer, lightmapCoord, overlayColor);
        } else {
            renderShield(itemStack, transformType, matrixStack, buffer, lightmapCoord, overlayColor);
        }
    }

    private void renderSword(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, HILT_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB_INNER) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

    private void renderShield(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightmapCoord, int overlayColor) {
        transformShield(transformType, matrixStack);    // Extra camera transforming

        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;

        IVertexBuilder currentBuffer = buffer.getBuffer(LaserBladeRenderType.HILT);
        renderQuads(matrixStack, currentBuffer, SHIELD_GRIP_QUADS, color.gripColor, lightmapCoord, overlayColor);

        if (color.isBroken) {
            return;
        }

        currentBuffer = color.isInnerSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB_INNER) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, SHIELD_IN_QUADS, color.innerColor, fullLight, overlayColor);

        currentBuffer = color.isOuterSubColor ? buffer.getBuffer(LaserBladeRenderType.LASER_SUB) : buffer.getBuffer(LaserBladeRenderType.LASER_ADD);
        renderQuads(matrixStack, currentBuffer, SHIELD_MID_QUADS, color.outerColor, fullLight, overlayColor);
        renderQuads(matrixStack, currentBuffer, SHIELD_OUT_QUADS, color.outerColor, fullLight, overlayColor);
    }

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

    private void transformShield(ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack) {
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
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector3f v6 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
        Vector3f v23 = new Vector3f(0.156250F, 0.000000F, 0.093750F);
        Vector3f v149 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v53 = new Vector3f(0.187500F, -0.125000F, 0.500000F);
        Vector3f v58 = new Vector3f(0.312500F, 0.437500F, 0.500000F);
        Vector3f v49 = new Vector3f(0.296875F, -0.093750F, -0.593750F);
        Vector3f v7 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
        Vector3f v38 = new Vector3f(0.218750F, -0.062500F, -0.562500F);
        Vector3f v17 = new Vector3f(0.062500F, 0.406250F, -0.062500F);
        Vector3f v2 = new Vector3f(0.031250F, 0.437500F, -0.031250F);
        Vector3f v31 = new Vector3f(0.156250F, 0.218750F, 0.156250F);
        Vector3f v13 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v48 = new Vector3f(0.296875F, -0.093750F, 0.468750F);
        Vector3f v4 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
        Vector3f v40 = new Vector3f(0.281250F, -0.062500F, 0.437500F);
        Vector3f v47 = new Vector3f(0.203125F, 0.406250F, -0.593750F);
        Vector3f v33 = new Vector3f(0.343750F, 0.093750F, 0.156250F);
        Vector3f v27 = new Vector3f(-0.156250F, 0.000000F, 0.093750F);
        Vector3f v20 = new Vector3f(0.156250F, 0.312500F, 0.093750F);
        Vector3f v30 = new Vector3f(0.343750F, 0.218750F, 0.156250F);
        Vector3f v146 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v148 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v44 = new Vector3f(0.203125F, 0.406250F, 0.468750F);
        Vector3f v37 = new Vector3f(0.218750F, -0.062500F, 0.437500F);
        Vector3f v9 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v1 = new Vector3f(-0.031250F, 0.437500F, -0.031250F);
        Vector3f v19 = new Vector3f(0.062500F, 0.406250F, 0.062500F);
        Vector3f v14 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v28 = new Vector3f(0.156250F, 0.218750F, -0.031250F);
        Vector3f v52 = new Vector3f(0.187500F, 0.437500F, 0.500000F);
        Vector3f v11 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v43 = new Vector3f(0.281250F, 0.375000F, -0.562500F);
        Vector3f v150 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v15 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v36 = new Vector3f(0.218750F, 0.375000F, 0.437500F);
        Vector3f v25 = new Vector3f(-0.156250F, 0.000000F, 0.031250F);
        Vector3f v55 = new Vector3f(0.187500F, 0.437500F, -0.625000F);
        Vector3f v18 = new Vector3f(-0.062500F, 0.406250F, 0.062500F);
        Vector3f v147 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v10 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v29 = new Vector3f(0.343750F, 0.218750F, -0.031250F);
        Vector3f v42 = new Vector3f(0.281250F, 0.375000F, 0.437500F);
        Vector3f v46 = new Vector3f(0.203125F, -0.093750F, -0.593750F);
        Vector3f v26 = new Vector3f(-0.156250F, 0.312500F, 0.093750F);
        Vector3f v34 = new Vector3f(0.156250F, 0.093750F, -0.031250F);
        Vector3f v35 = new Vector3f(0.156250F, 0.093750F, 0.156250F);
        Vector3f v56 = new Vector3f(0.312500F, -0.125000F, 0.500000F);
        Vector3f v45 = new Vector3f(0.203125F, -0.093750F, 0.468750F);
        Vector3f v24 = new Vector3f(-0.156250F, 0.312500F, 0.031250F);
        Vector3f v51 = new Vector3f(0.296875F, 0.406250F, -0.593750F);
        Vector3f v50 = new Vector3f(0.296875F, 0.406250F, 0.468750F);
        Vector3f v32 = new Vector3f(0.343750F, 0.093750F, -0.031250F);
        Vector3f v39 = new Vector3f(0.218750F, 0.375000F, -0.562500F);
        Vector3f v41 = new Vector3f(0.281250F, -0.062500F, -0.562500F);
        Vector3f v57 = new Vector3f(0.312500F, -0.125000F, -0.625000F);
        Vector3f v151 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v59 = new Vector3f(0.312500F, 0.437500F, -0.625000F);
        Vector3f v54 = new Vector3f(0.187500F, -0.125000F, -0.625000F);
        Vector3f v8 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v5 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
        Vector3f v0 = new Vector3f(-0.031250F, 0.437500F, 0.031250F);
        Vector3f v3 = new Vector3f(0.031250F, 0.437500F, 0.031250F);
        Vector3f v16 = new Vector3f(-0.062500F, 0.406250F, -0.062500F);
        Vector3f v12 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v21 = new Vector3f(0.156250F, 0.312500F, 0.031250F);
        Vector3f v22 = new Vector3f(0.156250F, 0.000000F, 0.031250F);
        Vector2f v120 = new Vector2f(0.68750F, 0.09375F);
        Vector2f v67 = new Vector2f(0.28125F, 0.78125F);
        Vector2f v110 = new Vector2f(0.37500F, 0.18750F);
        Vector2f v83 = new Vector2f(0.96875F, 0.81250F);
        Vector2f v139 = new Vector2f(0.53125F, 0.21875F);
        Vector2f v84 = new Vector2f(1.00000F, 0.81250F);
        Vector2f v134 = new Vector2f(0.46875F, 0.21875F);
        Vector2f v71 = new Vector2f(0.50000F, 0.75000F);
        Vector2f v95 = new Vector2f(0.96875F, 0.87500F);
        Vector2f v78 = new Vector2f(0.50000F, 0.81250F);
        Vector2f v74 = new Vector2f(0.00000F, 0.81250F);
        Vector2f v131 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v114 = new Vector2f(0.53125F, 0.09375F);
        Vector2f v79 = new Vector2f(0.50000F, 0.84375F);
        Vector2f v137 = new Vector2f(1.00000F, 0.71875F);
        Vector2f v85 = new Vector2f(1.00000F, 0.84375F);
        Vector2f v80 = new Vector2f(0.53125F, 0.81250F);
        Vector2f v73 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v77 = new Vector2f(0.00000F, 0.84375F);
        Vector2f v145 = new Vector2f(0.96875F, 0.18750F);
        Vector2f v135 = new Vector2f(0.78125F, 0.21875F);
        Vector2f v112 = new Vector2f(0.62500F, 0.18750F);
        Vector2f v101 = new Vector2f(0.00000F, 0.18750F);
        Vector2f v142 = new Vector2f(0.75000F, 0.71875F);
        Vector2f v64 = new Vector2f(0.25000F, 0.75000F);
        Vector2f v129 = new Vector2f(0.00000F, 0.21875F);
        Vector2f v66 = new Vector2f(0.28125F, 0.75000F);
        Vector2f v92 = new Vector2f(0.53125F, 0.87500F);
        Vector2f v102 = new Vector2f(0.18750F, 0.03125F);
        Vector2f v88 = new Vector2f(0.03125F, 0.90625F);
        Vector2f v141 = new Vector2f(0.75000F, 0.21875F);
        Vector2f v93 = new Vector2f(0.53125F, 0.90625F);
        Vector2f v81 = new Vector2f(0.53125F, 0.84375F);
        Vector2f v118 = new Vector2f(0.37500F, 0.09375F);
        Vector2f v94 = new Vector2f(0.96875F, 0.84375F);
        Vector2f v144 = new Vector2f(0.96875F, 0.21875F);
        Vector2f v76 = new Vector2f(0.03125F, 0.84375F);
        Vector2f v98 = new Vector2f(0.00000F, 0.03125F);
        Vector2f v119 = new Vector2f(0.68750F, 0.18750F);
        Vector2f v104 = new Vector2f(0.21875F, 0.03125F);
        Vector2f v122 = new Vector2f(0.46875F, 0.00000F);
        Vector2f v100 = new Vector2f(0.03125F, 0.18750F);
        Vector2f v63 = new Vector2f(0.00000F, 0.78125F);
        Vector2f v124 = new Vector2f(0.59375F, 0.00000F);
        Vector2f v143 = new Vector2f(0.75000F, 0.18750F);
        Vector2f v115 = new Vector2f(0.62500F, 0.09375F);
        Vector2f v91 = new Vector2f(0.50000F, 0.90625F);
        Vector2f v61 = new Vector2f(0.03125F, 0.75000F);
        Vector2f v138 = new Vector2f(0.78125F, 0.71875F);
        Vector2f v116 = new Vector2f(0.46875F, 0.18750F);
        Vector2f v72 = new Vector2f(0.50000F, 0.78125F);
        Vector2f v60 = new Vector2f(0.00000F, 0.75000F);
        Vector2f v90 = new Vector2f(0.50000F, 0.87500F);
        Vector2f v136 = new Vector2f(1.00000F, 0.21875F);
        Vector2f v70 = new Vector2f(0.46875F, 0.75000F);
        Vector2f v133 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v96 = new Vector2f(1.00000F, 0.87500F);
        Vector2f v68 = new Vector2f(0.46875F, 0.71875F);
        Vector2f v69 = new Vector2f(0.25000F, 0.71875F);
        Vector2f v65 = new Vector2f(0.25000F, 0.78125F);
        Vector2f v97 = new Vector2f(1.00000F, 0.90625F);
        Vector2f v111 = new Vector2f(0.03125F, 0.00000F);
        Vector2f v109 = new Vector2f(0.37500F, 0.03125F);
        Vector2f v89 = new Vector2f(0.00000F, 0.90625F);
        Vector2f v128 = new Vector2f(0.28125F, 0.71875F);
        Vector2f v130 = new Vector2f(0.03125F, 0.21875F);
        Vector2f v87 = new Vector2f(0.03125F, 0.87500F);
        Vector2f v127 = new Vector2f(0.50000F, 0.71875F);
        Vector2f v126 = new Vector2f(0.50000F, 0.21875F);
        Vector2f v125 = new Vector2f(0.28125F, 0.21875F);
        Vector2f v103 = new Vector2f(0.18750F, 0.18750F);
        Vector2f v62 = new Vector2f(0.03125F, 0.78125F);
        Vector2f v82 = new Vector2f(0.96875F, 0.78125F);
        Vector2f v121 = new Vector2f(0.53125F, 0.00000F);
        Vector2f v117 = new Vector2f(0.46875F, 0.09375F);
        Vector2f v140 = new Vector2f(0.53125F, 0.71875F);
        Vector2f v108 = new Vector2f(0.34375F, 0.03125F);
        Vector2f v86 = new Vector2f(0.00000F, 0.87500F);
        Vector2f v113 = new Vector2f(0.53125F, 0.18750F);
        Vector2f v132 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v75 = new Vector2f(0.03125F, 0.81250F);
        Vector2f v107 = new Vector2f(0.18750F, 0.00000F);
        Vector2f v106 = new Vector2f(0.34375F, 0.00000F);
        Vector2f v105 = new Vector2f(0.21875F, 0.18750F);
        Vector2f v123 = new Vector2f(0.59375F, 0.09375F);
        Vector2f v99 = new Vector2f(0.03125F, 0.03125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v63, v146, v2, c0, v62, v146, v1, c0, v61, v146, v0, c0, v60, v146));
        builder.add(new SimpleQuad(v2, c0, v62, v147, v5, c0, v65, v147, v4, c0, v64, v147, v1, c0, v61, v147));
        builder.add(new SimpleQuad(v5, c0, v65, v148, v7, c0, v67, v148, v6, c0, v66, v148, v4, c0, v64, v148));
        builder.add(new SimpleQuad(v5, c0, v70, v149, v2, c0, v64, v149, v3, c0, v69, v149, v7, c0, v68, v149));
        builder.add(new SimpleQuad(v7, c0, v67, v150, v3, c0, v72, v150, v0, c0, v71, v150, v6, c0, v66, v150));
        builder.add(new SimpleQuad(v1, c0, v61, v151, v4, c0, v64, v151, v6, c0, v69, v151, v0, c0, v73, v151));
        HILT_QUADS = builder.build();

        // BladeIn
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.9F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v11, c1, v77, v146, v10, c1, v76, v146, v9, c1, v75, v146, v8, c1, v74, v146));
        builder.add(new SimpleQuad(v10, c1, v76, v147, v2, c1, v79, v147, v1, c1, v78, v147, v9, c1, v75, v147));
        builder.add(new SimpleQuad(v2, c1, v79, v148, v3, c1, v81, v148, v0, c1, v80, v148, v1, c1, v78, v148));
        builder.add(new SimpleQuad(v2, c1, v83, v149, v10, c1, v78, v149, v11, c1, v72, v149, v3, c1, v82, v149));
        builder.add(new SimpleQuad(v3, c1, v81, v150, v11, c1, v85, v150, v8, c1, v84, v150, v0, c1, v80, v150));
        builder.add(new SimpleQuad(v9, c1, v75, v151, v1, c1, v78, v151, v0, c1, v72, v151, v8, c1, v62, v151));
        BLADE_IN_QUADS = builder.build();

        // BladeOut
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v15, c2, v89, v146, v14, c2, v88, v146, v13, c2, v87, v146, v12, c2, v86, v146));
        builder.add(new SimpleQuad(v14, c2, v88, v147, v17, c2, v91, v147, v16, c2, v90, v147, v13, c2, v87, v147));
        builder.add(new SimpleQuad(v17, c2, v91, v148, v19, c2, v93, v148, v18, c2, v92, v148, v16, c2, v90, v148));
        builder.add(new SimpleQuad(v17, c2, v95, v149, v14, c2, v90, v149, v15, c2, v79, v149, v19, c2, v94, v149));
        builder.add(new SimpleQuad(v19, c2, v93, v150, v15, c2, v97, v150, v12, c2, v96, v150, v18, c2, v92, v150));
        builder.add(new SimpleQuad(v13, c2, v87, v151, v16, c2, v90, v151, v18, c2, v79, v151, v12, c2, v76, v151));
        BLADE_OUT_QUADS = builder.build();

        // ShieldGrip
        Vector4f c3 = new Vector4f(0.8F, 0.8F, 0.8F, 1F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v23, c3, v101, v149, v22, c3, v100, v149, v21, c3, v99, v149, v20, c3, v98, v149));
        builder.add(new SimpleQuad(v22, c3, v100, v147, v25, c3, v103, v147, v24, c3, v102, v147, v21, c3, v99, v147));
        builder.add(new SimpleQuad(v25, c3, v103, v151, v27, c3, v105, v151, v26, c3, v104, v151, v24, c3, v102, v151));
        builder.add(new SimpleQuad(v25, c3, v108, v148, v22, c3, v102, v148, v23, c3, v107, v148, v27, c3, v106, v148));
        builder.add(new SimpleQuad(v27, c3, v105, v150, v23, c3, v110, v150, v20, c3, v109, v150, v26, c3, v104, v150));
        builder.add(new SimpleQuad(v21, c3, v99, v146, v24, c3, v102, v146, v26, c3, v107, v146, v20, c3, v111, v146));
        builder.add(new SimpleQuad(v31, c3, v115, v146, v30, c3, v114, v146, v29, c3, v113, v146, v28, c3, v112, v146));
        builder.add(new SimpleQuad(v30, c3, v114, v149, v33, c3, v117, v149, v32, c3, v116, v149, v29, c3, v113, v149));
        builder.add(new SimpleQuad(v33, c3, v117, v148, v35, c3, v118, v148, v34, c3, v110, v148, v32, c3, v116, v148));
        builder.add(new SimpleQuad(v35, c3, v120, v151, v31, c3, v115, v151, v28, c3, v112, v151, v34, c3, v119, v151));
        builder.add(new SimpleQuad(v35, c3, v122, v150, v33, c3, v117, v150, v30, c3, v114, v150, v31, c3, v121, v150));
        builder.add(new SimpleQuad(v28, c3, v124, v147, v29, c3, v123, v147, v32, c3, v114, v147, v34, c3, v121, v147));
        SHIELD_GRIP_QUADS = builder.build();

        // ShieldIn
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v39, c4, v128, v151, v38, c4, v127, v151, v37, c4, v126, v151, v36, c4, v125, v151));
        builder.add(new SimpleQuad(v38, c4, v131, v148, v41, c4, v73, v148, v40, c4, v130, v148, v37, c4, v129, v148));
        builder.add(new SimpleQuad(v41, c4, v73, v149, v43, c4, v69, v149, v42, c4, v132, v149, v40, c4, v130, v149));
        builder.add(new SimpleQuad(v43, c4, v69, v146, v39, c4, v128, v146, v36, c4, v125, v146, v42, c4, v132, v146));
        builder.add(new SimpleQuad(v36, c4, v133, v150, v37, c4, v100, v150, v40, c4, v130, v150, v42, c4, v132, v150));
        builder.add(new SimpleQuad(v39, c4, v116, v147, v43, c4, v134, v147, v41, c4, v132, v147, v38, c4, v133, v147));
        SHIELD_IN_QUADS = builder.build();

        // ShieldMid
        Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.5F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v47, c5, v138, v151, v46, c5, v137, v151, v45, c5, v136, v151, v44, c5, v135, v151));
        builder.add(new SimpleQuad(v46, c5, v127, v148, v49, c5, v140, v148, v48, c5, v139, v148, v45, c5, v126, v148));
        builder.add(new SimpleQuad(v49, c5, v140, v149, v51, c5, v142, v149, v50, c5, v141, v149, v48, c5, v139, v149));
        builder.add(new SimpleQuad(v51, c5, v142, v146, v47, c5, v138, v146, v44, c5, v135, v146, v50, c5, v141, v146));
        builder.add(new SimpleQuad(v44, c5, v143, v150, v45, c5, v113, v150, v48, c5, v139, v150, v50, c5, v141, v150));
        builder.add(new SimpleQuad(v47, c5, v145, v147, v51, c5, v144, v147, v49, c5, v141, v147, v46, c5, v143, v147));
        SHIELD_MID_QUADS = builder.build();

        // ShieldOut
        Vector4f c6 = new Vector4f(1F, 1F, 1F, 0.25F);
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v55, c6, v138, v151, v54, c6, v137, v151, v53, c6, v136, v151, v52, c6, v135, v151));
        builder.add(new SimpleQuad(v54, c6, v127, v148, v57, c6, v140, v148, v56, c6, v139, v148, v53, c6, v126, v148));
        builder.add(new SimpleQuad(v57, c6, v140, v149, v59, c6, v142, v149, v58, c6, v141, v149, v56, c6, v139, v149));
        builder.add(new SimpleQuad(v59, c6, v142, v146, v55, c6, v138, v146, v52, c6, v135, v146, v58, c6, v141, v146));
        builder.add(new SimpleQuad(v52, c6, v143, v150, v53, c6, v113, v150, v56, c6, v139, v150, v58, c6, v141, v150));
        builder.add(new SimpleQuad(v55, c6, v145, v147, v59, c6, v144, v147, v57, c6, v141, v147, v54, c6, v143, v147));
        SHIELD_OUT_QUADS = builder.build();
    }
}
