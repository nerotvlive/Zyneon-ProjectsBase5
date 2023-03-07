package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.mojang.authlib.GameProfile;
import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private UUID uuid;
    private OfflinePlayer offlinePlayer;
    private Player player;
    private String inventoryMode;
    private String interactMode;
    private String chatMode;
    private GameProfile profile;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(Bukkit.getPlayer(uuid)!=null) {
            this.player = Bukkit.getPlayer(uuid);
            assert player != null;
            if(player.hasPermission("zyneon.team")) {
                player.setPlayerListName("§c"+player.getName());
            } else if(player.hasPermission("zyneon.creator")) {
                player.setPlayerListName("§d"+player.getName());
            } else {
                player.setPlayerListName("§f"+player.getName());
            }
            player.setPlayerListHeaderFooter("\n§fZYNEON STUDIOS\n§7Minecraft§8, §7aber §fmehr§8!\n§7Projekt §8● §f"+Strings.prefixWord+ "\n"," \n§9 zyneonstudios.com §r\n§7 powered by §ftube-hosting.com §r\n ");
            Communicator.broadcastRaw("§8[§a+§8] §e"+player.getName());
        }
        if(!Main.economy.hasBalance(uuid)) {
            Main.economy.setBalance(uuid, 0);
        }
        if(!Main.economy.hasSalary(uuid)) {
            Main.economy.setSalary(uuid, 0);
        }
        profile = new GameProfile(uuid, player.getName());
        chatMode = "normal";
        inventoryMode = "normal";
        interactMode = "null";
    }

    public GameProfile getProfile() {
        return profile;
    }

    public Double getBalance() {
        return Main.economy.getBalance(uuid);
    }

    public Double getSalary() {
        return Main.economy.getSalary(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPing() {
        if(player!=null) {
            String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if (!player.getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
                player = Bukkit.getPlayer(player.getUniqueId());
            }
            try {
                assert player != null;
                return player.getPing();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        return -1;
    }

    public String getInventoryMode() {
        return inventoryMode;
    }

    public String getInteractMode() {
        return this.interactMode;
    }

    public String getChatMode() {
        return chatMode;
    }

    private boolean isGroundedCheck() {
        if (getPlayer() != null) {
            Player p = getPlayer();
            if (p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("slab") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("stair") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("farmland") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("path")) {
                return false;
            } else if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.AIR)) {
                if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.VOID_AIR)) {
                    return p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.CAVE_AIR);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean isGrounded() {
        return !isGroundedCheck();
    }

    public void setBalance(Double balance) {
        Main.economy.setBalance(uuid,balance);
    }

    public void addBalance(Double amount) {
        setBalance(getBalance()+amount);
    }

    public void removeBalance(Double amount) {
        setBalance(getBalance()-amount);
    }

    public void setSalary(Double salary) {
        Main.economy.setSalary(uuid,salary);
    }

    public void addSalary(Double amount) {
        setSalary(getSalary()+amount);
    }

    public void removeSalary(Double amount) {
        setSalary(getSalary()-amount);
    }

    public void setInventoryMode(String inventoryMode) {
        this.inventoryMode = inventoryMode;
    }

    public void setInteractMode(String interactMode) {
        this.interactMode = interactMode;
    }

    public void setChatMode(String chatMode) {
        this.chatMode = chatMode;
    }

    public void sendRaw(String message) {
        if(player!=null) {
            player.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRaw(Strings.prefix+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void sendWarning(String warning) {
        sendRaw("§6[WICHTIG] §e"+warning.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
        }
    }

    public void sendError(String error) {
        sendRaw("§4[FEHLER] §c"+error.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public void sendDebug(String debug) {
        if(!Communicator.sendDebug) {
            return;
        }
        sendRaw("§9[DEBUG] §c"+debug.replace("&&","%and%").replace("&","§").replace("%and%","&"));
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
            player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
            player.playSound(player.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void destroy() {
        Main.onlineUsers.remove(uuid);
        this.uuid = null;
        this.offlinePlayer = null;
        this.interactMode = null;
        this.inventoryMode = null;
        this.chatMode = null;
        this.profile = null;
        if(player!=null) {
            Communicator.broadcastRaw("§8[§c-§8]§e "+player.getName());
        }
        this.player = null;
        System.gc();
    }
}