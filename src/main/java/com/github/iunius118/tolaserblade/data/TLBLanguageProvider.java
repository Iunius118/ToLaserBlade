package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.En_usLanguageProvider;
import com.github.iunius118.tolaserblade.data.lang.Ja_jpLanguageProvider;
import net.minecraft.data.DataGenerator;

public class TLBLanguageProvider {
    public static void addProviders(DataGenerator gen) {
        gen.addProvider(new En_usLanguageProvider(gen));
        gen.addProvider(new Ja_jpLanguageProvider(gen));
        // Add LanguageProviders here or .json files in src/main/resources/assets/tolaserblade/lang directly
    }
}
