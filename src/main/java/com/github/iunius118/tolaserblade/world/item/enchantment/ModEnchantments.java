package com.github.iunius118.tolaserblade.world.item.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> LIGHT_ELEMENT = ResourceKey.create(Registries.ENCHANTMENT, LightElementEnchantment.ID);
}
