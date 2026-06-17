package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    private static String lastReceivedSender = null;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String [] args) {
        String receiver = lastReceivedSender;
        if(s instanceof Player p) {
            User u = Main.getUser(p);
            receiver = u.getLastReceivedSender();
        }
        if(args.length == 0) {
            Communicator.sendError(s,"/reply [Nachricht...]");
        } else {
            if(receiver == null) {
                Communicator.sendError(s,"Dir hat bisher niemand eine MSG geschickt!");
                return false;
            }
            String m = "";
            for (int i = 1; i < args.length; i++) {
                m = m + args[i] + " ";
            }
            Bukkit.dispatchCommand(s,"msg "+receiver+" "+m);
        }
        return false;
    }

    public static String getLastReceivedSender() {
        return lastReceivedSender;
    }

    public static void setLastReceivedSender(String lastReceivedSender) {
        ReplyCommand.lastReceivedSender = lastReceivedSender;
    }
}