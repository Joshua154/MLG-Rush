package com.laudynetwork.mlgrush.game;

import com.laudynetwork.api.builder.ItemBuilder;
import com.laudynetwork.mlgrush.MLG_Rush;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final Game game = MLG_Rush.get().getGame();
    public Material bedMaterial;
    public int roundsWon;
    public int roundsPlayed;
    public Material blockMaterial;
    public int kills = 0;
    public int deaths = 0;
    public int bedsDestroyed = 0;
    public ItemStack[] inventory = new ItemStack[41];
    public ItemStack stick;
    public ItemStack pickaxe;
    public ItemStack block;
    public String color;
    public String language = "en_US";
    Player player;
    List<String> invOrder = null;
    private Location spawnLocation;
    private Location bedLocation;
    private boolean teamType;


    public PlayerManager(Player player, boolean teamType) {
        this.player = player;
        this.teamType = teamType;

        World world = Bukkit.getWorlds().get(0);

        if (teamType) {
            Location center = game.center;
            this.bedLocation = center.clone().add((game.length - 1), 1, 0);
            this.spawnLocation = center.clone().add(0.5 + game.length, 5, 0.5);

            this.spawnLocation.setYaw(this.teamType ? 90 : -90);
            this.spawnLocation.setPitch(0);
        } else {
            Location center = new Location(world, 0, game.height, 0);
            this.bedLocation = center.clone().add(-(game.length - 1), 1, 0);
            this.spawnLocation = center.clone().add(0.5 - game.length, 5, 0.5);

            this.spawnLocation.setYaw(this.teamType ? 90 : -90);
            this.spawnLocation.setPitch(0);
        }
        generateBedMaterial();
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addBedDestroyed() {
        bedsDestroyed++;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getBedLocation() {
        return bedLocation;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void respawnBed() {
        if (teamType) {
            Block block = bedLocation.getBlock();
            Bed bed = (Bed) bedMaterial.createBlockData();
            bed.setPart(Bed.Part.FOOT);
            bed.setFacing(BlockFace.EAST);
            block.setBlockData(bed);

            block = bedLocation.clone().add(1, 0, 0).getBlock();
            bed = (Bed) bedMaterial.createBlockData();
            bed.setPart(Bed.Part.HEAD);
            bed.setFacing(BlockFace.EAST);
            block.setBlockData(bed);
        } else {
            Block block = bedLocation.getBlock();
            Bed bed = (Bed) bedMaterial.createBlockData();
            bed.setPart(Bed.Part.FOOT);
            bed.setFacing(BlockFace.WEST);
            block.setBlockData(bed);

            block = bedLocation.clone().add(-1, 0, 0).getBlock();
            bed = (Bed) bedMaterial.createBlockData();
            bed.setPart(Bed.Part.HEAD);
            bed.setFacing(BlockFace.WEST);
            block.setBlockData(bed);
        }
        setupSpawnPlatform();
        generateInv();
    }

    private void setupSpawnPlatform() {
        Block block = spawnLocation.getBlock();

        Material material = Material.getMaterial(color + "_STAINED_GLASS");
        material = material != null ? material : Material.getMaterial(color);
        material = material != null ? material : Material.WHITE_STAINED_GLASS;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                block.getRelative(x, -1, z).setType(material);
            }
        }
        material = Material.getMaterial(color + "_WOOL");
        material = material != null ? material : Material.getMaterial(color);
        material = material != null ? material : Material.WHITE_WOOL;
        block.getRelative(0, -1, 0).setType(material);
    }

    public void setupOccupiedBlocks() {
        Location location = spawnLocation.getBlock().getLocation();

        for (int y = 0; y <= 2; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    game.occupiedBlocks.add(location.clone().add(x, y, z));
                }
            }
        }
    }

    public void respawnPlayer() {
        saveCurrentInv();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(spawnLocation);
        player.setFallDistance(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFallDistance(0);

        player.getInventory().setContents(inventory.clone());
    }

    public boolean isPlayerEqual(Player player) {
        return this.player.equals(player);
    }

    public void setUpPath() {
        Location center = new Location(Bukkit.getWorlds().get(0), 0, game.height, 0);
        for (int i = 0; i < game.length; i++) {
            Block block = Bukkit.getWorlds().get(0).getBlockAt(center.clone().add(i * (teamType ? 1 : -1), 0, 0));
            if (block.isEmpty()) {
                block.setType(Material.STONE);
            }
        }
    }

    public void setUp() {
        respawnBed();
        setUpPath();
        setupOccupiedBlocks();
    }

    public Player getPlayer() {
        return player;
    }

    public void addRoundPlayed() {
        this.roundsPlayed++;
    }

    public void addWin() {
        this.roundsWon++;
    }

    public void generateInv() {
        //Inventory setup
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new ItemStack(Material.AIR);
        }

        stick = new ItemBuilder(Material.STICK).setAmount(1).addEnchant(Enchantment.KNOCKBACK, 1, false).build();
        //stick = new ItemBuilder(Material.STICK).setAmount(1).build();

        pickaxe = new ItemBuilder(Material.WOODEN_PICKAXE).setAmount(1).addEnchant(Enchantment.DIG_SPEED, 2, false).setUnbreakable(true).build();

        Material material = Material.getMaterial(color + "_CONCRETE");
        material = material != null ? material : Material.getMaterial(color);
        material = material != null ? material : Material.WHITE_CONCRETE;
        this.blockMaterial = material;

        block = new ItemBuilder(material).setAmount(64).build();

        if (invOrder != null) {
            inventory[Integer.parseInt(invOrder.get(0))] = stick;
            inventory[Integer.parseInt(invOrder.get(1))] = pickaxe;
            inventory[Integer.parseInt(invOrder.get(2))] = block;
        } else {
            inventory[0] = stick;
            inventory[1] = pickaxe;
            inventory[2] = block;

            invOrder = new ArrayList<>();

            invOrder.add(0 + "");
            invOrder.add(1 + "");
            invOrder.add(2 + "");
        }
    }

    public void generateBedMaterial() {
        Material bedMaterial = Material.getMaterial(color + "_BED");
        this.bedMaterial = bedMaterial != null ? bedMaterial : Material.WHITE_BED;
    }

    public boolean isPlayerBed(Block block) {
        if (block.getType().equals(bedMaterial)) {
            if (teamType) {
                return block.getLocation().equals(bedLocation) || block.getLocation().equals(bedLocation.clone().add(1, 0, 0));
            } else {
                return block.getLocation().equals(bedLocation) || block.getLocation().equals(bedLocation.clone().add(-1, 0, 0));
            }
        }
        return false;
    }

    public void saveCurrentInv() {
        boolean stickBool = false, pickaxeBool = false, blockBool = false;
        ItemStack[] temp = player.getInventory().getContents();
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null) {
                if (temp[i].getType().equals(stick.getType()) && !stickBool) {
                    stickBool = true;
                    invOrder.set(0, String.valueOf(i));
                }
                if (temp[i].getType().equals(pickaxe.getType()) && !pickaxeBool) {
                    pickaxeBool = true;
                    invOrder.set(1, String.valueOf(i));
                }
                if (temp[i].getType().equals(block.getType()) && !blockBool) {
                    blockBool = true;
                    invOrder.set(2, String.valueOf(i));
                }
            }
        }
        generateInv();
    }

    public int getBedsDestroyed() {
        return bedsDestroyed;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PlayerManager) {
            return ((PlayerManager) obj).getPlayer().equals(player);
        }
        return false;
    }
}
