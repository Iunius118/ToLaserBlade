package com.github.iunius118.tolaserblade.client.renderer;

import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Render context for {@link LBSwordSpecialRenderer}, used when rendering laser blade.
 */
public class LBSwordRenderContext {
    public final ItemStack itemStack;
    public ItemDisplayContext displayContext = ItemDisplayContext.NONE;

    public LBSwordRenderContext(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
