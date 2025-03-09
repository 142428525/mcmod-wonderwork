package kasisuno.wonderwork.screen.trivial;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.screen.WandScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerTypes
{
	public static final ScreenHandlerType<WandScreenHandler> WAND = register_screen_handler("wand",
			WandScreenHandler::new);
	
	private static <T extends ScreenHandler>
	ScreenHandlerType<T> register_screen_handler(String name_p, ScreenHandlerType.Factory<T> factory)
	{
		return Registry.register(Registries.SCREEN_HANDLER, new Identifier(Wonderwork.MOD_ID, name_p),
				new ScreenHandlerType<>(factory, FeatureSet.empty()));	//???
	}
	
	public static void registerModScreenHandlers()
	{
	
	}
}