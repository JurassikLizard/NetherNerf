package com.jurassiklizard.nethernerf.events;

import com.jurassiklizard.nethernerf.NetherNerf;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class PortalBreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockFromTo(BlockFromToEvent fromToEvent) {
        // The to block should never be null, but apparently it is sometimes...
        if (fromToEvent.getBlock() == null || fromToEvent.getToBlock() == null) {
            return;
        }

        // If lava/something else is trying to flow in...
        if (isInNetherPortal(fromToEvent.getToBlock())) {
            fromToEvent.setCancelled(true);
            return;
        }
        // If something is trying to flow out, stop that too.
        if (isInNetherPortal(fromToEvent.getBlock())) {
            fromToEvent.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerBucketEmpty(PlayerBucketEmptyEvent emptyEvent) {
        if (isInNetherPortal(emptyEvent.getBlockClicked())) {
            emptyEvent.setCancelled(true);
            emptyEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't empty that there!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockExplodeEvent(BlockExplodeEvent explodeEvent){
        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityExplodeEvent(EntityExplodeEvent explodeEvent){
        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreakEvent(BlockBreakEvent breakEvent){
        if(isInNetherPortal(breakEvent.getBlock())){
            breakEvent.setCancelled(true);
            breakEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't break that here!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerBlockPlace(BlockPlaceEvent placeEvent) {
        Block block = placeEvent.getBlock();

        if (isTouchingNetherPortal(block)) {
            placeEvent.setCancelled(true);
            placeEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't place that there!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockDispense(BlockDispenseEvent dispenseEvent) {
        Block block = dispenseEvent.getBlock();

        if(isTouchingNetherPortal(block) &&
                (dispenseEvent.getItem().getType() == Material.LAVA_BUCKET ||
                        dispenseEvent.getItem().getType() == Material.LAVA ||
                        dispenseEvent.getItem().getType() == Material.WATER_BUCKET ||
                        dispenseEvent.getItem().getType() == Material.WATER)) {
            dispenseEvent.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerBedSleep(PlayerBedEnterEvent bedEvent){
        if(bedEvent.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_HERE){
            bedEvent.setCancelled(true);
            bedEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't sleep here!");
        }
    }

    private static boolean isInNetherPortal(Block block){
        for(ArrayList<Block> portalBlocks : NetherNerf.portalBlocks){
            if(portalBlocks.contains(block)){
                return true;
            }
        }

        return false;
    }

    private static boolean isTouchingNetherPortal(Block block){
        if(isInNetherPortal(block.getRelative(BlockFace.NORTH))) { return true; }
        if(isInNetherPortal(block.getRelative(BlockFace.WEST))) { return true; }
        if(isInNetherPortal(block.getRelative(BlockFace.SOUTH))) { return true; }
        if(isInNetherPortal(block.getRelative(BlockFace.EAST))) { return true; }
        if(isInNetherPortal(block.getRelative(BlockFace.UP))) { return true; }
        if(isInNetherPortal(block.getRelative(BlockFace.DOWN))) { return true; }

        return false;
    }
}
