package com.mrbysco.allyeffect.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerEffectCapability implements IPlayerEffect, ICapabilitySerializable<CompoundTag>, ICapabilityProvider {
	private boolean effectActive;
	
	public PlayerEffectCapability(boolean effectActive) {
		this.effectActive = effectActive;
	}

	public PlayerEffectCapability() {
		this(false);
	}

	@Override
	public boolean isEffectActive() {
		return this.effectActive;
	}

	@Override
	public void setEffectActive(boolean active) {
		this.effectActive = active;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
		return null;
	}

	@Override
	public CompoundTag serializeNBT() {
		return null;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {

	}
}
