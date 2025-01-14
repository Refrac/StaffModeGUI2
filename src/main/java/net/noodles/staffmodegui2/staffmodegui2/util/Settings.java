package net.noodles.staffmodegui2.staffmodegui2.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    public static String DEVELOPER_NAME = "BGHDDevelopment";
    public static String DEVELOPER_URL = "https://bghddevelopment.com";
    public static String DEV_MESSAGE = ChatColor.RED + "This command can only be used by BGHDDevelopment for support reasons.";
    public static String DEV_MESSAGE2 = ChatColor.GRAY + "If you are worried about what this command does check it out on GitHub here:";
    public static String DEV_MESSAGE3 = ChatColor.GRAY + "https://goo.gl/87ZgJz";
    public static String PLUGIN_URL = "https://spigotmc.org/resources/60960";
    public static String SUPPORT_DISCORD_URL = "https://bghddevelopment.com/discord";
    public static String VERSION = "1.3.1";
    public static String WIKI = "https://wiki.bghddevelopment.com";
    public static String NAME = "StaffModeGUI2";

    public static List<Player> getOnlineStaff() {
        List<Player> players = new ArrayList<>();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.hasPermission("staffmodegui.staff")) {
                players.add(player);
            }
        }

        return players;
    }

}