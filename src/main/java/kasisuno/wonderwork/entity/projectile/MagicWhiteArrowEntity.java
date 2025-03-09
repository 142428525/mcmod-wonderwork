package kasisuno.wonderwork.entity.projectile;

import kasisuno.wonderwork.entity.trivial.ModEntityTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicWhiteArrowEntity extends PersistentProjectileEntity
{
	public MagicWhiteArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world)
	{
		super(entityType, world);
	}
	
	//create arrow
	public MagicWhiteArrowEntity(World world, LivingEntity owner)
	{
		super(ModEntityTypes.MAGIC_WHITE_ARROW, owner, world);
	}
	
	@Override
	protected ItemStack asItemStack()
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public void tick()
	{
		float prew_yaw = prevYaw;
		super.tick();
		
		if (prevYaw == prew_yaw + 180)
		{
			if (!getWorld().isClient)
			{
				discard_with_boom();
			}
			
			//显示不准确=不显示
//			if (getOwner() instanceof PlayerEntity player)
//			{
//				player.sendMessage(Text.translatable(getType().getTranslationKey() + ".iframe_tip")
//						.formatted(Formatting.RED, Formatting.ITALIC), true);
//			}
		}
		
		if (this.getWorld().isClient && !this.inGround)
		{
			this.getWorld().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(),
					0.0, 0.0, 0.0);
		}
		
		if (isOnFire())
		{
			extinguish();
			//magic doesn't interact with physical fire :)
		}
		
		if (getVelocity().length() < 1E-3 && !getWorld().isClient)
		{
			discard_with_boom();
		}
	}
	
	@Override
	protected void onBlockHit(BlockHitResult blockHitResult)
	{
		super.onBlockHit(blockHitResult);
		
		if (!getWorld().isClient)
		{
			discard_with_boom();
		}
	}
	
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult)
	{
		super.onEntityHit(entityHitResult);
		Entity target = entityHitResult.getEntity();
		
		if (target.getType() == EntityType.ENDERMAN && target instanceof LivingEntity target2)
		{
			Entity owner = getOwner();
			DamageSource damageSource = getDamageSources().create(DamageTypes.MAGIC, this, owner);
			
			if (owner instanceof LivingEntity owner2)
			{
				owner2.onAttacking(target);
			}
			
			if (target.damage(damageSource,
					MathHelper.ceil(MathHelper.clamp(getVelocity().length() * getDamage(),
							0, Integer.MAX_VALUE))))
			{
				if (getPunch() > 0)
				{
					double d = Math.max(0.0,
							1.0 - target2.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
					Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize()
							.multiply(getPunch() * 0.6 * d);
					if (vec3d.lengthSquared() > 0.0)
					{
						target.addVelocity(vec3d.x, 0.1, vec3d.z);
					}
				}
				
				if (!this.getWorld().isClient && owner instanceof LivingEntity owner2)
				{
					EnchantmentHelper.onUserDamaged(target2, owner);
					EnchantmentHelper.onTargetDamaged(owner2, target);
				}
			}
		}
	}
	
	private void discard_with_boom()
	{
		getWorld().sendEntityStatus(this, EntityStatuses.EXPLODE_FIREWORK_CLIENT);
		discard();
	}
	
	//for server-client sync?
	@Override
	public void handleStatus(byte status)
	{
		super.handleStatus(status);
		
		if (status == EntityStatuses.EXPLODE_FIREWORK_CLIENT && getWorld().isClient)
		{
			//nbt = {Explosions:[{Colors:[I; 0x66ccff]}]}
			
			NbtCompound nbt = new NbtCompound();
			NbtList explosions = new NbtList();
			NbtCompound explosion = new NbtCompound();
			explosion.putIntArray("Colors", new int[]{0x66CCFF});
			explosions.add(explosion);
			nbt.put("Explosions", explosions);
			
			var v = getVelocity();
			
			getWorld().addFireworkParticle(getX(), getY(), getZ(), v.x, v.y, v.z, nbt);
		}
	}
}