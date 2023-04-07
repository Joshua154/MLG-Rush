//package com.laudynetwork.mlgrush.scoreboard;
//
//import com.laudynetwork.mlgrush.MLG_Rush;
//import com.laudynetwork.mlgrush.game.Game;
//import net.kyori.adventure.text.Component;
//import net.kyori.adventure.text.minimessage.MiniMessage;
//import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
//import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
//import net.megavex.scoreboardlibrary.api.sidebar.AbstractSidebar;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
//import org.bukkit.scheduler.BukkitTask;
//import org.jetbrains.annotations.NotNull;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class oldSpecScoreBord extends AbstractSidebar {
//
//    private final String mainColor = MLG_Rush.get().getColors().get("mainColor");
//    private final String highlight = MLG_Rush.get().getColors().get("highlight");
//    Game game = MLG_Rush.get().getGame();
//    private final List<DynamicLine> dynamicLines = new ArrayList<>();
//    private int timer;
//
//    private final BukkitTask task;
//
//
//    public oldSpecScoreBord(@NotNull Plugin plugin, @NotNull ScoreboardLibrary scoreboardLibrary, @NotNull Player player) {
//        super(scoreboardLibrary.createSidebar(8));
//
//        sidebar.title(MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("MLGRush-Prefix")));
//        registerEmptyLine(7);
//        registerEmptyLine(4);
//        registerEmptyLine(1);
//
//        registerStaticLine(0, MiniMessage.miniMessage().deserialize(MLG_Rush.get().getColors().get("mainColor") + MLG_Rush.get().getColors().get("serverAddress")));
//
//        dynamicLines.add(registerDynamicLine(6, () -> MiniMessage.miniMessage().deserialize(
//                "Team <team>: (<beds>/<bedsToWin>)",
//                Placeholder.component("beds",
//                        Component.text(highlight + game.player1.getBedsDestroyed() + mainColor)
//                ),
//                Placeholder.component("bedsToWin",
//                        Component.text(highlight + game.bedsToWin + mainColor)
//                ),
//                Placeholder.component("team",
//                        Component.text(game.player1.color + getTeamName(game.player1.color) + mainColor)
//                )
//        )));
//
//        dynamicLines.add(registerDynamicLine(5, () -> MiniMessage.miniMessage().deserialize(
//                "Team <team>: (<beds>/<bedsToWin>)",
//                Placeholder.component("beds",
//                        Component.text(highlight + game.player2.getBedsDestroyed() + mainColor)
//                ),
//                Placeholder.component("bedsToWin",
//                        Component.text(highlight + game.bedsToWin + mainColor)
//                ),
//                Placeholder.component("team",
//                        Component.text(game.player2.color + getTeamName(game.player2.color) + mainColor)
//                )
//        )));
//
//        dynamicLines.add(registerDynamicLine(3, () -> MiniMessage.miniMessage().deserialize(
//                "Kills:  <kills>",
//                Placeholder.component("kills",
//                        Component.text(game.player1.color + game.player1.getKills() + game.player2.color + game.player2.getKills() + mainColor)
//                )
//        )));
//
//        dynamicLines.add(registerDynamicLine(2, () -> MiniMessage.miniMessage().deserialize(
//                "Deaths: <deaths>",
//                Placeholder.component("deaths",
//                        Component.text(game.player1.color + game.player1.getDeaths() + game.player2.color + game.player2.getDeaths() + mainColor)
//                )
//        )));
//
//        /*int deathLength = ("Deaths: {{deaths}}").replace("{{deaths}}", "").length();
//        int killLength = ("Kills: {{kills}}").replace("{{kills}}", "").length();
//        int space;
//        if (deathLength > killLength) {
//            space = deathLength - killLength;
//        } else {
//            space = -(killLength - deathLength);
//        }*/
//
//        task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
//            if (game.player1 != null && game.player2 != null) {
//                for (DynamicLine dynamicLine : dynamicLines) {
//                    dynamicLine.update();
//                }
//            }
//        }, 20, 20);
//
//        sidebar.addPlayer(player);
//    }
//
//    @Override
//    protected void onClosed() {
//        task.cancel();
//        sidebar.close();
//    }
//
//    private static String getCurrentDate() {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
//        LocalDateTime now = LocalDateTime.now();
//        return dtf.format(now);
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