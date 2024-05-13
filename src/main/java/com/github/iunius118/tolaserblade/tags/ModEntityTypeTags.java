package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> SENSITIVE_TO_LIGHT_ELEMENT = makeWrapperTag("sensitive_to_light_element");

    private static TagKey<EntityType<?>> makeWrapperTag(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, ToLaserBlade.makeId(id));
    }
}
