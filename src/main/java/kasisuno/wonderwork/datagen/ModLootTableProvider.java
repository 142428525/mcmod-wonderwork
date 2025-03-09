package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider
{
	public ModLootTableProvider(FabricDataOutput dataOutput)
	{
		super(dataOutput);
	}
	
	@Override
	public void generate()
	{
		addDrop(ModBlocks.STONE_BLOCK);
		addDrop(ModBlocks.LAVA_BLOCK);
	}
}