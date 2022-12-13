package me.mathrandom7910.ghoulish.client.features.gui.clickgui.widget;


import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.client.GuiModule;
import me.mathrandom7910.ghoulish.client.misc.MouseData;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiWidget<K extends GuiWidget<?>> {
    private final List<K> children = new ArrayList<>();
    private int largestChild = 0;
    private boolean expanded = false;
    protected GuiModule guiModule = ModuleManager.getModule(GuiModule.class);

    private final String name;

    public GuiWidget(String name) {
        this.name = name;
    }

    protected boolean isExpanded() {
        return expanded;
    }

    protected void setExpanded(boolean exp) {
        expanded = exp;
    }

    private Pos pos = new Pos(50, 50);

    public List<K> getChildren() {
        return children;
    }

    public void setLargest(int largest) {
        int largest1 = (guiModule.SIDE_BUFFER.get() * 2) + largest;
        if(largest1 > largestChild) {
            largestChild = largest1;
        }
    }

    public void setLargest(String str) {
        setLargest(RenderUtil2d.measureText(str));
    }

    protected int getLargest() {
        return largestChild;
    }

    protected Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public abstract void render(GuiWidget<?> parent, int largestChild, MatrixStack stack, MouseData mouseData);

    public boolean onMouseDown(double mouseX, double mouseY, int button) {
        for(K widget : getChildren()) {
            if(widget.onMouseDown(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    public boolean onMouseUp(double mouseX, double mouseY, int button) {
        for(K widget : getChildren()) {
            if(widget.onMouseUp(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    public boolean onMouseMove(double mouseX, double mouseY) {
        for(K widget : getChildren()) {
            if(widget.onMouseMove(mouseX, mouseY)) {
                return true;
            }
        }
        return false;
    }
}
