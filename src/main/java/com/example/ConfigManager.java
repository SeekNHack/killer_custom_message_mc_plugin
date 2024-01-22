package com.example;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

import static com.example.Plugin.LOGGER;

public class ConfigManager {
    Plugin plugin;
    FileConfiguration config;
    public ConfigManager(Plugin plugin) throws IOException {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        //Aggiungo un valore di default
        config.addDefault("defaultMessage", "%killer% ha ucciso %victim%");
        // Se il file di configurazione non esiste, lo creo
        config.save(plugin.getDataFolder() + "/config.yml");
    }

    public void savePlayerCustomMessage(Player player, String message) throws IOException {
        config.set(player.getUniqueId().toString(), message);
        config.save(plugin.getDataFolder() + "/config.yml");
    }
    public String loadPlayerCustomMessage(Player player) {
        // Controllo che il giocatore abbia un messaggio custom
        if (player == null || !config.contains(player.getUniqueId().toString())) {
            return config.getString("defaultMessage");
        }
        return config.getString(player.getUniqueId().toString());
    }

}
