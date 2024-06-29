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
        typeHilt = LaserBladeRenderType.getHiltRenderType("tlb_hilt", texture)
                .orElse(RenderType.entityTranslucent(texture));
        typeUnlit = LaserBladeRenderType.getUnlitRenderType("tlb_unlit", texture)
                .orElse(RenderType.entityTranslucent(texture));
        typeSubInner = LaserBladeRenderType.getSubRenderType("tlb_sub_in", texture)
                .orElse(RenderType.entityTranslucent(texture));
        typeAdd = LaserBladeRenderType.getAddRenderType("tlb_add", texture)
                .orElse(RenderType.entityTranslucent(texture));
        typeSub = LaserBladeRenderType.getSubRenderType("tlb_sub", texture)
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
