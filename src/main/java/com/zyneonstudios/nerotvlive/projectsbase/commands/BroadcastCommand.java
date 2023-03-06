package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class BroadcastCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length == 0) {
                Communicator.sendError(s,"/broadcast [error/info/raw/warning] [Nachricht...]");
            } else if(args.length == 1) {
                Communicator.sendError(s,"/broadcast [error/info/raw/warning] [Nachricht...]");
            } else {
                String m="";
                for(int i=1;i<args.length;i++) {
                    m=m+args[i]+" ";
                }
                if(args[0].equalsIgnoreCase("error")) {
                    Communicator.broadcastError(m);
                } else if(args[0].equalsIgnoreCase("info")) {
                    Communicator.broadcastMessage(m);
                } else if(args[0].equalsIgnoreCase("raw")) {
                    Communicator.broadcastRaw(m);
                } else if(args[0].equalsIgnoreCase("warning")) {
                    Communicator.broadcastWarning(m);
                } else {
                    Communicator.sendError(s,"/broadcast [error/info/raw/warning] [Nachricht...]");
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
            completer.add("error");
            completer.add("info");
            completer.add("raw");
            completer.add("warning");
        }
        return completer;
    }
}