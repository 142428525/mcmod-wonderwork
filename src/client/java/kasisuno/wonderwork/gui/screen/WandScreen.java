package kasisuno.wonderwork.gui.screen;

import io.wispforest.owo.ui.base.BaseUIModelHandledScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Insets;
import io.wispforest.owo.ui.event.WindowResizeCallback;
import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.entity.trivial.ManaManager;
import kasisuno.wonderwork.inventory.WandInventory;
import kasisuno.wonderwork.screen.WandScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WandScreen extends BaseUIModelHandledScreen<FlowLayout, WandScreenHandler>
{
	private static final Identifier TEXTURE = new Identifier(Wonderwork.MOD_ID, "textures/gui/wand.png");
	
	public WandScreen(WandScreenHandler handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title,
				FlowLayout.class, new Identifier(Wonderwork.MOD_ID, "wand_screen_model"));
		
		WindowResizeCallback.EVENT.register((client_p, window_p) ->
				component(FlowLayout.class, "main").margins(Insets.both(x, y)));
	}
	
	@Override
	protected void build(FlowLayout rootComponent)
	{
		component(FlowLayout.class, "main").margins(Insets.both(x, y));
		
		for (int i = 0; i < 3; i++)    //
		{
			for (int j = 0; j < 2; j++)    //
			{
				component(GridLayout.class, "plugin").child(Components.texture(TEXTURE,
						0, 166, 18, 18), i, j);
			}
		}
		
		component(ButtonComponent.class, "test-button")
				.onPress(buttonComponent -> Wonderwork.LOGGER.info("wcsndm"));
		
		for (int i = 0; i < WandInventory.ROW; i++)
		{
			for (int j = 0; j < WandInventory.COL; j++)
			{
				component(GridLayout.class, "cache").child(Components.texture(TEXTURE,
						0, 166, 18, 18), i, j);
			}
		}
	}
	
	@Override
	protected void init()
	{
		super.init();
		titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;    //centering
	}
	
	@Override
	public void render(DrawContext vanillaContext, int mouseX, int mouseY, float delta)
	{
		renderBackground(vanillaContext);    //vanilla translucent background
		super.render(vanillaContext, mouseX, mouseY, delta);
		
		component(LabelComponent.class, "test-text")
				.text(Text.literal("Mana: " + (getScreenHandler().getSyncedInt() == ManaManager.INVALID ?
						"INVALID" : getScreenHandler().getSyncedInt())));	//虽然不知道为什么但是放在这里就行
	}
	
	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY)
	{
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
	}
}