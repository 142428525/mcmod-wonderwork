package kasisuno.wonderwork;

import kasisuno.wonderwork.gui.hud.ModHudRendererCallbacks;
import kasisuno.wonderwork.gui.screen.trivial.ModHandledScreens;
import kasisuno.wonderwork.network.ModNetworkClient;
import kasisuno.wonderwork.render.entity.trivial.ModEntityRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class WonderworkClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ModEntityRenderers.registerModEntityRenderers();
		ModHandledScreens.registerHandledScreens();
		ModHudRendererCallbacks.registerModHudRendererCallbacks();
		ModNetworkClient.registerModNetworkClientUtils();
	}
}