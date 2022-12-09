package me.mathrandom7910.ConfigHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Config {
    private HashMap<String, String> map = new HashMap<>();
    private String name;
    private String dir;
    private String path;

    public Config(String name, String dir) throws IOException {
        this(name, dir, "cfghndl");
    }

    public Config(String name, String dir, String fileType) throws IOException {
        this.name = name;
        this.dir = dir;
        path = dir + "/" + name + "." + fileType;

        init();

        update();
        save();
    }

    private void init() throws IOException {
        new File(dir).mkdirs();
        new File(path).createNewFile();
    }

    public Config initVal(String key, String val) {
        if(get(key) == null) set(key, val);
        return this;
    }

    public void update() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = reader.readLine();
        if(line == null) return;
        JsonObject obj = new JsonParser().parse(line).getAsJsonObject();

        for(Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            set(entry.getKey(), entry.getValue().getAsString());
        }
    }

    public void set(String key, String val) {
        map.put(key, val);
    }

    public String get(String key) {
        return map.get(key);
    }

    public Set<String> list() {
        return map.keySet();
    }

    public String getName() {
        return this.name;
    }

    public void save() throws IOException {
        JsonObject obj = new JsonObject();
        for(String key : list()) {
            obj.addProperty(key, get(key));
        }
        File file = new File(path);
        PrintWriter writer = new PrintWriter(file);
        writer.println(obj.toString());
        writer.flush();
        writer.close();
    }
}
