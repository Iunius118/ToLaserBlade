package com.github.iunius118.tolaserblade.mixin;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Accessor
    int getLitTimeRemaining();

    @Accessor
    void setLitTimeRemaining(int litTimeRemaining);

    @Accessor
    void setLitTotalTime(int litTotalTime);
}
