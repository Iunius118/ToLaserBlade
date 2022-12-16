package com.github.iunius118.tolaserblade.client.model.laserblade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
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
                pushCount = modelObject.callFunction(matrices, pushCount);
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
        JsonModel jsonModel = parseFromJson(name, reader);
        return getLaserBladeModel(name, jsonModel);
    }

    private static JsonModel parseFromJson(String name, Reader reader) {
        try {
            Gson gson = (new GsonBuilder()).create();
            return GsonHelper.fromJson(gson, reader, JsonModel.class);
        } catch(JsonParseException e) {
            LOGGER.warn("Failed to load model: {}\n{}", name, e);
        }

        return null;
    }

    private static LaserBladeModel getLaserBladeModel(String name, JsonModel jsonModel) {
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
        private final static BiFunction<PoseStack, Integer, Integer> FN_NOP = (m, i) -> i;
        private final static BiFunction<PoseStack, Integer, Integer> FN_ROTATE = (m, i) -> {
            var minecraft = Minecraft.getInstance();
            var integratedServer = minecraft.getSingleplayerServer();
            float angle;

            // Calculate rotation angle
            if (minecraft.isPaused() && integratedServer != null) {
                // When the game is paused, rotating parts are fixed at certain angle
                angle = integratedServer.getTickCount() % 5 * 72;
            } else {
                angle = Util.getMillis() % 250L * 1.44F;
            }

            m.pushPose();
            m.mulPoseMatrix(new Matrix4f().rotate((float) Math.toRadians(angle), 0.0f, 1.0f, 0.0f));
            return i + 1;
        };
        private final static BiFunction<PoseStack, Integer, Integer> FN_POP_POSE = (m, i) -> {
            if (i > 0) {
                m.popPose();
                return i - 1;
            } else {
                return 0;
            }
        };

        private final static Predicate<LaserBladeItemColor> IS_ANY_STATE = itemColor -> true;
        private final static Predicate<LaserBladeItemColor> IS_WORKING = itemColor -> !itemColor.isBroken;
        private final static Predicate<LaserBladeItemColor> IS_BROKEN = itemColor -> itemColor.isBroken;

        private final static BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> GET_DEFAULT_RENDERER = (model, itemColor) -> model.getHiltRenderType();
        private final static BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> GET_UNLIT_RENDERER = (model, itemColor) -> model.getUnlitRenderType();
        private final static BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> GET_INNER_ADD_RENDERER = (model, itemColor) -> model.getInnerBladeAddRenderType(itemColor.isInnerSubColor);
        private final static BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> GET_OUTER_ADD_RENDERER = (model, itemColor) -> model.getOuterBladeAddRenderType(itemColor.isOuterSubColor);

        private final static Function<LaserBladeItemColor, Integer> GET_DEFAULT_COLOR = itemColor -> 0xFFFFFFFF;
        private final static Function<LaserBladeItemColor, Integer> GET_OFF_COLOR = itemColor -> 0xFFCCCCCC;
        private final static Function<LaserBladeItemColor, Integer> GET_GRIP_COLOR = itemColor -> itemColor.rawGripColor;
        private final static Function<LaserBladeItemColor, Integer> GET_INNER_DEFAULT_COLOR = itemColor -> itemColor.simpleInnerColor;
        private final static Function<LaserBladeItemColor, Integer> GET_OUTER_DEFAULT_COLOR = itemColor -> itemColor.simpleOuterColor;
        private final static Function<LaserBladeItemColor, Integer> GET_INNER_ADD_COLOR = itemColor -> itemColor.rawInnerColor;
        private final static Function<LaserBladeItemColor, Integer> GET_OUTER_ADD_COLOR = itemColor -> itemColor.rawOuterColor;

        private final static Function<Integer, Integer> GET_GIVEN_LIGHT = i -> i;
        private final static Function<Integer, Integer> GET_FULL_LIGHT = i -> 0xF000F0;

        private final static Vector2f FIXED_UV = new Vector2f(0, 0);

        private boolean isFunction = false;
        private BiFunction<PoseStack, Integer, Integer> fnCallFunction = FN_NOP;
        private Predicate<LaserBladeItemColor> fnIsVisible = IS_ANY_STATE;
        private BiFunction<SimpleLaserBladeModel, LaserBladeItemColor, RenderType> fnGetRenderType = GET_DEFAULT_RENDERER;
        private Function<LaserBladeItemColor, Integer> fnGetPartColor = GET_DEFAULT_COLOR;
        private Function<Integer, Integer> fnGetLightColor = GET_GIVEN_LIGHT;
        private List<SimpleQuad> quads = Collections.emptyList();

        public ModelObject(String objectName, JsonModel.JsonModelObject object, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces) {
            initRenderFunctions(object);

            if (!isFunction) {
                initQuads(objectName, vertices, colors, normals, faces, object);
            }
        }

        private void initRenderFunctions(JsonModel.JsonModelObject object) {
            final String type = object.type;
            final String part = object.part;

            switch (type) {
                case "default" -> {}
                case "unlit" -> {
                    fnGetRenderType = GET_UNLIT_RENDERER;
                    fnGetLightColor = GET_FULL_LIGHT;
                }
                case "add" -> {
                    if (part.equals("blade_in")) {
                        fnGetRenderType = GET_INNER_ADD_RENDERER;
                    } else {
                        fnGetRenderType = GET_OUTER_ADD_RENDERER;
                    }
                    fnGetLightColor = GET_FULL_LIGHT;
                }
                case "flat" -> {
                    fnGetRenderType = GET_DEFAULT_RENDERER;
                    fnGetLightColor = GET_FULL_LIGHT;
                }
                case "function" -> {
                    isFunction = true;
                }
                default -> {
                    fnGetRenderType = GET_DEFAULT_RENDERER;
                    fnGetLightColor = GET_GIVEN_LIGHT;
                }
            }

            if (isFunction) {
                switch (object.name) {
                    case "rotate" -> fnCallFunction = FN_ROTATE;
                    case "pop_pose" -> fnCallFunction = FN_POP_POSE;
                    default -> fnCallFunction = FN_NOP;
                }
            }

            switch (part) {
                case "" -> {}
                case "grip" -> fnGetPartColor = GET_GRIP_COLOR;
                case "off" -> fnGetPartColor = GET_OFF_COLOR;
                case "blade_in" -> {
                    if (type.equals("add")) {
                        fnGetPartColor = GET_INNER_ADD_COLOR;
                    } else {
                        fnGetPartColor = GET_INNER_DEFAULT_COLOR;
                    }
                }
                case "blade_out" -> {
                    if (type.equals("add")) {
                        fnGetPartColor = GET_OUTER_ADD_COLOR;
                    } else {
                        fnGetPartColor = GET_OUTER_DEFAULT_COLOR;
                    }
                }
                default -> fnGetPartColor = GET_DEFAULT_COLOR;
            }

            final String state = object.state;

            switch (state) {
                case "any" -> {}
                case "on" -> fnIsVisible = IS_WORKING;
                case "off" -> fnIsVisible = IS_BROKEN;
                default -> fnIsVisible = IS_ANY_STATE;
            }
        }

        private void initQuads(String objectName, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces, JsonModel.JsonModelObject object) {
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

        public int callFunction(PoseStack poseStack, int stackIndex) {
            return fnCallFunction.apply(poseStack, stackIndex);
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

    public static class JsonModel {
        public String type = "";
        public int id = -1;
        @SerializedName("renderer_id")
        public int rendererID = 0;
        @SerializedName("gui_resize")
        public float[] guiResize = null;
        public List<JsonModelObject> objects = Collections.emptyList();
        public List<int[]> faces = Collections.emptyList();
        public List<float[]> vertices = Collections.emptyList();
        public List<float[]> normals = Collections.emptyList();
        public List<float[]> colors = Collections.emptyList();

        public static class JsonModelObject {
            public String type = "default";
            public String name = "";
            public String part = "";
            public String state = "any";
            public int from = 0;
            public int size = 0;
        }
    }

}
