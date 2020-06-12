package party.lemons.adventuringparty.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.adventuringparty.AdventuringParty;
import party.lemons.adventuringparty.gui.MemberDisplayWidget;
import party.lemons.adventuringparty.party.PartyDisplayRenderer;
import party.lemons.adventuringparty.party.PartyMember;

import java.util.List;

import static party.lemons.adventuringparty.AdventuringParty.testParty;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T>
{

	public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title)
	{
		super(handler, inventory, title);
	}

	@Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo cbi)
	{
		super.render(matrices, mouseX, mouseY, delta);

		for(Element e : children())
		{
			if(e instanceof MemberDisplayWidget)
			{
				((Drawable) e).render(matrices, mouseX, mouseY, delta);
			}
		}
	}

	@Inject(at = @At("TAIL"), method = "init()V")
	public void init(CallbackInfo cbi)
	{
		testParty.clear();
		testParty.addMember(PartyMember.createNew(MinecraftClient.getInstance().world));
		testParty.addMember(PartyMember.createNew(MinecraftClient.getInstance().world));
		testParty.addMember(PartyMember.createNew(MinecraftClient.getInstance().world));
		testParty.addMember(PartyMember.createNew(MinecraftClient.getInstance().world));

		List<PartyMember> members = testParty.getMembers();
		for(int i = 0; i < members.size(); i++)
		{
			PartyMember member = members.get(i);
			int yOffset = i * 54;

			MemberDisplayWidget displayWidget = new MemberDisplayWidget(member, 0, yOffset);
			addChild(displayWidget);
		}
	}

}
