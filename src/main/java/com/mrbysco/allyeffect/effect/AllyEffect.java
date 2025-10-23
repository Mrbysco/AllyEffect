package com.mrbysco.allyeffect.effect;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.handler.FunctionHandler;
import net.minecraft.commands.CommandFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.List;

public class AllyEffect extends MobEffect {
	public static final TargetingConditions receiverCondition = TargetingConditions.forNonCombat()
			.selector(entity -> entity.getType().is(AllyEffectMod.RECEIVERS)).ignoreLineOfSight();

	public AllyEffect() {
		super(MobEffectCategory.NEUTRAL, 5882118);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return duration >= 1;
	}

	private int durationCounter = 0;

	@Override
	public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			List<LivingEntity> nearbyEntities = serverLevel.getNearbyEntities(LivingEntity.class,
					receiverCondition.range(AllyConfig.COMMON.effectRange.get()),
					livingEntity, livingEntity.getBoundingBox().inflate(AllyConfig.COMMON.effectRange.get()));
			if (nearbyEntities.isEmpty() && AllyConfig.COMMON.activateOnlyInRange.get()) {
				durationCounter++;
				return;
			}

			final MinecraftServer server = serverLevel.getServer();
			final ServerFunctionManager functions = server.getFunctions();
			if (durationCounter % AllyConfig.COMMON.giverEffectFrequency.get() == 0) {
				FunctionHandler.getGiverFunction().ifPresent(giverFunction ->
						functions.execute(giverFunction, server.createCommandSourceStack()
								.withEntity(livingEntity)
								.withPosition(livingEntity.position())
								.withRotation(livingEntity.getRotationVector())
								.withSuppressedOutput())
				);
			}
			if (durationCounter % AllyConfig.COMMON.receiverEffectFrequency.get() == 0) {
				CommandFunction receiverFunction = FunctionHandler.getReceiverFunction().orElse(null);
				if (receiverFunction != null) {
					for (LivingEntity receiver : nearbyEntities) {
						functions.execute(receiverFunction, server.createCommandSourceStack()
								.withEntity(receiver)
								.withPosition(receiver.position())
								.withRotation(receiver.getRotationVector())
								.withSuppressedOutput());
					}
				}
			}
		}
		durationCounter++;
	}
}
