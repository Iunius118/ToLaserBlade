package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;

public class LaserBladeBlocking {
    public static boolean isShield() {
        return TLBServerConfig.isEnabledBlockingWithLaserBlade;
    }

    public static UseAnim getUseAction() {
        if (isShield()) {
            return UseAnim.BLOCK;
        } else {
            return UseAnim.NONE;
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
    public static void start(Player player, InteractionHand hand) {
        if (isShield()) {
            UseAnim offhandItemAction = player.getOffhandItem().getUseAnimation();

            if (offhandItemAction != UseAnim.BOW && offhandItemAction != UseAnim.SPEAR) {
                player.startUsingItem(hand);
            }
        }
    }
}
