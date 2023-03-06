package com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File config;
    private YamlConfiguration cfg;

    public Config(String path) {
        this.config = new File(path);
        this.cfg = YamlConfiguration.loadConfiguration(this.config);
        reloadConfig();
    }

    public File getFile() {
        return config;
    }

    public YamlConfiguration getCFG() {
        return cfg;
    }

    public void set(String path, Object content) {
        reloadConfig();
        cfg.set(path,content);
        saveConfig();
        reloadConfig();
    }

    public Object get(String path) {
        reloadConfig();
        if(cfg.contains(path)) {
            return cfg.get(path);
        } else {
            return null;
        }
    }

    public void checkEntry(String path, Object content) {
        reloadConfig();
        if(!(config.exists())) {
            cfg.set(path, content);
            saveConfig();
        } else {
            if(!(cfg.contains(path))) {
                cfg.set(path, content);
                saveConfig();
            }
        }
    }

    public void saveConfig() {
        try {
            cfg.save(config);
        }
        catch (IOException ef) {
            ef.printStackTrace();
        }
        reloadConfig();
    }

    public void reloadConfig() {
        cfg = YamlConfiguration.loadConfiguration(config);
    }

    public void delete() {
        cfg = null;
        config.delete();
        config = null;
        System.gc();
    }

    public void unload() {
        saveConfig();
        reloadConfig();
        config = null;
        cfg = null;
        System.gc();
    }
}