package party.lemons.adventuringparty.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.party.PartyMember;
import party.lemons.adventuringparty.util.PostDrawable;

import java.util.List;

public class MemberActionButton extends AbstractButtonWidget implements PostDrawable
{
	public static final Identifier TEXTURE = new Identifier(AdventuringParty.MODID, "textures/gui/party_display.png");
	private int iconX, iconY;
	protected PartyMember member;
	protected String text;

	public MemberActionButton(int x, int y, int iconX, int iconY, PartyMember member, String text)
	{
		super(x, y, 11, 11, new LiteralText(""));

		this.iconX = iconX;
		this.iconY = iconY;
		this.member = member;
		this.text = text;
	}

	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		mc.getTextureManager().bindTexture(TEXTURE);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
		int yImage = this.getYImage(this.isHovered());
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		this.drawTexture(matrices, this.x, this.y, 51, 0 + (yImage * 11), this.width, this.height);
		this.renderBg(matrices, mc, mouseX, mouseY);
	}

	@Override
	protected void renderBg(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY)
	{
		int u = 62 + (getIconX() * 7);
		int v = 0 + (getIconY() * 7);
		drawTexture(matrices, this.x + 2, this.y  + 2, u, v, 7, 7);
	}

	public int getIconX()
	{
		return iconX;
	}

	public int getIconY()
	{
		return iconY;
	}

	@Override
	protected int getYImage(boolean isHovered)
	{
		return isHovered ? 1 : 0;
	}

	@Override
	public void renderPost(MatrixStack matrices, int mouseX, int mouseY, float delta, Screen screen)
	{
		if(isMouseOver(mouseX, mouseY))
		{
			List<Text> text = Lists.newArrayList();
			text.add(new TranslatableText("button.adventuringparty." + text));
			screen.renderTooltip(matrices, text, mouseX, mouseY);
		}
	}
}
