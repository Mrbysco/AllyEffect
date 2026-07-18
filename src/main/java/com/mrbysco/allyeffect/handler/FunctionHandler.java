package com.mrbysco.allyeffect.handler;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.effect.AllyEffect;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.Optional;

@EventBusSubscriber
public class FunctionHandler {
	private static final ResourceLocation GIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "giver");
	private static final ResourceLocation RECEIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "receiver");
	private static final ResourceLocation PASSIVE_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "passive");
	private static Optional<CommandFunction<CommandSourceStack>> giverFunction = Optional.empty();
	private static Optional<CommandFunction<CommandSourceStack>> receiverFunction = Optional.empty();
	private static Optional<CommandFunction<CommandSourceStack>> passiveFunction = Optional.empty();

	@SubscribeEvent
	public static void onServerStarted(ServerStartedEvent event) {
		final MinecraftServer server = event.getServer();
		final ServerFunctionManager functions = server.getFunctions();

		giverFunction = functions.get(GIVER_FUNCTION);
		if (!giverFunction.isPresent()) {
			AllyEffectMod.LOGGER.error("Failed to load giver function! Please check your function file!");
		}
		receiverFunction = functions.get(RECEIVER_FUNCTION);
		if (!receiverFunction.isPresent()) {
			AllyEffectMod.LOGGER.error("Failed to load receiver function! Please check your function file!");
		}
		passiveFunction = functions.get(PASSIVE_FUNCTION);
		if (!passiveFunction.isPresent()) {
			AllyEffectMod.LOGGER.error("Failed to load passive function! Please check your function file!");
		}
	}

	public static Optional<CommandFunction<CommandSourceStack>> getGiverFunction() {
		return giverFunction;
	}

	public static Optional<CommandFunction<CommandSourceStack>> getReceiverFunction() {
		return receiverFunction;
	}

	public static Optional<CommandFunction<CommandSourceStack>> getPassiveFunction() {
		return passiveFunction;
	}
}
