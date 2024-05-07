package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;

class ModelObjectFunctions {
    private ModelObjectFunctions() {}

    // Functions
    public final static ModelObject.ModelObjectFunction FN_NOP = (args, i) -> i;
    public final static ModelObject.ModelObjectFunction FN_ROTATE = (args, i) -> {
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

        PoseStack matrices = args.matrices();
        matrices.pushPose();
        matrices.mulPose(new Matrix4f().rotate((float) Math.toRadians(angle), 0.0f, 1.0f, 0.0f));
        return i + 1;
    };
    public final static ModelObject.ModelObjectFunction FN_SHIELD = (args, i) -> {
        PoseStack matrices = args.matrices();
        matrices.pushPose();
        transformShield(args.mode(), matrices);
        return i + 1;
    };
    public final static ModelObject.ModelObjectFunction FN_POP_POSE = (args, i) -> {
        if (i > 0) {
            args.matrices().popPose();
            return i - 1;
        } else {
            return 0;
        }
    };

    // FP_RIGHT_HAND_TRANSFORMATION: matrices.mulPose(new Quaternionf().rotationY((float) Math.toRadians(45)).rotateX((float) Math.toRadians(90))); matrices.translate(0.0D, -0.3125D, 0.0D); matrices.scale(1.5F, 1.5F, 1.5F);
    private static final Matrix4f FP_RIGHT_HAND_TRANSFORMATION = new Matrix4f(1.06066F, 0, -1.06066F, 0, 1.06066F, 0, 1.06066F, 0, 0, -1.5F, 0, 0, -0.220971F, 0, -0.220971F, 1);
    // FP_LEFT_HAND_TRANSFORMATION: matrices.translate(0.0D, 0.3125D, 0.0D); matrices.mulPose(new Quaternionf().rotationZYX((float) Math.toRadians(180), (float) Math.toRadians(45), (float) Math.toRadians(-90))); matrices.translate(0.0D, -0.15625D, 0.3125D); matrices.scale(1.5F, 1.5F, 1.5F);
    private static final Matrix4f FP_LEFT_HAND_TRANSFORMATION = new Matrix4f(-1.06066F, 0, -1.06066F, 0, 1.06066F, 0, -1.06066F, 0, 0, -1.5F, 0, 0, -0.110485F, 0, 0.110485F, 1);
    // TP_LEFT_HAND_TRANSFORMATION: matrices.translate(0.0D, 0.3125D, 0.0D); matrices.mulPose(new Quaternionf().rotationZ((float) Math.toRadians(180)));
    private static final Matrix4f TP_LEFT_HAND_TRANSFORMATION = new Matrix4f(-1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0.3125F, 0, 1);
    // GUI_TRANSFORMATION: matrices.translate(0.15625D, 0.75D, -0.15D); matrices.mulPose(GUI_TRANSFORMATION); matrices.scale(0.95F, 0.95F, 0.95F);
    private static final Matrix4f GUI_TRANSFORMATION = new Matrix4f(-0.95F, 0, 0, 0, 0, 0, 0.95F, 0, 0, 0.95F, 0, 0, 0.15625F, 0.75F, -0.15F, 1);

    private static void transformShield(ItemDisplayContext mode, PoseStack matrices) {
        switch (mode) {
            case FIRST_PERSON_RIGHT_HAND -> matrices.mulPose(FP_RIGHT_HAND_TRANSFORMATION);
            case FIRST_PERSON_LEFT_HAND -> matrices.mulPose(FP_LEFT_HAND_TRANSFORMATION);
            case THIRD_PERSON_LEFT_HAND -> matrices.mulPose(TP_LEFT_HAND_TRANSFORMATION);
            case GUI, FIXED -> matrices.mulPose(GUI_TRANSFORMATION);
        }
    }
}
