package kasisuno.wonderwork.datagen;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import kasisuno.wonderwork.item.trivial.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider
{
	public ModRecipeProvider(FabricDataOutput output)
	{
		super(output);
	}
	
	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter)
	{
		offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.STONE_INGOT,
				RecipeCategory.BUILDING_BLOCKS, ModBlocks.STONE_BLOCK);
	}
}