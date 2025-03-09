package kasisuno.wonderwork.render.entity;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.entity.projectile.MagicWhiteArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MagicWhiteArrowEntityRenderer extends ProjectileEntityRenderer<MagicWhiteArrowEntity>
{
	public static final Identifier TEXTURE = new Identifier(Wonderwork.MOD_ID,
			"textures/entity/projectiles/white_arrow.png");
	
	public MagicWhiteArrowEntityRenderer(EntityRendererFactory.Context context)
	{
		super(context);
	}
	
	@Override
	public Identifier getTexture(MagicWhiteArrowEntity entity)
	{
		return TEXTURE;
	}
}