package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ModItemTags {
    public static final ITag.INamedTag<Item> ATTACK_SPEED_UPGRADE = makeWrapperTag("upgrade/speed");
    public static final ITag.INamedTag<Item> EFFICIENCY_UPGRADE = makeWrapperTag("upgrade/efficiency");
    public static final ITag.INamedTag<Item> EFFICIENCY_REMOVER = makeWrapperTag("upgrade/efficiency_remover");

    public static final ITag.INamedTag<Item> ATTACK_DAMAGE_UPGRADE = makeWrapperTag("upgrade/damage");
    public static final ITag.INamedTag<Item> LIGHT_ELEMENT_UPGRADE = makeWrapperTag("upgrade/light_element");

    public static final ITag.INamedTag<Item> FIRE_ASPECT_UPGRADE = makeWrapperTag("upgrade/fire_aspect");
    public static final ITag.INamedTag<Item> SWEEPING_EDGE_UPGRADE = makeWrapperTag("upgrade/sweeping_edge");

    public static final ITag.INamedTag<Item> LOOTING_UPGRADE = makeWrapperTag("upgrade/looting");
    public static final ITag.INamedTag<Item> MENDING_UPGRADE = makeWrapperTag("upgrade/mending");
    public static final ITag.INamedTag<Item> FIREPROOF_UPGRADE = makeWrapperTag("upgrade/fireproof");

    public static final ITag.INamedTag<Item> CASING_REPAIR = makeWrapperTag("casing_repair");

    private static ITag.INamedTag<Item> makeWrapperTag(String id) {
        return ItemTags.makeWrapperTag(ToLaserBlade.MOD_ID + ":" + id);
    }
}
