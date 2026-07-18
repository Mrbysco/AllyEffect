package com.mrbysco.allyeffect.handler;

import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.effect.AllyEffect;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber
public class EffectHandler {

	/**
	 * Check if the ally effect is active for the given player.
	 *
	 * @param player The player to check.
	 * @return True if the ally effect is active, false otherwise.
	 */
	public static boolean isAllyActive(Player player) {
		return player.hasData(AllyRegistry.EFFECT_ACTIVE);
	}

	/**
	 * Set the ally effect active state for the given player UUID.
	 *
	 * @param player   The player for whom to set the ally effect state.
	 * @param isActive True to activate the ally effect, false to deactivate.
	 */
	public static void setAllyActive(Player player, boolean isActive) {
		if (isActive) {
			if (!player.hasData(AllyRegistry.EFFECT_ACTIVE)) {
				player.setData(AllyRegistry.EFFECT_ACTIVE, true);
				player.syncData(AllyRegistry.EFFECT_ACTIVE);
			}
		} else {
			player.removeData(AllyRegistry.EFFECT_ACTIVE);
		}
	}

	/**
	 * Handle player tick events to apply the ally effect if active.
	 *
	 * @param event The player tick event.
	 */
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		Player player = event.getEntity();
		if (player instanceof ServerPlayer serverPlayer) {
			CompoundTag persistentData = serverPlayer.getPersistentData();
			if (persistentData.getBoolean("allyeffectDisabled")) {
				setAllyActive(serverPlayer, false);
			}
			if (serverPlayer.level() instanceof ServerLevel serverLevel) {
				if (isAllyActive(serverPlayer)) {
					List<LivingEntity> nearbyEntities = serverLevel.getNearbyEntities(LivingEntity.class,
							AllyEffect.receiverCondition.range(AllyConfig.COMMON.effectRange.get()),
							serverPlayer, serverPlayer.getBoundingBox().inflate(AllyConfig.COMMON.effectRange.get()));
					if ((!nearbyEntities.isEmpty() && AllyConfig.COMMON.activateOnlyInRange.get())) {
						MobEffectInstance instance = new MobEffectInstance(AllyRegistry.ALLY, 20, 0, true, false);
						serverPlayer.addEffect(instance, serverPlayer);
					}
				}

				if (AllyConfig.COMMON.passiveEffectEnabled.get()) {
					List<LivingEntity> nearbyEntities = serverLevel.getNearbyEntities(LivingEntity.class,
							AllyEffect.receiverCondition.range(AllyConfig.COMMON.passiveEffectRange.get()),
							serverPlayer, serverPlayer.getBoundingBox().inflate(AllyConfig.COMMON.passiveEffectRange.get()));
					if (!nearbyEntities.isEmpty()) {
						int passiveFrequency = AllyConfig.COMMON.passiveEffectFrequency.get();
						if (serverPlayer.tickCount % passiveFrequency == 0) {
							CommandFunction<CommandSourceStack> passiveFunction = FunctionHandler.getPassiveFunction().orElse(null);
							if (passiveFunction != null) {
								final MinecraftServer server = serverLevel.getServer();
								final ServerFunctionManager functions = server.getFunctions();
								for (LivingEntity receiver : nearbyEntities) {
									functions.execute(passiveFunction, server.createCommandSourceStack()
											.withEntity(receiver)
											.withPosition(receiver.position())
											.withRotation(receiver.getRotationVector())
											.withSuppressedOutput());
								}
							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffectAdded(MobEffectEvent.Added event) {
		MobEffectInstance instance = event.getEffectInstance();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && instance != null && instance.getEffect() == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the effect to other players
					otherPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), instance, false));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffectRemove(MobEffectEvent.Remove event) {
		Holder<MobEffect> effect = event.getEffect();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && effect == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the removed effect to other players
					otherPlayer.connection.send(new ClientboundRemoveMobEffectPacket(serverPlayer.getId(), AllyRegistry.ALLY));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance instance = event.getEffectInstance();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && instance != null && instance.getEffect() == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the effect expiring to other players
					otherPlayer.connection.send(new ClientboundRemoveMobEffectPacket(serverPlayer.getId(), AllyRegistry.ALLY));
				}
			}
		}
	}
}
