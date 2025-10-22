package com.mrbysco.allyeffect.handler;

import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
public class FunctionHandler {
	private static final ResourceLocation GIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "giver");
	private static final ResourceLocation RECEIVER_FUNCTION = ResourceLocation.fromNamespaceAndPath("allyeffect", "receiver");
	private static Optional<CommandFunction> giverFunction = Optional.empty();
	private static Optional<CommandFunction> receiverFunction = Optional.empty();

	@SubscribeEvent
	public static void onServerStarted(ServerStartedEvent event) {
		final MinecraftServer server = event.getServer();
		final ServerFunctionManager functions = server.getFunctions();

		giverFunction = functions.get(GIVER_FUNCTION);
		receiverFunction = functions.get(RECEIVER_FUNCTION);
	}

	public static Optional<CommandFunction> getGiverFunction() {
		return giverFunction;
	}

	public static Optional<CommandFunction> getReceiverFunction() {
		return receiverFunction;
	}
}
