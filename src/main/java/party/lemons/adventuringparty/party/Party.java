package party.lemons.adventuringparty.party;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.World;

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
		for(int i = 0; i < tags.size(); i++)
		{
			addMember(PartyMember.fromTag(world, tags.getCompound(i)));
		}
	}

	public void addMember(PartyMember member)
	{
		if(canAddMember(member))
		{
			members.add(member);
		}
	}

	public boolean canAddMember(PartyMember member)
	{
		return members.size() < 4;
	}

	public static Party getPlayerParty(PlayerEntity playerEntity)
	{
		return ((PartyProvider)playerEntity).getParty();
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
