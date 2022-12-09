package me.mathrandom7910.ghoulish.client.features.gui.widget.widgets;

import me.mathrandom7910.ghoulish.client.features.gui.widget.GuiWidget;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.misc.MouseData;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleWidget extends GuiWidget<SettingWidget> {
    private final Module module;
    private int lChild;

    public ModuleWidget(Module m) {
        super(m.getName());
        module = m;

        for(Setting<?> setting : module.getSettings()) {
            getChildren().add(new SettingWidget(setting, this));
//            setLargest(RenderUtil2d.measureText(setting.getName()));
        }
    }

    @Override
    public void render(GuiWidget<?> parent, int largestChild, MatrixStack stack, MouseData mouseData) {
        RenderUtil2d.drawBox(stack, getPos(), largestChild, RenderUtil2d.FONT_HEIGHT, module.isEnabled() ? guiModule.GUI_ENABLED.getColor() : guiModule.GUI_COLOR.getColor());
        RenderUtil2d.drawText(stack, getName(), getPos().add((largestChild - RenderUtil2d.measureText(getName())) / 2), guiModule.GUI_TEXT_COLOR.getColor());
        lChild = largestChild;

        if (!isExpanded()) return;

        int subI = 0;
        // rewrite setting requirements

        renLoop: for (int i = 0; i < getChildren().size(); i++) {
            SettingWidget settingWidget = getChildren().get(i);
            if(settingWidget.getSetting().getRequirements() != null) {
                for(var req : settingWidget.getSetting().getRequirements()) {
                    if(!req.setting().get().equals(req.val())) {
                        subI++;
                        settingWidget.setPos(getPos().add(largestChild, ((i - subI) * RenderUtil2d.FONT_HEIGHT)));
                        continue renLoop;
                    }
                }
            }

            settingWidget.setPos(getPos().add(largestChild, ((i - subI) * RenderUtil2d.FONT_HEIGHT)));
            settingWidget.render(this, getLargest(), stack, mouseData);
        }
    }

    @Override
    public boolean onMouseMove(double mouseX, double mouseY) {
        if(!isExpanded()) return false;
        return super.onMouseMove(mouseX, mouseY);
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        if (mouseX >= getPos().getX() && mouseX <= getPos().getX() + lChild && mouseY >= getPos().getY() && mouseY <= getPos().getY() + RenderUtil2d.FONT_HEIGHT) {
            if(button == 1) {
                setExpanded(!isExpanded());
            } else if (button == 0) {
                module.toggle();
            }
            return true;
        }

        if(!isExpanded()) return false;

        return super.onMouseDown(mouseX, mouseY, button);
    }
}
