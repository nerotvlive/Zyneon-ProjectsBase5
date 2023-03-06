package com.zyneonstudios.nerotvlive.projectsbase.locks.managers;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.Storage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LockManager {

    public static HashMap<Location,UUID> locks = new HashMap<>();
    public static HashMap<Location,UUID> publs = new HashMap<>();
    public static ArrayList<String> allowedBlocks = new ArrayList<>();

    public static boolean lock(Block block, UUID owner, HashMap<Location,UUID> locked) {
        if (!publs.containsKey(block.getLocation())) {
            if (!locks.containsKey(block.getLocation())) {
                if (block.getType().toString().contains("_CHEST") || block.getType().toString().equals("CHEST")) {
                    return lockChest(block, owner, locked);
                } else if (block.getType().toString().contains("_DOOR")) {
                    return lockDoor(block, owner, locked);
                } else {
                    return lockBlock(block, owner, locked);
                }
            }
        }
        return false;
    }

    public static void unlock(Block block, HashMap<Location,UUID> locked) {
        for (int i = 0; i < 3; i++) {
            locked.remove(block.getLocation());
        }
        saveLocks();
    }

    public static boolean lockDoor(Block door, UUID owner, HashMap<Location,UUID> locked) {
        Location location = door.getLocation();
        if(!locked.containsKey(location)) {
            Location lower_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());
            Location upper_loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
            if (location.getWorld().getBlockAt(lower_loc).getType().toString().contains("_DOOR")) {
                locked.put(lower_loc,owner);
                locked.put(location,owner);
                locked.put(new Location(lower_loc.getWorld(),lower_loc.getBlockX(),lower_loc.getBlockY()-1,lower_loc.getBlockZ()),owner);
                saveLocks();
                return true;
            } else if (location.getWorld().getBlockAt(upper_loc).getType().toString().contains("_DOOR")) {
                locked.put(upper_loc,owner);
                locked.put(location,owner);
                locked.put(lower_loc,owner);
                saveLocks();
                return true;
            }
        }
        return false;
    }

    public static boolean lockChest(Block chest, UUID owner, HashMap<Location,UUID> locked) {
        Location location = chest.getLocation();
        if(!locked.containsKey(location)) {
            String blockData = chest.getBlockData().getAsString();
            String facing = "facing=null";
            if (blockData.contains("facing=west")) {
                facing = "facing=west";
            } else if (blockData.contains("facing=south")) {
                facing = "facing=south";
            } else if (blockData.contains("facing=east")) {
                facing = "facing=east";
            } else if (blockData.contains("facing=north")) {
                facing = "facing=north";
            }
            String side = "type=null";
            if (blockData.contains("type=left")) {
                side = "type=right";
            } else if (blockData.contains("type=right")) {
                side = "type=left";
            }
            ArrayList<Location> possibleOtherSides = new ArrayList<>();
            possibleOtherSides.add(new Location(location.getWorld(), location.getBlockX() - 1, location.getBlockY(), location.getBlockZ()));
            possibleOtherSides.add(new Location(location.getWorld(), location.getBlockX() + 1, location.getBlockY(), location.getBlockZ()));
            possibleOtherSides.add(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() - 1));
            possibleOtherSides.add(new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() + 1));
            for (Location sides : possibleOtherSides) {
                if (sides.getBlock().getType().equals(chest.getType())) {
                    if (sides.getBlock().getBlockData().getAsString().contains(facing)) {
                        if (sides.getBlock().getBlockData().getAsString().contains(side)) {
                            locked.put(sides,owner);
                        }
                    }
                }
            }
            locked.put(location,owner);
            saveLocks();
            return true;
        }
        return false;
    }

    public static boolean lockBlock(Block block, UUID owner, HashMap<Location,UUID> locked) {
        if(block.getType().toString().contains("_CABINET")||block.getType().toString().contains("NOTICE_BOARD")||block.getType().toString().contains("LECTERN")||block.getType().toString().contains("CRAFTING_TABLE")||block.getType().toString().contains("BARREL")||block.getType().toString().contains("_GATE")||block.getType().toString().contains("_TRAPDOOR")||block.getType().toString().contains("FURNACE")||block.getType().toString().contains("SMOKER")||block.getType().toString().contains("_GATE")||block.getType().toString().contains("SHULKER_BOX")||block.getType().toString().contains("_TANK")||allowedBlocks.contains(block.getType().toString())) {
            if (!locked.containsKey(block.getLocation())) {
                locked.put(block.getLocation(), owner);
                saveLocks();
                return true;
            }
        }
        return false;
    }

    public static void saveLocksSQL() {
        for (Map.Entry<Location, UUID> entry : locks.entrySet()) {
            Main.storage.set("locksPrivate",encryptLocation(entry.getKey()),entry.getValue().toString());
        }
        for (Map.Entry<Location, UUID> entry : publs.entrySet()) {
            Main.storage.set("locksPublic",encryptLocation(entry.getKey()),entry.getValue().toString());
        }
    }

    public static void getLocksSQL() {
        Main.storage.setupTable("locksPrivate", 1);
        Main.storage.setupTable("locksPublic", 1);
        Main.storage.setupTable("trustSystem", 1);

        locks = new HashMap<>();
        try {
            ResultSet rs = Main.storage.getConnection().prepareStatement("SELECT path, content0 FROM locksPrivate").executeQuery();
            while (rs.next()) {
                locks.put(decryptLocation(rs.getString("path")), UUID.fromString(rs.getString("content0")));
            }
        } catch (SQLException ignore) {}


        publs = new HashMap<>();
        try {
            ResultSet rs = Main.storage.getConnection().prepareStatement("SELECT path, content0 FROM locksPublic").executeQuery();
            while (rs.next()) {
                publs.put(decryptLocation(rs.getString("path")), UUID.fromString(rs.getString("content0")));
            }
        } catch (SQLException ignore) {}
    }

    public static void getLocks() {
        allowedBlocks.add("FARMERSDELIGHT_BASKET");
        allowedBlocks.add("SUPPLEMENTARIES_SAFE");

        if(Main.storage.getStorageType().equals(Storage.storageType.YAML)) {
            locks = new HashMap<>();
            File f = new File("plugins/ProjectsBase/locks.yml");
            if (!f.exists()) return;
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String l;
                while ((l = br.readLine()) != null) {
                    String[] t = l.split(":", 2);
                    if (t.length != 2) continue;
                    locks.put(decryptLocation(t[0]), UUID.fromString(t[1]));
                }
                br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            publs = new HashMap<>();
            File f2 = new File("plugins/ProjectsBase/publs.yml");
            if (!f2.exists()) return;
            try {
                BufferedReader br = new BufferedReader(new FileReader(f2));
                String l;
                while ((l = br.readLine()) != null) {
                    String[] t = l.split(":", 2);
                    if (t.length != 2) continue;
                    publs.put(decryptLocation(t[0]), UUID.fromString(t[1]));
                }
                br.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else {
            getLocksSQL();
        }
    }

    public static void saveLocks() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
            if (Main.storage.getStorageType().equals(Storage.storageType.YAML)) {
                File f = new File("plugins/ProjectsBase/locks.yml");
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.flush();
                    fos.close();
                    PrintWriter pw = new PrintWriter(f);
                    for (Map.Entry<Location, UUID> entry : locks.entrySet())
                        pw.println(encryptLocation(entry.getKey()) + ":" + entry.getValue());
                    pw.flush();
                    pw.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                File f2 = new File("plugins/ProjectsBase/publs.yml");
                try {
                    FileOutputStream fos = new FileOutputStream(f2);
                    fos.flush();
                    fos.close();
                    PrintWriter pw = new PrintWriter(f2);
                    for (Map.Entry<Location, UUID> entry : publs.entrySet())
                        pw.println(encryptLocation(entry.getKey()) + ":" + entry.getValue());
                    pw.flush();
                    pw.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                saveLocksSQL();
            }
        });
    }

    public static String encryptLocation(Location location) {
        return "LOCWOR.."+location.getWorld().getName()+".LOCX.."+location.getBlockX()+".LOCY.."+location.getBlockY()+".LOCZ.."+location.getBlockZ()+".LOCCLOSE";
    }

    public static Location decryptLocation(String location) {
        String world = StringUtils.substringBetween(location,"LOCWOR..",".LOCX..");

        if(Bukkit.getWorld(world)!=null) {
            return new Location(Bukkit.getWorld(world),Double.parseDouble(StringUtils.substringBetween(location,".LOCX..",".LOCY..")),Double.parseDouble(StringUtils.substringBetween(location,".LOCY..",".LOCZ..")),Double.parseDouble(StringUtils.substringBetween(location,".LOCZ..",".LOCCLOSE")));
        } else {
            return null;
        }
    }
}