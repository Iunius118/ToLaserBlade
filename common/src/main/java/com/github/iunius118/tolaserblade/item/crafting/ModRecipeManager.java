package com.github.iunius118.tolaserblade.item.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.item.crafting.RecipeMap;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class ModRecipeManager {
    @Nullable
    private static ClientSyncedRecipes clientSyncedRecipes;
    public static Phase registrationPhase = Phase.BEFORE_RECIPE_SYNC;
    @Nullable
    public static Runnable recipeRegister;

    public static void setClientSyncedRecipes(RecipeMap syncedRecipes) {
        var connectionId = getRemoteConnectionId();
        clientSyncedRecipes = new ClientSyncedRecipes(syncedRecipes, connectionId);
        registrationPhase = Phase.BEFORE_REGISTRATION;

        if (recipeRegister != null) {
            // If JEIModPlugin.registerRecipes() is called before recipe synchronization, register them again
            recipeRegister.run();
            recipeRegister = null;
        }
    }

    public static RecipeMap getClientSyncedRecipes() {
        if (clientSyncedRecipes != null) {
            var connectionId = getRemoteConnectionId();

            if (clientSyncedRecipes.connectionId().equals(connectionId)) {
                return clientSyncedRecipes.recipeMap();
            }
        }

        return RecipeMap.EMPTY;
    }

    @Nullable
    private static String getRemoteConnectionId() {
        return Optional.ofNullable(Minecraft.getInstance().getConnection())
                .map(ClientPacketListener::getConnection)
                .map(c -> c.isConnected() ? c.getLoggableAddress(true) : null)
                .orElse(null);
    }

    record ClientSyncedRecipes(RecipeMap recipeMap, String connectionId) {
    }

    public enum Phase {
        BEFORE_RECIPE_SYNC,
        BEFORE_REGISTRATION,
        AFTER_REGISTRATION
    }
}
