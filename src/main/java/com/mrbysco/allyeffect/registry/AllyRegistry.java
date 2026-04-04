package com.mrbysco.allyeffect.registry;

import com.mojang.serialization.Codec;
import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.effect.AllyEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AllyRegistry {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AllyEffectMod.MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AllyEffectMod.MOD_ID);

	public static final DeferredHolder<MobEffect, MobEffect> ALLY = EFFECTS.register("allyship", AllyEffect::new);

	public static final Supplier<AttachmentType<Boolean>> EFFECT_ACTIVE = ATTACHMENT_TYPES.register("effect_active", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).sync(ByteBufCodecs.BOOL).build());

}
