package com.laudynetwork.mlgrush;

public enum Colors {
    /*BLACK("§0", 0, 0, 0, "#000000"),
    DARK_BLUE("§1", 0, 0, 170, "#0000AA"),
    DARK_GREEN("§2", 0, 170, 0, "#00AA00"),
    DARK_AQUA("§3", 0, 170, 170, "#00AAAA"),
    DARK_RED("§4", 170, 0, 0, "#AA0000"),
    DARK_PURPLE("§5", 170, 0, 170, "#AA00AA"),
    GOLD("§6", 255, 170, 0, "#FFAA00"),
    GRAY("§7", 170, 170, 170, "#AAAAAA"),
    DARK_GRAY("§8", 85, 85, 85, "#555555"),
    BLUE("§9", 85, 85, 255, "#5555FF"),
    GREEN("§a", 85, 255, 85, "#55FF55"),
    AQUA("§b", 85, 255, 255, "#55FFFF"),
    RED("§c", 255, 85, 85, "#FF5555"),
    LIGHT_PURPLE("§d", 255, 85, 255, "#FF55FF"),
    YELLOW("§e", 255, 255, 85, "#FFFF55"),
    WHITE("§f", 255, 255, 255, "#FFFFFF"),
    MINECION_GOLD("§g", 221, 214, 5, "#DDD605");*/

    WHITE("#f9ffff"),
    LIGHT_GRAY("#9c9d97"),
    GRAY("#474f52"),
    BLACK("#1d1c21"),
    YELLOW("#ffd83d"),
    ORANGE("#f9801d"),
    RED("#b02e26"),
    BROWN("#825432"),
    LIME("#80c71f"),
    GREEN("#5d7c15"),
    LIGHT_BLUE("#3ab3da"),
    CYAN("#169c9d"),
    BLUE("#3c44a9"),
    PINK("#f38caa"),
    MAGENTA("#c64fbd"),
    PURPLE("#8932b7"),
    EMERALD_BLOCK("#80c71f");


    private String hexColor;

    Colors(String hexColor) {
        this.hexColor = hexColor;
    }

    public static String getHexColor(String color) {
        for (Colors c : Colors.values()) {
            if (c.name().equalsIgnoreCase(color.toUpperCase())) {
                return c.hexColor;
            }
        }
        return null;
    }

    public static String getColorFromHex(String hexColor) {
        for (Colors c : Colors.values()) {
            if (c.hexColor.equals(hexColor)) {
                return c.name();
            }
        }
        return null;
    }
}
