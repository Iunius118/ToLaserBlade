package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelObjectV1 {
    public static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelObjectVersion1");
    private final static Vector2f FIXED_UV = new Vector2f(0, 0);
    private final static int WHITE_ARGB = 0xFFFFFFFF;
    private final static int GRAY_ARGB = 0xFFCCCCCC;
    private final static int UNLIT_LIGHT = 0x00F000F0;

    final private JsonModelV1.JsonModelObject.Type type;
    private boolean isFunction = false;
    private FunctionsV1.ObjectFunction objectFunction = FunctionsV1.FN_NOP;
    final private JsonModelV1.JsonModelObject.Part part;
    final private JsonModelV1.JsonModelObject.State state;
    private List<SimpleModel.SimpleQuad> quads = Collections.emptyList();

    public ModelObjectV1(String objectName, JsonModelV1.JsonModelObject object, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces) {
        type = object.type;
        part = object.part;
        state = object.state;
        initRenderFunctions(object);

        if (!isFunction) {
            initQuads(objectName, vertices, colors, normals, faces, object);
        }
    }

    private void initRenderFunctions(JsonModelV1.JsonModelObject object) {
        if (type == JsonModelV1.JsonModelObject.Type.FUNCTION) {
            isFunction = true;

            switch (object.name) {
                case "rotate" -> objectFunction = FunctionsV1.FN_ROTATE;
                case "shield" -> objectFunction = FunctionsV1.FN_SHIELD;
                case "pop_pose" -> objectFunction = FunctionsV1.FN_POP_POSE;
                default -> objectFunction = FunctionsV1.FN_NOP;
            }
        }
    }

    private void initQuads(String objectName, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces, JsonModelV1.JsonModelObject object) {
        List<SimpleModel.SimpleQuad> simpleQuads = new ArrayList<>();
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

            SimpleModel.SimpleQuad quad = new SimpleModel.SimpleQuad(
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
        LOGGER.warn("Index is out of bounds: {}faces/{}/{}", objectName, faceIndex, elementIndex);
    }

    public boolean isFunction() {
        return isFunction;
    }

    public int callFunction(LaserBladeModelV1.RenderingContext context, int pushCount) {
        return objectFunction.invoke(context, pushCount);
    }

    public boolean isVisible(LaserBladeItemColor itemColor) {
        return switch (state) {
            case ANY -> true;
            case ONLY_WORKING -> !itemColor.isBroken;
            case ONLY_BROKEN -> itemColor.isBroken;
        };
    }

    public RenderType getRenderType(SimpleLaserBladeModel model, LaserBladeItemColor itemColor) {
        return switch (type) {
            case ADD -> {
                if (part == JsonModelV1.JsonModelObject.Part.BLADE_IN) {
                    yield model.getInnerBladeAddRenderType(itemColor.isInnerSubColor);
                } else {
                    yield model.getOuterBladeAddRenderType(itemColor.isOuterSubColor);
                }
            }
            case UNLIT -> model.getUnlitRenderType();
            case FLAT, DEFAULT -> model.getHiltRenderType();
            case FUNCTION -> null;
        };
    }

    public int getPartColor(LaserBladeItemColor itemColor) {
        return switch (part) {
            case GRIP -> itemColor.gripColor;
            case BLADE_IN -> {
                if (type == JsonModelV1.JsonModelObject.Type.ADD) {
                    yield itemColor.innerColor;
                } else {
                    yield itemColor.simpleInnerColor;
                }
            }
            case BLADE_OUT -> {
                if (type == JsonModelV1.JsonModelObject.Type.ADD) {
                    yield itemColor.outerColor;
                } else {
                    yield itemColor.simpleOuterColor;
                }
            }
            case DEFAULT -> WHITE_ARGB;
            case OFF -> GRAY_ARGB;
        };
    }

    public int getLightColor(int lightColor) {
        return switch (type) {
            case ADD, UNLIT, FLAT -> UNLIT_LIGHT;
            case DEFAULT, FUNCTION -> lightColor;
        };
    }

    public List<SimpleModel.SimpleQuad> getQuads() {
        return quads;
    }
}
