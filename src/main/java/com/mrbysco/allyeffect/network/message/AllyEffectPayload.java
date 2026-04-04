package com.mrbysco.allyeffect.network.message;

import com.mrbysco.allyeffect.AllyEffectMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record AllyEffectPayload(boolean active) implements CustomPacketPayload {

	public static final StreamCodec<FriendlyByteBuf, AllyEffectPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			payload -> payload.active,
			AllyEffectPayload::new);
	public static final Type<AllyEffectPayload> ID = new Type<>(AllyEffectMod.modLoc("ally_effect"));

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
