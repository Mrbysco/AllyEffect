package com.mrbysco.allyeffect.network.handler;

import com.mrbysco.allyeffect.handler.EffectHandler;
import com.mrbysco.allyeffect.network.message.AllyEffectPayload;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
	public static final ServerPayloadHandler INSTANCE = new ServerPayloadHandler();

	public static ServerPayloadHandler getInstance() {
		return INSTANCE;
	}

	public void handleEffect(final AllyEffectPayload effectPayload, final IPayloadContext context) {
		context.enqueueWork(() -> {
					if (context.player() != null) {
						EffectHandler.setAllyActive(context.player(), effectPayload.active());
					}
				})
				.exceptionally(e -> {
					context.disconnect(Component.translatable("allyeffect.networking.ally_effect.failed", e.getMessage()));
					return null;
				});
	}
}
