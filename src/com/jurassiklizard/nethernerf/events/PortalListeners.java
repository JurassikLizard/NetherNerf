package com.jurassiklizard.nethernerf.events;

import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PortalListeners implements Listener {
    @EventHandler
    public static void onPortalCreate(PortalCreateEvent createEvent){
        CreateReason createReason = createEvent.getReason();

        if(createEvent.getWorld().getEnvironment() == Environment.NETHER && createReason == CreateReason.FIRE){
            createEvent.setCancelled(true);
        }
    }
}
