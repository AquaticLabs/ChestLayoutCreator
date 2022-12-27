package me.extremesnow.chestlayoutcreator.util;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    private static final Pattern HEX_REGEX = Pattern.compile("#[0-9A-Fa-f]{6}|#[0-9A-Fa-f]{3}");


    public static String tryReplaceHexCodes(String input) {
        Version version = new Version(getVersion());

        if (version.compareTo(new Version("1.16.0")) > 0) {
            final Matcher matcher = HEX_REGEX.matcher(input);
            final StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
            }
            return matcher.appendTail(buffer).toString().replace("&", "§");
        }
        return input.replace("&", "§");
    }

    public static String getVersion() {
        String bukkitver = Bukkit.getServer().getVersion();
        String mcver = "1.0.0";
        int idx = bukkitver.indexOf("(MC: ");
        if (idx > 0) {
            mcver = bukkitver.substring(idx + 5);
            idx = mcver.indexOf(')');
            if (idx > 0) mcver = mcver.substring(0, idx);
        }
        return mcver;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}

