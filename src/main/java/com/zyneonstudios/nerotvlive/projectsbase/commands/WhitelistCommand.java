package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WhitelistCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length > 1) {
                if(args[0].equalsIgnoreCase("add")) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                    if(Main.whitelist.contains(p.getUniqueId().toString())) {
                        Communicator.sendError(s,"Diese*r Spieler*in ist bereits gewhitelisted§8!");
                    } else {
                        Main.whitelist.add(p.getUniqueId().toString());
                        Main.config.set("Settings.Lists.Whitelist",Main.whitelist);
                        Main.config.saveConfig();
                        Main.config.reloadConfig();
                        Communicator.sendInfo(s,"Du hast §e"+p.getName()+"§7 erfolgreich gewhitelisted§8.");
                    }
                } else if(args[0].equalsIgnoreCase("remove")) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                    if(Main.whitelist.contains(p.getUniqueId().toString())) {
                        Main.whitelist.remove(p.getUniqueId().toString());
                        Main.config.set("Settings.Lists.Whitelist",Main.whitelist);
                        Main.config.saveConfig();
                        Main.config.reloadConfig();
                        Communicator.sendInfo(s,"Du hast §e"+p.getName()+"§7 erfolgreich von der Whitelist entfernt§8.");
                    } else {
                        Communicator.sendError(s,"Diese*r Spieler*in ist nicht gewhitelisted§8!");
                    }
                } else {
                    Communicator.sendError(s,"/whitelist [add/list/remove] [Spieler*In]");
                }
            } else {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("list")) {
                        Communicator.sendInfo("§fWhitelist§8: §7"+Main.whitelist.toString());
                        if(s instanceof Player p) {
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG,100,100);
                        }
                        return false;
                    }
                }
                Communicator.sendError(s,"/whitelist [add/list/remove] [Spieler*In]");
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
            completer.add("add");
            completer.add("list");
            completer.add("remove");
        } else if(args.length==2) {
            if(args[0].equalsIgnoreCase("remove")) {
                for(Player all:Bukkit.getOnlinePlayers()) {
                    completer.add(all.getName());
                }
            }
        }
        return completer;
    }
}
