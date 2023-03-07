package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.managers.ItemManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        User u = Main.getUser(p);
        if(e.getCurrentItem()!=null) {
            if(e.getCurrentItem().getItemMeta()!=null) {
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.placeholder().getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                } else if(u.getInventoryMode().equalsIgnoreCase("deposit")) {
                    if(e.getCurrentItem().getType().toString().toLowerCase().contains("zyneonsource_mark_")) {
                        e.setCancelled(true);
                        int amount = Integer.parseInt(e.getCurrentItem().getType().toString().replace("ZYNEONSOURCE_MARK_", ""));
                        amount = amount * e.getCurrentItem().getAmount();
                        e.getCurrentItem().setAmount(0);
                        u.addBalance(amount+0.0);
                        u.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§aDu hast "+amount+"§mM§r§a eingezahlt."));
                        p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                    } else {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                    }
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.deposit(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    p.closeInventory();
                    u.setInventoryMode("deposit");
                    u.sendMessage("Klicke das Geld an§8,§7 welches zu einzahlen willst§8.");
                    p.openInventory(p.getInventory());
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.payout(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    u.setChatMode("payout");
                    u.sendWarning("Schreibe \"cancel\" in den Chat, um die Auszahlung abzubrechen.");
                    u.sendMessage("Schreibe den Betrag§8,§7 den du dir auszahlen lassen willst in den Chat§8.");
                    p.closeInventory();
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.salary(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ItemManager.spawn(p).getItemMeta().getDisplayName())) {
                    e.setCancelled(true);
                    if(e.getCurrentItem().getType().toString().toLowerCase().contains("red")) {
                        Communicator.sendError(p,"§cDazu hast du nicht genügend Level§8!");
                        p.closeInventory();
                    } else {
                        if(WarpAPI.isWarpEnabled("spawn")) {
                            if(u.isGrounded()) {
                                p.setLevel(p.getLevel() - 10);
                                p.closeInventory();
                                p.teleport(WarpAPI.getWarp("spawn"));
                                p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100, 100);
                                p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                            } else {
                                u.sendError("§cDazu musst du auf §4sicherem Boden§c stehen§8!");
                            }
                        } else {
                            Communicator.sendError(p,"§cDieser Warp ist zurzeit nicht aktiviert oder nicht vorhanden§8.");
                            p.closeInventory();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        User u = Main.getUser(e.getPlayer().getUniqueId());
        u.setInventoryMode("normal");
    }
}