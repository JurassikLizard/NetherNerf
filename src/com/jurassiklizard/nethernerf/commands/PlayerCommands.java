package com.jurassiklizard.nethernerf.commands;

import com.jurassiklizard.nethernerf.NetherNerf;
import com.jurassiklizard.nethernerf.managers.StructuresManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PlayerCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("nnaddplayer")){
            if(args.length == 0){ return true; }
            Player player = Bukkit.getPlayer(args[0]);

            if(player == null){ return true; }
            if(NetherNerf.speedrunners.contains(player.getUniqueId())){ return true; }

            NetherNerf.speedrunners.add(player.getUniqueId());
            System.out.println("Success: " + player.getUniqueId());
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("nnremoveplayer")){
            if(args.length == 0){ return true; }

            Player player = Bukkit.getPlayer(args[0]);

            if(player == null){ return true; }

            NetherNerf.speedrunners.remove(player.getUniqueId());
            return true;
        }

        return true;
    }
}
