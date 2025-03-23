package kasisuno.wonderwork.entity.effect;

import kasisuno.wonderwork.block.WormBlock;
import kasisuno.wonderwork.entity.trivial.ManaManager;
import kasisuno.wonderwork.util.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class WormWindingStatusEffect extends StatusEffect
{
//	private int history_fade_in_ticks = 0;
//	private int remaining_ticks = WormBlock.FADE_OUT_TICKS;
//	private int duration = 0;
//	private int amplifier;
	
	public WormWindingStatusEffect()
	{
		super(StatusEffectCategory.HARMFUL, 0x600000);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier)
	{
		//int i = 25 >> amplifier;	//25 = 0b11001, 0b1100 = 12, 0b110 = 6, 0b11 = 3
		return true;
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier)
	{
		/*duration++;
		if (!entity.getWorld().isClient)
		{
			if (entity instanceof PlayerEntity player)
			{
				int fade_in_ticks = WormBlock.WormWindingHelper.getFadeInTicks(player);
				int current_remaining_ticks = WormBlock.WormWindingHelper.getRemainingTicks(player);
				player.sendMessage(Text.literal(duration + " " + fade_in_ticks +
						" " + remaining_ticks + " " + current_remaining_ticks), true);
				if (history_fade_in_ticks < fade_in_ticks)
				{
//					if (history_fade_in_ticks + 1 != fade_in_ticks)
//					{
//						Wonderwork.LOGGER.warn("Have the NBT \"WormWinding.fadeInTicks\" been changed by commands?");
//						Wonderwork.LOGGER.info("Current value \"{}\" != prev value \"{}\" + 1, stepping per frame.",
//								fade_in_ticks, history_fade_in_ticks);
//					}
					
					history_fade_in_ticks = fade_in_ticks;
					
					if (history_fade_in_ticks > WormBlock.FADE_IN_TICKS && can_damage_cd())
					{
						ManaManager.writeMana(player, Math.max(ManaManager.readMana(player) - 1, 0));
						player.damage(player.getDamageSources().cramming(), 1);
					}
				}
				else
				{
					if (current_remaining_ticks == WormBlock.FADE_OUT_TICKS)
					{
						remaining_ticks = current_remaining_ticks;
					}
					
					if (remaining_ticks > 0)
					{
						remaining_ticks--;
						WormBlock.WormWindingHelper.setRemainingTicks(player, remaining_ticks);
					}
					else
					{
						entity.removeStatusEffect(ModStatusEffects.WORM_WINDING);
					}
				}
			}
			else
			{
				if (*//*remaining_ticks == WormBlock.FADE_OUT_TICKS &&*/
		/* can_damage_cd())
				{
					entity.damage(entity.getDamageSources().cramming(), 1);
				}
				
				if (remaining_ticks > 0)
				{
					remaining_ticks--;
				}
				else
				{
					entity.removeStatusEffect(ModStatusEffects.WORM_WINDING);
				}
			}
		}*/
		
		if (entity.getType().isIn(ModTags.Entity.MANA_USABLE))
		{
			ManaManager.changeMana(entity, -1);
		}
		
		entity.damage(entity.getDamageSources().cramming(), 1 + MathHelper.sqrt(amplifier) / 2);
	}
	
	@Override
	public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier)
	{
		if (!entity.getWorld().isClient)
		{
			entity.removeStatusEffect(StatusEffects.BLINDNESS);
			
			if (entity instanceof PlayerEntity player)
			{
				WormBlock.WormWindingHelper.setFadeInTicks(player, 0 /*history_fade_in_ticks*/);
				WormBlock.WormWindingHelper.setRemainingTicks(player, WormBlock.FADE_OUT_TICKS /*remaining_ticks*/);
			}
		}
		
		super.onRemoved(entity, attributes, amplifier);
	}
	
	/**
	 * waiting for cool down
	 */
	public static boolean isCoolingDown(int duration, int amplifier)
	{
		int i = getCoolDown(amplifier);	//25, 20, 18, 17, 17, ...
		return i <= 0 || duration % i == 0;
	}
	
	public static int getCoolDown(int amplifier)
	{
		return 15 + 10 / (amplifier + 1);
	}
}