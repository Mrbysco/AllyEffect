package com.mrbysco.allyeffect.effect;

import com.mrbysco.allyeffect.AllyEffectMod;
import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.handler.FunctionHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.functions.CommandFunction;
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
			.selector((entity, level) -> entity.is(AllyEffectMod.RECEIVERS)).ignoreLineOfSight();

	public AllyEffect() {
		super(MobEffectCategory.NEUTRAL, 5882118);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration >= 1;
	}

	private int durationCounter = 0;

	@Override
	public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity mob, int amplification) {
		List<LivingEntity> nearbyEntities = serverLevel.getNearbyEntities(LivingEntity.class,
				receiverCondition.range(AllyConfig.COMMON.effectRange.get()),
				mob, mob.getBoundingBox().inflate(AllyConfig.COMMON.effectRange.get()));
		if (nearbyEntities.isEmpty() && AllyConfig.COMMON.activateOnlyInRange.get()) {
			durationCounter++;
			return true;
		}

		final MinecraftServer server = serverLevel.getServer();
		final ServerFunctionManager functions = server.getFunctions();
		if (durationCounter % AllyConfig.COMMON.giverEffectFrequency.get() == 0) {
			FunctionHandler.getGiverFunction().ifPresent(giverFunction ->
					functions.execute(giverFunction, server.createCommandSourceStack()
							.withEntity(mob)
							.withPosition(mob.position())
							.withRotation(mob.getRotationVector())
							.withSuppressedOutput())
			);
		}
		if (durationCounter % AllyConfig.COMMON.receiverEffectFrequency.get() == 0) {
			CommandFunction<CommandSourceStack> receiverFunction = FunctionHandler.getReceiverFunction().orElse(null);
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
		durationCounter++;

		return true;
	}
}
