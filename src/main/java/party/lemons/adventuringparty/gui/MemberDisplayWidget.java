package party.lemons.adventuringparty.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.party.PartyMember;

public class MemberDisplayWidget extends DrawableHelper implements Element, Drawable
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

		int rawHeight = MinecraftClient.getInstance().getWindow().getHeight();
		double scale = MinecraftClient.getInstance().getWindow().getScaleFactor();

		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		int sW = (int)(31 * scale);
		int sH = (int)(31 * scale);
		int sX = (int)((x + 5) * scale);
		int sY = (int)(rawHeight - ((y + 5) * scale)) - (sH);

		GL11.glScissor(sX, sY, sW, sH);
		InventoryScreen.drawEntity(x + 20, y + 60, 25, mouseX, mouseY, member.getEntity());

		GL11.glDisable(GL11.GL_SCISSOR_TEST);

	}
}
