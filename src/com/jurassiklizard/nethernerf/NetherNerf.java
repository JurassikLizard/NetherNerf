package com.jurassiklizard.nethernerf;

import com.jurassiklizard.nethernerf.events.PortalListeners;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class NetherNerf extends JavaPlugin {

    private Logger logger = getLogger();

    @Override
    public void onEnable(){
        logger.info(ChatColor.GREEN + "NetherNerf has enabled!");
        getServer().getPluginManager().registerEvents(new PortalListeners(), this);
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
