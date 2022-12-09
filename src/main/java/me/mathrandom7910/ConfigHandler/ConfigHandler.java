package me.mathrandom7910.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    private List<Config> configs = new ArrayList<>();

    private String dir;
    public ConfigHandler(String dir) {
        this.dir = dir;
    }

    public Config getConfig(String configStr) {
        for(Config config : configs) {
            if(config.getName().equalsIgnoreCase(configStr)) return config;
        }
        return null;
    }

    public Config addConfig(String name) {
        try {
            Config gCfg = getConfig(name);
            if(gCfg != null) return gCfg;
            return addConfig(new Config(name, dir));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void saveConfigs() {
        try {
            for(Config config : configs) {
                config.save();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config addConfig(Config config) {
        configs.add(config);
        return config;
    }
}
