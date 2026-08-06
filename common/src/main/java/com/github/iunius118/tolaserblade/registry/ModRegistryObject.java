package com.github.iunius118.tolaserblade.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

/**
 * Common interface for classes holding each registry entry.
 */
public interface ModRegistryObject<T> extends Supplier<T> {

    /**
     * Gets a holder for this entry.
     *
     * @return A holder for this entry.
     */
    Holder<T> getHolder();

    /**
     * Gets the resource key for this entry.
     *
     * @return The resource key for this entry.
     */
    ResourceKey<T> getKey();

    /**
     * Gets whether the object is available.
     *
     * @return True if the underlying object is available.
     */
    boolean isBound();

    /**
     * Gets the object.
     *
     * @return The object.
     */
    T value();

    /**
     * Gets the object.
     *
     * @return The object.
     */
    @Override
    default T get() {
        return value();
    }

    /**
     * Creates an instance of ModRegistryObject from the holder.
     *
     * @param holder A holder for the entry.
     * @return An instance of ModRegistryObject for the holder.
     */
    static <T> ModRegistryObject<T> of(Holder<T> holder) {
        return new ModRegistryObject<>() {

            @Override
            public Holder<T> getHolder() {
                return holder;
            }

            @Override
            public ResourceKey<T> getKey() {
                return holder.unwrapKey().orElseThrow();
            }

            @Override
            public boolean isBound() {
                return holder.isBound();
            }

            @Override
            public T value() {
                return holder.value();
            }
        };
    }
}
