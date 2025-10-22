package com.mrbysco.allyeffect.registry;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.effect.AllyEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllyRegistry {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AllyEffectMod.MOD_ID);

	public static final RegistryObject<MobEffect> ALLY = EFFECTS.register("allyship", AllyEffect::new);
}
