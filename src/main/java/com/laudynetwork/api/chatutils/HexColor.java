package com.laudynetwork.api.chatutils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class HexColor {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F\\d]{6}");

    public static String translate(String message) {
        if (message == null) return "";
        if (message.contains("&x")) message = message.replaceAll("&x", "#");
        String bukkitVersion = Bukkit.getVersion();
        if (bukkitVersion.contains("1.16") || bukkitVersion.contains("1.17") || bukkitVersion.contains("1.18") ||
                bukkitVersion.contains("1.19")) {
            Matcher match = pattern.matcher(message);
            while (match.find()) {
                String color = message.substring(match.start(), match.end());
                message = message.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static TextComponent asTextComponent(String message) {
        return new TextComponent(TextComponent.fromLegacyText(translate(message)));
    }

}
