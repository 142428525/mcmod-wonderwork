package kasisuno.wonderwork.inventory;

import kasisuno.wonderwork.item.trivial.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Hand;

public class WandInventory extends SimpleInventory
{
	public static final int ROW = 3;
	
	public static final int COL = 3;
	
	public static final int SIZE = ROW * COL;
	
	public WandInventory()
	{
		super(SIZE);
	}
	
	@Override
	public void onClose(PlayerEntity player)
	{
		Hand hand = null;
		if (player.getMainHandStack().isOf(ModItems.STD_WAND))
		{
			hand = Hand.MAIN_HAND;
		}
		else if (player.getOffHandStack().isOf(ModItems.STD_WAND))
		{
			hand = Hand.OFF_HAND;
		}
		else
		{
			throw new IllegalStateException("What the hell how do you use the wand?");
		}
		
		player.getStackInHand(hand).getNbt().put("Inventory", toNbtList());
	}
	
	@Override
	public void readNbtList(NbtList nbtList)
	{
		for (int i = 0; i < this.size(); i++)
		{
			this.setStack(i, ItemStack.EMPTY);
		}
		
		for (int i = 0; i < nbtList.size(); i++)
		{
			NbtCompound nbtCompound = nbtList.getCompound(i);
			int j = nbtCompound.getByte("Slot") & 0b11111111;
			if (j < this.size())
			{
				this.setStack(j, ItemStack.fromNbt(nbtCompound));
			}
		}
	}
	
	@Override
	public NbtList toNbtList()
	{
		NbtList nbtList = new NbtList();
		
		for (int i = 0; i < this.size(); i++)
		{
			ItemStack itemStack = this.getStack(i);
			if (!itemStack.isEmpty())
			{
				NbtCompound nbtCompound = new NbtCompound();
				nbtCompound.putByte("Slot", (byte)i);
				itemStack.writeNbt(nbtCompound);
				nbtList.add(nbtCompound);
			}
		}
		
		return nbtList;
	}
}