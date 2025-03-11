package kasisuno.wonderwork.entity.trivial;

import kasisuno.wonderwork.network.ModNetwork;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;

public class PersistentDataHelper
{
	/**
	 * 当没有此key对应的NBT时为其创建
	 */
	public static NbtCompound getData(LivingEntity entity, String key)
	{
		NbtCompound nbt = entity.getPersistentData();
		if (nbt.contains(key, NbtElement.COMPOUND_TYPE))
		{
			return nbt.getCompound(key);
		}
		else
		{
			nbt.put(key, new NbtCompound());
			return new NbtCompound();
		}
	}
	
	public static void setData(LivingEntity entity, String key, NbtCompound new_data)
	{
		NbtCompound nbt = entity.getPersistentData();
		nbt.put(key, new_data);
		
		sync_data(entity, nbt);
	}
	
	/**
	 * @param data_super 持久数据母标签
	 */
	public static void setData(LivingEntity entity, NbtCompound data_super)
	{
		NbtCompound nbt = entity.getPersistentData();
		nbt.copyFrom(data_super);
		
		sync_data(entity, nbt);
	}
	
	/**
	 * @param data_super_p 持久数据母标签
	 */
	private static void sync_data(LivingEntity entity_p, NbtCompound data_super_p)
	{
		if (entity_p instanceof ServerPlayerEntity player)
		{
			ModNetwork.SYNC_STATUS.serverHandle(player)
					.send(new ModNetwork.Packet.SyncPersistentDataS2CPacket(data_super_p));
		}
	}
}