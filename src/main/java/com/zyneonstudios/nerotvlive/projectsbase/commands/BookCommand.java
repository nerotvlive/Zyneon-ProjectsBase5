package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BookCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (s instanceof Player p) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName() + " minecraft:writable_book");
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }
}