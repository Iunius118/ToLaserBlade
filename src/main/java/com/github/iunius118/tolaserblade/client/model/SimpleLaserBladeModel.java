package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class SimpleLaserBladeModel extends SimpleModel implements LaserBladeModel {
    private static RenderType typeHilt;
    private static RenderType typeUnlit;
    private static RenderType typeSubInner;
    private static RenderType typeAdd;
    private static RenderType typeSub;

    public static void resetRenderTypes(ResourceLocation texture) {
        typeHilt = LaserBladeRenderType.getRenderType("tlb_hilt", LaserBladeRenderType.getHiltRenderState(texture))
                .orElse(RenderType.entityTranslucent(texture));
        typeUnlit = LaserBladeRenderType.getRenderType("tlb_unlit", LaserBladeRenderType.getUnlitRenderState(texture))
                .orElse(RenderType.entityTranslucent(texture));
        typeSubInner = LaserBladeRenderType.getRenderType("tlb_sub_in", LaserBladeRenderType.getSubRenderState(texture))
                .orElse(RenderType.entityTranslucent(texture));
        typeAdd = LaserBladeRenderType.getRenderType("tlb_add", LaserBladeRenderType.getAddRenderState(texture))
                .orElse(RenderType.entityTranslucent(texture));
        typeSub = LaserBladeRenderType.getRenderType("tlb_sub", LaserBladeRenderType.getSubRenderState(texture))
                .orElse(RenderType.entityTranslucent(texture));
    }

    public RenderType getHiltRenderType() {
        return typeHilt;
    }

    public RenderType getUnlitRenderType() {
        return typeUnlit;
    }

    public RenderType getAddRenderType() {
        return typeAdd;
    }

    public RenderType getSubInnerRenderType() {
        return typeSubInner;
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
