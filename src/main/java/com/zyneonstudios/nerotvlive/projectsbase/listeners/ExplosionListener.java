package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        e.setCancelled(true);
    }
}