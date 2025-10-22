package com.mrbysco.allyeffect;

import com.mojang.logging.LogUtils;
import com.mrbysco.allyeffect.client.KeyHandler;
import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.network.PacketHandler;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AllyEffectMod.MOD_ID)
public class AllyEffectMod {
	public static final String MOD_ID = "allyeffect";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<EntityType<?>> RECEIVERS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "recievers"));

	public AllyEffectMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AllyConfig.commonSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AllyConfig.clientSpec);
		eventBus.register(AllyConfig.class);

		eventBus.addListener(this::setup);

		AllyRegistry.EFFECTS.register(eventBus);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			MinecraftForge.EVENT_BUS.addListener(KeyHandler::onClientTick);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		PacketHandler.init();
	}
}
