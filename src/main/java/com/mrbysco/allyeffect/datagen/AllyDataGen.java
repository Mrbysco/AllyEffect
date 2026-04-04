package com.mrbysco.allyeffect.datagen;

import com.mrbysco.allyeffect.datagen.client.AllyLanguageProvider;
import com.mrbysco.allyeffect.datagen.server.AllyEntityTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class AllyDataGen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new AllyLanguageProvider(packOutput));

		generator.addProvider(true, new AllyEntityTagsProvider(packOutput, lookupProvider));
	}
}
