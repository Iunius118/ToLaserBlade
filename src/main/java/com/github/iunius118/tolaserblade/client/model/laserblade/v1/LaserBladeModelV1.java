package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LaserBladeModelV1 extends SimpleLaserBladeModel {
    public static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelVersion1");

    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/laser_blade_3d.png");
    private static final String MODEL_TYPE = "tolaserblade:model_v1";

    private final List<ModelObjectV1> modelObjects = new ArrayList<>();
    private final String name;
    private final int id;
    private final Vector3f guiResize;

    private LaserBladeModelV1(String modelName, int modelID, Vector3f resizeInGUI) {
        name = modelName;
        id = modelID;
        guiResize = resizeInGUI;
    }

    public static void resetRenderTypes() {
        resetRenderTypes(TEXTURE);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void render(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        RenderingContext args = new RenderingContext(itemStack, mode, matrices);
        int pushCount = 0;

        if (guiResize != null && (mode == ItemDisplayContext.GUI || mode == ItemDisplayContext.FIXED)) {
            // Adjust model size and position in GUI or item frame
            // scale(guiResize.x) -> translate(guiResize.y, guiResize.z)
            matrices.translate(0, guiResize.y(), guiResize.z());
            float size = guiResize.x();
            matrices.scale(size, size, size);
        }

        for (ModelObjectV1 modelObject : modelObjects) {
            if (!modelObject.isVisible(color))
                continue;

            if (modelObject.isFunction()) {
                // Call function
                pushCount = modelObject.callFunction(args, pushCount);
            } else {
                // Render quads
                VertexConsumer currentBuffer = vertexConsumers.getBuffer(modelObject.getRenderType(this, color));
                renderQuads(matrices, currentBuffer, modelObject.getQuads(), modelObject.getPartColor(color), modelObject.getLightColor(light), overlay);
            }
        }

        // Restore the stack pointer of the pose stack
        for (int i = pushCount; i > 0; i--) {
            matrices.popPose();
        }
    }

    public static LaserBladeModel parseModel(String name, Reader reader) {
        JsonModelV1 jsonModel = parseModelFromJson(name, reader);
        return createLaserBladeModel(name, jsonModel);
    }

    private static JsonModelV1 parseModelFromJson(String name, Reader reader) {
        try {
            Gson gson = (new GsonBuilder()).create();
            return GsonHelper.fromJson(gson, reader, JsonModelV1.class);
        } catch(JsonParseException e) {
            LOGGER.warn("Failed to load model: {}\n{}", name, e);
        }

        return null;
    }

    private static LaserBladeModel createLaserBladeModel(String name, JsonModelV1 jsonModel) {
        if (jsonModel == null || !MODEL_TYPE.equals(jsonModel.type) || jsonModel.id < 0) {
            return null;
        }

        Vector3f resizeInGUI = null;

        if (jsonModel.guiResize != null) {
            resizeInGUI = getVector3fFromArray(jsonModel.guiResize);
        }

        List<int[]> faces = new ArrayList<>();

        for (int fi = 0; fi < jsonModel.faces.size(); fi++) {
            int[] face = jsonModel.faces.get(fi);

            if (face.length != 12) {
                LOGGER.warn("Incorrect face: {}:faces/{}", name, fi);
                return null;
            }

            faces.add(face);
        }

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector4f> colors = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();

        for (float[] vertex : jsonModel.vertices) {
            vertices.add(getVector3fFromArray(vertex));
        }

        for (float[] color : jsonModel.colors) {
            colors.add(getVector4fFromArray(color));
        }

        for (float[] normal : jsonModel.normals) {
            normals.add(getVector3fFromArray(normal));
        }

        LaserBladeModelV1 modelV1 = new LaserBladeModelV1(name, jsonModel.id, resizeInGUI);

        for (int oi = 0; oi < jsonModel.objects.size(); oi++) {
            var jsonModelObject = jsonModel.objects.get(oi);
            ModelObjectV1 modelObject = new ModelObjectV1(name + ":objects/" + oi, jsonModelObject, vertices, colors, normals, faces);
            modelV1.modelObjects.add(modelObject);
        }

        return modelV1;
    }

    private static Vector3f getVector3fFromArray(float[] floats) {
        int length = floats.length;
        float[] f = new float[3];

        for (int i = 0; i < 3 && i < length; i++) {
            f[i] = floats[i];
        }

        return new Vector3f(f);
    }

    private static Vector4f getVector4fFromArray(float[] floats) {
        int length = floats.length;
        float[] f = new float[4];

        for (int i = 0; i < 4 && i < length; i++) {
            f[i] = floats[i];
        }

        return new Vector4f(f);
    }

    public record RenderingContext(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices) {}
}
