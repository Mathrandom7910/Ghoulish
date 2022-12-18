package me.mathrandom7910.ghoulish.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.mathrandom7910.ghoulish.client.event.EventInitializer;
import me.mathrandom7910.ghoulish.client.features.commands.CommandManager;
import me.mathrandom7910.ghoulish.client.features.modules.ModuleManager;
import me.mathrandom7910.ghoulish.client.features.modules.module.Module;
import me.mathrandom7910.ghoulish.client.storage.StorageHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class GhoulishClient implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("GHOULISH");
//    public static final Config GUI_CONFIG = GhoulishClient.CONFIG_HANDLER.addConfig("guicfg");
    public static String PREFIX = "$";
    public static final String NAME = "Ghoulish";
    public static final Gson GSON_INST = new GsonBuilder().setPrettyPrinting().create();

    public static boolean loaded = false;



    @Override
    public void onInitializeClient() {
        StorageHandler.init();
        ModuleManager.init();
        CommandManager.init();
        EventInitializer.init();
        StorageHandler.save();

        loaded = true;

        ModuleManager.MODULES.forEach(Module::postInit);
    }
}
