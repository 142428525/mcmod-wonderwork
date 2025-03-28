package kasisuno.wonderwork.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WormBucketItem extends BlockItem implements FluidModificationItem
{
	private final SoundEvent placeSound;
	
	public WormBucketItem(Block block, SoundEvent placeSound, Settings settings)
	{
		super(block, settings);
		this.placeSound = placeSound;
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = super.useOnBlock(context);
		PlayerEntity playerEntity = context.getPlayer();
		if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
			Hand hand = context.getHand();
			playerEntity.setStackInHand(hand, Items.BUCKET.getDefaultStack());
		}
		
		return actionResult;
	}
	
	@Override
	public String getTranslationKey() {
		return this.getOrCreateTranslationKey();
	}
	
	@Override
	protected SoundEvent getPlaceSound(BlockState state) {
		return this.placeSound;
	}
	
	@Override
	public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult)
	{
		if (world.isInBuildLimit(pos) && world.isAir(pos)) {
			if (!world.isClient) {
				world.setBlockState(pos, this.getBlock().getDefaultState(), Block.NOTIFY_ALL);
			}
			
			world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
			world.playSound(player, pos, this.placeSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		} else {
			return false;
		}
	}
}