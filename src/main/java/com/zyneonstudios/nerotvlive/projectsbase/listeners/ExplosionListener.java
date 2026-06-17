package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        if (e.getBlock().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (!e.getEntity().getType().equals(EntityType.BREEZE_WIND_CHARGE) && e.getEntity().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        if (!e.getEntity().getType().equals(EntityType.BREEZE_WIND_CHARGE) && e.getEntity().getWorld() == Bukkit.getWorlds().getFirst()) {
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWarden(EntitySpawnEvent e) {
        if(e.getEntity().getType().equals(EntityType.WARDEN)) {
            if(e.getLocation().getWorld() == Bukkit.getWorlds().getFirst()) {
                e.setCancelled(true);
            }
        }
    }
}