package com.zyneonstudios.nerotvlive.projectsbase.locks.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class LockCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s instanceof Player p) {
            User u = Main.getUser(p);
            if(args.length == 0) {
                u.sendMessage("Um einen Block öffentlich zu sichern§8,§7 mache §e/lock public§8.");
                p.performCommand("lock private");
            } else {
                if(args[0].equalsIgnoreCase("private")) {
                    u.setInteractMode("locking");
                    u.sendMessage("Klicke den Block an§8,§7 den du §eprivat §7sichern möchtest§8...");
                } else if(args[0].equalsIgnoreCase("public")) {
                    u.setInteractMode("locking-public");
                    u.sendMessage("Klicke den Block an§8,§7 den du §eöffentlich §7sichern möchtest§8...");
                } else {
                    u.sendError("/lock [private/public]");
                }
            }
        } else {
            Communicator.sendError(s,Strings.needPlayer);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("private");
            completer.add("public");
        }
        return completer;
    }
}