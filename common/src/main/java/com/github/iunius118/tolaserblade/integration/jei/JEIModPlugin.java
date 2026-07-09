package com.github.iunius118.tolaserblade.integration.jei;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.client.gui.BlueprintScreen;
import com.github.iunius118.tolaserblade.integration.jei.recipe.BlueprintRecipeCategory;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.crafting.ModRecipeManager;
import com.github.iunius118.tolaserblade.menu.BlueprintMenu;
import com.github.iunius118.tolaserblade.menu.ModMenuTypes;
import com.github.iunius118.tolaserblade.platform.Services;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

@JeiPlugin
public class JEIModPlugin implements IModPlugin {
    public static final Identifier UID = CommonClass.modLocation("main");

    public static Phase registrationPhase = Phase.BEFORE_RECIPE_SYNC;
    @Nullable
    public static Runnable recipeRegisters;

    @Nullable
    private IJeiRuntime jeiRuntime;
    @Nullable
    private BlueprintRecipeCategory blueprintCategory;

    @Override
    public Identifier getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        blueprintCategory = new BlueprintRecipeCategory(guiHelper);
        registration.addRecipeCategories(blueprintCategory);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (registrationPhase == Phase.BEFORE_RECIPE_SYNC) {
            // Provide re-registration method, as this may have been called before recipe synchronization
            recipeRegisters = () -> this.registerRecipes(registration);
            return;
        }

        if (blueprintCategory == null
                || ModRecipeManager.getClientSyncedRecipes().values().isEmpty()) {
            return;
        }

        var blueprintRecipeHolders = blueprintCategory.getRecipeHolders();
        registration.addRecipes(BlueprintRecipeCategory.RECIPE_HOLDER_TYPE, blueprintRecipeHolders);
        registrationPhase = Phase.AFTER_REGISTRATION;
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(BlueprintMenu.class, ModMenuTypes.BLUEPRINT,
                BlueprintRecipeCategory.RECIPE_HOLDER_TYPE, 1, 4, 5, 36);

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BlueprintRecipeCategory.RECIPE_HOLDER_TYPE, ModItems.BL_BLUEPRINT);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BlueprintScreen.class, 100, 34, 23, 17,
                BlueprintRecipeCategory.RECIPE_HOLDER_TYPE);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        this.jeiRuntime = jeiRuntime;
    }

    @Override
    public void onRuntimeUnavailable() {
        registrationPhase = Phase.BEFORE_RECIPE_SYNC;
    }

    public JEIModPlugin() {
        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            Constants.LOG.info("JEI plugin ({}) is loaded on {}", getPluginUid(), Services.PLATFORM.getPlatformName());
        }
    }

    public enum Phase {
        BEFORE_RECIPE_SYNC,
        BEFORE_REGISTRATION,
        AFTER_REGISTRATION
    }
}
