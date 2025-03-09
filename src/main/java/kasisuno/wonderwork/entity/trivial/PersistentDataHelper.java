package kasisuno.wonderwork.entity.trivial;

import kasisuno.wonderwork.network.ModNetwork;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;

public class PersistentDataHelper
{
	public static NbtCompound getData(LivingEntity entity, String key)
	{
		NbtCompound nbt = ((IModPersistentData)entity).getPersistentData();
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
	
	public static void setData(LivingEntity entity, String key, NbtCompound data)
	{
		NbtCompound nbt = ((IModPersistentData)entity).getPersistentData();
		nbt.put(key, data);
		
		sync_data(entity, nbt);
	}
	
	public static void setData(LivingEntity entity, NbtCompound data_super)
	{
		NbtCompound nbt = ((IModPersistentData)entity).getPersistentData();
		nbt.copyFrom(data_super);
		
		sync_data(entity, nbt);
	}
	
	private static void sync_data(LivingEntity entity_p, NbtCompound data_super)
	{
		if (entity_p instanceof ServerPlayerEntity player)
		{
			ModNetwork.SYNC_STATUS.serverHandle(player)
					.send(new ModNetwork.Packet.SyncPersistentDataS2CPacket(data_super));
		}
	}
}