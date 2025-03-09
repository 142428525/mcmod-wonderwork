package kasisuno.wonderwork.sounds;

import kasisuno.wonderwork.Wonderwork;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds
{
	public static final SoundEvent MUSIC_DISK_KOBE = register_sound("music_disk.c_u_again");
	
	public static final SoundEvent BLOCK_WORM_BLOCK_WRIGGLE = register_sound("block.worm_block.wriggle");
	
	public static final SoundEvent ENTITY_KOBE_FLY = register_sound("entity.kobe.fly");
	
	public static final SoundEvent MAGIC_WHITE_ARROW_SHOOT = register_sound("magic.white_arrow.shoot");
	
	private static SoundEvent register_sound(String name_p)
	{
		Identifier id = new Identifier(Wonderwork.MOD_ID, name_p);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}
	
	public static void registerModSounds()
	{
	
	}
}