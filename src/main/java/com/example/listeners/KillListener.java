package com.example.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.example.ConfigManager;
import com.example.Plugin;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class KillListener implements Listener {
    Plugin plugin;
    public KillListener(Plugin plugin) throws IOException {
        this.plugin = plugin;
    }



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getLogger().info("Player death event");
        Player e = event.getEntity();
        // Controllo che e non sia nulla
        if (e.getKiller() == null) {
            return;
        }

        String message = plugin.configManager.loadPlayerCustomMessage(e.getKiller());
        //Controllo che il giocatore abbia un messaggio custom
        if (message == null) {
            return;
        }
        String victim = e.getPlayer().getName();
        ItemMeta item_meta = e.getKiller().getInventory().getItemInMainHand().getItemMeta();
        String item_name = null;
        if (item_meta == null) {
            item_name = plugin.configManager.getWeapon("hand");
        }
        else {
            plugin.getLogger().info("Item meta is not null");
            item_name = item_meta.getDisplayName();
            if (item_name == null || item_name.equals("")) {
                plugin.getLogger().info(e.getKiller().getInventory().getItemInMainHand().getType().toString());
                item_name = plugin.configManager.getWeapon(e.getKiller().getInventory().getItemInMainHand().getType().toString().toLowerCase());
            }
        }
        // Mando un messaggio in chat con il messaggio custom di morte
        String finalMessage = ChatColor.translateAlternateColorCodes('&', message.replace("%victim%", victim).replace("%killer%", e.getKiller().getName()).replace("%weapon%", item_name));
        e.getWorld().getPlayers().forEach(player -> player.sendMessage(finalMessage));
        event.setDeathMessage("");
    }
}