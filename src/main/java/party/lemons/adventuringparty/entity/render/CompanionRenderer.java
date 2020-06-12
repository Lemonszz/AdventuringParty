package party.lemons.adventuringparty.entity.render;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import party.lemons.adventuringparty.entity.CompanionEntity;

public class CompanionRenderer extends LivingEntityRenderer<CompanionEntity, PlayerEntityModel<CompanionEntity>>
{
	public CompanionRenderer(EntityRenderDispatcher r)
	{
		super(r, new PlayerEntityModel<>(0F, false),0.5F);
	}

	@Override
	public Identifier getTexture(CompanionEntity entity)
	{
		return DefaultSkinHelper.getTexture(entity.getUuid());
	}

	protected boolean hasLabel(CompanionEntity e) {
		if(e.isCustomNameVisible())
			return super.hasLabel(e);

		return false;
	}
}
