package com.jurassiklizard.nethernerf.events;

import com.jurassiklizard.nethernerf.NetherNerf;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

import java.util.ArrayList;

public class PortalCreateListener implements Listener {
    @EventHandler
    public static void onPortalCreate(PortalCreateEvent createEvent){
        if(createEvent.getWorld().getEnvironment() != Environment.NETHER){ return; }

        CreateReason createReason = createEvent.getReason();
        if(createReason == CreateReason.FIRE){
            createEvent.setCancelled(true);
            if(createEvent.getEntity() instanceof Player){
                createEvent.getEntity().sendMessage(ChatColor.RED + "Hey! You can't light that portal!");
            }
            return;
        }

        ArrayList<Block> portalBlocks = new ArrayList<>();

        for(BlockState blockState : createEvent.getBlocks()){
            portalBlocks.add(blockState.getBlock());
        }
        NetherNerf.portalBlocks.add(portalBlocks);
    }
}
