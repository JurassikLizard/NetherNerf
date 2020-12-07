package com.jurassiklizard.nethernerf.commands;

import com.jurassiklizard.nethernerf.NetherNerf;
import com.jurassiklizard.nethernerf.managers.StructuresManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public class WorldCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("nnaddworld")){
            if(args.length == 0){ return true; }
            String worldName = args[0].toLowerCase();
            String baseWorldName = worldName.split("_")[0];
            World netherWorld = Bukkit.getWorld(baseWorldName + "_nether");
            Location fortressLocation = null;

            if(netherWorld == null){ return true; }

            try {
                fortressLocation = StructuresManager.getStructure(netherWorld, "FORTRESS");
            } catch (ClassNotFoundException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if(fortressLocation != null){
                if(NetherNerf.fortressLocations.containsKey(fortressLocation)){ return true; }
                NetherNerf.fortressLocations.put(netherWorld, fortressLocation);
                System.out.println("Success: " + netherWorld + fortressLocation);
            }
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("nnremoveworld")){
            if(args.length == 0){ return true; }
            String worldName = args[0].toLowerCase();
            String baseWorldName = worldName.split("_")[0];
            World netherWorld = Bukkit.getWorld(baseWorldName + "_nether");

            if(netherWorld == null){ return true; }

            NetherNerf.fortressLocations.remove(netherWorld);
            return true;
        }

        return true;
    }
}
