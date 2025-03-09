package kasisuno.wonderwork.entity.effect.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.entity.effect.WormWindingStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects
{
	public static final StatusEffect WORM_WINDING = register_status_effect("worm_winding",
			new WormWindingStatusEffect());
	
	private static StatusEffect register_status_effect(String name_p, StatusEffect status_effect_p)
	{
		return Registry.register(Registries.STATUS_EFFECT, new Identifier(Wonderwork.MOD_ID, name_p), status_effect_p);
	}
	
	public static void registerModStatusEffects()
	{
	
	}
}