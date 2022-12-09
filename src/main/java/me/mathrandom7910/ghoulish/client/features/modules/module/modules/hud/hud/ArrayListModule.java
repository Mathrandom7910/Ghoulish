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
import me.mathrandom7910.ghoulish.client.util.RenderUtil2d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayListModule extends HudModule {
    private final EnumSetting<SortMode> sort = addEnum("sortmode", "the order in which modules are listed", SortMode.ALPHABETICAL).onSet(this::sort);
    private final BoolSetting invertSort = addBool("invert", "inverts the direction in which modules are sorted", false).onSet(this::sort);

    private final IntSetting sideBuf = addInt("padding", "padding beyond the module name added to the highlight", 3, 0, 8);

    private final RGBASettingCollection bgArCol = addCol("bgcol", "backgorund color of the arraylist", new Color(33, 82, 171, 144));

    private List<Module> sortedModules;

    public ArrayListModule() {
        super("arraylist", "visual list of enabled modules");
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

    boolean firstRen = true;

    @Override
    public void onRender(SubData<HudRenderEvent.HudRenderData, Void> subData) {
        if(firstRen) {
            sort();
            firstRen = false;
        }

        int renY = mc.textRenderer.fontHeight;
        int winWidth = mc.getWindow().getWidth() / 2;
//        System.out.println("rendering " + mc.getWindow().getWidth());
        for(Module module : sortedModules) {
            if(!module.isEnabled()) continue;
            int nameWidth = RenderUtil2d.measureText(module.getName());

            RenderUtil2d.drawBox(subData.data().stack(), (winWidth - nameWidth) - (sideBuf.get() * 2), renY, winWidth, renY + mc.textRenderer.fontHeight, bgArCol.getColor());
            RenderUtil2d.drawText(subData.data().stack(), module.getName(), (winWidth - nameWidth) - sideBuf.get(), renY, Color.BLACK);
            renY += mc.textRenderer.fontHeight;
        }
    }

    @Override
    public boolean doesMouseCollide(float mx, float my) {
        int y = getPos().getY();
        int winWidth = mc.getWindow().getWidth() / 2;
        for(Module module : sortedModules) {
            if(!module.isEnabled()) continue;
            int nameWidth = RenderUtil2d.measureText(module.getName());
//            if(mx >= winWidth - nameWidth && mx < )
        }
        return false;
    }

    private enum SortMode {
        ALPHABETICAL,
        SIZE
    }

    private enum ColorMode {
        STATIC,
        RAINBOW
    }
}
