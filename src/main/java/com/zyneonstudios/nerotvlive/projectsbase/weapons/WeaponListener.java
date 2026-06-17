package com.zyneonstudios.nerotvlive.projectsbase.weapons;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeaponListener implements Listener {

    private final NamespacedKey ammoKey;

    public WeaponListener() {
        ammoKey = new NamespacedKey(Main.getInstance(), "ammo");
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onWeaponShot(EntityShootBowEvent event) {
        Entity entity = event.getEntity();

        ItemStack weapon = event.getBow();
        if (weapon != null && weapon.getType() == Material.CROSSBOW) {
            var meta = weapon.getItemMeta();

            if (!meta.hasItemModel()) {
                entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.crossbow_shoot", 1f, 1f);
            } else if (meta.hasItemModel()) {
                String weaponModel = Objects.requireNonNull(meta.getItemModel()).toString();
                double damage = 1.0;
                double velocity = 1.0;

                switch (weaponModel) {
                    case String s when s.startsWith("zyneon:revolver"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.revolver_shoot", 2f, 1f);
                        damage = 1.5;
                        break;
                    case String s when s.startsWith("zyneon:shotgun"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.shotgun_shoot", 2f, 1f);
                        damage = 1.5;
                        break;
                    case String s when s.startsWith("zyneon:marksman_pistol"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.marksman_pistol_shoot", 2f, 1f);
                        damage = 2.5;
                        break;
                    case String s when s.startsWith("zyneon:mauser_c96"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.mauser_c96_shoot", 2f, 1f);
                        AutomaticReload(entity, weapon, 8);
                        damage = 0.7;
                        break;
                    case String s when s.startsWith("zyneon:lewis_gun"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.lewis_gun_shoot", 2f, 1f);
                        AutomaticReload(entity, weapon, 48);
                        damage = 0.5;
                        break;
                    case String s when s.startsWith("zyneon:luger"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.luger_shoot", 2f, 1f);
                        AutomaticReload(entity, weapon, 8);
                        damage = 0.7;
                        break;
                    case String s when s.startsWith("zyneon:rifle"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.rifle_shoot", 2f, 1f);
                        velocity = 1.9;
                        damage = 2.0;
                        break;
                    case String s when s.startsWith("zyneon:crystal_gun"):
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.crystal_gun_shoot", 2f, 1f);
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("§4Entladen§8! §cBenutze die Waffe um zu laden§8.");
                        meta.setLore(lore);
                        weapon.setItemMeta(meta);
                        Damageable damageable = (Damageable)weapon.getItemMeta();
                        damageable.setDamage(damageable.getMaxDamage()-2);
                        weapon.setItemMeta(damageable);
                        break;
                    default:
                        entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.crossbow_shoot", 1f, 1f);
                        break;
                }

                Entity projectile = event.getProjectile();
                if (projectile instanceof AbstractArrow arrow) {
                    NamespacedKey weaponKey = new NamespacedKey(Main.getInstance(), "weapon_model");
                    arrow.getPersistentDataContainer().set(weaponKey, PersistentDataType.STRING, weaponModel);
                    arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    arrow.setSilent(true);
                    arrow.setVelocity(arrow.getVelocity().multiply(velocity));
                    arrow.setDamage(damage);

                    for (Player all : org.bukkit.Bukkit.getOnlinePlayers()) {
                        all.hideEntity(Main.getInstance(), arrow);
                    }

                    new BukkitRunnable() {
                        Location lastPos = arrow.getLocation();

                        @Override
                        public void run() {
                            if(weaponModel.startsWith("zyneon:crystal_gun")) {
                                if (arrow.isOnGround() || arrow.isDead() || !arrow.isValid()) {
                                    energyCharge(arrow,2);
                                    this.cancel();
                                    return;
                                }
                            } else {
                                if (arrow.isOnGround()) {
                                    arrow.remove();
                                    this.cancel();
                                    return;
                                }
                                if (arrow.isDead() || !arrow.isValid()) {
                                    this.cancel();
                                    return;
                                }
                            }

                            Location currentPos = arrow.getLocation();
                            double distance = lastPos.distance(currentPos);
                            Vector direction = currentPos.toVector().subtract(lastPos.toVector()).normalize();

                            for (double d = 0; d < distance; d += 0.5) {
                                Location loc = lastPos.clone().add(direction.clone().multiply(d));
                                if(weaponModel.startsWith("zyneon:crystal_gun")) {
                                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(191, 50, 252), 1.5F);
                                    arrow.getWorld().spawnParticle(Particle.DUST, loc, 1, 0.0, 0.0, 0.0, 0.0, dustOptions);
                                } else {
                                    arrow.getWorld().spawnParticle(Particle.SMOKE, loc, 1, 0, 0, 0, 0.01);
                                }
                            }

                            lastPos = currentPos.clone();
                        }
                    }.runTaskTimer(Main.getInstance(), 0, 1);
                }
            }
        }
    }

    private void energyCharge(Entity arrow,int charge_numbers) {
        for(int i = 0; i < charge_numbers; i++) {
            BreezeWindCharge charge = (BreezeWindCharge) arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.BREEZE_WIND_CHARGE);
            arrow.remove();
            charge.setInvisible(true);
            charge.explode();
        }
    }

    private static final ArrayList<ItemStack> reloadingCrystalGuns = new ArrayList<>();
    @SuppressWarnings("deprecation")
    public static void reloadCrystalGun(ItemStack crystalGun, Player holder) {
        if(!reloadingCrystalGuns.contains(crystalGun)) {
            reloadingCrystalGuns.add(crystalGun);
            Damageable damageable = (Damageable) crystalGun.getItemMeta();
            holder.getWorld().playSound(holder.getLocation(), "zyneon:crossbow.crystal_gun_loading_start", 2f, 0.8f);
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if(damageable == null) return;
                        if (damageable.hasDamage()) {
                            damageable.setDamage(damageable.getDamage() - 1);
                            crystalGun.setItemMeta(damageable);
                            holder.playSound(holder.getLocation(), "zyneon:crossbow.crystal_gun_loading_middle", 0.04F, 0.01F);
                        } else {
                            damageable.setDamage(0);
                            damageable.setLore(null);
                            crystalGun.setItemMeta(damageable);
                            CrossbowMeta gunMeta = (CrossbowMeta) crystalGun.getItemMeta();
                            gunMeta.setChargedProjectiles(null);
                            gunMeta.addChargedProjectile(WeaponItems.getCrystalGunEnergy());
                            crystalGun.setItemMeta(gunMeta);
                            reloadingCrystalGuns.remove(crystalGun);
                            this.cancel();
                            holder.getWorld().playSound(holder.getLocation(), "zyneon:crossbow.crystal_gun_loading_end", 0.3f, 0.01f);
                        }
                    } catch (Exception e) {
                        Communicator.sendError("§4Fehler: §cKristallwaffe konnte nicht weiter aufgeladen werden§8: §7"+e.getMessage());
                        this.cancel();
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0,1);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
        NamespacedKey weaponKey = new NamespacedKey(Main.getInstance(), "weapon_model");
        if (!arrow.getPersistentDataContainer().has(weaponKey, PersistentDataType.STRING)) return;
        String weaponModel = arrow.getPersistentDataContainer().get(weaponKey, PersistentDataType.STRING);
        if (event.getHitEntity() instanceof LivingEntity target) {
            target.setNoDamageTicks(0);
            target.damage(4);
            if (weaponModel != null && weaponModel.startsWith("zyneon:crystal_gun")) {
                energyCharge(arrow,2);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWeaponReloadingStart(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.hasItem()) {
            ItemStack weapon = event.getItem();
            if (weapon != null && weapon.getType() == Material.CROSSBOW) {
                CrossbowMeta meta = (CrossbowMeta)weapon.getItemMeta();
                Player player = event.getPlayer();

                if(meta.hasItemModel() && Objects.requireNonNull(meta.getItemModel()).toString().startsWith("zyneon:crystal_gun")) {
                    event.setCancelled(!meta.hasChargedProjectiles());
                    Damageable damageable = (Damageable)meta;
                    if (damageable.hasDamage()) {
                        if(!reloadingCrystalGuns.contains(weapon)) {
                            reloadCrystalGun(weapon, player);
                        }
                    }
                    return;
                }

                if (meta.hasChargedProjectiles() || !player.getInventory().contains(Material.ARROW) && player.getGameMode() != GameMode.CREATIVE) {
                    return;
                }

                if (!meta.hasItemModel()) {
                    player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_start", 1f, 1f);
                    playWeaponReloadingMiddle(player, "zyneon:crossbow.crossbow_loading_middle");
                } else if (meta.hasItemModel()) {
                    String weaponModel = Objects.requireNonNull(meta.getItemModel()).toString();

                    switch (weaponModel) {
                        case String s when s.startsWith("zyneon:revolver"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.revolver_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.revolver_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:shotgun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.shotgun_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.shotgun_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:marksman_pistol"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.marksman_pistol_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.marksman_pistol_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:mauser_c96"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.mauser_c96_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.mauser_c96_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:lewis_gun"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.lewis_gun_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.lewis_gun_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:luger"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.luger_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.luger_loading_middle");
                            break;
                        case String s when s.startsWith("zyneon:rifle"):
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.rifle_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.rifle_loading_middle");
                            break;
                        default:
                            player.getWorld().playSound(player.getLocation(), "zyneon:crossbow.crossbow_loading_start", 1f, 1f);
                            playWeaponReloadingMiddle(player, "zyneon:crossbow.crossbow_loading_middle");
                            break;
                    }
                }
            }
        }
    }

    private void playWeaponReloadingMiddle(Player player, String sound) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            if (player.isHandRaised()) {
                player.getWorld().playSound(player.getLocation(), sound, 1f, 1f);
            }
        }, 10L);
    }

    @EventHandler
    public void onWeaponReloadingEnd(EntityLoadCrossbowEvent event) {
        Entity entity = event.getEntity();

        CrossbowMeta meta = (CrossbowMeta)event.getCrossbow().getItemMeta();
        if (!meta.hasItemModel()) {
            entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.crossbow_loading_end", 1f, 1f);
        } else if (meta.hasItemModel()) {
            String weaponModel = Objects.requireNonNull(meta.getItemModel()).toString();

            switch (weaponModel) {
                case String s when s.startsWith("zyneon:revolver"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.revolver_loading_end", 1f, 1f);
                    break;
                case String s when s.startsWith("zyneon:shotgun"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.shotgun_loading_end", 1f, 1f);
                    break;
                case String s when s.startsWith("zyneon:marksman_pistol"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.marksman_pistol_loading_end", 1f, 1f);
                    break;
                case String s when s.startsWith("zyneon:mauser_c96"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.mauser_c96_loading_end", 1f, 1f);
                    meta.lore(List.of(Component.text("§7Ammo: §e8/8")));
                    event.getCrossbow().setItemMeta(meta);
                    break;
                case String s when s.startsWith("zyneon:lewis_gun"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.lewis_gun_loading_end", 1f, 1f);
                    meta.lore(List.of(Component.text("§7Ammo: §e48/48")));
                    event.getCrossbow().setItemMeta(meta);
                    break;
                case String s when s.startsWith("zyneon:luger"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.luger_loading_end", 1f, 1f);
                    meta.lore(List.of(Component.text("§7Ammo: §e8/8")));
                    event.getCrossbow().setItemMeta(meta);
                    break;
                case String s when s.startsWith("zyneon:rifle"):
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.rifle_loading_end", 1f, 1f);
                    break;
                default:
                    entity.getWorld().playSound(entity.getLocation(), "zyneon:crossbow.crossbow_loading_end", 1f, 1f);
                    break;
            }
        }
    }

    private void AutomaticReload(Entity entity, ItemStack weapon, int magSize) {
        ItemMeta meta = weapon.getItemMeta();

        if (!meta.getPersistentDataContainer().has(ammoKey, PersistentDataType.INTEGER)) {
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
        }

        int currentAmmo = meta.getPersistentDataContainer().getOrDefault(ammoKey, PersistentDataType.INTEGER, 0);
        if (currentAmmo > 1) {
            if (entity instanceof Player player) {
                if (!player.getInventory().contains(Material.ARROW) && player.getGameMode() != GameMode.CREATIVE) {
                    meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
                    meta.lore(List.of(Component.text("§7Ammo: §e0/" + magSize)));
                    weapon.setItemMeta(meta);
                    return;
                }

                if (player.getGameMode() != GameMode.CREATIVE) {
                    player.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
                }
            }

            int newAmmo = currentAmmo - 1;
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, newAmmo);
            meta.lore(List.of(Component.text("§7Ammo: §e" + newAmmo + "/" + magSize)));

            weapon.setItemMeta(meta);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (meta instanceof CrossbowMeta cbMeta) {
                        cbMeta.setChargedProjectiles(null);
                        cbMeta.addChargedProjectile(new ItemStack(Material.ARROW));
                        weapon.setItemMeta(cbMeta);
                    }
                }
            }.runTaskLater(Main.getInstance(), 1L);
        } else {
            meta.getPersistentDataContainer().set(ammoKey, PersistentDataType.INTEGER, magSize);
            meta.lore(List.of(Component.text("§7Ammo: §e0/" + magSize)));
            weapon.setItemMeta(meta);
        }
    }
}