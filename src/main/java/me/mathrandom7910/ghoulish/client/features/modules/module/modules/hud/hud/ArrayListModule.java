package me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.hud;

import me.mathrandom7910.ghoulish.client.event.events.HudRenderEvent;
import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.HudModule;
import me.mathrandom7910.ghoulish.client.features.modules.settings.other.color.RGBASettingCollection;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.BoolSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.EnumSetting;
import me.mathrandom7910.ghoulish.client.features.modules.settings.settings.num.nums.IntSetting;
import me.mathrandom7910.ghoulish.client.misc.Box2D;
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;

import java.awt.Color;
import java.util.*;

public class ArrayListModule extends HudModule {
    private final EnumSetting<SortMode> sort = addEnum("sortmode", "the order in which modules are listed", SortMode.ALPHABETICAL).onSet(this::sort);
    private final BoolSetting invertSort = addBool("invert", "inverts the direction in which modules are sorted", false).onSet(this::sort);

    private final IntSetting sideBuf = addInt("padding", "padding beyond the module name added to the background", 3, 0, 8);

    private final RGBASettingCollection bgArCol = addCol("bgcol", "background color of the arraylist", new Color(33, 82, 171, 144));
    private final RGBASettingCollection arTxtCol = addCol("txtcol", "color of the text on the arraylist", new Color(12, 18, 35, 144));

    private List<Module> sortedModules;

    public ArrayListModule() {
        super("arraylist", "visual list of enabled modules", .5f, .5f);
    }

    @Override
    public void onPostInit() {
        sortedModules = new ArrayList<>(ModuleManager.MODULES);
    }

    private void sort() {
        if(sortedModules == null || mc.textRenderer == null) return;
        switch (sort.get()) {
            case ALPHABETICAL -> sortedModules.sort(Comparator.comparing(Module::getName));
            case SIZE -> sortedModules.sort(Comparator.comparingInt(m -> RenderUtil2d.measureText(m.getName())));
        }

        if(invertSort.get()) {
            Collections.reverse(sortedModules);
        }
    }

    private boolean firstRen = true;

    private final List<Box2D> boxes = new LinkedList<>();

    @Override
    public void onRender(SubData<HudRenderEvent.HudRenderData, Void> subData) {
        if(firstRen) {
            sort();
            firstRen = false;
        }

        int renY = getPos().getY();
        int renX = getPos().getX();

        System.out.println(renX + " " + renY);
        boxes.clear();

        for(Module module : sortedModules) {
            if(!module.isEnabled()) continue;
            int nameWidth = RenderUtil2d.measureText(module.getName());

            boxes.add(RenderUtil2d.drawBox(subData.data().stack(),
                    (renX - nameWidth) - (sideBuf.get() * 2),
                    renY,
                    renX,
                    renY + mc.textRenderer.fontHeight,
                    bgArCol.getColor()));
            RenderUtil2d.drawText(subData.data().stack(), module.getName(), (renX - nameWidth) - sideBuf.get(), renY, arTxtCol.getColor());
            renY += mc.textRenderer.fontHeight;
        }
    }

    @Override
    public boolean doesMouseCollide(double mx, double my) {
        for(Box2D box : boxes) {
            if(mx >= box.getX() && mx <= box.getEndX() && my >= box.getY() && my <= box.getEndY()) {
                return true;
            }
        }
        return false;
    }

    private enum SortMode {
        ALPHABETICAL,
        SIZE
    }
}
