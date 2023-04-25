//package com.laudynetwork.mlgrush.scoreboard;
//
//import com.laudynetwork.mlgrush.MLG_Rush;
//import com.laudynetwork.mlgrush.game.Game;
//import com.laudynetwork.mlgrush.game.PlayerManager;
//import com.laudynetwork.networkutils.api.scoreboard.ScoreboardBuilder;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.format.NamedTextColor;
//import net.kyori.adventure.text.minimessage.MiniMessage;
//import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class SpecScoreBord extends ScoreboardBuilder {
//    private final String mainColor = MLG_Rush.get().getColors().get("mainColor");
//    private final String highlight = MLG_Rush.get().getColors().get("highlight");
//    private final Game game;
//    PlayerManager playerManager;
//
//    public SpecScoreBord(Player player, Game game) {
//        super(player, MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("MLGRush-Prefix")));
//        this.game = game;
//        playerManager = game.getPlayer(player);
//
//        player.setScoreboard(getPlayerBoard());
//    }
//
//    private static String getCurrentDate() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
//        LocalDateTime now = LocalDateTime.now();
//        return dtf.format(now);
//    }
//
//    @Override
//    public void createBoard() {
//        for (int i = 1; i < 9; i++) {
//            setLine(Component.text(""), i);
//        }
//
//        setLine(Component.text(getCurrentDate()).color(NamedTextColor.GRAY), 9);
//
//        setLine(MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("mainColor") + MLG_Rush.get().getColors().get("serverAddress")), 1);
//
//
//        Bukkit.getScheduler().runTaskTimerAsynchronously(MLG_Rush.get(), this::update, 0L, 20L);
//    }
//
//    @Override
//    public void update() {
//        setLine(MiniMessage.miniMessage().deserialize(
//                "Team <team>: (<beds>/<beds_to_win>)",
//                Placeholder.component("beds",
//                        MiniMessage.miniMessage().deserialize(highlight + game.player1.getBedsDestroyed() + mainColor)
//                ),
//                Placeholder.component("beds_to_win",
//                        MiniMessage.miniMessage().deserialize(highlight + game.bedsToWin + mainColor)
//                ),
//                Placeholder.component("team",
//                        MiniMessage.miniMessage().deserialize("<" + game.player1.color + ">" + getTeamName(game.player1.color) + mainColor)
//                )
//        ), 7);
//
//        setLine(MiniMessage.miniMessage().deserialize(
//                "Team <team>: (<beds>/<beds_to_win>)",
//                Placeholder.component("beds",
//                        MiniMessage.miniMessage().deserialize(highlight + game.player2.getBedsDestroyed() + mainColor)
//                ),
//                Placeholder.component("beds_to_win",
//                        MiniMessage.miniMessage().deserialize(highlight + game.bedsToWin + mainColor)
//                ),
//                Placeholder.component("team",
//                        MiniMessage.miniMessage().deserialize("<" + game.player2.color + ">" + getTeamName(game.player2.color) + mainColor)
//                )
//        ), 6);
//
//        setLine(MiniMessage.miniMessage().deserialize(
//                "Kills:  <kills>",
//                Placeholder.component("kills",
//                        MiniMessage.miniMessage().deserialize("<" + game.player1.color + ">" + game.player1.getKills() + "<" + game.player2.color + ">" + game.player2.getKills() + mainColor)
//                )
//        ), 4);
//
//        setLine(MiniMessage.miniMessage().deserialize(
//                "Deaths: <deaths>",
//                Placeholder.component("deaths",
//                        MiniMessage.miniMessage().deserialize("<" + game.player1.color + ">" + game.player1.getDeaths() + "<" + game.player2.color + ">" + game.player2.getDeaths() + mainColor)
//                )
//        ), 3);
//    }
//
//    private String getTeamName(String team) {
//        String teamStr = team.substring(0, 1).toUpperCase() + team.substring(1).toLowerCase();
//        for (String str : teamStr.split("_")) {
//            teamStr = teamStr.replace(str, str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()).replaceAll("_", " ");
//        }
//        return teamStr;
//    }
//}