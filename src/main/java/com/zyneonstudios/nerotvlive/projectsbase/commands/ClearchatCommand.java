package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearchatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                if(!all.hasPermission("zyneon.team")) {
                    for (int i = 0; i < 200; i++) {
                        all.sendMessage("§0");
                    }
                } else {
                    Communicator.sendInfo(all,"Der Chat wurde geleert, du kannst ihn aber noch sehen, da du die Berechtigung dazu hast.");
                }
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }
}
