package com.zyneonstudios.nerotvlive.projectsbase.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static Inventory spawnInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null,InventoryType.HOPPER,"Zum Spawn zurückkehren?");
        inventory.setItem(0,ItemManager.placeholder());
        inventory.setItem(1,ItemManager.placeholder());
        inventory.setItem(2,ItemManager.spawn(player));
        inventory.setItem(3,ItemManager.placeholder());
        inventory.setItem(4,ItemManager.placeholder());
        return inventory;
    }

    public static Inventory bankerInventory_home(Player player) {
        Inventory inventory = Bukkit.createInventory(null,InventoryType.HOPPER,"Bänker");
            inventory.setItem(0, ItemManager.deposit(player));
            inventory.setItem(1, ItemManager.placeholder());
            inventory.setItem(2, ItemManager.salary(player));
            inventory.setItem(3, ItemManager.placeholder());
            inventory.setItem(4, ItemManager.payout(player));
        return inventory;
    }

    public static Inventory bankerInventory_deposit(Player player) {
        Inventory inventory = Bukkit.createInventory(null,InventoryType.HOPPER,"Geld einzahlen");
        inventory.setItem(0, ItemManager.deposit(player));
        inventory.setItem(1, ItemManager.placeholder());
        inventory.setItem(2, ItemManager.salary(player));
        inventory.setItem(3, ItemManager.placeholder());
        inventory.setItem(4, ItemManager.payout(player));
        return inventory;
    }
}