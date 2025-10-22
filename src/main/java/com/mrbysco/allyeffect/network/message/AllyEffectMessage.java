package com.mrbysco.allyeffect.network.message;

import com.mrbysco.allyeffect.handler.EffectHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AllyEffectMessage {
	private final boolean active;

	public AllyEffectMessage(boolean active) {
		this.active = active;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBoolean(active);
	}

	public static AllyEffectMessage decode(final FriendlyByteBuf packetBuffer) {
		return new AllyEffectMessage(packetBuffer.readBoolean());
	}

	public void handle(Supplier<NetworkEvent.Context> context) {
		NetworkEvent.Context ctx = context.get();
		ctx.enqueueWork(() -> {
			if (ctx.getDirection().getReceptionSide().isServer() && ctx.getSender() != null) {
				ServerPlayer serverPlayer = ctx.getSender();
				EffectHandler.setAllyActive(serverPlayer.getUUID(), active);
			}
		});
		ctx.setPacketHandled(true);
	}

}
