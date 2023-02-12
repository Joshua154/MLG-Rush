package com.laudynetwork.mlgrush.game;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InvisibleWall {
    private Material material;
    private Location location;
    private Axis axis;
    private int radius;
    private int maxRadius;
    private Player player;
    private boolean enabled;
    private List<Location> blocks = new ArrayList<>();
    private int distance = 0;
    private int wallCoordinate = 0;

    public InvisibleWall(Material material, Axis axis, int wallCoordinate, int radius, Player player) {
        if (axis.equals(Axis.X)) {
            Location temp = player.getLocation().clone();
            temp.setX(wallCoordinate);
            this.location = temp.clone();

            distance = location.getBlockX() - player.getLocation().getBlockX();
        } else if (axis.equals(Axis.Y)) {
            Location temp = player.getLocation().clone();
            temp.setY(wallCoordinate);
            this.location = temp.clone();


            distance = location.getBlockY() - player.getLocation().getBlockY();
        } else if (axis.equals(Axis.Z)) {
            Location temp = player.getLocation().clone();
            temp.setZ(wallCoordinate);
            this.location = temp.clone();


            distance = location.getBlockZ() - player.getLocation().getBlockZ();
        }

        this.material = material;
        this.axis = axis;
        this.radius = 0;
        this.maxRadius = radius;
        this.player = player;
        this.enabled = true;
        this.wallCoordinate = wallCoordinate;

        if (distance > 4) {
            this.radius = 0;
        }

        generateWall();
    }

    public void generateWall() {
        if (axis.equals(Axis.X)) {
            Location temp = player.getLocation().clone();
            temp.setX(wallCoordinate);
            this.location = temp.clone();

            distance = temp.getBlockX() - player.getLocation().getBlockX();
        } else if (axis.equals(Axis.Y)) {
            Location temp = player.getLocation().clone();
            temp.setY(wallCoordinate);
            this.location = temp.clone();


            distance = location.getBlockY() - player.getLocation().getBlockY();
        } else if (axis.equals(Axis.Z)) {
            Location temp = player.getLocation().clone();
            temp.setZ(wallCoordinate);
            this.location = temp.clone();


            distance = location.getBlockZ() - player.getLocation().getBlockZ();
        }

        this.radius = Math.min(Math.max(0, maxRadius - (distance - 2)), 4);

        player.sendMessage("Distance: " + distance);
        player.sendMessage("Radius: " + radius);

        for (int i = 0; i < blocks.size(); i++) {
            Location loc = blocks.get(i);
            player.sendBlockChange(loc, player.getWorld().getBlockAt(loc).getBlockData());
            blocks.remove(loc);
        }
        if (radius <= 0) return;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                if (isInCircle(x, y, radius, 0, 0)) {
                    Location loc = location.clone();
                    if (axis.equals(Axis.X)) {
                        loc.add(0, y, x);
                    } else if (axis.equals(Axis.Y)) {
                        loc.add(x, 0, y);
                    } else if (axis.equals(Axis.Z)) {
                        loc.add(x, y, 0);
                    }
                    blocks.add(loc);
                    player.sendBlockChange(loc, material.createBlockData());
                }
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Location> generateCircle(Location center, int radius, Axis axis) {
        List<Location> locations = new ArrayList<>();
        if (radius <= 0) return locations;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                if (isInCircle(x, y, radius, 0, 0)) {
                    Location loc = center.clone();
                    if (axis.equals(Axis.X)) {
                        locations.add(loc.add(0, y, x));
                    } else if (axis.equals(Axis.Y)) {
                        locations.add(loc.add(x, 0, y));
                    } else if (axis.equals(Axis.Z)) {
                        locations.add(loc.add(x, y, 0));
                    }
                }
            }
        }
        return locations;
    }

    public List<Location> generateCircle(Location center, int radius, Axis axis, double offset) {
        List<Location> locations = new ArrayList<>();
        if (radius <= 0) return locations;
        if (offset >= 1) return locations;
        for (double x = -radius / offset; x < radius / offset; x++) {
            for (double y = -radius / offset; y < radius / offset; y++) {
                if (isInCircle(x, y, radius, 0, 0)) {
                    Location loc = center.clone();
                    if (axis.equals(Axis.X)) {
                        locations.add(loc.add(0, y, x));
                    } else if (axis.equals(Axis.Y)) {
                        locations.add(loc.add(x, 0, y));
                    } else if (axis.equals(Axis.Z)) {
                        locations.add(loc.add(x, y, 0));
                    }
                }
            }
        }
        return locations;
    }

    private boolean isInCircle(double x, double y, double radius, double centerX, double centerY) {
        return (Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) < radius);
    }
}
