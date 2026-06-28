package com.github.iunius118.tolaserblade.platform;

import com.github.iunius118.tolaserblade.platform.services.IPlatformHelper;
import com.github.iunius118.tolaserblade.registry.ModObjectRegistry;
import com.github.iunius118.tolaserblade.registry.NeoForgeModObjectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

public class DataGenPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "DataGen";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !Objects.requireNonNull(FMLLoader.getCurrentOrNull()).isProduction();
    }

    @Override
    public <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace) {
        return new NeoForgeModObjectRegistry<>(DeferredRegister.create(registry, namespace), namespace);
    }

    @Override
    public CreativeModeTab.Builder createCreativeModeTabBuilder() {
        return CreativeModeTab.builder();
    }
}
