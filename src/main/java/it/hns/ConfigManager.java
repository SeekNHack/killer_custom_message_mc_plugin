package it.hns;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    Plugin plugin;
    public YamlConfiguration config;
    public FileConfiguration langConfig;
    public ConfigManager(Plugin plugin) throws IOException {
        this.plugin = plugin;
        plugin.saveResource("config.yml", /* replace */ false);
        plugin.saveResource("it.yml", /* replace */ false);
        plugin.saveResource("en.yml", /* replace */ false);
        this.config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        // Copia il file di configurazione dalla libreria
        plugin.saveDefaultConfig();
        // Copia i file di lingua dalla libreria
        this.langConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), config.get("lang") + ".yml"));
    }
    public void savePlayerCustomMessage(Player player, String message) throws IOException {
        // Salva il messaggio custom del giocatore nel file di configurazione
        config.set("messages."+player.getUniqueId(), message);
        config.save(plugin.getDataFolder()+"/config.yml");
    }
    public String loadPlayerCustomMessage(Player player) {
        // Controllo che il giocatore abbia un messaggio custom
        if (player == null || !config.contains("messages."+player.getUniqueId())) {
            return config.getString("defaultMessage");
        }
        return config.getString("messages."+player.getUniqueId());
    }

    public String getWeapon(String weapon) {
        return langConfig.getString("weapons." + weapon);
    }

}
