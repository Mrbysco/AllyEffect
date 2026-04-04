package com.mrbysco.allyeffect.network;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.network.handler.ServerPayloadHandler;
import com.mrbysco.allyeffect.network.message.AllyEffectPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class PacketHandler {


	@SubscribeEvent
	public static void setupPackets(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(AllyEffectMod.MOD_ID);

		registrar.playToServer(AllyEffectPayload.ID, AllyEffectPayload.CODEC, ServerPayloadHandler.getInstance()::handleEffect);
	}
}
