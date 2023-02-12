package com.laudynetwork.mlgrush.listener;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.mlgrush.Colors;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.actionbar.PlayerGameInfo;
import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.game.PlayerManager;
import com.laudynetwork.mlgrush.scoreboard.GameScoreBoard;
import com.laudynetwork.mlgrush.scoreboard.SpecScoreBord;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws NoSuchFieldException, IllegalAccessException {
        event.setJoinMessage("");
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);


        //MLG_Rush.get().playerLanguages.put(player, LanguageKey.of(MongoManager.getInstance().getDatabase().getCollection("playerData").find(Filters.eq("_id", player.getUniqueId().toString())).first().get("language").toString()));


        Game game = MLG_Rush.get().getGame();
        PlayerManager playerManager = MLG_Rush.get().getGame().addPlayer(player);


        if (playerManager != null && !game.isRunning) {
            /*Document doc = new Document("state", PlayerStatus.MLG_Playing.toString());

            MongoManager.getInstance().getDatabase().getCollection("playerData").updateOne(
                    Filters.eq("_id", player.getUniqueId().toString()),
                    new Document("$set", doc)
            );*/


            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);


            //String message = MLG_Rush.get().getTranslations().get(MLG_Rush.get().playerLanguages.get(player)).getTranslation("game.join");
            //player.sendMessage(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + message));

            player.setBedSpawnLocation(playerManager.getSpawnLocation());

            player.teleport(game.getSpectatorSpawnLocation());

            game.updatePlayer();

            new GameScoreBoard(player);
            new PlayerGameInfo(player);

            player.setPlayerListName(HexColor.translate(Colors.getHexColor(MLG_Rush.get().getGame().getPlayer(player).color)) + player.getName());
        } else {
            /*Document doc = new Document("state", PlayerStatus.MLG_Spec.toString());

            MongoManager.getInstance().getDatabase().getCollection("playerData").updateOne(
                    Filters.eq("_id", player.getUniqueId().toString()),
                    new Document("$set", doc)
            );*/

            new SpecScoreBord(player);


            String message = "You are now spectating the game";
            player.sendMessage(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + message));

            player.teleport(MLG_Rush.get().getGame().getSpectatorSpawnLocation());
        }

        //MLG_Rush.get().invisibleWall = new InvisibleWall(Material.RED_STAINED_GLASS, Axis.X, 20, 4, player);
    }
}