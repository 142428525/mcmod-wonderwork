package kasisuno.wonderwork.render.entity.trivial;

import kasisuno.wonderwork.entity.trivial.ModEntityTypes;
import kasisuno.wonderwork.render.entity.MagicWhiteArrowEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModEntityRenderers
{
	static
	{
		EntityRendererRegistry.register(ModEntityTypes.MAGIC_WHITE_ARROW, MagicWhiteArrowEntityRenderer::new);
	}
	
	public static void registerModEntityRenderers()
	{
	
	}
}