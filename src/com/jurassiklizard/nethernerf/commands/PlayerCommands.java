package com.jurassiklizard.nethernerf.commands;

import com.jurassiklizard.nethernerf.NetherNerf;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args){
        if(cmd.getName().equalsIgnoreCase("nnaddplayer")){
            if(args.length == 0){ return true; }
            Player player = Bukkit.getPlayer(args[0]);

            if(player == null){ return true; }
            if(NetherNerf.speedrunners.contains(player.getDisplayName())){ return true; }

            NetherNerf.speedrunners.add(player.getDisplayName());
            System.out.println("Success: " + player.getUniqueId());
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("nnremoveplayer")){
            if(args.length == 0){ return true; }

            Player player = Bukkit.getPlayer(args[0]);

            if(player == null){ return true; }

            NetherNerf.speedrunners.remove(player.getDisplayName());
            return true;
        }

        return true;
    }
}
