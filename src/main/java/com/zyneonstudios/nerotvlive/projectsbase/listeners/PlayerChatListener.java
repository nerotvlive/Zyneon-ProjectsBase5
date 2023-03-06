package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.events.ZyneonChatEvent;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.workers.Banker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onZyneonChat(ZyneonChatEvent e) {
        Player p = e.getPlayer();
        User u = Main.getUser(p);
        if(u.getChatMode().equalsIgnoreCase("payout")) {
            e.setCancelled(true);
            String check = e.getMessage().split(" ", 2)[0];
            if (check.contains("cancel")) {
                u.setChatMode("normal");
            }
            try {
                int amount = Integer.parseInt(check);
                if(Banker.payout(p,amount)) {
                    u.sendMessage("Du hast dir §e"+amount+"§mM§7 auszahlen lassen§8.");
                    u.setChatMode("normal");
                    return;
                } else {
                    u.sendError("Dazu hast du nicht genug Geld auf dem Konto!");
                }
            } catch (NumberFormatException ex) {
                u.sendError("Du hast keinen gültigen Betrag eingegeben, bitte versuche es erneut.");
            }
            u.sendWarning("Schreibe \"cancel\" in den Chat, um die Auszahlung abzubrechen.");
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        ZyneonChatEvent event = new ZyneonChatEvent(e.getPlayer(),e.getMessage());
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            Bukkit.broadcastMessage(event.getFormat());
        }
    }
}