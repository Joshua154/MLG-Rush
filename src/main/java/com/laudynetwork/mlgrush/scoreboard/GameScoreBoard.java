package com.laudynetwork.mlgrush.scoreboard;

import com.laudynetwork.api.chatutils.HexColor;
import com.laudynetwork.mlgrush.Colors;
import com.laudynetwork.mlgrush.MLG_Rush;
import com.laudynetwork.mlgrush.game.Game;
import com.laudynetwork.mlgrush.game.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameScoreBoard extends ScoreBoardBuilder {

    private static int animationFrame = 0;
    private final String mainColor = MLG_Rush.get().getColors().get("mainColor");
    private final String highlight = MLG_Rush.get().getColors().get("highlight");
    Game game = MLG_Rush.get().getGame();
    PlayerManager playerManager = null;


    public GameScoreBoard(Player player) {
        super(player, HexColor.translate(MLG_Rush.get().getColors().get("MLGRush-Prefix")));

        playerManager = game.getPlayer(player);

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
        setScore(ChatColor.GRAY + getCurrentDate(), 8);

        emptyLine(7);

        emptyLine(1);

        setScore(HexColor.translate(MLG_Rush.get().getColors().get("mainColor") + MLG_Rush.get().getColors().get("serverAddress")), 0);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (game.player1 != null && game.player2 != null) {
                    emptyLine(4);
                    Font font = new Font("Arial", Font.PLAIN, 2);
                    FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

                    //int deathLength = (int)(font.getStringBounds(MLG_Rush.get().getTranslations().get(MLG_Rush.get().playerLanguages.get(player)).getTranslation("scoreBoard.deaths").replace("{{deaths}}", ""), frc).getWidth());
                    //int killLength = (int)(font.getStringBounds(MLG_Rush.get().getTranslations().get(MLG_Rush.get().playerLanguages.get(player)).getTranslation("scoreBoard.kills").replace("{{kills}}", ""), frc).getWidth());
                    int deathLength = (int) (font.getStringBounds(("Deaths: {{deaths}}").replace("{{deaths}}", ""), frc).getWidth());
                    int killLength = (int) (font.getStringBounds(("Kills: {{kills}}").replace("{{kills}}", ""), frc).getWidth());
                    int space;
                    if (deathLength > killLength) {
                        space = deathLength - killLength;
                    } else {
                        space = -(killLength - deathLength);
                    }

                    String scoreBordText = "Team {{team}}: ({{beds}}/{{bedsToWin}})";
                    scoreBordText = scoreBordText
                            .replace("{{beds}}", highlight + playerManager.getBedsDestroyed() + mainColor)
                            .replace("{{bedsToWin}}", highlight + game.bedsToWin + mainColor)
                            .replace("{{team}}", Colors.getHexColor(playerManager.color) + "&l" + getTeamName(playerManager.color) + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 6);


                    scoreBordText = "Team {{team}}: ({{beds}}/{{bedsToWin}})";
                    scoreBordText = scoreBordText
                            .replace("{{beds}}", highlight + game.getOpponent(playerManager.getPlayer()).getBedsDestroyed() + mainColor)
                            .replace("{{bedsToWin}}", highlight + game.bedsToWin + mainColor)
                            .replace("{{team}}", Colors.getHexColor(game.getOpponent(playerManager.getPlayer()).color) + "&l" + getTeamName(game.getOpponent(playerManager.getPlayer()).color) + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 5);


                    scoreBordText = "Kills: {{kills}}";
                    scoreBordText = scoreBordText
                            .replace("{{kills}}", " ".repeat(Math.max(space, 0)) + highlight + playerManager.getKills() + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 3);


                    scoreBordText = "Deaths: {{deaths}}";
                    scoreBordText = scoreBordText
                            .replace("{{deaths}}", " ".repeat(Math.min(space, 0)) + highlight + playerManager.getDeaths() + mainColor);
                    setScore(HexColor.translate(mainColor + scoreBordText), 2);
                } else {
                    String str = "Waiting for Opponent";
                    setScore(HexColor.translate(mainColor + str), 4);
                }
            }
        }.runTaskTimer(MLG_Rush.get(), 0, 1L);
    }

    private String getTeamName(String team) {
        String teamStr = team.substring(0, 1).toUpperCase() + team.substring(1).toLowerCase();
        for (String str : teamStr.split("_")) {
            teamStr = teamStr.replace(str, str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase()).replace("_", " ");
        }
        return teamStr;
    }
}