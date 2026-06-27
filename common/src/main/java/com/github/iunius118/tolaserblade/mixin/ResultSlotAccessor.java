package com.github.iunius118.tolaserblade.mixin;

import net.minecraft.world.inventory.ResultSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ResultSlot.class, remap = false)
public interface ResultSlotAccessor {

    @Accessor
    void setRemoveCount(int removeCount);
}
