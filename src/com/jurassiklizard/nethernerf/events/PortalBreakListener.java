package com.jurassiklizard.nethernerf.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.Iterator;

public class PortalBreakListener implements Listener {


    @EventHandler
    public void blockPhysics(BlockPhysicsEvent physicsEvent) {
        if (physicsEvent.isCancelled()) {
            return;
        }
        Block block = physicsEvent.getBlock();

        if (physicsEvent.getChangedType() == Material.NETHER_PORTAL || block.getType() == Material.NETHER_PORTAL) {
            if (isInNetherPortal(block)) {
                physicsEvent.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockFromTo(BlockFromToEvent fromToEvent) {
        // Always check if the event has been canceled by someone else.
        if(fromToEvent.isCancelled()) {
            return;
        }

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

//    @EventHandler(priority = EventPriority.LOW)
//    public void playerBucketFill(PlayerBucketFillEvent fillEvent) {
//        if (fillEvent.isCancelled()) {
//            return;
//        }
//
//        if (isInNetherPortal(fillEvent.getBlockClicked())) {
//            fillEvent.setCancelled(true);
//        }
//    }

    @EventHandler(priority = EventPriority.LOW)
    public void playerBucketEmpty(PlayerBucketEmptyEvent emptyEvent) {
        if (emptyEvent.isCancelled()) {
            return;
        }

        if (isInNetherPortal(emptyEvent.getBlockClicked())) {
            emptyEvent.setCancelled(true);
            emptyEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't empty that there!");
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockExplodeEvent(BlockExplodeEvent explodeEvent){
        if(explodeEvent.isCancelled()){
            return;
        }

        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void entityExplodeEvent(EntityExplodeEvent explodeEvent){
        if(explodeEvent.isCancelled()){
            return;
        }

        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void blockBreakEvent(BlockBreakEvent breakEvent){
        if(breakEvent.isCancelled()){
            return;
        }

        if(isInNetherPortal(breakEvent.getBlock())){
            breakEvent.setCancelled(true);
            breakEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't break that here!");
        }
    }

    private static boolean isInNetherPortal(Block block){
        if(block.getWorld().getEnvironment() != World.Environment.NETHER){
            return false;
        }

        if(block.getType() == Material.NETHER_PORTAL){
            if(block.getRelative(BlockFace.UP).getType() == Material.OBSIDIAN){ return true; }
            if(block.getRelative(BlockFace.EAST).getType() == Material.OBSIDIAN){ return true; }
            if(block.getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN){ return true; }
            if(block.getRelative(BlockFace.WEST).getType() == Material.OBSIDIAN){ return true; }
        }
        else if(block.getType() == Material.OBSIDIAN){
            if(block.getRelative(BlockFace.UP).getType() == Material.NETHER_PORTAL){ return true; }
            if(block.getRelative(BlockFace.EAST).getType() == Material.NETHER_PORTAL){ return true; }
            if(block.getRelative(BlockFace.DOWN).getType() == Material.NETHER_PORTAL){ return true; }
            if(block.getRelative(BlockFace.WEST).getType() == Material.NETHER_PORTAL){ return true; }
        }

        return false;
    }
}
