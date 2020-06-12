package party.lemons.adventuringparty;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.BlockAttackInteractionAware;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import party.lemons.adventuringparty.entity.CompanionEntity;
import party.lemons.adventuringparty.party.Party;
import party.lemons.adventuringparty.party.PartyMember;
import party.lemons.adventuringparty.util.EntityUtil;

public class AdventuringParty implements ModInitializer
{
	public static final String MODID = "adventuringparty";

	public static final EntityType<CompanionEntity> COMPANION = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<CompanionEntity>)CompanionEntity::new).trackable(128, 3).dimensions(EntityDimensions.fixed(0.6F, 1.8F)).build();
	public static final Identifier NET_SEND_SPAWN = new Identifier(MODID, "spawn_entity");
	public static final Identifier NET_SEND_PARTY = new Identifier(MODID, "send_party");
	public static final Identifier NET_SEND_MEMBER_ENTITY = new Identifier(MODID, "send_member_entity");

	@Override
	public void onInitialize()
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, "companion"), COMPANION);

		//Debug stuff


		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult)->{
			BlockState state = world.getBlockState(hitResult.getBlockPos());

			if(state.getBlock() == Blocks.SPONGE && !player.world.isClient() && hand == Hand.MAIN_HAND)
			{
				Party party = Party.getPlayerParty(player);
				PartyMember newMember = PartyMember.createNew(world);
				if(party.addMember(newMember))
				{
					EntityUtil.syncParty(player);

					party.spawnPartyMember(newMember);
				}

				return ActionResult.SUCCESS;
			}


			return ActionResult.PASS;
		});
	}
}
