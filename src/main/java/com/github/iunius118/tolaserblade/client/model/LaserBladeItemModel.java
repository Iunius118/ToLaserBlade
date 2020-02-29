package com.github.iunius118.tolaserblade.client.model;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.math.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class LaserBladeItemModel implements SimpleItemModel {
    private static final Vector3f v1 = new Vector3f(0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v6 = new Vector3f(-0.093750F, 0.375000F, 0.093750F);
    private static final Vector3f v16 = new Vector3f(0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v37 = new Vector3f(0.062550F, 0.343700F, -0.062550F);
    private static final Vector3f v11 = new Vector3f(-0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v3 = new Vector3f(0.062550F, 0.375000F, -0.062550F);
    private static final Vector3f v30 = new Vector3f(-0.031200F, 1.375000F, -0.031300F);
    private static final Vector3f v9 = new Vector3f(-0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v2 = new Vector3f(0.062550F, 0.375000F, 0.062450F);
    private static final Vector3f v24 = new Vector3f(-0.031200F, 1.375000F, 0.031200F);
    private static final Vector3f v10 = new Vector3f(0.093750F, 0.312500F, -0.093750F);
    private static final Vector3f v21 = new Vector3f(0.062600F, 0.375000F, 0.062400F);
    private static final Vector3f v39 = new Vector3f(-0.062450F, 0.343700F, -0.062550F);
    private static final Vector3f v19 = new Vector3f(-0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v42 = new Vector3f(0.093800F, 0.375000F, 0.093700F);
    private static final Vector3f v114 = new Vector3f(-1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v43 = new Vector3f(-0.093700F, 0.375000F, 0.093700F);
    private static final Vector3f v113 = new Vector3f(0.00000F, 0.00000F, -1.00000F);
    private static final Vector3f v31 = new Vector3f(-0.031200F, 0.375000F, -0.031300F);
    private static final Vector3f v35 = new Vector3f(-0.062450F, 0.343700F, 0.062450F);
    private static final Vector3f v0 = new Vector3f(0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v32 = new Vector3f(-0.062450F, 1.406300F, 0.062450F);
    private static final Vector3f v20 = new Vector3f(-0.062400F, 0.375000F, 0.062400F);
    private static final Vector3f v36 = new Vector3f(0.062550F, 1.406300F, -0.062550F);
    private static final Vector3f v110 = new Vector3f(0.00000F, 1.00000F, 0.00000F);
    private static final Vector3f v17 = new Vector3f(0.031250F, 0.000000F, -0.031250F);
    private static final Vector3f v4 = new Vector3f(-0.093750F, 0.375000F, -0.093750F);
    private static final Vector3f v46 = new Vector3f(-0.093700F, 1.437500F, -0.093800F);
    private static final Vector3f v47 = new Vector3f(-0.093700F, 0.375000F, -0.093800F);
    private static final Vector3f v44 = new Vector3f(0.093800F, 1.437500F, -0.093800F);
    private static final Vector3f v33 = new Vector3f(0.062550F, 1.406300F, 0.062450F);
    private static final Vector3f v8 = new Vector3f(0.093750F, 0.312500F, 0.093750F);
    private static final Vector3f v45 = new Vector3f(0.093800F, 0.375000F, -0.093800F);
    private static final Vector3f v115 = new Vector3f(0.00000F, -1.00000F, 0.00000F);
    private static final Vector3f v12 = new Vector3f(-0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v40 = new Vector3f(-0.093700F, 1.437500F, 0.093700F);
    private static final Vector3f v41 = new Vector3f(0.093800F, 1.437500F, 0.093700F);
    private static final Vector3f v38 = new Vector3f(-0.062450F, 1.406300F, -0.062550F);
    private static final Vector3f v7 = new Vector3f(-0.062450F, 0.375000F, 0.062450F);
    private static final Vector3f v111 = new Vector3f(0.00000F, 0.00000F, 1.00000F);
    private static final Vector3f v34 = new Vector3f(0.062550F, 0.343700F, 0.062450F);
    private static final Vector3f v112 = new Vector3f(1.00000F, 0.00000F, 0.00000F);
    private static final Vector3f v15 = new Vector3f(-0.031250F, 0.000000F, 0.031250F);
    private static final Vector3f v18 = new Vector3f(-0.031250F, 0.312500F, -0.031250F);
    private static final Vector3f v29 = new Vector3f(0.031300F, 0.375000F, -0.031300F);
    private static final Vector3f v5 = new Vector3f(-0.062450F, 0.375000F, -0.062550F);
    private static final Vector3f v23 = new Vector3f(-0.062400F, 0.375000F, -0.062600F);
    private static final Vector3f v22 = new Vector3f(0.062600F, 0.375000F, -0.062600F);
    private static final Vector3f v28 = new Vector3f(0.031300F, 1.375000F, -0.031300F);
    private static final Vector3f v27 = new Vector3f(-0.031200F, 0.375000F, 0.031200F);
    private static final Vector3f v26 = new Vector3f(0.031300F, 0.375000F, 0.031200F);
    private static final Vector3f v25 = new Vector3f(0.031300F, 1.375000F, 0.031200F);
    private static final Vector3f v13 = new Vector3f(0.031250F, 0.312500F, 0.031250F);
    private static final Vector3f v14 = new Vector3f(0.031250F, 0.000000F, 0.031250F);
    private static final Vec2f v78 = new Vec2f(0.25000F, 0.00000F);
    private static final Vec2f v69 = new Vec2f(0.62500F, 0.06250F);
    private static final Vec2f v50 = new Vec2f(0.84375F, 0.40625F);
    private static final Vec2f v68 = new Vec2f(0.87500F, 0.37500F);
    private static final Vec2f v52 = new Vec2f(1.00000F, 0.56250F);
    private static final Vec2f v73 = new Vec2f(0.75000F, 0.06250F);
    private static final Vec2f v63 = new Vec2f(1.00000F, 0.62500F);
    private static final Vec2f v109 = new Vec2f(0.62500F, 0.12500F);
    private static final Vec2f v76 = new Vec2f(0.75000F, 0.00000F);
    private static final Vec2f v89 = new Vec2f(0.31250F, 0.00000F);
    private static final Vec2f v90 = new Vec2f(0.25000F, 0.12500F);
    private static final Vec2f v92 = new Vec2f(0.50000F, 0.00000F);
    private static final Vec2f v98 = new Vec2f(0.31250F, 1.00000F);
    private static final Vec2f v106 = new Vec2f(0.31250F, 0.94444F);
    private static final Vec2f v86 = new Vec2f(0.12500F, 1.00000F);
    private static final Vec2f v105 = new Vec2f(0.37500F, 0.94444F);
    private static final Vec2f v51 = new Vec2f(0.84375F, 0.53125F);
    private static final Vec2f v104 = new Vec2f(0.50000F, 0.94444F);
    private static final Vec2f v70 = new Vec2f(0.68750F, 0.06250F);
    private static final Vec2f v103 = new Vec2f(0.56250F, 0.94444F);
    private static final Vec2f v81 = new Vec2f(0.00000F, 0.00000F);
    private static final Vec2f v101 = new Vec2f(0.56250F, 0.06250F);
    private static final Vec2f v100 = new Vec2f(0.43750F, 1.00000F);
    private static final Vec2f v59 = new Vec2f(0.81250F, 0.87500F);
    private static final Vec2f v88 = new Vec2f(0.25000F, 0.06250F);
    private static final Vec2f v48 = new Vec2f(0.81250F, 0.56250F);
    private static final Vec2f v93 = new Vec2f(0.56250F, 0.00000F);
    private static final Vec2f v65 = new Vec2f(0.62500F, 0.87500F);
    private static final Vec2f v82 = new Vec2f(0.06250F, 0.00000F);
    private static final Vec2f v97 = new Vec2f(0.37500F, 1.00000F);
    private static final Vec2f v56 = new Vec2f(0.81250F, 0.81250F);
    private static final Vec2f v94 = new Vec2f(0.56250F, 1.00000F);
    private static final Vec2f v95 = new Vec2f(0.50000F, 1.00000F);
    private static final Vec2f v67 = new Vec2f(0.87500F, 0.06250F);
    private static final Vec2f v61 = new Vec2f(0.81250F, 0.62500F);
    private static final Vec2f v96 = new Vec2f(0.37500F, 0.00000F);
    private static final Vec2f v99 = new Vec2f(0.43750F, 0.00000F);
    private static final Vec2f v55 = new Vec2f(0.96875F, 0.40625F);
    private static final Vec2f v107 = new Vec2f(0.43750F, 0.94444F);
    private static final Vec2f v75 = new Vec2f(0.81250F, 0.00000F);
    private static final Vec2f v91 = new Vec2f(0.31250F, 0.12500F);
    private static final Vec2f v66 = new Vec2f(0.81250F, 0.06250F);
    private static final Vec2f v57 = new Vec2f(1.00000F, 0.81250F);
    private static final Vec2f v108 = new Vec2f(0.56250F, 0.12500F);
    private static final Vec2f v87 = new Vec2f(0.31250F, 0.06250F);
    private static final Vec2f v58 = new Vec2f(1.00000F, 0.87500F);
    private static final Vec2f v79 = new Vec2f(0.25000F, 1.00000F);
    private static final Vec2f v85 = new Vec2f(0.12500F, 0.00000F);
    private static final Vec2f v71 = new Vec2f(0.68750F, 0.37500F);
    private static final Vec2f v84 = new Vec2f(0.00000F, 1.00000F);
    private static final Vec2f v102 = new Vec2f(0.62500F, 0.00000F);
    private static final Vec2f v83 = new Vec2f(0.06250F, 1.00000F);
    private static final Vec2f v64 = new Vec2f(0.62500F, 0.81250F);
    private static final Vec2f v80 = new Vec2f(0.18750F, 1.00000F);
    private static final Vec2f v74 = new Vec2f(0.75000F, 0.37500F);
    private static final Vec2f v62 = new Vec2f(0.62500F, 0.62500F);
    private static final Vec2f v54 = new Vec2f(1.00000F, 0.37500F);
    private static final Vec2f v60 = new Vec2f(0.62500F, 0.56250F);
    private static final Vec2f v53 = new Vec2f(0.96875F, 0.53125F);
    private static final Vec2f v49 = new Vec2f(0.81250F, 0.37500F);
    private static final Vec2f v77 = new Vec2f(0.18750F, 0.00000F);
    private static final Vec2f v72 = new Vec2f(0.62500F, 0.37500F);

    private static final Vector4f c0 = new Vector4f(1F, 1F, 1F, 1F);

    public static List<FaceData> getBladeInnerMode1Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v27, c0, v80, v111, v26, c0, v79, v111, v25, c0, v78, v111, v24, c0, v77, v111));
        list.add(new FaceData(v26, c0, v84, v112, v29, c0, v83, v112, v28, c0, v82, v112, v25, c0, v81, v112));
        list.add(new FaceData(v29, c0, v83, v113, v31, c0, v86, v113, v30, c0, v85, v113, v28, c0, v82, v113));
        list.add(new FaceData(v31, c0, v86, v114, v27, c0, v80, v114, v24, c0, v77, v114, v30, c0, v85, v114));
        list.add(new FaceData(v24, c0, v89, v110, v25, c0, v78, v110, v28, c0, v88, v110, v30, c0, v87, v110));
        list.add(new FaceData(v27, c0, v87, v115, v31, c0, v91, v115, v29, c0, v90, v115, v26, c0, v88, v115));
        return list;
    }

    private static final Vector4f c1 = new Vector4f(1F, 1F, 1F, 0.5F);

    public static List<FaceData> getBladeOuter1Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v35, c1, v95, v111, v34, c1, v94, v111, v33, c1, v93, v111, v32, c1, v92, v111));
        list.add(new FaceData(v34, c1, v98, v112, v37, c1, v97, v112, v36, c1, v96, v112, v33, c1, v89, v112));
        list.add(new FaceData(v37, c1, v97, v113, v39, c1, v100, v113, v38, c1, v99, v113, v36, c1, v96, v113));
        list.add(new FaceData(v39, c1, v100, v114, v35, c1, v95, v114, v32, c1, v92, v114, v38, c1, v99, v114));
        list.add(new FaceData(v32, c1, v102, v110, v33, c1, v93, v110, v36, c1, v101, v110, v38, c1, v69, v110));
        return list;
    }

    private static final Vector4f c2 = new Vector4f(1F, 1F, 1F, 1F);

    public static List<FaceData> getBladeOuterMode1Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v38, c2, v92, v111, v39, c2, v95, v111, v37, c2, v94, v111, v36, c2, v93, v111));
        list.add(new FaceData(v32, c2, v89, v112, v35, c2, v98, v112, v39, c2, v97, v112, v38, c2, v96, v112));
        list.add(new FaceData(v33, c2, v96, v113, v34, c2, v97, v113, v35, c2, v100, v113, v32, c2, v99, v113));
        list.add(new FaceData(v36, c2, v99, v114, v37, c2, v100, v114, v34, c2, v95, v114, v33, c2, v92, v114));
        list.add(new FaceData(v32, c2, v101, v115, v38, c2, v93, v115, v36, c2, v102, v115, v33, c2, v69, v115));
        list.add(new FaceData(v21, c2, v50, v110, v22, c2, v51, v110, v23, c2, v53, v110, v20, c2, v55, v110));
        return list;
    }

    private static final Vector4f c3 = new Vector4f(0.75F, 0.75F, 0.75F, 1F);

    public static List<FaceData> getHiltFaces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v3, c3, v51, v110, v2, c3, v50, v110, v1, c3, v49, v110, v0, c3, v48, v110));
        list.add(new FaceData(v5, c3, v53, v110, v3, c3, v51, v110, v0, c3, v48, v110, v4, c3, v52, v110));
        list.add(new FaceData(v2, c3, v50, v110, v7, c3, v55, v110, v6, c3, v54, v110, v1, c3, v49, v110));
        list.add(new FaceData(v6, c3, v54, v110, v7, c3, v55, v110, v5, c3, v53, v110, v4, c3, v52, v110));
        list.add(new FaceData(v9, c3, v59, v111, v8, c3, v58, v111, v1, c3, v57, v111, v6, c3, v56, v111));
        list.add(new FaceData(v8, c3, v62, v112, v10, c3, v61, v112, v0, c3, v48, v112, v1, c3, v60, v112));
        list.add(new FaceData(v10, c3, v61, v113, v11, c3, v63, v113, v4, c3, v52, v113, v0, c3, v48, v113));
        list.add(new FaceData(v11, c3, v65, v114, v9, c3, v59, v114, v6, c3, v56, v114, v4, c3, v64, v114));
        list.add(new FaceData(v11, c3, v56, v115, v10, c3, v64, v115, v8, c3, v62, v115, v9, c3, v61, v115));
        list.add(new FaceData(v15, c3, v49, v111, v14, c3, v68, v111, v13, c3, v67, v111, v12, c3, v66, v111));
        list.add(new FaceData(v14, c3, v72, v112, v17, c3, v71, v112, v16, c3, v70, v112, v13, c3, v69, v112));
        list.add(new FaceData(v17, c3, v71, v113, v19, c3, v74, v113, v18, c3, v73, v113, v16, c3, v70, v113));
        list.add(new FaceData(v19, c3, v74, v114, v15, c3, v49, v114, v12, c3, v66, v114, v18, c3, v73, v114));
        list.add(new FaceData(v19, c3, v66, v115, v17, c3, v73, v115, v14, c3, v76, v115, v15, c3, v75, v115));
        return list;
    }

    private static final Vector4f c4 = new Vector4f(0.75F, 0.75F, 0.75F, 1F);

    public static List<FaceData> getHilt2Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v21, c4, v50, v110, v22, c4, v51, v110, v23, c4, v53, v110, v20, c4, v55, v110));
        return list;
    }

    private static final Vector4f c5 = new Vector4f(1F, 1F, 1F, 0.9F);

    public static List<FaceData> getBladeInnerFaces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v27, c5, v80, v111, v26, c5, v79, v111, v25, c5, v78, v111, v24, c5, v77, v111));
        list.add(new FaceData(v26, c5, v84, v112, v29, c5, v83, v112, v28, c5, v82, v112, v25, c5, v81, v112));
        list.add(new FaceData(v29, c5, v83, v113, v31, c5, v86, v113, v30, c5, v85, v113, v28, c5, v82, v113));
        list.add(new FaceData(v31, c5, v86, v114, v27, c5, v80, v114, v24, c5, v77, v114, v30, c5, v85, v114));
        list.add(new FaceData(v24, c5, v89, v110, v25, c5, v78, v110, v28, c5, v88, v110, v30, c5, v87, v110));
        list.add(new FaceData(v27, c5, v87, v115, v31, c5, v91, v115, v29, c5, v90, v115, v26, c5, v88, v115));
        return list;
    }

    private static final Vector4f c6 = new Vector4f(1F, 1F, 1F, 0.25F);

    public static List<FaceData> getBladeOuter2Faces() {
        List<FaceData> list = new ArrayList<>();
        list.add(new FaceData(v43, c6, v104, v111, v42, c6, v103, v111, v41, c6, v93, v111, v40, c6, v92, v111));
        list.add(new FaceData(v42, c6, v106, v112, v45, c6, v105, v112, v44, c6, v96, v112, v41, c6, v89, v112));
        list.add(new FaceData(v45, c6, v105, v113, v47, c6, v107, v113, v46, c6, v99, v113, v44, c6, v96, v113));
        list.add(new FaceData(v47, c6, v107, v114, v43, c6, v104, v114, v40, c6, v92, v114, v46, c6, v99, v114));
        list.add(new FaceData(v40, c6, v102, v110, v41, c6, v93, v110, v44, c6, v101, v110, v46, c6, v69, v110));
        list.add(new FaceData(v47, c6, v109, v115, v45, c6, v108, v115, v42, c6, v101, v115, v43, c6, v69, v115));
        return list;
    }
}
