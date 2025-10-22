package com.mrbysco.allyeffect.client;

import com.mrbysco.allyeffect.config.AllyConfig;
import com.mrbysco.allyeffect.registry.AllyRegistry;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AnimationHandler {

	public static void animatePlayer(LivingEntity livingEntity, HumanoidModel<?> humanoidModel,
	                                 float limbSwing, float limbSwingAmount, float ageInTicks,
	                                 float netHeadYaw, float headPitch) {
		if (!(livingEntity instanceof Player player)) return; // Only animate players
		if (!(humanoidModel instanceof PlayerModel<?> playerModel)) return;
		if (!livingEntity.hasEffect(AllyRegistry.ALLY.get())) return; // Only animate if the player has the Ally effect

		Animation animation = AllyConfig.CLIENT.effectAnimation.get();
		switch (animation) {
			case EVOCATION -> animateEvocation(playerModel, player, ageInTicks);
			case DANCE -> animateDance(playerModel, player, ageInTicks);
			default -> animateHug(playerModel, player, ageInTicks);
		}
	}

	private static void animateHug(PlayerModel<?> playerModel, Player player, float ageInTicks) {
		AnimationUtils.animateZombieArms(playerModel.leftArm, playerModel.rightArm,
				false, 0, ageInTicks);
	}

	private static void animateDance(PlayerModel<?> playerModel, Player player, float ageInTicks) {
		float f3 = ageInTicks / 60.0F;
		playerModel.head.x = Mth.sin(f3 * 10.0F);
		playerModel.head.y = Mth.sin(f3 * 40.0F) + 0.4F;
		playerModel.rightArm.zRot = ((float)Math.PI / 180F) * (70.0F + Mth.cos(f3 * 40.0F) * 10.0F);
		playerModel.leftArm.zRot = playerModel.rightArm.zRot * -1.0F;
		playerModel.rightArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
		playerModel.leftArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
		playerModel.body.y = Mth.sin(f3 * 40.0F) * 0.35F;
	}

	private static void animateEvocation(PlayerModel<?> playerModel, Player player, float ageInTicks) {
		playerModel.rightArm.z = 0.0F;
		playerModel.rightArm.x = -5.0F;
		playerModel.leftArm.z = 0.0F;
		playerModel.leftArm.x = 5.0F;
		playerModel.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
		playerModel.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
		playerModel.rightArm.zRot = 2.3561945F;
		playerModel.leftArm.zRot = -2.3561945F;
		playerModel.rightArm.yRot = 0.0F;
		playerModel.leftArm.yRot = 0.0F;
	}
}
