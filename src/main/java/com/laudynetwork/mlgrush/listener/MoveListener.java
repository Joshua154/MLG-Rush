package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;
        Game game = MLG_Rush.get().getGame();
        if (!game.isRunning) {
            if (event.getTo().getX() != event.getFrom().getX() || event.getTo().getZ() != event.getFrom().getZ()) {
                event.setCancelled(true);
            }
        }

        Player player = event.getPlayer();

        if (player.getLocation().getY() <= game.height - game.falldistance) {
            game.getPlayer(player).addDeath();
            game.getOpponent(player).addKill();
            game.getPlayer(player).respawnPlayer();
        }
    }

    private boolean movedBlock(Location from, Location to) {
        return from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ();
    }
}
