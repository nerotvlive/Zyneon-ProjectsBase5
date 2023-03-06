package com.zyneonstudios.nerotvlive.projectsbase.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

public class ItemManager {

    public static ItemStack placeholder() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§0");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack spawn(Player player) {
        ItemStack item;
        if(player.getLevel()>=10) {
            item = new ItemStack(Material.LIME_TERRACOTTA);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Spawn zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §a10 Level");
            lore.add("§7Das kannst du dir leisten§8.");
            lore.add("§8----------------------");
            int level = player.getLevel()-10;
            lore.add("§7Level (jetzt)§8: §e"+player.getLevel());
            lore.add("§7Level (danach)§8: §e"+level);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        } else {
            item = new ItemStack(Material.RED_TERRACOTTA);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§eZum Spawn zurückkehren");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Kosten§8: §c10 Level");
            lore.add("§7Das kannst du dir nicht leisten§8.");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }
        return item;
    }

    public static ItemStack deposit(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATE_SAND_PAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eEinzahlen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack payout(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATE_RED_SAND_PAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eAuszahlen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack salary(Player player) {
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.valueOf("CREATEADDITION_DIAMOND_GRIT_SANDPAPER"));
        } catch (IllegalArgumentException e) {
            itemStack = new ItemStack(Material.valueOf("PAPER"));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§eLohn abholen");
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
