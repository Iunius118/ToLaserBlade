package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.event.LaserBladeModelRegistrationEvent;
import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

/**
 * ToLaserBlade API
 */
public class ToLaserBladeAPI {
    /**
     * Add a listener of {@link LaserBladeModelRegistrationEvent} to register laser blade models.
     * Only on the {@linkplain LogicalSide#CLIENT logical client}.
     * <p>
     * The following code is an example of usage.
     * </p>
     * <pre>{@code
     * ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(loadMyLaserBladeModels()));
     * }</pre>
     * @param listener Listener of {@link LaserBladeModelRegistrationEvent}.
     */
    public static void registerModelRegistrationListener(Consumer<LaserBladeModelRegistrationEvent> listener) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(listener);
    }

    /**
     * Get the state of a laser blade.
     * @param itemStack Item stack of a laser blade.
     * @return State of the laser blade.
     */
    public static LaserBladeState getLaserBladeState(ItemStack itemStack) {
        return com.github.iunius118.tolaserblade.core.laserblade.LaserBlade.getState(itemStack);
    }
}
