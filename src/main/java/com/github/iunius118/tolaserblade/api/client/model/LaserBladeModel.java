package com.github.iunius118.tolaserblade.api.client.model;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Define and render a model of laser blade.
 * This interface is implemented to register an additional model of laser blade.
 *
 * @see ToLaserBladeAPI#registerModelRegistrationListener
 */
public interface LaserBladeModel {
    /**
     * Return a number to identify model type of laser blade.
     *
     * @return Non-negative integer to identify model type. If a negative integer is returned, the model registration will fail. If this value duplicates an already registered model, the model will be overwritten with this model.
     */
    default int getID() {
        return -1;
    }

    /**
     * Render this model into world or GUI.
     * This method will be called from the laser blade item renderer. The method of rendering the model is left to the implementer.
     *
     * @param itemStack       Item stack of the laser blade to be rendered.
     * @param mode            Transformation type of the rendering.
     * @param matrices        Transformation matrices set at the rendering position of the laser blade.
     * @param vertexConsumers Get the VertexConsumer you want to use from this object.
     * @param light           Brightness.
     * @param overlay         Overlaid color.
     */
    void render(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay);
}
