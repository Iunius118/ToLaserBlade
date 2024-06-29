package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Set;

public class TLBDataGenerator {
    public static void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var builtinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), getEntriesBuilder(), Set.of(ToLaserBlade.MOD_ID));
        var lookupProvider = builtinEntriesProvider.getFullRegistries();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new TLBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // Server
        final boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, builtinEntriesProvider);
        dataGenerator.addProvider(includesServer, new TLBRecipeProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(includesServer, blockTagsProvider);
        dataGenerator.addProvider(includesServer, new TLBItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(includesServer, new TLBEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(includesServer, new TLBEnchantmentTagsProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(includesServer, new TLBAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        TLBOldRecipeProvider6.addProviders(event);

        // Client
        final boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new TLBItemModelProvider(packOutput, existingFileHelper));
        TLBLanguageProvider.addProviders(includesClient, dataGenerator, packOutput);
        dataGenerator.addProvider(includesClient, new TLBSoundDefinitionsProvider(packOutput, existingFileHelper));
    }

    private static RegistrySetBuilder getEntriesBuilder() {
        RegistrySetBuilder.RegistryBootstrap<Enchantment> enchantmentRegistry = bootstrapContext -> {
            HolderGetter<Item> itemGetter = bootstrapContext.lookup(Registries.ITEM);
            HolderGetter<Enchantment> enchantmentGetter = bootstrapContext.lookup(Registries.ENCHANTMENT);
            // Register Light Element to bootstrap context
            bootstrapContext.register(ModEnchantments.LIGHT_ELEMENT, LightElementEnchantment.get(itemGetter, enchantmentGetter));
        };
        return new RegistrySetBuilder().add(Registries.ENCHANTMENT, enchantmentRegistry);
    }
}
