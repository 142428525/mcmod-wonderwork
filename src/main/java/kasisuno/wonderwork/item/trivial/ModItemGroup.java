package kasisuno.wonderwork.item.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.block.trivial.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup
{
	public static final ItemGroup JOKE_GROUP = Registry.register(Registries.ITEM_GROUP,
			new Identifier(Wonderwork.MOD_ID, "joke_group"),
			FabricItemGroup.builder().displayName(Text.translatable("itemGroup.joke_group"))
					.icon(() -> new ItemStack(ModItems.DEBUG_WAND)).entries((displayContext, entries) ->
					{
						entries.add(ModItems.STONE_INGOT);
						
						entries.add(ModItems.DEBUG_WAND);
						entries.add(ModItems.STD_WAND);
						
						entries.add(ModItems.MUSIC_DISK_KOBE);
						
						entries.add(ModBlocks.STONE_BLOCK);
						entries.add(ModBlocks.LAVA_BLOCK);
						entries.add(ModBlocks.SpecialBlockItems.WORM_BUCKET);
						
						//etc.
					}).build());
	
	public static void registerModItemGroup()
	{
	
	}
}