package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {
    public static final Tag<Item> ATTACK_DAMAGE_UPGRADE = makeWrapperTag("upgrade/damage");
    public static final Tag<Item> ATTACK_SPEED_UPGRADE = makeWrapperTag("upgrade/speed");
    public static final Tag<Item> EFFICIENCY_UPGRADE = makeWrapperTag("upgrade/efficiency");
    public static final Tag<Item> LIGHT_ELEMENT_UPGRADE = makeWrapperTag("upgrade/light_element");
    public static final Tag<Item> FIRE_ASPECT_UPGRADE = makeWrapperTag("upgrade/fire_aspect");
    public static final Tag<Item> SWEEPING_EDGE_UPGRADE = makeWrapperTag("upgrade/sweeping_edge");
    public static final Tag<Item> LOOTING_UPGRADE = makeWrapperTag("upgrade/looting");
    public static final Tag<Item> MENDING_UPGRADE = makeWrapperTag("upgrade/mending");

    private static Tag<Item> makeWrapperTag(String id) {
        return new ItemTags.Wrapper(new ResourceLocation(ToLaserBlade.MOD_ID, id));
    }
}
