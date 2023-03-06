package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.commands.SRLCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Main.getUser(p);
        e.setJoinMessage(null);
        p.setScoreboard(Main.scoreboard);
        if(p.getWorld().getName().contains("FW")) {
            Main.scoreboard.getTeam("farm").addPlayer(p);
        } else {
            Main.scoreboard.getTeam("default").addPlayer(p);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(SRLCommand.countdown!=null) {
            e.setKickMessage("§cDer Server startet zurzeit neu§8,§c versuch es später erneut§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(!Main.whitelist.contains(e.getPlayer().getUniqueId().toString())) {
            e.setKickMessage("§cDu darfst auf diesem Server nicht spielen§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}