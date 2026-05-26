package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> LASER_BLADES = makeWrapperTag("laser_blades");
    public static final TagKey<Item> LASER_BLADE_ENCHANTABLE = makeWrapperTag("enchantable/laser_blade");

    public static final TagKey<Item> COLORIZER_CAN_CHANGE_COLOR =
            ItemTags.create(Identifier.fromNamespaceAndPath("laserbladetools", "colorizer_can_change_color"));

    private static TagKey<Item> makeWrapperTag(String id) {
        return ItemTags.create(ToLaserBlade.id(id));
    }
}
