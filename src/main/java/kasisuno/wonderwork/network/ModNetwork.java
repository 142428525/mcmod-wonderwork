package kasisuno.wonderwork.network;

import io.wispforest.owo.network.OwoNetChannel;
import kasisuno.wonderwork.Wonderwork;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class ModNetwork
{
	public static final OwoNetChannel SYNC_STATUS =
			OwoNetChannel.create(new Identifier(Wonderwork.MOD_ID, "sync_status"));
	
	static
	{
		SYNC_STATUS.registerClientboundDeferred(Packet.SyncPersistentDataS2CPacket.class);
	}
	
	public static void registerModNetworkUtils()
	{
	
	}
	
	public static class Packet
	{
		public record SyncPersistentDataS2CPacket(NbtCompound data)
		{
		
		}
	}
}