package com.mrbysco.allyeffect.config;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.client.Animation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class AllyConfig {
	public static class Client {
		public final ForgeConfigSpec.EnumValue<Animation> effectAnimation;
		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client settings")
					.push("client");

			effectAnimation = builder
					.comment("The animation style for the ally effect (Default: HUG)")
					.defineEnum("effectAnimation", Animation.HUG);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static class Common {

		public final ForgeConfigSpec.IntValue effectRange;
		public final ForgeConfigSpec.BooleanValue activateOnlyInRange;
		public final ForgeConfigSpec.IntValue giverEffectFrequency;
		public final ForgeConfigSpec.IntValue receiverEffectFrequency;

		Common(ForgeConfigSpec.Builder builder) {
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

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
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
