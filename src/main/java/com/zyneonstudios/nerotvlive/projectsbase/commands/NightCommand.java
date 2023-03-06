package com.zyneonstudios.nerotvlive.projectsbase.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NightCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        Bukkit.dispatchCommand(s,"time set night");
        return false;
    }
}
