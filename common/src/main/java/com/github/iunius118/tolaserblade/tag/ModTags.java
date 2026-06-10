package com.github.iunius118.tolaserblade.tag;

import com.github.iunius118.tolaserblade.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Blocks {
    }

    public static class Items {
        public static final TagKey<Item> LASER_BLADES = modTag("laser_blades");
        public static final TagKey<Item> LASER_BLADE_ENCHANTABLE = modTag("enchantable/laser_blade");
        // LaserBlade-Tools tag
        public static final TagKey<Item> COLORIZER_CAN_CHANGE_COLOR =
                modTag("laserbladetools", "colorizer_can_change_color");

        private static TagKey<Item> modTag(String id) {
            return TagKey.create(Registries.ITEM, CommonClass.modLocation(id));
        }

        private static TagKey<Item> modTag(String namespace, String path) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, path));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> SENSITIVE_TO_LASER_BLADE = modTag("sensitive_to_laser_blade");

        private static TagKey<EntityType<?>> modTag(String id) {
            return TagKey.create(Registries.ENTITY_TYPE, CommonClass.modLocation(id));
        }
    }
}
