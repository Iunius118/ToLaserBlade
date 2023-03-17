package com.github.iunius118.tolaserblade.api.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public interface LaserBladeModel {
    default int getID() {
        return -1;
    }

    void render(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);
}
