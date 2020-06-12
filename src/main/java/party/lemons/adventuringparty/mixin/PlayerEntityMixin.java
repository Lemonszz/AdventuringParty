package party.lemons.adventuringparty.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.adventuringparty.party.Party;
import party.lemons.adventuringparty.party.PartyProvider;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements PartyProvider
{
	public PlayerEntityMixin(EntityType<?> type, World world)
	{
		super(type, world);
	}

	@Inject(at=@At("TAIL"), method = "<init>")
	public void onConstruct(World world, BlockPos blockPos, GameProfile gameProfile, CallbackInfo cbi)
	{
		party = new Party((PlayerEntity)(Object)this);
	}

	@Inject(at = @At("TAIL"), method = "writeCustomDataToTag(Lnet/minecraft/nbt/CompoundTag;)V")
	public void writeCustomDataToTag(CompoundTag tag, CallbackInfo cbi)
	{
		tag.put("PlayerParty", getParty().write());
	}

	@Inject(at = @At("TAIL"), method = "readCustomDataFromTag(Lnet/minecraft/nbt/CompoundTag;)V")
	public void readCustomDataFromTag(CompoundTag tag, CallbackInfo cbi)
	{
		getParty().read(world, tag.getList("PlayerParty", 10));
	}

	@Override
	public Party getParty()
	{
		return party;
	}

	public Party party;
}
