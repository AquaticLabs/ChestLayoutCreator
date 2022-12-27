package me.extremesnow.chestlayoutcreator.util;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Trevor/extremesnow
 * @since 12/24/2020 at 4:49 PM
 */
public class DebugLogger {

    @Setter
    private static boolean debug = false;

    public static void logDebugMessage(String message) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}
