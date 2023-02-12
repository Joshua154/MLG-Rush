package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Game game = MLG_Rush.get().getGame();

        if (game.occupiedBlocks.contains(event.getBlockPlaced().getLocation())) {
            event.setCancelled(true);
            return;
        }
        if (!event.getBlock().getLocation().toVector().isInAABB(game.center.toVector().clone().subtract(new Vector(game.radius, game.radius, game.radius)), game.center.toVector().clone().add(new Vector(game.radius, game.radius, game.radius)))) {
            event.setCancelled(true);
            return;
        }
        game.blockPlaced.add(event.getBlock());
    }
}