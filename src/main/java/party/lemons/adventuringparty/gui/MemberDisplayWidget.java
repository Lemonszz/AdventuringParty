package party.lemons.adventuringparty.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import org.lwjgl.opengl.GL11;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.entity.CompanionEntity;
import party.lemons.adventuringparty.party.PartyMember;
import party.lemons.adventuringparty.util.PostDrawable;
import java.util.List;

public class MemberDisplayWidget extends DrawableHelper implements Element, Drawable, PostDrawable
{
	public static final Identifier TEXTURE = new Identifier(AdventuringParty.MODID, "textures/gui/party_display.png");

	private int x, y;
	private PartyMember member;

	public MemberDisplayWidget(PartyMember member, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.member = member;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
		drawTexture(matrices, x, y, 0, 0, 51, 53);

		CompanionEntity entity = member.getEntity();

		//Health
		float barWidth = 35F;
		float healthPercent = entity.getHealth() / entity.getMaxHealth();
		drawTexture(matrices, x + 11, y + 39, 0, 57, (int)(barWidth * healthPercent), 3);

		float hungerPercent = 5F / 10F;
		drawTexture(matrices, x + 11, y + 45, 0, 61, (int)(barWidth * hungerPercent), 3);

		//Portrait
		int rawHeight = MinecraftClient.getInstance().getWindow().getHeight();
		double scale = MinecraftClient.getInstance().getWindow().getScaleFactor();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		int sW = (int)(31 * scale);
		int sH = (int)(31 * scale);
		int sX = (int)((x + 5) * scale);
		int sY = (int)(rawHeight - ((y + 5) * scale)) - (sH);

		GL11.glScissor(sX, sY, sW, sH);
		drawEntity(x + 20, y + 60, 25, mouseX, mouseY, entity);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
		RenderSystem.pushMatrix();
		RenderSystem.translatef((float)x, (float)y, 1050.0F);
		RenderSystem.scalef(1.0F, 1.0F, -1.0F);
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.translate(0.0D, 0.0D, 1000.0D);
		matrixStack.scale((float)size, (float)size, (float)size);
		Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
		Quaternion quaternion2 = Vector3f.POSITIVE_X.getDegreesQuaternion(0 * 20.0F);
		quaternion.hamiltonProduct(quaternion2);
		matrixStack.multiply(quaternion);
		float h = entity.bodyYaw;
		float i = entity.yaw;
		float j = entity.pitch;
		float k = entity.prevHeadYaw;
		float l = entity.headYaw;
		entity.bodyYaw = 225.0F;
		entity.yaw = 200.0F;
		entity.pitch = 0F;
		entity.headYaw = entity.yaw;
		entity.prevHeadYaw = entity.yaw;
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderManager();
		quaternion2.conjugate();
		entityRenderDispatcher.setRotation(quaternion2);
		entityRenderDispatcher.setRenderShadows(false);
		VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		entity.setCustomNameVisible(false);
		Text name = entity.getCustomName();
		entity.setCustomName(null);
		entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, immediate, 15728880);
		entity.setCustomName(name);
		entity.setCustomNameVisible(true);

		immediate.draw();
		entityRenderDispatcher.setRenderShadows(true);
		entity.bodyYaw = h;
		entity.yaw = i;
		entity.pitch = j;
		entity.prevHeadYaw = k;
		entity.headYaw = l;
		RenderSystem.popMatrix();
	}

	@Override
	public void renderPost(MatrixStack matrices, int mouseX, int mouseY, float delta, Screen screen)
	{
		if(isMouseOver(mouseX, mouseY))
		{
			List<Text> text = Lists.newArrayList();
			text.add(new LiteralText(member.getName()));
			screen.renderTooltip(matrices, text, mouseX, mouseY);
		}
	}

	@Override
	public boolean isMouseOver(double mouseX, double mouseY)
	{
		return mouseX >= x && mouseX <= x + 51 && mouseY >= y && mouseY <= y + 53;
	}
}
