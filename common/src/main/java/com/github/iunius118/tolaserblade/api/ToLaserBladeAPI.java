package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.event.LaserBladeModelRegistrationEvent;

import java.util.function.Consumer;

/**
 * ToLaserBlade API
 */
public class ToLaserBladeAPI {

    /**
     * Add a listener of {@link LaserBladeModelRegistrationEvent} to register laser blade models.
     * Only on the logical client.
     * <p>
     * The following code is an example of usage.
     * </p>
     * <pre>{@code
     * ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(loadMyLaserBladeModels()));
     * }</pre>
     * @param listener Listener of {@link LaserBladeModelRegistrationEvent}.
     */
    public static void registerModelRegistrationListener(Consumer<LaserBladeModelRegistrationEvent> listener) {
        com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager.addEventListener(listener);
    }
}
