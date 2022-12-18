package me.mathrandom7910.ghoulish.client.storage;

import me.mathrandom7910.ghoulish.client.storage.storage.CategoryStorage;

import java.util.HashMap;
import java.util.Map;

public class StorageData {
    private final Map<String, CategoryStorage> categories = new HashMap<>();

    public Map<String, CategoryStorage> getCategories() {
        return categories;
    }
}
