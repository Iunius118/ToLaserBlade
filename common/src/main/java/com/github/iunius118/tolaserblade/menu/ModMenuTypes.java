package com.github.iunius118.tolaserblade.menu;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<BlueprintMenu> BLUEPRINT =
            new MenuType<>(BlueprintMenu::new, FeatureFlags.DEFAULT_FLAGS);
}
