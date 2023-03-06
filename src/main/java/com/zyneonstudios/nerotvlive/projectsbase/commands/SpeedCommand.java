package com.zyneonstudios.nerotvlive.projectsbase.commands;

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

public class SpeedCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player) {
            Communicator.sendError(s,"/speed [0-9/d/default] §7[Spieler*in]");
        } else {
            Communicator.sendError(s,"/speed [0-9/d/default] [Spieler*in]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Speed")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team.speed")) {
                    if(args.length == 0) {
                        sendSyntax(p);
                    } else if(args.length == 1) {
                        if(isSpeedCompatible(args[0])) {
                            if (p.isFlying()) {
                                String Speed = "0." + args[0];
                                p.setFlySpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(p, "Du hast deine §eFluggeschwindigkeit§7 auf §e" + args[0] + "§7 gesetzt!");
                            } else {
                                String Speed = "0." + args[0];
                                p.setWalkSpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(p, "Du hast deine §eLaufgeschwindigkeit§7 auf §e" + args[0] + "§7 gesetzt!");
                            }
                        } else if(args[0].equalsIgnoreCase("d")||args[0].equalsIgnoreCase("default")) {
                            if (p.isFlying()) {
                                String Speed = "0.1";
                                p.setFlySpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(p, "Du hast deine §eFluggeschwindigkeit§7 auf §eStandard§7 gesetzt!");
                            } else {
                                String Speed = "0.2";
                                p.setWalkSpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(p, "Du hast deine §eLaufgeschwindigkeit§7 auf §eStandard§7 gesetzt!");
                            }
                        } else {
                            Communicator.sendError(s, "§cDas ist keine gültige Zahl!");
                        }
                    } else {
                        if(p.hasPermission("zyneon.team.speed.other")) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                if (isSpeedCompatible(args[0])) {
                                    Player t = Bukkit.getPlayer(args[1]);
                                    if (t.isFlying()) {
                                        String Speed = "0."+args[0];
                                        t.setFlySpeed(Float.parseFloat(Speed));
                                        Communicator.sendInfo(s, "Du hast die §eFluggeschwindigkeit§7 von §a" + t.getName() + "§7auf §e" + args[0] + "§7 gesetzt!");
                                    } else {
                                        String Speed = "0."+args[0];
                                        t.setWalkSpeed(Float.parseFloat(Speed));
                                        Communicator.sendInfo(s, "Du hast die §eLaufgeschwindigkeit§7 von §a" + t.getName() + "§7auf §e" + args[0] + "§7 gesetzt!");
                                    }
                                } else if(args[0].equalsIgnoreCase("d")||args[0].equalsIgnoreCase("default")) {
                                    Player t = Bukkit.getPlayer(args[1]);
                                    if (t.isFlying()) {
                                        String Speed = "0.1";
                                        t.setFlySpeed(Float.parseFloat(Speed));
                                        Communicator.sendInfo(s, "Du hast die §eFluggeschwindigkeit§7 von §a" + t.getName() + "§7auf §eStandard§7 gesetzt!");
                                    } else {
                                        String Speed = "0.2";
                                        t.setWalkSpeed(Float.parseFloat(Speed));
                                        Communicator.sendInfo(s, "Du hast die §eLaufgeschwindigkeit§7 von §a" + t.getName() + "§7auf §eStandard§7 gesetzt!");
                                    }
                                } else {
                                    Communicator.sendError(s, "§cDas ist keine gültige Zahl!");
                                }
                            } else {
                                Communicator.sendError(s, Strings.playerNotFound);
                            }
                        } else {
                            Communicator.sendError(p, Strings.noPermission);
                        }
                    }
                } else {
                    Communicator.sendError(p, Strings.noPermission);
                }
            } else {
                if(args.length == 0) {
                    sendSyntax(s);
                } else if(args.length == 1) {
                    sendSyntax(s);
                } else {
                    if(Bukkit.getPlayer(args[1])!=null) {
                        if(isSpeedCompatible(args[0])) {
                            Player t = Bukkit.getPlayer(args[1]);
                            if(t.isFlying()) {
                                String Speed = "0."+args[0];
                                t.setFlySpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(s,"Du hast die §eFluggeschwindigkeit§7 von §a"+t.getName()+"§7auf §e"+args[0]+"§7 gesetzt!");
                            } else {
                                String Speed = "0."+args[0];
                                t.setWalkSpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(s,"Du hast die §eLaufgeschwindigkeit§7 von §a"+t.getName()+"§7auf §e"+args[0]+"§7 gesetzt!");
                            }
                        } else if(args[0].equalsIgnoreCase("d")||args[0].equalsIgnoreCase("default")) {
                            Player t = Bukkit.getPlayer(args[1]);
                            if (t.isFlying()) {
                                String Speed = "0.1";
                                t.setFlySpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(s, "Du hast die §eFluggeschwindigkeit§7 von §a" + t.getName() + "§7auf §eStandard§7 gesetzt!");
                            } else {
                                String Speed = "0.2";
                                t.setWalkSpeed(Float.parseFloat(Speed));
                                Communicator.sendInfo(s, "Du hast die §eLaufgeschwindigkeit§7 von §a" + t.getName() + "§7auf §eStandard§7 gesetzt!");
                            }
                        } else {
                            Communicator.sendError(s,"§cDas ist keine gültige Zahl!");
                        }
                    } else {
                        Communicator.sendError(s, Strings.playerNotFound);
                    }
                }
            }
        }
        return false;
    }

    private boolean isSpeedCompatible(String Check) {
        if(isNumeric(Check)) {
            int i = Integer.parseInt(Check);
            return i >= 0 && i <= 9;
        } else {
            return false;
        }
    }

    private boolean isNumericPart(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isNumeric(String Check) {
        if(isNumericPart(Check)) {
            return !(Double.parseDouble(Check) > 999999998);
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("0");
            completer.add("1");
            completer.add("2");
            completer.add("3");
            completer.add("4");
            completer.add("5");
            completer.add("6");
            completer.add("7");
            completer.add("8");
            completer.add("9");
            completer.add("d");
            completer.add("default");
        } else if(args.length == 2) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                completer.add(all.getName());
            }
        }
        return completer;
    }
}