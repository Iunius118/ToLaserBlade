package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

class ModelParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelVersion1");

    private ModelParser() {}

    public static LaserBladeModel parse(String modelName, Reader resourceReader) {
        JsonModelV1 jsonModel = parseModelFromJson(modelName, resourceReader);
        return createLaserBladeModel(modelName, jsonModel);
    }

    private static JsonModelV1 parseModelFromJson(String modelName, Reader reader) {
        try {
            Gson gson = (new GsonBuilder()).create();
            return GsonHelper.fromJson(gson, reader, JsonModelV1.class);
        } catch (JsonParseException e) {
            LOGGER.warn("Failed to load model: {}\n{}", modelName, e);
        }

        return null;
    }

    private static LaserBladeModelV1 createLaserBladeModel(String modelName, JsonModelV1 jsonModel) {
        if (jsonModel == null || !LaserBladeModelV1.MODEL_TYPE.equals(jsonModel.type) || jsonModel.id < 0) {
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
                LOGGER.warn("Incorrect face: {}:faces/{}", modelName, fi);
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

        List<ModelObject> modelObjectList = new ArrayList<>();

        for (int oi = 0; oi < jsonModel.objects.size(); oi++) {
            var jsonModelObject = jsonModel.objects.get(oi);
            var modelObject = new ModelObject(modelName + ":objects/" + oi, jsonModelObject, vertices, colors, normals, faces);
            modelObjectList.add(modelObject);
        }

        return new LaserBladeModelV1(modelName, jsonModel.id, resizeInGUI, modelObjectList);
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
}
