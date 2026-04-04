package com.mrbysco.allyeffect.mixin;

import com.mrbysco.allyeffect.client.AnimationHandler;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends HumanoidRenderState> extends EntityModel<T> {

	protected HumanoidModelMixin(ModelPart root) {
		super(root);
	}

	@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
					shift = At.Shift.BEFORE
			)
	)
	private void allyeffect$setupAnim(T state, CallbackInfo ci) {
		HumanoidModel<T> model = (HumanoidModel<T>) (Object) this;
		AnimationHandler.animatePlayer(model, state);
	}
}
