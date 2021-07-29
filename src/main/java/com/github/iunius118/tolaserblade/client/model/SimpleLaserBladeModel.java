package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SimpleLaserBladeModel extends SimpleModel implements LaserBladeModel {
    private final boolean canUseFixedVertexBuffer;
    private RenderType typeHilt;
    private RenderType typeFlat;
    private RenderType typeAdd;
    private RenderType typeSubInner;
    private RenderType typeSub;
    private final RenderStateShard.TextureStateShard textureState;

    public SimpleLaserBladeModel() {
        LaserBladeInternalModelManager internalModelManager = LaserBladeInternalModelManager.getInstance();
        // Can use fixed vertex buffer when using external model, or using internal model but not multiple models.
        canUseFixedVertexBuffer = LaserBladeRenderType.canUseFixedVertexBuffer()
                && !internalModelManager.canRenderMultipleModels();
        textureState = new RenderStateShard.TextureStateShard(getTexture(), false, false);
    }

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

    }

    public RenderType getHiltRenderType() {
        if (canUseFixedVertexBuffer) {
            return LaserBladeRenderType.HILT;
        }

        // Not use fixed vertex buffer
        if (typeHilt == null) {
            CompositeState renderState = LaserBladeRenderType.getHiltRenderState(textureState);
            typeHilt = LaserBladeRenderType.getBladeRenderType("hilt", renderState);
        }

        return typeHilt;
    }

    public RenderType getFlatRenderType() {
        if (canUseFixedVertexBuffer) {
            return LaserBladeRenderType.LASER_FLAT;
        }

        // Not use fixed vertex buffer
        if (typeFlat == null) {
            CompositeState renderState = LaserBladeRenderType.getFlatRenderState(textureState);
            typeFlat = LaserBladeRenderType.getBladeRenderType("laser_flat", renderState);
        }

        return typeFlat;
    }

    public RenderType getAddRenderType() {
        if (canUseFixedVertexBuffer) {
            return LaserBladeRenderType.LASER_ADD;
        }

        // Not use fixed vertex buffer
        if (typeAdd == null) {
            CompositeState renderState = LaserBladeRenderType.getAddRenderState(textureState);
            typeAdd = LaserBladeRenderType.getBladeRenderType("laser_add", renderState);
        }

        return typeAdd;
    }

    public RenderType getSubInnerRenderType() {
        if (canUseFixedVertexBuffer) {
            return LaserBladeRenderType.LASER_SUB_INNER;
        }

        // Not use fixed vertex buffer
        if (typeSubInner == null) {
            CompositeState renderState = LaserBladeRenderType.getSubRenderState(textureState);
            typeSubInner = LaserBladeRenderType.getBladeRenderType("laser_sub_in", renderState);
        }

        return typeSubInner;
    }

    public RenderType getSubRenderType() {
        if (canUseFixedVertexBuffer) {
            return LaserBladeRenderType.LASER_SUB;
        }

        // Not use fixed vertex buffer
        if (typeSub == null) {
            CompositeState renderState = LaserBladeRenderType.getSubRenderState(textureState);
            typeSub = LaserBladeRenderType.getBladeRenderType("laser_sub", renderState);
        }

        return typeSub;
    }

    public RenderType getInnerBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubInnerRenderType() : getAddRenderType();
    }

    public RenderType getOuterBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubRenderType() : getAddRenderType();
    }

    public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("forge", "textures/white.png");

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE_WHITE;
    }
}
