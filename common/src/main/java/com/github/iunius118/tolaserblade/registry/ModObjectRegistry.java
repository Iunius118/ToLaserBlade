package com.github.iunius118.tolaserblade.registry;

import net.minecraft.resources.ResourceKey;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Common interface for classes that register modded objects to the game.
 */
public interface ModObjectRegistry<V, T extends V> {

    /**
     * Adds a new entry to register.
     *
     * @param name The new entry's name.
     * @param object The new entry to register.
     * @return A holder for this entry.
     */
    ModRegistryObject<V> register(String name, Supplier<T> object);

    /**
     * Adds a new entry to register.
     *
     * @param resourceKey The new entry's resource key.
     * @param object The new entry to register.
     * @return A holder for this entry.
     */
    default ModRegistryObject<V> register(ResourceKey<T> resourceKey, Supplier<T> object) {
        return register(resourceKey.identifier().getPath(), object);
    }

    /**
     * Registers the entries registered on Forge-based platforms to the event.
     * This method MUST be called after entry registration has completed.
     */
    void register();

    /**
     * Registers entries using the given registry for bulk registration.
     * After the registration, {@link ModObjectRegistry#register()} is called automatically.
     *
     * @param registry Consumer for invoking the entry registration method.
     */
    default void registerObjects(Consumer<ObjectRegistry<V, T>> registry){
        registry.accept(new ObjectRegistry<>(this));
        register();
    }

    /**
     * Class that provides a method for registering modded object.
     */
    class ObjectRegistry<V, T extends V> {
        private final ModObjectRegistry<V, T> registry;

        ObjectRegistry(ModObjectRegistry<V, T> registry) {
            this.registry = registry;
        }

        /**
         * Adds a new entry to register.
         *
         * @param name The new entry's name.
         * @param object The new entry to register.
         * @return A holder for this entry.
         */
        public ModRegistryObject<V> register(String name, Supplier<T> object) {
            return registry.register(name, object);
        }

        /**
         * Adds a new entry to register.
         *
         * @param resourceKey The new entry's resource key.
         * @param object The new entry to register.
         * @return A holder for this entry.
         */
        public ModRegistryObject<V> register(ResourceKey<T> resourceKey, Supplier<T> object) {
            return registry.register(resourceKey, object);
        }
    }
}
