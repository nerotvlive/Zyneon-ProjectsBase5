package com.zyneonstudios.nerotvlive.projectsbase.api;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WarpAPI {

    public static boolean ifWarpExists(String Warpname) {
        Warpname = Warpname.toLowerCase();
        File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
        if(WarpFile.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWarpEnabled(String Warpname) {
        Warpname = Warpname.toLowerCase();
        if(ifWarpExists(Warpname)) {
            File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
            YamlConfiguration WF = YamlConfiguration.loadConfiguration(WarpFile);
            if(WF.getBoolean("Warp.isEnabled")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void setWarp(String Warpname, World w, double x, double y, double z, float yaw, float pitch, boolean enable) {
        Warpname = Warpname.toLowerCase();
        File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
        YamlConfiguration WF = YamlConfiguration.loadConfiguration(WarpFile);
        WF.set("Warp.World",w.getName());
        WF.set("Warp.X",x);
        WF.set("Warp.Y",y);
        WF.set("Warp.Z",z);
        WF.set("Warp.Yaw",yaw);
        WF.set("Warp.Pitch",pitch);
        if(enable) {
            WF.set("Warp.isEnabled",true);
        } else {
            WF.set("Warp.isEnabled",false);
        }
        ConfigAPI.saveConfig(WarpFile,WF);
    }

    public static void setWarp(String Warpname, Player p, boolean enable) {
        Warpname = Warpname.toLowerCase();
        setWarp(Warpname,p.getWorld(),p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ(),p.getLocation().getYaw(),p.getLocation().getPitch(),enable);
    }

    public static Location getWarp(String Warpname) {
        Warpname = Warpname.toLowerCase();
        File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
        YamlConfiguration WF = YamlConfiguration.loadConfiguration(WarpFile);
        return new Location(Bukkit.getWorld(WF.getString("Warp.World")),WF.getDouble("Warp.X"),WF.getDouble("Warp.Y"),WF.getDouble("Warp.Z"),(float)WF.getDouble("Warp.Yaw"),(float)WF.getDouble("Warp.Pitch"));
    }

    public static void removeWarp(String Warpname) {
        Warpname = Warpname.toLowerCase();
        if(ifWarpExists(Warpname)) {
            File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
            WarpFile.delete();
        }
    }

    public static void enableWarp(String Warpname) {
        Warpname = Warpname.toLowerCase();
        if(ifWarpExists(Warpname)) {
            File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
            YamlConfiguration WF = YamlConfiguration.loadConfiguration(WarpFile);
            WF.set("Warp.isEnabled",true);
            ConfigAPI.saveConfig(WarpFile,WF);
        }
    }

    public static void disableWarp(String Warpname) {
        Warpname = Warpname.toLowerCase();
        if(ifWarpExists(Warpname)) {
            File WarpFile = new File("plugins/ProjectsBase/Warps/"+Warpname+".yml");
            YamlConfiguration WF = YamlConfiguration.loadConfiguration(WarpFile);
            WF.set("Warp.isEnabled",false);
            ConfigAPI.saveConfig(WarpFile,WF);
        }
    }

    public static File[] getWarpList() {
        File folder = new File("plugins/ProjectsBase/Warps/");
        return folder.listFiles();
    }

    @Deprecated
    public static void sendOldWarpList(CommandSender s) {
        StringBuilder list = new StringBuilder("§aWarps§8: §f");
        for (int i = 0; i < Objects.requireNonNull(getWarpList()).length; i++) {
            if (getWarpList()[i].isFile()) {
                list.append(getWarpList()[i].getName().replace(".yml", "§8, §f"));
            }
        }
        s.sendMessage(list.toString());
        if(s instanceof Player p) {
            p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public static void sendWarpList(CommandSender s) {
        TextComponent list = new TextComponent("§aWarps§8: §f");
        for (int i = 0; i < Objects.requireNonNull(getWarpList()).length; i++) {
            if (getWarpList()[i].isFile()) {
                String warp = getWarpList()[i].getName().replace(".yml", "§8, §f");
                TextComponent component = new TextComponent(warp);
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klicke zum Teleportieren").create()));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/warp teleport "+warp.replace("§8, §f","")));
                list.addExtra(component);
            }
        }
        if(s instanceof Player p) {
            p.spigot().sendMessage(list);
            p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        } else {
            s.spigot().sendMessage(list);
        }
    }
}