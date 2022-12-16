package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SimpleLaserBladeModel extends SimpleModel implements LaserBladeModel {
    private static RenderType typeHilt;
    private static RenderType typeUnlit;
    private static RenderType typeSubInner;
    private static RenderType typeAdd;
    private static RenderType typeSub;

    public static void resetRenderTypes(ResourceLocation texture) {
        if (ToLaserBlade.canUseFixedVertexBuffer()) {
            typeHilt = LaserBladeRenderType.HILT;
            typeUnlit = LaserBladeRenderType.LASER_UNLIT;
            typeSubInner = LaserBladeRenderType.LASER_SUB_INNER;
            typeAdd = LaserBladeRenderType.LASER_ADD;
            typeSub = LaserBladeRenderType.LASER_SUB;
        } else {
            var textureStateShard = new RenderStateShard.TextureStateShard(texture, false, false);
            typeHilt = LaserBladeRenderType.getBladeRenderType("tlb_hilt",
                    LaserBladeRenderType.getHiltRenderState(textureStateShard));
            typeUnlit = LaserBladeRenderType.getBladeRenderType("tlb_unlit",
                    LaserBladeRenderType.getUnlitRenderState(textureStateShard));
            typeSubInner = LaserBladeRenderType.getBladeRenderType("tlb_sub_in",
                    LaserBladeRenderType.getSubRenderState(textureStateShard));
            typeAdd = LaserBladeRenderType.getBladeRenderType("tlb_add",
                    LaserBladeRenderType.getAddRenderState(textureStateShard));
            typeSub = LaserBladeRenderType.getBladeRenderType("tlb_sub",
                    LaserBladeRenderType.getSubRenderState(textureStateShard));
        }
    }

    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {

    }

    public RenderType getHiltRenderType() {
        return typeHilt;
    }

    public RenderType getUnlitRenderType() {
        return typeUnlit;
    }

    public RenderType getSubInnerRenderType() {
        return typeSubInner;
    }

    public RenderType getAddRenderType() {
        return typeAdd;
    }

    public RenderType getSubRenderType() {
        return typeSub;
    }

    public RenderType getInnerBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubInnerRenderType() : getAddRenderType();
    }

    public RenderType getOuterBladeAddRenderType(boolean isSubColor) {
        return isSubColor ? getSubRenderType() : getAddRenderType();
    }
}
