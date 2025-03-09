package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import kasisuno.wonderwork.item.trivial.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;

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
		blockStateModelGenerator.registerSingleton(ModBlocks.LAVA_BLOCK, TexturedModel.CUBE_COLUMN);
		blockStateModelGenerator.registerSimpleState(ModBlocks.WORM_BLOCK);	//block model is written manually
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator)
	{
		itemModelGenerator.register(ModItems.STONE_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.DEBUG_WAND, Models.HANDHELD_ROD);
		//itemModelGenerator.register(ModItems.STD_WAND, Models.HANDHELD_ROD);
		itemModelGenerator.register(ModItems.MUSIC_DISK_KOBE, Models.GENERATED);
		
		//special block item
		itemModelGenerator.register(ModBlocks.SpecialBlockItems.WORM_BUCKET, Models.GENERATED);
	}
}