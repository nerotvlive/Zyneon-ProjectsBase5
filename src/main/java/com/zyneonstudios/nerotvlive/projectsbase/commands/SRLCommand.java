package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Countdown;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class SRLCommand implements CommandExecutor, TabCompleter {

    public static Countdown countdown = null;

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("zyneon.team")) {
            if(args.length == 0) {
                Bukkit.dispatchCommand(s,"srl countdown");
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("instant")||args[0].equalsIgnoreCase("i")) {
                    for(Player all:Bukkit.getOnlinePlayers()) {
                        all.kickPlayer("§cDer Server startet neu§8...");
                    }
                    Bukkit.shutdown();
                } else if(args[0].equalsIgnoreCase("countdown")||args[0].equalsIgnoreCase("c")) {
                    if(countdown==null) {
                        restart(25);
                    } else {
                        Communicator.sendError(s,"§cDer Server stoppt bereits§8...");
                    }
                } else if(args[0].equalsIgnoreCase("reloadSource")||args[0].equalsIgnoreCase("reloadBase")||args[0].equalsIgnoreCase("reload")|args[0].equalsIgnoreCase("r")) {
                    Communicator.sendInfo(s,"Die ProjectsBase-Config wird erneut geladen§8...");
                    Main.checkConfig();
                    Communicator.sendInfo(s,"Die ProjectsBase-Config wurde erneut geladen§8!");
                } else if(args[0].equalsIgnoreCase("stopCountdown")||args[0].equalsIgnoreCase("s")|args[0].equalsIgnoreCase("stop")) {
                    if(countdown==null) {
                        Communicator.sendError(s,"§cEs läuft zurzeit kein Stopp-Countdown§8...");
                    } else {
                        countdown.stop();
                        Communicator.sendInfo(s,"§7Du hast den §eStoppvorgang§7 beendet§8!");
                        countdown = null;
                    }
                }
            }
        } else {
            Communicator.sendError(s,Strings.noPermission);
        }
        return false;
    }

    private void restart(int seconds) {
        countdown = new Countdown(seconds, Main.getInstance()) {
            @Override
            public void count(int current) {
                if(current==25) {
                    Communicator.broadcastWarning("§eDer Server startet in "+current+" Sekunden neu...");
                } else if(current==20) {
                    Communicator.broadcastWarning("§eDer Server startet in "+current+" Sekunden neu...");
                } else if(current==15) {
                    Communicator.broadcastWarning("§eDer Server startet in "+current+" Sekunden neu...");
                } else if(current < 11) {
                    Communicator.broadcastWarning("§eDer Server startet in "+current+" Sekunden neu...");
                    if(current < 6) {
                        for(Player all: Bukkit.getOnlinePlayers()) {
                            all.sendTitle("§e"+current+"§f Sekunden§8...","§8...§7bis zum Serverneustart§8!");
                        }
                        if (current == 0) {
                            for(Player all:Bukkit.getOnlinePlayers()) {
                                all.kickPlayer("§cDer Server startet neu§8...");
                            }
                            Bukkit.shutdown();
                        }
                    }
                }
            }
        };
        countdown.start();
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length==1) {
            completer.add("instant");
            completer.add("countdown");
            completer.add("reloadSource");
            completer.add("reloadBase");
            completer.add("stopCountdown");
        }
        return completer;
    }
}