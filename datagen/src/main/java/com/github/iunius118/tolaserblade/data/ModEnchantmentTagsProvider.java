package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
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
                .addOptional(Constants.Enchantments.LASER_BLADE)
                .addOptional(Constants.Enchantments.LIGHT_ELEMENT)
                .addOptional(Constants.Enchantments.REPULSIVE_FORCE);
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE)
                .addOptional(Constants.Enchantments.LIGHT_ELEMENT);
    }
}
