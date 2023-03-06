package com.zyneonstudios.nerotvlive.projectsbase.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ZyneonChatEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private String name;
    private String rank;
    private String message;
    private final Player player;

    public ZyneonChatEvent(Player player, String message) {
        this.player = player;
        this.message = message;
        if(player.hasPermission("zyneon.team")) {
            rank = "§8[§cTeam§8] §f";
        } else if(player.hasPermission("zyneon.creator")) {
            rank = "§8[§dCreator§8] §f";
        }/* else if(player.hasPermission("zyneon.premium")) {
            rank = "§8[§6Premium§8] §f";
        }*/ else {
            rank = "§8[§7Member§8] §f";
        }
        name = rank+player.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public String getFormat() {
        if(player.hasPermission("zyneon.premium")) {
            message = message.replace("&&","%and%").replace("&","§");
        }
        return name+"§8 » §7"+message.replace("&&","%and%").replace("%and%","&");
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}