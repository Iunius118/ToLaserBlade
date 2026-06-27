package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagsProvider extends EnchantmentTagsProvider {

    public ModEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(EnchantmentTags.TOOLTIP_ORDER)
                .addOptional(ModEnchantments.LASER_BLADE)
                .addOptional(ModEnchantments.LIGHT_ELEMENT)
                .addOptional(ModEnchantments.REPULSIVE_FORCE);
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE)
                .addOptional(ModEnchantments.LIGHT_ELEMENT);
    }
}
