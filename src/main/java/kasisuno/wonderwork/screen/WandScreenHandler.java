package kasisuno.wonderwork.screen;

import io.wispforest.owo.client.screens.ScreenUtils;
import io.wispforest.owo.client.screens.SlotGenerator;
import io.wispforest.owo.client.screens.SyncedProperty;
import io.wispforest.owo.client.screens.ValidatingSlot;
import kasisuno.wonderwork.entity.trivial.ManaManager;
import kasisuno.wonderwork.inventory.WandInventory;
import kasisuno.wonderwork.screen.trivial.ModScreenHandlerTypes;
import kasisuno.wonderwork.util.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class WandScreenHandler extends ScreenHandler
{
	private final WandInventory inventory;
	private int synced_int;
	private final SyncedProperty<Integer> property;
	
	public WandScreenHandler(int syncId, PlayerInventory playerInventory)
	{
		this(syncId, playerInventory, new WandInventory());
	}
	
	public WandScreenHandler(int syncId, PlayerInventory playerInventory, WandInventory inventory)
	{
		super(ModScreenHandlerTypes.WAND, syncId);
		checkSize(inventory, WandInventory.SIZE);    //must pass
		this.inventory = inventory;
		// 玩家开启时，一些物品栏有自定义的逻辑。
		inventory.onOpen(playerInventory.player);
		
		property = createProperty(Integer.class, ManaManager.INVALID);
		property.observe(this::sync_int);
		if (playerInventory.player instanceof ServerPlayerEntity)
		{
			property.set(ManaManager.readMana(playerInventory.player));
			property.markDirty();
		}
		
		//slots here
		SlotGenerator.begin(this::addSlot, 8, 84)
				.playerInventory(playerInventory)
				.moveTo(62, 17)
				.slotFactory((inventoryX, index, x, y) -> new ValidatingSlot(inventoryX, index, x, y,
						itemStack -> itemStack.isIn(ModTags.Item.WAND_CACHES)))
				.grid(this.inventory, 0, WandInventory.COL, WandInventory.ROW);
		//if slots would be added, remember SIZE is INDEX_END
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int slot)
	{
		return ScreenUtils.handleSlotTransfer(this, slot, inventory.size());
	}
	
	@Override
	public boolean canUse(PlayerEntity player)
	{
		return inventory.canPlayerUse(player);
	}
	
	@Override
	public void onClosed(PlayerEntity player)
	{
		super.onClosed(player);
		inventory.onClose(player);
	}
	
	public int getSyncedInt()
	{
		return synced_int;
	}
	
	private void sync_int(int i)
	{
		synced_int = i;
		synced_int = property.get();
		
		assert i == property.get();
	}
}