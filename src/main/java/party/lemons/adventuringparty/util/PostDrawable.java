package party.lemons.adventuringparty.util;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public interface PostDrawable
{
	void renderPost(MatrixStack matrices, int mouseX, int mouseY, float delta, Screen screen);
}
