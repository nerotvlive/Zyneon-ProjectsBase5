package com.zyneonstudios.nerotvlive.projectsbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(args.length==0) {
            Bukkit.dispatchCommand(s,"minecraft:gamemode");
        } else if(args.length==1) {
            if(args[0].equalsIgnoreCase("0")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode survival");
            } else if(args[0].equalsIgnoreCase("1")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode creative");
            } else if(args[0].equalsIgnoreCase("2")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode adventure");
            } else if(args[0].equalsIgnoreCase("3")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode spectator");
            } else {
                Bukkit.dispatchCommand(s,"minecraft:gamemode "+args[0]);
            }
        } else {
            if(args[0].equalsIgnoreCase("0")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode survival "+args[1]);
            } else if(args[0].equalsIgnoreCase("1")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode creative "+args[1]);
            } else if(args[0].equalsIgnoreCase("2")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode adventure "+args[1]);
            } else if(args[0].equalsIgnoreCase("3")) {
                Bukkit.dispatchCommand(s,"minecraft:gamemode spectator "+args[1]);
            } else {
                Bukkit.dispatchCommand(s,"minecraft:gamemode "+args[0]+" "+args[1]);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            completer.add("0");
            completer.add("1");
            completer.add("2");
            completer.add("3");
            completer.add("survival");
            completer.add("creative");
            completer.add("adventure");
            completer.add("spectator");
        } else if(args.length==2) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                completer.add(all.getName());
            }
        }
        return completer;
    }
}