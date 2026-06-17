package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.events.ZyneonChatEvent;
import com.zyneonstudios.nerotvlive.projectsbase.objects.Character;
import com.zyneonstudios.nerotvlive.projectsbase.objects.CharacterSkin;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.property.SkinVariant;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Optional;

public class PlayerChatListener implements Listener {

    SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
    SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
    PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

    @EventHandler
    public void onZyneonChat(ZyneonChatEvent e) {
        Player p = e.getPlayer();
        User u = Main.getUser(p);
        if(u.getChatMode().equalsIgnoreCase("payout")) {
            e.setCancelled(true);
            String check = e.getMessage().split(" ", 2)[0];
            if (check.contains("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            Communicator.sendWarning(p,"Schreibe \"cancel\" in den Chat, um die Auszahlung abzubrechen.");
        } else if(u.getChatMode().equalsIgnoreCase("character_name")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords<4) {
                Communicator.sendInfo(p,"Dein Charakter heißt nun§8: §e"+e.getMessage());
                u.getSelectedCharacter().setName(e.getMessage());
                u.setChatMode("normal");
                u.setRoleplay(u.isRoleplay());
                u.initListName();
            } else {
                Communicator.sendError(p,"Du darfst maximal 3 und musst minimal 2 Wörter für einen Namen angeben§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_job")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords<4) {
                if(e.getMessage().getBytes().length > 16) {
                    Communicator.sendError(p,"Dein RP-Job darf maximal 16 Zeichen lang sein§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
                } else {
                    Communicator.sendInfo(p,"Dein Charakter hat nun den Job§8: §e"+e.getMessage());
                    u.getSelectedCharacter().setJob(e.getMessage());
                    u.setChatMode("normal");
                    u.setRoleplay(u.isRoleplay());
                    u.initListName();
                }
            } else {
                Communicator.sendError(p,"Du darfst maximal ein Wort für deinen Job angeben§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_skin")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==1) {
                Communicator.sendInfo(p,"Dein Charakter hat nun den Skin§8: §e"+e.getMessage());
                u.getSelectedCharacter().getSelectedSkin().setSkinUrl(e.getMessage());

                Character character = u.getSelectedCharacter();
                CharacterSkin skin = character.getSelectedSkin();

                try {
                    Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin.getSkinUrl());
                    if(result.isPresent()) {
                        playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
                        skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
                    }
                } catch (Exception ignore) {}

                u.setChatMode("normal");
            } else {
                Communicator.sendError(p,"Das ist keine gültige URL oder Spielername§9! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.getChatMode().equalsIgnoreCase("character_variant")) {
            e.setCancelled(true);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                Communicator.sendInfo(p,"Vorgang abgebrochen§8!");
                u.setChatMode("normal");
                return;
            }
            String[] words = e.getMessage().split("\\s+");
            int numOfWords = words.length;
            if(numOfWords==1) {
                if(e.getMessage().equalsIgnoreCase("SLIM")) {
                    u.getSelectedCharacter().getSelectedSkin().setSkinVariant(SkinVariant.SLIM);
                    u.setChatMode("character_skin");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-URL, oder den Spielernamen, von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else if(e.getMessage().equalsIgnoreCase("CLASSIC")) {
                    u.getSelectedCharacter().getSelectedSkin().setSkinVariant(SkinVariant.CLASSIC);
                    u.setChatMode("character_skin");
                    Communicator.sendInfo(p,"Schreibe die neue Skin-URL, oder den Spielernamen, von deinem Charakter in den Chat§8. §7Schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8.");
                } else {
                    Communicator.sendError(p,"Das ist keine gültige Variante, nutze \"SLIM\" oder \"CLASSIC\"§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
                }
            } else {
                Communicator.sendError(p,"Das ist keine gültige Variante, nutze \"SLIM\" oder \"CLASSIC\"§8! §7Versuche es erneut§8, §7schreibe §e\"cancel\"§7 um den Vorgang abzubrechen§8!");
            }
        } else if(u.isRoleplay()) {
            onRoleplayChat(u,e.getMessage());
            e.setCancelled(true);
        }
    }

    //okay, that goes on me
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(true);
        ZyneonChatEvent event = new ZyneonChatEvent(e.getPlayer(),e.getMessage());
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            Bukkit.broadcastMessage(event.getFormat());
        }
    }

    private void onRoleplayChat(User u, String message) {
        Player p = u.getPlayer();
        message = "§8[§6RP§8] §e"+u.getSelectedCharacter().getJob()+" §f• §e"+u.getSelectedCharacter().getName()+"§8 » §7"+message;
        Communicator.sendRaw(message);
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.getWorld() == p.getWorld() && all.getLocation().distance(p.getLocation()) <= 30) {
                Communicator.sendRaw(all, message);
            }
        }
    }
}