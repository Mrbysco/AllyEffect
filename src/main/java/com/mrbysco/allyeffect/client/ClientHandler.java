package com.mrbysco.allyeffect.client;

import com.google.common.reflect.TypeToken;
import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {
	public static final ContextKey<Boolean> IS_PLAYER = new ContextKey<>(AllyEffectMod.modLoc("is_player"));
	public static final ContextKey<Boolean> HAS_EFFECT = new ContextKey<>(AllyEffectMod.modLoc("has_effect"));

	private static final TypeToken<AvatarRenderer<?>> AVATAR_RENDERER = new TypeToken<>() {
	};

	@SubscribeEvent
	public static void registerCustomRenderData(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(
				AVATAR_RENDERER,
				(avatar, state) -> {
					if (avatar instanceof Player) {
						state.setRenderData(IS_PLAYER, true);
						state.setRenderData(HAS_EFFECT, avatar.hasData(AllyRegistry.EFFECT_ACTIVE));
					}
				}
		);
	}
}
