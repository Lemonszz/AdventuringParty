package party.lemons.adventuringparty.party;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

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

	public void read(ListTag tags)
	{
		for(int i = 0; i < tags.size(); i++)
		{
			addMember(PartyMember.fromTag(tags.getCompound(i)));
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
}
