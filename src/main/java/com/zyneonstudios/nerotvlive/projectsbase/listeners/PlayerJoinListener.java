package com.zyneonstudios.nerotvlive.projectsbase.listeners;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.warp.WarpAPI;
import com.zyneonstudios.nerotvlive.projectsbase.commands.SRLCommand;
import com.zyneonstudios.nerotvlive.projectsbase.objects.Character;
import com.zyneonstudios.nerotvlive.projectsbase.objects.CharacterSkin;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import net.skinsrestorer.api.SkinsRestorer;
import net.skinsrestorer.api.SkinsRestorerProvider;
import net.skinsrestorer.api.property.InputDataResult;
import net.skinsrestorer.api.storage.PlayerStorage;
import net.skinsrestorer.api.storage.SkinStorage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

//no, it's totally fine to use a default String
@SuppressWarnings( "deprecation")
public class PlayerJoinListener implements Listener {

    Scoreboard mainBoard = Bukkit.getScoreboardManager().getNewScoreboard();

    SkinsRestorer skinsRestorerAPI = SkinsRestorerProvider.get();
    SkinStorage skinStorage = skinsRestorerAPI.getSkinStorage();
    PlayerStorage playerStorage = skinsRestorerAPI.getPlayerStorage();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setScoreboard(mainBoard);
        Team team;
        if(mainBoard.getTeam(p.getUniqueId().toString())!=null) {
            team = mainBoard.getTeam(p.getUniqueId().toString());
        } else {
            team = mainBoard.registerNewTeam(p.getUniqueId().toString());
        }
        assert team != null;
        team.addEntry(p.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        Main.onlineUsers.remove(p.getUniqueId());
        User u = Main.getUser(p);
        if(!p.getGameMode().equals(GameMode.SURVIVAL)&&(p.isOp()||p.hasPermission("zyneon.teammode"))) {
            u.setTeamMode(true);
            p.setOp(true);
            p.setGameMode(GameMode.SPECTATOR);
            Communicator.sendInfo(p,"Teammodus§8: §etrue");
        } else {
            u.setTeamMode(false);
            p.setOp(false);
        }
        if(!p.hasPermission("zyneon.teammode")) {
            p.setGameMode(GameMode.SURVIVAL);
            p.setOp(false);
        }

        if(!u.hasPlayedBefore()) {
            p.teleport(getRandomSpawn());
            u.setHasPlayedBefore(true);
        }
        u.setRoleplay(p.getWorld().equals(Bukkit.getWorlds().getFirst()));

        p.setPlayerListHeader("\n§r §r §cPrimal 4§r §r \n§r §r §cPrimal Aftermath§r §r \n");
        p.setPlayerListFooter("\n§r §r §7by §fZYNEON PROJECTS§r §r \n§r §r §7hosted by §9elizon.app§r §r \n");

        Character character = u.getSelectedCharacter();
        CharacterSkin skin = character.getSelectedSkin();
        u.initListName();

        try {
            Optional<InputDataResult> result = skinStorage.findOrCreateSkinData(skin.getSkinUrl());
            if(result.isPresent()) {
                playerStorage.setSkinIdOfPlayer(p.getUniqueId(), result.get().getIdentifier());
                skinsRestorerAPI.getSkinApplier(Player.class).applySkin(p);
            }
        } catch (Exception ignore) {}

        e.setJoinMessage("§8» §a"+p.getName());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(SRLCommand.countdown!=null) {
            e.setKickMessage("§cDer Server startet zurzeit neu§8,§c versuch es später erneut§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        } else if(Main.maintenance) {
            if(!e.getPlayer().hasPermission("zyneon.teammode")) {
                e.setKickMessage("§cDer Server ist zurzeit im Wartungsmodus§8,§c versuch es später erneut§8!");
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            }
        } else if(!Main.whitelist.contains(e.getPlayer().getUniqueId().toString())) {
            e.setKickMessage("§cDu darfst auf diesem Server nicht spielen§8!");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    private static ArrayList<String> warps = null;
    private static void initSpawns() {
        warps = new ArrayList<>();
        for(int i = 1; i <= 4; i++) {
            String name = "rincon_hotel_0"+i;
            if(WarpAPI.ifWarpExists(name)) {
                warps.add(name);
            }
        }
    }

    private static Location getRandomSpawn() {
        try {
            if (warps == null) {
                initSpawns();
            }
            ArrayList<String> list = warps;
            String result = "rincon_hotel_0";
            int random = ThreadLocalRandom.current().nextInt(1, 5);
            return WarpAPI.getWarp(result+random).getLocation();
        } catch (Exception e) {
            Communicator.sendError(e.getMessage());
            try {
                return WarpAPI.getWarp("rincon_hotel_01").getLocation();
            } catch (Exception e1) {
                return Bukkit.getWorlds().getFirst().getSpawnLocation();
            }
        }
    }
}