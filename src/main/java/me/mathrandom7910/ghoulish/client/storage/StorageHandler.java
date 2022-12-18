package me.mathrandom7910.ghoulish.client.storage;

import me.mathrandom7910.ghoulish.client.GhoulishClient;
import me.mathrandom7910.ghoulish.client.features.modules.Category;
import me.mathrandom7910.ghoulish.client.storage.storage.CategoryStorage;

import java.io.*;

public class StorageHandler {
    public static StorageData DATA;
    private static final File configPath = new File("./mathrandom7910/ghoulish/config");
    private static final File configFile = new File("./mathrandom7910/ghoulish/config/config.json");

    public static void init() {
        if(!configFile.exists()) {
            DATA = new StorageData();

            for(Category category : Category.values()) {
                DATA.getCategories().computeIfAbsent(category.name(), e -> new CategoryStorage(category));
            }
        } else {
            try {
                Reader reader = new FileReader(configFile);
                DATA = GhoulishClient.GSON_INST.fromJson(reader, StorageData.class);
                reader.close();

                for(var d : DATA.getCategories().values()) {
                    for(var m : d.getModulesMap().values()) {
                        for(var es : m.entrySet()) {
                            System.out.println(es.getKey() + " " + es.getValue());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                configFile.delete();
                DATA = new StorageData();
            }
        }
    }

    public static void save() {
        if(!configPath.exists()) {
            configPath.mkdirs();
        }

        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }

            Writer fw = new FileWriter(configFile);
            GhoulishClient.GSON_INST.toJson(DATA, fw);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
