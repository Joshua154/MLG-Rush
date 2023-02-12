package com.laudynetwork.mlgrush.game;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class sphere {
    public sphere() {
    }

    public static List<Location> generateSphere(Location centerBlock, int radius, boolean hollow) {
        List<Location> circleBlocks = new ArrayList();
        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; ++x) {
            for (int y = by - radius; y <= by + radius; ++y) {
                for (int z = bz - radius; z <= bz + radius; ++z) {
                    double distance = (double) ((bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y));
                    if (distance < (double) (radius * radius) && (!hollow || !(distance < (double) ((radius - 1) * (radius - 1))))) {
                        Location l = new Location(centerBlock.getWorld(), (double) x, (double) y, (double) z);
                        circleBlocks.add(l);
                    }
                }
            }
        }

        return circleBlocks;
    }
}