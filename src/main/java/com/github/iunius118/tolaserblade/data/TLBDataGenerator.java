package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.world.item.enchantment.LightElementEnchantment;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public class TLBDataGenerator {
    public static void gatherData(final GatherDataEvent.Client event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var builtinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), getEntriesBuilder(), Set.of(ToLaserBlade.MOD_ID));
        var lookupProvider = builtinEntriesProvider.getRegistryProvider();
        var blockTagsProvider = new TLBBlockTagsProvider(packOutput, lookupProvider);

        // Data
        event.addProvider(builtinEntriesProvider);
        event.addProvider(new TLBRecipeProvider.Runner(packOutput, lookupProvider));
        event.addProvider(blockTagsProvider);
        event.addProvider(new TLBItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
        event.addProvider(new TLBEntityTypeTagsProvider(packOutput, lookupProvider));
        event.addProvider(new TLBEnchantmentTagsProvider(packOutput, lookupProvider));
        event.addProvider(new TLBAdvancementProvider(packOutput, lookupProvider));
        TLBOldRecipeProvider6.addProviders(event);

        // Assets
        event.addProvider(new TLBModelProvider(packOutput));
        TLBLanguageProvider.addProviders(event);
        event.addProvider(new TLBSoundDefinitionsProvider(packOutput));
    }

    private static RegistrySetBuilder getEntriesBuilder() {
        RegistrySetBuilder.RegistryBootstrap<Enchantment> enchantmentRegistry = bootstrapContext -> {
            HolderGetter<Item> itemGetter = bootstrapContext.lookup(Registries.ITEM);
            HolderGetter<Enchantment> enchantmentGetter = bootstrapContext.lookup(Registries.ENCHANTMENT);
            HolderGetter<EntityType<?>> entityTypeHolderGetter = bootstrapContext.lookup(Registries.ENTITY_TYPE);
            // Register Light Element to bootstrap context
            bootstrapContext.register(ModEnchantments.LIGHT_ELEMENT, LightElementEnchantment.get(itemGetter, enchantmentGetter, entityTypeHolderGetter));
        };
        return new RegistrySetBuilder().add(Registries.ENCHANTMENT, enchantmentRegistry);
    }
}
