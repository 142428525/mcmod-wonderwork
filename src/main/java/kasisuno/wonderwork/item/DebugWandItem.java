package kasisuno.wonderwork.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class DebugWandItem extends WandItem
{
	public DebugWandItem(Settings settings)
	{
		super(10, (float)(1.6 - 4.0), settings);
	}
	
	@Override
	public boolean hasGlint(ItemStack itemStack)
	{
		return true;
	}
	
	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
		tooltip.add(Text.translatable(getTranslationKey() + ".tooltip")
				.formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
	}
}