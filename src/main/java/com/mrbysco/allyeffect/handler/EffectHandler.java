package com.mrbysco.allyeffect.handler;

import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.effect.AllyEffect;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EffectHandler {
	private static final List<UUID> activeAllies = new ArrayList<>();

	/**
	 * Check if the ally effect is active for the given player UUID.
	 *
	 * @param playerUUID The UUID of the player to check.
	 * @return True if the ally effect is active, false otherwise.
	 */
	public static boolean isAllyActive(UUID playerUUID) {
		return activeAllies.contains(playerUUID);
	}

	/**
	 * Check if the ally effect is active for the given player.
	 *
	 * @param player The player to check.
	 * @return True if the ally effect is active, false otherwise.
	 */
	public static boolean isAllyActive(Player player) {
		return isAllyActive(player.getUUID());
	}

	/**
	 * Set the ally effect active state for the given player UUID.
	 *
	 * @param playerUUID The UUID of the player.
	 * @param isActive   True to activate the ally effect, false to deactivate.
	 */
	public static void setAllyActive(UUID playerUUID, boolean isActive) {
		if (isActive) {
			if (!activeAllies.contains(playerUUID)) {
				activeAllies.add(playerUUID);
			}
		} else {
			activeAllies.remove(playerUUID);
		}
	}

	/**
	 * Handle player tick events to apply the ally effect if active.
	 *
	 * @param event The player tick event.
	 */
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.side.isServer()) {
			Player player = event.player;
			if (!(player instanceof ServerPlayer serverPlayer)) return;
			CompoundTag persistentData = player.getPersistentData();
			if (persistentData.getBoolean("allyeffectDisabled")) {
				setAllyActive(player.getUUID(), false);
			}
			if (!activeAllies.isEmpty() && isAllyActive(player) && player.level() instanceof ServerLevel serverLevel) {
				List<LivingEntity> nearbyEntities = serverLevel.getNearbyEntities(LivingEntity.class,
						AllyEffect.receiverCondition.range(AllyConfig.COMMON.effectRange.get()),
						player, player.getBoundingBox().inflate(AllyConfig.COMMON.effectRange.get()));
				if (nearbyEntities.isEmpty() && AllyConfig.COMMON.activateOnlyInRange.get()) // Check for nearby entities
					return;

				MobEffectInstance instance = new MobEffectInstance(AllyRegistry.ALLY.get(), 20, 0, true, false);
				player.addEffect(instance, player);
			}
		}
	}

	@SubscribeEvent
	public static void onEffectAdded(MobEffectEvent.Added event) {
		MobEffectInstance instance = event.getEffectInstance();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && instance.getEffect() == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the effect to other players
					otherPlayer.connection.send(new ClientboundUpdateMobEffectPacket(serverPlayer.getId(), instance));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffectRemove(MobEffectEvent.Remove event) {
		MobEffectInstance instance = event.getEffectInstance();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && instance.getEffect() == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the removed effect to other players
					otherPlayer.connection.send(new ClientboundRemoveMobEffectPacket(serverPlayer.getId(), AllyRegistry.ALLY.get()));
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance instance = event.getEffectInstance();
		LivingEntity effectEntity = event.getEntity();
		if (effectEntity instanceof ServerPlayer serverPlayer && instance.getEffect() == AllyRegistry.ALLY.get()) {
			ServerLevel serverLevel = serverPlayer.serverLevel();
			for (Entity entity : serverLevel.getEntities().getAll()) {
				if (entity instanceof ServerPlayer otherPlayer) {
					// Sync the effect expiring to other players
					otherPlayer.connection.send(new ClientboundRemoveMobEffectPacket(serverPlayer.getId(), AllyRegistry.ALLY.get()));
				}
			}
		}
	}
}
