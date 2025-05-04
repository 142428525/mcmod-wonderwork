package kasisuno.wonderwork.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import kasisuno.wonderwork.entity.trivial.ManaNbtManager;
import kasisuno.wonderwork.entity.projectile.MagicWhiteArrowEntity;
import kasisuno.wonderwork.inventory.WandInventory;
import kasisuno.wonderwork.screen.WandScreenHandler;
import kasisuno.wonderwork.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandItem extends Item
{
	//private final float attackDamage;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	private WandInventory inventory = new WandInventory();
	
	public WandItem(int attackDamage, float attackSpeed, Item.Settings settings)
	{
		super(settings);
		
		//this.attackDamage = (float)attackDamage;
		
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
				new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
						/*this.*/attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
						attackSpeed, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}
	
	//public float getAttackDamage()
	//{
	//	return this.attackDamage;
	//}
	
	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.BOW;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 30;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		user.setCurrentHand(hand);
		
		ItemStack stack = user.getStackInHand(hand);
		
		if (!world.isClient)
		{
			if (!stack.hasNbt())
			{
				NbtCompound nbt = new NbtCompound();
				nbt.putFloat("CustomModelData", 0);
				stack.setNbt(nbt);
			}
			else if (!stack.getNbt().contains("CustomModelData"))    //not null
			{
				stack.getNbt().putFloat("CustomModelData", 0);
			}
			
			//
			if (ManaNbtManager.readMana(user) == ManaNbtManager.INVALID)
			{
				ManaNbtManager.writeMana(user, 20);
			}
			
			if (ManaNbtManager.readMana(user) == 0)
			{
				ManaNbtManager.writeMana(user, 20);
				user.sendMessage(Text.literal("csndm"), true);
			}
			//
		}
		
		return TypedActionResult.consume(stack);
	}
	
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks)
	{
		float p = get_charging_progress(getMaxUseTime(stack) - remainingUseTicks);
		
		if (!stack.hasNbt())
		{
			NbtCompound nbt = new NbtCompound();
			nbt.putFloat("CustomModelData", 0);
			stack.setNbt(nbt);
		}
		
		stack.getNbt().putFloat("CustomModelData", Math.round(p * 5));    //not null
	}
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks)
	{
		if (user instanceof PlayerEntity player)
		{
			if (is_low_mana(player, 5))
			{
				return;
			}
			
			int use_ticks = getMaxUseTime(stack) - remainingUseTicks;
			
			//
			//player.sendMessage(Text.literal("wand: on_stopped_using " + use_ticks), true);
			
			float p = get_charging_progress(use_ticks);	//p must be in [0,1]
			
			if (!world.isClient)
			{
				var arrow = new MagicWhiteArrowEntity(world, player);
				arrow.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;//DISALLOWED;
				arrow.setNoGravity(true);
				arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0, p * 3, 1);
				
				world.spawnEntity(arrow);
				
				stack.getNbt().putFloat("CustomModelData", 0);
				
				ManaNbtManager.mapMana(player, val -> val - 5);
			}
			
			world.playSound(null, player.getX(), player.getY(), player.getZ(),
					ModSounds.MAGIC_WHITE_ARROW_SHOOT, SoundCategory.PLAYERS,
					1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + p * 0.5F);
		}
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if (user instanceof PlayerEntity player)
		{
			if (is_low_mana(player, 10))
			{
				return stack;
			}
			
			//
			//player.sendMessage(Text.literal("wand: finish_using"), true);
			
			if (!world.isClient)
			{
				if (player.isSneaking())
				{
					if (stack.getNbt().contains("Inventory", NbtElement.LIST_TYPE))
					{
						inventory.readNbtList(stack.getNbt().getList("Inventory", NbtElement.COMPOUND_TYPE));
					}
					
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerX) ->
							new WandScreenHandler(syncId, playerInventory, inventory),
							Text.empty().append(getName()).append("@").append(player.getName())));
				}
				else
				{
					ItemStack mid = inventory.getStack(4);
					
					if (mid.isOf(Items.ENCHANTED_GOLDEN_APPLE))
					{
						TntEntity tnt = new TntEntity(world, player.getX(), player.getEyeY(), player.getZ(), player);
						tnt.setVelocity(player.getRotationVector().normalize().multiply(3));
						world.spawnEntity(tnt);
						
						ManaNbtManager.mapMana(player, val -> val - 10);
					}
					
					player.getItemCooldownManager().set(this, 50);
				}
				
				stack.getNbt().putFloat("CustomModelData", 0);
			}
		}
		
		return stack;
	}
	
	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner)
	{
		return !miner.isCreative();
	}
	
	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot)
	{
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}
	
	private float get_charging_progress(int use_ticks)
	{
		double g = (double)use_ticks / 20;    //max charging time = 1s
		return g < 1 ? (float)(Math.pow(g - 1, 3) + 1) : 1;
	}
	
	private boolean is_low_mana(PlayerEntity player, int target_amount)
	{
			return ManaNbtManager.readMana(player) < target_amount;
	}
}