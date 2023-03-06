package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.managers.InventoryManager;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        Communicator.sendRaw(s,"§8================================");
        Communicator.sendRaw(s,"§0");
        Communicator.sendRaw(s,"§7Plugin§8: §fZyneon ProjectsBase");
        Communicator.sendRaw(s,"§7Author§8: §cnerotvlive");
        Communicator.sendRaw(s,"§7Version§8: §b"+Main.version);
        Communicator.sendRaw(s,"§0");
        Communicator.sendRaw(s,"§8================================");
        if(s instanceof Player p) {
            p.openInventory(InventoryManager.bankerInventory_home(p));
        }
        return false;
    }
}
