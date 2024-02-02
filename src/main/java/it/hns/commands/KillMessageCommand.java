package it.hns.commands;

import it.hns.ConfigManager;
import it.hns.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;

public class KillMessageCommand implements CommandExecutor {
    private HashMap<String, CommandFunction> subcommands = new HashMap<>();
    private final Plugin plugin;
    public KillMessageCommand(Plugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("kcm").setExecutor(this);
        subcommands.put("set", this::setCommand);
        subcommands.put("reload", this::reloadCommand);
        subcommands.put("help", this::helpCommand);
    }

    private void helpCommand(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage(ChatColor.GOLD+plugin.configManager.langConfig.getString("help.title"));
        for (String key : subcommands.keySet()) {
            commandSender.sendMessage(ChatColor.GOLD+"/kcm " + key+ ChatColor.WHITE+" - "+plugin.configManager.langConfig.getString("help."+key));
        }
    }

    private void reloadCommand(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission("kcm.command.reload") && !commandSender.isOp()) {
            commandSender.sendMessage(plugin.configManager.langConfig.getString("error.noperm"));
            return;
        }
        try {
            plugin.configManager = new ConfigManager(plugin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commandSender.sendMessage(plugin.configManager.langConfig.getString("success.reload"));
    }

    private void setCommand(CommandSender sender, String[] args){
        // Ottieni il giocatore che ha eseguito il comando
        Player player = (Player) sender;
        if(!player.hasPermission("kcm.command.set")) {
            player.sendMessage(plugin.configManager.langConfig.getString("error.noperm"));
            return;
        }
        // Ottieni la stringa dall'argomento del comando
        String nuovaStringa = String.join(" ", args).replace("set ", "");
        plugin.getLogger().info(plugin.configManager.langConfig.toString());
        if (nuovaStringa.length() > 60) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.toolong"));
            return;
        }
        // Se la stringa non contiene %victim%, %killer% e %weapon% non è valida
        if (!nuovaStringa.contains("%victim%") || !nuovaStringa.contains("%killer%") || !nuovaStringa.contains("%weapon%")) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.invalid"));
            sender.sendMessage("");
            return;
        }

        try {
            plugin.configManager.savePlayerCustomMessage(player, nuovaStringa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sender.sendMessage(plugin.configManager.langConfig.getString("success.set"));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se il comando è stato eseguito da un giocatore
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.onlyplayer"));
            return true;
        }
        // Verifica se il comando ha il giusto numero di argomenti e il corretto uso
        if (args.length == 0 || !subcommands.containsKey(args[0])) {
            subcommands.get("help").apply(sender, args);
            return true;
        }
        // Rimuovo il primo argomento dal comando
        subcommands.get(args[0]).apply(sender, args);
        return true;
    }
    @FunctionalInterface
    interface CommandFunction {
        void apply(CommandSender sender, String[] args);
    }
}
