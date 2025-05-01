package kasisuno.wonderwork.entity.effect.trivial;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CustomStatusEffectInstance extends StatusEffectInstance	// XXX: remove this class?
{
	public static final String MAIN_KEY = "wonderwork:custom_data";
	
	public CustomStatusEffectInstance(StatusEffect type)
	{
		super(type);
	}
	
	public CustomStatusEffectInstance(StatusEffect type, int duration)
	{
		super(type, duration);
	}
	
	public CustomStatusEffectInstance(StatusEffect type, int duration, int amplifier)
	{
		super(type, duration, amplifier);
	}
	
	public CustomStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean visible)
	{
		super(type, duration, amplifier, ambient, visible);
	}
	
	public CustomStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon)
	{
		super(type, duration, amplifier, ambient, showParticles, showIcon);
	}
	
	public CustomStatusEffectInstance(StatusEffect type, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, @Nullable StatusEffectInstance hiddenEffect, Optional<FactorCalculationData> factorCalculationData)
	{
		super(type, duration, amplifier, ambient, showParticles, showIcon, hiddenEffect, factorCalculationData);
	}
	
	public CustomStatusEffectInstance(StatusEffectInstance instance)
	{
		super(instance);
	}
	
	@Override
	public NbtCompound writeNbt(NbtCompound nbt)
	{
		nbt.put(MAIN_KEY, custom_data_write());
		return super.writeNbt(nbt);
	}
	
	protected NbtCompound custom_data_write()
	{
		return new NbtCompound();
	}
}