package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class SkinCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (s instanceof Player p) {
            if (args.length == 2) {
                if(args[0].equalsIgnoreCase("delete")) {
                    if(Main.storage.get(p.getUniqueId()+"_"+args[1])!=null) {
                        Main.storage.set(p.getUniqueId() + "_" + args[1], "null");
                        Communicator.sendInfo(p,"Du hast den Skin §e"+args[1]+"§7 erfolgreich gelöscht§8!");
                    } else {
                        Communicator.sendError(p,"Dieser Skin ist nicht gespeichert§8! §7("+args[1]+")");
                    }
                } else if(args[0].equalsIgnoreCase("load")) {
                    if(Main.storage.get(p.getUniqueId()+"_"+args[1])!=null) {
                        GameProfile profile = new GameProfile(p.getUniqueId(),null);
                        profile.getProperties().removeAll("textures");
                        profile.getProperties().put("textures",new Property("ewogICJ0aW1lc3RhbXAiIDogMTY3ODA0Njk2NTE3MCwKICAicHJvZmlsZUlkIiA6ICI2NDQ3NzU3ZjU5ZmU0MjA2YWUzZmRjNjhmZjJiYjZmMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJuZXJvdHZsaXZlIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzliYTVhYTYzZDA5M2Y0YjM3NDYxNWE2YmUzOTQ1MmM3NzIyNmI2MWRiMzIyZjM3ZmY5MzkzNGNiNjA0YzFiOTUiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOiAic2xpbSIKICAgICAgfQogICAgfQogIH0KfQ==","textures"));
                        Bukkit.getOnlinePlayers().forEach(a -> a.hidePlayer(Main.getInstance(),p)); Bukkit.getOnlinePlayers().forEach(a -> a.showPlayer(Main.getInstance(),p));
                    } else {
                        Communicator.sendError(p,"Dieser Skin ist nicht gespeichert§8! §7("+args[1]+")");
                    }
                } else {
                    Communicator.sendError(s, "§c/skin [delete/load/save] [skin url]");
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("save")) {
                    if(args[1].toLowerCase().contains("http://") || args[1].toLowerCase().contains("https://")) {
                        Main.storage.set(p.getUniqueId()+"_"+args[2],args[1]);
                        Communicator.sendInfo(s,"Du hast den Skin §e"+args[2]+" §7 mit der URL §e"+args[1]+"§7 gespeichert§8.");
                        Communicator.sendInfo(p,"Mache &a/skin load "+args[2]+"§7 um ihn zu laden und zu tragen§8.");
                    } else {
                        Communicator.sendError(s, "§c/skin [delete/load/save] [skin url]");
                    }
                } else {
                    Communicator.sendError(s, "§c/skin [delete/load/save] [skin url]");
                }
            } else {
                Communicator.sendError(s, "§c/skin [delete/load/save] [skin url]");
            }
        } else {
            Communicator.sendError(s, Strings.needPlayer);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            completer.add("delete");
            completer.add("load");
            completer.add("save");
        }
        return completer;
    }
}
