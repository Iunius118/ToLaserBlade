package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.data.pack.RepulsiveForceProvider;
import com.github.iunius118.tolaserblade.data.pack.SampleSoundPackProvider;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class ToLaserBlade {
    public static IEventBus modEventBus;

    public ToLaserBlade(IEventBus modEventBus, ModContainer modContainer) {
        ToLaserBlade.modEventBus = modEventBus;

        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello DataGen world!");
        CommonClass.init();

        // Register mod event listeners
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Data
        var builder = new RegistrySetBuilder()
                // Register dynamic data providers
                .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
        event.createDatapackRegistryObjects(builder);
        RepulsiveForceProvider.addProviders(event);
        event.createBlockAndItemTags(ModBlockTagsProvider::new, ModItemTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModEnchantmentTagsProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);

        // Assets
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(event);
        event.createProvider(ModSoundDefinitionsProvider::new);
        SampleSoundPackProvider.addProviders(event);
    }
}
