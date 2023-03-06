package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PingCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player p) {
            User u = Main.getUser(p);
            if(p.hasPermission("zyneon.team")&&args.length!=0) {
                if(Bukkit.getPlayer(args[0])!=null) {
                    User t = Main.getUser(Bukkit.getPlayer(args[0]));
                    u.sendMessage("Der Ping von §a"+t.getPlayer().getName()+"§7 beträgt§8: §e"+t.getPing()+"ms");
                    return false;
                }
            }
            u.sendMessage("Dein Ping beträgt§8: §e"+u.getPing()+"ms");
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
