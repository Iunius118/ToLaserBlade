package com.github.iunius118.tolaserblade.client.renderer.item.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class LBSwordItemOverrides extends ItemOverrides {
    @Nullable
    @Override
    public BakedModel resolve(BakedModel bakedModel, ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        if (!(bakedModel instanceof LBSwordItemModel laserBladeModel)) return bakedModel;

        var humanoidArm = Minecraft.getInstance().options.mainHand;
        var isBlocking = false;

        if (livingEntity != null) {
            humanoidArm = livingEntity.getMainArm();
            isBlocking = livingEntity.isBlocking();
        }

        return laserBladeModel.handleItemOverride(itemStack, humanoidArm, isBlocking);
    }
}
