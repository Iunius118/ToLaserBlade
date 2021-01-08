/*
 * This is a dummy class to temporarily fix a bug in OptiFine's Dynamic Lights.
 */

package net.minecraftforge.fml.common;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class Loader {
    private static final Loader INSTANCE = new Loader();

    public static Loader instance() {
        ToLaserBlade.LOGGER.info("Dummy Loader class has been loaded.");
        return INSTANCE;
    }

    public List<ModContainer> getActiveModList() {
        List<ModContainer> modList = new ArrayList<>();
        ModList.get().getMods().forEach(modInfo -> modList.add(new ModContainer(modInfo.getModId())));
        return modList;
    }
}
