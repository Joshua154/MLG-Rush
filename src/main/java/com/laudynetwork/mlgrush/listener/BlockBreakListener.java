package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Game game = MLG_Rush.get().getGame();

        boolean player1and2bed = game.player2.isPlayerBed(event.getBlock()) && game.player1.isPlayerEqual(event.getPlayer());
        boolean player2and1bed = game.player1.isPlayerBed(event.getBlock()) && game.player2.isPlayerEqual(event.getPlayer());

        if (player1and2bed || player2and1bed) {
            event.setCancelled(true);

            if (player1and2bed) {
                game.player1.addBedDestroyed();

                game.player1.respawnPlayer();
                game.player2.respawnPlayer();

                game.player1.getPlayer().playSound(game.player1.getSpawnLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 100, 1);
                game.player2.getPlayer().playSound(game.player2.getSpawnLocation(), Sound.BLOCK_CONDUIT_DEACTIVATE, 100, 2);
            } else {
                game.player2.addBedDestroyed();

                game.player1.respawnPlayer();
                game.player2.respawnPlayer();

                game.player1.getPlayer().playSound(game.player1.getSpawnLocation(), Sound.BLOCK_CONDUIT_DEACTIVATE, 100, 2);
                game.player2.getPlayer().playSound(game.player2.getSpawnLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 100, 1);
            }
            //game.isRunning = false;
            game.startNextRound();
        } else if (!game.blockPlaced.contains(event.getBlock())) {
            event.setCancelled(true);
        } else {
            if (game.blockPlaced.contains(event.getBlock())) {
                if (event.getPlayer().getInventory().getItemInOffHand().getType().equals(game.getPlayer(event.getPlayer()).getBlockMaterial())) {
                    if (event.getPlayer().getInventory().getItemInOffHand().getAmount() < 64) {
                        event.getPlayer().getInventory().getItemInOffHand().setAmount(event.getPlayer().getInventory().getItemInOffHand().getAmount() + 1);
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.6F, ThreadLocalRandom.current().nextFloat(0.9F, 1.9F));
                    } else {
                        event.getPlayer().getInventory().addItem(new ItemStack(game.getPlayer(event.getPlayer()).getBlockMaterial()));
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.6F, ThreadLocalRandom.current().nextFloat(0.9F, 1.9F));
                    }
                } else {
                    event.getPlayer().getInventory().addItem(new ItemStack(game.getPlayer(event.getPlayer()).getBlockMaterial()));
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.6F, ThreadLocalRandom.current().nextFloat(0.9F, 1.9F));
                }

                game.blockPlaced.remove(event.getBlock());
            }
        }
    }
}
