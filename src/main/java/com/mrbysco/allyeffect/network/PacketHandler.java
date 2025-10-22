package com.mrbysco.allyeffect.network;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.network.message.AllyEffectMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(AllyEffectMod.MOD_ID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	private static int id = 0;

	public static void init() {
		CHANNEL.registerMessage(id++, AllyEffectMessage.class, AllyEffectMessage::encode, AllyEffectMessage::decode, AllyEffectMessage::handle);
	}
}
