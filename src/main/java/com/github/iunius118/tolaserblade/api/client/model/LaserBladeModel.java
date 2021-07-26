package com.github.iunius118.tolaserblade.api.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface LaserBladeModel {
    void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);

    ResourceLocation getTexture();
}
