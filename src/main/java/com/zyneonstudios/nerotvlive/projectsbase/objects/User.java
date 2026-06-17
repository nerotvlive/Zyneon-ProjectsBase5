package com.zyneonstudios.nerotvlive.projectsbase.objects;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.storage.types.Config;
import net.skinsrestorer.api.property.SkinVariant;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

//maybe paper isn't that good :/
@SuppressWarnings("deprecation")
public class User {

    private final Config config;
    private int adventurePoints = 0;

    private final UUID uuid;
    private final OfflinePlayer offlinePlayer;
    private final String name;
    private Player player = null;
    private boolean roleplay = true;
    private String lastCity = Math.random() < 0.5 ? "silberfels" : "rincon";
    private ArrayList<Character> characters = new ArrayList<>();
    private UUID selectedCharacter;
    private boolean hasPlayedBefore = false;
    private String lastReceivedSender = null;

    //MODES
    private String interactMode = "null";
    private String chatMode = "normal";
    private boolean teamMode = false;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.config = new Config("plugins/ProjectsBase/users/" + uuid + ".yml");
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (Bukkit.getPlayer(uuid) != null) {
            this.player = Bukkit.getPlayer(uuid);
            assert player != null;
            this.name = player.getName();
        } else {
            this.name = offlinePlayer.getName();
        }
        if(config.get("user.hasPlayedBefore")!=null) {
            this.hasPlayedBefore = config.getCFG().getBoolean("user.hasPlayedBefore");
        }
        if(config.get("user.lastCity")!=null) {
            this.lastCity = config.get("user.lastCity").toString();
        } else {
            setLastCity(lastCity);
        }
        if(config.get("user.adventurePoints")!=null) {
            this.adventurePoints = Integer.parseInt(config.get("user.adventurePoints").toString());
        } else {
            setAdventurePoints(0);
        }
        initCharacters();
    }

    @SuppressWarnings("unchecked")
    private void initCharacters() {
        if(characters.isEmpty()) {
            config.checkEntry("characters.list", new ArrayList<>());
            ArrayList<String> characters = (ArrayList<String>)config.get("characters.list");
            ArrayList<String> finalCharacters = new ArrayList<>();
            for(String uuid : characters) {
                if(new File("plugins/ProjectsBase/characters/"+uuid+".yml").exists()) {
                    finalCharacters.add(uuid);
                    this.characters.add(new Character(UUID.fromString(uuid)));
                    if (this.characters.getLast().getUUID().equals(selectedCharacter)) {
                        selectedCharacter = this.characters.getLast().getUUID();
                    }
                }
            }
            config.set("characters.list", finalCharacters);
            if(characters.isEmpty()) {
                Communicator.sendWarning(player, "Du hast einen nicht eingerichteten Standard-Charakter, stelle ihn mit /char ein!");
                addNewCharacter();
            }
            config.checkEntry("characters.selected", characters.getFirst());
            selectedCharacter = UUID.fromString(config.get("characters.selected").toString());
        }
    }

    public void addNewCharacter() {
        addNewCharacter(true);
    }

    @SuppressWarnings("unchecked")
    public void addNewCharacter(boolean save) {
        ArrayList<String> characters = (ArrayList<String>) config.get("characters.list");
        Character character = new Character(UUID.randomUUID());
        character.setName("Unbekannt ("+name+")");
        character.getSelectedSkin().setSkinUrl(name);
        character.getSelectedSkin().setSkinVariant(SkinVariant.SLIM);
        characters.add(character.getUUID().toString());
        if(save) {
            config.set("characters.list", characters);
        }
        this.characters = new ArrayList<>();
        for(String uuid : characters) {
            this.characters.add(new Character(UUID.fromString(uuid)));
            if(this.characters.getLast().getUUID().equals(selectedCharacter)) {
                selectedCharacter = this.characters.getLast().getUUID();
            }
        }
        config.checkEntry("characters.selected", characters.getFirst());
        selectedCharacter = UUID.fromString(config.get("characters.selected").toString());
    }

    public Character getSelectedCharacter() {
        return characters.stream().filter(character -> character.getUUID().equals(selectedCharacter)).findFirst().orElse(null);

    }

    public boolean hasPlayedBefore() {
        return hasPlayedBefore;
    }

    public void setHasPlayedBefore(boolean hasPlayedBefore) {
        this.hasPlayedBefore = hasPlayedBefore;
        config.set("user.hasPlayedBefore", hasPlayedBefore);
    }

    public UUID getSelectedCharacterId() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(UUID uuid) {
        selectedCharacter = uuid;
        config.set("characters.selected", uuid.toString());
    }

    public void setSelectedCharacter(Character character) {
        selectedCharacter = character.getUUID();
        config.set("characters.selected", character.getUUID().toString());
    }

    public void setSelectedCharacter(int index) {
        selectedCharacter = characters.get(index).getUUID();
        config.set("characters.selected", characters.get(index).getUUID().toString());
    }

    public void setSelectedCharacter(String name) {
        selectedCharacter = Objects.requireNonNull(characters.stream().filter(character -> character.getName().equals(name)).findFirst().orElse(null)).getUUID();
        config.set("characters.selected", selectedCharacter.toString());
    }

    public boolean hasCharacter(UUID uuid) {
        return characters.stream().anyMatch(character -> character.getUUID().equals(uuid));
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public Character getCharacter(int index) {
        return characters.get(index);
    }

    public void addCharacter(Character character) {
        characters.add(character);
        config.set("characters.list", characters.stream().map(Character::getUUID).toList());
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
        config.set("characters.list", characters.stream().map(Character::getUUID).toList());
    }

    public void removeCharacter(int index) {
        characters.remove(index);
        config.set("characters.list", characters.stream().map(Character::getUUID).toList());
    }

    public String getName() {
        return name;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isRoleplay() {
        return roleplay;
    }

    public boolean isTeamMode() {
        return teamMode;
    }

    public void setChatMode(String chatMode) {
        this.chatMode = chatMode;
    }

    public void setInteractMode(String interactMode) {
        if(this.interactMode.contains("mode")) {
            if(interactMode.contains("nullmode")) {
                this.interactMode = "null";
                return;
            }
            return;
        }
        this.interactMode = interactMode;
    }

    public void setPlayer(Player player) {
        if(player.getUniqueId().equals(this.uuid)) {
            this.player = player;
        }
    }

    public void setRoleplay(boolean roleplay) {
        this.roleplay = roleplay;
        if(Bukkit.getPlayer(uuid)!=null) {
            Player p = Bukkit.getPlayer(uuid);
            initListName();
        }
    }

    public void initListName() {
        if(Bukkit.getPlayer(uuid)!=null) {
            Player p = Bukkit.getPlayer(uuid);
            if(!player.getWorld().equals(Bukkit.getWorlds().getFirst())) {
                assert p != null;
                p.setPlayerListName("§8FARM | "+name);
            } else if(roleplay) {
                assert p != null;
                p.setPlayerListName("§6" + getSelectedCharacter().getJob()+" §8| §f"+getSelectedCharacter().getName());
            } else {
                assert p != null;
                p.setPlayerListName("§8OOC | "+name);
            }
        }
    }

    public void setTeamMode(boolean teamMode) {
        this.teamMode = teamMode;
        if(Bukkit.getPlayer(uuid)!=null) {
            Objects.requireNonNull(Bukkit.getPlayer(uuid)).setOp(teamMode);
        }
    }

    public Config getConfig() {
        return config;
    }

    public String getChatMode() {
        return chatMode;
    }

    public String getInteractMode() {
        return interactMode;
    }

    public String getLastCity() {
        return lastCity;
    }

    private boolean isGroundedCheck() {
        if (getPlayer() != null) {
            Player p = getPlayer();
            if (p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("slab") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("stair") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("farmland") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("path")) {
                return false;
            } else if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.AIR)) {
                if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.VOID_AIR)) {
                    return p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.CAVE_AIR);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean isGrounded() {
        return !isGroundedCheck();
    }

    public int getAdventurePoints() {
        return adventurePoints;
    }

    public void setAdventurePoints(int adventurePoints) {
        this.adventurePoints = adventurePoints;
        config.set("user.adventurePoints", adventurePoints);
    }

    public void addAdventurePoints(int adventurePoints) {
        setAdventurePoints(getAdventurePoints() + adventurePoints);
    }

    public void removeAdventurePoints(int adventurePoints) {
        setAdventurePoints(getAdventurePoints() - adventurePoints);
    }

    public void resetAdventurePoints() {
        setAdventurePoints(0);
    }

    public void setLastCity(String lastCity) {
        this.lastCity = lastCity;
        config.set("user.lastCity", lastCity);
    }

    public void setLastReceivedSender(String lastReceivedSender) {
        this.lastReceivedSender = lastReceivedSender;
    }

    public String getLastReceivedSender() {
        return lastReceivedSender;
    }
}