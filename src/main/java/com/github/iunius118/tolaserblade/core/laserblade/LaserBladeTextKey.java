package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public enum LaserBladeTextKey {
    KEY_ITEM_GROUP_GENERAL("itemGroup.tolaserblade.general"),

    KEY_TOOLTIP_FIREPROOF("upgrade.tolaserblade.fireproof"),
    KEY_TOOLTIP_MODEL("tooltip.tolaserblade.model"),
    KEY_TOOLTIP_ATTACK_DAMAGE("upgrade.tolaserblade.attackDamage"),
    KEY_TOOLTIP_ATTACK_SPEED("upgrade.tolaserblade.attackSpeed"),
    KEY_TOOLTIP_REMOVE("tooltip.tolaserblade.remove"),

    KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_1("tooltip.tolaserblade.brandNew1"),
    KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_2("tooltip.tolaserblade.brandNew2"),
    KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_3("tooltip.tolaserblade.brandNew3"),
    ;

    private final String key;

    LaserBladeTextKey(String translationKey) {
        key = translationKey;
    }

    public String getKey() {
        return key;
    }

    public MutableComponent translate() {
        return Component.translatable(key);
    }

    public MutableComponent translate(Object... additional) {
        return Component.translatable(key, additional);
    }

    @Override
    public String toString() {
        return key;
    }
}
