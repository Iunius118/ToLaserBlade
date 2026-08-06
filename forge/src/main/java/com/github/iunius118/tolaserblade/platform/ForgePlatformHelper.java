package com.github.iunius118.tolaserblade.platform;

import com.github.iunius118.tolaserblade.platform.services.IPlatformHelper;
import com.github.iunius118.tolaserblade.registry.ForgeModObjectRegistry;
import com.github.iunius118.tolaserblade.registry.ModObjectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace) {
        return new ForgeModObjectRegistry<>(DeferredRegister.create(registry.key(), namespace), namespace);
    }

    @Override
    public CreativeModeTab.Builder createCreativeModeTabBuilder() {
        return CreativeModeTab.builder();
    }
}
