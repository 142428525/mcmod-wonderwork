package kasisuno.wonderwork.block.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.block.LavaBlock;
import kasisuno.wonderwork.block.WormBlock;
import kasisuno.wonderwork.item.WormBucketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModBlocks
{
	public static final Block STONE_BLOCK = register_block("stone_block",
			new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
	
	public static final Block LAVA_BLOCK = register_block("lava_block",
			new LavaBlock(FabricBlockSettings.copyOf(Blocks.MAGMA_BLOCK)
					.mapColor(MapColor.RED)
					.luminance(state -> 15)));
	
	public static final Block WORM_BLOCK = register_block_only("worm_block",
			new WormBlock(FabricBlockSettings.copyOf(Blocks.POWDER_SNOW)
					.mapColor(MapColor.BLACK)
					.sounds(BlockSoundGroup.HONEY)
					.solid()
					.strength(100)));
	
	public static class SpecialBlockItems
	{
		public static final Item WORM_BUCKET = register_block_item("worm_bucket",
				new WormBucketItem(WORM_BLOCK, SoundEvents.BLOCK_HONEY_BLOCK_PLACE,
						new FabricItemSettings().maxCount(1)));
		//no specific subtitle so no specific sound event here ^^^
		
		public static void registerSpecialBlockItems()
		{
		
		}
	}
	
	private static Block register_block(String name_p, Block block_p)
	{
		/*var ??? = */
		register_block_item(name_p, block_p);
		return register_block_only(name_p, block_p);
	}
	
	private static Block register_block_only(String name_p, Block block_p)
	{
		return Registry.register(Registries.BLOCK, new Identifier(Wonderwork.MOD_ID, name_p), block_p);
	}
	
	private static Item register_block_item(String name_p, Block block_p)
	{
		return Registry.register(Registries.ITEM, new Identifier(Wonderwork.MOD_ID, name_p),
				new BlockItem(block_p, new FabricItemSettings()));
	}
	
	private static Item register_block_item(String name_p, Item item_p)
	{
		return Registry.register(Registries.ITEM, new Identifier(Wonderwork.MOD_ID, name_p), item_p);
	}
	
	public static void registerModBlocks()
	{
		SpecialBlockItems.registerSpecialBlockItems();
	}
}