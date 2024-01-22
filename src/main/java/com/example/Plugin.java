package com.example;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import com.example.commands.KillMessageCommand;
import listeners.JoinListener;
import listeners.KillListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import listeners.BreakListener;
import listeners.MoveListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

/*
 * demo java plugin
 */
public class Plugin extends JavaPlugin
{
  public static final Logger LOGGER=Logger.getLogger("demo");
  public ConfigManager configManager;
  @Override
  public void onEnable()
  {
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
      LOGGER.info("Killer Custom Message Plugin enabled");
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
  public void onDisable()
  {
    LOGGER.info("demo disabled");
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("demo"))
    {
      sender.sendMessage("demo");
      return true;
    }
    return false;
  }
}