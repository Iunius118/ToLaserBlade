package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.sounds.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public void registerSounds() {
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_SWING.value(), true);
        addSingleFileSound(ModSoundEvents.ITEM_LASER_BLADE_HIT.value(), true);
        assignSoundEvent(ModSoundEvents.ITEM_LASER_BLADE_BLOCK.value(), SoundEvents.SHIELD_BLOCK.value(), true);
        assignSoundEvent(ModSoundEvents.ITEM_LASER_BLADE_BREAK.value(), SoundEvents.SHIELD_BREAK.value(), true);
    }

    private void addSingleFileSound(SoundEvent soundEvent, boolean replace) {
        var soundDefinition = definition().with(getSound(soundEvent.location()));

        if (replace) {
            soundDefinition.replace(true);
        }

        add(soundEvent, soundDefinition);
    }

    private void assignSoundEvent(SoundEvent to, SoundEvent from, boolean replace) {
        var soundDefinition = definition().with(sound(from.location(), SoundDefinition.SoundType.EVENT));

        if (replace) {
            soundDefinition.replace(true);
        }

        add(to, soundDefinition);
    }

    private SoundDefinition.Sound getSound(Identifier location) {
        return sound(Identifier.fromNamespaceAndPath(location.getNamespace(), location.getPath().replace(".", "/")));
    }
}
