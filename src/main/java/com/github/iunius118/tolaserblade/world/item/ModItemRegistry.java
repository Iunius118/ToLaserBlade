package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModItemRegistry {
    private static final DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, ToLaserBlade.MOD_ID);

    // DX Laser B1ade
    public static final RegistryObject<Item> DX_LASER_BLADE = registerItem("dx_laser_blade", DXLaserBladeItem::new);
    // Laser Blade
    public static final RegistryObject<Item> LASER_BLADE = registerLBSwordItem("laser_blade", false);
    public static final RegistryObject<Item> LASER_BLADE_FP = registerLBSwordItem("laser_blade_fp", true);
    // Brand-new Laser Blade
    public static final RegistryObject<Item> LB_BRAND_NEW = registerLBBrandNewItem("lb_brand_new", LBBrandNewType.NONE, false);
    public static final RegistryObject<Item> LB_BRAND_NEW_1 = registerLBBrandNewItem("lb_brand_new_1", LBBrandNewType.LIGHT_ELEMENT_1, false);
    public static final RegistryObject<Item> LB_BRAND_NEW_2 = registerLBBrandNewItem("lb_brand_new_2", LBBrandNewType.LIGHT_ELEMENT_2, false);
    public static final RegistryObject<Item> LB_BRAND_NEW_FP = registerLBBrandNewItem("lb_brand_new_fp", LBBrandNewType.FP, true);
    public static final RegistryObject<Item> LB_BROKEN = registerItem("lb_broken", LBBrokenItem::new);
    public static final RegistryObject<Item> LB_BROKEN_FP = registerFireResistantItem("lb_broken_fp", LBBrokenItem::new);
    // Blueprint
    public static final RegistryObject<Item> LB_BLUEPRINT = registerItem("lb_blueprint", LBBlueprintItem::new);
    // Laser Blade parts
    public static final RegistryObject<Item> LB_DISASSEMBLED = registerItem("lb_disassembled", LBDisassembledItem::new);
    public static final RegistryObject<Item> LB_DISASSEMBLED_FP = registerFireResistantItem("lb_disassembled_fp", LBDisassembledItem::new);
    public static final RegistryObject<Item> LB_BATTERY = registerLBPartItem("lb_battery", Upgrade.Type.BATTERY, false);
    public static final RegistryObject<Item> LB_MEDIUM = registerLBPartItem("lb_medium", Upgrade.Type.MEDIUM, false);
    public static final RegistryObject<Item> LB_EMITTER = registerLBPartItem("lb_emitter", Upgrade.Type.EMITTER, false);
    public static final RegistryObject<Item> LB_CASING = registerLBPartItem("lb_casing", Upgrade.Type.CASING, false);
    public static final RegistryObject<Item> LB_CASING_FP = registerLBPartItem("lb_casing_fp", Upgrade.Type.CASING, true);

    private static ResourceKey<Item> modItemId(String name) {
        return ResourceKey.create(Registries.ITEM, ToLaserBlade.makeId(name));
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> func) {
        return registerItem(name, func, new Item.Properties());
    }

    private static RegistryObject<Item> registerFireResistantItem(String name, Function<Item.Properties, Item> func) {
        return registerItem(name, func, new Item.Properties().fireResistant());
    }

    private static RegistryObject<Item> registerLBSwordItem(String name, boolean isFireResistant) {
        return registerItem(name, p -> new LBSwordItem(p, isFireResistant),
                isFireResistant ? new Item.Properties().fireResistant() : new Item.Properties());
    }

    private static RegistryObject<Item> registerLBBrandNewItem(String name, LBBrandNewType type, boolean isFireResistant) {
        return registerItem(name, p -> new LBBrandNewItem(p, type),
                isFireResistant ? new Item.Properties().fireResistant() : new Item.Properties());
    }

    private static RegistryObject<Item> registerLBPartItem(String name, Upgrade.Type type, boolean isFireResistant) {
        return registerItem(name, p -> new LBPartItem(p, type),
                isFireResistant ? new Item.Properties().fireResistant() : new Item.Properties());
    }

    private static RegistryObject<Item> registerItem(String name, Function<Item.Properties, Item> func, Item.Properties properties) {
        return itemRegister.register(name, () -> func.apply(properties.setId(modItemId(name))));
    }

    public static void register(IEventBus modEventBus) {
        ToLaserBlade.LOGGER.debug("Register mod items");
        itemRegister.register(modEventBus);
    }
}
