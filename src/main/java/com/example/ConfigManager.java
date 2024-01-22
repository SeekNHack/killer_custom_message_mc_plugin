package com.example;

import com.example.lang.LangManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static com.example.Plugin.LOGGER;

public class ConfigManager {
    Plugin plugin;
    FileConfiguration config;
    public FileConfiguration langConfig;
    public ConfigManager(Plugin plugin) throws IOException {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        // Copia il file di configurazione dalla libreria
        plugin.saveResource("config.yml", /* replace */ false);
        plugin.saveResource("it.yml", /* replace */ false);
        plugin.saveResource("en.yml", /* replace */ false);
        plugin.saveDefaultConfig();
        // Copia i file di lingua dalla libreria
        this.langConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), config.get("lang") + ".yml"));
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
