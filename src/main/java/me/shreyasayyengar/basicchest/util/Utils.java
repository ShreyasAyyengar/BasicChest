package me.shreyasayyengar.basicchest.util;

import org.bukkit.ChatColor;

public class Utils {

    public static String colourise(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
