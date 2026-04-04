package com.mrbysco.allyeffect.config;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.client.Animation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AllyConfig {
	public static class Client {
		public final ModConfigSpec.EnumValue<Animation> effectAnimation;
		Client(ModConfigSpec.Builder builder) {
			builder.comment("Client settings")
					.push("client");

			effectAnimation = builder
					.comment("The animation style for the ally effect (Default: HUG)")
					.defineEnum("effectAnimation", Animation.EVOCATION);

			builder.pop();
		}
	}

	public static final ModConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static class Common {

		public final ModConfigSpec.IntValue effectRange;
		public final ModConfigSpec.BooleanValue activateOnlyInRange;
		public final ModConfigSpec.IntValue giverEffectFrequency;
		public final ModConfigSpec.IntValue receiverEffectFrequency;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("Effect settings")
					.push("effect");

			effectRange = builder
					.comment("The range the ally effect checks for other entities (Default: 3)")
					.defineInRange("effectRange", 3, 0, 64);

			activateOnlyInRange = builder
					.comment("If true, the effect only activates when another entity is within range (Default: true)")
					.define("activateOnlyInRange", true);

			giverEffectFrequency = builder
					.comment("The frequency (in ticks) at which the giver entity receives the effect (Default: 20)")
					.defineInRange("giverEffectFrequency", 20, 1, Integer.MAX_VALUE);

			receiverEffectFrequency = builder
					.comment("The frequency (in ticks) at which the receiver entity receives the effect (Default: 20)")
					.defineInRange("receiverEffectFrequency", 20, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		AllyEffectMod.LOGGER.debug("Loaded Ally Effect's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		AllyEffectMod.LOGGER.warn("Ally Effect's config just got changed on the file system!");
	}
}
