package kasisuno.wonderwork.mixin;

import kasisuno.wonderwork.entity.effect.WormWindingStatusEffect;
import kasisuno.wonderwork.entity.effect.WormWindingStatusEffectInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements Comparable<StatusEffectInstanceMixin>
{
	//for custom status effect instance data
	@Unique
	private static final String KEY = "wonderwork:custom_data";
	
	@Inject(method = "fromNbt(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/effect/StatusEffectInstance;",
			at = @At("HEAD"), cancellable = true)
	private static void wonderwork$customInstanceFromNbt(NbtCompound nbt, CallbackInfoReturnable<StatusEffectInstance> cir)
	{
		int i = nbt.getInt("Id");
		StatusEffect statusEffect = StatusEffect.byRawId(i);
		if (statusEffect != null)
		{
			if (statusEffect instanceof WormWindingStatusEffect)
			{
				cir.setReturnValue(new WormWindingStatusEffectInstance(Math.max(nbt.getByte("Amplifier"), 0),
						nbt.getCompound(KEY)));
			}
			
			//maybe others?...
		}
	}
}