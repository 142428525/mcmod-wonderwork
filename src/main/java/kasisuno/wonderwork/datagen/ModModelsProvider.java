package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import kasisuno.wonderwork.item.trivial.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelsProvider extends FabricModelProvider
{
	public ModModelsProvider(FabricDataOutput output)
	{
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator)
	{
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STONE_BLOCK);
		blockStateModelGenerator.registerSimpleState(ModBlocks.LAVA_BLOCK);	// NOTE: its block model is written manually, in order to use lava's textures
		blockStateModelGenerator.registerSimpleState(ModBlocks.WORM_BLOCK);	// NOTE: its block model is written manually, in order to derivate from powder snow
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		itemModelGenerator.register(ModItems.STONE_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.DEBUG_WAND, Models.HANDHELD_ROD);
		//itemModelGenerator.register(ModItems.STD_WAND, Models.HANDHELD_ROD);	// NOTE: written manually & fabulously
		itemModelGenerator.register(ModItems.MUSIC_DISK_KOBE, Models.GENERATED);
		
		//special block item
		itemModelGenerator.register(ModBlocks.SpecialBlockItems.WORM_BUCKET, Models.GENERATED);
	}
}