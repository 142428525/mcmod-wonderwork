package kasisuno.wonderwork.entity.trivial;

import net.minecraft.nbt.NbtCompound;

public interface IModPersistentData
{
	default NbtCompound getPersistentData()
	{
		throw new IllegalStateException("This method is implemented in \"ManaManagerMixin.class\".");
	}
}