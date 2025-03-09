package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider
{
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
	{
		super(output, registriesFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg)
	{
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(ModBlocks.STONE_BLOCK)
				.add(ModBlocks.LAVA_BLOCK);
		
		//必须手动指定挖掘等级<=0的方块
		//getOrCreateTagBuilder(MiningLevelManager.getBlockTag(MiningLevels.WOOD))
				//.add(ModBlocks.STONE_BLOCK);
		
		getOrCreateTagBuilder(MiningLevelManager.getBlockTag(MiningLevels.NETHERITE))
				.add(ModBlocks.LAVA_BLOCK);
	}
}