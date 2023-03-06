package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);
        Main.getUser(p).destroy();
    }
}