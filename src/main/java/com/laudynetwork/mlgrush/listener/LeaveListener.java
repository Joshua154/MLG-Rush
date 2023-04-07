package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.database.mysql.MySQL;
import com.laudynetwork.database.mysql.utils.Update;
import com.laudynetwork.database.mysql.utils.UpdateValue;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.game.PlayerStatus;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class LeaveListener implements Listener {
    private final MySQL sql;

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.quitMessage(Component.empty());
        Game game = MLG_Rush.get().getGame();

        /*Document doc = new Document("status", PlayerStatus.Lobby.toString());

        MongoManager.getInstance().getDatabase().getCollection("playerData").updateOne(
                Filters.eq("_id", event.getPlayer().getUniqueId().toString()),
                new Document("$set", doc)
        );*/

        sql.rowUpdate(new Update("minecraft_general_playerData",
                new UpdateValue("status", PlayerStatus.Lobby),
                "uuid='" + event.getPlayer().getUniqueId() + "'")
        );

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
