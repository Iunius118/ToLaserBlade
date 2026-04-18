package com.github.iunius118.tolaserblade.data;

import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerator {
    public static void gatherData(GatherDataEvent.Client event) {
        // Data
        event.createProvider(ModEnchantmentProvider::new);

        // Assets
        event.createProvider(ModModelProvider::new);
    }
}
