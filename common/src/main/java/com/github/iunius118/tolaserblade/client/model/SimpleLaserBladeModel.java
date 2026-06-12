package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.renderer.ModRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public abstract class SimpleLaserBladeModel extends SimpleModel implements LaserBladeModel {
    private static RenderType typeHilt;
    private static RenderType typeUnlit;
    private static RenderType typeSubInner;
    private static RenderType typeAdd;
    private static RenderType typeSub;

    public static void resetRenderTypes(Identifier texture) {
        typeHilt = ModRenderTypes.hilt(texture);
        typeUnlit = ModRenderTypes.unlit(texture);
        typeSubInner = ModRenderTypes.subtractive(texture);
        typeAdd = ModRenderTypes.additive(texture);
        typeSub = ModRenderTypes.subtractive(texture);

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

    public RenderType getInnerBladeAddRenderType(boolean isSubColor, boolean isGUI) {
        // When rendering to GUI, always return AddRenderType
        return (isSubColor && !isGUI) ? getSubInnerRenderType() : getAddRenderType();
    }

    public RenderType getOuterBladeAddRenderType(boolean isSubColor, boolean isGUI) {
        // When rendering to GUI, always return AddRenderType
        return (isSubColor && !isGUI) ? getSubRenderType() : getAddRenderType();
    }
}
