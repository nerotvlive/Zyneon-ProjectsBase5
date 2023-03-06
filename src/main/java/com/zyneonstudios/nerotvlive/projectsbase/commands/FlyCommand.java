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

public class FlyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(s instanceof Player p) {
                if(args.length==0) {
                    p.setAllowFlight(!p.getAllowFlight());
                    Communicator.sendInfo(p,"Du hast deine §aFlugfähigkeit§7 auf §e"+p.getAllowFlight()+"§7 gesetzt§8.");
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        t.setAllowFlight(!t.getAllowFlight());
                        Communicator.sendInfo(p,"Du hast §a"+t.getName()+"§8'§7s §aFlugfähigkeit§7 auf §e"+t.getAllowFlight()+"§7 gesetzt§8.");
                    } else {
                        Communicator.sendError(p,Strings.playerNotFound);
                    }
                }
                return false;
            }
            if(args.length==0) {
                Communicator.sendError(s,"/fly [Spieler*in]");
            } else {
                if(Bukkit.getPlayer(args[0])!=null) {
                    Player t = Bukkit.getPlayer(args[0]);
                    t.setAllowFlight(!t.getAllowFlight());
                    Communicator.sendInfo(s,"Du hast §a"+t.getName()+"§8'§7s §aFlugfähigkeit§7 auf §e"+t.getAllowFlight()+"§7 gesetzt§8.");
                } else {
                    Communicator.sendError(s,Strings.playerNotFound);
                }
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