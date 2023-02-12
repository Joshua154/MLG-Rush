package com.laudynetwork.mlgrush.game;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.api.chatutils.TextUtils;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.timer.Timer;
import com.laudynetwork.mlgrush.timer.TimerMode;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Game {
    public final int falldistance = 10;
    public final int bedsToWin = 10;
    private final int lobbycountdownTime = 5;
    private final Location SpectatorSpawnLocation;
    public PlayerManager player1 = null;
    public PlayerStats player1Stats = null;
    public PlayerManager player2 = null;
    public PlayerStats player2Stats = null;
    public int length = 14;
    public int height = 100;
    public int radius = 20;
    public Location center = new Location(Bukkit.getWorlds().get(0), 0, height, 0);
    public List<Block> blockPlaced = new ArrayList<>();
    public List<Location> occupiedBlocks = new ArrayList<>();
    public boolean isRunning = false;
    Timer timer = new Timer(false, 0, TimerMode.COUNTUP);
    private int round = 0;

    public Game() {
        SpectatorSpawnLocation = center.clone().add(0, 5, 10);
        SpectatorSpawnLocation.setYaw(180);
        SpectatorSpawnLocation.setPitch(30);
    }

    public Location getSpectatorSpawnLocation() {
        return SpectatorSpawnLocation;
    }

    public void startMatch() {
        if (round == 1) {
            //removeGhostBlocksLoop();
        }

        this.isRunning = true;
        timer.setRunning(true);

        player1.player.setGameMode(GameMode.SURVIVAL);
        player2.player.setGameMode(GameMode.SURVIVAL);
    }

    public void startNextRound() {
        if (player1.bedsDestroyed >= bedsToWin) {
            endGame(player1);
            return;
        } else if (player2.bedsDestroyed >= bedsToWin) {
            endGame(player2);
            return;
        }

        this.round++;

        player1.respawnPlayer();
        player2.respawnPlayer();

        if (this.round == 1) {
            new CountDown(lobbycountdownTime, Bukkit.getWorlds().get(0));
        } else {
            startMatch();
            resetMap();
        }
    }

    public void endGameDisconnect(Player disconnectedPlayer) {
        resetMap();
        timer.setRunning(false);
        isRunning = false;

        PlayerManager winner = getOpponent(disconnectedPlayer);
        endGame(winner);
    }

    public void endGame(PlayerManager wonPlayer) {
        resetMap();
        timer.setRunning(false);

        player1.addRoundPlayed();
        player2.addRoundPlayed();
        wonPlayer.addWin();

        player1Stats.saveDataToDatabase();
        player2Stats.saveDataToDatabase();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(SpectatorSpawnLocation);
            player.setGameMode(GameMode.SPECTATOR);


            String message = "Game ended! {{player}} won!";
            message = message.replace("{{player}}", MLG_Rush.get().getColors().get("highlight") + wonPlayer.player.getName() + MLG_Rush.get().getColors().get("mainColor"));
            player.sendMessage(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + message));
        });

        stopServer();
    }

    public void abortGame(Player quitPlayer) {
        timer.setRunning(false);
        isRunning = false;

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.teleport(SpectatorSpawnLocation);
            player.setGameMode(GameMode.SPECTATOR);

            String message = "Game ended! {{player}} has quit!\nNo Stats will be saved";
            player.sendMessage(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + message));
            for (String s : message.split("\\n")) {
                s = s.replace("\n", "");
                player.sendMessage(HexColor.translate(s.replace("{{player}}", MLG_Rush.get().getColors().get("highlight") + quitPlayer.getName() + MLG_Rush.get().getColors().get("mainColor"))));
            }
        });
        stopServer();
    }

    public void stopServer() {
        Bukkit.getScheduler().runTaskLater(MLG_Rush.get(), () -> {
            /*Bukkit.getOnlinePlayers().forEach(player -> {
                IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);
                playerManager.getPlayerExecutor(player.getUniqueId()).connect("Lobby-1");
            });*/
            MLG_Rush.get().getServer().shutdown();
        }, 20 * 5);
    }

    public PlayerManager addPlayer(Player player) {
        if (this.player1 == null) {
            this.player1 = new PlayerManager(player, true);
            this.player1Stats = new PlayerStats(player1);
            player1.setUp();
            return this.player1;
        } else if (this.player2 == null) {
            this.player2 = new PlayerManager(player, false);
            this.player2Stats = new PlayerStats(player2);
            player2.setUp();
            return this.player1;
        }
        return null;
    }

    public void removePlayer(Player player) {
        if (this.player1.equals(player)) {
            this.player1 = null;
        } else if (this.player2.equals(player)) {
            this.player2 = null;
        }
    }

    public void updatePlayer() {
        if (player1 != null && player2 != null) {
            startNextRound();
        }
    }

    public PlayerManager getPlayer(Player player) {
        if (player1.player.equals(player)) {
            return player1;
        } else if (player2.player.equals(player)) {
            return player2;
        } else return null;
    }

    public PlayerManager getOpponent(Player player) {
        if (player1.player.equals(player)) {
            return player2;
        } else if (player2.player.equals(player)) {
            return player1;
        } else return null;
    }

    public void resetMap() {
        if (blockPlaced.size() == 0) return;
        Collections.reverse(blockPlaced);

        /*for(Block block : blockPlaced) {
            blockPlacedMaterial.add(block.getType());
        }*/

        for (Block block : blockPlaced) {


            BlockData blockData = block.getBlockData();

            block.setType(Material.AIR);

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.spawnParticle(Particle.BLOCK_DUST, block.getLocation().clone().add(0.5, 0.5, 0.5), 20, blockData);
                player.playSound(block.getLocation(), Sound.BLOCK_HONEY_BLOCK_BREAK, 1, 1);
            }
        }

        blockPlaced.clear();

        //clearGhostBlocks();
    }

    public String getTime() {
        return TextUtils.formatTime(timer.getTime(), false);
    }

    public int getRound() {
        return round;
    }

    public void clearGhostBlocks() {
        Iterator var10 = sphere.generateSphere(center, this.radius, false).iterator();

        while (var10.hasNext()) {
            Location location = (Location) var10.next();
            Block block = location.getBlock();

            player1.player.sendBlockChange(location, block.getBlockData());
            player2.player.sendBlockChange(location, block.getBlockData());
        }
    }

    public PlayerStats getPlayerStats(Player player) {
        if (player1.player.equals(player)) {
            return player1Stats;
        } else if (player2.player.equals(player)) {
            return player2Stats;
        } else return null;
    }

    /*public void updateStatus(Player player, PlayerStatus playing) {
        Document doc = new Document("status", playing.toString());

        MongoManager.getInstance().getDatabase().getCollection("playerData").updateOne(
                Filters.eq("_id", player.getUniqueId().toString()),
                new Document("$set", doc)
        );
    }*/

    /*public void removeGhostBlocksLoop(){
        Bukkit.getScheduler().runTaskTimer(MLG_Rush.get(), this::clearGhostBlocks, 20, 20);
    }*/
}
