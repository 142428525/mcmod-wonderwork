package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.item.trivial.ModItems;
import kasisuno.wonderwork.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider
{
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
	{
		super(output, completableFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg)
	{
		getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
				.add(ModItems.MUSIC_DISK_KOBE);
		
		getOrCreateTagBuilder(ModTags.Items.WAND_CACHES)
				.add(Items.ENCHANTED_GOLDEN_APPLE)
				.add(new Identifier(PatchouliAPI.MOD_ID, "guide_book"));	//fixme: hard-coded!!!
	}
}