package com.mrbysco.allyeffect.datagen.server;

import com.mrbysco.allyeffect.AllyEffectMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityTypes;

import java.util.concurrent.CompletableFuture;

public class AllyEntityTagsProvider extends EntityTypeTagsProvider {
	public AllyEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
		super(output, provider, AllyEffectMod.MOD_ID);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(AllyEffectMod.RECEIVERS)
				.add(
						EntityTypes.PLAYER.builtInRegistryHolder().key(),
						EntityTypes.VILLAGER.builtInRegistryHolder().key(),
						EntityTypes.WOLF.builtInRegistryHolder().key()
				);
	}
}
