package com.mrbysco.allyeffect.mixin;

import com.mrbysco.allyeffect.client.AnimationHandler;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> extends AgeableListModel<T> {

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V",
					shift = At.Shift.BEFORE
			)
	)
	private void allyeffect$setupAnim(T livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		HumanoidModel<T> model = (HumanoidModel<T>) (Object) this;
		AnimationHandler.animatePlayer(livingEntity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}
