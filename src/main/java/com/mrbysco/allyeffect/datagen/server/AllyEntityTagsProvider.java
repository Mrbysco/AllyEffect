package com.mrbysco.allyeffect.datagen.server;

import com.mrbysco.allyeffect.AllyEffectMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class AllyEntityTagsProvider extends EntityTypeTagsProvider {
	public AllyEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
		super(output, provider, AllyEffectMod.MOD_ID, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(AllyEffectMod.RECEIVERS)
				.add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WOLF);
	}
}
