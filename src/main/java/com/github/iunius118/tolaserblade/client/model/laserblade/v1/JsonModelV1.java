package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class JsonModelV1 {
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
