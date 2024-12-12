package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.En_usLanguageProvider;
import com.github.iunius118.tolaserblade.data.lang.Ja_jpLanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class TLBLanguageProvider {
    public static void addProviders(GatherDataEvent.Client event) {
        var packOutput = event.getGenerator().getPackOutput();

        event.addProvider(new En_usLanguageProvider(packOutput));
        event.addProvider(new Ja_jpLanguageProvider(packOutput));
        // Add LanguageProviders here or .json files in src/main/resources/assets/tolaserblade/lang directly
    }
}
