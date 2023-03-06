package com.zyneonstudios.nerotvlive.projectsbase.workers;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class Banker {

    public static boolean deposit(UUID uuid, Integer amount) {
        Main.economy.set(uuid,Main.economy.getBalance(uuid)+amount,Main.economy.getSalary(uuid));
        if(Bukkit.getPlayer(uuid)!=null) {
            Main.getUser(uuid).setBalance(Main.economy.getBalance(uuid));
        }
        return false;
    }

    public static boolean payout(Player player, Integer amount) {
        User u = Main.getUser(player);
        UUID uuid = u.getUUID();
        if(Main.economy.checkPayment(uuid,amount)) {
            double finalAmount = amount;
            int m500; int m200; int m100; int m50;
            int m20; int m10; int m5; int m2; int m1;
            m500 = amount/500; amount = amount - m500*500;
            m200 = amount/200; amount = amount - m200*200;
            m100 = amount/100; amount = amount - m100*100;
            m50 = amount/50; amount = amount - m50*50;
            m20 = amount/20; amount = amount - m20*20;
            m10 = amount/10; amount = amount - m10*10;
            m5 = amount/5; amount = amount - m5*5;
            m2 = amount/2; amount = amount - m2*2;
            m1 = amount;
            ItemStack M500 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_500")); M500.setAmount(m500);
            ItemStack M200 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_200")); M200.setAmount(m200);
            ItemStack M100 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_100")); M100.setAmount(m100);
            ItemStack M50 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_50")); M50.setAmount(m50);
            ItemStack M20 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_20")); M20.setAmount(m20);
            ItemStack M10 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_10")); M10.setAmount(m10);
            ItemStack M5 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_5")); M5.setAmount(m5);
            ItemStack M2 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_2")); M2.setAmount(m2);
            ItemStack M1 = new ItemStack(Material.valueOf("ZYNEONSOURCE_MARK_1")); M1.setAmount(m1);
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M500);
            } else {
                player.getInventory().addItem(M500);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M200);
            } else {
                player.getInventory().addItem(M200);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M100);
            } else {
                player.getInventory().addItem(M100);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M50);
            } else {
                player.getInventory().addItem(M50);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M20);
            } else {
                player.getInventory().addItem(M20);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M10);
            } else {
                player.getInventory().addItem(M10);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M5);
            } else {
                player.getInventory().addItem(M5);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M2);
            } else {
                player.getInventory().addItem(M2);
            }
            if(player.getInventory().firstEmpty()==-1) {
                player.getWorld().dropItem(player.getLocation(),M1);
            } else {
                player.getInventory().addItem(M1);
            }
            u.removeBalance(finalAmount);
            return true;
        }
        return false;
    }
}