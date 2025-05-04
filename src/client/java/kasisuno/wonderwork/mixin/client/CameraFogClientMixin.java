package kasisuno.wonderwork.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import kasisuno.wonderwork.block.trivial.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraFogClientMixin
{
	@Inject(method = "getSubmersionType()Lnet/minecraft/client/render/CameraSubmersionType;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"),
			cancellable = true)
	private void wonderwork$addFogs(CallbackInfoReturnable<CameraSubmersionType> cir, @Local BlockState blockState)
	{
		// worm winding fog
		if (blockState.isOf(ModBlocks.WORM_BLOCK))
		{
			cir.setReturnValue(CameraSubmersionType.POWDER_SNOW);
		}
		
		
		
		// etc.
	}
}