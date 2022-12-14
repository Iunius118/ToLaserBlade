package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.item.LBBrokenItemRenderer;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class LBBrokenItem extends Item implements LaserBladeItemBase {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair();
    public final Upgrade.Type upgradeType = Upgrade.Type.REPAIR;

    public LBBrokenItem(boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
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

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(stack, level, tooltip, flag, upgradeType);
    }
}
