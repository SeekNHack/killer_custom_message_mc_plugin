package it.hns.lang;

import it.hns.Plugin;

public class LangManager {
    Plugin plugin;
    public LangManager(Plugin plugin) {
        this.plugin = plugin;
        //Aggiungo un valore di default
    }
    public String getString(String key) {
        return plugin.configManager.langConfig.getString(key);
    }
}
