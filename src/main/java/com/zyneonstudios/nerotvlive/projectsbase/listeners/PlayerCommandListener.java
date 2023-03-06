package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class PlayerCommandListener implements Listener {

    private static ArrayList<String> blockedCommands = new ArrayList<>();
    public static void initBlockedCommands(ArrayList<String> list) {
        blockedCommands.add("/gsit");
        blockedCommands.add("/bellyflop");
        blockedCommands.add("/crawl");
        blockedCommands.add("/emote");
        blockedCommands.add("/gbellyflop");
        blockedCommands.add("/gcrawl");
        blockedCommands.add("/gemote");
        blockedCommands.add("/glay");
        blockedCommands.add("/gspin");
        blockedCommands.add("/lay");
        blockedCommands.add("/spin");
        for(String array:list) {
            blockedCommands.add(array);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        for(String check:blockedCommands) {
            if(e.getMessage().contains(check)) {
                e.setCancelled(true);
                e.getPlayer().performCommand("neino");
            }
        }
    }
}