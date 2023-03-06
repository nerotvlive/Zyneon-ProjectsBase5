package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PowerUserCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length == 0) {
                Communicator.sendError(s,"/pu [Spieler*in]");
                Communicator.sendWarning(s,"Bedenke dies nur zu tun, wenn du jemanden zum Operator ernennen willst. Operator können ALLES auf diesem Server tun.");
            } else {
                Communicator.sendInfo(s,"Befehl wird ausgeführt...");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:op "+args[0]);
            }
        } else {
            Communicator.sendError(s,Strings.noPermission);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            for(Player all: Bukkit.getOnlinePlayers()) {
                completer.add(all.getName());
            }
        }
        return completer;
    }
}
