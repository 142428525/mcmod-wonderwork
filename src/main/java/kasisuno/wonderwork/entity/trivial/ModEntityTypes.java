package kasisuno.wonderwork.entity.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.entity.projectile.MagicWhiteArrowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityTypes
{
	public static final EntityType<MagicWhiteArrowEntity> MAGIC_WHITE_ARROW =
			register_entity_type("magic_white_arrow", FabricEntityTypeBuilder
					.<MagicWhiteArrowEntity>create(SpawnGroup.MISC, MagicWhiteArrowEntity::new)
					.dimensions(EntityDimensions.fixed(0.5F, 0.5F))
					.trackRangeChunks(4).trackedUpdateRate(20));
	
	private static <T extends Entity> EntityType<T> register_entity_type(String name_p,
																		 FabricEntityTypeBuilder<T> builder_p)
	{
		return Registry.register(Registries.ENTITY_TYPE, new Identifier(Wonderwork.MOD_ID, name_p), builder_p.build());
	}
	
	public static void registerModEntityTypes()
	{
	
	}
}