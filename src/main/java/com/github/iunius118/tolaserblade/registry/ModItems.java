package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ToLaserBlade.MOD_ID);

    public static final DeferredItem<Item> LASER_BLADE = ITEMS.registerSimpleItem("laser_blade", Item.Properties::new);
    public static final DeferredItem<Item> LB_BATTERY = ITEMS.registerSimpleItem("lb_battery", Item.Properties::new);
    public static final DeferredItem<Item> LB_MEDIUM = ITEMS.registerSimpleItem("lb_medium", Item.Properties::new);
    public static final DeferredItem<Item> LB_EMITTER = ITEMS.registerSimpleItem("lb_emitter", Item.Properties::new);
    public static final DeferredItem<Item> LB_CASING = ITEMS.registerSimpleItem("lb_casing", Item.Properties::new);
}
