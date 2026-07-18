package com.mrbysco.allyeffect.datagen.client;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.Nullable;

public class AllyLanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

	public AllyLanguageProvider(PackOutput packOutput) {
		super(packOutput, AllyEffectMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addEffect(AllyRegistry.ALLY, "Allyship");

		add("key.allyeffect.category", "Allyship Effect");
		add("key.allyeffect.ally_effect", "Trigger Ally Effect");

		addConfig("effect", "Effect", "Effect Settings");
		addConfig("effectRange", "Effect Range", "The range the ally effect checks for other entities (Default: 3)");
		addConfig("activateOnlyInRange", "Activate Only In Range", "If true, the effect only activates when another entity is within range (Default: true)");
		addConfig("giverEffectFrequency", "Giver Effect Frequency", "The frequency (in ticks) at which the giver entity receives the effect (Default: 20)");
		addConfig("receiverEffectFrequency", "Receiver Effect Frequency", "The frequency (in ticks) at which the receiver entity receives the effect (Default: 20)");
		addConfig("passive", "Passive", "Passive Settings");
		addConfig("passiveEffectEnabled", "Passive Effect Enabled", "If true, the passive effect is enabled (Default: false)");
		addConfig("passiveEffectRange", "Passive Effect Range", "The range the passive effect checks for other entities (Default: 5)");
		addConfig("passiveEffectFrequency", "Passive Effect Frequency", "The frequency (in ticks) at which the passive effect is applied (Default: 20)");

		addConfig("client", "Client", "Client Settings");
		addConfig("effectAnimation", "Effect Animation", "The animation style for the ally effect (Default: HUG)");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(AllyEffectMod.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(AllyEffectMod.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
