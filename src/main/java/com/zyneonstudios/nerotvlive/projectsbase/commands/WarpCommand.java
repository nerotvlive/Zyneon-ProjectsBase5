package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.managers.InventoryManager;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI.getWarpList;

public class WarpCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        if (!(s instanceof Player p)) {
            Communicator.sendError(s, Strings.needPlayer);
        } else {
            Main.getUser(p).sendError("§c/warp [set/remove/enable/disable/toggle/teleport/§7list§c] [Warp]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player p)) {
            Communicator.sendError(s, Strings.needPlayer);
        } else {
            User u = Main.getUser(p);
            if (args.length == 0) {
                p.openInventory(InventoryManager.spawnInventory(p));
                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                return false;
            }
            if (p.hasPermission("zyneon.team")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        WarpAPI.sendWarpList(s);
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    String Warp = args[1];
                    if (args[0].equalsIgnoreCase("list")) {
                        WarpAPI.sendWarpList(s);
                    } else if (args[0].equalsIgnoreCase("set")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            u.sendError("§cDieser Warp existiert bereits!");
                        } else {
                            WarpAPI.setWarp(Warp, p, false);
                            TextComponent component = new TextComponent("Du hast erfolgreich den Warp §e" + Warp + "§7 erstellt! Aktiviere ihn mit ");
                            TextComponent clickable = new TextComponent("/warp enable " + Warp);
                            clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klicke hier um den Warp zu (de)aktivieren").create()));
                            clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp toggle " + Warp));
                            component.addExtra(clickable);
                            component.addExtra("§7.");
                            p.spigot().sendMessage(component);
                            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                        }
                    } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("delete")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            WarpAPI.removeWarp(Warp);
                            u.sendMessage("Du hast erfolgreich den Warp §e" + Warp + "§7 gelöscht!");
                        } else {
                            u.sendError("§cDieser Warp existiert nicht!");
                        }
                    } else if (args[0].equalsIgnoreCase("enable")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            if (WarpAPI.isWarpEnabled(Warp)) {
                                u.sendError("§cDieser Warp ist bereits aktiviert!");
                            } else {
                                WarpAPI.enableWarp(Warp);
                                u.sendMessage("Du hast erfolgreich den Warp §e" + Warp + "§7 aktiviert!");
                            }
                        } else {
                            u.sendError("§cDieser Warp existiert nicht!");
                        }
                    } else if (args[0].equalsIgnoreCase("disable")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            if (WarpAPI.isWarpEnabled(Warp)) {
                                WarpAPI.disableWarp(Warp);
                                u.sendMessage("Du hast erfolgreich den Warp §e" + Warp + "§7 deaktiviert!");
                            } else {
                                u.sendError("§cDieser Warp ist bereits deaktiviert!");
                            }
                        } else {
                            u.sendError("§cDieser Warp existiert nicht!");
                        }
                    } else if (args[0].equalsIgnoreCase("toggle")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            if (WarpAPI.isWarpEnabled(Warp)) {
                                p.performCommand("warp disable " + Warp);
                            } else {
                                p.performCommand("warp enable " + Warp);
                            }
                        } else {
                            u.sendError("§cDieser Warp existiert nicht!");
                        }
                    } else if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
                        if (WarpAPI.ifWarpExists(Warp)) {
                            if (WarpAPI.isWarpEnabled(Warp)) {
                                p.teleport(WarpAPI.getWarp(Warp));
                                u.sendMessage("Du bist nun bei §e" + Warp + "§7!");
                            } else {
                                u.sendError("§cDieser Warp ist nicht aktiviert!");
                            }
                        } else {
                            u.sendError("§cDieser Warp existiert nicht!");
                        }
                    }
                }
            } else {
                u.sendError(Strings.noPermission);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length == 1) {
                completer.add("list");
                completer.add("set");
                completer.add("del");
                completer.add("delete");
                completer.add("rem");
                completer.add("remove");
                completer.add("enable");
                completer.add("disable");
                completer.add("toggle");
                completer.add("tp");
                completer.add("teleport");
            } else if (args.length == 2) {
                if (!args[0].equalsIgnoreCase("list")) {
                    for (int i = 0; i < Objects.requireNonNull(getWarpList()).length; i++) {
                        if (getWarpList()[i].isFile()) {
                            completer.add(getWarpList()[i].getName().replace(".yml", ""));
                        }
                    }
                }
            }
        }
        return completer;
    }
}