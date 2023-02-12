package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Game game = MLG_Rush.get().getGame();

        /*Document doc = new Document("status", PlayerStatus.Lobby.toString());

        MongoManager.getInstance().getDatabase().getCollection("playerData").updateOne(
                Filters.eq("_id", event.getPlayer().getUniqueId().toString()),
                new Document("$set", doc)
        );*/

        if (game.getPlayer(event.getPlayer()) != null) {
            /*String lang = "en_US";

            if(MLG_Rush.get().getGame().getPlayerStats(event.getPlayer()) != null) lang = MLG_Rush.get().getGame().getPlayerStats(event.getPlayer()).getLang();
            FileConfiguration langConfig = MLG_Rush.get().getLanguages().getFileConfiguration();
            String message = MLG_Rush.decodeColorFromConfig(langConfig.getString(lang + ".game.quit"));

            event.setQuitMessage(HexColor.translate(message.replaceAll("%player%", event.getPlayer().getName())));*/

            if (game.isRunning) {
                if (game.getRound() >= 5) {
                    game.endGameDisconnect(event.getPlayer());
                } else {
                    game.abortGame(event.getPlayer());
                }
            } else {
                game.stopServer();
            }
        }
    }
}
