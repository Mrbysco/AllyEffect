package com.mrbysco.allyeffect.datagen.client;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class AllyLanguageProvider extends LanguageProvider {

	public AllyLanguageProvider(PackOutput packOutput) {
		super(packOutput, AllyEffectMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addEffect(AllyRegistry.ALLY, "Allyship");

		add("key.allyeffect.category", "Allyship Effect");
		add("key.allyeffect.ally_effect", "Trigger Ally Effect");
	}
}
