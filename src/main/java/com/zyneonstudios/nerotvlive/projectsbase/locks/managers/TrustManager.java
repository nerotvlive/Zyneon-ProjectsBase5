package com.zyneonstudios.nerotvlive.projectsbase.locks.managers;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.Storage;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TrustManager {

    public static boolean isTrusted(UUID check, UUID owner) {
        return trustedUsers(owner).contains(check.toString());
    }

    public static void addTrusted(UUID uuid, UUID owner) {
        if(Main.storage.getStorageType().equals(Storage.storageType.YAML)) {
            Config config = new Config("plugins/ProjectsBase/players/" + owner + ".yml");
            config.checkEntry("Player.Lists.TrustedIDs", new ArrayList<>());
            ArrayList<String> uuids = (ArrayList<String>) config.getCFG().getList("Player.Lists.TrustedIDs");
            if (!uuids.contains(uuid)) {
                uuids.add(uuid.toString());
                config.set("Player.Lists.TrustedIDs", uuids);
                config.saveConfig();
            }
        } else {
            ArrayList<String> trusted = trustedUsers(owner);
            if(!trusted.contains(uuid.toString())) {
                trusted.add(uuid.toString());
                Main.storage.set("trustSystem",owner.toString(),trusted.toString().replace("[","").replace("]","").replace(" ",""));
            }
        }
    }

    public static void removeTrusted(UUID uuid, UUID owner) {
        if(Main.storage.getStorageType().equals(Storage.storageType.YAML)) {
            Config config = new Config("plugins/ProjectsBase/players/" + owner + ".yml");
            config.checkEntry("Player.Lists.TrustedIDs", new ArrayList<>());
            ArrayList<String> uuids = (ArrayList<String>) config.getCFG().getList("Player.Lists.TrustedIDs");
            uuids.remove(uuid.toString());
            config.set("Player.Lists.TrustedIDs", uuids);
            config.saveConfig();
        } else {
            ArrayList<String> trusted = trustedUsers(owner);
            if(trusted.contains(uuid.toString())) {
                trusted.remove(uuid.toString());
                Main.storage.set("trustSystem",owner.toString(),trusted.toString().replace("[","").replace("]","").replace(" ",""));
            }
        }
    }

    public static ArrayList<String> trustedUsers(UUID uuid) {
        if(Main.storage.getStorageType().equals(Storage.storageType.YAML)) {
            Config config = new Config("plugins/ProjectsBase/players/"+uuid +".yml");
            config.checkEntry("Player.Lists.TrustedIDs",new ArrayList<>());
            ArrayList<String> list = (ArrayList<String>) config.getCFG().getList("Player.Lists.TrustedIDs");
            return list;
        } else {
            ArrayList<String> list = new ArrayList<>();
            if(Main.storage.get("trustSystem",uuid.toString(),0)!=null) {
                String listString = (String)Main.storage.get("trustSystem",uuid.toString(),0);
                list = new ArrayList<>(Arrays.asList(listString.split(",")));
            }
            return list;
        }
    }
}