package party.lemons.adventuringparty.party;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import party.lemons.adventuringparty.util.NameGenerator;

public class PartyMember
{
	private String name;
	private SimpleInventory inventory;
	private AttributeContainer attributes;

	public PartyMember(String name, SimpleInventory inventory, AttributeContainer attributes)
	{
		this.name = name;
		this.inventory = inventory;
		this.attributes = attributes;
	}

	public CompoundTag writeToNBT()
	{
		CompoundTag tags = new CompoundTag();

		tags.putString(NBT_NAME, name);
		tags.put(NBT_INVENTORY, inventory.getTags());
		tags.put(NBT_ATTRIBUTES, attributes.toTag());

		return tags;
	}

	public static PartyMember createNew()
	{
		String name = NameGenerator.createName();
		SimpleInventory inventory = new SimpleInventory(41);
		AttributeContainer attributeContainer = new AttributeContainer(DefaultAttributeRegistry.get(EntityType.PLAYER));

		return new PartyMember(name, inventory, attributeContainer);
	}

	public static PartyMember fromTag(CompoundTag tag)
	{
		String name = tag.getString(NBT_NAME);
		SimpleInventory inventory = new SimpleInventory(41);
		inventory.readTags(tag.getList(NBT_INVENTORY, 10));
		AttributeContainer attributeContainer = new AttributeContainer(DefaultAttributeRegistry.get(EntityType.PLAYER));
		attributeContainer.fromTag(tag.getList(NBT_ATTRIBUTES, 10));
		PartyMember member = new PartyMember(name, inventory, attributeContainer);

		return member;
	}

	public String getName()
	{
		return name;
	}

	public SimpleInventory getInventory()
	{
		return inventory;
	}

	public AttributeContainer getAttributes()
	{
		return attributes;
	}

	private static final String NBT_NAME = "name";
	private static final String NBT_INVENTORY = "inventory";
	private static final String NBT_ATTRIBUTES = "attributes";
}
