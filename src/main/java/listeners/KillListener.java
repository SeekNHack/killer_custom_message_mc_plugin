package listeners;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.example.ConfigManager;
import com.example.Plugin;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class KillListener implements Listener {
    Plugin plugin;
    ConfigManager config;
    public KillListener(Plugin plugin) throws IOException {
        this.config = new ConfigManager((com.example.Plugin) plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity e = event.getEntity();
        // Controllo che e non sia nulla
        if (e == null || e.getKiller() == null) {
            return;
        }

        String message = config.loadPlayerCustomMessage(e.getKiller());
        //Controllo che il giocatore abbia un messaggio custom
        if (message == null) {
            return;
        }
        String victim = e.getName();
        ItemStack item = e.getKiller().getInventory().getItemInMainHand();
        String itemName;

        // Mando un messaggio in chat con il messaggio custom di morte
        e.getWorld().getPlayers().forEach(player -> player.sendMessage(message.replace("%victim%", victim).replace("%killer%", e.getKiller().getName())));
    }
}
