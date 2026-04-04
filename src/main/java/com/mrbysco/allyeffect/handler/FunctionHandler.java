package com.mrbysco.allyeffect.handler;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Optional;

@EventBusSubscriber
public class FunctionHandler {
	private static final ResourceLocation GIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "giver");
	private static final ResourceLocation RECEIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "receiver");
	private static Optional<CommandFunction<CommandSourceStack>> giverFunction = Optional.empty();
	private static Optional<CommandFunction<CommandSourceStack>> receiverFunction = Optional.empty();

	@SubscribeEvent
	public static void onServerStarted(net.neoforged.neoforge.event.server.ServerStartedEvent event) {
		final MinecraftServer server = event.getServer();
		final ServerFunctionManager functions = server.getFunctions();

		giverFunction = functions.get(GIVER_FUNCTION);
		receiverFunction = functions.get(RECEIVER_FUNCTION);
	}

	public static Optional<CommandFunction<CommandSourceStack>> getGiverFunction() {
		return giverFunction;
	}

	public static Optional<CommandFunction<CommandSourceStack>> getReceiverFunction() {
		return receiverFunction;
	}
}
