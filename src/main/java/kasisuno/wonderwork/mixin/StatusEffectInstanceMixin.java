package kasisuno.wonderwork.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements Comparable<StatusEffectInstanceMixin>	// XXX: remove this class?
{
	@Inject(method = "fromNbt(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/effect/StatusEffectInstance;",
			at = @At("HEAD"), cancellable = true)
	private static void wonderwork$customInstanceFromNbt(NbtCompound nbt, CallbackInfoReturnable<StatusEffectInstance> cir)
	{
		int i = nbt.getInt("Id");
		StatusEffect statusEffect = StatusEffect.byRawId(i);
		if (statusEffect != null)
		{
//			if (statusEffect instanceof WormWindingStatusEffect)
//			{
//				cir.setReturnValue(new WormWindingStatusEffectInstance(Math.max(nbt.getByte("Amplifier"), 0),
//						nbt.getCompound(CustomStatusEffectInstance.MAIN_KEY)));
//			}
			
			//maybe others?...
		}
	}
}