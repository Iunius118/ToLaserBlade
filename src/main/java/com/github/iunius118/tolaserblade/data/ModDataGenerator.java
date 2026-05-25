package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.pack.RepulsiveForceProvider;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerator {
    public static void gatherData(final GatherDataEvent.Client event) {
        // - Data //
        var builder = new RegistrySetBuilder()
                // Register dynamic data providers
                .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
        event.createDatapackRegistryObjects(builder);
        RepulsiveForceProvider.addProviders(event);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModEnchantmentTagsProvider::new);

        // - Assets //
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(event);
    }
}
