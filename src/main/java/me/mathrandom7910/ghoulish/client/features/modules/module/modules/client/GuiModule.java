package me.mathrandom7910.ghoulish.client.features.modules.module.modules.client;

import me.mathrandom7910.ghoulish.client.features.gui.ClickGui;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.ByteSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.LongSetting;
import me.mathrandom7910.ghoulish.client.misc.OnKeyEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubKey;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubTick;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

public class GuiModule extends Module implements ISubKey, ISubTick {
    private ClickGui clickGui;
    private byte lastSideBuf;
    public final RGBASettingCollection GUI_BG_COLOR = addCol("guibackgroundcolor", "bg color", new Color(91, 149, 154, 0));
    public final RGBASettingCollection GUI_COLOR = addCol("guicolor", "color of the gui", new Color(10, 20, 30, 200));
    public final RGBASettingCollection GUI_TEXT_COLOR = addCol("textcolor", "color of the text", new Color(71, 164, 215, 220));
    public final RGBASettingCollection GUI_ENABLED = addCol("guienabled", "color of enabled modules/settings", new Color(30, 134, 79, 200));
    public final ByteSetting SIDE_BUFFER = addByte("sidebuffer", "buffer (in pixels) from the edge that text should be", 4, 1, 10);
    public final LongSetting DESC_DELAY = addLong("descdelay", "delay (in ms) on when to begin rendering a description", 1000, 0, 5000);

    public GuiModule() {
        super("gui", "the ugliest gui you'll ever see, (some settings will require the gui to relaunch to take effect)", Category.CLIENT);
    }

    @Override
    public void onEnable() {
        if (lastSideBuf != SIDE_BUFFER.get() || clickGui == null) {
            clickGui = new ClickGui();
            lastSideBuf = SIDE_BUFFER.get();
        }
        mc.setScreen(clickGui);
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen != null) mc.currentScreen.close();
    }

    @Override
    public void onKey(SubData<OnKeyEvent, CallbackInfo> subData) {
        OnKeyEvent ke = subData.data();
        if (ke.key() == getBind().get() && ke.action() == 1) {
            disable();
        }
    }

    @Override
    public void onTick(SubData<ClientWorld, Void> subData) {
        if (!(mc.currentScreen instanceof ClickGui)) {
            disable();
        }
    }
}
