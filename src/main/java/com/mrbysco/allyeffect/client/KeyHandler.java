package com.mrbysco.allyeffect.client;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.network.PacketHandler;
import com.mrbysco.allyeffect.network.message.AllyEffectMessage;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyHandler {
	public static KeyMapping KEY_ALLY = new KeyMapping(getKey("ally_effect"), GLFW.GLFW_KEY_LEFT_ALT, getKey("category"));
	private static String getKey(String name) {
		return String.join(".", "key", AllyEffectMod.MOD_ID, name);
	}

	@SubscribeEvent
	public static void registerKeyMapping(final RegisterKeyMappingsEvent event) {
		event.register(KEY_ALLY);
	}

	private static boolean pressed = false;
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		boolean isDown = KEY_ALLY.isDown();
		if (isDown != pressed) {
			pressed = isDown;
			PacketHandler.CHANNEL.send(PacketDistributor.SERVER.noArg(), new AllyEffectMessage(pressed));
		}
	}
}
