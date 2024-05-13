package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.resources.ResourceLocation;

public enum UpgradeID {
    EFFICIENCY_UPGRADE("upgrade/efficiency", "efc"),
    LIGHT_ELEMENT_UPGRADE("upgrade/light_element", "lte"),
    FIRE_ASPECT_UPGRADE("upgrade/fire_aspect", "fra"),
    SWEEPING_EDGE_UPGRADE("upgrade/sweeping_edge", "swp"),
    SILK_TOUCH_UPGRADE("upgrade/silk_touch", "slt"),
    LOOTING_UPGRADE("upgrade/looting", "ltn"),
    MENDING_UPGRADE("upgrade/mending", "mnd"),

    EFFICIENCY_REMOVER("upgrade/efficiency_remover", "efr"),

    ATTACK_DAMAGE_UPGRADE("upgrade/attack", "adm"),
    ATTACK_SPEED_UPGRADE("upgrade/speed", "asp");

    private final ResourceLocation id;
    private final String shortName;

    UpgradeID(String key, String shortName) {
        this.id = ToLaserBlade.makeId(key);
        this.shortName = shortName;
    }

    public ResourceLocation getID() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }
}
