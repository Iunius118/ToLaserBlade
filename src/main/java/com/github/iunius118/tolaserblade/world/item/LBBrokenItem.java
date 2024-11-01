package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.item.LBBrokenItemRenderer;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class LBBrokenItem extends LBPartItem {
    public LBBrokenItem(Properties properties) {
        super(properties, Upgrade.Type.REPAIR);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        var itemRenderProperties = new IClientItemExtensions() {
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return LBBrokenItemRenderer.INSTANCE;
            }
        };

        consumer.accept(itemRenderProperties);
    }
}
