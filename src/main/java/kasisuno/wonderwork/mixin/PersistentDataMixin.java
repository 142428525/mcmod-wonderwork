package kasisuno.wonderwork.mixin;

import kasisuno.wonderwork.entity.trivial.IModPersistentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PersistentDataMixin implements IModPersistentData
{
	@Unique
	private NbtCompound persistent_data;
	
	@Unique
	private static final String KEY = "wonderwork:persistent_data";
	
	@Override
	public NbtCompound getPersistentData()
	{
		if (persistent_data == null)
		{
			persistent_data = new NbtCompound();
		}
		
		return persistent_data;
	}
	
	@Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
	private void wonderwork$writePersistentData(NbtCompound nbt, CallbackInfo ci)
	{
		if (persistent_data != null)
		{
			nbt.put(KEY, persistent_data);
		}
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
	private void wonderwork$readPersistentData(NbtCompound nbt, CallbackInfo ci)
	{
		if (nbt.contains(KEY, NbtElement.COMPOUND_TYPE))
		{
			persistent_data = nbt.getCompound(KEY);	//not empty
		}
	}
}