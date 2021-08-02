package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LaserBladeModelType222 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/internal/laser_blade_222.png");
    public static final List<SimpleQuad> HILT_QUADS;
    public static final List<SimpleQuad> HILT_LIGHT_QUADS;
    public static final List<SimpleQuad> BLADE_IN_QUADS;
    public static final List<SimpleQuad> BLADE_MID_QUADS;
    public static final List<SimpleQuad> BLADE_OUT_QUADS;

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (mode == ItemTransforms.TransformType.FIXED
                || mode == ItemTransforms.TransformType.GUI
                || mode == ItemTransforms.TransformType.GROUND) {
            matrices.translate(0.0D, 0.2D, 0.0D);
            matrices.scale(0.9F, 0.9F, 0.9F);
        }

        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        final int fullLight = 0xF000F0;
        final int noTintColor = 0xFFFFFFFF;

        VertexConsumer currentBuffer = vertexConsumers.getBuffer(getHiltRenderType());
        renderQuads(matrices, currentBuffer, HILT_QUADS, color.gripColor, light, overlay);

        if (color.isBroken) {
            renderQuads(matrices, currentBuffer, HILT_LIGHT_QUADS, noTintColor, light, overlay);
            return;
        }

        currentBuffer = vertexConsumers.getBuffer(getUnlitRenderType());
        renderQuads(matrices, currentBuffer, HILT_LIGHT_QUADS, noTintColor, fullLight, overlay);

        currentBuffer = vertexConsumers.getBuffer(getInnerBladeAddRenderType(color.isInnerSubColor));
        renderQuads(matrices, currentBuffer, BLADE_IN_QUADS, color.innerColor, fullLight, overlay);

        currentBuffer = vertexConsumers.getBuffer(getOuterBladeAddRenderType(color.isOuterSubColor));
        renderQuads(matrices, currentBuffer, BLADE_MID_QUADS, color.outerColor, fullLight, overlay);
        renderQuads(matrices, currentBuffer, BLADE_OUT_QUADS, color.outerColor, fullLight, overlay);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    static {
        Vector4f c0 = new Vector4f(0.8F, 0.8F, 0.8F, 1F); // Hilt color
        Vector4f c1 = new Vector4f(1F, 1F, 1F, 1F); // HiltLight color
        Vector4f c2 = new Vector4f(1F, 1F, 1F, 0.9F); // BladeIn color
        Vector4f c3 = new Vector4f(1F, 1F, 1F, 0.5F); // BladeMid color
        Vector4f c4 = new Vector4f(1F, 1F, 1F, 0.25F); // BladeOut color

        Vector3f v31 = new Vector3f(-0.039062F, 0.468750F, 0.078125F);
        Vector3f v19 = new Vector3f(0.046875F, 0.343750F, -0.109375F);
        Vector3f v32 = new Vector3f(0.070312F, 0.500000F, 0.015625F);
        Vector3f v108 = new Vector3f(0.093750F, 1.437500F, -0.093750F);
        Vector3f v41 = new Vector3f(-0.062500F, 0.406250F, 0.093750F);
        Vector3f v4 = new Vector3f(0.078125F, 0.468750F, 0.078125F);
        Vector3f v54 = new Vector3f(-0.062500F, 0.390625F, 0.093750F);
        Vector3f v1 = new Vector3f(-0.078125F, 0.468750F, 0.078125F);
        Vector3f v37 = new Vector3f(0.039062F, 0.468750F, 0.078125F);
        Vector3f v25 = new Vector3f(-0.070312F, 0.500000F, 0.015625F);
        Vector3f v80 = new Vector3f(0.031250F, -0.093750F, 0.406250F);
        Vector3f v24 = new Vector3f(-0.039062F, 0.500000F, 0.015625F);
        Vector3f v35 = new Vector3f(0.070312F, 0.468750F, 0.015625F);
        Vector3f v57 = new Vector3f(0.062500F, 0.390625F, -0.281250F);
        Vector3f v267 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
        Vector3f v266 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
        Vector3f v83 = new Vector3f(0.031250F, -0.031250F, 0.406250F);
        Vector3f v44 = new Vector3f(-0.062500F, -0.093750F, 0.093750F);
        Vector3f v264 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
        Vector3f v51 = new Vector3f(-0.062500F, 0.328125F, -0.281250F);
        Vector3f v61 = new Vector3f(0.062500F, 0.328125F, 0.093750F);
        Vector3f v42 = new Vector3f(-0.062500F, 0.406250F, -0.031250F);
        Vector3f v13 = new Vector3f(0.015625F, 0.406250F, -0.078125F);
        Vector3f v111 = new Vector3f(-0.093750F, 0.437500F, -0.093750F);
        Vector3f v110 = new Vector3f(-0.093750F, 1.437500F, -0.093750F);
        Vector3f v109 = new Vector3f(0.093750F, 0.437500F, -0.093750F);
        Vector3f v107 = new Vector3f(-0.093750F, 0.437500F, 0.093750F);
        Vector3f v50 = new Vector3f(-0.125000F, 0.328125F, -0.281250F);
        Vector3f v106 = new Vector3f(0.093750F, 0.437500F, 0.093750F);
        Vector3f v56 = new Vector3f(0.125000F, 0.390625F, -0.281250F);
        Vector3f v40 = new Vector3f(0.062500F, 0.406250F, 0.093750F);
        Vector3f v105 = new Vector3f(0.093750F, 1.437500F, 0.093750F);
        Vector3f v45 = new Vector3f(-0.062500F, -0.093750F, -0.031250F);
        Vector3f v30 = new Vector3f(-0.039062F, 0.500000F, 0.078125F);
        Vector3f v89 = new Vector3f(0.031250F, 1.375000F, 0.031250F);
        Vector3f v11 = new Vector3f(-0.015625F, 0.437500F, -0.078125F);
        Vector3f v70 = new Vector3f(-0.015625F, 0.000000F, -0.046875F);
        Vector3f v8 = new Vector3f(0.078125F, 0.406250F, -0.078125F);
        Vector3f v28 = new Vector3f(-0.070312F, 0.500000F, 0.078125F);
        Vector3f v75 = new Vector3f(0.078125F, -0.312500F, 0.015625F);
        Vector3f v103 = new Vector3f(-0.062500F, 0.437500F, -0.062500F);
        Vector3f v102 = new Vector3f(-0.062500F, 1.406250F, -0.062500F);
        Vector3f v101 = new Vector3f(0.062500F, 0.437500F, -0.062500F);
        Vector3f v38 = new Vector3f(0.070312F, 0.500000F, 0.078125F);
        Vector3f v100 = new Vector3f(0.062500F, 1.406250F, -0.062500F);
        Vector3f v33 = new Vector3f(0.039062F, 0.500000F, 0.015625F);
        Vector3f v26 = new Vector3f(-0.070312F, 0.468750F, 0.015625F);
        Vector3f v17 = new Vector3f(-0.046875F, 0.406250F, -0.109375F);
        Vector3f v97 = new Vector3f(0.062500F, 1.406250F, 0.062500F);
        Vector3f v34 = new Vector3f(0.039062F, 0.468750F, 0.015625F);
        Vector3f v96 = new Vector3f(-0.062500F, 1.406250F, 0.062500F);
        Vector3f v95 = new Vector3f(-0.031250F, 0.437500F, -0.031250F);
        Vector3f v7 = new Vector3f(0.078125F, 0.343750F, -0.078125F);
        Vector3f v6 = new Vector3f(-0.078125F, 0.406250F, -0.078125F);
        Vector3f v12 = new Vector3f(-0.015625F, 0.406250F, -0.078125F);
        Vector3f v91 = new Vector3f(-0.031250F, 0.437500F, 0.031250F);
        Vector3f v93 = new Vector3f(0.031250F, 0.437500F, -0.031250F);
        Vector3f v90 = new Vector3f(0.031250F, 0.437500F, 0.031250F);
        Vector3f v22 = new Vector3f(0.046875F, 0.406250F, -0.078125F);
        Vector3f v92 = new Vector3f(0.031250F, 1.375000F, -0.031250F);
        Vector3f v94 = new Vector3f(-0.031250F, 1.375000F, -0.031250F);
        Vector3f v16 = new Vector3f(0.046875F, 0.406250F, -0.109375F);
        Vector3f v82 = new Vector3f(-0.031250F, -0.031250F, 0.406250F);
        Vector3f v43 = new Vector3f(0.062500F, 0.406250F, -0.031250F);
        Vector3f v53 = new Vector3f(-0.125000F, 0.328125F, 0.093750F);
        Vector3f v87 = new Vector3f(0.031250F, -0.031250F, 0.093750F);
        Vector3f v84 = new Vector3f(-0.031250F, -0.093750F, 0.093750F);
        Vector3f v9 = new Vector3f(0.078125F, 0.437500F, -0.078125F);
        Vector3f v21 = new Vector3f(-0.046875F, 0.343750F, -0.078125F);
        Vector3f v66 = new Vector3f(-0.078125F, -0.312500F, 0.015625F);
        Vector3f v65 = new Vector3f(-0.078125F, -0.312500F, -0.046875F);
        Vector3f v81 = new Vector3f(-0.031250F, -0.093750F, 0.406250F);
        Vector3f v78 = new Vector3f(0.078125F, 0.000000F, -0.046875F);
        Vector3f v86 = new Vector3f(0.031250F, -0.093750F, 0.093750F);
        Vector3f v20 = new Vector3f(-0.046875F, 0.406250F, -0.078125F);
        Vector3f v3 = new Vector3f(-0.078125F, 0.343750F, -0.078125F);
        Vector3f v48 = new Vector3f(-0.062500F, 0.390625F, -0.281250F);
        Vector3f v39 = new Vector3f(0.070312F, 0.468750F, 0.078125F);
        Vector3f v58 = new Vector3f(0.062500F, 0.328125F, -0.281250F);
        Vector3f v85 = new Vector3f(-0.031250F, -0.031250F, 0.093750F);
        Vector3f v265 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
        Vector3f v88 = new Vector3f(-0.031250F, 1.375000F, 0.031250F);
        Vector3f v60 = new Vector3f(0.062500F, 0.390625F, 0.093750F);
        Vector3f v79 = new Vector3f(0.078125F, 0.000000F, 0.015625F);
        Vector3f v77 = new Vector3f(0.015625F, 0.000000F, 0.015625F);
        Vector3f v76 = new Vector3f(0.015625F, 0.000000F, -0.046875F);
        Vector3f v5 = new Vector3f(0.078125F, 0.468750F, -0.078125F);
        Vector3f v52 = new Vector3f(-0.125000F, 0.390625F, 0.093750F);
        Vector3f v46 = new Vector3f(0.062500F, -0.093750F, 0.093750F);
        Vector3f v18 = new Vector3f(-0.046875F, 0.343750F, -0.109375F);
        Vector3f v104 = new Vector3f(-0.093750F, 1.437500F, 0.093750F);
        Vector3f v74 = new Vector3f(0.015625F, -0.312500F, 0.015625F);
        Vector3f v71 = new Vector3f(-0.015625F, 0.000000F, 0.015625F);
        Vector3f v36 = new Vector3f(0.039062F, 0.500000F, 0.078125F);
        Vector3f v27 = new Vector3f(-0.039062F, 0.468750F, 0.015625F);
        Vector3f v29 = new Vector3f(-0.070312F, 0.468750F, 0.078125F);
        Vector3f v72 = new Vector3f(0.078125F, -0.312500F, -0.046875F);
        Vector3f v73 = new Vector3f(0.015625F, -0.312500F, -0.046875F);
        Vector3f v47 = new Vector3f(0.062500F, -0.093750F, -0.031250F);
        Vector3f v69 = new Vector3f(-0.078125F, 0.000000F, 0.015625F);
        Vector3f v68 = new Vector3f(-0.078125F, 0.000000F, -0.046875F);
        Vector3f v67 = new Vector3f(-0.015625F, -0.312500F, 0.015625F);
        Vector3f v64 = new Vector3f(-0.015625F, -0.312500F, -0.046875F);
        Vector3f v63 = new Vector3f(0.125000F, 0.328125F, 0.093750F);
        Vector3f v14 = new Vector3f(0.015625F, 0.437500F, -0.078125F);
        Vector3f v62 = new Vector3f(0.125000F, 0.390625F, 0.093750F);
        Vector3f v263 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
        Vector3f v49 = new Vector3f(-0.125000F, 0.390625F, -0.281250F);
        Vector3f v55 = new Vector3f(-0.062500F, 0.328125F, 0.093750F);
        Vector3f v2 = new Vector3f(-0.078125F, 0.343750F, 0.078125F);
        Vector3f v59 = new Vector3f(0.125000F, 0.328125F, -0.281250F);
        Vector3f v23 = new Vector3f(0.046875F, 0.343750F, -0.078125F);
        Vector3f v98 = new Vector3f(0.062500F, 0.437500F, 0.062500F);
        Vector3f v15 = new Vector3f(0.078125F, 0.343750F, 0.078125F);
        Vector3f v268 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
        Vector3f v0 = new Vector3f(-0.078125F, 0.468750F, -0.078125F);
        Vector3f v99 = new Vector3f(-0.062500F, 0.437500F, 0.062500F);
        Vector3f v10 = new Vector3f(-0.078125F, 0.437500F, -0.078125F);
        Vector2f v251 = new Vector2f(0.43750F, 0.31250F);
        Vector2f v261 = new Vector2f(0.81250F, 0.37500F);
        Vector2f v178 = new Vector2f(0.25000F, 0.43750F);
        Vector2f v159 = new Vector2f(0.71875F, 0.09375F);
        Vector2f v157 = new Vector2f(0.68750F, 0.06250F);
        Vector2f v257 = new Vector2f(0.90625F, 0.90625F);
        Vector2f v189 = new Vector2f(0.25000F, 0.90625F);
        Vector2f v213 = new Vector2f(0.71875F, 0.12500F);
        Vector2f v229 = new Vector2f(0.34375F, 0.37500F);
        Vector2f v174 = new Vector2f(0.00000F, 0.68750F);
        Vector2f v177 = new Vector2f(0.25000F, 0.68750F);
        Vector2f v196 = new Vector2f(0.03125F, 0.71875F);
        Vector2f v206 = new Vector2f(0.78125F, 0.12500F);
        Vector2f v135 = new Vector2f(0.12500F, 0.37500F);
        Vector2f v176 = new Vector2f(0.06250F, 0.68750F);
        Vector2f v145 = new Vector2f(0.87500F, 0.06250F);
        Vector2f v182 = new Vector2f(0.18750F, 0.90625F);
        Vector2f v120 = new Vector2f(0.15625F, 0.15625F);
        Vector2f v134 = new Vector2f(0.12500F, 0.31250F);
        Vector2f v228 = new Vector2f(1.00000F, 0.12500F);
        Vector2f v115 = new Vector2f(0.31250F, 0.28125F);
        Vector2f v153 = new Vector2f(0.81250F, 0.06250F);
        Vector2f v144 = new Vector2f(0.21875F, 0.28125F);
        Vector2f v222 = new Vector2f(0.93750F, 0.09375F);
        Vector2f v122 = new Vector2f(0.25000F, 0.18750F);
        Vector2f v118 = new Vector2f(0.15625F, 0.21875F);
        Vector2f v249 = new Vector2f(0.50000F, 0.37500F);
        Vector2f v250 = new Vector2f(0.50000F, 0.87500F);
        Vector2f v202 = new Vector2f(0.84375F, 0.12500F);
        Vector2f v262 = new Vector2f(0.81250F, 0.90625F);
        Vector2f v209 = new Vector2f(0.75000F, 0.12500F);
        Vector2f v187 = new Vector2f(0.12500F, 0.90625F);
        Vector2f v179 = new Vector2f(0.18750F, 0.71875F);
        Vector2f v150 = new Vector2f(0.96875F, 0.09375F);
        Vector2f v127 = new Vector2f(0.00000F, 0.28125F);
        Vector2f v242 = new Vector2f(0.56250F, 0.37500F);
        Vector2f v191 = new Vector2f(0.06250F, 0.71875F);
        Vector2f v235 = new Vector2f(0.28125F, 0.84375F);
        Vector2f v232 = new Vector2f(0.34375F, 0.84375F);
        Vector2f v195 = new Vector2f(0.09375F, 0.90625F);
        Vector2f v125 = new Vector2f(0.21875F, 0.18750F);
        Vector2f v156 = new Vector2f(0.90625F, 0.00000F);
        Vector2f v205 = new Vector2f(0.84375F, 0.28125F);
        Vector2f v172 = new Vector2f(0.12500F, 0.68750F);
        Vector2f v221 = new Vector2f(0.96875F, 0.12500F);
        Vector2f v138 = new Vector2f(0.15625F, 0.37500F);
        Vector2f v194 = new Vector2f(0.06250F, 0.90625F);
        Vector2f v131 = new Vector2f(0.31250F, 0.00000F);
        Vector2f v212 = new Vector2f(0.68750F, 0.12500F);
        Vector2f v143 = new Vector2f(0.21875F, 0.31250F);
        Vector2f v220 = new Vector2f(0.93750F, 0.12500F);
        Vector2f v199 = new Vector2f(0.00000F, 0.90625F);
        Vector2f v259 = new Vector2f(0.71875F, 0.90625F);
        Vector2f v130 = new Vector2f(0.15625F, 0.00000F);
        Vector2f v151 = new Vector2f(1.00000F, 0.06250F);
        Vector2f v219 = new Vector2f(0.62500F, 0.12500F);
        Vector2f v239 = new Vector2f(0.28125F, 0.34375F);
        Vector2f v200 = new Vector2f(0.00000F, 0.71875F);
        Vector2f v233 = new Vector2f(0.25000F, 0.37500F);
        Vector2f v207 = new Vector2f(0.78125F, 0.28125F);
        Vector2f v190 = new Vector2f(0.25000F, 0.71875F);
        Vector2f v146 = new Vector2f(0.90625F, 0.06250F);
        Vector2f v198 = new Vector2f(0.03125F, 0.90625F);
        Vector2f v162 = new Vector2f(0.78125F, 0.09375F);
        Vector2f v204 = new Vector2f(0.81250F, 0.28125F);
        Vector2f v132 = new Vector2f(0.46875F, 0.00000F);
        Vector2f v185 = new Vector2f(0.15625F, 0.68750F);
        Vector2f v168 = new Vector2f(0.12500F, 0.43750F);
        Vector2f v117 = new Vector2f(0.15625F, 0.28125F);
        Vector2f v137 = new Vector2f(0.15625F, 0.31250F);
        Vector2f v167 = new Vector2f(0.06250F, 0.37500F);
        Vector2f v112 = new Vector2f(0.31250F, 0.15625F);
        Vector2f v180 = new Vector2f(0.21875F, 0.71875F);
        Vector2f v154 = new Vector2f(0.81250F, 0.09375F);
        Vector2f v126 = new Vector2f(0.00000F, 0.15625F);
        Vector2f v181 = new Vector2f(0.21875F, 0.68750F);
        Vector2f v158 = new Vector2f(0.71875F, 0.06250F);
        Vector2f v214 = new Vector2f(0.68750F, 0.28125F);
        Vector2f v216 = new Vector2f(0.65625F, 0.12500F);
        Vector2f v260 = new Vector2f(0.62500F, 0.90625F);
        Vector2f v258 = new Vector2f(0.71875F, 0.37500F);
        Vector2f v256 = new Vector2f(1.00000F, 0.90625F);
        Vector2f v142 = new Vector2f(0.12500F, 0.28125F);
        Vector2f v188 = new Vector2f(0.12500F, 0.71875F);
        Vector2f v248 = new Vector2f(0.37500F, 0.87500F);
        Vector2f v124 = new Vector2f(0.21875F, 0.21875F);
        Vector2f v255 = new Vector2f(1.00000F, 0.37500F);
        Vector2f v237 = new Vector2f(0.31250F, 0.37500F);
        Vector2f v253 = new Vector2f(0.56250F, 0.31250F);
        Vector2f v252 = new Vector2f(0.50000F, 0.31250F);
        Vector2f v141 = new Vector2f(0.03125F, 0.28125F);
        Vector2f v161 = new Vector2f(0.78125F, 0.06250F);
        Vector2f v166 = new Vector2f(0.71875F, 0.00000F);
        Vector2f v247 = new Vector2f(0.43750F, 0.87500F);
        Vector2f v245 = new Vector2f(0.56250F, 0.87500F);
        Vector2f v114 = new Vector2f(0.46875F, 0.28125F);
        Vector2f v170 = new Vector2f(0.18750F, 0.43750F);
        Vector2f v246 = new Vector2f(0.43750F, 0.37500F);
        Vector2f v210 = new Vector2f(0.87500F, 0.28125F);
        Vector2f v149 = new Vector2f(0.96875F, 0.06250F);
        Vector2f v244 = new Vector2f(0.62500F, 0.87500F);
        Vector2f v123 = new Vector2f(0.25000F, 0.21875F);
        Vector2f v241 = new Vector2f(0.34375F, 0.34375F);
        Vector2f v240 = new Vector2f(0.31250F, 0.34375F);
        Vector2f v238 = new Vector2f(0.31250F, 0.84375F);
        Vector2f v201 = new Vector2f(0.81250F, 0.12500F);
        Vector2f v254 = new Vector2f(0.90625F, 0.37500F);
        Vector2f v236 = new Vector2f(0.25000F, 0.84375F);
        Vector2f v197 = new Vector2f(0.03125F, 0.68750F);
        Vector2f v234 = new Vector2f(0.28125F, 0.37500F);
        Vector2f v183 = new Vector2f(0.21875F, 0.90625F);
        Vector2f v128 = new Vector2f(0.62500F, 0.15625F);
        Vector2f v113 = new Vector2f(0.46875F, 0.15625F);
        Vector2f v169 = new Vector2f(0.06250F, 0.43750F);
        Vector2f v231 = new Vector2f(0.37500F, 0.84375F);
        Vector2f v139 = new Vector2f(0.00000F, 0.31250F);
        Vector2f v208 = new Vector2f(0.75000F, 0.28125F);
        Vector2f v171 = new Vector2f(0.18750F, 0.68750F);
        Vector2f v230 = new Vector2f(0.37500F, 0.37500F);
        Vector2f v160 = new Vector2f(0.68750F, 0.09375F);
        Vector2f v227 = new Vector2f(1.00000F, 0.28125F);
        Vector2f v215 = new Vector2f(0.71875F, 0.28125F);
        Vector2f v148 = new Vector2f(0.87500F, 0.09375F);
        Vector2f v192 = new Vector2f(0.09375F, 0.71875F);
        Vector2f v203 = new Vector2f(0.84375F, 0.09375F);
        Vector2f v147 = new Vector2f(0.90625F, 0.09375F);
        Vector2f v119 = new Vector2f(0.15625F, 0.18750F);
        Vector2f v136 = new Vector2f(0.03125F, 0.37500F);
        Vector2f v164 = new Vector2f(0.62500F, 0.09375F);
        Vector2f v175 = new Vector2f(0.00000F, 0.43750F);
        Vector2f v226 = new Vector2f(0.90625F, 0.28125F);
        Vector2f v225 = new Vector2f(0.90625F, 0.12500F);
        Vector2f v121 = new Vector2f(0.31250F, 0.18750F);
        Vector2f v186 = new Vector2f(0.15625F, 0.90625F);
        Vector2f v116 = new Vector2f(0.31250F, 0.21875F);
        Vector2f v133 = new Vector2f(0.03125F, 0.31250F);
        Vector2f v224 = new Vector2f(0.96875F, 0.28125F);
        Vector2f v223 = new Vector2f(0.93750F, 0.28125F);
        Vector2f v184 = new Vector2f(0.15625F, 0.71875F);
        Vector2f v163 = new Vector2f(0.62500F, 0.06250F);
        Vector2f v218 = new Vector2f(0.65625F, 0.28125F);
        Vector2f v155 = new Vector2f(0.87500F, 0.00000F);
        Vector2f v217 = new Vector2f(0.65625F, 0.09375F);
        Vector2f v173 = new Vector2f(0.18750F, 0.37500F);
        Vector2f v211 = new Vector2f(0.87500F, 0.12500F);
        Vector2f v193 = new Vector2f(0.09375F, 0.68750F);
        Vector2f v165 = new Vector2f(0.68750F, 0.00000F);
        Vector2f v140 = new Vector2f(0.00000F, 0.37500F);
        Vector2f v152 = new Vector2f(1.00000F, 0.09375F);
        Vector2f v243 = new Vector2f(0.62500F, 0.37500F);
        Vector2f v129 = new Vector2f(0.62500F, 0.28125F);
        ImmutableList.Builder<SimpleQuad> builder;

        // Hilt
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v3, c0, v115, v263, v2, c0, v114, v263, v1, c0, v113, v263, v0, c0, v112, v263));
        builder.add(new SimpleQuad(v5, c0, v115, v264, v0, c0, v114, v264, v1, c0, v113, v264, v4, c0, v112, v264));
        builder.add(new SimpleQuad(v8, c0, v118, v265, v7, c0, v117, v265, v3, c0, v115, v265, v6, c0, v116, v265));
        builder.add(new SimpleQuad(v10, c0, v121, v265, v0, c0, v112, v265, v5, c0, v120, v265, v9, c0, v119, v265));
        builder.add(new SimpleQuad(v14, c0, v125, v265, v13, c0, v124, v265, v12, c0, v123, v265, v11, c0, v122, v265));
        builder.add(new SimpleQuad(v15, c0, v127, v266, v7, c0, v117, v266, v5, c0, v120, v266, v4, c0, v126, v266));
        builder.add(new SimpleQuad(v2, c0, v114, v267, v15, c0, v129, v267, v4, c0, v128, v267, v1, c0, v113, v267));
        builder.add(new SimpleQuad(v5, c0, v120, v264, v0, c0, v112, v264, v1, c0, v131, v264, v4, c0, v130, v264));
        builder.add(new SimpleQuad(v15, c0, v131, v268, v2, c0, v132, v268, v3, c0, v113, v268, v7, c0, v112, v268));
        builder.add(new SimpleQuad(v19, c0, v136, v265, v18, c0, v135, v265, v17, c0, v134, v265, v16, c0, v133, v265));
        builder.add(new SimpleQuad(v18, c0, v135, v263, v21, c0, v138, v263, v20, c0, v137, v263, v17, c0, v134, v263));
        builder.add(new SimpleQuad(v23, c0, v140, v266, v19, c0, v136, v266, v16, c0, v133, v266, v22, c0, v139, v266));
        builder.add(new SimpleQuad(v16, c0, v133, v264, v17, c0, v134, v264, v20, c0, v142, v264, v22, c0, v141, v264));
        builder.add(new SimpleQuad(v23, c0, v142, v268, v21, c0, v144, v268, v18, c0, v143, v268, v19, c0, v134, v268));
        builder.add(new SimpleQuad(v27, c0, v148, v265, v26, c0, v147, v265, v25, c0, v146, v265, v24, c0, v145, v265));
        builder.add(new SimpleQuad(v26, c0, v147, v263, v29, c0, v150, v263, v28, c0, v149, v263, v25, c0, v146, v263));
        builder.add(new SimpleQuad(v29, c0, v150, v267, v31, c0, v152, v267, v30, c0, v151, v267, v28, c0, v149, v267));
        builder.add(new SimpleQuad(v31, c0, v154, v266, v27, c0, v148, v266, v24, c0, v145, v266, v30, c0, v153, v266));
        builder.add(new SimpleQuad(v24, c0, v145, v264, v25, c0, v146, v264, v28, c0, v156, v264, v30, c0, v155, v264));
        builder.add(new SimpleQuad(v35, c0, v160, v265, v34, c0, v159, v265, v33, c0, v158, v265, v32, c0, v157, v265));
        builder.add(new SimpleQuad(v34, c0, v159, v263, v37, c0, v162, v263, v36, c0, v161, v263, v33, c0, v158, v263));
        builder.add(new SimpleQuad(v37, c0, v162, v267, v39, c0, v154, v267, v38, c0, v153, v267, v36, c0, v161, v267));
        builder.add(new SimpleQuad(v39, c0, v164, v266, v35, c0, v160, v266, v32, c0, v157, v266, v38, c0, v163, v266));
        builder.add(new SimpleQuad(v32, c0, v157, v264, v33, c0, v158, v264, v36, c0, v166, v264, v38, c0, v165, v264));
        builder.add(new SimpleQuad(v43, c0, v169, v264, v42, c0, v168, v264, v41, c0, v135, v264, v40, c0, v167, v264));
        builder.add(new SimpleQuad(v42, c0, v168, v263, v45, c0, v172, v263, v44, c0, v171, v263, v41, c0, v170, v263));
        builder.add(new SimpleQuad(v45, c0, v170, v268, v47, c0, v168, v268, v46, c0, v135, v268, v44, c0, v173, v268));
        builder.add(new SimpleQuad(v47, c0, v176, v266, v43, c0, v169, v266, v40, c0, v175, v266, v46, c0, v174, v266));
        builder.add(new SimpleQuad(v40, c0, v178, v267, v41, c0, v170, v267, v44, c0, v171, v267, v46, c0, v177, v267));
        builder.add(new SimpleQuad(v47, c0, v176, v265, v45, c0, v172, v265, v42, c0, v168, v265, v43, c0, v169, v265));
        builder.add(new SimpleQuad(v51, c0, v171, v265, v50, c0, v181, v265, v49, c0, v180, v265, v48, c0, v179, v265));
        builder.add(new SimpleQuad(v50, c0, v183, v263, v53, c0, v180, v263, v52, c0, v179, v263, v49, c0, v182, v263));
        builder.add(new SimpleQuad(v53, c0, v171, v267, v55, c0, v185, v267, v54, c0, v184, v267, v52, c0, v179, v267));
        builder.add(new SimpleQuad(v55, c0, v188, v266, v51, c0, v187, v266, v48, c0, v186, v266, v54, c0, v184, v266));
        builder.add(new SimpleQuad(v48, c0, v186, v264, v49, c0, v182, v264, v52, c0, v179, v264, v54, c0, v184, v264));
        builder.add(new SimpleQuad(v55, c0, v190, v268, v53, c0, v180, v268, v50, c0, v183, v268, v51, c0, v189, v268));
        builder.add(new SimpleQuad(v59, c0, v176, v265, v58, c0, v193, v265, v57, c0, v192, v265, v56, c0, v191, v265));
        builder.add(new SimpleQuad(v58, c0, v195, v263, v61, c0, v192, v263, v60, c0, v191, v263, v57, c0, v194, v263));
        builder.add(new SimpleQuad(v61, c0, v176, v267, v63, c0, v197, v267, v62, c0, v196, v267, v60, c0, v191, v267));
        builder.add(new SimpleQuad(v63, c0, v200, v266, v59, c0, v199, v266, v56, c0, v198, v266, v62, c0, v196, v266));
        builder.add(new SimpleQuad(v56, c0, v198, v264, v57, c0, v194, v264, v60, c0, v191, v264, v62, c0, v196, v264));
        builder.add(new SimpleQuad(v63, c0, v188, v268, v61, c0, v192, v268, v58, c0, v195, v268, v59, c0, v187, v268));
        builder.add(new SimpleQuad(v67, c0, v154, v268, v66, c0, v203, v268, v65, c0, v202, v268, v64, c0, v201, v268));
        builder.add(new SimpleQuad(v66, c0, v205, v263, v69, c0, v202, v263, v68, c0, v201, v263, v65, c0, v204, v263));
        builder.add(new SimpleQuad(v69, c0, v154, v264, v71, c0, v162, v264, v70, c0, v206, v264, v68, c0, v201, v264));
        builder.add(new SimpleQuad(v71, c0, v209, v266, v67, c0, v208, v266, v64, c0, v207, v266, v70, c0, v206, v266));
        builder.add(new SimpleQuad(v64, c0, v207, v265, v65, c0, v204, v265, v68, c0, v201, v265, v70, c0, v206, v265));
        builder.add(new SimpleQuad(v71, c0, v211, v267, v69, c0, v202, v267, v66, c0, v205, v267, v67, c0, v210, v267));
        builder.add(new SimpleQuad(v75, c0, v160, v268, v74, c0, v159, v268, v73, c0, v213, v268, v72, c0, v212, v268));
        builder.add(new SimpleQuad(v74, c0, v215, v263, v77, c0, v213, v263, v76, c0, v212, v263, v73, c0, v214, v263));
        builder.add(new SimpleQuad(v77, c0, v160, v264, v79, c0, v217, v264, v78, c0, v216, v264, v76, c0, v212, v264));
        builder.add(new SimpleQuad(v79, c0, v219, v266, v75, c0, v129, v266, v72, c0, v218, v266, v78, c0, v216, v266));
        builder.add(new SimpleQuad(v72, c0, v218, v265, v73, c0, v214, v265, v76, c0, v212, v265, v78, c0, v216, v265));
        builder.add(new SimpleQuad(v79, c0, v209, v267, v77, c0, v213, v267, v74, c0, v215, v267, v75, c0, v208, v267));
        builder.add(new SimpleQuad(v83, c0, v222, v267, v82, c0, v150, v267, v81, c0, v221, v267, v80, c0, v220, v267));
        builder.add(new SimpleQuad(v82, c0, v224, v263, v85, c0, v221, v263, v84, c0, v220, v263, v81, c0, v223, v263));
        builder.add(new SimpleQuad(v85, c0, v222, v265, v87, c0, v147, v265, v86, c0, v225, v265, v84, c0, v220, v265));
        builder.add(new SimpleQuad(v87, c0, v211, v266, v83, c0, v210, v266, v80, c0, v226, v266, v86, c0, v225, v266));
        builder.add(new SimpleQuad(v80, c0, v226, v268, v81, c0, v223, v268, v84, c0, v220, v268, v86, c0, v225, v268));
        builder.add(new SimpleQuad(v87, c0, v228, v264, v85, c0, v221, v264, v82, c0, v224, v264, v83, c0, v227, v264));
        HILT_QUADS = builder.build();

        // HiltLight
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v9, c1, v119, v265, v8, c1, v118, v265, v13, c1, v124, v265, v14, c1, v125, v265));
        builder.add(new SimpleQuad(v12, c1, v123, v265, v6, c1, v116, v265, v10, c1, v121, v265, v11, c1, v122, v265));
        HILT_LIGHT_QUADS = builder.build();

        // BladeIn
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v91, c2, v232, v267, v90, c2, v231, v267, v89, c2, v230, v267, v88, c2, v229, v267));
        builder.add(new SimpleQuad(v90, c2, v236, v266, v93, c2, v235, v266, v92, c2, v234, v266, v89, c2, v233, v266));
        builder.add(new SimpleQuad(v93, c2, v235, v265, v95, c2, v238, v265, v94, c2, v237, v265, v92, c2, v234, v265));
        builder.add(new SimpleQuad(v95, c2, v238, v263, v91, c2, v232, v263, v88, c2, v229, v263, v94, c2, v237, v263));
        builder.add(new SimpleQuad(v88, c2, v240, v264, v89, c2, v239, v264, v92, c2, v234, v264, v94, c2, v237, v264));
        builder.add(new SimpleQuad(v91, c2, v241, v268, v95, c2, v229, v268, v93, c2, v237, v268, v90, c2, v240, v268));
        BLADE_IN_QUADS = builder.build();

        // BladeMid
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v99, c3, v245, v267, v98, c3, v244, v267, v97, c3, v243, v267, v96, c3, v242, v267));
        builder.add(new SimpleQuad(v98, c3, v248, v266, v101, c3, v247, v266, v100, c3, v246, v266, v97, c3, v230, v266));
        builder.add(new SimpleQuad(v101, c3, v247, v265, v103, c3, v250, v265, v102, c3, v249, v265, v100, c3, v246, v265));
        builder.add(new SimpleQuad(v103, c3, v250, v263, v99, c3, v245, v263, v96, c3, v242, v263, v102, c3, v249, v263));
        builder.add(new SimpleQuad(v96, c3, v252, v264, v97, c3, v251, v264, v100, c3, v246, v264, v102, c3, v249, v264));
        builder.add(new SimpleQuad(v98, c3, v252, v268, v99, c3, v253, v268, v103, c3, v242, v268, v101, c3, v249, v268));
        BLADE_MID_QUADS = builder.build();

        // BladeOut
        builder = ImmutableList.builder();
        builder.add(new SimpleQuad(v107, c4, v257, v267, v106, c4, v256, v267, v105, c4, v255, v267, v104, c4, v254, v267));
        builder.add(new SimpleQuad(v106, c4, v260, v266, v109, c4, v259, v266, v108, c4, v258, v266, v105, c4, v243, v266));
        builder.add(new SimpleQuad(v109, c4, v259, v265, v111, c4, v262, v265, v110, c4, v261, v265, v108, c4, v258, v265));
        builder.add(new SimpleQuad(v111, c4, v262, v263, v107, c4, v257, v263, v104, c4, v254, v263, v110, c4, v261, v263));
        builder.add(new SimpleQuad(v104, c4, v204, v264, v105, c4, v215, v264, v108, c4, v258, v264, v110, c4, v261, v264));
        builder.add(new SimpleQuad(v111, c4, v254, v268, v109, c4, v261, v268, v106, c4, v204, v268, v107, c4, v226, v268));
        BLADE_OUT_QUADS = builder.build();
    }
}
