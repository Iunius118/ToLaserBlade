package com.github.iunius118.tolaserblade.registry;

import net.minecraft.core.Holder;

import java.util.function.BiFunction;
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
    Holder<V> register(String name, Supplier<T> object);

    /**
     * Registers entries using the given method for bulk registration.
     *
     * @param registry Consumer for invoking the entry registration method.
     * @return This instance.
     */
    ModObjectRegistry<V, T> registerObjects(Consumer<BiFunction<String, Supplier<T>, Holder<V>>> registry);

    /**
     * Registers the entries registered on Forge-based platforms to the event.
     * This method MUST be called after entry registration has completed.
     */
    void register();
}
