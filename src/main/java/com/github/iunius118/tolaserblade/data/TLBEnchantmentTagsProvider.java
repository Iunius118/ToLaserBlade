package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class TLBEnchantmentTagsProvider extends EnchantmentTagsProvider {
    public TLBEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(EnchantmentTags.DAMAGE_EXCLUSIVE).add(ModEnchantments.LIGHT_ELEMENT);
        tag(EnchantmentTags.TOOLTIP_ORDER).add(ModEnchantments.LIGHT_ELEMENT);
    }
}