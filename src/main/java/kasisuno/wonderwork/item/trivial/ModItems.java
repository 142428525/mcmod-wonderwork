package kasisuno.wonderwork.item.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.item.DebugWandItem;
import kasisuno.wonderwork.item.WandItem;
import kasisuno.wonderwork.sounds.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems
{
	public static final Item STONE_INGOT = register_item("stone_ingot", new Item(new FabricItemSettings()));
	
	public static final Item DEBUG_WAND = register_item("debug_wand",
			new DebugWandItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
	
	public static final Item STD_WAND = register_item("standard_wand",
			new WandItem(6, (float)(1.3 - 4.0), new FabricItemSettings().maxCount(1)));
	//由于原版“属性”系统限制，工具攻速需要在空手（4.0F）的基础上加减。
	//这里为了让物品具有1.3F的攻速，需要传入(float)(1.3 - 4.0)，然后4.0F + (1.3F - 4.0F) = 1.3F。
	//最终，物品的攻击冷却为round(20gt / 1.3) = 15gt。
	
	public static final Item MUSIC_DISK_KOBE = register_item("music_disk_kobe",
			new MusicDiscItem(8, ModSounds.MUSIC_DISK_KOBE,
					new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 114));
	
	private static Item register_item(String name_p, Item item_p)
	{
		return Registry.register(Registries.ITEM, new Identifier(Wonderwork.MOD_ID, name_p), item_p);
	}
	
	public static void registerModItems()
	{
	
	}
}