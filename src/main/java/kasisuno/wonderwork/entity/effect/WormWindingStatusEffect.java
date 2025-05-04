package kasisuno.wonderwork.entity.effect;

import kasisuno.wonderwork.entity.trivial.ManaNbtManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
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
		//int i = 25 >> amplifier;	//25 = 0b11001, 0b1100 = 12, 0b110 = 6, 0b11 = 3
		
		return true;	// to ensure visual effect updates every frame
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier)
	{
		if (ManaNbtManager.isManaUsable(entity) && Random.create().nextBoolean())
		{
			ManaNbtManager.mapMana(entity, val -> val - 1);
		}
		
		entity.damage(entity.getDamageSources().cramming(), 1 + MathHelper.sqrt(amplifier) / 2);
	}
	
	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier)
	{
		if (!entity.getWorld().isClient)	// in server world
		{
			if (entity instanceof PlayerEntity player)
			{
				//WormWindingNbtHelper.setPlayerOverlayProgress(player, 0);
				
				
				
				// ?
			}
		}
		
		super.onRemoved(entity, attributes, amplifier);
	}
	
	/**
	 * waiting for cool down
	 */
	public static boolean isCoolingDown(int duration, int amplifier)
	{
		int i = getCoolDown(amplifier);    //25, 20, 18, 17, 17, ...
		return i <= 0 || duration % i == 0;
	}
	
	public static int getCoolDown(int amplifier)
	{
		return 15 + 10 / (amplifier + 1);
	}
}