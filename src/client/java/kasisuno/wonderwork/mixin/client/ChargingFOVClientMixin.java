package kasisuno.wonderwork.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import kasisuno.wonderwork.item.WandItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//@Mixin(MinecraftClient.class)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class ChargingFOVClientMixin extends PlayerEntity
{
	//why?
	public ChargingFOVClientMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile)
	{
		super(world, pos, yaw, gameProfile);
	}
	
	//@Inject(at = @At("HEAD"), method = "run")
	//@Inject(method = "getFovMultiplier()F", at = @At("TAIL"))
	//private void init(CallbackInfoReturnable<Float> cir, @Local LocalFloatRef f)	//妈个鸡为什么不能修改f？
	
	@Inject(method = "getFovMultiplier()F", at = @At("RETURN"), cancellable = true)
	private void wonderwork$charging_FOV_mixin(CallbackInfoReturnable<Float> cir, @Local float f)
	{
		// This code is injected into the start of acpe.gfm(...) -> float //MinecraftClient.run()V
		
		ItemStack itemStack = this.getActiveItem();
		if (this.isUsingItem())
		{
			if (itemStack.getItem() instanceof WandItem)
			{
				int i = this.getItemUseTime();
				float g = (float)i / 20.0F;
				if (g > 1.0F)
				{
					g = 1.0F;    //蓄力时的FOV变化最长1s
				}
				else
				{
					g *= g;
				}
				
				f *= (1.0F - g * 0.15F);
				
				//傻逼jvav不给重载运算符导致的，fuck jvav
				//f.set(f.get() * (1.0F - g * 0.15F));
				
				if (!this.isUsingSpyglass())
				{
					cir.setReturnValue(MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0F, f));
				}
				else
				{
					cir.setReturnValue(0.1F);
				}
			}
		}
	}
}