package com.github.iunius118.tolaserblade.mixin;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Item.class, remap = false)
public interface ItemAccessor {
    @Accessor
    DataComponentMap getComponents();

    @Accessor
    @Mutable
    void setComponents(DataComponentMap components);

    @Accessor
    @Mutable
    void setCraftingRemainingItem(Item item);
}
