package com.mrbysco.allyeffect.datagen;

import com.mrbysco.allyeffect.datagen.client.AllyLanguageProvider;
import com.mrbysco.allyeffect.datagen.server.AllyEntityTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AllyDataGen {
	@SubscribeEvent
	public static void gatherData(net.neoforged.neoforge.data.event.GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(event.includeClient(), new AllyLanguageProvider(packOutput));

		generator.addProvider(event.includeServer(), new AllyEntityTagsProvider(packOutput, lookupProvider, helper));
	}
}
