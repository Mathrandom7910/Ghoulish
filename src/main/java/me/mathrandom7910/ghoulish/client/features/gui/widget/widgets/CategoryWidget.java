package me.mathrandom7910.ghoulish.client.features.gui.widget.widgets;

import me.mathrandom7910.ConfigHandler.Config;
import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.features.gui.ClickGui;
import me.mathrandom7910.ghoulish.client.features.gui.widget.GuiWidget;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.MouseData;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

public class CategoryWidget extends GuiWidget<ModuleWidget> implements MCInst {
    private final Config guiConfig = GhoulishClient.CONFIG_HANDLER.addConfig("gui_" + getName());
    private final Category cat;

    public CategoryWidget(Category category, Pos startPos) {
        super(category.name());
        cat = category;

        guiConfig.initVal("x", String.valueOf(startPos.getX()));
        guiConfig.initVal("y", String.valueOf(startPos.getY()));
        guiConfig.initVal("exp", "true");

        boolean exp = Boolean.parseBoolean(guiConfig.get("exp"));

        setPos(new Pos(Integer.parseInt(guiConfig.get("x")), Integer.parseInt(guiConfig.get("y"))));
        setExpanded(exp, false);
//        setPos(new Pos(new Random().nextInt(0, 500), new Random().nextInt(0, 500)));
    }

    public void postInit() {
        for (ModuleWidget moduleWidget : getChildren()) {
            setLargest(moduleWidget.getName());
        }
    }

    public void save(@Nullable Pos pos) {
        if (pos != null) {
            guiConfig.set("x", String.valueOf(pos.getX()));
            guiConfig.set("y", String.valueOf(pos.getY()));
        }
        guiConfig.set("exp", String.valueOf(isExpanded()));
        GhoulishClient.CONFIG_HANDLER.saveConfigs();
    }

    @Override
    public void setPos(Pos pos) {
        super.setPos(pos);
        save(pos);
    }

    @Override
    public void render(GuiWidget<?> parent, int largest, MatrixStack stack, MouseData mouseData) {
        RenderUtil2d.drawBox(stack, getPos(), getLargest(), mc.textRenderer.fontHeight, guiModule.GUI_COLOR.getColor());
        setLargest(getName());

        RenderUtil2d.drawText(stack, cat.name(), getPos().add((getLargest() - RenderUtil2d.measureText(getName())) / 2), guiModule.GUI_TEXT_COLOR.getColor());

        if (!isExpanded()) return;
        for (int i = 0; i < getChildren().size(); i++) {
            ModuleWidget moduleWidget = getChildren().get(i);
            setLargest(moduleWidget.getName());

            moduleWidget.setPos(getPos().add(0, (i * RenderUtil2d.FONT_HEIGHT) + RenderUtil2d.FONT_HEIGHT));
            moduleWidget.render(this, getLargest(), stack, mouseData);
        }
    }

    @Override
    protected void setExpanded(boolean exp) {
        setExpanded(exp, true);
    }

    protected void setExpanded(boolean exp, boolean s) {
        super.setExpanded(exp);
        if(s) save(null);
    }

    @Override
    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        if (mouseX >= getPos().getX() && mouseX <= getPos().getX() + getLargest() && mouseY >= getPos().getY() && mouseY <= getPos().getY() + RenderUtil2d.FONT_HEIGHT) {
            if (button == 1) {
                setExpanded(!isExpanded());
            } else if (button == 0) {
                if (ClickGui.dragging == null) {
                    ClickGui.dragging = this;
                    ClickGui.dragx = (int) (mouseX - getPos().getX());
                    ClickGui.dragy = (int) (mouseY - getPos().getY());
                }
            }

            return true;
        }

        return super.onMouseDown(mouseX, mouseY, button);
    }
}
