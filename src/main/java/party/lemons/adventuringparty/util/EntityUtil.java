package party.lemons.adventuringparty.util;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.party.Party;

public class EntityUtil
{
	public static PacketByteBuf writeEntitySpawn(Entity entity){
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
		buf.writeUuid(entity.getUuid());
		buf.writeVarInt(entity.getEntityId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeFloat(entity.pitch);
		buf.writeFloat(entity.yaw);

		return buf;
	}

	public static void syncParty(PlayerEntity playerEntity)
	{
		if(playerEntity.world.isClient())
			return;

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		CompoundTag tag = new CompoundTag();
		tag.put("Data", Party.getPlayerParty(playerEntity).write());
		buf.writeCompoundTag(tag);
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(playerEntity, AdventuringParty.NET_SEND_PARTY, buf);
	}
}
