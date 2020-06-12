package party.lemons.adventuringparty.party;

import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import party.lemons.adventuringparty.AdventuringParty;

import java.util.List;

public class Party
{
	private final List<PartyMember> members;
	private final PlayerEntity player;

	public Party(PlayerEntity player)
	{
		this.player = player;

		members = Lists.newArrayList();
	}

	public Tag write()
	{
		ListTag tags = new ListTag();
		for(int i = 0; i < members.size(); i++)
		{
			tags.add(members.get(i).writeToNBT());
		}
		return tags;
	}

	public void read(World world, ListTag tags)
	{
		clear();
		for(int i = 0; i < tags.size(); i++)
		{
			addMember(PartyMember.fromTag(world, tags.getCompound(i)));
		}
	}

	public boolean addMember(PartyMember member)
	{
		if(canAddMember(member))
		{
			return members.add(member);
		}

		return false;
	}

	public boolean canAddMember(PartyMember member)
	{
		return members.size() < 4;
	}

	public static Party getPlayerParty(PlayerEntity playerEntity)
	{
		return ((PartyProvider)playerEntity).getParty();
	}

	public void spawnPartyMember(PartyMember member)
	{
		if(player.world.isClient() || !members.contains(member))
			return;

		Entity e = member.recreateEntity(player.world);
		e.resetPosition(player.getX(), player.getY(), player.getZ());
		e.updatePosition(player.getX(), player.getY(), player.getZ());
		player.world.spawnEntity(e);

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeInt(e.getEntityId());
		buf.writeInt(members.indexOf(member));
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, AdventuringParty.NET_SEND_MEMBER_ENTITY, buf);
	}

	public List<PartyMember> getMembers()
	{
		return members;
	}

	public void clear()
	{
		members.clear();
	}
}
