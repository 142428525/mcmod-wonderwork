package kasisuno.wonderwork.entity.effect;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import kasisuno.wonderwork.entity.trivial.PersistentDataHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class WormWindingNbtHelper
{
	//public static final int FADED = -1;
	private static final String MAIN_KEY = "WormWinding";
	private static final String COLLISION_COUNT = "CollisionCount";
	private static final String PLAYER_OVERLAY_PROGRESS = "PlayerOverlayProgress";
	
	// collision count
	public static int getCollisionCount(LivingEntity entity)
	{
		return PersistentDataHelper.getData(entity, MAIN_KEY).getInt(COLLISION_COUNT);
	}
	
	public static void setCollisionCount(LivingEntity entity, int value)
	{
		NbtCompound data = PersistentDataHelper.getData(entity, MAIN_KEY);
		data.putInt(COLLISION_COUNT, value);
		PersistentDataHelper.setData(entity, MAIN_KEY, data);
	}
	
	public static void mapCollisionCount(LivingEntity entity, Int2IntFunction f)
	{
		setCollisionCount(entity, f.applyAsInt(getCollisionCount(entity)));
	}
	
	// player overlay progress
	public static int getPlayerOverlayProgress(PlayerEntity entity)
	{
		return PersistentDataHelper.getData(entity, MAIN_KEY).getInt(PLAYER_OVERLAY_PROGRESS);
	}
	
	public static void setPlayerOverlayProgress(PlayerEntity entity, int value)
	{
		NbtCompound data = PersistentDataHelper.getData(entity, MAIN_KEY);
		data.putInt(PLAYER_OVERLAY_PROGRESS, value);
		PersistentDataHelper.setData(entity, MAIN_KEY, data);
	}
	
	public static void mapPlayerOverlayProgress(PlayerEntity entity, Int2IntFunction f)
	{
		setPlayerOverlayProgress(entity, f.applyAsInt(getPlayerOverlayProgress(entity)));
	}
}