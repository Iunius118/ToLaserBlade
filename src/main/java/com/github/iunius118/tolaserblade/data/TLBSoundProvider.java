package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.util.ModSoundEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.IOException;

public class TLBSoundProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    private final String modid;

    public TLBSoundProvider(DataGenerator generatorIn) {
        this(generatorIn, ToLaserBlade.MOD_ID);
    }

    public TLBSoundProvider(DataGenerator generatorIn, String modidIn) {
        generator = generatorIn;
        modid = modidIn;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        JsonObject jsonSoundsRoot = new JsonObject();

        // Add sound of swinging DX Laser B1ade very easily
        JsonArray sounds = new JsonArray();
        sounds.add("tolaserblade:item/dx_laser_blade/swing");
        JsonObject soundEventItemDXLaserBladeSwing = new JsonObject();
        soundEventItemDXLaserBladeSwing.add("sounds", sounds);
        jsonSoundsRoot.add(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING.getName().getPath(), soundEventItemDXLaserBladeSwing);

        try {
            IDataProvider.save(GSON, cache, jsonSoundsRoot, generator.getOutputFolder().resolve("assets/" + modid + "/sounds.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return "ToLaserBlade Sounds";
    }
}
