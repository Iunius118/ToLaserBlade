package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;

public class LaserBladeBlocking {
    public static boolean isShield() {
        return ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get();
    }

    public static UseAction getUseAction() {
        if (isShield()) {
            return UseAction.BLOCK;
        } else {
            return UseAction.NONE;
        }
    }

    public static int getUseDuration() {
        if (isShield()) {
            // 1 hour
            return 72000;
        } else {
            return 0;
        }
    }
    public static void start(PlayerEntity player, Hand hand) {
        if (isShield()) {
            UseAction offhandItemAction = player.getOffhandItem().getUseAnimation();

            if (offhandItemAction != UseAction.BOW && offhandItemAction != UseAction.SPEAR) {
                player.startUsingItem(hand);
            }
        }
    }
}
