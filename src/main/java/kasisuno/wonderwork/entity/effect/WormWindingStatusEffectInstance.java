package kasisuno.wonderwork.entity.effect;

import kasisuno.wonderwork.block.WormBlock;
import kasisuno.wonderwork.entity.effect.trivial.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class WormWindingStatusEffectInstance extends /*Custom*/StatusEffectInstance
{
//	private int prev_duration = -1;
//	private int duration = -1;
//	private int history_fade_in_ticks = 0;
	
	//private int overlay_progress = 0;
	
	/**
	 * no duration means infinite duration
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
	
	/**
	 * for deserialization
	 */
//	public WormWindingStatusEffectInstance(int amplifier, NbtCompound data)
//	{
//		super(ModStatusEffects.WORM_WINDING, INFINITE, amplifier,
//				true, false, true);
//
//		//deserialization
////		prev_duration = data.getInt("PrevDuration");
////		duration = data.getInt("Duration");
////		history_fade_in_ticks = data.getInt("HistoryFadeInTicks");
//
//		//overlay_progress = data.getInt("OverlayProgress");
//	}

//	@Override
//	protected NbtCompound custom_data_write()
//	{
//		NbtCompound data = new NbtCompound();
////		data.putInt("PrevDuration", prev_duration);
////		data.putInt("Duration", duration);
////		data.putInt("HistoryFadeInTicks", history_fade_in_ticks);
//
//		data.putInt("OverlayProgress", overlay_progress);
//		return data;
//	}
	@Override
	public void applyUpdateEffect(LivingEntity entity)
	{
		// runs every frame
		if (!entity.getWorld().isClient && entity instanceof PlayerEntity player)
		{
			WormWindingNbtHelper.mapPlayerOverlayProgress(player, val ->
					MathHelper.clamp(val + (isInfinite() ? 1 : -1),
							0, WormBlock.FADE_OUT_TICKS));
		}
		
		if (WormWindingStatusEffect.isCoolingDown(getDuration(), getAmplifier()))
		{
			super.applyUpdateEffect(entity);
		}

//		duration = prev_duration == -1 && duration == -1 ?
//				0 :	//for init
//				duration + entity.age - prev_duration;
//		prev_duration = entity.age;
//
//		if (!entity.getWorld().isClient)
//		{
//			if (entity instanceof PlayerEntity player)
//			{
//				int fade_in_ticks = WormBlock.WormWindingHelper.getFadeInTicks(player);
//				int current_remaining_ticks = WormBlock.WormWindingHelper.getRemainingTicks(player);
//
//				//
//				player.sendMessage(Text.literal(duration + " " + fade_in_ticks +
//						" " + remaining_ticks + " " + current_remaining_ticks), true);
//				//
//
//				if (history_fade_in_ticks < fade_in_ticks)
//				{
////					if (history_fade_in_ticks + 1 != fade_in_ticks)
////					{
////						Wonderwork.LOGGER.warn("Have the NBT \"WormWinding.fadeInTicks\" been changed by commands?");
////						Wonderwork.LOGGER.info("Current value \"{}\" != prev value \"{}\" + 1, stepping per frame.",
////								fade_in_ticks, history_fade_in_ticks);
////					}
//
//					history_fade_in_ticks = fade_in_ticks;
//
//					if (history_fade_in_ticks > WormBlock.FADE_IN_TICKS &&
//							WormWindingStatusEffect.isCoolingDown(duration, getAmplifier()))
//					{
//						getEffectType().applyUpdateEffect(player, getAmplifier());
//					}
//				}
//				else
//				{
//					if (current_remaining_ticks == WormBlock.FADE_OUT_TICKS)
//					{
//						remaining_ticks = current_remaining_ticks;
//					}
//
//					if (remaining_ticks > 0)
//					{
//						remaining_ticks =
//								Math.max(remaining_ticks - 1WormWindingStatusEffect.getCoolDown(getAmplifier()), 0);
//						WormBlock.WormWindingHelper.setRemainingTicks(player, remaining_ticks);
//					}
//					else
//					{
//						entity.removeStatusEffect(ModStatusEffects.WORM_WINDING);
//					}
//				}
//			}
//			else
//			{
//				if (remaining_ticks == WormBlock.FADE_OUT_TICKS &&
//						WormWindingStatusEffect.isCoolingDown(duration, getAmplifier()))
//				{
//					getEffectType().applyUpdateEffect(entity, getAmplifier());
//				}
//
//				if (remaining_ticks > 0)
//				{
//					remaining_ticks =
//							Math.max(remaining_ticks - 1WormWindingStatusEffect.getCoolDown(getAmplifier()), 0);
//				}
//				else
//				{
//					entity.removeStatusEffect(ModStatusEffects.WORM_WINDING);
//				}
//			}
//		}
	}
}