package me.mathrandom7910.ghoulish.client.storage.storage;

import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;

import java.util.HashMap;
import java.util.Map;

public class CategoryStorage {
    private final Map<String, Map<String, String>> modules = new HashMap<>();
    private int x = (int) (Math.random() * 200);
    private int y = (int) (Math.random() * 200);
    private boolean expanded = false;

    public CategoryStorage(Category category) {
        for(Module module : ModuleManager.inCategory(category)) {
            Map<String, String> modStor = new HashMap<>();

            for(Setting<?> setting : module.getSettings()) {
                modStor.putIfAbsent(setting.getName(), setting.toString());
            }

            modules.putIfAbsent(module.getName(), modStor);

        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public Map<String, Map<String, String>> getModulesMap() {
        return modules;
    }
}
