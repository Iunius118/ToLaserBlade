package com.github.iunius118.tolaserblade.platform.services;

import com.github.iunius118.tolaserblade.registry.ModObjectRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Creates a registrar for registering objects to given registry.
     *
     * @param registry  The registry to register to
     * @param namespace The namespace for all objects registered to the register
     * @return A registrar for registering objects to a registry
     */
    <V, T extends V> ModObjectRegistry<V, T> createModObjectRegistry(Registry<V> registry, String namespace);

    /**
     * Creates a new creative mode tab builder.
     *
     * @return A creative mode tab builder
     */
    CreativeModeTab.Builder createCreativeModeTabBuilder();
}
