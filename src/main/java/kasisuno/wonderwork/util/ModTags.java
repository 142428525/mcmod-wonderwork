package kasisuno.wonderwork.util;

import kasisuno.wonderwork.Wonderwork;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags
{
	public static class Blocks
	{
	
	}
	
	public static class Items
	{
		public static final TagKey<Item> WAND_CACHES = register_item_tag("wand_caches");
		
		private static TagKey<Item> register_item_tag(String name_p)
		{
			return TagKey.of(RegistryKeys.ITEM, new Identifier(Wonderwork.MOD_ID, name_p));
		}
	}
	
	public static class Entities
	{
		public static final TagKey<EntityType<?>> MANA_USABLE = register_entity_tag("mana_usable");
		
		private static TagKey<EntityType<?>> register_entity_tag(String name_p)
		{
			return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Wonderwork.MOD_ID, name_p));
		}
	}
}