package com.jurassiklizard.nethernerf.events;

import com.jurassiklizard.nethernerf.NetherNerf;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class PlayerNetherListener implements Listener {
    @EventHandler
    public void onEnterNether(PlayerChangedWorldEvent worldEvent){
        Player player = worldEvent.getPlayer();

        if(player.getWorld().getEnvironment() != World.Environment.NETHER){
            return;
        }
        if(!NetherNerf.speedrunners.contains(player.getDisplayName())){
            return;
        }
        if(NetherNerf.fortressLocations.get(player.getWorld()) == null){
            return;
        }

        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setDisplayName("Nether Fortress Tracker");
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(NetherNerf.fortressLocations.get(player.getWorld()));
        compass.setItemMeta(compassMeta);

        player.getInventory().remove(Material.COMPASS);

        if(player.getInventory().firstEmpty() == -1){
            player.getWorld().dropItem(player.getLocation(), compass);
        }
        else{
            player.getInventory().addItem(compass);
        }
    }
}
