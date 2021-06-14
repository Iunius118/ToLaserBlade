package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.util.text.TranslationTextComponent;

public enum LaserBladeTextKey {
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

    public TranslationTextComponent translate() {
        return new TranslationTextComponent(key);
    }

    public TranslationTextComponent translate(Object... additional) {
        return new TranslationTextComponent(key, additional);
    }

    @Override
    public String toString() {
        return key;
    }
}
