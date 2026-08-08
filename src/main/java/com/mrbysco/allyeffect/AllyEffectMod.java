package com.mrbysco.allyeffect;

import com.mojang.logging.LogUtils;
import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(AllyEffectMod.MOD_ID)
public class AllyEffectMod {
	public static final String MOD_ID = "allyeffect";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final TagKey<EntityType<?>> RECEIVERS = TagKey.create(Registries.ENTITY_TYPE, modLoc("recievers"));

	public AllyEffectMod(IEventBus eventBus, ModContainer container, Dist dist) {
		container.registerConfig(ModConfig.Type.COMMON, AllyConfig.commonSpec);
		eventBus.register(AllyConfig.class);

		AllyRegistry.EFFECTS.register(eventBus);
		AllyRegistry.ATTACHMENT_TYPES.register(eventBus);

		if (dist.isClient()) {
			container.registerConfig(ModConfig.Type.CLIENT, AllyConfig.clientSpec);
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}
