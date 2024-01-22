package listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;

public class MoveListener implements Listener{
    // Listener movement
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        // If player has a block in their hand he can run faste
        Material handItem= player.getInventory().getItemInMainHand().getType();
        //player.sendMessage(!handItem.equals(Material.AIR) + "");
        if (handItem.isBlock() && !(handItem.equals(Material.AIR))){
            player.setWalkSpeed(0.5f);
        }
        else{
            player.setWalkSpeed(0.2f);
        }
    }
}
