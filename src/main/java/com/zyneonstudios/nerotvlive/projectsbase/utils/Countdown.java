package com.zyneonstudios.nerotvlive.projectsbase.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Countdown {

    private int time;
    protected BukkitTask task;
    protected final Plugin plugin;
    private int taskID;

    public Countdown(int time, Plugin plugin) {
        this.time = time;
        this.plugin = plugin;
    }

    public int getTaskID() {
        return taskID;
    }

    public abstract void count(int current);

    public final void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                count(time);
                if (time-- <= 0) cancel();
            }
        }.runTaskTimer(plugin, 0L, 20L);
        this.taskID = task.getTaskId();
    }

    public final void startTicks() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                count(time);
                if (time-- <= 0) cancel();
            }
        }.runTaskTimer(plugin, 0L, 3L);
        this.taskID = task.getTaskId();
    }

    public void stop() {
        task.cancel();
    }
}