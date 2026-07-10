package com.github.iunius118.tolaserblade.client.gui;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.menu.BlueprintMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BlueprintScreen extends AbstractContainerScreen<BlueprintMenu> {
    // GUI texture
    private static final Identifier TEXTURE = CommonClass.modLocation("textures/gui/blueprint.png");

    public BlueprintScreen(BlueprintMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        // Render background texture
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0F, 0F, this.imageWidth, this.imageHeight, 256, 256);
    }
}
