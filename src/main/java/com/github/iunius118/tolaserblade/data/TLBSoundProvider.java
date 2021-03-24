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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TLBSoundProvider implements IDataProvider {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    private final String modid;
    private final List<SoundEvent> soundEvents = new ArrayList<>();

    public TLBSoundProvider(DataGenerator generatorIn) {
        this(generatorIn, ToLaserBlade.MOD_ID);
    }

    public TLBSoundProvider(DataGenerator generatorIn, String modidIn) {
        generator = generatorIn;
        modid = modidIn;
    }

    public void registerSoundEvents() {
        addSoundEvent(ModSoundEvents.ITEM_DX_LASER_BLADE_SWING);
        addSoundEvent(ModSoundEvents.ITEM_LASER_BLADE_SWING);
        addSoundEvent(ModSoundEvents.ITEM_LASER_BLADE_FP_SWING);
    }

    public void addSoundEvent(SoundEvent soundEvent) {
        soundEvents.add(soundEvent);
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        registerSoundEvents();
        JsonObject jsonSoundsRoot = new JsonObject();

        for (SoundEvent soundEvent : soundEvents) {
            addSoundEventToJson(soundEvent, jsonSoundsRoot);
        }

        IDataProvider.save(GSON, cache, jsonSoundsRoot, generator.getOutputFolder().resolve("assets/" + modid + "/sounds.json"));
    }

    private void addSoundEventToJson(SoundEvent soundEvent, JsonObject jsonObject) {
        // Add sound events of Laser Blades very easily
        ResourceLocation soundName = soundEvent.getLocation();
        String soundPath = soundName.toString().replace(".", "/");
        JsonArray sounds = new JsonArray();
        sounds.add(soundPath);

        JsonObject soundEventJson = new JsonObject();
        soundEventJson.add("sounds", sounds);

        jsonObject.add(soundName.getPath(), soundEventJson);
    }

    @Override
    public String getName() {
        return "ToLaserBlade Sounds";
    }
}
