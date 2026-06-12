package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.data.pack.RepulsiveForceProvider;
import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.registry.DataGenModRegistries;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class ToLaserBlade {

    public ToLaserBlade(IEventBus modEventBus, ModContainer modContainer) {
        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello DataGen world!");
        CommonClass.init();

        // Register mod event listeners
        DataGenModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Data
        var builder = new RegistrySetBuilder()
                // Register dynamic data providers
                .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
        event.createDatapackRegistryObjects(builder);
        RepulsiveForceProvider.addProviders(event);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModEnchantmentTagsProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);

        // Assets
        event.createProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(event);
    }
}
