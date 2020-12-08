package com.jurassiklizard.nethernerf.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class PortalBreakListener implements Listener {


    @EventHandler
    public void blockPhysics(BlockPhysicsEvent physicsEvent) {
        Block block = physicsEvent.getBlock();

        if (physicsEvent.getChangedType() == Material.NETHER_PORTAL || block.getType() == Material.NETHER_PORTAL) {
            if (isInNetherPortal(block)) {
                physicsEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
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

    @EventHandler
    public void playerBucketEmpty(PlayerBucketEmptyEvent emptyEvent) {
        if (isInNetherPortal(emptyEvent.getBlockClicked())) {
            emptyEvent.setCancelled(true);
            emptyEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't empty that there!");
        }
    }

    @EventHandler
    public void blockExplodeEvent(BlockExplodeEvent explodeEvent){
        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent explodeEvent){
        if(explodeEvent.isCancelled()){
            return;
        }

        Iterator<Block> iter = explodeEvent.blockList().iterator();
        while(iter.hasNext())
            if(isInNetherPortal(iter.next()))
                iter.remove();
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent breakEvent){
        if(breakEvent.isCancelled()){
            return;
        }

        if(isInNetherPortal(breakEvent.getBlock())){
            breakEvent.setCancelled(true);
            breakEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't break that here!");
        }
    }

    @EventHandler
    public void playerBlockPlace(BlockPlaceEvent placeEvent) {
        if (placeEvent.isCancelled()) {
            return;
        }

        Block block = placeEvent.getBlock();

        if (isTouchingNetherPortal(block)) {
            placeEvent.setCancelled(true);
            placeEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't place that there!");
        }
    }

    @EventHandler
    public void blockDispense(BlockDispenseEvent dispenseEvent) {
        if (dispenseEvent.isCancelled()) {
            return;
        }

        Block block = dispenseEvent.getBlock();

        if(isTouchingNetherPortal(block) &&
                (dispenseEvent.getItem().getType() == Material.LAVA_BUCKET ||
                        dispenseEvent.getItem().getType() == Material.LAVA ||
                        dispenseEvent.getItem().getType() == Material.WATER_BUCKET ||
                        dispenseEvent.getItem().getType() == Material.WATER)) {
            dispenseEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void playerBedSleep(PlayerBedEnterEvent bedEvent){
        if(bedEvent.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_HERE){
            bedEvent.setCancelled(true);
            bedEvent.getPlayer().sendMessage(ChatColor.RED + "Hey! You can't sleep here!");
        }
    }

//    @EventHandler
//    public void onInteract(PlayerInteractEvent interactEvent){
//        Player player = interactEvent.getPlayer();
//        if(interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK){
//            if(interactEvent.getClickedBlock().getType() == Material.Bed){
//                Block block = interactEvent.getClickedBlock();
//                if(block.getLocation().getWorld() == Bukkit.getWorld("nether")) {
//                    interactEvent.setCancelled(true);
//                }
//            }
//        }
//    }


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
