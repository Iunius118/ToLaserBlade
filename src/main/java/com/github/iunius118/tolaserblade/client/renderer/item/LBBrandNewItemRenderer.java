package com.github.iunius118.tolaserblade.client.renderer.item;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

@OnlyIn(Dist.CLIENT)
public class LBBrandNewItemRenderer extends LBSwordItemRenderer {
    public static final LBBrandNewItemRenderer INSTANCE = new LBBrandNewItemRenderer();
    public static final IClientItemExtensions CLIENT_ITEM_EXTENSIONS = new IClientItemExtensions() {
        @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return INSTANCE;
        }
    };
}
