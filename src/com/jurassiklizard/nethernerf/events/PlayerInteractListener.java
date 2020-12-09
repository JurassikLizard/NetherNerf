package com.jurassiklizard.nethernerf.events;

import com.jurassiklizard.nethernerf.NetherNerf;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onPlayerInteract(PlayerInteractEvent interactEvent){
        Player player = interactEvent.getPlayer();

        if(player.getEquipment().getItemInMainHand().getType() == Material.COMPASS &&
                NetherNerf.speedrunners.contains(player.getName())){
            interactEvent.setCancelled(true);
        }
    }
}
