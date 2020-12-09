package com.jurassiklizard.nethernerf;

import com.jurassiklizard.nethernerf.commands.PlayerCommands;
import com.jurassiklizard.nethernerf.commands.WorldCommands;
import com.jurassiklizard.nethernerf.events.PlayerInteractListener;
import com.jurassiklizard.nethernerf.events.PlayerNetherListener;
import com.jurassiklizard.nethernerf.events.PortalBreakListener;
import com.jurassiklizard.nethernerf.events.PortalCreateListener;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class NetherNerf extends JavaPlugin {

    private Logger logger = getLogger();
    private static NetherNerf plugin;

    public static HashMap<World, Location> fortressLocations;
    public static ArrayList<String> speedrunners;
    public static ArrayList<ArrayList<Block>> portalBlocks;

    @Override
    public void onEnable(){
        logger.info(ChatColor.GREEN + "NetherNerf has enabled!");
        plugin = this;
        fortressLocations = new HashMap<>();
        speedrunners = new ArrayList<>();
        portalBlocks = new ArrayList<>();

        WorldCommands worldCommands = new WorldCommands();
        getCommand("nnaddworld").setExecutor(worldCommands);
        getCommand("nnremoveworld").setExecutor(worldCommands);

        PlayerCommands playerCommands = new PlayerCommands();
        getCommand("nnaddplayer").setExecutor(playerCommands);
        getCommand("nnremoveplayer").setExecutor(playerCommands);

        getServer().getPluginManager().registerEvents(new PortalCreateListener(), this);
        getServer().getPluginManager().registerEvents(new PortalBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerNetherListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable(){
        logger.info(ChatColor.RED + "NetherNerf has disabled!");
    }

    @Override
    public Logger getLogger() {
        return super.getLogger();
    }

    private static NetherNerf getInstance(){
        return plugin;
    }
}
