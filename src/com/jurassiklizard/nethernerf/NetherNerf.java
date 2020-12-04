package com.jurassiklizard.nethernerf;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class NetherNerf extends JavaPlugin {

    private Logger logger = getLogger();

    @Override
    public void onEnable(){
        logger.info(ChatColor.GREEN + "NetherNerf has enabled!");
    }

    @Override
    public void onDisable(){
        logger.info(ChatColor.RED + "NetherNerf has disabled!");
    }

    @Override
    public Logger getLogger() {
        return super.getLogger();
    }
}
