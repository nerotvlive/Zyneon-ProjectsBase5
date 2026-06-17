package com.zyneonstudios.nerotvlive.projectsbase.custom;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.weapons.WeaponItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.EquippableComponent;

import java.util.HashMap;

@SuppressWarnings("all")
public class CustomItems {
    private static final HashMap<String, ItemStack> customItems = new HashMap<>();

    public static Inventory getCustomItemsMenu() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Custom Items");
        for (String key : customItems.keySet()) {
            inventory.addItem(customItems.get(key).clone());
        }
        return inventory;
    }

    public static void init() {
        customItems.clear();
        customItems.put("cloak_boots", getCloakBoots());
        customItems.put("cloak_chestplate", getCloakChestplate());
        customItems.put("cloak_helmet", getCloakHelmet());
        customItems.put("cloak_leggings", getCloakLeggings());
        customItems.put("fishbasket", getFishbasket());
        customItems.put("ice", getIce());

        customItems.put("mark_1", getMark(Variant.ONE));
        customItems.put("mark_2", getMark(Variant.TWO));
        customItems.put("mark_5", getMark(Variant.FIVE));
        customItems.put("mark_10", getMark(Variant.TEN));
        customItems.put("mark_20", getMark(Variant.TWENTY));
        customItems.put("mark_50", getMark(Variant.FIFTY));
        customItems.put("mark_100", getMark(Variant.ONEHUNDRET));
        customItems.put("mark_200", getMark(Variant.TWOHUNDRET));

        customItems.put("toolbox", getToolbox());
        customItems.put("wrench", getWrench());
        customItems.put("sack", getSack());
        if(Main.config.getCFG().getBoolean("Settings.modules.weapons")) {
            WeaponItems.init();
            customItems.putAll(WeaponItems.getWeapons());
        }
    }

    public static ItemStack getMark(Variant variant) {
        ItemStack mark;
        if (variant.numValue < 5) {
            mark = new ItemStack(Material.COPPER_NUGGET);
        } else {
            mark = new ItemStack(Material.PAPER);
        }
        ItemMeta meta = mark.getItemMeta();
        meta.setDisplayName("§r§f" + variant.numValue + " Mark");
        meta.setItemModel(new NamespacedKey("zyneon", "mark" + variant.numValue));
        mark.setItemMeta(meta);
        return mark;
    }

    public static ItemStack getIce() {
        ItemStack ice = new ItemStack(Material.COOKIE);
        ItemMeta meta = ice.getItemMeta();
        meta.setDisplayName("§r§fEis");
        meta.setItemModel(new NamespacedKey("zyneon", "ice"));
        ice.setItemMeta(meta);
        return ice;
    }

    public static ItemStack getFishbasket() {
        ItemStack fishbasket = new ItemStack(Material.COOKED_BEEF);
        ItemMeta meta = fishbasket.getItemMeta();
        meta.setDisplayName("§r§fFischbrötchen");
        meta.setItemModel(new NamespacedKey("zyneon", "fishbasket"));
        fishbasket.setItemMeta(meta);
        return fishbasket;
    }

    public static ItemStack getWrench() {
        ItemStack wrench = new ItemStack(Material.DEBUG_STICK);
        ItemMeta meta = wrench.getItemMeta();
        meta.setDisplayName("§r§fSchraubenschlüssel");
        meta.setEnchantmentGlintOverride(false);
        wrench.setItemMeta(meta);
        return wrench;
    }

    public static ItemStack getToolbox() {
        ItemStack toolbox = new ItemStack(Material.STICK);
        ItemMeta meta = toolbox.getItemMeta();
        meta.setDisplayName("§r§fWerkzeugkasten");
        meta.setItemModel(new NamespacedKey("zyneon", "toolbox"));
        toolbox.setItemMeta(meta);
        return toolbox;
    }

    public static ItemStack getSack() {
        ItemStack sack = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta meta = sack.getItemMeta();
        meta.setDisplayName("§r§fSack");
        meta.setItemModel(new NamespacedKey("zyneon", "sack"));
        EquippableComponent equip = meta.getEquippable();
        equip.setSlot(EquipmentSlot.HEAD);
        equip.setModel(new NamespacedKey("zyneon", "sack"));
        meta.setEquippable(equip);
        sack.setItemMeta(meta);
        return sack;
    }

    public static ItemStack getCloakHelmet() {
        ItemStack cloakHelmet = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta meta = cloakHelmet.getItemMeta();
        meta.setDisplayName("§r§fMantel Helm");
        meta.setItemModel(new NamespacedKey("zyneon", "cloak_helmet"));
        EquippableComponent equip = meta.getEquippable();
        equip.setSlot(EquipmentSlot.HEAD);
        equip.setModel(new NamespacedKey("zyneon", "cloak"));
        meta.setEquippable(equip);
        cloakHelmet.setItemMeta(meta);
        return cloakHelmet;
    }

    public static ItemStack getCloakChestplate() {
        ItemStack cloakChestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta meta = cloakChestplate.getItemMeta();
        meta.setDisplayName("§r§fMantel Brustplatte");
        meta.setItemModel(new NamespacedKey("zyneon", "cloak_chestplate"));
        EquippableComponent equip = meta.getEquippable();
        equip.setSlot(EquipmentSlot.CHEST);
        equip.setModel(new NamespacedKey("zyneon", "cloak"));
        meta.setEquippable(equip);
        cloakChestplate.setItemMeta(meta);
        return cloakChestplate;
    }

    public static ItemStack getCloakLeggings() {
        ItemStack cloakLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemMeta meta = cloakLeggings.getItemMeta();
        meta.setDisplayName("§r§fMantel Hose");
        meta.setItemModel(new NamespacedKey("zyneon", "cloak_leggings"));
        EquippableComponent equip = meta.getEquippable();
        equip.setSlot(EquipmentSlot.LEGS);
        equip.setModel(new NamespacedKey("zyneon", "cloak"));
        meta.setEquippable(equip);
        cloakLeggings.setItemMeta(meta);
        return cloakLeggings;
    }

    public static ItemStack getCloakBoots() {
        ItemStack cloakBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta meta = cloakBoots.getItemMeta();
        meta.setDisplayName("§r§fMantel Schuhe");
        meta.setItemModel(new NamespacedKey("zyneon", "cloak_boots"));
        EquippableComponent equip = meta.getEquippable();
        equip.setSlot(EquipmentSlot.FEET);
        equip.setModel(new NamespacedKey("zyneon", "cloak"));
        meta.setEquippable(equip);
        cloakBoots.setItemMeta(meta);
        return cloakBoots;
    }

    public enum Variant {
        ONE(1),
        TWO(2),
        FIVE(5),
        TEN(10),
        TWENTY(20),
        FIFTY(50),
        ONEHUNDRET(100),
        TWOHUNDRET(200);

        private int numValue;

        Variant(int numValue) {
            this.numValue = numValue;
        }

        public int getNumValue() {
            return numValue;
        }
    }

    public static HashMap<String, ItemStack> getCustomItems() {
        return customItems;
    }
}
