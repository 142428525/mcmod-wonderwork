package kasisuno.wonderwork.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ModHudRendererCallbacks
{
	static
	{
		HudRenderCallback.EVENT.register((drawContext, tickDelta) ->
		{
			MinecraftClient client = MinecraftClient.getInstance();
			
			//final int p = WormWindingNbtHelper.getPlayerOverlayProgress(client.player);
//			if (p > 0)
//			{
//				drawContext.drawTexture(new Identifier(Wonderwork.MOD_ID, "textures/misc/04.png"),
//						0, 0, 0, 0, 256, 5 * p);
//			}
			
			
			
			// ?
		});
	}
	
	public static void registerModHudRendererCallbacks()
	{
	
	}
}