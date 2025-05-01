package kasisuno.wonderwork.entity.trivial;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import kasisuno.wonderwork.util.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class ManaNbtManager
{
	public static final int INVALID = -1;
	private static final String MAIN_KEY = "Mana";
	private static final String MANA_LEVEL = "ManaLevel";
	
	public static int readMana(LivingEntity entity)
	{
		if (entity == null || !isManaUsable(entity))
		{
			return INVALID;
		}
		
		NbtCompound mana = PersistentDataHelper.getData(entity, MAIN_KEY);
		return !mana.isEmpty() ? mana.getInt(MANA_LEVEL) : INVALID;
	}
	
	public static void writeMana(LivingEntity entity, int val)
	{
		if (entity == null || !isManaUsable(entity))
		{
			return;
		}
		
		NbtCompound mana = new NbtCompound();
		mana.putInt(MANA_LEVEL, val);
		PersistentDataHelper.setData(entity, MAIN_KEY, mana);
	}
	
	public static void mapMana(LivingEntity entity, Int2IntFunction f)
	{
		if (entity == null || !isManaUsable(entity))
		{
			return;
		}
		
		writeMana(entity, Math.max(f.applyAsInt(readMana(entity)), 0));
	}
	
	public static boolean isManaUsable(LivingEntity entity)
	{
		return entity.getType().isIn(ModTags.Entity.MANA_USABLE);
	}
}