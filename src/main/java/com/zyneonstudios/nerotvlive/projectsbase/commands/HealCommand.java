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

public class HealCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(s instanceof Player p) {
                if(args.length==0) {
                    p.setFoodLevel(20);
                    p.setHealth(p.getHealthScale());
                    p.setFireTicks(0);
                    Communicator.sendInfo(p,"Du hast dich geheilt§8.");
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        t.setFoodLevel(20);
                        t.setHealth(t.getHealthScale());
                        t.setFireTicks(0);
                        Communicator.sendInfo(p,"Du hast den*die Spieler*in §e"+t.getName()+"§7 geheilt§8.");
                    } else {
                        Communicator.sendError(p, Strings.playerNotFound);
                    }
                }
                return false;
            }
            if(args.length==0) {
                Communicator.sendError(s,"/feed [Spieler*in]");
            } else {
                if(Bukkit.getPlayer(args[0])!=null) {
                    Player t = Bukkit.getPlayer(args[0]);
                    t.setFoodLevel(20);
                    t.setHealth(t.getHealthScale());
                    t.setFireTicks(0);
                    Communicator.sendInfo(s,"Du hast den*die Spieler*in §e"+t.getName()+"§7 geheilt§8.");
                } else {
                    Communicator.sendError(s,Strings.playerNotFound);
                }
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
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
