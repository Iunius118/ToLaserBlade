package com.github.iunius118.tolaserblade.client.extensions;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class LBSwordItemExtensions implements IClientItemExtensions {
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        if (itemInHand.getItem() instanceof LBSwordItem) {
            // When laser blade is in player's hand
            var interactionHand = player.getMainArm() == arm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

            if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == interactionHand) {
                // When player is using laser blade (e.g. blocking with laser blade)
                poseStack.translate((arm == HumanoidArm.RIGHT) ? 0.56F : -0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
                return true;
            }

            return false;
        } else {
            return IClientItemExtensions.super.applyForgeHandTransform(poseStack, player, arm, itemInHand, partialTick, equipProcess, swingProcess);
        }
    }
}
