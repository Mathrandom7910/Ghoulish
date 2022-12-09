package me.mathrandom7910.ghoulish.client.features.gui;

import me.mathrandom7910.ghoulish.client.features.gui.widget.GuiWidget;
import me.mathrandom7910.ghoulish.client.features.gui.widget.widgets.CategoryWidget;
import me.mathrandom7910.ghoulish.client.features.gui.widget.widgets.ModuleWidget;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.client.GuiModule;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.MouseData;
import me.mathrandom7910.ghoulish.client.misc.Pos;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClickGui extends Screen implements MCInst {
    private final List<CategoryWidget> cats = new ArrayList<>();
    private final GuiModule guiModule = ModuleManager.getModule(GuiModule.class);
    public static @Nullable CategoryWidget dragging;
    public static GuiWidget<?> renderingDesc;
    public static int dragx;
    public static int dragy;

    public ClickGui() {
        super(Text.of("ClickGui"));
        renderingDesc = null;

        Map<Category, CategoryWidget> catMap = new HashMap<>();

        int posInc = 50;
        for(Module module : ModuleManager.MODULES) {

            CategoryWidget catWidget;
            if(!catMap.containsKey(module.getCategory())) {
                posInc += RenderUtil2d.measureText(module.getCategory().name());
                catWidget = new CategoryWidget(module.getCategory(), new Pos(50, posInc));
                catMap.put(module.getCategory(), catWidget);
                cats.add(catWidget);
            } else {
                catWidget = catMap.get(module.getCategory());
            }

            catWidget.getChildren().add(new ModuleWidget(module));
            catWidget.postInit();
        }

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        System.out.println(mouseX + ", " + mouseY + ", " + delta);
        fill(matrices, 0, 0, 2000, 2000, guiModule.GUI_BG_COLOR.getColor().hashCode());
        for(CategoryWidget cat : cats) {
            cat.render(null, -1, matrices, new MouseData(mouseX, mouseY));
        }


    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(CategoryWidget categoryWidget : cats) {
            if(categoryWidget.onMouseDown(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if(dragging != null) {
            dragging.setPos(new Pos((int) mouseX - dragx, (int) mouseY - dragy));
        }

        for(CategoryWidget categoryWidget : cats) {
            if(categoryWidget.onMouseMove(mouseX, mouseY)) {
                return;
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = null;
        for(CategoryWidget categoryWidget : cats) {
            if(categoryWidget.onMouseUp(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateNarrator() {
        super.updateNarrator();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
