package kasisuno.wonderwork.block;

import kasisuno.wonderwork.block.trivial.ModBlocks;
import kasisuno.wonderwork.entity.effect.WormWindingStatusEffectInstance;
import kasisuno.wonderwork.entity.effect.trivial.ModStatusEffects;
import kasisuno.wonderwork.entity.trivial.PersistentDataHelper;
import kasisuno.wonderwork.sounds.ModSounds;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;

import java.util.Optional;

public class WormBlock extends Block implements FluidDrainable
{
	public static final int FADE_IN_TICKS = 50;    //in ticks
	public static final int FADE_OUT_TICKS = 50;    //in ticks
	private static final VoxelShape FALLING_SHAPE =
			VoxelShapes.cuboid(0, 0, 0, 1, 0.9, 1);
	
	public WormBlock(Settings settings)
	{
		super(settings);
	}
	
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction)
	{
		return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity)
	{
		if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this))
		{
			entity.slowMovement(state, new Vec3d(0.9, 1.5, 0.9));
			
			if (world.random.nextInt(15) == 0)
			{
				entity.playSound(ModSounds.BLOCK_WORM_BLOCK_WRIGGLE, 1.0F, 1.0F);
			}
			
			if (world.isClient)
			{
				Random random = world.getRandom();
				boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
				if (bl && random.nextBoolean())
				{
					world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK,
									ModBlocks.WORM_BLOCK.getDefaultState()),
							entity.getX(), pos.getY() + 1, entity.getZ(),
							MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F,
							0.05F,
							MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F);
				}
			}
			else if (entity instanceof LivingEntity livingEntity)    //server-side
			{
				//worm winding logic's here
				
				if (!livingEntity.hasStatusEffect(ModStatusEffects.WORM_WINDING))
				{
					livingEntity.addStatusEffect(new WormWindingStatusEffectInstance(world.getDifficulty().ordinal()));
					
					if (livingEntity instanceof PlayerEntity player)
					{
						WormWindingHelper.setFadeInTicks(player, 0);
						WormWindingHelper.setRemainingTicks(player, FADE_OUT_TICKS);
					}
				}
				else if (livingEntity instanceof PlayerEntity player)	//has already had the effect
				{
					if (WormWindingHelper.getRemainingTicks(player) != FADE_OUT_TICKS)
					{
						int tmp = WormWindingHelper.getFadeInTicks(player);
						WormWindingHelper.setRemainingTicks(player,
								(tmp - tmp / FADE_OUT_TICKS) % FADE_OUT_TICKS + 1);
					}
					
					WormWindingHelper.setFadeInTicks(player, WormWindingHelper.getFadeInTicks(player) + 1);
				}
				
				if (world.getBlockState(BlockPos.ofFloored(pos.getX(), entity.getEyeY(), pos.getZ())).isOf(this))
				{
					livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,
							StatusEffectInstance.INFINITE));
				}
				else
				{
					livingEntity.removeStatusEffect(StatusEffects.BLINDNESS);
				}
			}
		}

//		entity.setInPowderSnow(true);
//		if (!world.isClient)
//		{
//			if (entity.isOnFire() && (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || entity instanceof PlayerEntity) && entity.canModifyAt(world, pos))
//			{
//				world.breakBlock(pos, false);
//			}
//
//			entity.setOnFire(false);
//		}
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance)
	{
		if (/*fallDistance >= 4.0 && */entity instanceof LivingEntity livingEntity)
		{
			LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
			SoundEvent soundEvent = fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
			entity.playSound(soundEvent, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		BlockPos blockPos = pos.up();
		if (world.getBlockState(blockPos).isAir() && !world.getBlockState(blockPos).isOpaqueFullCube(world, blockPos))
		{
			if (random.nextInt(100) == 0)
			{
				double d = (double)pos.getX() + random.nextDouble();
				double e = (double)pos.getY() + 1.0;
				double f = (double)pos.getZ() + random.nextDouble();
				world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK,
								ModBlocks.WORM_BLOCK.getDefaultState()), d, e, f,
						0.0, 0.0, 0.0);
				world.playSound(d, e, f, ModSounds.BLOCK_WORM_BLOCK_WRIGGLE, SoundCategory.BLOCKS,
						0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F,
						false);
			}
			
			if (random.nextInt(200) == 0)
			{
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BLOCK_WORM_BLOCK_WRIGGLE,
						SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F,
						0.9F + random.nextFloat() * 0.15F, false);
			}
		}
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return VoxelShapes.fullCube();
	}
	
	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		if (context instanceof EntityShapeContext entityShapeContext)
		{
			Entity entity = entityShapeContext.getEntity();
			if (entity != null)
			{
				if (entity.fallDistance > 1.5F || entity instanceof ItemEntity)
				{
					return FALLING_SHAPE;
				}
				
				if (entity instanceof FallingBlockEntity/* || false && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending()*/)
				{
					return super.getCollisionShape(state, world, pos, context);
				}
			}
		}
		
		return VoxelShapes.empty();
	}
	
	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return VoxelShapes.empty();
	}

//	public static boolean canWalkOnPowderSnow(Entity entity)
//	{
//		if (entity.getType().isIn(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS))
//		{
//			return true;
//		}
//		else
//		{
//			return entity instanceof LivingEntity ? ((LivingEntity)entity).getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS) : false;
//		}
//	}
	
	@Override
	public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
		if (!world.isClient())
		{
			world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
		}
		
		return new ItemStack(ModBlocks.SpecialBlockItems.WORM_BUCKET);
	}
	
	@Override
	public Optional<SoundEvent> getBucketFillSound()
	{
		return Optional.of(SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type)
	{
		return false;
	}
	
	public static class WormWindingHelper
	{
		//public static final int FADED = -1;
		public static final String KEY = "WormWinding";
		
		public static void setFadeInTicks(PlayerEntity player, int fadeInTicks)
		{
			NbtCompound data = PersistentDataHelper.getData(player, KEY);
			data.putInt("fadeInTicks", fadeInTicks);
			PersistentDataHelper.setData(player, KEY, data);
		}
		
		public static void setRemainingTicks(PlayerEntity player, int remainingTicks)
		{
			NbtCompound data = PersistentDataHelper.getData(player, KEY);
			data.putInt("remainingTicks", remainingTicks);
			PersistentDataHelper.setData(player, KEY, data);
		}
		
		public static int getFadeInTicks(PlayerEntity player)
		{
			return PersistentDataHelper.getData(player, KEY).getInt("fadeInTicks");
		}
		
		public static int getRemainingTicks(PlayerEntity player)
		{
			return PersistentDataHelper.getData(player, KEY).getInt("remainingTicks");
		}
	}
}