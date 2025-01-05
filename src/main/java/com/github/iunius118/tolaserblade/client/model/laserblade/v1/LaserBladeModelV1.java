package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

import java.io.Reader;
import java.util.List;

public class LaserBladeModelV1 extends SimpleLaserBladeModel {
    public static final String MODEL_TYPE = "tolaserblade:model_v1";

    private static final ResourceLocation TEXTURE = ToLaserBlade.makeId("textures/item/laser_blade_3d.png");

    private final List<ModelObject> modelObjects;
    private final String name;
    private final int id;
    private final Vector3f guiResize;

    public static LaserBladeModel parseModel(String modelName, Reader resourceReader) {
        return ModelParser.parse(modelName, resourceReader);
    }

    public static void resetRenderTypes() {
        resetRenderTypes(TEXTURE);
    }

    LaserBladeModelV1(String modelName, int modelID, Vector3f resizeInGUI, List<ModelObject> modelObjectList) {
        name = modelName;
        id = modelID;
        guiResize = resizeInGUI;
        modelObjects = modelObjectList;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void render(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (guiResize != null && (mode == ItemDisplayContext.GUI || mode == ItemDisplayContext.FIXED)) {
            // Adjust model size and position in GUI or item frame
            // scale(guiResize.x), and then translate(guiResize.y, guiResize.z)
            matrices.translate(0, guiResize.y(), guiResize.z());
            float size = guiResize.x();
            matrices.scale(size, size, size);
        }

        var laserBladeItemColor = LaserBladeItemColor.of(itemStack);
        var renderingContext = new ModelObject.RenderingContext(itemStack, mode, matrices);
        int pushCount = 0;

        // Process each object
        for (ModelObject modelObject : modelObjects) {
            if (!modelObject.isAvailable(laserBladeItemColor.isBroken()))
                continue;

            if (modelObject.isFunction) {
                // Call function
                pushCount = modelObject.function.invoke(renderingContext, pushCount);
            } else {
                // Render quads
                var vertexConsumer = vertexConsumers.getBuffer(modelObject.renderType.get(this, laserBladeItemColor));
                int color = modelObject.color.get(laserBladeItemColor);
                int lightColorCoordinate = modelObject.getLightColor(light);
                renderQuads(matrices, vertexConsumer, modelObject.quads, color, lightColorCoordinate, overlay);
            }
        }

        // Restore the stack pointer of the pose stack
        for (int i = pushCount; i > 0; i--) {
            matrices.popPose();
        }
    }
}
