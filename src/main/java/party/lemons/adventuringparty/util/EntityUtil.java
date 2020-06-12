package party.lemons.adventuringparty.util;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

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
}
