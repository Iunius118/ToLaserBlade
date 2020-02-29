package com.github.iunius118.tolaserblade.client.model;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class LaserBladeItemModel implements SimpleItemModel {
    private static final Vector3f V3F_29 = new Vector3f(0.031300F, 0.375000F, -0.031300F);
    private static final Vector3f V3F_11 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f V3F_42 = new Vector3f(0.093800F, 0.375000F, 0.093700F);
    private static final Vector3f V3F_13 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f V3F_1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f V3F_16 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f V3F_2 = new Vector3f(0.062550F, 0.375000F, 0.062450F);
    private static final Vector3f V3F_43 = new Vector3f(-0.093700F, 0.375000F, 0.093700F);
    private static final Vector3f V3F_19 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f V3F_31 = new Vector3f(-0.031200F, 0.375000F, -0.031300F);
    private static final Vector3f V3F_53 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f V3F_25 = new Vector3f(0.031300F, 1.375000F, 0.031200F);
    private static final Vector3f V3F_15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f V3F_52 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f V3F_51 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f V3F_17 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f V3F_37 = new Vector3f(0.062550F, 0.343700F, -0.062550F);
    private static final Vector3f V3F_28 = new Vector3f(0.031300F, 1.375000F, -0.031300F);
    private static final Vector3f V3F_45 = new Vector3f(0.093800F, 0.375000F, -0.093800F);
    private static final Vector3f V3F_30 = new Vector3f(-0.031200F, 1.375000F, -0.031300F);
    private static final Vector3f V3F_14 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f V3F_26 = new Vector3f(0.031300F, 0.375000F, 0.031200F);
    private static final Vector3f V3F_47 = new Vector3f(-0.093700F, 0.375000F, -0.093800F);
    private static final Vector3f V3F_10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f V3F_20 = new Vector3f(-0.062400F, 0.375000F, 0.062400F);
    private static final Vector3f V3F_22 = new Vector3f(0.062600F, 0.375000F, -0.062600F);
    private static final Vector3f V3F_41 = new Vector3f(0.093800F, 1.437500F, 0.093700F);
    private static final Vector3f V3F_6 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f V3F_46 = new Vector3f(-0.093700F, 1.437500F, -0.093800F);
    private static final Vector3f V3F_49 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f V3F_44 = new Vector3f(0.093800F, 1.437500F, -0.093800F);
    private static final Vector3f V3F_40 = new Vector3f(-0.093700F, 1.437500F, 0.093700F);
    private static final Vector3f V3F_18 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f V3F_39 = new Vector3f(-0.062450F, 0.343700F, -0.062550F);
    private static final Vector3f V3F_7 = new Vector3f(-0.062450F, 0.375000F, 0.062450F);
    private static final Vector3f V3F_9 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f V3F_33 = new Vector3f(0.062550F, 1.406300F, 0.062450F);
    private static final Vector3f V3F_50 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f V3F_36 = new Vector3f(0.062550F, 1.406300F, -0.062550F);
    private static final Vector3f V3F_35 = new Vector3f(-0.062450F, 0.343700F, 0.062450F);
    private static final Vector3f V3F_34 = new Vector3f(0.062550F, 0.343700F, 0.062450F);
    private static final Vector3f V3F_38 = new Vector3f(-0.062450F, 1.406300F, -0.062550F);
    private static final Vector3f V3F_4 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f V3F_32 = new Vector3f(-0.062450F, 1.406300F, 0.062450F);
    private static final Vector3f V3F_24 = new Vector3f(-0.031200F, 1.375000F, 0.031200F);
    private static final Vector3f V3F_21 = new Vector3f(0.062600F, 0.375000F, 0.062400F);
    private static final Vector3f V3F_0 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f V3F_5 = new Vector3f(-0.062450F, 0.375000F, -0.062550F);
    private static final Vector3f V3F_23 = new Vector3f(-0.062400F, 0.375000F, -0.062600F);
    private static final Vector3f V3F_8 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f V3F_48 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f V3F_27 = new Vector3f(-0.031200F, 0.375000F, 0.031200F);
    private static final Vector3f V3F_12 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f V3F_3 = new Vector3f(0.062550F, 0.375000F, -0.062550F);
    private static final Vec2f V2F_49 = new Vec2f(0.37500F, 1.00000F);
    private static final Vec2f V2F_35 = new Vec2f(0.06250F, 1.00000F);
    private static final Vec2f V2F_44 = new Vec2f(0.50000F, 0.00000F);
    private static final Vec2f V2F_1 = new Vec2f(0.81250F, 0.37500F);
    private static final Vec2f V2F_10 = new Vec2f(1.00000F, 0.87500F);
    private static final Vec2f V2F_34 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f V2F_61 = new Vec2f(0.62500F, 0.12500F);
    private static final Vec2f V2F_45 = new Vec2f(0.56250F, 0.00000F);
    private static final Vec2f V2F_4 = new Vec2f(1.00000F, 0.56250F);
    private static final Vec2f V2F_42 = new Vec2f(0.25000F, 0.12500F);
    private static final Vec2f V2F_8 = new Vec2f(0.81250F, 0.81250F);
    private static final Vec2f V2F_38 = new Vec2f(0.12500F, 1.00000F);
    private static final Vec2f V2F_58 = new Vec2f(0.31250F, 0.94444F);
    private static final Vec2f V2F_57 = new Vec2f(0.37500F, 0.94444F);
    private static final Vec2f V2F_14 = new Vec2f(0.62500F, 0.62500F);
    private static final Vec2f V2F_31 = new Vec2f(0.25000F, 1.00000F);
    private static final Vec2f V2F_15 = new Vec2f(1.00000F, 0.62500F);
    private static final Vec2f V2F_16 = new Vec2f(0.62500F, 0.81250F);
    private static final Vec2f V2F_22 = new Vec2f(0.68750F, 0.06250F);
    private static final Vec2f V2F_41 = new Vec2f(0.31250F, 0.00000F);
    private static final Vec2f V2F_54 = new Vec2f(0.62500F, 0.00000F);
    private static final Vec2f V2F_29 = new Vec2f(0.18750F, 0.00000F);
    private static final Vec2f V2F_24 = new Vec2f(0.62500F, 0.37500F);
    private static final Vec2f V2F_53 = new Vec2f(0.56250F, 0.06250F);
    private static final Vec2f V2F_17 = new Vec2f(0.62500F, 0.87500F);
    private static final Vec2f V2F_50 = new Vec2f(0.31250F, 1.00000F);
    private static final Vec2f V2F_7 = new Vec2f(0.96875F, 0.40625F);
    private static final Vec2f V2F_52 = new Vec2f(0.43750F, 1.00000F);
    private static final Vec2f V2F_51 = new Vec2f(0.43750F, 0.00000F);
    private static final Vec2f V2F_48 = new Vec2f(0.37500F, 0.00000F);
    private static final Vec2f V2F_47 = new Vec2f(0.50000F, 1.00000F);
    private static final Vec2f V2F_19 = new Vec2f(0.87500F, 0.06250F);
    private static final Vec2f V2F_25 = new Vec2f(0.75000F, 0.06250F);
    private static final Vec2f V2F_46 = new Vec2f(0.56250F, 1.00000F);
    private static final Vec2f V2F_60 = new Vec2f(0.56250F, 0.12500F);
    private static final Vec2f V2F_37 = new Vec2f(0.12500F, 0.00000F);
    private static final Vec2f V2F_28 = new Vec2f(0.75000F, 0.00000F);
    private static final Vec2f V2F_43 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f V2F_2 = new Vec2f(0.84375F, 0.40625F);
    private static final Vec2f V2F_0 = new Vec2f(0.81250F, 0.56250F);
    private static final Vec2f V2F_26 = new Vec2f(0.75000F, 0.37500F);
    private static final Vec2f V2F_39 = new Vec2f(0.31250F, 0.06250F);
    private static final Vec2f V2F_9 = new Vec2f(1.00000F, 0.81250F);
    private static final Vec2f V2F_13 = new Vec2f(0.81250F, 0.62500F);
    private static final Vec2f V2F_40 = new Vec2f(0.25000F, 0.06250F);
    private static final Vec2f V2F_3 = new Vec2f(0.84375F, 0.53125F);
    private static final Vec2f V2F_30 = new Vec2f(0.25000F, 0.00000F);
    private static final Vec2f V2F_27 = new Vec2f(0.81250F, 0.00000F);
    private static final Vec2f V2F_6 = new Vec2f(1.00000F, 0.37500F);
    private static final Vec2f V2F_18 = new Vec2f(0.81250F, 0.06250F);
    private static final Vec2f V2F_33 = new Vec2f(0.00000F, 0.00000F);
    private static final Vec2f V2F_5 = new Vec2f(0.96875F, 0.53125F);
    private static final Vec2f V2F_56 = new Vec2f(0.50000F, 0.94444F);
    private static final Vec2f V2F_36 = new Vec2f(0.00000F, 1.00000F);
    private static final Vec2f V2F_20 = new Vec2f(0.87500F, 0.37500F);
    private static final Vec2f V2F_21 = new Vec2f(0.62500F, 0.06250F);
    private static final Vec2f V2F_32 = new Vec2f(0.18750F, 1.00000F);
    private static final Vec2f V2F_23 = new Vec2f(0.68750F, 0.37500F);
    private static final Vec2f V2F_59 = new Vec2f(0.43750F, 0.94444F);
    private static final Vec2f V2F_12 = new Vec2f(0.62500F, 0.56250F);
    private static final Vec2f V2F_55 = new Vec2f(0.56250F, 0.94444F);
    private static final Vec2f V2F_11 = new Vec2f(0.81250F, 0.87500F);

    private static final Vector4f COLOR_0 = new Vector4f(1F, 1F, 1F, 0.5F);

    public static List<FaceData> getBladeOuter1Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(V3F_35, COLOR_0, V2F_47, V3F_49, V3F_34, COLOR_0, V2F_46, V3F_49, V3F_33, COLOR_0, V2F_45, V3F_49, V3F_32, COLOR_0, V2F_44, V3F_49));
        list.add(new FaceData(V3F_34, COLOR_0, V2F_50, V3F_50, V3F_37, COLOR_0, V2F_49, V3F_50, V3F_36, COLOR_0, V2F_48, V3F_50, V3F_33, COLOR_0, V2F_41, V3F_50));
        list.add(new FaceData(V3F_37, COLOR_0, V2F_49, V3F_51, V3F_39, COLOR_0, V2F_52, V3F_51, V3F_38, COLOR_0, V2F_51, V3F_51, V3F_36, COLOR_0, V2F_48, V3F_51));
        list.add(new FaceData(V3F_39, COLOR_0, V2F_52, V3F_52, V3F_35, COLOR_0, V2F_47, V3F_52, V3F_32, COLOR_0, V2F_44, V3F_52, V3F_38, COLOR_0, V2F_51, V3F_52));
        list.add(new FaceData(V3F_32, COLOR_0, V2F_54, V3F_48, V3F_33, COLOR_0, V2F_45, V3F_48, V3F_36, COLOR_0, V2F_53, V3F_48, V3F_38, COLOR_0, V2F_21, V3F_48));
        return list;
    }

    private static final Vector4f COLOR_1 = new Vector4f(0.75F, 0.75F, 0.75F, 1F);

    public static List<FaceData> getHiltFaces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(V3F_3, COLOR_1, V2F_3, V3F_48, V3F_2, COLOR_1, V2F_2, V3F_48, V3F_1, COLOR_1, V2F_1, V3F_48, V3F_0, COLOR_1, V2F_0, V3F_48));
        list.add(new FaceData(V3F_5, COLOR_1, V2F_5, V3F_48, V3F_3, COLOR_1, V2F_3, V3F_48, V3F_0, COLOR_1, V2F_0, V3F_48, V3F_4, COLOR_1, V2F_4, V3F_48));
        list.add(new FaceData(V3F_2, COLOR_1, V2F_2, V3F_48, V3F_7, COLOR_1, V2F_7, V3F_48, V3F_6, COLOR_1, V2F_6, V3F_48, V3F_1, COLOR_1, V2F_1, V3F_48));
        list.add(new FaceData(V3F_6, COLOR_1, V2F_6, V3F_48, V3F_7, COLOR_1, V2F_7, V3F_48, V3F_5, COLOR_1, V2F_5, V3F_48, V3F_4, COLOR_1, V2F_4, V3F_48));
        list.add(new FaceData(V3F_9, COLOR_1, V2F_11, V3F_49, V3F_8, COLOR_1, V2F_10, V3F_49, V3F_1, COLOR_1, V2F_9, V3F_49, V3F_6, COLOR_1, V2F_8, V3F_49));
        list.add(new FaceData(V3F_8, COLOR_1, V2F_14, V3F_50, V3F_10, COLOR_1, V2F_13, V3F_50, V3F_0, COLOR_1, V2F_0, V3F_50, V3F_1, COLOR_1, V2F_12, V3F_50));
        list.add(new FaceData(V3F_10, COLOR_1, V2F_13, V3F_51, V3F_11, COLOR_1, V2F_15, V3F_51, V3F_4, COLOR_1, V2F_4, V3F_51, V3F_0, COLOR_1, V2F_0, V3F_51));
        list.add(new FaceData(V3F_11, COLOR_1, V2F_17, V3F_52, V3F_9, COLOR_1, V2F_11, V3F_52, V3F_6, COLOR_1, V2F_8, V3F_52, V3F_4, COLOR_1, V2F_16, V3F_52));
        list.add(new FaceData(V3F_11, COLOR_1, V2F_8, V3F_53, V3F_10, COLOR_1, V2F_16, V3F_53, V3F_8, COLOR_1, V2F_14, V3F_53, V3F_9, COLOR_1, V2F_13, V3F_53));
        list.add(new FaceData(V3F_15, COLOR_1, V2F_1, V3F_49, V3F_14, COLOR_1, V2F_20, V3F_49, V3F_13, COLOR_1, V2F_19, V3F_49, V3F_12, COLOR_1, V2F_18, V3F_49));
        list.add(new FaceData(V3F_14, COLOR_1, V2F_24, V3F_50, V3F_17, COLOR_1, V2F_23, V3F_50, V3F_16, COLOR_1, V2F_22, V3F_50, V3F_13, COLOR_1, V2F_21, V3F_50));
        list.add(new FaceData(V3F_17, COLOR_1, V2F_23, V3F_51, V3F_19, COLOR_1, V2F_26, V3F_51, V3F_18, COLOR_1, V2F_25, V3F_51, V3F_16, COLOR_1, V2F_22, V3F_51));
        list.add(new FaceData(V3F_19, COLOR_1, V2F_26, V3F_52, V3F_15, COLOR_1, V2F_1, V3F_52, V3F_12, COLOR_1, V2F_18, V3F_52, V3F_18, COLOR_1, V2F_25, V3F_52));
        list.add(new FaceData(V3F_19, COLOR_1, V2F_18, V3F_53, V3F_17, COLOR_1, V2F_25, V3F_53, V3F_14, COLOR_1, V2F_28, V3F_53, V3F_15, COLOR_1, V2F_27, V3F_53));
        return list;
    }

    private static final Vector4f COLOR_2 = new Vector4f(1F, 1F, 1F, 0.25F);

    public static List<FaceData> getBladeOuter2Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(V3F_43, COLOR_2, V2F_56, V3F_49, V3F_42, COLOR_2, V2F_55, V3F_49, V3F_41, COLOR_2, V2F_45, V3F_49, V3F_40, COLOR_2, V2F_44, V3F_49));
        list.add(new FaceData(V3F_42, COLOR_2, V2F_58, V3F_50, V3F_45, COLOR_2, V2F_57, V3F_50, V3F_44, COLOR_2, V2F_48, V3F_50, V3F_41, COLOR_2, V2F_41, V3F_50));
        list.add(new FaceData(V3F_45, COLOR_2, V2F_57, V3F_51, V3F_47, COLOR_2, V2F_59, V3F_51, V3F_46, COLOR_2, V2F_51, V3F_51, V3F_44, COLOR_2, V2F_48, V3F_51));
        list.add(new FaceData(V3F_47, COLOR_2, V2F_59, V3F_52, V3F_43, COLOR_2, V2F_56, V3F_52, V3F_40, COLOR_2, V2F_44, V3F_52, V3F_46, COLOR_2, V2F_51, V3F_52));
        list.add(new FaceData(V3F_40, COLOR_2, V2F_54, V3F_48, V3F_41, COLOR_2, V2F_45, V3F_48, V3F_44, COLOR_2, V2F_53, V3F_48, V3F_46, COLOR_2, V2F_21, V3F_48));
        list.add(new FaceData(V3F_47, COLOR_2, V2F_61, V3F_53, V3F_45, COLOR_2, V2F_60, V3F_53, V3F_42, COLOR_2, V2F_53, V3F_53, V3F_43, COLOR_2, V2F_21, V3F_53));
        return list;
    }

    private static final Vector4f COLOR_3 = new Vector4f(1F, 1F, 1F, 1F);

    public static List<FaceData> getHilt2Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(V3F_21, COLOR_3, V2F_2, V3F_48, V3F_22, COLOR_3, V2F_3, V3F_48, V3F_23, COLOR_3, V2F_5, V3F_48, V3F_20, COLOR_3, V2F_7, V3F_48));
        return list;
    }

    private static final Vector4f COLOR_4 = new Vector4f(1F, 1F, 1F, 0.9F);

    public static List<FaceData> getBladeInnerFaces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(V3F_27, COLOR_4, V2F_32, V3F_49, V3F_26, COLOR_4, V2F_31, V3F_49, V3F_25, COLOR_4, V2F_30, V3F_49, V3F_24, COLOR_4, V2F_29, V3F_49));
        list.add(new FaceData(V3F_26, COLOR_4, V2F_36, V3F_50, V3F_29, COLOR_4, V2F_35, V3F_50, V3F_28, COLOR_4, V2F_34, V3F_50, V3F_25, COLOR_4, V2F_33, V3F_50));
        list.add(new FaceData(V3F_29, COLOR_4, V2F_35, V3F_51, V3F_31, COLOR_4, V2F_38, V3F_51, V3F_30, COLOR_4, V2F_37, V3F_51, V3F_28, COLOR_4, V2F_34, V3F_51));
        list.add(new FaceData(V3F_31, COLOR_4, V2F_38, V3F_52, V3F_27, COLOR_4, V2F_32, V3F_52, V3F_24, COLOR_4, V2F_29, V3F_52, V3F_30, COLOR_4, V2F_37, V3F_52));
        list.add(new FaceData(V3F_24, COLOR_4, V2F_41, V3F_48, V3F_25, COLOR_4, V2F_30, V3F_48, V3F_28, COLOR_4, V2F_40, V3F_48, V3F_30, COLOR_4, V2F_39, V3F_48));
        list.add(new FaceData(V3F_27, COLOR_4, V2F_39, V3F_53, V3F_31, COLOR_4, V2F_43, V3F_53, V3F_29, COLOR_4, V2F_42, V3F_53, V3F_26, COLOR_4, V2F_40, V3F_53));
        return list;
    }
}
