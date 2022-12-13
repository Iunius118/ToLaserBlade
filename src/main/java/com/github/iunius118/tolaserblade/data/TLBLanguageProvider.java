package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.En_usLanguageProvider;
import com.github.iunius118.tolaserblade.data.lang.Ja_jpLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

public class TLBLanguageProvider {
    public static void addProviders(boolean needsRun, DataGenerator dataGenerator, PackOutput packOutput) {
        dataGenerator.addProvider(needsRun, new En_usLanguageProvider(packOutput));
        dataGenerator.addProvider(needsRun, new Ja_jpLanguageProvider(packOutput));
        // Add LanguageProviders here or .json files in src/main/resources/assets/tolaserblade/lang directly
    }
}
