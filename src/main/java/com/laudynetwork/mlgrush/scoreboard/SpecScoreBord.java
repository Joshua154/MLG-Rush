package com.laudynetwork.mlgrush.scoreboard;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.mlgrush.Colors;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpecScoreBord extends ScoreBoardBuilder {

    private final String mainColor = MLG_Rush.get().getColors().get("mainColor");
    private final String highlight = MLG_Rush.get().getColors().get("highlight");
    Game game = MLG_Rush.get().getGame();


    public SpecScoreBord(Player player) {
        super(player, HexColor.translate(MLG_Rush.get().getColors().get("MLGRush-Prefix")));

        run();
    }

    private static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public void update() {

    }

    @Override
    public void createScoreboard() {
        //Date ServerID
        setScore(ChatColor.GRAY + getCurrentDate(), 5);

        emptyLine(7);

        emptyLine(4);

        emptyLine(1);

        setScore(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + MLG_Rush.get().getColors().get("serverAddress")), 0);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game.player1 != null && game.player2 != null) {
                    int deathLength = ("Deaths: {{deaths}}").replace("{{deaths}}", "").length();
                    int killLength = ("Kills: {{kills}}").replace("{{kills}}", "").length();
                    int space;
                    if (deathLength > killLength) {
                        space = deathLength - killLength;
                    } else {
                        space = -(killLength - deathLength);
                    }

                    String scoreBordText = "Team {{team}}: ({{beds}}/{{bedsToWin}})";
                    scoreBordText = scoreBordText
                            .replace("{{beds}}", highlight + game.player1.getBedsDestroyed() + mainColor)
                            .replace("{{bedsToWin}}", highlight + game.bedsToWin + mainColor)
                            .replace("{{team}}", Colors.getHexColor(game.player1.color) + "&l" + getTeamName(game.player1.color) + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 6);


                    scoreBordText = "Team {{team}}: ({{beds}}/{{bedsToWin}})";
                    scoreBordText = scoreBordText
                            .replace("{{beds}}", highlight + game.player2.getBedsDestroyed() + mainColor)
                            .replace("{{bedsToWin}}", highlight + game.bedsToWin + mainColor)
                            .replace("{{team}}", Colors.getHexColor(game.player2.color) + "&l" + getTeamName(game.player2.color) + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 5);


                    scoreBordText = "Kills: {{kills}}";
                    scoreBordText = scoreBordText
                            .replace("{{kills}}", " ".repeat(Math.max(space, 0)) + Colors.getHexColor(game.player1.color) + game.player1.getKills() + "  &l" + Colors.getHexColor(game.player2.color) + game.player2.getKills() + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 3);


                    scoreBordText = "Deaths: {{deaths}}";
                    scoreBordText = scoreBordText
                            .replace("{{deaths}}", " ".repeat(Math.min(space, 0)) + Colors.getHexColor(game.player1.color) + game.player1.getDeaths() + "  &l" + Colors.getHexColor(game.player2.color) + game.player2.getDeaths() + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 2);
                }
            }
        }.runTaskTimer(MLG_Rush.get(), 0, 1L);
    }

    private String getTeamName(String team) {
        String teamStr = team.substring(0, 1).toUpperCase() + team.substring(1).toLowerCase();
        for (String str : teamStr.split("_")) {
            teamStr = teamStr.replace(str, str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()).replaceAll("_", " ");
        }
        return teamStr;
    }
}