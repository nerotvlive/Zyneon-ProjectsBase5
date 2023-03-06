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

public class InvseeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player p) {
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    Communicator.sendError(p,"/invsee [Spieler*in]");
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        Communicator.sendInfo(p,"Öffne §e"+t.getName()+"§8'§7s §7Inventar§8...");
                        p.openInventory(t.getInventory());
                    } else {
                        Communicator.sendError(p,Strings.playerNotFound);
                    }
                }
            } else {
                Communicator.sendError(p,Strings.noPermission);
            }
        } else {
            Communicator.sendError(s,Strings.needPlayer);
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
