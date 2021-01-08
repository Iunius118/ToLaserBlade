/*
 * This is a dummy class to temporarily fix a bug in OptiFine's Dynamic Lights.
 */

package net.minecraftforge.fml.common;

public class ModContainer {
    private final String modId;

    public ModContainer(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }
}
