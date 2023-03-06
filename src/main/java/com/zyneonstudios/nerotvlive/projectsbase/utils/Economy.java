package com.zyneonstudios.nerotvlive.projectsbase.utils;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import java.util.UUID;

public class Economy {

    public Economy() {
        Main.storage.setupTable("economyBalance",1);
        Main.storage.setupTable("economySalary",1);
    }

    public boolean checkPayment(UUID uuid, double price) {
        if (Main.economy.hasBalance(uuid)) {
            double balance = Main.economy.getBalance(uuid);
            return balance - price >= 0;
        } else {
            return false;
        }
    }

    public boolean hasBalance(UUID uuid) {
        return Main.storage.getDouble("economyBalance",uuid.toString(),0)!=-1;
    }

    public boolean hasSalary(UUID uuid) {
        return Main.storage.getDouble("economySalary",uuid.toString(),0)!=-1;
    }

    public void set(UUID uuid, double balance, double salary) {
        Main.storage.setDouble("economyBalance",uuid.toString(),balance);
        Main.storage.setDouble("economySalary",uuid.toString(),salary);
    }

    public void setBalance(UUID uuid, double balance) {
        Main.storage.setDouble("economyBalance",uuid.toString(),balance);
    }

    public void setSalary(UUID uuid, double salary) {
        Main.storage.setDouble("economySalary",uuid.toString(),salary);
    }

    public Double getBalance(UUID uuid) {
        double data;
        if(hasBalance(uuid)) {
            data = Main.storage.getDouble("economyBalance",uuid.toString(),0);
        } else {
            data = 0.0;
        }
        return data;
    }

    public Double getSalary(UUID uuid) {
        double data;
        if(hasBalance(uuid)) {
            data = Main.storage.getDouble("economySalary",uuid.toString(),0);
        } else {
            data = 0.0;
        }
        return data;
    }
}