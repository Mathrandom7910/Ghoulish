package me.mathrandom7910.ghoulish.client.features.gui.screens;

import me.mathrandom7910.ghoulish.client.event.events.HudRenderEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.HudModule;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.hud.HudEditorModule;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class HudEditorScreen extends Screen implements MCInst {
    private @Nullable HudModule draggingModule = null;
    private int dragX;
    private int dragY;
    private final HudEditorModule hudEditor = ModuleManager.getModule(HudEditorModule.class);

    public HudEditorScreen() {
        super(Text.of("hud_editor"));
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        RenderUtil2d.drawBox(stack, 0, 0, mc.getWindow().getWidth() / 2, mc.getWindow().getHeight() / 2, hudEditor.HUD_BG.getColor());
        SubData<HudRenderEvent.HudRenderData, Void> subData = new SubData<>(new HudRenderEvent.HudRenderData(stack, delta));
        for(Module mod : ModuleManager.MODULES) {
            if(mod.isEnabled() && mod instanceof HudModule hudModule) {
                hudModule.onRender(subData);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(draggingModule != null) return false;
        if(button == 0) {
            for (Module mod : ModuleManager.MODULES) {
                if (mod.isEnabled() && mod instanceof HudModule hudModule) {
                    if (hudModule.doesMouseCollide(mouseX, mouseY)) {
                        draggingModule = hudModule;
                        dragX = (int) (mouseX - hudModule.getPos().getX());
                        dragY = (int) (mouseY - hudModule.getPos().getY());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(draggingModule == null || button != 0) return false;

        draggingModule = null;
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if(draggingModule == null) return;

        draggingModule.setPos((int) mouseX - dragX, (int) mouseY - dragY);

        if(hudEditor.SNAPPING.get()) {
            draggingModule.snap();
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
