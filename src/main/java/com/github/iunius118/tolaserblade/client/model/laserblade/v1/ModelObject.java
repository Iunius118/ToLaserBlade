package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.model.SimpleLaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.SimpleModel;
import com.github.iunius118.tolaserblade.client.model.Vector2f;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class ModelObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToLaserBlade.MOD_NAME + ".LaserBladeModelVersion1.ModelObject");
    private static final Vector2f FIXED_UV = new Vector2f(0, 0);
    private static final int WHITE_ARGB = 0xFFFFFFFF;
    private static final int GRAY_ARGB = 0xFFCCCCCC;
    private static final int UNLIT_LIGHT = 0x00F000F0;

    private final JsonModelV1.JsonModelObject.State state;
    private final boolean isUnlitLight;

    public final boolean isFunction;
    public final ModelObjectFunction function;
    public final ObjectRenderType renderType;
    public final ObjectPartColor color;
    public final List<SimpleModel.SimpleQuad> quads = new ArrayList<>();

    public ModelObject(String objectName, JsonModelV1.JsonModelObject object, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces) {
        final JsonModelV1.JsonModelObject.Type type = object.type;
        final JsonModelV1.JsonModelObject.Part part = object.part;

        state = object.state;
        isFunction = (type == JsonModelV1.JsonModelObject.Type.FUNCTION);
        function = getObjectFunction(object);
        renderType = getRenderType(type, part);
        color = getPartColor(type, part);
        isUnlitLight = isUnlitLight(type);

        if (!isFunction) {
            initQuads(objectName, vertices, colors, normals, faces, object);
        }
    }

    public boolean isAvailable(boolean isBroken) {
        return (state == JsonModelV1.JsonModelObject.State.ANY)
                || (!isBroken && state == JsonModelV1.JsonModelObject.State.ONLY_WORKING)
                || (isBroken && state == JsonModelV1.JsonModelObject.State.ONLY_BROKEN);
    }

    public record RenderingContext(ItemStack itemStack, ItemDisplayContext mode, PoseStack matrices) {}

    @FunctionalInterface
    public interface ModelObjectFunction {
        int invoke(RenderingContext context, int pushCount);
    }

    private ModelObjectFunction getObjectFunction(JsonModelV1.JsonModelObject object) {
        if (object.type == JsonModelV1.JsonModelObject.Type.FUNCTION) {
            return switch (object.name) {
                case "rotate" -> ModelObjectFunctions.FN_ROTATE;
                case "shield" -> ModelObjectFunctions.FN_SHIELD;
                case "pop_pose" -> ModelObjectFunctions.FN_POP_POSE;
                default -> ModelObjectFunctions.FN_NOP;
            };
        } else {
            return ModelObjectFunctions.FN_NOP;
        }
    }

    @FunctionalInterface
    public interface ObjectRenderType {
        RenderType get(SimpleLaserBladeModel model, LaserBladeItemColor itemColor);
    }

    private static final ObjectRenderType ADD_INNER_TYPE = (model, itemColor) -> model.getInnerBladeAddRenderType(itemColor.isInnerSubColor());
    private static final ObjectRenderType ADD_OUTER_TYPE = (model, itemColor) -> model.getOuterBladeAddRenderType(itemColor.isOuterSubColor());
    private static final ObjectRenderType UNLIT_TYPE = (model, itemColor) -> model.getUnlitRenderType();
    private static final ObjectRenderType HILT_TYPE = (model, itemColor) -> model.getHiltRenderType();

    private ObjectRenderType getRenderType(JsonModelV1.JsonModelObject.Type type, JsonModelV1.JsonModelObject.Part part) {
        return switch (type) {
            case ADD -> (part == JsonModelV1.JsonModelObject.Part.BLADE_IN) ? ADD_INNER_TYPE : ADD_OUTER_TYPE;
            case UNLIT -> UNLIT_TYPE;
            case FLAT, DEFAULT -> HILT_TYPE;
            case FUNCTION -> null;
        };
    }

    @FunctionalInterface
    public interface ObjectPartColor {
        int get(LaserBladeItemColor itemColor);
    }

    private static final ObjectPartColor GRIP_COLOR = LaserBladeItemColor::gripColor;
    private static final ObjectPartColor INNER_COLOR = LaserBladeItemColor::innerColor;
    private static final ObjectPartColor SIMPLE_INNER_COLOR = LaserBladeItemColor::simpleInnerColor;
    private static final ObjectPartColor OUTER_COLOR = LaserBladeItemColor::outerColor;
    private static final ObjectPartColor SIMPLE_OUTER_COLOR = LaserBladeItemColor::simpleOuterColor;
    private static final ObjectPartColor DEFAULT_COLOR = itemColor -> WHITE_ARGB;
    private static final ObjectPartColor OFF_COLOR = itemColor -> GRAY_ARGB;

    public ObjectPartColor getPartColor(JsonModelV1.JsonModelObject.Type type, JsonModelV1.JsonModelObject.Part part) {
        return switch (part) {
            case GRIP -> GRIP_COLOR;
            case BLADE_IN -> (type == JsonModelV1.JsonModelObject.Type.ADD) ? INNER_COLOR : SIMPLE_INNER_COLOR;
            case BLADE_OUT -> (type == JsonModelV1.JsonModelObject.Type.ADD) ? OUTER_COLOR : SIMPLE_OUTER_COLOR;
            case DEFAULT -> DEFAULT_COLOR;
            case OFF -> OFF_COLOR;
        };
    }

    private boolean isUnlitLight(JsonModelV1.JsonModelObject.Type type) {
        return switch (type) {
            case ADD, UNLIT, FLAT -> true;
            case DEFAULT, FUNCTION -> false;
        };
    }

    public int getLightColor(int light) {
        return isUnlitLight ? UNLIT_LIGHT : light;
    }

    private void initQuads(String objectName, List<Vector3f> vertices, List<Vector4f> colors, List<Vector3f> normals, List<int[]> faces, JsonModelV1.JsonModelObject object) {
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
            quads.add(quad);
        }
    }

    private void warnIndexOutOfBounds(String objectName, int faceIndex, int elementIndex) {
        LOGGER.warn("Index is out of bounds: {}faces/{}/{}", objectName, faceIndex, elementIndex);
    }
}
