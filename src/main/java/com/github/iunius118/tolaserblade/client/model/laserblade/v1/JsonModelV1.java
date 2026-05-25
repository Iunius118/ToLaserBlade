package com.github.iunius118.tolaserblade.client.model.laserblade.v1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonModelV1 {
    public String type = "";
    public int id = -1;
    @SerializedName("renderer_id")
    public int rendererID = 0;
    @SerializedName("gui_resize")
    public float[] guiResize = null;
    public List<JsonModelObject> objects = List.of();
    public List<int[]> faces = List.of();
    public List<float[]> vertices = List.of();
    public List<float[]> normals = List.of();
    public List<float[]> colors = List.of();

    public static class JsonModelObject {
        public Type type = Type.DEFAULT;
        public String name = "";
        public Part part = Part.DEFAULT;
        public State state = State.ANY;
        public int from = 0;
        public int size = 0;

        public enum Type {
            @SerializedName("default")
            DEFAULT,
            @SerializedName("flat")
            FLAT,
            @SerializedName("unlit")
            UNLIT,
            @SerializedName("add")
            ADD,
            @SerializedName("function")
            FUNCTION,
        }

        public enum Part {
            @SerializedName("default")
            DEFAULT,
            @SerializedName("off")
            OFF,
            @SerializedName("grip")
            GRIP,
            @SerializedName("blade_in")
            BLADE_IN,
            @SerializedName("blade_out")
            BLADE_OUT,
        }

        public enum State {
            @SerializedName("any")
            ANY,
            @SerializedName("on")
            ONLY_WORKING,
            @SerializedName("off")
            ONLY_BROKEN,
        }
    }
}
