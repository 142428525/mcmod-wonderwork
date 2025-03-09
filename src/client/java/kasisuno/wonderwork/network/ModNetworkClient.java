package kasisuno.wonderwork.network;

import kasisuno.wonderwork.entity.trivial.PersistentDataHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModNetworkClient
{
	static
	{
		ModNetwork.SYNC_STATUS.registerClientbound(ModNetwork.Packet.SyncPersistentDataS2CPacket.class,
				(message, access) -> PersistentDataHelper.setData(access.player(), message.data()));
	}
	
	public static void registerModNetworkClientUtils()
	{
	
	}
}