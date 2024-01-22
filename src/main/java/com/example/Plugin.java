package com.example;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.commands.KillMessageCommand;
import com.example.lang.LangManager;
import com.example.listeners.JoinListener;
import com.example.listeners.KillListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.example.listeners.BreakListener;
import com.example.listeners.MoveListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
        this.getServer().getPluginManager().registerEvents(new MoveListener(), this);
        this.getServer().getPluginManager().registerEvents(new BreakListener(), this);
        this.getServer().getPluginManager().registerEvents(new KillListener(this), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);

        // Commands
        KillMessageCommand kmc = new KillMessageCommand(this);
    }

    public void onDisable() {
        LOGGER.info(configManager.langConfig.getString("plugin.disabled"));
    }
}