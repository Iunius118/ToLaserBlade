package com.github.iunius118.tolaserblade.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class LBPartItem extends Item {
    public final boolean fireResistant;

    public LBPartItem(Properties properties, boolean fireResistant) {
        super(properties);
        this.fireResistant = fireResistant;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);

        if (fireResistant) {
            builder.accept(Component.translatable("tooltip.tolaserblade.fire_resistant")
                    .withStyle(ChatFormatting.GOLD));
        }
    }
}
