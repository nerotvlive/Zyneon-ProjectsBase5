package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class TellCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(args.length < 2) {
            Communicator.sendError(s,"/msg [Spieler*in] [Nachricht...]");
        } else {
            if(Bukkit.getPlayer(args[0])!=null) {
                User t = Main.getUser(Bukkit.getPlayer(args[0]));
                String m = "";
                for (int i = 1; i < args.length; i++) {
                    m = m + args[i] + " ";
                }
                Communicator.sendRaw(s,"§8[§7MSG§8] §6Du §f-> §e"+t.getPlayer().getName()+"§8: §7"+m);
                String name;
                if(s instanceof Player p) {
                    name = p.getName();
                    p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                } else {
                    name = "§cServer";
                }
                t.sendRaw("§8[§7MSG§8] §6"+name+" §f-> §eDir§8: §7"+m);
                t.getPlayer().playSound(t.getPlayer().getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
            } else if(args[0].equalsIgnoreCase("server")||args[0].equalsIgnoreCase("console")||args[0].equalsIgnoreCase("konsole")) {
                String m = "";
                for (int i = 1; i < args.length; i++) {
                    m = m + args[i] + " ";
                }
                Communicator.sendRaw(s,"§8[§7MSG§8] §6Du §f-> §cServer§8: §7"+m);
                String name;
                if(s instanceof Player p) {
                    name = p.getName();
                    p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                } else {
                    name = "§cServer";
                }
                Communicator.sendRaw("§8[§7MSG§8] §6"+name+" §f-> §eDir§8: §7"+m);
            } else {
                Communicator.sendError(s,Strings.playerNotFound);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                completer.add(all.getName());
            }
        } else if(args.length==2) {
            completer.add("...");
        }
        return completer;
    }
}
