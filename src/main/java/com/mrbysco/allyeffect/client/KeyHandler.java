package com.mrbysco.allyeffect.client;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.network.message.AllyEffectPayload;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyHandler {
	public static final KeyMapping KEY_ALLY = new KeyMapping(getKey("ally_effect"), GLFW.GLFW_KEY_LEFT_ALT, getKey("category"));

	private static String getKey(String name) {
		return String.join(".", "key", AllyEffectMod.MOD_ID, name);
	}

	@SubscribeEvent
	public static void registerKeyMapping(final RegisterKeyMappingsEvent event) {
		event.register(KEY_ALLY);
	}

	private static boolean pressed = false;

	public static void onClientTick(ClientTickEvent.Pre event) {
		boolean isDown = KEY_ALLY.isDown();
		if (isDown != pressed) {
			pressed = isDown;
			PacketDistributor.sendToServer(new AllyEffectPayload(pressed));
		}
	}
}
