package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.data.lang.EnUsLanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ModLanguageProvider {
	public static void addProviders(GatherDataEvent.Client event) {
		event.createProvider(EnUsLanguageProvider::new);
	}

	private ModLanguageProvider() {}
}
