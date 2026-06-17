package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.Main;
import com.zyneonstudios.nerotvlive.projectsbase.api.WorldAPI;
import com.zyneonstudios.nerotvlive.projectsbase.objects.User;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class WorldCommand implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player p) {
            Communicator.sendError(s, "§4Fehler: §c/world §7(load/create) [Welt] §f(normal/nether/end) §8(normal/flat/amplified/large_biomes/cleanroom)");
        } else {
            Communicator.sendError(s, "§4Fehler: §c/world load/create [Welt] §f(normal/nether/end) §7(normal/flat/amplified/large_biomes/cleanroom)");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("world")) {
            if(s instanceof Player p) {
                User u = Main.getUser(p);
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        sendSyntax(p);
                    } else if(args.length == 1){
                        if(Bukkit.getWorld(args[0])!=null) {
                            Communicator.sendInfo(p,"§7Teleportiere§8...");
                            p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[0])).getSpawnLocation());
                        } else {
                            Communicator.sendError(p,"§cDiese Welt existiert nicht oder ist nicht geladen. Versuche einen anderen Namen, oder mache §4/world load/create "+args[0]+"§c!");
                        }
                    } else if(args.length == 2){
                        if(Bukkit.getWorld(args[0])!=null) {
                            p.performCommand("world "+args[0]);
                        } else {
                            if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                                if(Bukkit.getWorld(args[1])==null) {
                                    Communicator.sendInfo(p,"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.NORMAL,true);
                                    p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                    Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                } else {
                                    Communicator.sendError(p,"§cDiese Welt ist bereits geladen. Mache §4/world "+args[1]+"§c um zu ihr zu gelangen!");
                                }
                            } else {
                                sendSyntax(p);
                            }
                        }
                    } else if(args.length == 3) {
                        if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                            if(Bukkit.getWorld(args[1])==null) {
                                if(args[2].equalsIgnoreCase("nether")) {
                                    s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NETHER,WorldType.NORMAL,true);
                                    p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                    Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                    s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1],org.bukkit.World.Environment.THE_END,WorldType.NORMAL,true);
                                    p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                    Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                } else if(args[2].equalsIgnoreCase("normal")) {
                                    s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.NORMAL,true);
                                    p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                    Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                } else {
                                    sendSyntax(s);
                                }
                            } else {
                                Communicator.sendError(s,"§cDiese Welt ist bereits geladen. Aber da ich ein schlaues Plugin bin, teleportiere ich dich trotzdem!");
                                p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                            if(Bukkit.getWorld(args[1])==null) {
                                if(args[3].equalsIgnoreCase("normal")) {
                                    if(args[2].equalsIgnoreCase("nether")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NETHER,WorldType.NORMAL,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.THE_END,WorldType.NORMAL,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("normal")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.NORMAL,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                } else if(args[3].equalsIgnoreCase("flat")) {
                                    if(args[2].equalsIgnoreCase("nether")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NETHER,WorldType.FLAT,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.THE_END,WorldType.FLAT,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("normal")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.FLAT,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                } else if(args[3].equalsIgnoreCase("large_biomes")||args[3].equalsIgnoreCase("largebiomes")) {
                                    if(args[2].equalsIgnoreCase("nether")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NETHER,WorldType.LARGE_BIOMES,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.THE_END,WorldType.LARGE_BIOMES,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("normal")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.LARGE_BIOMES,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                } else if(args[3].equalsIgnoreCase("void")||args[3].equalsIgnoreCase("clean")||args[3].equalsIgnoreCase("clear")||args[3].equalsIgnoreCase("cleanroom")) {
                                    if(args[2].equalsIgnoreCase("nether")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createVoidWorld(args[1],org.bukkit.World.Environment.NETHER);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createVoidWorld(args[1],org.bukkit.World.Environment.THE_END);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("normal")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createVoidWorld(args[1],org.bukkit.World.Environment.NORMAL);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                } else if(args[3].equalsIgnoreCase("amplified")) {
                                    if(args[2].equalsIgnoreCase("nether")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NETHER,WorldType.AMPLIFIED,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.THE_END,WorldType.AMPLIFIED,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else if(args[2].equalsIgnoreCase("normal")) {
                                        s.sendMessage(Strings.prefix+"§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                        WorldAPI.createWorld(args[1],org.bukkit.World.Environment.NORMAL,WorldType.AMPLIFIED,true);
                                        p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                                        Communicator.sendInfo(p,"§7Die Welt wurde geladen und du wurdest zu ihr teleportiert§8!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                } else {
                                    sendSyntax(s);
                                }
                            } else {
                                Communicator.sendError(s,"§cDiese Welt ist bereits geladen. Aber da ich ein schlaues Plugin bin, teleportiere ich dich trotzdem!");
                                p.teleport(Objects.requireNonNull(Bukkit.getWorld(args[1])).getSpawnLocation());
                            }
                        } else {
                            sendSyntax(s);
                        }
                    }
                } else {
                    Communicator.sendError(p,Strings.noPermission);
                }
            } else {
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                        if (Bukkit.getWorld(args[1]) == null) {
                            Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                            WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.NORMAL,true);
                            Communicator.sendInfo("§7Welt geladen!");
                        } else {
                            Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                        if(args[2].equalsIgnoreCase("normal")) {
                            if (Bukkit.getWorld(args[1]) == null) {
                                Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.NORMAL,true);
                                Communicator.sendInfo("§7Welt geladen!");
                            } else {
                                Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                            }
                        } else if(args[2].equalsIgnoreCase("nether")) {
                            if (Bukkit.getWorld(args[1]) == null) {
                                Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NETHER, WorldType.NORMAL,true);
                                Communicator.sendInfo("§7Welt geladen!");
                            } else {
                                Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                            }
                        } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                            if (Bukkit.getWorld(args[1]) == null) {
                                Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                WorldAPI.createWorld(args[1], org.bukkit.World.Environment.THE_END, WorldType.NORMAL,true);
                                Communicator.sendInfo("§7Welt geladen!");
                            } else {
                                Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else if(args.length == 4) {
                    if(args[0].equalsIgnoreCase("load")||args[0].equalsIgnoreCase("create")) {
                        if(args[3].equalsIgnoreCase("normal")) {
                            if(args[2].equalsIgnoreCase("normal")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.NORMAL,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("nether")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NETHER, WorldType.NORMAL,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.THE_END, WorldType.NORMAL,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else if(args[3].equalsIgnoreCase("flat")) {
                            if(args[2].equalsIgnoreCase("normal")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.FLAT,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("nether")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NETHER, WorldType.FLAT,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.THE_END, WorldType.FLAT,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else if(args[3].equalsIgnoreCase("void")||args[3].equalsIgnoreCase("clean")||args[3].equalsIgnoreCase("clear")||args[3].equalsIgnoreCase("cleanroom")) {
                            if(args[2].equalsIgnoreCase("normal")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createVoidWorld(args[1], org.bukkit.World.Environment.NORMAL);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("nether")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createVoidWorld(args[1], org.bukkit.World.Environment.NETHER);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createVoidWorld(args[1], org.bukkit.World.Environment.THE_END);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else if(args[3].equalsIgnoreCase("amplified")) {
                            if(args[2].equalsIgnoreCase("normal")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.AMPLIFIED,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("nether")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NETHER, WorldType.AMPLIFIED,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.THE_END, WorldType.AMPLIFIED,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else if(args[3].equalsIgnoreCase("largebiomes")||args[3].equalsIgnoreCase("large_biomes")) {
                            if(args[2].equalsIgnoreCase("normal")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NORMAL, WorldType.LARGE_BIOMES,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("nether")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.NETHER, WorldType.LARGE_BIOMES,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else if(args[2].equalsIgnoreCase("end")||args[2].equalsIgnoreCase("the_end")) {
                                if (Bukkit.getWorld(args[1]) == null) {
                                    Communicator.sendInfo("§7Erstelle Welt§8,§7 dies kann ein bisschen dauern§8...");
                                    WorldAPI.createWorld(args[1], org.bukkit.World.Environment.THE_END, WorldType.LARGE_BIOMES,true);
                                    Communicator.sendInfo("§7Welt geladen!");
                                } else {
                                    Communicator.sendError(s, "§cDiese Welt ist bereits geladen!");
                                }
                            } else {
                                sendSyntax(s);
                            }
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    sendSyntax(s);
                }
            }
        }
        return false;
    }
}
