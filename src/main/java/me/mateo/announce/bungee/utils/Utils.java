
package me.mateo.announce.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;


public class Utils {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static TextComponent formatComponent(String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static String getName = "    NasgarAnnonunce";
    public static String getVersion = "1.0";
    public static String getDeveloper = "AYisGOD";

}
