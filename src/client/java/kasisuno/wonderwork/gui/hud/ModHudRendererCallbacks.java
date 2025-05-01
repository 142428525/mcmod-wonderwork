package kasisuno.wonderwork.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.entity.effect.WormWindingNbtHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModHudRendererCallbacks
{
	static
	{
		HudRenderCallback.EVENT.register((drawContext, tickDelta) ->
		{
			MinecraftClient client = MinecraftClient.getInstance();
			
			final int p = WormWindingNbtHelper.getPlayerOverlayProgress(client.player);
			if (p > 0)
			{
				drawContext.drawTexture(new Identifier(Wonderwork.MOD_ID, "textures/misc/04.png"),
						0, 0, 0, 0, 256, 5 * p);
				Wonderwork.LOGGER.debug("progress: {}", p);
			}
			
			/*
			final int fade_in_ticks = WormBlock.WormWindingHelper.getFadeInTicks(client.player);
			final int remaining_ticks = WormBlock.WormWindingHelper.getRemainingTicks(client.player);
			if ((remaining_ticks > 0 && remaining_ticks < WormBlock.FADE_OUT_TICKS) || fade_in_ticks > 0)
			{
				final float process = remaining_ticks < WormBlock.FADE_OUT_TICKS ?
						Math.min(remaining_ticks / (float)WormBlock.FADE_OUT_TICKS, 1) :
						Math.min(fade_in_ticks / (float)WormBlock.FADE_IN_TICKS, 1);	//in (0, 1]
				
//				render_overlay(drawContext, new Identifier(Wonderwork.MOD_ID, "textures/misc/worm_overlay.png"),
//						process);
				final int scaledWidth = drawContext.getScaledWindowWidth();
				final int scaledHeight = drawContext.getScaledWindowHeight();
				drawContext.drawTexture(new Identifier(Wonderwork.MOD_ID, "textures/misc/04.png"),
						0, 0, 0.0F, 0.0F, scaledWidth, scaledHeight, scaledWidth, scaledHeight);
			}
			*/
			
			//render_overlay(drawContext, new Identifier(Wonderwork.MOD_ID, "textures/misc/worm_overlay.png"),
			//		0.5F);
		});
	}
	
	public static void registerModHudRendererCallbacks()
	{
	
	}
	
	private static void render_overlay(DrawContext context, Identifier texture, float opacity)
	{
		int scaledWidth = context.getScaledWindowWidth();
		int scaledHeight = context.getScaledWindowHeight();
		
		RenderSystem.disableDepthTest();    //虽然不知道为什么但是这样可以减少一些bug
		RenderSystem.depthMask(false);
		context.setShaderColor(1.0F, 0.0F, 1.0F, opacity);
		context.drawTexture(texture, 0, 0, -90, 0.0F, 0.0F,
				scaledWidth, scaledHeight, scaledWidth, scaledHeight);
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}