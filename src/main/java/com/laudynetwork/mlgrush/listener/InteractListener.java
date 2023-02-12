package com.laudynetwork.mlgrush.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getPlayer().isSneaking()) return;
        if (!event.getClickedBlock().getType().toString().contains("_BED")) return;

        event.setCancelled(true);
    }
}
