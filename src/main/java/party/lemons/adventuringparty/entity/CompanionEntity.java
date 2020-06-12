package party.lemons.adventuringparty.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.world.World;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.util.EntityUtil;

public class CompanionEntity extends MobEntityWithAi
{
	public CompanionEntity(EntityType<? extends MobEntityWithAi> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new CustomPayloadS2CPacket(AdventuringParty.NET_SEND_SPAWN, EntityUtil.writeEntitySpawn(this));
	}
}
