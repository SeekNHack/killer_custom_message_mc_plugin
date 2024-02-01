package com.example.commands;

import com.example.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class KillMessageCommand implements CommandExecutor {
    private final Plugin plugin;
    public KillMessageCommand(Plugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("kcm").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se il comando è stato eseguito da un giocatore
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.onlyplayer"));
            return true;
        }
        if (!sender.hasPermission("kcm.command")) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.noperm"));
            return true;
        }
        // Verifica se il comando ha il giusto numero di argomenti e il corretto uso
        if (args.length <= 1 || !args[0].equalsIgnoreCase("set")) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.usage"));
            return true;
        }
        // Ottieni il giocatore che ha eseguito il comando
        Player player = (Player) sender;

        // Ottieni la stringa dall'argomento del comando
        String nuovaStringa = String.join(" ", args).replaceFirst("set ", "");
        plugin.getLogger().info(plugin.configManager.langConfig.toString());
        if (nuovaStringa.length() > 60) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.toolong"));
            return true;
        }
        // Se la stringa non contiene %victim% e %killer%, non è valida
        if (!nuovaStringa.contains("%victim%") || !nuovaStringa.contains("%killer%") || !nuovaStringa.contains("%weapon%")) {
            sender.sendMessage(plugin.configManager.langConfig.getString("error.invalid"));
            sender.sendMessage("");
            return true;
        }

        // Esegui le operazioni desiderate con la stringa (ad esempio, salvarla in un file o nel database)
        // Sostituisci le prossime linee con la tua logica specifica
        // esempio: ConfigManager.salvaStringa(player, nuovaStringa);

        // Altrimenti, salvala nel file di configurazione
        try {
            plugin.configManager.savePlayerCustomMessage(player, nuovaStringa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sender.sendMessage(plugin.configManager.langConfig.getString("success.set"));
        return true;
    }

}
