package party.lemons.adventuringparty;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import party.lemons.adventuringparty.entity.CompanionEntity;
import party.lemons.adventuringparty.entity.render.CompanionRenderer;
import party.lemons.adventuringparty.party.Party;

import java.util.UUID;

public class AdventuringPartyClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		EntityRendererRegistry.INSTANCE.register(AdventuringParty.COMPANION, (r,c)->new CompanionRenderer(r));

		ClientSidePacketRegistry.INSTANCE.register(AdventuringParty.NET_SEND_PARTY, (ctx, data)->{
			Party.getPlayerParty(MinecraftClient.getInstance().player).read(MinecraftClient.getInstance().world, data.readCompoundTag().getList("Data", 10));
		});

		ClientSidePacketRegistry.INSTANCE.register(AdventuringParty.NET_SEND_MEMBER_ENTITY, (ctx, data)->{
			int id = data.readInt();
			int index = data.readInt();
			ctx.getTaskQueue().execute(()->{
				Entity e = MinecraftClient.getInstance().world.getEntityById(id);

				if(e == null || !(e instanceof CompanionEntity))
					return;
				Party.getPlayerParty(MinecraftClient.getInstance().player).getMembers().get(index).setEntity((CompanionEntity)e);
			});

		});

		ClientSidePacketRegistry.INSTANCE.register(AdventuringParty.NET_SEND_SPAWN, (ctx, data)->{
			EntityType<?> type = Registry.ENTITY_TYPE.get(data.readVarInt());
			UUID entityUUID = data.readUuid();
			int entityID = data.readVarInt();
			double x = data.readDouble();
			double y = data.readDouble();
			double z = data.readDouble();
			float pitch = data.readFloat();
			float yaw = data.readFloat();

			ctx.getTaskQueue().execute(() -> {
				ClientWorld world = MinecraftClient.getInstance().world;
				Entity entity = type.create(world);
				if(entity != null) {
					entity.updatePosition(x, y, z);
					entity.updateTrackedPosition(x, y, z);
					entity.pitch = pitch;
					entity.yaw = yaw;

					entity.setEntityId(entityID);
					entity.setUuid(entityUUID);
					world.addEntity(entityID, entity);
				}
			});
		});
	}
}
