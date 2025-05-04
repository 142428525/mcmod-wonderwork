package kasisuno.wonderwork.mixin;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public abstract class FluidFlowMixin extends Fluid
{
	@Inject(method = "canFill(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/Fluid;)Z",
			at = @At("HEAD"), cancellable = true)
	private void wonderwork$addFlowStoppingBlock(BlockView world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> cir)
	{
		//Block block = state.getBlock();
		if (state.isOf(ModBlocks.WORM_BLOCK))
		{
			cir.setReturnValue(false);
		}
	}
}