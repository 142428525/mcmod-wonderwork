package kasisuno.wonderwork;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import kasisuno.wonderwork.entity.effect.trivial.ModStatusEffects;
import kasisuno.wonderwork.entity.trivial.ModEntityTypes;
import kasisuno.wonderwork.item.trivial.ModItemGroup;
import kasisuno.wonderwork.item.trivial.ModItems;
import kasisuno.wonderwork.network.ModNetwork;
import kasisuno.wonderwork.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wonderwork implements ModInitializer
{
	public static final String MOD_ID = "wonderwork";
	
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		ModItems.registerModItems();
		ModItemGroup.registerModItemGroup();
		ModBlocks.registerModBlocks();
		ModSounds.registerModSounds();
		ModEntityTypes.registerModEntityTypes();
		ModStatusEffects.registerModStatusEffects();
		ModNetwork.registerModNetworkUtils();
		
		if (true)
		{
			System.setProperty("owo.uwu", "yes please");
			LOGGER.info("Let's kawaii-ize the world!");
		}
		
		LOGGER.info("Mod \"Wonderwork\" successfully initialized. Elbow Mojang!");
	}
}