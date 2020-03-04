package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.En_usLanguageGenerator;
import com.github.iunius118.tolaserblade.data.lang.Ja_jpLanguageGenerator;
import net.minecraft.data.DataGenerator;

public class LanguageGenerator {
    public static void addProviders(DataGenerator gen) {
        gen.addProvider(new En_usLanguageGenerator(gen));
        gen.addProvider(new Ja_jpLanguageGenerator(gen));
        // Add LanguageProviders here or .json files in src/main/resources/assets/tolaserblade/lang directly
    }
}
