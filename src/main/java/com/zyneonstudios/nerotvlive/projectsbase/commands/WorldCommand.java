package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s instanceof Player p) {
            if(p.hasPermission("zyneon.team")) {
                if(p.getWorld().getName().equalsIgnoreCase("world_argria")) {
                    p.teleport(new Location(Bukkit.getWorlds().get(0),p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ(),p.getLocation().getYaw(),p.getLocation().getPitch()));
                    p.playSound(p.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,100,100);
                } else {
                    p.teleport(new Location(Bukkit.getWorld("world_argria"),p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ(),p.getLocation().getYaw(),p.getLocation().getPitch()));
                    p.playSound(p.getLocation(),Sound.ENTITY_ENDERMAN_TELEPORT,100,100);
                }
            }
        } else {
            Communicator.sendError(s,Strings.needPlayer);
        }
        return false;
    }
}
