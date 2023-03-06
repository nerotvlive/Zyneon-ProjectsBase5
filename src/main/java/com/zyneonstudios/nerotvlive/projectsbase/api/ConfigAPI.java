package com.zyneonstudios.nerotvlive.projectsbase.api;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ConfigAPI {

    @Deprecated
    public static void checkEntry(String path, Object content, File file, YamlConfiguration cfg) {
        reloadConfig(file,cfg);
        if(!(file.exists())) {
            cfg.set(path, content);
            saveConfig(file,cfg);
        } else {
            if(!(cfg.contains(path))) {
                cfg.set(path, content);
                saveConfig(file,cfg);
            }
        }
    }

    @Deprecated
    public static void saveConfig(final File file, final YamlConfiguration cfg) {
        try {
            cfg.save(file);
        }
        catch (IOException ef) {
            ef.printStackTrace();
        }
        reloadConfig(file, cfg);
    }

    @Deprecated
    public static void reloadConfig(final File file, YamlConfiguration cfg) {
        cfg = YamlConfiguration.loadConfiguration(file);
    }
}