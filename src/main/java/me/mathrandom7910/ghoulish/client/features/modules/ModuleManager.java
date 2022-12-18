package me.mathrandom7910.ghoulish.client.features.modules;

import me.mathrandom7910.ghoulish.client.event.sub.SubData;
import me.mathrandom7910.ghoulish.client.event.sub.Subscriptions;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubKey;
import me.mathrandom7910.ghoulish.client.event.sub.interfaces.ISubscription;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.client.GuiModule;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.client.VersionSpoof;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.combat.*;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.hud.ArrayListModule;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.hud.hud.HudEditorModule;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.misc.FakePlayer;
import me.mathrandom7910.ghoulish.client.features.modules.module.modules.player.Eagle;
import me.mathrandom7910.ghoulish.client.features.modules.settings.Setting;
import me.mathrandom7910.ghoulish.client.misc.MCInst;
import me.mathrandom7910.ghoulish.client.misc.OnKeyEvent;
import me.mathrandom7910.ghoulish.client.storage.StorageHandler;
import me.mathrandom7910.ghoulish.client.storage.storage.CategoryStorage;
import me.mathrandom7910.ghoulish.client.util.ChatUtil;
import me.mathrandom7910.ghoulish.client.util.KeyUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;


public class ModuleManager implements ISubKey, MCInst {
    public static final List<Module> MODULES = new ArrayList<>();
    private static final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();

    private static @Nullable Module bindingModule;

    public static void init() {
        addModules();
        MODULES.sort(Comparator.comparing(Module::getName));
        loadModules();
        Subscriptions.addSub(new ModuleManager());
    }

    public static @Nullable Module getModule(String modName) {
        for(Module module : MODULES) {
            if(module.getName().equalsIgnoreCase(modName)) return module;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getModule(Class<T> moduleClass) {
        return (T) moduleMap.get(moduleClass);
    }

    private static <T extends Module> void add(T module) {

        if(moduleMap.containsKey(module.getClass())) {
            throw new RuntimeException("Module " + module.getName() + " already registered!");
        }
        moduleMap.put(module.getClass(), module);
        MODULES.add(module);
        if(module instanceof ISubscription) {
            Subscriptions.addSub((ISubscription) module);
        }
    }

    private static void loadModules() {
        for(CategoryStorage cs : StorageHandler.DATA.getCategories().values()) {
            for(String modName : cs.getModulesMap().keySet()) {
                Module module = getModule(modName);
                if(module == null) {
                    continue;
                }

                for(Map<String, String> modStor : cs.getModulesMap().values()) {
                    for(String setName : modStor.keySet()) {
                        Setting<?> setting = module.getSetting(setName);
                        if(setting == null) {
                            continue;
                        }

                        try {
                            setting.set(modStor.get(setName));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public static @Nullable Module getBindingModule() {
        return bindingModule;
    }

    public static void setBindingModule(@Nullable Module bindingModule) {
        ModuleManager.bindingModule = bindingModule;
        if(bindingModule != null) ChatUtil.info("Binding " + bindingModule.getName() + " ...");
    }

    @Override
    public void onKey(SubData<OnKeyEvent, CallbackInfo> subData) {
        if(subData.data().action() != 1 || mc.currentScreen != null) return;

        if(bindingModule != null) {
            bindingModule.getBind().set(subData.data().key());
            bindingModule = null;
            ChatUtil.info("Set bind to " + subData.data().key() + " (key " + KeyUtil.getKeyName(subData.data().key()) + ")");
            return;
        }

        for(Module m : MODULES) {
            if(m.getBind().get().equals(subData.data().key())) {
                m.toggle();
            }
        }
    }

    public static List<Module> inCategory(Category category) {
        return MODULES.stream().filter(mod -> mod.getCategory().equals(category)).toList();
    }

    private static void addModules() {
        add(new AutoClicker());
        add(new GuiModule());
        add(new AutoCrystal());
        add(new VersionSpoof());
        add(new AutoAttack());
        add(new FakePlayer());
        add(new Velocity());
        add(new Reach());
        add(new AimAssist());
        add(new ArrayListModule());
        add(new Eagle());
        add(new HudEditorModule());
    }
}
