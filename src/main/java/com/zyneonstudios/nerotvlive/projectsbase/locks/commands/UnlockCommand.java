package com.zyneonstudios.nerotvlive.projectsbase.locks.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s instanceof Player p) {
            User u = Main.getUser(p);
            u.setInteractMode("unlocking");
            u.sendMessage("Klicke den Block an§8,§7 den du entsichern möchtest§8...");
        } else {
            Communicator.sendError(s,Strings.needPlayer);
        }
        return false;
    }
}
