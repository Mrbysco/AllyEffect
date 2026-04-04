package com.mrbysco.allyeffect.client;

import com.mrbysco.allyeffect.config.AllyConfig;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.item.SwingAnimationType;

public class AnimationHandler {

	public static void animatePlayer(HumanoidModel<?> humanoidModel, HumanoidRenderState renderState) {
		if (!(renderState instanceof AvatarRenderState avatarRenderState)) return; // Only animate players
		if (!(humanoidModel instanceof PlayerModel playerModel)) return;
		if (!avatarRenderState.getRenderDataOrDefault(ClientHandler.HAS_EFFECT, false))
			return; // Only animate if the player has the Ally effect

		Animation animation = AllyConfig.CLIENT.effectAnimation.get();
		switch (animation) {
			case EVOCATION -> animateEvocation(playerModel, avatarRenderState);
			case DANCE -> animateDance(playerModel, avatarRenderState);
			default -> animateHug(playerModel, avatarRenderState);
		}
	}

	private static void animateHug(PlayerModel playerModel, AvatarRenderState state) {
		boolean animateAttack = state.swingAnimationType != SwingAnimationType.STAB;
		if (animateAttack) {
			float attackTime = state.attackTime;
			float armDrop = -(float) Math.PI / 1.5F;
			float attackYRotModifier = Mth.sin((double) (attackTime * (float) Math.PI));
			float attackXRotModifier = Mth.sin((double) ((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * (float) Math.PI));
			playerModel.rightArm.zRot = 0.0F;
			playerModel.rightArm.yRot = -(0.1F - attackYRotModifier * 0.6F);
			playerModel.rightArm.xRot = armDrop;
			playerModel.rightArm.xRot += attackYRotModifier * 1.2F - attackXRotModifier * 0.4F;
			playerModel.leftArm.zRot = 0.0F;
			playerModel.leftArm.yRot = 0.1F - attackYRotModifier * 0.6F;
			playerModel.leftArm.xRot = armDrop;
			playerModel.leftArm.xRot += attackYRotModifier * 1.2F - attackXRotModifier * 0.4F;
		}
	}

	private static void animateDance(PlayerModel playerModel, AvatarRenderState renderState) {
		float f3 = renderState.ageInTicks / 60F;
		playerModel.head.x = Mth.sin(f3 * 10.0F);
		playerModel.head.y = Mth.sin(f3 * 40.0F) + 0.4F;
		playerModel.rightArm.zRot = ((float) Math.PI / 180F) * (70.0F + Mth.cos(f3 * 40.0F) * 10.0F);
		playerModel.leftArm.zRot = playerModel.rightArm.zRot * -1.0F;
		playerModel.rightArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
		playerModel.leftArm.y = Mth.sin(f3 * 40.0F) * 0.5F + 1.5F;
		playerModel.body.y = Mth.sin(f3 * 40.0F) * 0.35F;
	}

	private static void animateEvocation(PlayerModel playerModel, AvatarRenderState renderState) {
		playerModel.rightArm.z = 0.0F;
		playerModel.rightArm.x = -5.0F;
		playerModel.leftArm.z = 0.0F;
		playerModel.leftArm.x = 5.0F;
		playerModel.rightArm.xRot = Mth.cos(renderState.ageInTicks * 0.6662F) * 0.25F;
		playerModel.leftArm.xRot = Mth.cos(renderState.ageInTicks * 0.6662F) * 0.25F;
		playerModel.rightArm.zRot = 2.3561945F;
		playerModel.leftArm.zRot = -2.3561945F;
		playerModel.rightArm.yRot = 0.0F;
		playerModel.leftArm.yRot = 0.0F;
	}
}
