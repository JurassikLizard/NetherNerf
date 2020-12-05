package com.jurassiklizard.nethernerf.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.inventory.ItemStack;

public class PortalCreateListener implements Listener {
    @EventHandler
    public static void onPortalCreate(PortalCreateEvent createEvent){
        CreateReason createReason = createEvent.getReason();

        if(createEvent.getWorld().getEnvironment() == Environment.NETHER && createReason == CreateReason.FIRE){
            createEvent.setCancelled(true);
            if(createEvent.getEntity() instanceof Player){
                createEvent.getEntity().sendMessage(ChatColor.RED + "Hey! You can't light that portal!");
            }
        }
    }
}
