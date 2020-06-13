package party.lemons.adventuringparty.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.party.PartyMember;
import party.lemons.adventuringparty.util.EntityUtil;

public class CompanionEntity extends MobEntityWithAi
{
	private PartyMember member;

	public CompanionEntity(EntityType<? extends MobEntityWithAi> entityType, World world)
	{
		super(entityType, world);
	}

	public CompanionEntity(PartyMember member, World world)
	{
		this(AdventuringParty.COMPANION, world);
		this.member = member;

		setCustomName(new LiteralText(member.getName()));
		setCustomNameVisible(true);
		setCanPickUpLoot(true);
	}

	@Override
	public void tick()
	{
		super.tick();
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3499999940395355D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
	}

	@Override
	public boolean canPickUp(ItemStack stack)
	{
		return super.canPickUp(stack);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand)
	{
		return super.interactMob(player, hand);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(AdventuringParty.NET_SEND_SPAWN, EntityUtil.writeEntitySpawn(this));
	}
}
