package com.laudynetwork.mlgrush.scoreboard;

import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.game.PlayerManager;
import com.laudynetwork.networkutils.api.scoreboard.ScoreboardBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameScoreBoard extends ScoreboardBuilder {
    private final String highlight = MLG_Rush.get().getColors().get("highlight");
    private final Game game;
    PlayerManager playerManager;


    public GameScoreBoard(Player player, Game game) {
        super(player, MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("MLGRush-Prefix")));
        this.game = game;

        playerManager = game.getPlayer(player);

        player.setScoreboard(getPlayerBoard());
    }

    private static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public void createBoard() {
        for (int i = 1; i < 9; i++) {
            setLine(Component.text(""), i);
        }

        setLine(Component.text(getCurrentDate()).color(NamedTextColor.GRAY), 9);

        setLine(MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("mainColor") + MLG_Rush.get().getColors().get("serverAddress")), 1);

        Bukkit.getScheduler().runTaskTimer(MLG_Rush.get(), this::update, 0L, 20L);
    }

    @Override
    public void update() {
        setLine(MiniMessage.miniMessage().deserialize(
                "Team <team>: (<beds>/<beds_to_win>)",
                Placeholder.component("beds",
                        Component.text(highlight + playerManager.getBedsDestroyed())
                ),
                Placeholder.component("beds_to_win",
                        Component.text(highlight + game.bedsToWin)
                ),
                Placeholder.component("team",
                        Component.text(playerManager.color + getTeamName(playerManager.color))
                )
        ), 7);

        setLine(MiniMessage.miniMessage().deserialize(
                "Team <team>: (<beds>/<beds_to_win>)",
                Placeholder.component("beds",
                        Component.text(highlight + game.getOpponent(playerManager.getPlayer()).getBedsDestroyed())
                ),
                Placeholder.component("beds_to_win",
                        Component.text(highlight + game.bedsToWin)
                ),
                Placeholder.component("team",
                        Component.text(game.getOpponent(playerManager.getPlayer()).color + getTeamName(game.getOpponent(playerManager.getPlayer()).color))
                )
        ), 6);


        if (game.player1 != null && game.player2 != null) {
            setLine(Component.text(""), 5);
        } else {
            setLine(MiniMessage.miniMessage().deserialize("Waiting for Opponent"), 5);
        }

        setLine(MiniMessage.miniMessage().deserialize(
                "Kills:  <kills>",
                Placeholder.component("kills",
                        Component.text(highlight + playerManager.getKills())
                )
        ), 4);

        setLine(MiniMessage.miniMessage().deserialize(
                "Deaths: <deaths>",
                Placeholder.component("deaths",
                        Component.text(highlight + playerManager.getDeaths())
                )
        ), 3);
    }

    private String getTeamName(String team) {
        String teamStr = team.substring(0, 1).toUpperCase() + team.substring(1).toLowerCase();
        for (String str : teamStr.split("_")) {
            teamStr = teamStr.replace(str, str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()).replaceAll("_", " ");
        }
        return teamStr;
    }
}