package kasisuno.wonderwork.entity.trivial;

import kasisuno.wonderwork.util.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class ManaManager
{
	public static final int INVALID = -1;
	private static final String KEY = "Mana";
	
	public static int readMana(LivingEntity entity)
	{
		if (entity == null || !entity.getType().isIn(ModTags.Entities.MANA_USABLE))
		{
			return INVALID;
		}
		
		NbtCompound mana = PersistentDataHelper.getData(entity, KEY);
		return !mana.isEmpty() ? mana.getInt("ManaLevel") : INVALID;
	}
	
	public static void writeMana(LivingEntity entity, int val)
	{
		if (entity == null || !entity.getType().isIn(ModTags.Entities.MANA_USABLE))
		{
			return;
		}
		
		NbtCompound mana = new NbtCompound();
		mana.putInt("ManaLevel", val);
		PersistentDataHelper.setData(entity, KEY, mana);
	}
	
	public static void changeMana(LivingEntity entity, int delta)
	{
		if (entity == null || !entity.getType().isIn(ModTags.Entities.MANA_USABLE))
		{
			return;
		}
		
		writeMana(entity, Math.max(readMana(entity) + delta, 0));
	}
}