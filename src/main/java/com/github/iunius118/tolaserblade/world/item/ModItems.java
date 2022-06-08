package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;

public class ModItems {
    // DX Laser B1ade
    public static final Item DX_LASER_BLADE = new DXLaserBladeItem();
    // Laser Blade
    public static final Item LASER_BLADE = new LBSwordItem(false);
    public static final Item LASER_BLADE_FP = new LBSwordItem(true);
    // Brand-new Laser Blade
    public static final Item LB_BRAND_NEW = new LBBrandNewItem(LBBrandNewType.NONE, false);
    public static final Item LB_BRAND_NEW_1 = new LBBrandNewItem(LBBrandNewType.LIGHT_ELEMENT_1, false);
    public static final Item LB_BRAND_NEW_2 = new LBBrandNewItem(LBBrandNewType.LIGHT_ELEMENT_2, false);
    public static final Item LB_BRAND_NEW_FP = new LBBrandNewItem(LBBrandNewType.FP, true);
    public static final Item LB_BROKEN = new LBBrokenItem(false);
    public static final Item LB_BROKEN_FP = new LBBrokenItem(true);
    // Blueprint
    public static final Item LB_BLUEPRINT = new LBBlueprintItem();
    // Laser Blade parts
    public static final Item LB_DISASSEMBLED = new LBDisassembledItem(false);
    public static final Item LB_DISASSEMBLED_FP = new LBDisassembledItem(true);
    public static final Item LB_BATTERY = new LBBatteryItem();
    public static final Item LB_MEDIUM = new LBMediumItem();
    public static final Item LB_EMITTER = new LBEmitterItem();
    public static final Item LB_CASING = new LBCasingItem(false);
    public static final Item LB_CASING_FP = new LBCasingItem(true);
}
