package com.laudynetwork.mlgrush.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemLoot(ItemSpawnEvent event) {
        event.setCancelled(true);
    }
}
