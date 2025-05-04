package kasisuno.wonderwork.mixin.client;

import kasisuno.wonderwork.Wonderwork;
import kasisuno.wonderwork.block.WormBlock;
import kasisuno.wonderwork.entity.effect.WormWindingNbtHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class HudOverlayClientMixin
{
	@Shadow @Final private MinecraftClient client;
	
	@Shadow
	protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);
	
	@Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"))
	private void wonderwork$addOverlays(DrawContext context, float tickDelta, CallbackInfo ci)
	{
		// worm winding overlay
		final float wp = (float)WormWindingNbtHelper.getPlayerOverlayProgress(this.client.player) / WormBlock.FADE_OUT_TICKS;
		if (wp > 0)
		{
			renderOverlay(context, new Identifier(Wonderwork.MOD_ID, "textures/misc/04.png"), wp);
		}
		
		
		
		// etc.
	}
}