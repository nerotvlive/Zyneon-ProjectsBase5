package com.zyneonstudios.nerotvlive.projectsbase.commands;

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

public class MoneyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            if(p.hasPermission("zyneon.team")) {
                if (args.length == 3) {
                    User u = Main.getUser(p);
                    if (args[0].equalsIgnoreCase("add")) {
                        try {
                            if(args[1].equalsIgnoreCase("salary")) {
                                u.addSalary(Double.parseDouble(args[2]));
                            } else {
                                u.addBalance(Double.parseDouble(args[2]));
                            }
                        } catch (NumberFormatException e) {
                            Communicator.sendError(p,"§cDu hast keine gültige Zahl angegeben.");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        try {
                            if(args[1].equalsIgnoreCase("salary")) {
                                u.removeSalary(Double.parseDouble(args[2]));
                            } else {
                                u.removeBalance(Double.parseDouble(args[2]));
                            }
                        } catch (NumberFormatException e) {
                            Communicator.sendError(p,"§cDu hast keine gültige Zahl angegeben.");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        try {
                            if(args[1].equalsIgnoreCase("salary")) {
                                u.setSalary(Double.parseDouble(args[2]));
                            } else {
                                u.setBalance(Double.parseDouble(args[2]));
                            }
                        } catch (NumberFormatException e) {
                            Communicator.sendError(p,"§cDu hast keine gültige Zahl angegeben.");
                            return false;
                        }
                    }
                }
                Communicator.sendInfo(p,"Dein Geld beträgt§8: §e"+ Main.economy.getBalance(p.getUniqueId()));
                Communicator.sendInfo(p,"Dein Lohn beträgt§8: §e"+ Main.economy.getSalary(p.getUniqueId()));
            } else {
                Communicator.sendError(p,Strings.noPermission);
            }
        } else {
            Communicator.sendError(sender,Strings.needPlayer);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            completer.add("set");
            completer.add("add");
            completer.add("remove");
        } else if(args.length==2) {
            completer.add("money");
            completer.add("salary");
        }
        return completer;
    }
}
