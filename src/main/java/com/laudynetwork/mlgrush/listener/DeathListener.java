package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.api.knockback.KnockBack;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathListener implements Listener {
    /*@EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(MLG_Rush.get().getGame().getPlayer(player).getSpawnLocation());


        event.setDeathMessage("");
    }*/

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;


        Game game = MLG_Rush.get().getGame();
        player.setFoodLevel(20);
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(0);

            /*event.setCancelled(true);
            player.setVelocity(player.getVelocity().add((player.getLocation().getDirection().multiply(1.6d).setY(1.0d))));
            if(event instanceof EntityDamageByEntityEvent){
                Entity attacker = ((EntityDamageByEntityEvent)event).getDamager();
                KnockBack.applyKnockback(player, attacker, 0);
            }*/
        }

        if (event.getDamage() >= player.getHealth() && !event.isCancelled()) {
            event.setCancelled(true);
            game.getOpponent(player).addKill();
            game.getPlayer(player).addDeath();
            game.getPlayer(player).respawnPlayer();
        }
    }
}
