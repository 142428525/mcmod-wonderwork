package kasisuno.wonderwork.gui.screen.trivial;

import kasisuno.wonderwork.gui.screen.WandScreen;
import kasisuno.wonderwork.screen.trivial.ModScreenHandlerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ModHandledScreens
{
	static
	{
		HandledScreens.register(ModScreenHandlerTypes.WAND, WandScreen::new);
	}
	
	public static void registerHandledScreens()
	{
	
	}
}