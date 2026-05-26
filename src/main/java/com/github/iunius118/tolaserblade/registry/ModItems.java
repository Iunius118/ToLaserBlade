package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.LBSwordItem;
import com.github.iunius118.tolaserblade.item.ModToolMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ToLaserBlade.MOD_ID);

    public static final DeferredItem<Item> LASER_BLADE =
            ITEMS.registerItem("laser_blade", p -> new LBSwordItem(p, false), () ->
                    ModToolMaterials.applyLaserBladeProperties(new Item.Properties(), false, 3F, -2.4F, 0F)
            );
    public static final DeferredItem<Item> LASER_BLADE_FP =
            ITEMS.registerItem("laser_blade_fp", p -> new LBSwordItem(p, true), () ->
                    ModToolMaterials.applyLaserBladeProperties(new Item.Properties(), true, 3F, -2.4F, 0F)
            );
    public static final DeferredItem<Item> LB_BATTERY = 
            ITEMS.registerSimpleItem("lb_battery", Item.Properties::new);
    public static final DeferredItem<Item> LB_MEDIUM = 
            ITEMS.registerSimpleItem("lb_medium", Item.Properties::new);
    public static final DeferredItem<Item> LB_EMITTER = 
            ITEMS.registerSimpleItem("lb_emitter", Item.Properties::new);
    public static final DeferredItem<Item> LB_CASING = 
            ITEMS.registerSimpleItem("lb_casing", Item.Properties::new);
    public static final DeferredItem<Item> LB_CASING_FP =
            ITEMS.registerItem("lb_casing_fp", Item::new, () -> new Item.Properties().fireResistant());

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
