package com.example;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.commands.KillMessageCommand;
import com.example.listeners.KillListener;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * demo java plugin
 */
public class Plugin extends JavaPlugin {
    public static final Logger LOGGER = Logger.getLogger("demo");
    public ConfigManager configManager;


    @Override
    public void onEnable() {
        try {
            registerEvents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.configManager = new ConfigManager(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info(configManager.langConfig.getString("plugin.enabled"));
    }

    public void registerEvents() throws IOException {
        // Listeners
        this.getServer().getPluginManager().registerEvents(new KillListener(this), this);

        // Commands
        KillMessageCommand kmc = new KillMessageCommand(this);
    }

    public void onDisable() {
        LOGGER.info(configManager.langConfig.getString("plugin.disabled"));
    }
}