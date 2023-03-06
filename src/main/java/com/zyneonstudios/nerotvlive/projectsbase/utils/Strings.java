package com.zyneonstudios.nerotvlive.projectsbase.utils;

public class Strings {

    public static String prefixWord = "Projekt";
    public static void setPrefixWord(String newPrefixWord) {
        prefixWord = newPrefixWord;
        prefix = "§f"+prefixWord+" §8» §7";
    }
    public static String prefix = "§f"+prefixWord+" §8» §7";
    public static String needPlayer = "§cDazu musst du ein*e Spieler*in sein§8!";
    public static String needConsole = "§cDazu darfst du kein*e Spieler*in sein§8!";
    public static String playerNotFound = "§cDiese*r Spieler*in wurde nicht gefunden§8!";
    public static String noPermission = "§cDas darfst du nicht§8!";
}