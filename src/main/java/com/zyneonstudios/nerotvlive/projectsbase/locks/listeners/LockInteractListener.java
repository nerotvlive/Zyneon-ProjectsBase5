package com.zyneonstudios.nerotvlive.projectsbase.locks.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.LockManager;
import com.zyneonstudios.nerotvlive.projectsbase.locks.managers.TrustManager;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getClickedBlock()!=null) {
            Player p = e.getPlayer();
            User u = Main.getUser(p);
            if(u.getInteractMode().equalsIgnoreCase("locking")) {
                u.setInteractMode("null");
                e.setCancelled(true);
                if (LockManager.lock(e.getClickedBlock(), u.getUUID(), LockManager.locks)) {
                    u.sendMessage("Du hast den Block erfolgreich gesichert§8!");
                } else {
                    u.sendError("§cDieser Block ist bereits gesichert, oder kann nicht gesichert werden§8.");
                }
            } else if(u.getInteractMode().equalsIgnoreCase("locking-public")) {
                u.setInteractMode("null");
                e.setCancelled(true);
                if (LockManager.lock(e.getClickedBlock(), u.getUUID(), LockManager.publs)) {
                    u.sendMessage("Du hast den Block erfolgreich gesichert§8!");
                } else {
                    u.sendError("§cDieser Block ist bereits gesichert, oder kann nicht gesichert werden§8.");
                }
            } else if(u.getInteractMode().equalsIgnoreCase("unlocking")) {
                u.setInteractMode("null");
                Block block = e.getClickedBlock();
                if(LockManager.locks.containsKey(block.getLocation())) {
                    if (LockManager.locks.get(block.getLocation()).toString().equals(e.getPlayer().getUniqueId().toString())) {
                        if (block.getType().toString().contains("_DOOR")) {
                            Location location = block.getLocation();
                            Location lower_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
                            Location upper_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
                            if (location.getWorld().getBlockAt(lower_loc).getType().toString().contains("_DOOR")) {
                                LockManager.unlock(lower_loc.getBlock(),LockManager.locks);
                                LockManager.unlock(location.getBlock(),LockManager.locks);
                                LockManager.unlock(new Location(lower_loc.getWorld(), lower_loc.getBlockX(), lower_loc.getBlockY() - 1, lower_loc.getBlockZ()).getBlock(),LockManager.locks);
                                u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                            } else if (location.getWorld().getBlockAt(upper_loc).getType().toString().contains("_DOOR")) {
                                LockManager.unlock(upper_loc.getBlock(),LockManager.locks);
                                LockManager.unlock(location.getBlock(),LockManager.locks);
                                LockManager.unlock(lower_loc.getBlock(),LockManager.locks);
                                u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                            }
                        } else {
                            LockManager.unlock(block,LockManager.locks);
                            u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                        }
                    } else {
                        e.setCancelled(true);
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§cDieser Block ist gesichert§8."));
                    }
                } else if(LockManager.publs.containsKey(block.getLocation())) {
                    if (LockManager.publs.get(block.getLocation()).toString().equals(e.getPlayer().getUniqueId().toString())) {
                        if (block.getType().toString().contains("_DOOR")) {
                            Location location = block.getLocation();
                            Location lower_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
                            Location upper_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
                            if (location.getWorld().getBlockAt(lower_loc).getType().toString().contains("_DOOR")) {
                                LockManager.unlock(lower_loc.getBlock(),LockManager.publs);
                                LockManager.unlock(location.getBlock(),LockManager.publs);
                                LockManager.unlock(new Location(lower_loc.getWorld(), lower_loc.getBlockX(), lower_loc.getBlockY() - 1, lower_loc.getBlockZ()).getBlock(),LockManager.publs);
                                u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                            } else if (location.getWorld().getBlockAt(upper_loc).getType().toString().contains("_DOOR")) {
                                LockManager.unlock(upper_loc.getBlock(),LockManager.publs);
                                LockManager.unlock(location.getBlock(),LockManager.publs);
                                LockManager.unlock(lower_loc.getBlock(),LockManager.publs);
                                u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                            }
                        } else {
                            LockManager.unlock(block,LockManager.publs);
                            u.sendMessage("Du hast den Block erfolgreich entsichert§8!");
                        }
                    } else {
                        e.setCancelled(true);
                        e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§cDieser Block ist gesichert§8."));
                    }
                }
            } else {
                if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (LockManager.locks.containsKey(e.getClickedBlock().getLocation())) {
                        if (!LockManager.locks.get(e.getClickedBlock().getLocation()).toString().equals(u.getUUID().toString())) {
                            if(TrustManager.isTrusted(p.getUniqueId(),LockManager.locks.get(e.getClickedBlock().getLocation()))) {
                                return;
                            }
                            e.setCancelled(true);
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§cDieser Block ist gesichert§8."));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(LockManager.locks.containsKey(e.getBlock().getLocation())) {
            if (LockManager.locks.get(e.getBlock().getLocation()).toString().equals(e.getPlayer().getUniqueId().toString())) {
                if (e.getBlock().getType().toString().contains("_DOOR")) {
                    Location location = e.getBlock().getLocation();
                    Location lower_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
                    Location upper_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
                    if (location.getWorld().getBlockAt(lower_loc).getType().toString().contains("_DOOR")) {
                        LockManager.unlock(lower_loc.getBlock(),LockManager.locks);
                        LockManager.unlock(location.getBlock(),LockManager.locks);
                        LockManager.unlock(new Location(lower_loc.getWorld(), lower_loc.getBlockX(), lower_loc.getBlockY() - 1, lower_loc.getBlockZ()).getBlock(),LockManager.locks);
                    } else if (location.getWorld().getBlockAt(upper_loc).getType().toString().contains("_DOOR")) {
                        LockManager.unlock(upper_loc.getBlock(),LockManager.locks);
                        LockManager.unlock(location.getBlock(),LockManager.locks);
                        LockManager.unlock(lower_loc.getBlock(),LockManager.locks);
                    }
                } else {
                    LockManager.unlock(e.getBlock(),LockManager.locks);
                }
            } else {
                e.setCancelled(true);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§cDieser Block ist gesichert§8."));
            }
        } else if(LockManager.publs.containsKey(e.getBlock().getLocation())) {
            if (LockManager.publs.get(e.getBlock().getLocation()).toString().equals(e.getPlayer().getUniqueId().toString())) {
                if (e.getBlock().getType().toString().contains("_DOOR")) {
                    Location location = e.getBlock().getLocation();
                    Location lower_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
                    Location upper_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
                    if (location.getWorld().getBlockAt(lower_loc).getType().toString().contains("_DOOR")) {
                        LockManager.unlock(lower_loc.getBlock(),LockManager.publs);
                        LockManager.unlock(location.getBlock(),LockManager.publs);
                        LockManager.unlock(new Location(lower_loc.getWorld(), lower_loc.getBlockX(), lower_loc.getBlockY() - 1, lower_loc.getBlockZ()).getBlock(),LockManager.publs);
                    } else if (location.getWorld().getBlockAt(upper_loc).getType().toString().contains("_DOOR")) {
                        LockManager.unlock(upper_loc.getBlock(),LockManager.publs);
                        LockManager.unlock(location.getBlock(),LockManager.publs);
                        LockManager.unlock(lower_loc.getBlock(),LockManager.publs);
                    }
                } else {
                    LockManager.unlock(e.getBlock(),LockManager.publs);
                }
            } else {
                e.setCancelled(true);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("§cDieser Block ist gesichert§8."));
            }
        }
    }

    @EventHandler
    private void onSandFall(EntityChangeBlockEvent e) {
        if(e.getEntityType() == EntityType.FALLING_BLOCK && e.getTo() == Material.AIR) {
            if(LockManager.locks.containsKey(e.getBlock().getLocation())) {
                e.setCancelled(true);
                e.getBlock().getState().update(false, false);
            } else if(LockManager.publs.containsKey(e.getBlock().getLocation())) {
                e.setCancelled(true);
                e.getBlock().getState().update(false, false);
            }
        }
    }
}