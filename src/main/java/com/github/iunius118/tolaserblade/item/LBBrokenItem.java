package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.client.renderer.LBBrokenItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBBrokenItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.MISC).setISTER(() -> LBBrokenItemRenderer::new);

    public LBBrokenItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return type == LaserBladeUpgrade.Type.REPAIR;
    }
}
