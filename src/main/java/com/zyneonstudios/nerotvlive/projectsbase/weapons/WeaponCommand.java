package com.zyneonstudios.nerotvlive.projectsbase.weapons;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        Communicator.sendError(s, "§c/weapon §7<weapon> §7<amount>");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (s.hasPermission("zyneon.team")) {
            if (args.length > 0) {
                int amount = 1;
                if (args.length > 1) {
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sendSyntax(s);
                        return false;
                    }
                }
                String id = args[0].toLowerCase();
                if(id.equalsIgnoreCase("crystalboost")) {
                    try {
                        WeaponListener.crystalBoost = Integer.parseInt(args[1]);
                        Communicator.sendInfo(s,"§7Der §dCrystal Boost§7 wurde auf §e" + args[1] + "§7 gesetzt§8.");
                    } catch (Exception e) {
                        Communicator.sendError(s, "§cDie §4"+args[0]+"§c Zahl ist nicht valide§8.");
                    }
                    return true;
                }
                if (WeaponItems.getWeapons().containsKey(id)) {
                    if (s instanceof Player p) {
                        ItemStack item = WeaponItems.getWeapons().get(id).clone();
                        item.setAmount(amount);
                        p.getInventory().addItem(item);
                    } else {
                        Communicator.sendError(s, Strings.needPlayer);
                    }
                }
            } else {
                if (s instanceof Player p) {
                    p.openInventory(WeaponItems.getWeaponsMenu());
                } else {
                    Communicator.sendError(s, Strings.needPlayer);
                }
            }
        } else {
            Communicator.sendError(s, Strings.noPermission);
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender.hasPermission("zyneon.team")) {
            if (args.length == 1) {
                return WeaponItems.getWeapons().keySet().stream().toList();
            }
        }
        return List.of();
    }
}