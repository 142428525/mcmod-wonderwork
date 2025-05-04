package kasisuno.wonderwork.entity.effect;

import kasisuno.wonderwork.entity.trivial.ManaNbtManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class WormWindingStatusEffect extends StatusEffect
{
	public WormWindingStatusEffect()
	{
		super(StatusEffectCategory.HARMFUL, 0x600000);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier)
	{
		return true;    // to ensure those logics update every frame
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier)
	{
		if (ManaNbtManager.isManaUsable(entity) && Random.create().nextBoolean())
		{
			ManaNbtManager.mapMana(entity, val -> val - 1);
		}
		
		entity.damage(entity.getDamageSources().cramming(),
				1 + MathHelper.sqrt(Math.max(amplifier - 1, 0)) / 2);
	}
	
	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier)
	{
		if (!entity.getWorld().isClient)    // in server world
		{
			if (WormWindingNbtHelper.getDoSkipEffectRemoveOnce(entity))
			{
				WormWindingNbtHelper.setDoSkipEffectRemoveOnce(entity, false);
			}
			else
			{
				WormWindingNbtHelper.setEffectDuration(entity, 0);
			}
		}
		
		super.onRemoved(entity, attributes, amplifier);
	}
	
	/**
	 * waiting for cool down
	 */
	public static boolean isCoolingDown(int duration, int amplifier)
	{
		int i = getCoolDown(amplifier);    //50, 40, 36, 35
		return i <= 0 || duration % i == 0;
	}
	
	public static int getCoolDown(int amplifier)
	{
		return 25 + 25 / (amplifier + 1);
	}
}