package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LaserBladeModelV1 extends SimpleLaserBladeModel {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ToLaserBlade.MOD_ID, "textures/item/laser_blade_3d.png");
    private static final Logger LOGGER = ToLaserBlade.LOGGER;
    private static final String MODEL_TYPE = "tolaserblade:model_v1";

    private final List<ModelObject> modelObjects = new ArrayList<>();
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
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        LaserBladeItemColor color = new LaserBladeItemColor(itemStack);
        Arguments args = new Arguments(itemStack, mode, matrices);
        int pushCount = 0;

        if (guiResize != null && (mode == ItemTransforms.TransformType.GUI || mode == ItemTransforms.TransformType.FIXED)) {
            // Adjust model size and position to GUI or item frame
            // scale(guiResize.x) -> translate(guiResize.y, guiResize.z)
            matrices.translate(0, guiResize.y(), guiResize.z());
            float size = guiResize.x();
            matrices.scale(size, size, size);
        }

        for (ModelObject modelObject : modelObjects) {
            if (!modelObject.isVisible(color))
                continue;

            if (modelObject.isFunction()) {
                // Function
                pushCount = modelObject.callFunction(args, pushCount);
                continue;
            }

            // Render quads
            VertexConsumer currentBuffer = vertexConsumers.getBuffer(modelObject.getRenderType(this, color));
            renderQuads(matrices, currentBuffer, modelObject.getQuads(), modelObject.getPartColor(color), modelObject.getLightColor(light), overlay);
        }

        for (int i = pushCount; i > 0; i--) {
            matrices.popPose();
        }
    }

    public static LaserBladeModel parseModel(String name, Reader reader) {
        JsonModelV1 jsonModel = parseFromJson(name, reader);
        return getLaserBladeModel(name, jsonModel);
    }

    private static JsonModelV1 parseFromJson(String name, Reader reader) {
        try {
            Gson gson = (new GsonBuilder()).create();
            return GsonHelper.fromJson(gson, reader, JsonModelV1.class);
        } catch(JsonParseException e) {
            LOGGER.warn("Failed to load model: {}\n{}", name, e);
        }

        return null;
    }

    private static LaserBladeModel getLaserBladeModel(String name, JsonModelV1 jsonModel) {
        if (jsonModel == null || !MODEL_TYPE.equals(jsonModel.type) || jsonModel.id < 0) {
            // Not for me
            return null;
        }

        // Get gui_resize
        Vector3f resizeInGUI = null;

        if (jsonModel.guiResize != null) {
            resizeInGUI = getVector3fFromArray(jsonModel.guiResize);
        }

        // Get faces
        List<int[]> faces = new ArrayList<>();

        for (int fi = 0; fi < jsonModel.faces.size(); fi++) {
            int[] face = jsonModel.faces.get(fi);

            if (face.length != 12) {
                LOGGER.warn("Incorrect face: {}:faces/{}", name, fi);
                return null;
            }

            faces.add(face);
        }

        // Get vertices, colors, and normals
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

        // Get objects
        for (int oi = 0; oi < jsonModel.objects.size(); oi++) {
            var jsonModelObject = jsonModel.objects.get(oi);
            ModelObject modelObject = new ModelObject(name + ":objects/" + oi, jsonModelObject, vertices, colors, normals, faces);
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

    public static class ModelObject {
        private final static Vector2f FIXED_UV = new Vector2f(0, 0);

        private boolean isFunction = false;
        private BiFunction<Arguments, Integer, Integer> fnCallFunction = FunctionsV1.FN_NOP;
        private Predicate<LaserBladeItemColor> fnIsVisible = FunctionsV1.IS_ANY_STATE;
        private BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> fnGetRenderType = FunctionsV1.GET_DEFAULT_RENDERER;
        private Function<LaserBladeItemColor, Integer> fnGetPartColor = FunctionsV1.GET_DEFAULT_COLOR;
        private Function<Integer, Integer> fnGetLightColor = FunctionsV1.GET_GIVEN_LIGHT;
        private List<SimpleQuad> quads = Collections.emptyList();

        public ModelObject(String objectName, JsonModelV1.JsonModelObject object, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces) {
            initRenderFunctions(object);

            if (!isFunction) {
                initQuads(objectName, vertices, colors, normals, faces, object);
            }
        }

        private void initRenderFunctions(JsonModelV1.JsonModelObject object) {
            final String type = object.type;
            final String part = object.part;

            // Types and lights
            switch (type) {
                case "default" -> {}
                case "unlit" -> {
                    fnGetRenderType = FunctionsV1.GET_UNLIT_RENDERER;
                    fnGetLightColor = FunctionsV1.GET_FULL_LIGHT;
                }
                case "add" -> {
                    if (part.equals("blade_in")) {
                        fnGetRenderType = FunctionsV1.GET_INNER_ADD_RENDERER;
                    } else {
                        fnGetRenderType = FunctionsV1.GET_OUTER_ADD_RENDERER;
                    }
                    fnGetLightColor = FunctionsV1.GET_FULL_LIGHT;
                }
                case "flat" -> {
                    fnGetRenderType = FunctionsV1.GET_DEFAULT_RENDERER;
                    fnGetLightColor = FunctionsV1.GET_FULL_LIGHT;
                }
                case "function" -> {
                    isFunction = true;
                }
                default -> {
                    fnGetRenderType = FunctionsV1.GET_DEFAULT_RENDERER;
                    fnGetLightColor = FunctionsV1.GET_GIVEN_LIGHT;
                }
            }

            // Functions
            if (isFunction) {
                switch (object.name) {
                    case "rotate" -> fnCallFunction = FunctionsV1.FN_ROTATE;
                    case "shield" -> fnCallFunction = FunctionsV1.FN_SHIELD;
                    case "pop_pose" -> fnCallFunction = FunctionsV1.FN_POP_POSE;
                    default -> fnCallFunction = FunctionsV1.FN_NOP;
                }
            }

            // Parts
            switch (part) {
                case "" -> {}
                case "grip" -> fnGetPartColor = FunctionsV1.GET_GRIP_COLOR;
                case "off" -> fnGetPartColor = FunctionsV1.GET_OFF_COLOR;
                case "blade_in" -> {
                    if (type.equals("add")) {
                        fnGetPartColor = FunctionsV1.GET_INNER_ADD_COLOR;
                    } else {
                        fnGetPartColor = FunctionsV1.GET_INNER_DEFAULT_COLOR;
                    }
                }
                case "blade_out" -> {
                    if (type.equals("add")) {
                        fnGetPartColor = FunctionsV1.GET_OUTER_ADD_COLOR;
                    } else {
                        fnGetPartColor = FunctionsV1.GET_OUTER_DEFAULT_COLOR;
                    }
                }
                default -> fnGetPartColor = FunctionsV1.GET_DEFAULT_COLOR;
            }

            // States
            final String state = object.state;

            switch (state) {
                case "any" -> {}
                case "on" -> fnIsVisible = FunctionsV1.IS_WORKING;
                case "off" -> fnIsVisible = FunctionsV1.IS_BROKEN;
                default -> fnIsVisible = FunctionsV1.IS_ANY_STATE;
            }
        }

        private void initQuads(String objectName, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces, JsonModelV1.JsonModelObject object) {
            List<SimpleQuad> simpleQuads = new ArrayList<>();
            final int from = object.from;
            final int size = object.size;

            if (from < 0 || size < 0 || faces.size() < from + size) {
                LOGGER.warn("Incorrect range specified: {}", objectName);
                return;
            }

            for (int fi = from; fi < from + size; fi++) {
                int[] face = faces.get(fi);

                // Check if indices are within bounds
                for (int i = 0; i < 12; i += 3) {
                    if (face[i] < 0 || face[i] >= vertices.size()) {
                        warnIndexOutOfBounds(objectName, fi, i);
                        return;
                    } else if (face[i + 1] < 0 || face[i + 1] >= colors.size()) {
                        warnIndexOutOfBounds(objectName, fi, i + 1);
                        return;
                    } else if (face[i + 2] < 0 || face[i + 2] >= normals.size()) {
                        warnIndexOutOfBounds(objectName, fi, i + 2);
                        return;
                    }
                }

                SimpleQuad quad = new SimpleQuad(
                        vertices.get(face[0]), colors.get(face[1]), FIXED_UV, normals.get(face[2]),
                        vertices.get(face[3]), colors.get(face[4]), FIXED_UV, normals.get(face[5]),
                        vertices.get(face[6]), colors.get(face[7]), FIXED_UV, normals.get(face[8]),
                        vertices.get(face[9]), colors.get(face[10]), FIXED_UV, normals.get(face[11])
                );
                simpleQuads.add(quad);
            }

            quads = simpleQuads;
        }

        private void warnIndexOutOfBounds(String objectName, int faceIndex, int elementIndex) {
            LOGGER.warn("Index is out of bounds: {}faces/{}/{}" + objectName, faceIndex, elementIndex);
        }

        public boolean isFunction() {
            return isFunction;
        }

        public int callFunction(Arguments args, int stackIndex) {
            return fnCallFunction.apply(args, stackIndex);
        }

        public boolean isVisible(LaserBladeItemColor itemColor) {
            return fnIsVisible.test(itemColor);
        }

        public RenderType getRenderType(SimpleLaserBladeModel model, LaserBladeItemColor itemColor) {
            return fnGetRenderType.apply(model, itemColor);
        }

        public int getPartColor(LaserBladeItemColor itemColor) {
            return fnGetPartColor.apply(itemColor);
        }

        public int getLightColor(int lightColor) {
            return fnGetLightColor.apply(lightColor);
        }

        public List<SimpleQuad> getQuads() {
            return quads;
        }
    }

    public record Arguments(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack matrices) {}
}
