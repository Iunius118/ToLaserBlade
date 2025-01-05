package com.github.iunius118.tolaserblade.client.renderer.item.properties;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record UsingOriginalModel() implements ConditionalItemModelProperty {
    public static final MapCodec<UsingOriginalModel> MAP_CODEC = MapCodec.unit(new UsingOriginalModel());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext context) {
        return LaserBladeModelManager.getInstance().canUseOriginalModelType();
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
