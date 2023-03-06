package com.zyneonstudios.nerotvlive.projectsbase.utils;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Communicator {

    public static boolean sendDebug = false;

    public static void broadcastRaw(String message) {
        sendRaw(Bukkit.getConsoleSender(),message);
        for(User users:Main.onlineUsers.values()) {
            users.sendRaw(message);
        }
    }

    public static void broadcastMessage(String message) {
        message = message.replace("&&","%and%").replace("&","§").replace("%and%","&");
        sendInfo(message);
        for(User users:Main.onlineUsers.values()) {
            users.sendMessage(message);
        }
    }

    public static void broadcastWarning(String warning) {
        warning = warning.replace("&&","%and%").replace("&","§").replace("%and%","&");
        sendWarning(warning);
        for(User users:Main.onlineUsers.values()) {
            users.sendWarning(warning);
        }
    }

    public static void broadcastError(String error) {
        error = error.replace("&&","%and%").replace("&","§").replace("%and%","&");
        sendError(error);
        for(User users:Main.onlineUsers.values()) {
            users.sendError(error);
        }
    }

    public static void broadcastDebug(String debug) {
        if(!sendDebug) {
            return;
        }
        debug = debug.replace("&&","%and%").replace("&","§").replace("%and%","&");
        sendDebug(debug);
        for(User users:Main.onlineUsers.values()) {
            users.sendDebug(debug);
        }
    }

    public static void sendRaw(String message) {
        Bukkit.getConsoleSender().sendMessage("§f[PB5] §r"+message);
    }

    public static void sendInfo(String info) {
        sendRaw("§f[INFO] §7"+info.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendWarning(String warning) {
        sendRaw("§e[WARNING] §7"+warning.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendError(String error) {
        sendRaw("§c[ERROR] §7"+error.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendDebug(String debug) {
        if(!sendDebug) {
            return;
        }
        sendRaw("§9[DEBUG] §7"+debug.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendRaw(CommandSender receiver, String message) {
        if(receiver instanceof Player p) {
            Main.getUser(p).sendRaw(message);
            return;
        }
        receiver.sendMessage("§f[PB5] §r"+message);
    }

    public static void sendInfo(CommandSender receiver, String info) {
        if(receiver instanceof Player p) {
            Main.getUser(p).sendMessage(info);
            return;
        }
        sendRaw(receiver,"§f[INFO] §7"+info.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendWarning(CommandSender receiver, String warning) {
        if(receiver instanceof Player p) {
            Main.getUser(p).sendWarning(warning);
            return;
        }
        sendRaw(receiver,"§e[WARNING] §7"+warning.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendError(CommandSender receiver, String error) {
        if(receiver instanceof Player p) {
            Main.getUser(p).sendError(error);
            return;
        }
        sendRaw(receiver,"§c[ERROR] §7"+error.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public static void sendDebug(CommandSender receiver, String debug) {
        if(!sendDebug) {
            return;
        }
        if(receiver instanceof Player p) {
            Main.getUser(p).sendDebug(debug);
            return;
        }
        sendRaw(receiver,"§9[DEBUG] §7"+debug.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }
}