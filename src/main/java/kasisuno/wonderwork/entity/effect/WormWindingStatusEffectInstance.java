package kasisuno.wonderwork.entity.effect;

import kasisuno.wonderwork.block.WormBlock;
import kasisuno.wonderwork.entity.effect.trivial.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class WormWindingStatusEffectInstance extends /*Custom*/StatusEffectInstance
{
	/**
	 * no duration means the duration is infinite
	 */
	public WormWindingStatusEffectInstance(int amplifier)
	{
		this(amplifier, INFINITE);
	}
	
	public WormWindingStatusEffectInstance(int amplifier, int duration)
	{
		super(ModStatusEffects.WORM_WINDING, duration, amplifier,
				true, false, true);
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity)
	{
		// runs every frame
		if (!entity.getWorld().isClient)
		{
			WormWindingNbtHelper.mapEffectDuration(entity, val -> val + 1);
			
			if (entity instanceof PlayerEntity player)
			{
				WormWindingNbtHelper.mapPlayerOverlayProgress(player, val ->
						MathHelper.clamp(val + (isInfinite() ? 1 : -1),
								0, WormBlock.FADE_OUT_TICKS));
			}
		}
		
		if (WormWindingStatusEffect.isCoolingDown(WormWindingNbtHelper.getEffectDuration(entity), getAmplifier()))
		{
			super.applyUpdateEffect(entity);
		}
	}
}