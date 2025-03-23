package kasisuno.wonderwork.util;

import kasisuno.wonderwork.Wonderwork;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags
{
	public static class Block
	{
	
	}
	
	public static class Item
	{
		public static final TagKey<net.minecraft.item.Item> WAND_CACHES = register_item_tag("wand_caches");
		
		private static TagKey<net.minecraft.item.Item> register_item_tag(String name_p)
		{
			return TagKey.of(RegistryKeys.ITEM, new Identifier(Wonderwork.MOD_ID, name_p));
		}
	}
	
	public static class Entity
	{
		public static final TagKey<EntityType<?>> MANA_USABLE = register_entity_tag("mana_usable");
		
		private static TagKey<EntityType<?>> register_entity_tag(String name_p)
		{
			return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Wonderwork.MOD_ID, name_p));
		}
	}
}