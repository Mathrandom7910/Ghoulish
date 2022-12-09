package me.mathrandom7910.ghoulish.client.features.modules.settings.settings;

public class SettingParseException extends Exception {
    public SettingParseException(String name, String invalidValue){
        super("Failed to parse setting " + name + ", with an invalid value of " + invalidValue);
    }
}
